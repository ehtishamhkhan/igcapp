-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 10, 2021 at 07:31 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.3.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vaccine`
--

-- --------------------------------------------------------

--
-- Table structure for table `configs`
--

CREATE TABLE `configs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `config_key` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `config_value` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `configs`
--

INSERT INTO `configs` (`id`, `config_key`, `config_value`, `created_at`, `updated_at`) VALUES
(1, 'trial_end', '28-01-2021', '2021-01-06 16:18:57', '2021-01-06 17:52:35'),
(2, 'trial_test', '25-01-2021', '2021-01-06 16:19:02', '2021-01-06 17:52:12'),
(3, 'max_volunteers', '1', '2021-01-06 19:37:52', '2021-01-06 17:40:30');

-- --------------------------------------------------------

--
-- Table structure for table `failed_jobs`
--

CREATE TABLE `failed_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `connection` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `queue` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `exception` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(2, '2014_10_12_100000_create_password_resets_table', 1),
(3, '2016_06_01_000001_create_oauth_auth_codes_table', 1),
(4, '2016_06_01_000002_create_oauth_access_tokens_table', 1),
(5, '2016_06_01_000003_create_oauth_refresh_tokens_table', 1),
(6, '2016_06_01_000004_create_oauth_clients_table', 1),
(7, '2016_06_01_000005_create_oauth_personal_access_clients_table', 1),
(8, '2019_08_19_000000_create_failed_jobs_table', 1),
(11, '2021_01_06_135903_create_configs_table', 3),
(12, '2021_01_06_161520_create_vaccines_table', 4),
(13, '2014_10_12_000000_create_users_table', 5);

-- --------------------------------------------------------

--
-- Table structure for table `oauth_access_tokens`
--

CREATE TABLE `oauth_access_tokens` (
  `id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `client_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `scopes` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `revoked` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `expires_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `oauth_access_tokens`
--

INSERT INTO `oauth_access_tokens` (`id`, `user_id`, `client_id`, `name`, `scopes`, `revoked`, `created_at`, `updated_at`, `expires_at`) VALUES
('06175cf9dd28337c3860b526b57f1c99076def9ccf5182da37e1f6f60a824d5dec1134db6114e8c8', 6, 4, 'Personal Access Token', '[]', 0, '2021-01-06 11:23:32', '2021-01-06 11:23:32', '2022-01-06 16:23:32'),
('081dd8e6a25f01f40494c2a426b0043d80eeed1141ac3f415ccf973e5f5f59c43a0d522a97ada287', 2, 4, 'Personal Access Token', '[]', 0, '2021-01-06 06:31:58', '2021-01-06 06:31:58', '2022-01-06 11:31:58'),
('0943f6a1a0627d1d9d2a67eaef794aa5096291c452657cf6c9a4725d5ffe6d187fc2e392ee56758c', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 04:20:54', '2021-01-06 04:20:54', '2022-01-06 09:20:54'),
('0dc3614fbf1ba8b0569baec0e19ac0a1128bf3b7de4e40534ac31d45c283e7794d6d3947b9453f24', 4, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:22:53', '2021-01-06 13:22:53', '2022-01-06 18:22:53'),
('1639e3b456dd7d74f413157ff726b46312b11febf74e6dfa8f603665203008b5ff403b8f7dd0fa12', 4, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:19:47', '2021-01-06 13:19:47', '2022-01-06 18:19:47'),
('2091c379d1c0c35a963d7d80027e63984bf0a9e7f5f3a18885958603dfbe00ab5590dc0b83c8ab50', 4, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:20:30', '2021-01-06 13:20:30', '2022-01-06 18:20:30'),
('2bf41a598c5547e1303715e00433dde631fe200cc03d05b6109fe4e710337fee3520e20e528736be', 4, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:20:55', '2021-01-06 13:20:55', '2022-01-06 18:20:55'),
('42c12044f837e5306d871b33558a41d5032acf6895f8004be0ec52ce02e9ecd48e31f1aaa2db505b', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:25:54', '2021-01-06 13:25:54', '2022-01-06 18:25:54'),
('446844d2922fa3c5fa41dd987adc16ccc9dd20e49a9927c4825a17bf9293c6901a0b541129d2c240', 4, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:20:22', '2021-01-06 13:20:22', '2022-01-06 18:20:22'),
('4508ca0871673d9ec705cc1714139acb57464fc793783a9187ed990eefa4706ab1640ab5a1e01aac', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 16:50:55', '2021-01-06 16:50:55', '2022-01-06 21:50:55'),
('504afa5a8ea21d15256a083ebe15f147c43349de1918c10684b371096c1d5fda9735b9ed3191b6e0', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 17:01:58', '2021-01-06 17:01:58', '2022-01-06 22:01:58'),
('584537badd9bd33d88400a5408ac3a5a94d9858356a01b2411f81b6c24907a04eea91c0047625ba7', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 12:41:34', '2021-01-06 12:41:34', '2022-01-06 17:41:34'),
('601b9bf1a5a081f16286692b6c9559aff4cd3b0ab4b01f4ff66d32983f6b4b3edaaafa2141d9d6f9', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:25:06', '2021-01-06 13:25:06', '2022-01-06 18:25:06'),
('607ed737ccbb83598816518e49cdb46498dcd18d5131f006e905ae4482592f36fbc69fb550aaa2ad', 4, 4, 'Personal Access Token', '[]', 0, '2021-01-06 08:00:56', '2021-01-06 08:00:56', '2022-01-06 13:00:56'),
('6e396dac3aea4c78e5c23c30b0e6510adc80146159c9b86f9d2520a12ecf7c8a5d3b1dc35d8a7055', 3, 4, 'Personal Access Token', '[]', 0, '2021-01-06 07:00:00', '2021-01-06 07:00:00', '2022-01-06 12:00:00'),
('6fc35621b9e51754b0144741e6f83838765fc80ef02d182404b1a07ce1bc5957d46c9b4aa65486e2', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:08:00', '2021-01-06 13:08:00', '2022-01-06 18:08:00'),
('75f86a704ae5547ee001910ab52929ede46275419ab6e2454490c112b79fe2d9e5e02dc16a0ff730', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 04:25:37', '2021-01-06 04:25:37', '2022-01-06 09:25:37'),
('7751dddb8b57ebe284495ca99267ef811dfcd90ff15f4bf9fb2bc7eca2d13b335cb00e6a05714c71', 4, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:21:20', '2021-01-06 13:21:20', '2022-01-06 18:21:20'),
('811db4671e54e543fd9e837bc192623abf672ab37cb0190538b1639cfefc9b08d28c88274334a5cd', 4, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:21:46', '2021-01-06 13:21:46', '2022-01-06 18:21:46'),
('8f34385536d95173030ee46e6076b87a3eabaf7ec799e10cbe7c43f0da975486503cdce0a44a8fdc', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:08:05', '2021-01-06 13:08:05', '2022-01-06 18:08:05'),
('97256857264a47aaca0f67bae910429489fe32558b90c1cb907587555d90a011eefd0a41b56e2448', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:25:17', '2021-01-06 13:25:17', '2022-01-06 18:25:17'),
('a014c39bfc49910133a3d8295676ae70d1d191c77f7ea6119e981997bd94691bb26f5ce0face8ceb', 7, 4, 'Personal Access Token', '[]', 0, '2021-01-06 12:05:41', '2021-01-06 12:05:41', '2022-01-06 17:05:41'),
('b4e74cdb2fdb0182a54ec75f19fbe2993f78140ecd2a946750350d2db62be9b187f6263e27787868', 5, 4, 'Personal Access Token', '[]', 0, '2021-01-06 10:38:39', '2021-01-06 10:38:39', '2022-01-06 15:38:39'),
('d37c9524779ced3d759dffd4b0faa29c0ef097fe42d21a7207f8e7bb5e28f6117be5f1c9f9a06776', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:19:07', '2021-01-06 13:19:07', '2022-01-06 18:19:07'),
('d8d27c74d7d8cf904aa6bc832abc2bd077fb2db1f75df3ad0d0ee60cde16309e4ce45a6bf9f929f1', 4, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:21:28', '2021-01-06 13:21:28', '2022-01-06 18:21:28'),
('eb5b025af5e2f97238a31fe464b4d269dff427bdb762198937c3ff8cff439c9851c80fa1cb72c412', 4, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:21:34', '2021-01-06 13:21:34', '2022-01-06 18:21:34'),
('efba48a519d5b2b51836a4ebacbd72758b3328d12f5fe449523aca6f70c9bd6284618f3281f9a6d8', 3, 4, 'Personal Access Token', '[]', 0, '2021-01-06 13:16:00', '2021-01-06 13:16:00', '2022-01-06 18:16:00'),
('f9153e314ae26d6a5682d74d5f6e1136cbf38dfa39da0b6f37645cb1c4a8ed048b227566c86af2e6', 1, 4, 'Personal Access Token', '[]', 0, '2021-01-06 16:49:11', '2021-01-06 16:49:11', '2022-01-06 21:49:11');

-- --------------------------------------------------------

--
-- Table structure for table `oauth_auth_codes`
--

CREATE TABLE `oauth_auth_codes` (
  `id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `client_id` bigint(20) UNSIGNED NOT NULL,
  `scopes` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expires_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `oauth_clients`
--

CREATE TABLE `oauth_clients` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `secret` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `provider` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `redirect` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `personal_access_client` tinyint(1) NOT NULL,
  `password_client` tinyint(1) NOT NULL,
  `revoked` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `oauth_clients`
--

INSERT INTO `oauth_clients` (`id`, `user_id`, `name`, `secret`, `provider`, `redirect`, `personal_access_client`, `password_client`, `revoked`, `created_at`, `updated_at`) VALUES
(1, NULL, 'Laravel Personal Access Client', 'pBsJGdaHn7PRALVLcXzvLQEzrHw88FPRd3izg2nD', NULL, 'http://localhost', 1, 0, 0, '2021-01-05 04:48:20', '2021-01-05 04:48:20'),
(2, NULL, 'Laravel Password Grant Client', 'WZdO3Jep6aeOoHf2yn0CIOxxh0nHzjUM3jWDO6cP', 'users', 'http://localhost', 0, 1, 0, '2021-01-05 04:48:20', '2021-01-05 04:48:20'),
(3, 1, 'admin', 'ADLJEK9kGhx2xVuo5Kj0Azmd2Ww7Rxtk6Mhr0llA', NULL, 'http://localhost/auth/callback', 0, 0, 0, '2021-01-05 04:50:49', '2021-01-05 04:50:49'),
(4, NULL, 'admin', 'b1hz4MYQpwFB2C1kP89q4cMRIUGox7nIAz10w0sL', NULL, 'http://localhost', 1, 0, 0, '2021-01-05 04:51:04', '2021-01-05 04:51:04');

-- --------------------------------------------------------

--
-- Table structure for table `oauth_personal_access_clients`
--

CREATE TABLE `oauth_personal_access_clients` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `client_id` bigint(20) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `oauth_personal_access_clients`
--

INSERT INTO `oauth_personal_access_clients` (`id`, `client_id`, `created_at`, `updated_at`) VALUES
(1, 1, '2021-01-05 04:48:20', '2021-01-05 04:48:20'),
(2, 4, '2021-01-05 04:51:04', '2021-01-05 04:51:04');

-- --------------------------------------------------------

--
-- Table structure for table `oauth_refresh_tokens`
--

CREATE TABLE `oauth_refresh_tokens` (
  `id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `access_token_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expires_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `password_resets`
--

CREATE TABLE `password_resets` (
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type_id` tinyint(3) UNSIGNED NOT NULL DEFAULT 0,
  `gender` tinyint(3) UNSIGNED NOT NULL,
  `age` int(10) UNSIGNED NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `health_condition` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vaccine_group` tinyint(3) UNSIGNED DEFAULT NULL,
  `dose` double(8,2) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `infected` tinyint(3) UNSIGNED NOT NULL DEFAULT 0,
  `tested_at` timestamp NULL DEFAULT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `type_id`, `gender`, `age`, `address`, `health_condition`, `vaccine_group`, `dose`, `email`, `infected`, `tested_at`, `email_verified_at`, `password`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'Admin', 2, 1, 25, 'basjsjsj hsjjsjbjsjsjsj', 'hsjsjsjzjj hzjzjsjbjjsj', 2, 0.50, 'admin@gmail.com', 1, '2021-01-06 12:41:46', NULL, '$2y$10$t13tdeMUxzsNAIUTggDiz.fCbYCa6YVTNw4SERRPIoXlNVbCL6tyi', 'bWUPzw9ZeOZLl1dc5BkUEnwxhAMKnIvdnfpIQThby8l99dhWfsShyOXrMnkU', '2021-01-06 12:41:34', '2021-01-06 12:42:18'),
(3, 'arsgahaha', 1, 2, 42, 'yahahaahha', 'gshshshhzhzhhz', 2, 1.00, 'arsalan1@gmail.com', 0, '2021-01-06 13:17:31', NULL, '$2y$10$2e2n0zyoX1RttsUvOvD63.nFZDQ/09w9Uy8wUlKdL.yLe/WPDYBlu', NULL, '2021-01-06 13:16:00', '2021-01-06 13:17:31');

-- --------------------------------------------------------

--
-- Table structure for table `vaccines`
--

CREATE TABLE `vaccines` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `group` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `vaccine_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `vaccines`
--

INSERT INTO `vaccines` (`id`, `group`, `name`, `vaccine_type`, `created_at`, `updated_at`) VALUES
(1, 'A', 'SLCV2020', 'vaccine', '2021-01-06 16:18:40', '2021-01-06 16:18:43'),
(2, 'B', 'Unknown', 'placebo', '2021-01-06 16:18:46', '2021-01-06 16:18:49');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `configs`
--
ALTER TABLE `configs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `oauth_access_tokens`
--
ALTER TABLE `oauth_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD KEY `oauth_access_tokens_user_id_index` (`user_id`);

--
-- Indexes for table `oauth_auth_codes`
--
ALTER TABLE `oauth_auth_codes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `oauth_auth_codes_user_id_index` (`user_id`);

--
-- Indexes for table `oauth_clients`
--
ALTER TABLE `oauth_clients`
  ADD PRIMARY KEY (`id`),
  ADD KEY `oauth_clients_user_id_index` (`user_id`);

--
-- Indexes for table `oauth_personal_access_clients`
--
ALTER TABLE `oauth_personal_access_clients`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `oauth_refresh_tokens`
--
ALTER TABLE `oauth_refresh_tokens`
  ADD PRIMARY KEY (`id`),
  ADD KEY `oauth_refresh_tokens_access_token_id_index` (`access_token_id`);

--
-- Indexes for table `password_resets`
--
ALTER TABLE `password_resets`
  ADD KEY `password_resets_email_index` (`email`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- Indexes for table `vaccines`
--
ALTER TABLE `vaccines`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `configs`
--
ALTER TABLE `configs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `oauth_clients`
--
ALTER TABLE `oauth_clients`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `oauth_personal_access_clients`
--
ALTER TABLE `oauth_personal_access_clients`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `vaccines`
--
ALTER TABLE `vaccines`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
