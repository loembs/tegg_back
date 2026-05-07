package sn.tegg.platforme.services;

import sn.tegg.platforme.data.enums.UserType;
import sn.tegg.platforme.web.dto.request.LoginRequest;
import sn.tegg.platforme.web.dto.request.RegisterRequest;
import sn.tegg.platforme.web.dto.request.VerifyOtpRequest;
import sn.tegg.platforme.web.dto.response.JwtResponse;
import sn.tegg.platforme.web.dto.response.UserResponse;

public interface AuthService {

    JwtResponse login(LoginRequest request);

    UserResponse register(RegisterRequest request);

    boolean verifyOtp(VerifyOtpRequest request);

    String sendOtp(String phone);

    UserResponse getCurrentUser(String phone);

    boolean logout(String phone);
}
