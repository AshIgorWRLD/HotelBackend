INSERT INTO apartments (name, description, floor, rooms_total, price_per_day, available_count, hotel_id)
VALUES  ('Standart with city view / 1 big bed', 'Impedit, mollitia neque, nesciunt nisi non numquam odit quia quo sequi similique soluta voluptates', 5, 1, 70, 2,
        (SELECT hotels.id
        FROM hotels
            WHERE(hotels.name = 'Horizon Patio'))),
        ('Lux', 'Impedit, mollitia neque, nesciunt nisi non numquam odit quia quo sequi similique soluta voluptates', 8, 3, 160, 4,
        (SELECT hotels.id
        FROM hotels
            WHERE(hotels.name = 'Horizon Patio'))),
        ('President', 'Impedit, mollitia neque, nesciunt nisi non numquam odit quia quo sequi similique soluta voluptates', 10, 3, 215, 2,
        (SELECT hotels.id
        FROM hotels
            WHERE(hotels.name = 'Horizon Patio'))),
        ('Standart / 2 beds', 'Impedit, mollitia neque, nesciunt nisi non numquam odit quia quo sequi similique soluta voluptates', 1, 1, 40, 10,
        (SELECT hotels.id
        FROM hotels
           WHERE(hotels.name = 'Crowne Plaza'))),
        ('Standart / 2 beds', 'Impedit, mollitia neque, nesciunt nisi non numquam odit quia quo sequi similique soluta voluptates', 1, 1, 35, 8,
        (SELECT hotels.id
        FROM hotels
           WHERE(hotels.name = 'Sunset Lodge'))),
        ('Standart / 1 big bed', 'Impedit, mollitia neque, nesciunt nisi non numquam odit quia quo sequi similique soluta voluptates', 2, 1, 50, 6,
        (SELECT hotels.id
        FROM hotels
           WHERE(hotels.name = 'The Home'))),
        ('Standart / 1 big bed', 'Impedit, mollitia neque, nesciunt nisi non numquam odit quia quo sequi similique soluta voluptates', 5, 1, 50, 3,
        (SELECT hotels.id
        FROM hotels
           WHERE(hotels.name = 'Sunset Lodge'))),
        ('Deluxe with sofa new', 'Impedit, mollitia neque, nesciunt nisi non numquam odit quia quo sequi similique soluta voluptates', 9, 3, 120, 1,
        (SELECT hotels.id
        FROM hotels
           WHERE(hotels.name = 'Sunset Lodge'))),
        ('Standart / 2 beds', 'Impedit, mollitia neque, nesciunt nisi non numquam odit quia quo sequi similique soluta voluptates', 4, 1, 55, 4,
        (SELECT hotels.id
        FROM hotels
           WHERE(hotels.name = 'Sunset Lodge'))),
        ('1', 'Impedit, mollitia neque, nesciunt nisi non numquam odit quia quo sequi similique soluta voluptates', 1, 1, 10, 2,
        (SELECT hotels.id
        FROM hotels
           WHERE(hotels.name = 'Sunset Lodge'))),
        ('Standart / 2 beds', 'Impedit, mollitia neque, nesciunt nisi non numquam odit quia quo sequi similique soluta voluptates', 3, 1, 54, 6,
        (SELECT hotels.id
        FROM hotels
           WHERE(hotels.name = 'Sunset Lodge')));



