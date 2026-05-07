-- ============================================
-- Platforme Tegg - Initial Data Script
-- ============================================

-- Insert Users (password: password123 for all test users)
-- Password hashed with BCrypt for "password123"
INSERT INTO users (phone, password_hash, user_type, status, phone_verified, created_at, last_login) VALUES
('+221771234567', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', 'ACTIVE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('+221772345678', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'CLIENT', 'ACTIVE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('+221773456789', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ARTISAN', 'ACTIVE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('+221774567890', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ARTISAN', 'ACTIVE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('+221775678901', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'CLIENT', 'ACTIVE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (phone) DO NOTHING;

-- Insert Admin Profile
INSERT INTO admins (user_id, first_name, last_name, email, role, created_at, updated_at) VALUES
(1, 'Admin', 'Principal', 'admin@tegg.sn', 'SUPER_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (user_id) DO NOTHING;

-- Insert Client Profiles
INSERT INTO clients (user_id, first_name, last_name, email, photo_url, client_type, company_name, created_at, updated_at) VALUES
(2, 'Moussa', 'Diop', 'moussa.diop@email.com', null, 'INDIVIDUAL', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Fatou', 'Sow', 'fatou.sow@email.com', null, 'PROFESSIONAL', 'Sarl Services Plus', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (user_id) DO NOTHING;

-- Insert Artisan Profiles
INSERT INTO artisans (user_id, first_name, last_name, email, photo_url, bio, cin_number, is_validated, validated_at, validated_by, rating, is_online, created_at, updated_at) VALUES
(3, 'Ibrahima', 'Ndiaye', 'ibrahima.ndiaye@email.com', null, 'Plombier professionnel avec 10 ans d''expérience', '1234567890123', true, CURRENT_TIMESTAMP, 1, 4.50, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Aminata', 'Ba', 'aminata.ba@email.com', null, 'Électricienne certifiée', '9876543210987', true, CURRENT_TIMESTAMP, 1, 4.80, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (user_id) DO NOTHING;

-- Insert Service Categories
INSERT INTO service_categories (name, name_fr, description, icon, color, display_order, is_active, created_at, updated_at) VALUES
('Plumbing', 'Plomberie', 'Services de plomberie et sanitaires', 'plumbing', '#2196F3', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Electricity', 'Électricité', 'Services électricité et éclairage', 'electricity', '#FFC107', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Carpentry', 'Menuiserie', 'Travaux de menuiserie et bois', 'carpentry', '#795548', 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Painting', 'Peinture', 'Services de peinture et décoration', 'painting', '#E91E63', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cleaning', 'Nettoyage', 'Services de nettoyage et entretien', 'cleaning', '#4CAF50', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- Insert Service Subcategories
INSERT INTO service_subcategories (category_id, name, name_fr, description, icon, color, display_order, is_active, created_at, updated_at) VALUES
(1, 'Leak Repair', 'Réparation fuites', 'Réparation de fuites d''eau', 'leak', '#F44336', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Sanitary Installation', 'Installation sanitaires', 'Installation de lavabos, WC, douches', 'sanitary', '#F44336', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Outlet Installation', 'Installation prises', 'Installation de prises électriques', 'outlet', '#FFC107', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Panel Installation', 'Tableau électrique', 'Installation et rénovation de tableaux électriques', 'panel', '#FFC107', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Custom Furniture', 'Meubles sur mesure', 'Conception et fabrication de meubles', 'furniture', '#795548', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Furniture Repair', 'Réparation meubles', 'Réparation de meubles existants', 'repair', '#795548', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Interior Painting', 'Peinture intérieure', 'Peinture des murs et plafonds intérieurs', 'brush', '#E91E63', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Exterior Painting', 'Peinture extérieure', 'Peinture de façades et extérieurs', 'roller', '#E91E63', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Home Cleaning', 'Nettoyage domicile', 'Nettoyage complet de maison/appartement', 'home', '#4CAF50', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Office Cleaning', 'Nettoyage bureaux', 'Nettoyage de bureaux et espaces professionnels', 'office', '#4CAF50', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- Insert Service Items
INSERT INTO service_items (category_id, subcategory_id, name, name_fr, description, estimated_price, is_active, created_at, updated_at) VALUES
-- Plomberie services
(1, 1, 'Leak Repair', 'Réparation fuite eau', 'Réparation de fuites d''eau dans la cuisine ou salle de bain', 5000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 'Unclogging', 'Débouchement canalisations', 'Débouchement de canalisations bouchées', 3000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 'Faucet Installation', 'Installation robinet', 'Installation de robinet de cuisine ou salle de bain', 2000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 'Toilet Installation', 'Installation WC', 'Installation complète de WC avec raccordement', 10000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Électricité services
(2, 3, 'Outlet Installation', 'Installation prise électrique', 'Installation de prise électrique standard', 1500, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 'Outlet Replacement', 'Remplacement prise', 'Remplacement de prise électrique défectueuse', 1000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 'Panel Installation', 'Installation tableau', 'Installation de tableau électrique divisionnaire', 15000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 'Compliance', 'Mise aux normes', 'Mise aux normes électriques d''un logement', 25000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- ============================================
-- Test Credentials Summary:
-- ============================================
-- ADMIN:   +221771234567 / password123
-- CLIENT:  +221772345678 / password123  (Moussa Diop)
-- ARTISAN: +221773456789 / password123  (Ibrahima Ndiaye - Plombier)
-- ARTISAN: +221774567890 / password123  (Aminata Ba - Électricienne)
-- CLIENT:  +221775678901 / password123  (Fatou Sow - Professionnelle)
