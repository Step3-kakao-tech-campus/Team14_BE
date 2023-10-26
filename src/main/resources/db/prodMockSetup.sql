SET foreign_key_checks = 0;
TRUNCATE TABLE member;
TRUNCATE TABLE image;
TRUNCATE TABLE post;
TRUNCATE TABLE post_like_count;
TRUNCATE TABLE point;
SET foreign_key_checks = 1;


-- Member Table
INSERT INTO member (created_at, insta_id, kakao_id, profile_image_url, total_like, updated_at,
                    user_name, user_status,
                    role)
VALUES (NOW(), 'insta1', 'kakao1',
        'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg', 10,
        NOW(), 'user1', 'STATUS_ACTIVE', 'ROLE_BEGINNER'),
       (NOW(), 'insta2', 'kakao2',
        'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg', 20,
        NOW(), 'user2', 'STATUS_ACTIVE', 'ROLE_BEGINNER'),
       (NOW(), 'insta3', 'kakao3',
        'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg', 30,
        NOW(), 'user3', 'STATUS_DORMANT', 'ROLE_BEGINNER');

-- Image Table
INSERT INTO image (created_at, image_uri)
VALUES (NOW(), '/images/test.jpg'),
       (NOW(), '/images/test.jpg'),
       (NOW(), '/images/test.jpg'),
       (NOW(), '/images/test.jpg'),
       (NOW(), '/images/test.jpg'),
       (NOW(), '/images/test.jpg'),
       (NOW(), '/images/test.jpg'),
       (NOW(), '/images/test.jpg'),
       (NOW(), '/images/test.jpg'),
       (NOW(), '/images/test.jpg');

-- -- Post Table
-- INSERT INTO post (created_at, nickname, popularity, published, report_count, view_count,
--                   image_id, member_id, hashtag)
-- VALUES (NOW(), 'nickname1', 100, true, 0, 1000, 1, 1, '#hashtag1'),
--        (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
--        (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
--        (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
--        (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
--        (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
--        (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
--        (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
--        (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
--        (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
--        (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
--        (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
--        (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
--        (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
--        (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
--        (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
--        (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
--        (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
--        (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
--        (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10');

-- Using UNION ALL to generate numbers up to 280
WITH RECURSIVE numbers(val) AS (SELECT 1
                                UNION ALL
                                SELECT val + 1
                                FROM numbers
                                WHERE val < 300)

INSERT
INTO post
(created_at, nickname, popularity, published, report_count, view_count, image_id, member_id,
 hashtag)
SELECT NOW(),
       'nickname' || (val + 20),
       (val * 100),
       CASE WHEN val % 2 = 0 THEN true ELSE false END,
       0,
       (val * 1000),
       ((val % 10) + 1),
       ((val % 3) + 1),
       '#hashtag' || ((val % 10) + 1)
FROM numbers;


-- Insert PostLikeCount for all the 300 posts
INSERT INTO post_like_count
    (post_id, like_count, created_at, modified_at)
SELECT post_id,
       0,
       NOW(),
       NOW()
FROM post
WHERE post_id BETWEEN 1 AND 300;

UPDATE post_like_count
SET like_count  = CASE
                      WHEN post_id BETWEEN 1 AND 60 THEN FLOOR(RAND() * 10) -- 0~9 (한 자리 숫자)
                      WHEN post_id BETWEEN 61 AND 120 THEN FLOOR(RAND() * 100) -- 0~99 (두 자리 숫자)
                      WHEN post_id BETWEEN 121 AND 240 THEN FLOOR(RAND() * 1000) -- 0~999 (세 자리 숫자)
                      WHEN post_id BETWEEN 241 AND 300 THEN FLOOR(RAND() * 10000) -- 0~9999 (네 자리 숫자)
                      ELSE 0
    END,
    modified_at = NOW()
WHERE post_id BETWEEN 1 AND 300;


-- Insert into Point Table
INSERT INTO point
    (member_id, now_point, created_at, updated_at)
VALUES (1, 200, NOW(), NOW()),
       (2, 200, NOW(), NOW()),
       (3, 300, NOW(), NOW());
UPDATE member
SET total_like = FLOOR(RAND() * (999 - 100 + 1)) + 100
WHERE insta_id = 'insta1';
UPDATE member
SET total_like = FLOOR(RAND() * (9999 - 1000 + 1)) + 1000
WHERE insta_id = 'insta2';
UPDATE member
SET total_like = FLOOR(RAND() * (9 - 1 + 1)) + 1
WHERE insta_id = 'insta3';
