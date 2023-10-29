SET
foreign_key_checks = 0;
TRUNCATE TABLE member;
TRUNCATE TABLE image;
TRUNCATE TABLE post;
TRUNCATE TABLE post_like_count;
TRUNCATE TABLE point;


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
INSERT INTO post (created_at, nickname, popularity, published, report_count, view_count, image_id, member_id, hashtag)
VALUES (NOW(), 'nickname1', 200, true, 1, 2000, 2, 1, '#hashtag2'),
       (NOW(), 'nickname2', 300, true, 2, 3000, 3, 2, '#hashtag3'),
       (NOW(), 'nickname3', 400, true, 3, 4000, 4, 0, '#hashtag4'),
       (NOW(), 'nickname4', 500, true, 4, 5000, 5, 1, '#hashtag5'),
       (NOW(), 'nickname5', 600, true, 5, 6000, 6, 2, '#hashtag6'),
       (NOW(), 'nickname6', 700, true, 6, 7000, 7, 0, '#hashtag7'),
       (NOW(), 'nickname7', 800, true, 7, 8000, 8, 1, '#hashtag8'),
       (NOW(), 'nickname8', 900, true, 8, 9000, 9, 2, '#hashtag9'),
       (NOW(), 'nickname9', 1000, true, 9, 10000, 10, 0, '#hashtag10'),
       (NOW(), 'nickname10', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname11', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname12', 300, true, 2, 3000, 3, 0, '#hashtag3'),
       (NOW(), 'nickname13', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname14', 500, true, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname15', 600, true, 5, 6000, 6, 0, '#hashtag6'),
       (NOW(), 'nickname16', 700, true, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname17', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname18', 900, true, 8, 9000, 9, 0, '#hashtag9'),
       (NOW(), 'nickname19', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname20', 100, true, 0, 1000, 1, 2, '#hashtag1'),
       (NOW(), 'nickname21', 200, true, 1, 2000, 2, 0, '#hashtag2'),
       (NOW(), 'nickname22', 300, true, 2, 3000, 3, 1, '#hashtag3'),
       (NOW(), 'nickname23', 400, true, 3, 4000, 4, 2, '#hashtag4'),
       (NOW(), 'nickname24', 500, true, 4, 5000, 5, 0, '#hashtag5'),
       (NOW(), 'nickname25', 600, true, 5, 6000, 6, 1, '#hashtag6'),
       (NOW(), 'nickname26', 700, true, 6, 7000, 7, 2, '#hashtag7'),
       (NOW(), 'nickname27', 800, true, 7, 8000, 8, 0, '#hashtag8'),
       (NOW(), 'nickname28', 900, true, 8, 9000, 9, 1, '#hashtag9'),
       (NOW(), 'nickname29', 1000, true, 9, 10000, 10, 2, '#hashtag10'),
       (NOW(), 'nickname30', 100, true, 0, 1000, 1, 0, '#hashtag1');


-- Insert PostLikeCount for all the 300 posts
    INSERT
INTO post_like_count
    (post_id, like_count, created_at, modified_at)
SELECT post_id,
       0,
       NOW(),
       NOW()
FROM post
WHERE post_id BETWEEN 1 AND 15;

UPDATE post_like_count
SET like_count  = CASE
                      WHEN post_id BETWEEN 1 AND 3 THEN FLOOR(RAND() * 10) -- 0~9 (한 자리 숫자)
                      WHEN post_id BETWEEN 4 AND 7 THEN FLOOR(RAND() * 100) -- 0~99 (두 자리 숫자)
                      WHEN post_id BETWEEN 8 AND 17 THEN FLOOR(RAND() * 1000) -- 0~999 (세 자리 숫자)
                      WHEN post_id BETWEEN 18 AND 30 THEN FLOOR(RAND() * 10000) -- 0~9999 (네 자리 숫자)
                      ELSE 0
    END,
    modified_at = NOW()
WHERE post_id BETWEEN 1 AND 30;


-- Insert into Point Table
INSERT INTO point
    (member_id, now_point, created_at, updated_at)
VALUES (1, 200, NOW(), NOW()),
       (2, 200, NOW(), NOW()),
       (3, 300, NOW(), NOW());
SET
foreign_key_checks = 1;
