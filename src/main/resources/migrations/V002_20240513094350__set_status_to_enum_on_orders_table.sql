-- Dropping Status because you cant alter to ENUM
ALTER TABLE orders
DROP COLUMN Status;

ALTER TABLE orders
ADD COLUMN Status ENUM('Open', 'InProgress', 'Done', 'Error') NOT NULL DEFAULT 'Open';