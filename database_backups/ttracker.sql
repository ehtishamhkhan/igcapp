-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 02, 2021 at 10:31 AM
-- Server version: 10.4.16-MariaDB
-- PHP Version: 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ttracker`
--

-- --------------------------------------------------------

--
-- Table structure for table `attendances`
--

CREATE TABLE `attendances` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `attendances`
--

INSERT INTO `attendances` (`id`, `user_id`, `created_at`, `updated_at`) VALUES
(1, 3, '2020-12-19 12:46:07', '2020-12-19 12:46:07');

-- --------------------------------------------------------

--
-- Table structure for table `checkpoints`
--

CREATE TABLE `checkpoints` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `route_id` int(11) NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lat` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '24.869555',
  `lng` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '67.135869',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `checkpoints`
--

INSERT INTO `checkpoints` (`id`, `route_id`, `description`, `lat`, `lng`, `created_at`, `updated_at`) VALUES
(1, 1, 'Stop A', '24.86094315', '67.14057339', NULL, NULL),
(2, 1, 'Stop B', '24.86094661', '67.14058195', NULL, NULL),
(3, 1, 'Stop C', '24.86095985', '67.14059199', NULL, NULL);

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
(7, '2014_10_12_000000_create_users_table', 1),
(8, '2014_10_12_100000_create_password_resets_table', 1),
(9, '2019_08_19_000000_create_failed_jobs_table', 1),
(10, '2020_10_11_130203_create_points_table', 1),
(11, '2020_10_11_233659_create_point_routes_table', 1),
(12, '2020_10_12_001830_create_stops_table', 1),
(13, '2020_12_13_224903_create_attendances_table', 2),
(14, '2016_06_01_000001_create_oauth_auth_codes_table', 3),
(15, '2016_06_01_000002_create_oauth_access_tokens_table', 3),
(16, '2016_06_01_000003_create_oauth_refresh_tokens_table', 3),
(17, '2016_06_01_000004_create_oauth_clients_table', 3),
(18, '2016_06_01_000005_create_oauth_personal_access_clients_table', 3),
(19, '2020_12_13_233542_create_checkpoints_table', 4),
(20, '2020_12_19_135045_create_user_points_table', 5);

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
('064e5f35519302fcf9bd8bcc993880828a4e1d0349aedd675c8661c7759ef9c2a526a5573e2fae97', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 17:23:19', '2020-12-19 17:23:19', '2021-12-19 22:23:19'),
('0a4f33b4e359765045ea83fd87c83e57db17d8ddc44ad6161128d6044ec977f7c463c64bdd29e378', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 22:57:02', '2020-12-19 22:57:02', '2021-12-20 03:57:02'),
('0ddcd59cabe5a10c8061a249e9c845100b507dbcc17d29dc89950f3aa3090daf038bb12c38c917ce', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-13 20:39:04', '2020-12-13 20:39:04', '2021-12-14 01:39:04'),
('186e22e20ba1c28bbe93321a78fefd455b8fbbb52677552d623df825fd0c11f6e96295969f22eaa3', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-13 20:28:45', '2020-12-13 20:28:45', '2021-12-14 01:28:45'),
('19a6806e573974d4f1997d1164b805713a585157dac1b57a5df4da839d29a3e7ac2e06832a394939', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 18:06:43', '2020-12-19 18:06:43', '2021-12-19 23:06:43'),
('1ae0a936a49a2b1cb5cc10ce43c63a75bee459a6a068865622d027e3627b865b56dab5e0aee1c532', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-13 18:47:38', '2020-12-13 18:47:38', '2021-12-13 23:47:38'),
('1b81597c3a955d3d262d03b7f72e8199cd983e7be60dcdbfa511f59526c15c715d0e17e7a87c7e94', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 10:46:36', '2020-12-19 10:46:36', '2021-12-19 15:46:36'),
('1cb6db760a83eccc6321f371d510e5e9bdc6b6ab5fbfb1344395624a40dda51812d52ce6f113193b', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 21:47:27', '2020-12-19 21:47:27', '2021-12-20 02:47:27'),
('2109e6821c39f5813e64da0353f22a5cf3465e544419d87d5fd0266e0b9ff9b6cae60f6c14a87e65', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:50', '2020-12-19 13:19:50', '2021-12-19 18:19:50'),
('272b99e4d1408b83f5e351cbccb8804882e09c51f0a1e12ce3b979f2e66805e2becb08f3cf303c07', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:22:29', '2020-12-19 11:22:29', '2021-12-19 16:22:29'),
('2a6f6d093211599866f94bf6a17d1747937d228f648326f86c820b45473c900a43184a7626ebd4c0', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:20:45', '2020-12-19 13:20:45', '2021-12-19 18:20:45'),
('33681c6fc289abc96b63e6f766fc53b3e6b7ac5ea7ecaa2ed120fd5b7f4c5df383febd815deff7bf', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-13 18:52:56', '2020-12-13 18:52:56', '2021-12-13 23:52:56'),
('38c1302eafaf91d26bfb919162622fa30e2d63a7fff64afd9e9402e11e41b24958d485cad9f9cb9f', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:02:15', '2020-12-19 13:02:15', '2021-12-19 18:02:15'),
('3f59e663d942e7dd0b915aeed6a7e652a963323817fb6d16e707adb88593f30e8f47f0470e76271a', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:20:35', '2020-12-19 11:20:35', '2021-12-19 16:20:35'),
('4130f4a99d4c266e4c4902c6768256f8fde7fc347f3c4e3800813e2841a62d61c049f7184d6440dd', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:28:20', '2020-12-19 13:28:20', '2021-12-19 18:28:20'),
('43fbf9e369bdadd51b9373a09182af74abd3245a45da250311c6b00418e2a56d087b816f4cf6da3e', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 21:15:16', '2020-12-19 21:15:16', '2021-12-20 02:15:16'),
('46f151d56029dc4fb56843c520705d964499799b8a940bd60fb46396b2d83808dca8b550b586537c', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:22:14', '2020-12-19 11:22:14', '2021-12-19 16:22:14'),
('474fb23c7d008464033d3f63ea2d42f64ea7ae9e0f1c28674b81876824f5049d19798b6feaa550a8', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:11:43', '2020-12-19 11:11:43', '2021-12-19 16:11:43'),
('4882f69ed4dd9d20f84f546302c75004141178d9602ed3e3b93ae7132cbc8612bf19eb8afcdd6f51', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:51', '2020-12-19 13:19:51', '2021-12-19 18:19:51'),
('488e57a1eea75d999cd2d8a132d6c56543e532ab04c5629e1c4a90ae412f751d5d14add3b77d8ffc', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:37:36', '2020-12-19 11:37:36', '2021-12-19 16:37:36'),
('4ae518669c21867ae723d0c5465e1c14fd9e438fa06a192f59c2227aa944d0271ee77618025ffc71', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 20:18:01', '2020-12-19 20:18:01', '2021-12-20 01:18:01'),
('4c9f92c746d2d484ce012d1948564a23758460dc38f5315208d11957cae051d0cdefad9dd712f48a', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:49', '2020-12-19 13:19:49', '2021-12-19 18:19:49'),
('4cafe6c71c8310199eb7b97c3a9a003eb94ec81c27ad0d101b84ec33a9f460f8d714a6980eb97fe6', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:50', '2020-12-19 13:19:50', '2021-12-19 18:19:50'),
('519bfbd12a8ebae4e248d45d0123b1da437edc0f1c9ca4a708d0be5ea73dd37b7992ec129888b426', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:10:48', '2020-12-19 13:10:48', '2021-12-19 18:10:48'),
('5835e3020f952fe5866d22078d89f5af6e83099102e4f3b7aed32023e7ad46a35b57f2eb217d1529', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:52', '2020-12-19 13:19:52', '2021-12-19 18:19:52'),
('5a3f44b107fa82dba92cf4c408b55518f65732057922d652c3ea56425ad32d2fe85b402f631159c5', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 20:10:49', '2020-12-19 20:10:49', '2021-12-20 01:10:49'),
('5aefb235c4f93a6e5d31fb2bbd9af337be203c7628ed71d47d11005a923e17da2308a6b850d852ed', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-13 18:49:57', '2020-12-13 18:49:57', '2021-12-13 23:49:57'),
('5c7142e3418e33342312af86dcd968c9993e1c7221bf1998ef4ab06aa9d5b857d212a450f6444df0', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:18:59', '2020-12-19 11:18:59', '2021-12-19 16:18:59'),
('60c446904148e03630b2783b1d2636b65fdbba56e0d4f62f79f0887efd1a0511eb65cf9435a488f1', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:13:09', '2020-12-19 11:13:09', '2021-12-19 16:13:09'),
('69201212ad131a1ce496c021722eeecef71f7d2fe7340070132cd36ac18d71ce1b784530b8be207b', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 22:49:49', '2020-12-19 22:49:49', '2021-12-20 03:49:49'),
('6b360ebbd114e02b3d9b3d2d906f0f6583345205feea4ba664c626d0b5f1c9dad204f0cc532fc33b', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:21:32', '2020-12-19 11:21:32', '2021-12-19 16:21:32'),
('70c6110573bfa7ceda5d843ca3ad0093e5ea42cfdf5607c42d0af921933b96144545e1043ca11d33', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:49', '2020-12-19 13:19:49', '2021-12-19 18:19:49'),
('785c35e1d8c84ddb24fed3e14e33b603787e22e31f2f49715c83c50238cc99a55a76b1ae443288f2', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:32:42', '2020-12-19 11:32:42', '2021-12-19 16:32:42'),
('7f7521e1056fce30f63e040ffbf042b9157ce87044d8f0d8fe81860bf64df8fe2a8fc54fcc6dfe10', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 20:16:35', '2020-12-19 20:16:35', '2021-12-20 01:16:35'),
('83bc24c88a98aae0e1e06d0ab70cf9a1c1abbf291e0504cd0943dbf78068c7fa53b63c3dfac38d79', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:51', '2020-12-19 13:19:51', '2021-12-19 18:19:51'),
('888d150c1c04764ec52d0855757ff6aed33c0b42857612f9e2623b1bccac01082332d37d510f9fd9', 5, 4, 'Personal Access Token', '[]', 0, '2020-12-19 22:56:11', '2020-12-19 22:56:11', '2021-12-20 03:56:11'),
('8f0d3142224a59a7142b0ec0c507d4ec07ec8802361c1677863d8932ea71af7a487bc03d2e33aea1', 5, 4, 'Personal Access Token', '[]', 0, '2020-12-19 22:57:55', '2020-12-19 22:57:55', '2021-12-20 03:57:55'),
('942ad6afa6fe2376aa5895152573046d6c288738d7c2608451b444f564810dd465276e6af17a3c3e', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 20:58:34', '2020-12-19 20:58:34', '2021-12-20 01:58:34'),
('9bd6efe643eadc05326882154b99c39fb0beedc139cff07cc4b285b3df58c7951f62462d6dd07310', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:16:17', '2020-12-19 11:16:17', '2021-12-19 16:16:17'),
('9e65195941aa9f4bfc1a4310dc7e0e8665cd37ffdb0b0388ddf3607c1f00d52edd11f8a0bddfbfb0', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:48', '2020-12-19 13:19:48', '2021-12-19 18:19:48'),
('a36fc323d9d74cbc2eb9c5cb24b5cc996b6b8761f5cb334865020a9f1842492257954a00e0fb3759', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:51', '2020-12-19 13:19:51', '2021-12-19 18:19:51'),
('a94cd32ea62ba6ff93891d516a34f958b5e05b5ce001a47ff1fe8384712b0eff7fe739c5ab7af38f', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:48', '2020-12-19 13:19:48', '2021-12-19 18:19:48'),
('a9990bec147a85f2958d997a5409c75ecd83cd45e94c013d30b5e16098dcb04a504987e2bf121d1a', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:48', '2020-12-19 13:19:48', '2021-12-19 18:19:48'),
('aa61c9df04d12069106e25afd5cacb08ad03b6bfab80440b9b858aa40f93c7d7927c985b140c0e56', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:36:36', '2020-12-19 13:36:36', '2021-12-19 18:36:36'),
('aa7b746739e58e607910a63cfa6d4c0b3d19e85a63190e660c22ab1e783cb348cb25de6574fcf3f3', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 22:48:39', '2020-12-19 22:48:39', '2021-12-20 03:48:39'),
('ab58edf4153c85aa15830a71035843f3bfe6f76a83939b27d79b05ad674e87757544c6115f23e0a9', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-13 20:29:54', '2020-12-13 20:29:54', '2021-12-14 01:29:54'),
('ac42833275e5765acc6528688fdbd4d1d6ccad36db8b4d57e79ea62b02929aeec069df2fb588bfeb', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 20:59:33', '2020-12-19 20:59:33', '2021-12-20 01:59:33'),
('ae401c4beafbfbef940e1bca4a9e0fc62be7faece63e7a0398b424da8e1459627c84aa35701a37e1', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:47:27', '2020-12-19 13:47:27', '2021-12-19 18:47:27'),
('b3d3d252df9a08291ba29eed30865d4e5d7122813485032760a96c5832024bacde633f17e8af2bdb', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 20:11:09', '2020-12-19 20:11:09', '2021-12-20 01:11:09'),
('b73e8e5f765a815f89d321218b008249da92c270c46541d96e464d521382f842ab885194b4e4c604', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 12:01:50', '2020-12-19 12:01:50', '2021-12-19 17:01:50'),
('ba36340a90f1de6ff9ef00bb2dd1093d5b4f2f3453a8a29fb0c1cfd2214d2b278c1cfded8237ba64', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:52', '2020-12-19 13:19:52', '2021-12-19 18:19:52'),
('bd8767680c9256fb743a75075a18c6dfd1efbb1488fd1a21f1f8b4e1855565ac60fb5fd2c4d5071e', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:56:40', '2020-12-19 11:56:40', '2021-12-19 16:56:40'),
('c29004a905a384fa302f7009e0b9719f945b793c0c4f314e91db35904c72b237464b3fd57b1a24da', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-13 18:51:52', '2020-12-13 18:51:52', '2021-12-13 23:51:52'),
('c32030c746dc33c873207646130719e742a5c7aa6e87b774b722a1db9ad107e0b282b068e59e739b', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-13 18:52:35', '2020-12-13 18:52:35', '2021-12-13 23:52:35'),
('c58cdb03e4c240c131002090671599579e90ff362eed515663c2a041ef84b282e5e63fdbf209c299', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:12:05', '2020-12-19 11:12:05', '2021-12-19 16:12:05'),
('c6bf8fff02065b0266780924733ad34327b2eff7a90ad95195da39ccc0884f33666bde00afb09b3f', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:16:22', '2020-12-19 11:16:22', '2021-12-19 16:16:22'),
('c7c302aec15471bf1e6cadc55fe01e748316d62e59b82b016ec22acec6ca66db42f67886c5478d10', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:51', '2020-12-19 13:19:51', '2021-12-19 18:19:51'),
('cb1ef8d8834c3793ac3e804f7e073e13d0713dcc3da37f852b72c616d5dbbcab1c822f5347d14adc', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 12:10:06', '2020-12-19 12:10:06', '2021-12-19 17:10:06'),
('cc3b8734dc1ebc8e9938ef7ddc0bafa573b0d8af7bfa0f4b452bfd8fb75ac7219e4c28b26c783bd6', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 23:00:58', '2020-12-19 23:00:58', '2021-12-20 04:00:58'),
('ccb4046f098d9d5c6cb8a30dbeb176ed52095a45607ffe27a206dd46a14fc7d717492d30719b0073', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:17:22', '2020-12-19 11:17:22', '2021-12-19 16:17:22'),
('cda5f55ddb87dc79023a34af5c2e41a2db973549ba7b00a7ba90f4afd8588c72736538abf5eb8cd3', 3, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:21:41', '2020-12-19 11:21:41', '2021-12-19 16:21:41'),
('d5da1cee6b9624b5c2eb5756741981ef78f8f909b02afb04cfffd5281a9d9ae75cbf98ac765228a7', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:17:13', '2020-12-19 11:17:13', '2021-12-19 16:17:13'),
('db2fa8e75edb09824fc8c6f58b46c340562784227f993e45e7973b66c10878d3890e6830aa229e97', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-13 18:48:16', '2020-12-13 18:48:16', '2021-12-13 23:48:16'),
('dd1148d8a385972db5856874e3ec2e2c39d07bcc582fd062f9676ae28aecf988d371aa5756e616f3', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:50', '2020-12-19 13:19:50', '2021-12-19 18:19:50'),
('dd8dcb9e7afbc1f1131ed1856f888bf3759b3dfd5e8f9f0513e39c934719d5676057bdd6792d144b', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 14:40:11', '2020-12-19 14:40:11', '2021-12-19 19:40:11'),
('e8b2bcea8a9cbd312d590be1776a4a7a722ac6090cdd9eba96ad4cb8bb0ea62f468e7f7d0b7aa064', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:16:58', '2020-12-19 11:16:58', '2021-12-19 16:16:58'),
('ec612a9f25472f5d7c5f07d879c00ac7570ea2fec52950330b32c03a184573c13d1c51f303785925', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:34:11', '2020-12-19 13:34:11', '2021-12-19 18:34:11'),
('ed1b68931d4e829c327ec7722f2ab832d95c1799006cf20d3715977653b858305af8457ff2eaedef', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:39:10', '2020-12-19 11:39:10', '2021-12-19 16:39:10'),
('f22738cf4e7e432fb9b7a594e174546bd31a3b69c4c211faee8e97f157c997b83fcfee297a685985', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 12:13:00', '2020-12-19 12:13:00', '2021-12-19 17:13:00'),
('f55fd3fbe1cff3f2d79a31dbbcea6be645288e0f513082a8108725d069663f56b27c76a6911e11f1', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 12:07:55', '2020-12-19 12:07:55', '2021-12-19 17:07:55'),
('f95c058ca8dcd9e50f49816cd3193650962cbccbeccc113a5c64e804f8b7bd1eb8312630f765b128', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 12:59:53', '2020-12-19 12:59:53', '2021-12-19 17:59:53'),
('f9910e424180ec8015dcd0cfcbd643d064c9506abe71d111630b36d234f40c6c5dfd651662428494', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:49', '2020-12-19 13:19:49', '2021-12-19 18:19:49'),
('f9a811dabbed111faf6075ead11bbc1445b4b4505ba1a4692e383d2882ed227951166c3026112573', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 13:19:48', '2020-12-19 13:19:48', '2021-12-19 18:19:48'),
('fd0dfe79ddd530435fcd79d1511aa489931cf7aee98feb360dcbe7d79be005ed4f2039860a7815d4', 2, 4, 'Personal Access Token', '[]', 0, '2020-12-19 11:19:21', '2020-12-19 11:19:21', '2021-12-19 16:19:21');

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
(1, NULL, 'Laravel Personal Access Client', 'LyvHqXWSped3hjMoP6ICr4A5eRyUph2wVRfHSa0K', NULL, 'http://localhost', 1, 0, 0, '2020-12-13 17:52:46', '2020-12-13 17:52:46'),
(2, NULL, 'Laravel Password Grant Client', 'AbT2IPsR1ADsGrIF2XvxSoKpqdcByeSj2M3cAFZb', 'users', 'http://localhost', 0, 1, 0, '2020-12-13 17:52:46', '2020-12-13 17:52:46'),
(3, 1, 'admin', '2jxwe4wbJN6JJSj1a7CS4bkdqU4aQUaESCUnCVNC', NULL, 'http://localhost/auth/callback', 0, 0, 0, '2020-12-13 17:55:28', '2020-12-13 17:55:28'),
(4, NULL, '1', 'DQBuTYw3zlTnn149h5lYuym2DnM8ooJBjGuGUFbS', NULL, 'http://localhost', 1, 0, 0, '2020-12-13 17:55:53', '2020-12-13 17:55:53');

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
(1, 1, '2020-12-13 17:52:46', '2020-12-13 17:52:46'),
(2, 4, '2020-12-13 17:55:53', '2020-12-13 17:55:53');

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
-- Table structure for table `points`
--

CREATE TABLE `points` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `driver_id` int(11) NOT NULL,
  `vehicle_reg_no` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `live_lat` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '24.869555',
  `live_lng` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '67.135869',
  `route_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `points`
--

INSERT INTO `points` (`id`, `driver_id`, `vehicle_reg_no`, `live_lat`, `live_lng`, `route_id`, `created_at`, `updated_at`) VALUES
(1, 2, 'ABC-123', '24.86099722', '67.1405182', 1, '2020-12-20 04:12:54', '2020-12-19 23:12:54'),
(2, 5, 'KHI-123', '24.86101518', '67.14053338', 2, '2020-12-20 03:57:57', '2020-12-19 22:57:57');

-- --------------------------------------------------------

--
-- Table structure for table `point_routes`
--

CREATE TABLE `point_routes` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `alias` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `url` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `point_routes`
--

INSERT INTO `point_routes` (`id`, `alias`, `url`, `created_at`, `updated_at`) VALUES
(1, 'Johar Route', 'https://www.google.com/maps/dir/Johar+Mor+Road+Service+Lane,+Gulistan-e-Johar,+Karachi/Shah+Faisal+Town,+Karachi+City,+Sindh,+Pakistan/Karachi+Institute+of+Economics+and+Technology,+PAF+Airmen+Academy,+PAF+Colony+Paf+Colony+Korangi+Creek,+Karachi,+Karachi+City,+Sindh+75190,+Pakistan/@24.8528128,67.0707118,12z/', '2020-10-12 02:06:45', '2020-10-12 02:06:45'),
(2, 'Another Route', 'https://www.google.com/maps/dir/Johar+Mor+Road+Service+Lane,+Gulistan-e-Johar,+Karachi/Shah+Faisal+Town,+Karachi+City,+Sindh,+Pakistan/Karachi+Institute+of+Economics+and+Technology,+PAF+Airmen+Academy,+PAF+Colony+Paf+Colony+Korangi+Creek,+Karachi,+Karachi+City,+Sindh+75190,+Pakistan/@24.8528128,67.0707118,12z/', '2020-12-19 14:53:41', '2020-12-19 14:53:41');

-- --------------------------------------------------------

--
-- Table structure for table `stops`
--

CREATE TABLE `stops` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `stops`
--

INSERT INTO `stops` (`id`, `description`, `created_at`, `updated_at`) VALUES
(1, 'Johar+Mor+Road+Service+Lane,+Gulistan-e-Johar,+Karachi', '2020-10-12 02:06:45', '2020-10-12 02:06:45'),
(2, 'Shah+Faisal+Town,+Karachi+City,+Sindh,+Pakistan', '2020-10-12 02:06:45', '2020-10-12 02:06:45'),
(3, 'Karachi+Institute+of+Economics+and+Technology,+PAF+Airmen+Academy,+PAF+Colony+Paf+Colony+Korangi+Creek,+Karachi,+Karachi+City,+Sindh+75190,+Pakistan', '2020-10-12 02:06:45', '2020-10-12 02:06:45'),
(4, '@24.8528128,67.0707118,12z', '2020-10-12 02:06:45', '2020-10-12 02:06:45');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `type_id` int(11) NOT NULL DEFAULT 2,
  `contact` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fcm` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `user_id`, `type_id`, `contact`, `fcm`, `email`, `email_verified_at`, `password`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'Admin', NULL, 2, NULL, NULL, 'admin@gmail.com', NULL, '$2y$10$3zTE98QNK4DOLaLXhapW0urvhxoNjW8jdVN.TZAkrvmOUzjHCa6y.', NULL, '2020-10-12 02:04:00', '2020-10-12 02:04:00'),
(2, 'Mazhar', 1002, 3, '03121234567', 'cZyN28naTSSUX7KMH_hJaW:APA91bG-PniIHbcFUjJRgAY8eFFVhg5lIDuBCvE9YTpS_9GTRmAvJfSucdARxfCs85QWNY1nSHB0gmlopKOvcjjaI0kvgOsfr2RF7eD8gcvD4qM2Nq1_F80-U1EDKW8hYt-QU7urfjyh', '1002@pafkiet.edu.pk', NULL, '$2y$10$3zTE98QNK4DOLaLXhapW0urvhxoNjW8jdVN.TZAkrvmOUzjHCa6y.', NULL, '2020-10-12 02:05:43', '2020-12-19 20:59:33'),
(3, 'Ehtisham', 7588, 1, '03022034139', 'fEVKba00RyKh50Lr-RzNXo:APA91bFnwKQNAQ9FBzjKVZlr5Px49vEhBblfvC9yJX4FMF-Tf2M0yg9YH6LLAyWuj-vty_tbDyEBhcY24k-ZUoih_DYgPBOpEV1fMw-jsZVVK1OQt3SkEFz4aTQdXL-wLDDFSmsyX_g-', '7588@pafkiet.edu.pk', NULL, '$2y$10$3zTE98QNK4DOLaLXhapW0urvhxoNjW8jdVN.TZAkrvmOUzjHCa6y.', NULL, '2020-12-13 17:48:04', '2020-12-19 22:48:39'),
(5, 'Khan', 1004, 3, '03022034139', 'fEVKba00RyKh50Lr-RzNXo:APA91bFnwKQNAQ9FBzjKVZlr5Px49vEhBblfvC9yJX4FMF-Tf2M0yg9YH6LLAyWuj-vty_tbDyEBhcY24k-ZUoih_DYgPBOpEV1fMw-jsZVVK1OQt3SkEFz4aTQdXL-wLDDFSmsyX_g-', '1004@pafkiet.edu.pk', NULL, '$2y$10$JV8FKrx2BmvZRCFNYXZ6l.q3YpHCJ/1tqg4jUFeXNFTfaOmWk1tFu', NULL, '2020-12-19 14:53:25', '2020-12-19 22:56:11');

-- --------------------------------------------------------

--
-- Table structure for table `user_points`
--

CREATE TABLE `user_points` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` int(11) NOT NULL,
  `point_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user_points`
--

INSERT INTO `user_points` (`id`, `user_id`, `point_id`, `created_at`, `updated_at`) VALUES
(1, 7588, 1, '2020-12-19 09:53:00', '2020-12-19 09:53:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `attendances`
--
ALTER TABLE `attendances`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `checkpoints`
--
ALTER TABLE `checkpoints`
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
-- Indexes for table `points`
--
ALTER TABLE `points`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `point_routes`
--
ALTER TABLE `point_routes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stops`
--
ALTER TABLE `stops`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- Indexes for table `user_points`
--
ALTER TABLE `user_points`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attendances`
--
ALTER TABLE `attendances`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `checkpoints`
--
ALTER TABLE `checkpoints`
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
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

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
-- AUTO_INCREMENT for table `points`
--
ALTER TABLE `points`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `point_routes`
--
ALTER TABLE `point_routes`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `stops`
--
ALTER TABLE `stops`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user_points`
--
ALTER TABLE `user_points`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
