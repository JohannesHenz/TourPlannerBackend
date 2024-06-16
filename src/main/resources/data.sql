INSERT INTO tour (id, name, description, from_location, to_location, transport_type, distance, estimated_time, map_image_url)
VALUES ('1', 'Tour 1', 'Description 1', 'Location A', 'Location B', 'driving-car', 120.0, 90.0, 'path/to/image1.png'),
       ('2', 'Tour 2', 'Description 2', 'Location C', 'Location D', 'cycling-regular', 60.0, 45.0, 'path/to/image2.png');

INSERT INTO tour_log (log_id, tour_id, date, time, comment, difficulty, total_time, rating, total_distance)
VALUES ('1', '1', '2024-06-15 10:00:00', 1.0, 'Great tour!', 3, 120.0, 5, 10.5),
       ('2', '2', '2024-06-16 12:00:00', 1.0, 'Nice tour!', 2, 60.0, 4, 5.2);
