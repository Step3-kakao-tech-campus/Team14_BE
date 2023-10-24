-- Check if the 'krampoline' database exists and create it if not
CREATE SCHEMA IF NOT EXISTS `krampoline` DEFAULT CHARACTER SET utf8mb4;
USE `krampoline`;



GRANT ALL ON *.* TO 'root'@'localhost' WITH GRANT OPTION;
GRANT ALL
    ON krampoline.* TO 'root'@'localhost';
FLUSH
    PRIVILEGES;


# -- Image 테이블 생성
# CREATE TABLE image
# (
#     imageId   BIGINT(20)   NOT NULL AUTO_INCREMENT,
#     imageUri  VARCHAR(100) NOT NULL,
#     createdAt DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
#     PRIMARY KEY (imageId)
# );
#
# -- Member 테이블 생성
# CREATE TABLE member
# (
#     memberId        BIGINT(20)                               NOT NULL AUTO_INCREMENT,
#     userName        VARCHAR(50)                              NOT NULL,
#     kakaoId         VARCHAR(50)                              NOT NULL,
#     instaId         VARCHAR(50)                              NOT NULL,
#     profileImageUrl VARCHAR(255)                             NOT NULL,
#     role            ENUM ('ROLE_R', 'ROLE_ADMIN')            NOT NULL,
#     totalLike       BIGINT(20)                               NOT NULL DEFAULT 0,
#     createdAt       DATETIME(6)                              NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
#     updatedAt       DATETIME(6)                              NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
#     userStatus      ENUM ('NORMAL', 'BANNED', 'DEACTIVATED') NOT NULL,
#     PRIMARY KEY (memberId)
# );
#
# -- Point 테이블 생성
# CREATE TABLE point
# (
#     memberId  BIGINT(20)  NOT NULL,
#     nowPoint  BIGINT(20)  NOT NULL,
#     createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
#     updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
#     PRIMARY KEY (memberId),
#     FOREIGN KEY (memberId) REFERENCES Member (memberId) ON DELETE CASCADE ON UPDATE CASCADE
# );
#
# -- PointHistory 테이블 생성
# CREATE TABLE point_history
# (
#     recieverId      BIGINT(20)                   NOT NULL AUTO_INCREMENT,
#     senderId        BIGINT(20)                   NOT NULL,
#     transferPoint   BIGINT(20)                   NOT NULL,
#     transactionType ENUM ('TRANSFER', 'RECEIVE') NOT NULL,
#     createdAt       DATETIME(6)                  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
#     PRIMARY KEY (recieverId)
# );
#
# -- Post 테이블 생성
# CREATE TABLE post
# (
#     postId      BIGINT(20)  NOT NULL AUTO_INCREMENT,
#     memberId    BIGINT(20)  NOT NULL,
#     imageId     BIGINT(20),
#     nickname    VARCHAR(50) NOT NULL,
#     createdAt   DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
#     published   BOOLEAN     NOT NULL,
#     hashtag     VARCHAR(30),
#     viewCount   BIGINT(20)           DEFAULT 0,
#     popularity  BIGINT(20)           DEFAULT 0,
#     reportCount INT(11)     NOT NULL DEFAULT 0,
#     PRIMARY KEY (postId),
#     FOREIGN KEY (memberId) REFERENCES Member (memberId) ON DELETE CASCADE ON UPDATE CASCADE,
#     FOREIGN KEY (imageId) REFERENCES Image (imageId) ON DELETE SET NULL ON UPDATE CASCADE
# );
#
# -- PostLike 테이블 생성
# CREATE TABLE post_like
# (
#     postLikeId BIGINT(20) NOT NULL AUTO_INCREMENT,
#     memberId   BIGINT(20) NOT NULL,
#     postId     BIGINT(20) NOT NULL,
#     isLiked    BOOLEAN    NOT NULL,
#     PRIMARY KEY (postLikeId),
#     FOREIGN KEY (memberId) REFERENCES Member (memberId) ON DELETE CASCADE ON UPDATE CASCADE,
#     FOREIGN KEY (postId) REFERENCES Post (postId) ON DELETE CASCADE ON UPDATE CASCADE
# );
#
# -- PostLikeCount 테이블 생성
# CREATE TABLE post_like_count
# (
#     postId     BIGINT(20)  NOT NULL,
#     likeCount  BIGINT(20)  NOT NULL DEFAULT 0,
#     createdAt  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
#     modifiedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
#     PRIMARY KEY (postId),
#     FOREIGN KEY (postId) REFERENCES Post (postId) ON DELETE CASCADE ON UPDATE CASCADE
# );

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
VALUES (NOW(), '/image/test.jpg'),
       (NOW(), '/image/test.jpg'),
       (NOW(), '/image/test.jpg'),
       (NOW(), '/image/test.jpg'),
       (NOW(), '/image/test.jpg'),
       (NOW(), '/image/test.jpg'),
       (NOW(), '/image/test.jpg'),
       (NOW(), '/image/test.jpg'),
       (NOW(), '/image/test.jpg'),
       (NOW(), '/image/test.jpg');

-- Post Table
INSERT INTO post (created_at, nickname, popularity, published, report_count, view_count,
                  image_id, member_id, hashtag)
VALUES (NOW(), 'nickname1', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),

       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname2', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname3', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname4', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname5', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname6', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname7', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname8', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname9', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname10', 1000, true, 9, 10000, 10, 1, '#hashtag10'),
       (NOW(), 'nickname11', 100, true, 0, 1000, 1, 1, '#hashtag1'),
       (NOW(), 'nickname12', 200, true, 1, 2000, 2, 2, '#hashtag2'),
       (NOW(), 'nickname13', 300, false, 2, 3000, 3, 3, '#hashtag3'),
       (NOW(), 'nickname14', 400, true, 3, 4000, 4, 1, '#hashtag4'),
       (NOW(), 'nickname15', 500, false, 4, 5000, 5, 2, '#hashtag5'),
       (NOW(), 'nickname16', 600, true, 5, 6000, 6, 3, '#hashtag6'),
       (NOW(), 'nickname17', 700, false, 6, 7000, 7, 1, '#hashtag7'),
       (NOW(), 'nickname18', 800, true, 7, 8000, 8, 2, '#hashtag8'),
       (NOW(), 'nickname19', 900, false, 8, 9000, 9, 3, '#hashtag9'),
       (NOW(), 'nickname20', 1000, true, 9, 10000, 10, 1, '#hashtag10');


-- Insert PostLikeCount for all the 300 posts
INSERT INTO post_like_count
    (post_id, like_count, created_at, modified_at)
SELECT post_id,
       0,
       NOW(),
       NOW()
FROM post
WHERE post_id BETWEEN 1 AND 300;

-- Insert into Point Table
INSERT INTO point
    (member_id, now_point, created_at, updated_at)
VALUES (1, 200, NOW(), NOW()),
       (2, 200, NOW(), NOW()),
       (3, 300, NOW(), NOW());

-- Update like_count and total_like dynamically
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (99 - 10 + 1)) + 10
WHERE post_id = 1;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (99 - 10 + 1)) + 10
WHERE post_id = 2;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (9999 - 1000 + 1)) + 1000
WHERE post_id = 3;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (99 - 10 + 1)) + 10
WHERE post_id = 4;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (99 - 10 + 1)) + 10
WHERE post_id = 5;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (99 - 10 + 1)) + 10
WHERE post_id = 6;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (9999 - 1000 + 1)) + 1000
WHERE post_id = 7;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (9999 - 1000 + 1)) + 1000
WHERE post_id = 8;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (999 - 100 + 1)) + 100
WHERE post_id = 9;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (99 - 10 + 1)) + 10
WHERE post_id = 10;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (9 - 1 + 1)) + 1
WHERE post_id = 11;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (0 - 0 + 1)) + 0
WHERE post_id = 12;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (9999 - 1000 + 1)) + 1000
WHERE post_id = 13;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (999 - 100 + 1)) + 100
WHERE post_id = 14;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (9 - 1 + 1)) + 1
WHERE post_id = 15;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (9999 - 1000 + 1)) + 1000
WHERE post_id = 16;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (9 - 1 + 1)) + 1
WHERE post_id = 17;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (0 - 0 + 1)) + 0
WHERE post_id = 18;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (9 - 1 + 1)) + 1
WHERE post_id = 19;
UPDATE post_like_count
SET like_count = FLOOR(RAND() * (99 - 10 + 1)) + 10
WHERE post_id = 20;
UPDATE member
SET total_like = FLOOR(RAND() * (999 - 100 + 1)) + 100
WHERE insta_id = 'insta1';
UPDATE member
SET total_like = FLOOR(RAND() * (9999 - 1000 + 1)) + 1000
WHERE insta_id = 'insta2';
UPDATE member
SET total_like = FLOOR(RAND() * (9 - 1 + 1)) + 1
WHERE insta_id = 'insta3';
UPDATE member
SET total_like = FLOOR(RAND() * (9999 - 1000 + 1)) + 1000
WHERE insta_id = 'insta4';
UPDATE member
SET total_like = FLOOR(RAND() * (9 - 1 + 1)) + 1
WHERE insta_id = 'insta5';
UPDATE member
SET total_like = FLOOR(RAND() * (99 - 10 + 1)) + 10
WHERE insta_id = 'insta6';
UPDATE member
SET total_like = FLOOR(RAND() * (9 - 1 + 1)) + 1
WHERE insta_id = 'insta7';
UPDATE member
SET total_like = FLOOR(RAND() * (0 - 0 + 1)) + 0
WHERE insta_id = 'insta8';
UPDATE member
SET total_like = FLOOR(RAND() * (999 - 100 + 1)) + 100
WHERE insta_id = 'insta9';
UPDATE member
SET total_like = FLOOR(RAND() * (99 - 10 + 1)) + 10
WHERE insta_id = 'insta10';

-- Update post_like_count with random likeCount values between 1 and 9999
UPDATE post_like_count
SET likeCount = FLOOR(RAND() * 9999) + 1;

-- Update each row in post_like_count with a unique random likeCount value between 1 and 9999
SET @RAND_SEED := ROUND(RAND(CURTIME()) * 1000);
DELIMITER //
CREATE PROCEDURE UpdateLikeCount()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE p_id BIGINT(20);
    DECLARE cur CURSOR FOR SELECT postId FROM post_like_count;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;

    read_loop:
    LOOP
        FETCH cur INTO p_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        USE `krampoline`;

        SET @RAND_SEED := @RAND_SEED + 1;
        SET @NEW_COUNT := FLOOR(RAND(@RAND_SEED) * 9999) + 1;
        UPDATE post_like_count SET likeCount = @NEW_COUNT WHERE postId = p_id;
    END LOOP;

    CLOSE cur;
END;
//
DELIMITER ;
CALL UpdateLikeCount();
DROP PROCEDURE IF EXISTS UpdateLikeCount;
