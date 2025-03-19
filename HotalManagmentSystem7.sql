
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotelmanagementsystem7`
--

-- --------------------------------------------------------

--
--


-- --------------------------------------------------------

--
-- Table structure for table `guest`
--

CREATE TABLE `guest` (
  `GuestID` int(50) NOT NULL,
  `GuestName` varchar(50) NOT NULL,
  `Nationaility` varchar(20) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Phone` int(50) NOT NULL,
  `Email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 
--


-- --------------------------------------------------------
ALTER TABLE guest ADD INDEX guest_id_index (GuestID);
--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `BookingID` int(50) NOT NULL,
  `GuestID` int(50) NOT NULL,
  `RoomNumber` varchar(20) NOT NULL,
  `CheckinDate` date NOT NULL,
  `CheckoutDate` date NOT NULL,
  `Status` varchar(20) NOT NULL DEFAULT 'Checked In',
FOREIGN KEY (`GuestID`) REFERENCES `guest` (`GuestID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `booking`
--


-- --------------------------------------------------------
-- Table structure for table `payment`
--
ALTER TABLE booking ADD INDEX booking_id_index (BookingID);

CREATE TABLE `payment` (
  `PaymentID` int(50) NOT NULL,
  `BookingID` int(50) NOT NULL,
  `date` date NOT NULL,
  `Amount` int(50) NOT NULL,
FOREIGN KEY (`BookingID`) REFERENCES `booking` (`BookingID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bills`
--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `RoomNumber` varchar(20) NOT NULL,
  `Type` varchar(50) NOT NULL,
  `Ppice` int(20) NOT NULL,
  `Status` varchar(50) NOT NULL DEFAULT 'Not Booked'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`RoomNumber`, `Type`, `Ppice`, `Status`) VALUES
('100', 'Single', 1000, 'Booked'),
('101', 'Double', 1500, 'Not Booked'),
('102', 'Single', 1000, 'Booked'),
('200', 'Family', 3000, 'Not Booked'),
('201', 'Luxury', 5000, 'Not Booked'),
('202', 'Double', 1500, 'Booked'),
('301', 'Luxury', 5000, 'Not Booked'),
('302', 'Luxury', 5000, 'Not Booked'),
('303', 'Family', 3000, 'Not Booked'),
('401', 'Single', 1000, 'Not Booked'),
('402', 'Double', 1000, 'Not Booked'),
('501', 'Presidential Suite', 10000, 'Not Booked'),
('502', 'Presidential Suite', 10000, 'Not Booked'),
('701', 'Double', 1500, 'Not Booked');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `gender` varchar(20) NOT NULL,
  `securityQuestion` varchar(100) NOT NULL,
  `answer` varchar(200) NOT NULL,
  `address` varchar(200) NOT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `username`, `password`, `gender`, `securityQuestion`, `answer`, `address`, `status`) VALUES
(1, 'Duc', 'minhduc1122002', 'Duc2002lol@', 'Male', 'What is the name of your first pet?', 'Jeff', 'Ha Noi', NULL),
(2, 'Khanh', 'khanh0140', 'iamironman3', 'Male', 'What is the name of the town where you were born?', 'Ha Noi', 'Ha Noi', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`PaymentID`),
  ADD UNIQUE KEY `fk_pay_book` (`bookingID`) USING BTREE;
 

--
-- Indexes for table `guest`
--
ALTER TABLE `guest`
  ADD PRIMARY KEY (`GuestID`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`BookingID`),
  ADD KEY `fk_customers_res` (`GuestID`),
  ADD KEY `fk_rooms_res` (`RoomNumber`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`RoomNumber`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `PaymentID` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `Booking`
--
ALTER TABLE `booking`
  MODIFY `BookingID` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `payment`
--

--
-- Constraints for table `reservations`
--
ALTER TABLE `booking`
  
  ADD CONSTRAINT `fk_rooms_res` FOREIGN KEY (`RoomNumber`) REFERENCES `room` (`RoomNumber`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;