-- data.sql
INSERT INTO tour (id, name, description, from_location, to_location, transport_type, distance, estimated_time, map_image_url)
VALUES ('1', 'Tour 1', 'Description 1', 'Location A', 'Location B', 'driving-car', 120.0, 90.0, 'path/to/image1.png')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO tour (id, name, description, from_location, to_location, transport_type, distance, estimated_time, map_image_url)
VALUES ('2', 'Tour 2', 'Description 2', 'Location C', 'Location D', 'cycling-regular', 60.0, 45.0, 'path/to/image2.png')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO tour_log (log_id, date, comment, difficulty, total_time, rating, tour_id)
VALUES ('1', '2024-06-15', 'Great tour!', 3, 120.0, 5, '1')
    ON CONFLICT (log_id) DO NOTHING;

INSERT INTO tour_log (log_id, date, comment, difficulty, total_time, rating, tour_id)
VALUES ('2', '2024-06-16', 'Nice tour!', 2, 60.0, 4, '2')
    ON CONFLICT (log_id) DO NOTHING;


DELETE FROM tour WHERE id = '1';
DELETE FROM tour WHERE id = '2';