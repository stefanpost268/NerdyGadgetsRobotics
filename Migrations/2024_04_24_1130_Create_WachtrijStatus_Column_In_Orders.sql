 -- 24/04/2024 11:30

 
-- Colom WachtrijStatus toegevoegd aan Orders
ALTER TABLE nerdygadgets.Orders
ADD WachtrijStatus VARCHAR(25) NOT NULL;

-- alle oude Orders op Status: 'Done'
UPDATE orders
SET WachtrijStatus = "Done";

-- De laatste 15 Orders op status "Open", voor testdata
UPDATE orders
SET WachtrijStatus = "Open"
Where OrderID <= 15;