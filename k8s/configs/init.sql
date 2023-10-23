CREATE SCHEMA IF NOT EXISTS `krampoline` DEFAULT CHARACTER SET utf8mb4;

GRANT
    ALL
        ON *.* TO 'root'@'localhost' IDENTIFIED BY 'root' WITH GRANT OPTION;
GRANT ALL
    ON krampoline.* TO 'root'@'localhost';
FLUSH
    PRIVILEGES;

USE
    `krampoline`;

DROP TABLE IF EXISTS `sample_data`;

-- Image 테이블 생성
CREATE TABLE Image
(
    imageId   BIGINT(20)   NOT NULL AUTO_INCREMENT,
    imageUri  VARCHAR(100) NOT NULL,
    createdAt DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (imageId)
);

-- Member 테이블 생성
CREATE TABLE Member
(
    memberId        BIGINT(20)                               NOT NULL AUTO_INCREMENT,
    userName        VARCHAR(50)                              NOT NULL,
    kakaoId         VARCHAR(50)                              NOT NULL,
    instaId         VARCHAR(50)                              NOT NULL,
    profileImageUrl VARCHAR(255)                             NOT NULL,
    role            ENUM ('ROLE_USER', 'ROLE_ADMIN')         NOT NULL,
    totalLike       BIGINT(20)                               NOT NULL DEFAULT 0,
    createdAt       DATETIME(6)                              NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updatedAt       DATETIME(6)                              NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    userStatus      ENUM ('NORMAL', 'BANNED', 'DEACTIVATED') NOT NULL,
    PRIMARY KEY (memberId)
);

-- Point 테이블 생성
CREATE TABLE Point
(
    memberId  BIGINT(20)  NOT NULL,
    nowPoint  BIGINT(20)  NOT NULL,
    createdAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updatedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (memberId),
    FOREIGN KEY (memberId) REFERENCES Member (memberId) ON DELETE CASCADE ON UPDATE CASCADE
);

-- PointHistory 테이블 생성
CREATE TABLE PointHistory
(
    recieverId      BIGINT(20)                   NOT NULL AUTO_INCREMENT,
    senderId        BIGINT(20)                   NOT NULL,
    transferPoint   BIGINT(20)                   NOT NULL,
    transactionType ENUM ('TRANSFER', 'RECEIVE') NOT NULL,
    createdAt       DATETIME(6)                  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (recieverId)
);

-- Post 테이블 생성
CREATE TABLE Post
(
    postId      BIGINT(20)  NOT NULL AUTO_INCREMENT,
    memberId    BIGINT(20)  NOT NULL,
    imageId     BIGINT(20),
    nickname    VARCHAR(50) NOT NULL,
    createdAt   DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    published   BOOLEAN     NOT NULL,
    hashtag     VARCHAR(30),
    viewCount   BIGINT(20)           DEFAULT 0,
    popularity  BIGINT(20)           DEFAULT 0,
    reportCount INT(11)     NOT NULL DEFAULT 0,
    PRIMARY KEY (postId),
    FOREIGN KEY (memberId) REFERENCES Member (memberId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (imageId) REFERENCES Image (imageId) ON DELETE SET NULL ON UPDATE CASCADE
);

-- PostLike 테이블 생성
CREATE TABLE PostLike
(
    postLikeId BIGINT(20) NOT NULL AUTO_INCREMENT,
    memberId   BIGINT(20) NOT NULL,
    postId     BIGINT(20) NOT NULL,
    isLiked    BOOLEAN    NOT NULL,
    PRIMARY KEY (postLikeId),
    FOREIGN KEY (memberId) REFERENCES Member (memberId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (postId) REFERENCES Post (postId) ON DELETE CASCADE ON UPDATE CASCADE
);

-- PostLikeCount 테이블 생성
CREATE TABLE PostLikeCount
(
    postId     BIGINT(20)  NOT NULL,
    likeCount  BIGINT(20)  NOT NULL DEFAULT 0,
    createdAt  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    modifiedAt DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (postId),
    FOREIGN KEY (postId) REFERENCES Post (postId) ON DELETE CASCADE ON UPDATE CASCADE
);
