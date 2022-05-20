INSERT INTO services_hotels (service_id, hotel_id)
VALUES ((SELECT services.id
        FROM services
            WHERE services.name = 'Spa'),
       (SELECT hotels.id
        FROM hotels
            WHERE hotels.name = 'Horizon Patio')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Gym'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'Horizon Patio')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Diving'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'Horizon Patio')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Aquapark'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'Horizon Patio')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Tennis'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'Horizon Patio')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Bungee jumping'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'Horizon Patio')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Spa'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'Sunset Lodge')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Gym'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'Sunset Lodge')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Diving'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'Sunset Lodge')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Aquapark'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'Crowne Plaza')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Spa'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'Crowne Plaza')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Tennis'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'The Home')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Bungee jumping'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'The Home')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Excursion to pyramids'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'The Home')),
       ((SELECT services.id
           FROM services
           WHERE services.name = 'Gym'),
       (SELECT hotels.id
           FROM hotels
           WHERE hotels.name = 'The Home'));