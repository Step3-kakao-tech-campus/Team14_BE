-- Check if the 'krampoline' database exists and create it if not
CREATE SCHEMA IF NOT EXISTS `krampoline` DEFAULT CHARACTER SET utf8mb4;
USE `krampoline`;

GRANT ALL ON *.* TO 'root'@'localhost' WITH GRANT OPTION;
GRANT ALL
    ON krampoline.* TO 'root'@'localhost';
FLUSH
    PRIVILEGES;

-- image 테이블
CREATE TABLE image
(
    image_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    image_uri  VARCHAR(100)                        NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- member 테이블
CREATE TABLE member
(
    member_id         BIGINT PRIMARY KEY,
    user_name         VARCHAR(50)                                                     NOT NULL,
    kakao_id          VARCHAR(50)                                                     NOT NULL,
    insta_id          VARCHAR(50)                                                     NOT NULL,
    profile_image_url TEXT                                                            NOT NULL,
    role              VARCHAR(50)                                                     NOT NULL,
    total_like        BIGINT                                                          NOT NULL DEFAULT 0,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    user_status       VARCHAR(20)                                                     NOT NULL
);

-- post 테이블
CREATE TABLE post
(
    post_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id    BIGINT,
    image_id     BIGINT,
    nickname     VARCHAR(50)                         NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    published    BOOLEAN                             NOT NULL,
    hashtag      VARCHAR(30),
    view_count   BIGINT,
    popularity   BIGINT,
    report_count INT                                 NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (member_id),
    FOREIGN KEY (image_id) REFERENCES image (image_id)
);

-- post_like 테이블
CREATE TABLE post_like
(
    post_like_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id    BIGINT,
    post_id      BIGINT,
    is_liked     BOOLEAN,
    FOREIGN KEY (member_id) REFERENCES member (member_id),
    FOREIGN KEY (post_id) REFERENCES post (post_id)
);
-- PostLikeCount 테이블 생성
CREATE TABLE post_like_count
(
    post_id     BIGINT                                                          NOT NULL,
    like_count  BIGINT    DEFAULT 0                                             NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (post_id) REFERENCES post (post_id)
);

-- Point 테이블 생성
CREATE TABLE point
(
    member_id  BIGINT                                                             NOT NULL,
    now_point  BIGINT                                                             NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP                                NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (member_id),
    FOREIGN KEY (member_id) REFERENCES member (member_id)
);

-- PointHistory 테이블 생성
CREATE TABLE point_history
(
    point_history_id      BIGINT AUTO_INCREMENT               NOT NULL,
    reciever_id      BIGINT                             NOT NULL,
    sender_id        BIGINT,
    transfer_point   BIGINT,
    transaction_type INTEGER                             NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (point_history_id)
);
