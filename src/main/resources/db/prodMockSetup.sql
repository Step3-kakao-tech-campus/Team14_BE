SET foreign_key_checks = 0;
TRUNCATE TABLE member;
TRUNCATE TABLE image;
TRUNCATE TABLE post;
TRUNCATE TABLE post_like_count;
TRUNCATE TABLE point;
TRUNCATE TABLE post_insta_count;

-- Member Table
INSERT INTO member (member_id,created_at, insta_id, kakao_id, profile_image_url, total_like, updated_at,
                    user_name, user_status,
                    role)
VALUES (1,NOW(), 'insta1', 'kakao1',
        'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg', 10,
        NOW(), 'user1', 'STATUS_ACTIVE', 'ROLE_BEGINNER'),
       (2,NOW(), 'insta2', 'kakao2',
        'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg', 20,
        NOW(), 'user2', 'STATUS_ACTIVE', 'ROLE_BEGINNER'),
       (3,NOW(), 'insta3', 'kakao3',
        'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg', 30,
        NOW(), 'user3', 'STATUS_INACTIVE', 'ROLE_BEGINNER');

-- Image Table
INSERT INTO image (created_at, image_uri)
VALUES (NOW(), '/images/test.jpg'),
       (NOW(), '/images/1일차.jpg'),
       (NOW(), '/images/2.jpg'),
       (NOW(), '/images/2조단체인생네컷.jpg'),
       (NOW(), '/images/3.jpg'),
       (NOW(), '/images/3조화이팅.jpg'),
       (NOW(), '/images/4.png'),
       (NOW(), '/images/4조인생네컷.jpg'),
       (NOW(), '/images/5.jpg'),
       (NOW(), '/images/6.jpg'),
       (NOW(), '/images/9조.jpg'),
       (NOW(), '/images/10조 마침내!! 즐겁다!! 재밋따!!.jpg'),
       (NOW(), '/images/12조.png'),
       (NOW(), '/images/13조정수리에서불나는중.jpg'),
       (NOW(), '/images/15조의 인생네컷.png'),
       (NOW(), '/images/16조의 인생네컷.jpg'),
       (NOW(), '/images/18조 퀴즈 화이팅 ...!!.jpg'),
       (NOW(), '/images/귀염뽀짝3조.jpg'),
       (NOW(), '/images/멋쟁이 5조.jpg'),
       (NOW(), '/images/세븐틴 최고.jpg'),
       (NOW(), '/images/솔직히 우주 뿌실만큼 귀엽다 ㅇㅈ!.jpg'),
       (NOW(), '/images/웨딩네컷.jpg'),
       (NOW(), '/images/재밋다!즐겁다!인생네컷.jpg'),
       (NOW(), '/images/조장님의단독샷.jpg'),
       (NOW(), '/images/주문하시겠어요 고갱님!.jpg'),
       (NOW(), '/images/최강에이스존예존멋카리스마일조등장이시다.png');

-- Post 테이블 데이터 삽입
INSERT INTO post (created_at, nickname, popularity, published, report_count, view_count, image_id, member_id, hashtag)
VALUES
    (NOW(), 'Ryan', 100, true, 0, 100, 1 % 25 + 1, 1, '춘식이귀여워 카카오짱짱'),
    (NOW(), 'Muzi', 200, true, 0, 200, 2 % 25 + 1, 2, '라이언용감해 카카오프렌즈'),
    (NOW(), 'Apeach', 300, true, 0, 300, 3 % 25 + 1, 3, '무지사랑해 카카오파워'),
    (NOW(), 'Frodo', 400, true, 0, 400, 4 % 25 + 1, 1, '어피치깜찍해 라이언귀여워'),
    (NOW(), 'Neo', 500, true, 0, 500, 5 % 25 + 1, 2, '프로도멋져 네오천재'),
    (NOW(), 'Tube', 600, true, 0, 600, 6 % 25 + 1, 3, '튜브까꿍 카카오최고'),
    (NOW(), 'Jay-G', 700, true, 0, 700, 7 % 25 + 1, 1, '제이지스타일 춘식이최고'),
    (NOW(), 'Con', 800, true, 0, 800, 8 % 25 + 1, 2, '콘의꿈 카카오러블리'),
    (NOW(), 'Chunsik', 900, true, 0, 900, 9 % 25 + 1, 3, '카카오세상 라이언귀여워'),
    (NOW(), 'Ryan', 1000, true, 0, 1000, 10 % 25 + 1, 1, '춘식이귀여워 카카오짱짱'),
    (NOW(), 'Muzi', 1100, true, 0, 1100, 11 % 25 + 1, 2, '라이언용감해 카카오프렌즈'),
    (NOW(), 'Apeach', 1200, true, 0, 1200, 12 % 25 + 1, 3, '무지사랑해 카카오파워'),
    (NOW(), 'Frodo', 1300, true, 0, 1300, 13 % 25 + 1, 1, '어피치깜찍해 라이언귀여워'),
    (NOW(), 'Neo', 1400, true, 0, 1400, 14 % 25 + 1, 2, '프로도멋져 네오천재'),
    (NOW(), 'Tube', 1500, true, 0, 1500, 15 % 25 + 1, 3, '튜브까꿍 카카오최고'),
    (NOW(), 'Jay-G', 1600, true, 0, 1600, 16 % 25 + 1, 1, '제이지스타일 춘식이최고'),
    (NOW(), 'Con', 1700, true, 0, 1700, 17 % 25 + 1, 2, '콘의꿈 카카오러블리'),
    (NOW(), 'Chunsik', 1800, true, 0, 1800, 18 % 25 + 1, 3, '카카오세상 라이언귀여워'),
    (NOW(), 'Ryan', 1900, true, 0, 1900, 19 % 25 + 1, 1, '춘식이귀여워 카카오짱짱'),
    (NOW(), 'Muzi', 2000, true, 0, 2000, 20 % 25 + 1, 2, '라이언용감해 카카오프렌즈'),
    (NOW(), 'Apeach', 2100, true, 0, 2100, 21 % 25 + 1, 3, '무지사랑해 카카오파워'),
    (NOW(), 'Frodo', 2200, true, 0, 2200, 22 % 25 + 1, 1, '어피치깜찍해 라이언귀여워'),
    (NOW(), 'Neo', 2300, true, 0, 2300, 23 % 25 + 1, 2, '프로도멋져 네오천재'),
    (NOW(), 'Tube', 2400, true, 0, 2400, 24 % 25 + 1, 3, '튜브까꿍 카카오최고'),
    (NOW(), 'Jay-G', 2500, true, 0, 2500, 25 % 25 + 1, 1, '제이지스타일 춘식이최고'),
    (NOW(), 'Con', 2600, true, 0, 2600, 26 % 25 + 1, 2, '콘의꿈 카카오러블리'),
    (NOW(), 'Chunsik', 2700, true, 0, 2700, 27 % 25 + 1, 3, '카카오세상 라이언귀여워'),
    (NOW(), 'Ryan', 2800, true, 0, 2800, 28 % 25 + 1, 1, '춘식이귀여워 카카오짱짱'),
    (NOW(), 'Muzi', 2900, true, 0, 2900, 29 % 25 + 1, 2, '라이언용감해 카카오프렌즈'),
    (NOW(), 'Apeach', 3000, true, 0, 3000, 30 % 25 + 1, 3, '무지사랑해 카카오파워');


INSERT INTO post_like_count
    (post_id, like_count, created_at, modified_at)
SELECT post_id,
       0,
       NOW(),
       NOW()
FROM post
WHERE post_id BETWEEN 1 AND 30;

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

INSERT INTO post_insta_count (post_id, member_id, insta_count, created_at, modified_at)
SELECT post_id, member_id, 0, NOW(), NOW()
FROM post
WHERE post_id BETWEEN 1 AND 30;

SET foreign_key_checks = 1;
