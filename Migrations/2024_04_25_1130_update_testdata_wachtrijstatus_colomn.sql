 -- 25/04/2024 10:30
 
-- De laatste 15 Orders op status "Open", voor testdata
UPDATE orders
SET Status = "Open"
Where OrderID <= 15;

 UPDATE orders
 SET Status = 'In progress'
 WHERE OrderID = 25;
