INSERT INTO concert (name, singer) VALUES
('아이유콘서트', '아이유'),
('트와이스드림콘서트', '트와이스'),
('흠뻑쇼', '싸이'),
('랩콘서트', '빈지노');


INSERT INTO concert_schedule (concert_date, concert_id) VALUES
('2024-05-01', 1),
('2024-05-02', 2),
('2024-05-03', 3),
('2024-05-04', 4),
('2024-05-05', 1),
('2024-05-06', 2),
('2024-05-07', 3),
('2024-05-08', 4),
('2024-05-09', 1),
('2024-05-10', 2);

-- 좌석을 50개 INSERT 하는 쿼리
-- INSERT INTO seat (price, seat_status, concert_id, concert_schedule_id, seat_no)
-- SELECT
--     100, -- 가격 설정
--     0,   -- 좌석 상태 (0: available)
--     1,   -- 콘서트 ID
--     cs.concert_schedule_id,   -- 콘서트 스케줄 ID
--     s.seat_no   -- 좌석 번호
-- FROM
--     (SELECT
--          cs.concert_schedule_id
--      FROM
--          concert_schedule cs
--      WHERE
--          cs.concert_date BETWEEN '2024-05-01' AND '2024-05-04') cs
--         CROSS JOIN
--     (SELECT
--              (a.a + (10 * b.a)) + 1 AS seat_no
--      FROM
--          (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a
--              CROSS JOIN
--          (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b
--     ) s
--     LIMIT 50;

-- 좌석을 10만개 INSERT 하는 쿼리
INSERT INTO seat (price, seat_status, concert_id, concert_schedule_id, seat_no)
SELECT
    100, -- 가격 설정
    0,   -- 좌석 상태 (0: available)
    1,   -- 콘서트 ID
    cs.concert_schedule_id,   -- 콘서트 스케줄 ID
    s.seat_no   -- 좌석 번호
FROM
    (SELECT
         cs.concert_schedule_id
     FROM
         concert_schedule cs
     WHERE
         cs.concert_date BETWEEN '2024-05-01' AND '2024-05-04') cs
        CROSS JOIN
    (SELECT
             (a.a + (10 * b.a) + (100 * c.a) + (1000 * d.a) + (10000 * e.a)) + 1 AS seat_no
     FROM
         (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a
             CROSS JOIN
         (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b
             CROSS JOIN
         (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS c
             CROSS JOIN
         (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS d
             CROSS JOIN
         (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS e
    ) s;


INSERT INTO seatO (price, seat_status, concert_id, concert_schedule_id, seat_no, version)
SELECT
    100, -- 가격 설정
    0, -- 좌석 상태 설정 (AVAILABLE: 예약 가능)
    1,   -- 콘서트 ID
    cs.concert_schedule_id,   -- 콘서트 스케줄 ID
    s.seat_no,   -- 좌석 번호
    0 -- version 필드 초기화
FROM
    (SELECT
         cs.concert_schedule_id
     FROM
         concert_schedule cs
     WHERE
         cs.concert_date BETWEEN '2024-05-01' AND '2024-05-04') cs
        CROSS JOIN
    (SELECT
             (a.a + (10 * b.a)) + 1 AS seat_no
     FROM
         (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a
             CROSS JOIN
         (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b
    ) s
    LIMIT 50;

INSERT INTO seatP (price, seat_status, concert_id, concert_schedule, seat_no, temp_reserved_user_id, temp_reserved_expired_at)
SELECT
    100,                         -- 가격 설정
    0,                           -- 좌석 상태 (0: available)
    1,                           -- 콘서트 ID
    cs.concert_date,             -- 콘서트 스케줄 날짜
    s.seat_no,                   -- 좌석 번호
    NULL,                        -- 임시 예약 사용자 ID (NULL로 초기화)
    NULL                         -- 임시 예약 만료 시각 (NULL로 초기화)
FROM
    (SELECT DISTINCT concert_date FROM concert_schedule WHERE concert_date BETWEEN '2024-05-01' AND '2024-05-04') cs
        CROSS JOIN
    (SELECT (a.a + (10 * b.a)) + 1 AS seat_no FROM (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b ) s
    LIMIT 50;


INSERT INTO seatD (price, seat_status, concert_id, concert_schedule, seat_no, temp_reserved_user_id, temp_reserved_expired_at)
SELECT
    100,                         -- 가격 설정
    0,                           -- 좌석 상태 (0: available)
    1,                           -- 콘서트 ID
    cs.concert_date,             -- 콘서트 스케줄 날짜
    s.seat_no,                   -- 좌석 번호
    NULL,                        -- 임시 예약 사용자 ID (NULL로 초기화)
    NULL                         -- 임시 예약 만료 시각 (NULL로 초기화)
FROM
    (SELECT DISTINCT concert_date FROM concert_schedule WHERE concert_date BETWEEN '2024-05-01' AND '2024-05-04') cs
        CROSS JOIN
    (SELECT (a.a + (10 * b.a)) + 1 AS seat_no FROM (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b ) s
    LIMIT 50;