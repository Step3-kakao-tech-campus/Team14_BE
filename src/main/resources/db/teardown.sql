SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE member;
TRUNCATE TABLE image;
TRUNCATE TABLE post;

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
INSERT INTO post (created_at, nickname, popularity, published, report_count, university, view_count, post_point,
                  image_id, member_id, hashtag)
VALUES (NOW(), 'nickname1', 100, true, 0, 'university1', 1000, 400, 1, 1, '#hashtag1'),
       (NOW(), 'nickname2', 200, true, 1, 'university2', 2000, 400,2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 'university3', 3000, 400,3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 'university4', 4000, 400,4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 'university5', 5000, 400,5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 'university6', 6000, 400,6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 'university7', 7000, 400,7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 'university8', 8000, 400,8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 'university9', 9000, 400,9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 'university10', 10000, 400,10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 'university1', 1000, 400,1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 'university2', 2000, 400,2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 'university3', 3000, 400,3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 'university4', 4000, 400,4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 'university5', 5000, 400,5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 'university6', 6000, 400,6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 'university7', 7000, 400,7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 'university8', 8000, 400,8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 'university9', 9000, 400,9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 'university10', 10000, 400,10, 1, '#hashtag10');

-- PostLikeCount Table
INSERT INTO post_like_count (post_id, like_count, created_at, modified_at)
VALUES (1, 0, NOW(), NOW()),
       (2, 0, NOW(), NOW()),
       (3, 0, NOW(), NOW()),
       (4, 0, NOW(), NOW()),
       (5, 0, NOW(), NOW()),
       (6, 0, NOW(), NOW()),
       (7, 0, NOW(), NOW()),
       (8, 0, NOW(), NOW()),
       (9, 0, NOW(), NOW()),
       (10, 0, NOW(), NOW()),
       (11, 0, NOW(), NOW()),
       (12, 0, NOW(), NOW()),
       (13, 0, NOW(), NOW()),
       (14, 0, NOW(), NOW()),
       (15, 0, NOW(), NOW()),
       (16, 0, NOW(), NOW()),
       (17, 0, NOW(), NOW()),
       (18, 0, NOW(), NOW()),
       (19, 0, NOW(), NOW()),
       (20, 0, NOW(), NOW());
