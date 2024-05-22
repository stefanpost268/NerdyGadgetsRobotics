UPDATE stockitems
SET ItemLocation = CONCAT(
    CHAR(FLOOR(RAND() * 5) + 65),  -- Generates a random letter between A and E
    FLOOR(RAND() * 5) + 1          -- Generates a random number between 1 and 5
);