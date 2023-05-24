CREATE TABLE IF NOT EXISTS `appointment` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `type` enum('consultation','service','operation') NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `meeting_date` datetime NOT NULL,
  `user_doctor_id` int unsigned NOT NULL,
  `hospital_card_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);
INSERT INTO `appointment` (`id`, `type`, `description`, `meeting_date`, `user_doctor_id`, `hospital_card_id`) VALUES
	(1, 'consultation', 'Consultation for checking to the doctor', '2023-05-16 14:14:14', 1, 1);

CREATE TABLE IF NOT EXISTS `categories` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
);
INSERT INTO `categories` (`id`, `name`) VALUES
	(2, 'dentist'),
	(4, 'pediatritian'),
	(3, 'sergeon'),
	(1, 'troumatologist'),
	(5, 'veterinarian');

CREATE TABLE IF NOT EXISTS `diagnoses` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `type` enum('tough','normal','easy') DEFAULT NULL,
  `hospital_card_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);
INSERT INTO `diagnoses` (`id`, `name`, `description`, `type`, `hospital_card_id`) VALUES
	(1, 'COVID_19', 'Very tough illness', 'tough', 4),
	(2, 'Lag brocken', 'He have brake his lag!', 'normal', 1),
	(3, 'Eyes too soft', 'Her eyes is too soft. Medical care needed', 'easy', 3),
	(4, 'Nothing', 'You are absolutelly healthy man', 'tough', 2);

CREATE TABLE IF NOT EXISTS `doctors` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `second_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `work_experience` int unsigned DEFAULT NULL,
  `type` enum('doctor','nurse') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `category_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
);
INSERT INTO `doctors` (`id`, `name`, `second_name`, `email`, `work_experience`, `type`, `created_at`, `updated_at`, `category_id`) VALUES
	(1, 'Grygoryi', 'Molchanov', 'xone@gmail.com', 3, 'doctor', '2023-03-30 13:29:46', '2023-03-30 13:29:47', 4),
	(2, 'Andy', 'Fedorchuk', 'adjei_fedorchuk@gmail.com', 2, 'doctor', '2023-03-30 13:31:38', '2023-03-30 13:31:38', 2),
	(3, 'Marina', 'Dibrova-Fedorchuk', 'marina_dibrova_fedorchuk@gmail.com', 5, 'nurse', '2023-03-30 13:32:17', '2023-03-30 13:32:18', 2),
	(4, 'Andrew', 'Brune', 'adrew_brune@gmail.com', 1, 'doctor', '2023-03-30 13:32:51', '2023-03-30 13:32:52', 3),
	(5, 'Magdalena', 'Fedorchuk', 'magdalena_fedorchuk@gmail.com', 6, 'doctor', '2023-03-30 13:33:30', '2023-03-30 13:33:31', 5),
	(6, 'Roman', 'Fedorchuk', 'roman_fedorchuk@gmail.com', 4, 'nurse', '2023-03-30 13:34:06', '2023-03-30 13:34:07', 1),
	(7, 'Dima', 'Zhdanov', 'dima_zhdanov@gmail.com', 3, 'doctor', '2023-03-30 13:29:46', '2023-03-30 13:29:47', 3);

CREATE TABLE IF NOT EXISTS `enrolls` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(250) NOT NULL,
  `is_approved` tinyint NOT NULL DEFAULT '0',
  `user_id` int unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);
INSERT INTO `enrolls` (`id`, `description`, `is_approved`, `user_id`, `created_at`) VALUES
	(1, 'I want my dog to be fixed!', 1, 4, '2023-03-30 13:41:47'),
	(2, 'I want to speak English with doctor, because others do not understand me', 0, 3, '2023-03-30 13:42:19'),
	(3, 'I fill pain in my teath', 1, 1, '2023-03-30 13:42:39'),
	(4, 'I fill pain', 0, 2, '2023-03-30 13:43:04'),
	(5, 'I want to check my health', 1, 3, '2023-03-30 13:43:47');

CREATE TABLE IF NOT EXISTS `hospital_cards` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);
INSERT INTO `hospital_cards` (`id`, `name`, `created_at`, `user_id`) VALUES
	(1, 'Yehor\'s hospital card', '2023-03-30 13:35:37', 1),
	(2, 'Illia\'s hospital card', '2023-03-30 13:35:59', 3),
	(3, 'Sonya\'s hospital card', '2023-03-30 13:36:20', 4),
	(4, 'Anya\'s hospital card', '2023-03-30 13:37:02', 2);

CREATE TABLE IF NOT EXISTS `log_events` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(500) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);
INSERT INTO `log_events` (`id`, `description`, `created_at`) VALUES
	(1, 'created all dumps for the dataabse', '2023-03-30 13:40:35'),
	(2, 'created an application', '2023-03-30 13:40:46'),
	(3, 'compiled an application', '2023-03-30 13:40:56'),
	(4, 'deployed an application', '2023-03-30 13:41:06'),
	(5, 'tested an application', '2023-03-30 13:41:15');

CREATE TABLE IF NOT EXISTS `permissons` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
);
INSERT INTO `permissons` (`id`, `name`, `description`) VALUES
	(1, 'read', 'read permission allows you to read information from some sources'),
	(2, 'write', 'write permission allows you to write some sources'),
	(3, 'delete', 'delete permission allows you delete some information from sources');

CREATE TABLE IF NOT EXISTS `roles` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
);
INSERT INTO `roles` (`id`, `name`) VALUES
	(1, 'admin'),
	(2, 'moderator'),
	(3, 'user');

CREATE TABLE IF NOT EXISTS `roles_permissons` (
  `permisson_id` int unsigned NOT NULL,
  `role_id` int unsigned NOT NULL
);
INSERT INTO `roles_permissons` (`permisson_id`, `role_id`) VALUES
	(1, 3),
	(1, 2),
	(2, 2),
	(1, 1),
	(2, 1),
	(3, 1);

CREATE TABLE IF NOT EXISTS `users` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `second_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `password` varchar(128) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
);
INSERT INTO `users` (`id`, `name`, `second_name`, `email`, `phone`, `password`, `created_at`, `updated_at`) VALUES
	(1, 'Yehor', 'Chevardin', 'egor03052004@gmail.com', '+380999425174', 'password', '2023-03-30 13:22:07', '2023-03-30 13:22:08'),
	(2, 'Anya', 'Sheremet', 'an.scheremet2013@gmail.com', '+48451001028', 'anya', '2023-03-30 13:24:33', '2023-03-30 13:24:34'),
	(3, 'Illia', 'Kryzhanovskiy', 'kryzha@gmail.com', '+48574350682', 'kryzha', '2023-03-30 13:25:17', '2023-03-30 13:25:17'),
	(4, 'Sonya', 'Avdeeva', 'sonya_avdeeva', '+380999564732', 'sonya', '2023-03-30 13:25:59', '2023-03-30 13:25:59'),
	(5, 'Some', 'Some', 'email@example.com', '123-456-7890', 'googleit', '2023-04-29 03:14:12', '2023-04-29 03:14:12');

CREATE TABLE IF NOT EXISTS `users_doctors` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned NOT NULL,
  `doctor_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);
INSERT INTO `users_doctors` (`id`, `user_id`, `doctor_id`) VALUES
	(1, 1, 1),
	(2, 3, 1),
	(3, 4, 1),
	(4, 2, 6),
	(5, 2, 2);

CREATE TABLE IF NOT EXISTS `users_roles` (
  `role_id` int unsigned NOT NULL,
  `user_id` int unsigned NOT NULL
);
INSERT INTO `users_roles` (`role_id`, `user_id`) VALUES
	(1, 1),
	(2, 1),
	(3, 1),
	(2, 3),
	(3, 3),
	(3, 2),
	(3, 4);
