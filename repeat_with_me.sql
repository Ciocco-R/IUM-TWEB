-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Feb 08, 2023 alle 22:42
-- Versione del server: 10.4.25-MariaDB
-- Versione PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `repeat_with_me`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `booking`
--

CREATE TABLE `booking` (
  `idBooking` int(4) NOT NULL,
  `idUser` int(4) NOT NULL,
  `idTeacher` int(4) NOT NULL,
  `idCourse` int(4) NOT NULL,
  `dayCalendar` enum('lunedì','martedì','mercoledì','giovedì','venerdì') NOT NULL,
  `hourCalendar` enum('15-16','16-17','17-18','18-19') NOT NULL,
  `state` enum('deleted','active','done') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `calendar`
--

CREATE TABLE `calendar` (
  `day` enum('lunedì','martedì','mercoledì','giovedì','venerdì') NOT NULL,
  `hour` enum('15-16','16-17','17-18','18-19') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `calendar`
--

INSERT INTO `calendar` (`day`, `hour`) VALUES
('lunedì', '15-16'),
('lunedì', '16-17'),
('lunedì', '17-18'),
('lunedì', '18-19'),
('martedì', '15-16'),
('martedì', '16-17'),
('martedì', '17-18'),
('martedì', '18-19'),
('mercoledì', '15-16'),
('mercoledì', '16-17'),
('mercoledì', '17-18'),
('mercoledì', '18-19'),
('giovedì', '15-16'),
('giovedì', '16-17'),
('giovedì', '17-18'),
('giovedì', '18-19'),
('venerdì', '15-16'),
('venerdì', '16-17'),
('venerdì', '17-18'),
('venerdì', '18-19');

-- --------------------------------------------------------

--
-- Struttura della tabella `course`
--

CREATE TABLE `course` (
  `idCourse` int(4) NOT NULL,
  `title` varchar(30) NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `course`
--

INSERT INTO `course` (`idCourse`, `title`, `visible`) VALUES
(1, 'Matematica discreta', 1),
(5, 'Programmazione3', 1),
(6, 'TWEB', 1),
(7, 'DB', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `teacher`
--

CREATE TABLE `teacher` (
  `idTeacher` int(4) NOT NULL,
  `name` varchar(30) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `teacher`
--

INSERT INTO `teacher` (`idTeacher`, `name`, `surname`, `visible`) VALUES
(8, 'Ardissono', 'Liliana', 1),
(9, 'Paolo', 'Pensa', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `teaching`
--

CREATE TABLE `teaching` (
  `idTeacher` int(4) NOT NULL,
  `idCourse` int(4) NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `teaching`
--

INSERT INTO `teaching` (`idTeacher`, `idCourse`, `visible`) VALUES
(8, 5, 1),
(8, 6, 1),
(9, 7, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `idUser` int(4) NOT NULL,
  `email` varchar(40) NOT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` enum('admin','client','guest') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`idUser`, `email`, `username`, `password`, `role`) VALUES
(6, 'c1@c1.c1', 'c1', 'A9F7E97965D6CF799A529102A973B8B9', 'client'),
(8, 'ch@ch.ch', 'ch', 'D88FC6EDF21EA464D35FF76288B84103', 'admin'),
(16, 'c2@c2.c2', 'c2', '9AB62B5EF34A985438BFDF7EE0102229', 'client');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`idBooking`),
  ADD KEY `idUser` (`idUser`),
  ADD KEY `idTeacher` (`idTeacher`,`idCourse`),
  ADD KEY `dayCalendar` (`dayCalendar`,`hourCalendar`);

--
-- Indici per le tabelle `calendar`
--
ALTER TABLE `calendar`
  ADD PRIMARY KEY (`day`,`hour`);

--
-- Indici per le tabelle `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`idCourse`);

--
-- Indici per le tabelle `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`idTeacher`);

--
-- Indici per le tabelle `teaching`
--
ALTER TABLE `teaching`
  ADD PRIMARY KEY (`idTeacher`,`idCourse`),
  ADD KEY `vincoloInsegnamentoCorso` (`idCourse`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `booking`
--
ALTER TABLE `booking`
  MODIFY `idBooking` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT per la tabella `course`
--
ALTER TABLE `course`
  MODIFY `idCourse` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT per la tabella `teacher`
--
ALTER TABLE `teacher`
  MODIFY `idTeacher` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT per la tabella `user`
--
ALTER TABLE `user`
  MODIFY `idUser` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`idTeacher`,`idCourse`) REFERENCES `teaching` (`idTeacher`, `idCourse`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`dayCalendar`,`hourCalendar`) REFERENCES `calendar` (`day`, `hour`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `teaching`
--
ALTER TABLE `teaching`
  ADD CONSTRAINT `vincoloInsegnamentoCorso` FOREIGN KEY (`idCourse`) REFERENCES `course` (`idCourse`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `vincoloInsegnamentoDocente` FOREIGN KEY (`idTeacher`) REFERENCES `teacher` (`idTeacher`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
