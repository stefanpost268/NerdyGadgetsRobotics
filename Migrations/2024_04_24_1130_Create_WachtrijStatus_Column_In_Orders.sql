 -- 24/04/2024 11:30

-- Colom WachtrijStatus toegevoegd aan Orders
ALTER TABLE nerdygadgets.Orders
ADD Status VARCHAR(25) NOT NULL;