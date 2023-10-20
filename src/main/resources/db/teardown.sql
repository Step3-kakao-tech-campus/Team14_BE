SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE member;
TRUNCATE TABLE image;
TRUNCATE TABLE post;
TRUNCATE TABLE post_like_count;
TRUNCATE TABLE point;
SET REFERENTIAL_INTEGRITY TRUE;

-- Member Table
INSERT INTO member (created_at, insta_id, kakao_id, total_like, updated_at, user_name, user_status,
                    role)
VALUES (NOW(), 'insta1', 'kakao1', 10, NOW(), 'user1', 'STATUS_ACTIVE', 'ROLE_BEGINNER'),
       (NOW(), 'insta2', 'kakao2', 20, NOW(), 'user2', 'STATUS_ACTIVE', 'ROLE_BEGINNER'),
       (NOW(), 'insta3', 'kakao3', 30, NOW(), 'user3', 'STATUS_DORMANT', 'ROLE_BEGINNER');

-- Image Table
INSERT INTO image (created_at, image_uri)
VALUES (NOW(), 'image_uri1'),
       (NOW(), 'image_uri2'),
       (NOW(), 'image_uri3'),
       (NOW(), 'image_uri4'),
       (NOW(), 'image_uri5'),
       (NOW(), 'image_uri6'),
       (NOW(), 'image_uri7'),
       (NOW(), 'image_uri8'),
       (NOW(), 'image_uri9'),
       (NOW(), 'image_uri10');

-- Post Table
INSERT INTO post (created_at, nickname, popularity, published, report_count, university, view_count,
                  image_id, member_id, hashtag)
VALUES (NOW(), 'nickname1', 100, true, 0, 'university1', 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname2', 200, true, 1, 'university2', 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 'university3', 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 'university4', 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 'university5', 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 'university6', 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 'university7', 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 'university8', 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 'university9', 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 'university10', 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 'university1', 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 'university2', 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 'university3', 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 'university4', 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 'university5', 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 'university6', 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 'university7', 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 'university8', 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 'university9', 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 'university10', 10000, 10, 1, '#hashtag10');

-- Using UNION ALL to generate numbers up to 280
WITH RECURSIVE numbers(val) AS (
    SELECT 1
    UNION ALL
    SELECT val + 1
    FROM numbers
    WHERE val < 280
)

INSERT INTO post (created_at, nickname, popularity, published, report_count, university, view_count, image_id, member_id, hashtag)
SELECT
    NOW(),
    'nickname' || (val + 20),
    (val * 100),
    CASE WHEN val % 2 = 0 THEN true ELSE false END,
    0,
    'university' || ((val % 10) + 1),
    (val * 1000),
    ((val % 10) + 1),
    ((val % 3) + 1),
    '#hashtag' || ((val % 10) + 1)
FROM numbers;


-- Insert PostLikeCount for all the 300 posts
INSERT INTO post_like_count (post_id, like_count, created_at, modified_at)
SELECT post_id, 0, NOW(), NOW()
FROM post
WHERE post_id BETWEEN 1 AND 300;

-- Point Table
INSERT INTO point (member_id, now_point, created_at, updated_at)
VALUES (1, 200, NOW(), NOW()),
       (2, 200, NOW(), NOW()),
       (3, 300, NOW(), NOW());
