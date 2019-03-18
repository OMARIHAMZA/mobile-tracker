-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 12, 2019 at 05:57 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lar_fetcher`
--

-- --------------------------------------------------------

--
-- Table structure for table `add_requests`
--

CREATE TABLE `add_requests` (
  `id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `added_id` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `contacts_uploads`
--

CREATE TABLE `contacts_uploads` (
  `id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `friendships`
--

CREATE TABLE `friendships` (
  `id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `added_id` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `friendships`
--

INSERT INTO `friendships` (`id`, `user_id`, `added_id`, `created_at`, `updated_at`) VALUES
(5, 1, 2, NULL, NULL),
(6, 2, 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2014_10_12_000000_create_users_table', 1),
(2, '2014_10_12_100000_create_password_resets_table', 1),
(3, '2019_03_12_104151_create_add_requests_table', 1),
(4, '2019_03_12_104311_create_friendships_table', 1),
(5, '2019_03_12_105301_create_contacs_uploads_table', 1);

-- --------------------------------------------------------

--
-- Table structure for table `password_resets`
--

CREATE TABLE `password_resets` (
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(10) UNSIGNED NOT NULL,
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `brand` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone_model` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lat` decimal(10,8) DEFAULT NULL,
  `long` decimal(11,8) DEFAULT NULL,
  `location_last_updated` timestamp NULL DEFAULT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `phone`, `password`, `brand`, `phone_model`, `lat`, `long`, `location_last_updated`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'samsaydali', '+963951419123', '$2y$10$s5ehRwPSJZoyPu48ZFX6Zu7Wz54BEuwl52W6kK7ZZwUwaOTKY6Avi', 'Apple', 'iPhone 5s', '33.50282240', '36.28974080', '2019-03-12 14:44:04', NULL, '2019-03-12 09:14:24', '2019-03-12 14:44:04'),
(2, 'hamza', '+963789412', '$2y$10$UexLZoXQ/rOki4MtBPK1ie58O.2/KuGjcbLjXJYp5XHXpb2aShp8O', 'Samsung', 'Galaxy S9', NULL, NULL, NULL, NULL, '2019-03-12 10:20:22', '2019-03-12 10:20:22'),
(3, 'mohammad', '+963243789412', '$2y$10$UexLZoXQ/rOki4MtBPK1ie58O.2/KuGjcbLjXJYp5XHXpb2aShp8O', 'Samsung', 'Galaxy S9', NULL, NULL, NULL, NULL, '2019-03-12 10:22:51', '2019-03-12 10:22:51'),
(4, 'john', '+963979658456', 'secret', 'Hawawi', 'Mate 10 Pro', NULL, NULL, NULL, NULL, '2019-03-12 10:57:53', '2019-03-12 10:57:53'),
(5, 'jane', '+963977658456', 'secret', 'Hawawi', 'Mate 10 Pro', NULL, NULL, NULL, NULL, '2019-03-12 13:23:17', '2019-03-12 13:23:17'),
(6, 'mike', '+963977653333', '$2y$10$UexLZoXQ/rOki4MtBPK1ie58O.2/KuGjcbLjXJYp5XHXpb2aShp8O', 'Sony', 'Xperia Z', NULL, NULL, NULL, NULL, '2019-03-12 13:25:22', '2019-03-12 13:25:22');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `add_requests`
--
ALTER TABLE `add_requests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `add_requests_user_id_foreign` (`user_id`),
  ADD KEY `add_requests_added_id_foreign` (`added_id`);

--
-- Indexes for table `contacts_uploads`
--
ALTER TABLE `contacts_uploads`
  ADD PRIMARY KEY (`id`),
  ADD KEY `contacts_uploads_user_id_foreign` (`user_id`);

--
-- Indexes for table `friendships`
--
ALTER TABLE `friendships`
  ADD PRIMARY KEY (`id`),
  ADD KEY `friendships_user_id_foreign` (`user_id`),
  ADD KEY `friendships_added_id_foreign` (`added_id`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

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
  ADD UNIQUE KEY `users_phone_unique` (`phone`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `add_requests`
--
ALTER TABLE `add_requests`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `contacts_uploads`
--
ALTER TABLE `contacts_uploads`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `friendships`
--
ALTER TABLE `friendships`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `add_requests`
--
ALTER TABLE `add_requests`
  ADD CONSTRAINT `add_requests_added_id_foreign` FOREIGN KEY (`added_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `add_requests_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `contacts_uploads`
--
ALTER TABLE `contacts_uploads`
  ADD CONSTRAINT `contacts_uploads_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `friendships`
--
ALTER TABLE `friendships`
  ADD CONSTRAINT `friendships_added_id_foreign` FOREIGN KEY (`added_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `friendships_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
