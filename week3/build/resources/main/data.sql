INSERT INTO concert (concert_date, price, concert_name)
VALUES
('2024-04-11', 10000, 'Maroon 5'),
('2024-04-12', 12000, 'Beyoncé'),
('2024-04-13', 11000, 'Ed Sheeran'),
('2024-04-14', 9000, 'Taylor Swift'),
('2024-04-15', 9500, 'Ariana Grande');


INSERT INTO seat (reservation_status, concert_id, seat_no, temp_reserved_expired_at, temp_reserved_user_id)
VALUES
(1, 1, 1, '2024-04-11 12:00:00', 1001),
(0, 1, 2, NULL, NULL),
(0, 1, 3, NULL, NULL),
(1, 1, 4, '2024-04-12 14:00:00', 1002),
(0, 1, 5, NULL, NULL);