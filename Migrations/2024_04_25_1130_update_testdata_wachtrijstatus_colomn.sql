 -- 25/04/2024 10:30
 
-- De laatste 15 Orders op status "Open", voor testdata
UPDATE orders
SET WachtrijStatus = "Open"
Where OrderID <= 15;