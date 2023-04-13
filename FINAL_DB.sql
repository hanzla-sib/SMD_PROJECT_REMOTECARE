-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 13, 2023 at 02:35 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `remote_care`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment_history`
--

CREATE TABLE `appointment_history` (
  `id` int(11) NOT NULL,
  `d_name` varchar(128) DEFAULT NULL,
  `d_email` varchar(128) DEFAULT NULL,
  `Time1` varchar(100) DEFAULT NULL,
  `Date1` varchar(100) DEFAULT NULL,
  `p_name` varchar(128) DEFAULT NULL,
  `p_email` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointment_history`
--

INSERT INTO `appointment_history` (`id`, `d_name`, `d_email`, `Time1`, `Date1`, `p_name`, `p_email`) VALUES
(1, 'Muhammad Taha', 'taha@gmail.com', '3:30', '22-2-2023', 'Hanzla Sibghat', 'hanzla@gmail.com'),
(2, 'Muhammad Ahmad', 'ahmad@gmail.com', '3:32', '28-2-2023', 'Umaid khakwani', 'umaid@gmail.com'),
(3, 'Muhammad Ahmad', 'ahmad@gmail.com', '1:32', '17-2-2023', 'Hanzla Sibghat', 'hanzla@gmail.com'),
(4, 'Muhammad Taha', 'taha@gmail.com', '8:30', '20-2-2023', 'Umaid khakwani', 'umaid@gmail.com'),
(5, 'Rana Ali', 'ali@gmail.com', '1:34', '3-2-2023', 'Umaid khakwani', 'umaid@gmail.com'),
(6, 'Rana Ali', 'ali@gmail.com', '1:34', '15-3-2023', 'Raja Fozan', 'fozan@gmail.com'),
(7, 'Saad Jilani', 'saad@gmail.com', '16:30', '18-2-2023', 'Umaid khakwani', 'umaid@gmail.com'),
(8, 'Saad Jilani', 'saad@gmail.com', '11:30', '19-2-2023', 'Hanzla Sibghat', 'hanzla@gmail.com'),
(9, 'Rana Ali', 'ali@gmail.com', '1:34', '17-2-2023', 'Muhammad Bilal', 'bilal@gmail.com'),
(10, 'Muhammad Ahmad', 'ahmad@gmail.com', '1:32', '8-2-2023', 'Raja Fozan', 'fozan@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `consumed_calories`
--

CREATE TABLE `consumed_calories` (
  `id` int(11) NOT NULL,
  `p_email` varchar(128) DEFAULT NULL,
  `date_log` date DEFAULT NULL,
  `Calories` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `consumed_calories`
--

INSERT INTO `consumed_calories` (`id`, `p_email`, `date_log`, `Calories`) VALUES
(1, 'umaid@gmail.com', '2022-12-20', 1000),
(2, 'umaid@gmail.com', '2022-12-21', 3000),
(3, 'umaid@gmail.com', '2022-12-22', 1500),
(4, 'umaid@gmail.com', '2022-12-23', 2000),
(5, 'umaid@gmail.com', '2022-12-24', 500),
(6, 'umaid@gmail.com', '2022-12-25', 900),
(7, 'umaid@gmail.com', '2022-12-26', 1800),
(8, 'umaid@gmail.com', '2022-12-27', 2022),
(9, 'umaid@gmail.com', '2022-12-28', 2200),
(10, 'umaid@gmail.com', '2022-12-29', 1200),
(11, 'umaid@gmail.com', '2022-12-30', 1700),
(12, 'umaid@gmail.com', '2022-12-31', 1900),
(13, 'umaid@gmail.com', '2023-03-01', 1000),
(14, 'umaid@gmail.com', '2023-03-02', 550),
(15, 'umaid@gmail.com', '2023-03-03', 900),
(16, 'umaid@gmail.com', '2023-03-04', 1100),
(17, 'umaid@gmail.com', '2023-03-05', 600),
(18, 'umaid@gmail.com', '2023-03-06', 1100),
(19, 'umaid@gmail.com', '2023-03-07', 1300),
(20, 'umaid@gmail.com', '2023-03-08', 1500),
(21, 'bilal@gmail.com', '2023-01-20', 1000),
(22, 'bilal@gmail.com', '2023-01-21', 3000),
(23, 'bilal@gmail.com', '2023-01-22', 1500),
(24, 'bilal@gmail.com', '2023-01-23', 2000),
(25, 'bilal@gmail.com', '2023-01-24', 500),
(26, 'bilal@gmail.com', '2023-01-25', 900),
(27, 'bilal@gmail.com', '2023-01-26', 1800),
(28, 'bilal@gmail.com', '2023-01-27', 2022),
(29, 'bilal@gmail.com', '2023-01-28', 2200),
(30, 'bilal@gmail.com', '2023-01-29', 1200),
(31, 'bilal@gmail.com', '2023-01-30', 1700),
(32, 'bilal@gmail.com', '2023-01-31', 1900),
(33, 'bilal@gmail.com', '2023-02-01', 1000),
(34, 'bilal@gmail.com', '2023-02-02', 550),
(35, 'bilal@gmail.com', '2023-02-03', 900),
(36, 'bilal@gmail.com', '2023-02-04', 1100),
(37, 'bilal@gmail.com', '2023-02-05', 600),
(38, 'bilal@gmail.com', '2023-02-06', 1100),
(39, 'bilal@gmail.com', '2023-02-07', 1300),
(40, 'bilal@gmail.com', '2023-02-08', 1500),
(41, 'hanzla@gmail.com', '2023-02-20', 1000),
(42, 'hanzla@gmail.com', '2023-02-21', 3000),
(43, 'hanzla@gmail.com', '2023-02-22', 1500),
(44, 'hanzla@gmail.com', '2023-02-23', 2000),
(45, 'hanzla@gmail.com', '2023-02-24', 500),
(46, 'hanzla@gmail.com', '2023-02-25', 900),
(47, 'hanzla@gmail.com', '2023-02-26', 1800),
(48, 'hanzla@gmail.com', '2023-02-27', 2022),
(49, 'hanzla@gmail.com', '2023-02-28', 2200),
(53, 'hanzla@gmail.com', '2023-03-01', 1000),
(54, 'hanzla@gmail.com', '2023-03-02', 550),
(55, 'hanzla@gmail.com', '2023-03-03', 900),
(56, 'hanzla@gmail.com', '2023-03-04', 1100),
(57, 'hanzla@gmail.com', '2023-03-05', 1000),
(58, 'hanzla@gmail.com', '2023-03-06', 550),
(59, 'hanzla@gmail.com', '2023-03-07', 900),
(60, 'hanzla@gmail.com', '2023-03-08', 1100),
(61, 'hanzla@gmail.com', '2023-03-09', 263),
(62, 'hanzla@gmail.com', '2023-03-10', 263),
(63, 'hanzla@gmail.com', '2023-04-07', 1000),
(64, 'hanzla@gmail.com', '2023-04-08', 550),
(65, 'hanzla@gmail.com', '2023-04-09', 900),
(66, 'hanzla@gmail.com', '2023-04-10', 1100),
(67, 'hanzla@gmail.com', '2023-04-11', 1000),
(68, 'hanzla@gmail.com', '2023-04-12', 550),
(69, 'hanzla@gmail.com', '2023-04-13', 900);

-- --------------------------------------------------------

--
-- Table structure for table `daily_steps`
--

CREATE TABLE `daily_steps` (
  `id` int(11) NOT NULL,
  `Demail` varchar(128) DEFAULT NULL,
  `steps_daily` int(11) DEFAULT NULL,
  `date_log` date DEFAULT NULL,
  `motion` varchar(40) DEFAULT NULL,
  `Burnt_Calories` float DEFAULT NULL,
  `distance_covered` double DEFAULT NULL,
  `speed` double DEFAULT NULL,
  `heartbeat` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `daily_steps`
--

INSERT INTO `daily_steps` (`id`, `Demail`, `steps_daily`, `date_log`, `motion`, `Burnt_Calories`, `distance_covered`, `speed`, `heartbeat`) VALUES
(1, 'mirza@gmail.com', 0, NULL, 'Resting', NULL, NULL, NULL, NULL),
(2, 'bilal@gmail.com', 13, '2023-03-26', 'Resting', 0.52, NULL, NULL, NULL),
(3, 'umaid@gmail.com', 0, NULL, 'Resting', NULL, NULL, NULL, NULL),
(4, 'hanzla@gmail.com', 17, '2023-04-07', 'Resting', 0.68, NULL, NULL, 94),
(5, 'fozan@gmail.com', 0, NULL, 'Resting', NULL, NULL, NULL, NULL),
(6, 'waqas@gmail.com', 0, NULL, 'Resting', NULL, NULL, NULL, NULL),
(7, 'ahmad@gmail.com', 0, NULL, 'Resting', NULL, NULL, NULL, NULL),
(8, 'taha@gmail.com', 0, NULL, 'Resting', NULL, NULL, NULL, NULL),
(9, 'ali@gmail.com', 0, NULL, 'Resting', NULL, NULL, NULL, NULL),
(10, 'saad@gmail.com', 0, NULL, 'Resting', NULL, NULL, NULL, NULL),
(11, 'adeel@gmail.com', 0, NULL, 'Resting', NULL, NULL, NULL, NULL),
(12, 'saleem@gmail.com', 0, NULL, 'Resting', NULL, NULL, NULL, NULL),
(16, 'kashif@gmail.com', 0, NULL, 'Resting', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `doctor_appointment`
--

CREATE TABLE `doctor_appointment` (
  `d_name` varchar(128) DEFAULT NULL,
  `d_email` varchar(128) NOT NULL,
  `appoint_status` varchar(128) DEFAULT NULL,
  `Time1` varchar(100) DEFAULT NULL,
  `Date1` varchar(100) DEFAULT NULL,
  `p_name` varchar(128) DEFAULT NULL,
  `p_email` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctor_appointment`
--

INSERT INTO `doctor_appointment` (`d_name`, `d_email`, `appoint_status`, `Time1`, `Date1`, `p_name`, `p_email`) VALUES
('Adeel Afzal', 'adeel@gmail.com', 'Pending', NULL, NULL, 'Hanzla Sibghat', 'hanzla@gmail.com'),
('Rana Ali', 'ali@gmail.com', 'Pending', NULL, NULL, 'Muhammad Bilal', 'bilal@gmail.com'),
('Rana Ali', 'ali@gmail.com', 'Accepted', '23:42', '22-4-2023', 'Hanzla Sibghat', 'hanzla@gmail.com'),
('Rana Ali', 'ali@gmail.com', 'Accepted', '2:26', '8-3-2023', 'Waqas Ahmad ', 'waqas@gmail.com'),
('Saad Jilani', 'saad@gmail.com', 'Accepted', '18:35', '25-2-2023', 'Muhammad Bilal', 'bilal@gmail.com'),
('Saad Jilani', 'saad@gmail.com', 'Accepted', '18:30', '14-2-2023', 'Raja Fozan', 'fozan@gmail.com'),
('Saad Jilani', 'saad@gmail.com', 'Pending', NULL, NULL, 'Hanzla Sibghat', 'hanzla@gmail.com'),
('Saad Jilani', 'saad@gmail.com', 'Pending', NULL, NULL, 'Waqas Ahmad ', 'waqas@gmail.com'),
('Muhammad Taha', 'taha@gmail.com', 'Accepted', '12:30', '9-2-2023', 'Muhammad Bilal', 'bilal@gmail.com'),
('Muhammad Taha', 'taha@gmail.com', 'Accepted', '15:30', '17-2-2023', 'Raja Fozan', 'fozan@gmail.com'),
('Muhammad Taha', 'taha@gmail.com', 'Pending', NULL, NULL, 'Waqas Ahmad ', 'waqas@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `doctor_recommendation`
--

CREATE TABLE `doctor_recommendation` (
  `p_email` varchar(128) NOT NULL,
  `steps_recommended` int(11) DEFAULT NULL,
  `d_email` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctor_recommendation`
--

INSERT INTO `doctor_recommendation` (`p_email`, `steps_recommended`, `d_email`) VALUES
('hanzla@gmail.com', 400, 'ali@gmail.com'),
('hanzla@gmail.com', 235, 'ahmad@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `heartbeat`
--

CREATE TABLE `heartbeat` (
  `id` int(11) NOT NULL,
  `Demail` varchar(128) DEFAULT NULL,
  `HeartBeat_daily` int(11) DEFAULT NULL,
  `date_log` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `heartbeat`
--

INSERT INTO `heartbeat` (`id`, `Demail`, `HeartBeat_daily`, `date_log`) VALUES
(1, 'hanzla@gmail.com', 79, '2023-03-10'),
(2, 'hanzla@gmail.com', 79, '2023-03-10'),
(3, 'hanzla@gmail.com', 85, '2023-03-10'),
(4, 'hanzla@gmail.com', 90, '2023-04-11'),
(5, 'hanzla@gmail.com', 93, '2023-04-12'),
(6, 'hanzla@gmail.com', 94, '2023-04-12'),
(7, 'bilal@gmail.com', 79, '2023-04-10'),
(8, 'bilal@gmail.com', 79, '2023-04-10'),
(9, 'bilal@gmail.com', 85, '2023-04-10'),
(10, 'bilal@gmail.com', 90, '2023-04-11'),
(11, 'bilal@gmail.com', 93, '2023-04-12'),
(12, 'bilal@gmail.com', 94, '2023-04-13');

-- --------------------------------------------------------

--
-- Table structure for table `patient_appointment`
--

CREATE TABLE `patient_appointment` (
  `p_name` varchar(128) DEFAULT NULL,
  `p_email` varchar(128) NOT NULL,
  `appoint_status` varchar(128) DEFAULT NULL,
  `Time1` varchar(100) DEFAULT NULL,
  `Date1` varchar(100) DEFAULT NULL,
  `d_name` varchar(128) DEFAULT NULL,
  `d_email` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient_appointment`
--

INSERT INTO `patient_appointment` (`p_name`, `p_email`, `appoint_status`, `Time1`, `Date1`, `d_name`, `d_email`) VALUES
('Muhammad Bilal', 'bilal@gmail.com', 'Pending', NULL, NULL, 'Rana Ali', 'ali@gmail.com'),
('Muhammad Bilal', 'bilal@gmail.com', 'Accepted', '18:35', '25-2-2023', 'Saad Jilani', 'saad@gmail.com'),
('Muhammad Bilal', 'bilal@gmail.com', 'Accepted', '12:30', '9-2-2023', 'Muhammad Taha', 'taha@gmail.com'),
('Raja Fozan', 'fozan@gmail.com', 'Accepted', '18:30', '14-2-2023', 'Saad Jilani', 'saad@gmail.com'),
('Raja Fozan', 'fozan@gmail.com', 'Accepted', '15:30', '17-2-2023', 'Muhammad Taha', 'taha@gmail.com'),
('Hanzla Sibghat', 'hanzla@gmail.com', 'Pending', NULL, NULL, 'Adeel Afzal', 'adeel@gmail.com'),
('Hanzla Sibghat', 'hanzla@gmail.com', 'Accepted', '23:42', '22-4-2023', 'Rana Ali', 'ali@gmail.com'),
('Hanzla Sibghat', 'hanzla@gmail.com', 'Pending', NULL, NULL, 'Saad Jilani', 'saad@gmail.com'),
('Waqas Ahmad ', 'waqas@gmail.com', 'Accepted', '2:26', '8-3-2023', 'Rana Ali', 'ali@gmail.com'),
('Waqas Ahmad ', 'waqas@gmail.com', 'Pending', NULL, NULL, 'Saad Jilani', 'saad@gmail.com'),
('Waqas Ahmad ', 'waqas@gmail.com', 'Pending', NULL, NULL, 'Muhammad Taha', 'taha@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `test_record`
--

CREATE TABLE `test_record` (
  `id` int(11) NOT NULL,
  `Temail` varchar(128) DEFAULT NULL,
  `imageurl` text DEFAULT NULL,
  `details` varchar(1000) DEFAULT NULL,
  `image_link` varchar(3000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `test_record`
--

INSERT INTO `test_record` (`id`, `Temail`, `imageurl`, `details`, `image_link`) VALUES
(1, 'umaid@gmail.com', '07-02-2023-1675800940-65671.jpg', 'Allergy Report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675800938956.jpg?alt=media&token=53055904-3230-47d3-9cf0-8da2b0ad6b0a'),
(2, 'umaid@gmail.com', '07-02-2023-1675800969-78163.jpg', 'Blood Pressure report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675800968282.jpg?alt=media&token=f3b8ef49-d0cd-4db1-a4e9-de12a43de336'),
(3, 'umaid@gmail.com', '07-02-2023-1675801011-69734.jpg', 'MRI report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801010086.jpg?alt=media&token=29a34518-e2a2-4046-8a9c-5cfa53d7e01e'),
(4, 'umaid@gmail.com', '07-02-2023-1675801035-75332.jpg', 'Tumor report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801034387.jpg?alt=media&token=70035024-a8c0-4dbe-a884-d4acab682ede'),
(6, 'hanzla@gmail.com', '07-02-2023-1675801171-47970.jpg', 'BP REPORT', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801170296.jpg?alt=media&token=f978638c-5359-4970-a03e-6a94ece2f36d'),
(7, 'hanzla@gmail.com', '07-02-2023-1675801189-56615.jpg', 'alergic report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801187922.jpg?alt=media&token=cf361120-80d0-4863-a580-4c4a6ec99bce'),
(8, 'waqas@gmail.com', '07-02-2023-1675801213-30453.jpg', 'Allergy report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801212641.jpg?alt=media&token=77134fb8-5ea6-4dfc-a104-77f2e4ec8672'),
(9, 'hanzla@gmail.com', '07-02-2023-1675801228-25523.jpg', 'Hematology Report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801226584.jpg?alt=media&token=d73017e1-9ced-4224-ac63-18ee79268ea0'),
(10, 'waqas@gmail.com', '07-02-2023-1675801233-34509.jpg', 'Blood pressure report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801232926.jpg?alt=media&token=82174e9f-95ad-422b-ae0b-3d168023140a'),
(11, 'hanzla@gmail.com', '07-02-2023-1675801248-17739.jpg', 'PCR Report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801246834.jpg?alt=media&token=e560be71-224d-4f11-aecc-53f2f330ef56'),
(12, 'hanzla@gmail.com', '07-02-2023-1675801267-37271.jpg', 'Skin Report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801267248.jpg?alt=media&token=0fdf5145-6900-4d55-9a14-7fa4d1d88a25'),
(13, 'waqas@gmail.com', '07-02-2023-1675801296-20498.jpg', 'MRI report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801295370.jpg?alt=media&token=ad6bb9e0-08e9-431b-9559-d77823d646bd'),
(14, 'waqas@gmail.com', '07-02-2023-1675801358-61523.jpg', 'Tumor report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801357362.jpg?alt=media&token=827ea815-41ab-4d63-b31c-3d61c3319946'),
(15, 'bilal@gmail.com', '07-02-2023-1675801542-88959.jpg', 'sugar report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801540861.jpg?alt=media&token=cbd958fa-baf1-4636-80d3-5629744d72b0'),
(16, 'bilal@gmail.com', '07-02-2023-1675801558-35612.jpg', 'BP report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801557708.jpg?alt=media&token=577e5e7b-9990-4643-bdb2-a818df72017d'),
(17, 'bilal@gmail.com', '07-02-2023-1675801584-70741.jpg', 'skin test report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801584030.jpg?alt=media&token=fb513d92-bb42-436d-acb6-e5f98440c9ea'),
(18, 'fozan@gmail.com', '07-02-2023-1675801687-90846.jpg', 'Allergy report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801685997.jpg?alt=media&token=bd91f20d-e0ec-4bcc-a35f-7b92a7f69055'),
(19, 'fozan@gmail.com', '07-02-2023-1675801699-25959.jpg', 'Blood report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801698794.jpg?alt=media&token=33207fe2-a9b7-4372-ae52-2af156992cf5'),
(20, 'fozan@gmail.com', '07-02-2023-1675801715-76687.jpg', 'MRI report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801713821.jpg?alt=media&token=bf4897d9-f1a8-4f7f-95a6-5489b91d21e3'),
(21, 'fozan@gmail.com', '07-02-2023-1675801727-69059.jpg', 'Tumor report', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1675801726861.jpg?alt=media&token=445ef364-af39-45ec-96e9-9f34fe80301b'),
(22, 'hanzla@gmail.com', '22-02-2023-1677099103-85401.jpg', 'test', 'https://firebasestorage.googleapis.com/v0/b/remotecare-e1d52.appspot.com/o/test_record%2F1677099099411.jpg?alt=media&token=5da716db-36e5-4060-8d85-9e85669f2dbb');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_type` varchar(1) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `imageurl` text DEFAULT NULL,
  `doc_type` varchar(20) DEFAULT NULL,
  `user_token` varchar(400) DEFAULT NULL,
  `uid` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`, `user_type`, `gender`, `imageurl`, `doc_type`, `user_token`, `uid`) VALUES
(3, 'Umaid khakwani', 'umaid@gmail.com', '123456', '1', 'Male', '07-02-2023-1675800542-68251.jpg', '', '', 'zgojIve5C2bO79RnfedCGqnPr0F3'),
(4, 'Hanzla Sibghat', 'hanzla@gmail.com', '1234567', '1', 'Male', '07-02-2023-1675800950-81988.jpg', '', '', 'XbZgV0mVQfUk0t78Q5MxCITkxso1'),
(5, 'Muhammad Bilal', 'bilal@gmail.com', '123456', '1', 'Male', '07-02-2023-1675801505-49180.jpg', '', '', '1f0RVl9yUoYd9TURC8WSDjOxsDF3'),
(6, 'Raja Fozan', 'fozan@gmail.com', '123456', '1', 'Male', '07-02-2023-1675801761-81638.jpg', '', '', 'bTIJDxuISTNdnkhPKABHo2oLwAu1'),
(7, 'Waqas Ahmad ', 'waqas@gmail.com', '123456', '1', 'Male', '07-02-2023-1675801524-30077.jpg', '', '', '0u02olpTR3e5yxdZFon9WSgsDO13'),
(8, 'Muhammad Ahmad', 'ahmad@gmail.com', '123456', '2', 'Male', '07-02-2023-1675800708-95180.jpg', 'Cardiologists', '', 'C8uaqEx9VVZRdTAMbdQG0rA7N772'),
(9, 'Muhammad Taha', 'taha@gmail.com', '123456', '2', 'Male', '07-02-2023-1675800893-77597.jpg', 'Dermatologists', 'djMpWasfR92Pmufqhm5ME7:APA91bGCWX3_mw6eXXV8aIeNItybfBML2swAAJhkmeWpWteHXUWOTlK_am9MKl890-xTYeXvkdpx9L44bh6z3IHH-8skwTDPkDp7Y-OVCA8nhHvwHboOtA-ghwGxLbN8_cds7N-Dhj6M', '3ycPoqYEVmUeIYsLjjK8dO26VX32'),
(10, 'Rana Ali', 'ali@gmail.com', '123456', '2', 'Male', '07-02-2023-1675803235-18581.jpg', 'Neurologists', 'djMpWasfR92Pmufqhm5ME7:APA91bGCWX3_mw6eXXV8aIeNItybfBML2swAAJhkmeWpWteHXUWOTlK_am9MKl890-xTYeXvkdpx9L44bh6z3IHH-8skwTDPkDp7Y-OVCA8nhHvwHboOtA-ghwGxLbN8_cds7N-Dhj6M', 'dE8AcMj8XcfyslikrtYUIiF2pd02'),
(11, 'Saad Jilani', 'saad@gmail.com', '123456', '2', 'Male', '07-02-2023-1675803151-41352.jpg', 'Radiologists', NULL, 'bKWP2IMWH2gqng0aJtRrej2L8rd2'),
(12, 'Adeel Afzal', 'adeel@gmail.com', '123456', '2', 'Male', '07-02-2023-1675803316-51322.jpg', 'Allergists', NULL, 'XW9Tceh8KBNhBGFLn6VjqvyOumR2'),
(13, 'Saleem Raza', 'saleem@gmail.com', '123456', '2', 'Male', '07-02-2023-1675803403-91837.jpg', 'Plastic Surgeons', NULL, '6kKzNlksVLZ7vcEjMlno8rQFME52'),
(14, 'Admin', 'admin@gmail.com', '123456', '0', NULL, NULL, NULL, NULL, NULL),
(17, 'kashif', 'kashif@gmail.com', '123456', '2', 'Male', NULL, 'Physiatrists', '', 'K5ce8JJyrxWGfaQGIxUf4AAEcFA2');

-- --------------------------------------------------------

--
-- Table structure for table `weekly_steps`
--

CREATE TABLE `weekly_steps` (
  `id` int(11) NOT NULL,
  `Demail` varchar(128) DEFAULT NULL,
  `steps_daily` int(11) DEFAULT NULL,
  `date_log` date DEFAULT NULL,
  `Burnt_Calories` float DEFAULT NULL,
  `distance_covered` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `weekly_steps`
--

INSERT INTO `weekly_steps` (`id`, `Demail`, `steps_daily`, `date_log`, `Burnt_Calories`, `distance_covered`) VALUES
(1, 'waqas@gmail.com', 500, '2023-02-20', 500, NULL),
(2, 'waqas@gmail.com', 1000, '2023-02-21', 200, NULL),
(3, 'waqas@gmail.com', 3000, '2023-02-22', 550, NULL),
(4, 'waqas@gmail.com', 2000, '2023-02-23', 600, NULL),
(5, 'waqas@gmail.com', 1500, '2023-02-24', 670, NULL),
(6, 'waqas@gmail.com', 1100, '2023-02-25', 679, NULL),
(7, 'waqas@gmail.com', 1400, '2023-02-26', 170, NULL),
(8, 'waqas@gmail.com', 1900, '2023-02-27', 800, NULL),
(9, 'waqas@gmail.com', 1800, '2023-02-28', 1000, NULL),
(10, 'waqas@gmail.com', 2200, '2023-03-28', 1400, NULL),
(11, 'waqas@gmail.com', 2400, '0000-00-00', 1000, NULL),
(12, 'waqas@gmail.com', 2700, '2023-03-30', 2300, NULL),
(13, 'waqas@gmail.com', 3000, '2023-04-01', 2300, NULL),
(14, 'waqas@gmail.com', 3200, '2023-04-02', 2400, NULL),
(15, 'waqas@gmail.com', 400, '2023-04-03', 900, NULL),
(16, 'waqas@gmail.com', 1000, '2023-04-04', 1100, NULL),
(17, 'waqas@gmail.com', 3000, '2023-04-05', 2300, NULL),
(18, 'waqas@gmail.com', 3200, '2023-04-06', 2400, NULL),
(19, 'waqas@gmail.com', 400, '2023-04-07', 900, NULL),
(20, 'waqas@gmail.com', 1000, '2023-04-08', 1100, NULL),
(21, 'bilal@gmail.com', 500, '2022-12-20', 500, NULL),
(22, 'bilal@gmail.com', 1000, '2022-12-21', 200, NULL),
(23, 'bilal@gmail.com', 3000, '2022-12-22', 550, NULL),
(24, 'bilal@gmail.com', 2000, '2022-12-23', 600, NULL),
(25, 'bilal@gmail.com', 1500, '2022-12-24', 670, NULL),
(26, 'bilal@gmail.com', 1100, '2022-12-25', 679, NULL),
(27, 'bilal@gmail.com', 1400, '2022-12-26', 170, NULL),
(28, 'bilal@gmail.com', 1900, '2022-12-27', 800, NULL),
(29, 'bilal@gmail.com', 1800, '2022-12-28', 1000, NULL),
(30, 'bilal@gmail.com', 2200, '2022-12-29', 1400, NULL),
(31, 'bilal@gmail.com', 2400, '2022-12-30', 1000, NULL),
(32, 'bilal@gmail.com', 2700, '2022-12-31', 2300, NULL),
(33, 'bilal@gmail.com', 3000, '2023-04-01', 2300, NULL),
(34, 'bilal@gmail.com', 3200, '2023-04-02', 2400, NULL),
(35, 'bilal@gmail.com', 400, '2023-04-03', 900, NULL),
(36, 'bilal@gmail.com', 1000, '2023-04-04', 1100, NULL),
(37, 'bilal@gmail.com', 3000, '2023-04-05', 2300, NULL),
(38, 'bilal@gmail.com', 3200, '2023-04-06', 2400, NULL),
(39, 'bilal@gmail.com', 400, '2023-04-07', 900, NULL),
(40, 'bilal@gmail.com', 1000, '2023-04-08', 1100, NULL),
(41, 'hanzla@gmail.com', 900, '2023-01-20', 36, NULL),
(42, 'hanzla@gmail.com', 400, '2023-01-21', 16, NULL),
(43, 'hanzla@gmail.com', 600, '2023-01-22', 24, NULL),
(44, 'hanzla@gmail.com', 424, '2023-01-23', 16.9, NULL),
(45, 'hanzla@gmail.com', 900, '2023-01-24', 36, NULL),
(46, 'hanzla@gmail.com', 370, '2023-01-25', 14.8, NULL),
(47, 'hanzla@gmail.com', 140, '2023-01-26', 17, NULL),
(48, 'hanzla@gmail.com', 400, '2023-01-27', 20, NULL),
(49, 'hanzla@gmail.com', 800, '2023-01-28', 30, NULL),
(50, 'hanzla@gmail.com', 210, '2023-01-29', 22.5, NULL),
(51, 'hanzla@gmail.com', 420, '2023-01-30', 31.6, NULL),
(52, 'hanzla@gmail.com', 270, '2023-01-31', 18.8, NULL),
(53, 'hanzla@gmail.com', 300, '2023-04-01', 12, NULL),
(54, 'hanzla@gmail.com', 450, '2023-04-02', 18, NULL),
(55, 'hanzla@gmail.com', 780, '2023-04-03', 31.2, NULL),
(56, 'hanzla@gmail.com', 440, '2023-04-04', 17.6, NULL),
(57, 'hanzla@gmail.com', 520, '2023-04-05', 20.8, NULL),
(58, 'hanzla@gmail.com', 670, '2023-04-06', 26.8, NULL),
(59, 'hanzla@gmail.com', 640, '2023-04-07', 25.6, NULL),
(60, 'hanzla@gmail.com', 180, '2023-04-08', 7.2, NULL),
(61, 'hanzla@gmail.com', 760, '2023-04-09', 30.4, NULL),
(62, 'hanzla@gmail.com', 710, '2023-04-10', 28.4, NULL),
(63, 'hanzla@gmail.com', 630, '2023-04-11', 25.2, NULL),
(64, 'hanzla@gmail.com', 280, '2023-04-13', 27.2, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment_history`
--
ALTER TABLE `appointment_history`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `consumed_calories`
--
ALTER TABLE `consumed_calories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `daily_steps`
--
ALTER TABLE `daily_steps`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `doctor_appointment`
--
ALTER TABLE `doctor_appointment`
  ADD PRIMARY KEY (`d_email`,`p_email`);

--
-- Indexes for table `heartbeat`
--
ALTER TABLE `heartbeat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `patient_appointment`
--
ALTER TABLE `patient_appointment`
  ADD PRIMARY KEY (`p_email`,`d_email`);

--
-- Indexes for table `test_record`
--
ALTER TABLE `test_record`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `weekly_steps`
--
ALTER TABLE `weekly_steps`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment_history`
--
ALTER TABLE `appointment_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `consumed_calories`
--
ALTER TABLE `consumed_calories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT for table `daily_steps`
--
ALTER TABLE `daily_steps`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `heartbeat`
--
ALTER TABLE `heartbeat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `test_record`
--
ALTER TABLE `test_record`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `weekly_steps`
--
ALTER TABLE `weekly_steps`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=110;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
