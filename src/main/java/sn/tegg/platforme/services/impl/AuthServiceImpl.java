package sn.tegg.platforme.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.tegg.platforme.data.enums.UserStatus;
import sn.tegg.platforme.data.enums.UserType;
import sn.tegg.platforme.data.models.*;
import sn.tegg.platforme.data.repository.*;
import sn.tegg.platforme.security.JwtUtils;
import sn.tegg.platforme.security.UserDetailsImpl;
import sn.tegg.platforme.services.AuthService;
import sn.tegg.platforme.web.dto.request.LoginRequest;
import sn.tegg.platforme.web.dto.request.RegisterRequest;
import sn.tegg.platforme.web.dto.request.VerifyOtpRequest;
import sn.tegg.platforme.web.dto.response.JwtResponse;
import sn.tegg.platforme.web.dto.response.UserResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ArtisanRepository artisanRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, LocalDateTime> otpExpiry = new HashMap<>();
    private static final int OTP_EXPIRY_MINUTES = 5;

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return JwtResponse.builder()
                .token(jwt)
                .id(userDetails.getId())
                .phone(userDetails.getUsername())
                .userType(user.getUserType().name())
                .build();
    }

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone number already registered");
        }

        User user = User.builder()
                .phone(request.getPhone())
                .passwordHash(encoder.encode(request.getPassword()))
                .userType(request.getUserType())
                .status(UserStatus.ACTIVE)
                .phoneVerified(false)
                .build();

        user = userRepository.save(user);

        if (request.getUserType() == UserType.CLIENT) {
            Client client = Client.builder()
                    .userId(user.getId())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .clientType(request.getClientType())
                    .companyName(request.getCompanyName())
                    .build();

            clientRepository.save(client);
        } else if (request.getUserType() == UserType.ARTISAN) {
            Artisan artisan = Artisan.builder()
                    .userId(user.getId())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .build();

            artisanRepository.save(artisan);
        }

        sendOtp(request.getPhone());

        return getUserByPhone(request.getPhone());
    }

    @Override
    public boolean verifyOtp(VerifyOtpRequest request) {
        String storedOtp = otpStorage.get(request.getPhone());
        LocalDateTime expiry = otpExpiry.get(request.getPhone());

        if (storedOtp == null || expiry == null) {
            return false;
        }

        if (LocalDateTime.now().isAfter(expiry)) {
            otpStorage.remove(request.getPhone());
            otpExpiry.remove(request.getPhone());
            return false;
        }

        if (!storedOtp.equals(request.getCode())) {
            return false;
        }

        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPhoneVerified(true);
        userRepository.save(user);

        otpStorage.remove(request.getPhone());
        otpExpiry.remove(request.getPhone());

        return true;
    }

    @Override
    public String sendOtp(String phone) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(phone, otp);
        otpExpiry.put(phone, LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));

        System.out.println("OTP for " + phone + ": " + otp);

        return otp;
    }

    @Override
    public UserResponse getCurrentUser(String phone) {
        return getUserByPhone(phone);
    }

    @Override
    public boolean logout(String phone) {
        SecurityContextHolder.clearContext();
        return true;
    }

    private UserResponse getUserByPhone(String phone) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return buildUserResponse(user);
    }

    private UserResponse buildUserResponse(User user) {
        UserResponse.UserResponseBuilder builder = UserResponse.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .userType(user.getUserType())
                .status(user.getStatus())
                .phoneVerified(user.getPhoneVerified())
                .createdAt(user.getCreatedAt())
                .lastLogin(user.getLastLogin());

        if (user.getUserType() == UserType.CLIENT) {
            clientRepository.findByUserId(user.getId()).ifPresent(client -> {
                builder.client(UserResponse.ClientInfo.builder()
                        .id(client.getId())
                        .firstName(client.getFirstName())
                        .lastName(client.getLastName())
                        .email(client.getEmail())
                        .photoUrl(client.getPhotoUrl())
                        .clientType(client.getClientType())
                        .companyName(client.getCompanyName())
                        .build());
            });
        } else if (user.getUserType() == UserType.ARTISAN) {
            artisanRepository.findByUserId(user.getId()).ifPresent(artisan -> {
                builder.artisan(UserResponse.ArtisanInfo.builder()
                        .id(artisan.getId())
                        .firstName(artisan.getFirstName())
                        .lastName(artisan.getLastName())
                        .email(artisan.getEmail())
                        .photoUrl(artisan.getPhotoUrl())
                        .bio(artisan.getBio())
                        .isValidated(artisan.getIsValidated())
                        .rating(artisan.getRating().toString())
                        .isOnline(artisan.getIsOnline())
                        .build());
            });
        }

        return builder.build();
    }
}
