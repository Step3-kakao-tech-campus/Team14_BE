SET REFERENTIAL_INTEGRITY False;
TRUNCATE TABLE member;
TRUNCATE TABLE image;
TRUNCATE TABLE post;
TRUNCATE TABLE post_like_count;
TRUNCATE TABLE point;
TRUNCATE TABLE post_insta_count;
SET REFERENTIAL_INTEGRITY True;
-- Member Table
INSERT INTO member (member_id,created_at, insta_id, kakao_id, profile_image_url, total_like, updated_at,
                    user_name, user_status,
                    role)
VALUES (1L,NOW(), 'insta1', 'kakao1',
        'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg', 10,
        NOW(), 'user1', 'STATUS_ACTIVE', 'ROLE_BEGINNER'),
       (2L,NOW(), 'insta2', 'kakao2',
        'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg', 20,
        NOW(), 'user2', 'STATUS_ACTIVE', 'ROLE_BEGINNER'),
       (3L,NOW(), 'insta3', 'kakao3',
        'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg', 30,
        NOW(), 'user3', 'STATUS_INACTIVE', 'ROLE_BEGINNER');

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
       'nickname' || (val),
       (val * 100),
       CASE WHEN val % 2 = 0 THEN true ELSE false END,
       0,
       (val * 1000),
       ((val % 10) + 1),
       ((val % 3) + 1),
       'hashtag' || ((val % 10) + 1) || ' ' || 'hashtag' || ((val % 10) + 2)
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
                      WHEN post_id BETWEEN 241 AND 300
                          THEN FLOOR(RAND() * 10000) -- 0~9999 (네 자리 숫자)
                      ELSE 0
    END,
    modified_at = NOW()
WHERE post_id BETWEEN 1 AND 300;

INSERT INTO post_insta_count (post_id, member_id, insta_count, created_at, modified_at)
SELECT post_id, member_id, 0, NOW(), NOW()
FROM post
WHERE post_id BETWEEN 1 AND 300;

-- Insert into Point Table
INSERT INTO point
    (member_id, now_point, created_at, updated_at)
VALUES (1, 200, NOW(), NOW()),
       (2, 200, NOW(), NOW()),
       (3, 300, NOW(), NOW());
