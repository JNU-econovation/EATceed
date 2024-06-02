CREATE
    USER 'gaebaljip-local'@'localhost' IDENTIFIED BY 'gaebaljip-local';
CREATE
    USER 'gaebaljip-local'@'%' IDENTIFIED BY 'gaebaljip-local';

GRANT ALL PRIVILEGES ON *.* TO
    'gaebaljip-local'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO
    'gaebaljip-local'@'%';

CREATE
    DATABASE gaebaljip DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
