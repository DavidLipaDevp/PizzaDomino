CREATE OR REPLACE PROCEDURE take_random_pizza_order(
    id_customer VARCHAR(15),
    method CHAR(1),
    OUT order_taken BOOLEAN
)
LANGUAGE plpgsql AS $$
DECLARE
    id_random_pizza INT;
    price_random_pizza NUMERIC(5,2);
    price_with_discount NUMERIC(5,2);
    WITH_ERRORS BOOLEAN := FALSE;
BEGIN
    BEGIN
        SELECT id_pizza, price
        INTO id_random_pizza, price_random_pizza
        FROM pizza
        WHERE available = 1
        ORDER BY random()
        LIMIT 1;

        price_with_discount := price_random_pizza - (price_random_pizza * 0.20);

        BEGIN
            INSERT INTO pizza_order (id_customer, date, total, method, additional_notes)
            VALUES (id_customer, current_timestamp, price_with_discount, method, '20% OFF PIZZA RANDOM PROMOTION')
            ;

            INSERT INTO orden_item (id_item, id_order, id_pizza, quantity, price)
            VALUES (1, lastval (), id_random_pizza, 1, price_random_pizza);

        EXCEPTION
            WHEN others THEN
                -- If there is an error, set WITH_ERRORS to true
                WITH_ERRORS := TRUE;
                -- Rollback the transaction
        END;

        -- Set order_taken based on WITH_ERRORS
        IF WITH_ERRORS THEN 
           order_taken := FALSE;
        ELSE 
            order_taken := TRUE;
        END IF;
    
   END;
END;
$$
;
