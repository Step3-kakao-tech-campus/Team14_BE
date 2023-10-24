-- Check if the 'krampoline' database exists and create it if not
CREATE SCHEMA IF NOT EXISTS `krampoline` DEFAULT CHARACTER SET utf8mb4;
USE `krampoline`;

GRANT ALL ON *.* TO 'root'@'localhost' WITH GRANT OPTION;
GRANT ALL
    ON krampoline.* TO 'root'@'localhost';
FLUSH
    PRIVILEGES;
