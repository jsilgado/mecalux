ALTER TABLE warehouse ADD cca3 varchar NULL;

-- populate

UPDATE warehouse
SET cca3 = 'col'
WHERE client = 'Amazon';

UPDATE warehouse
SET cca3 = 'esp'
WHERE client = 'Mercadona';