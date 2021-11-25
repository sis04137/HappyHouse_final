-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ssafyweb
-- -----------------------------------------------------
-- DROP SCHEMA IF EXISTS `ssafyweb` ;

-- --------------------------------- --------------------
-- Schema ssafyweb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafyweb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafyweb` ;


DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `member` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` VARCHAR(16) NULL DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `tel` varchar(30) DEFAULT NULL,
  `picture` text DEFAULT NULL,
  `role` varchar(30) DEFAULT 'ROLE_MEMBER',
  PRIMARY KEY (`member_id`)
) 
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `ssafyweb`.`member`
(
`email`,
`password`,
`name`,
`address`,
`tel`,
`role`)
VALUES
('w.ga@gmail.com',
'1111',
'관리자구아','싸피 어딘가',
'01096121458',
'ADMIN'),
('k.hr@gmail.com',
'1111',
'관리자혜란','싸피 어딘가',
'01033333333',
'ADMIN');
INSERT INTO `ssafyweb`.`member` (`email`, `password`, `name`, `address`, `tel`, `role`) VALUES ('member11@goohye.com', 'member11', '선량한이용자', '대전시', '01055551122', 'MEMBER');
INSERT INTO `ssafyweb`.`member` (`email`, `password`, `name`, `address`, `role`) VALUES ('parkszin@goohye.com', 'parkszin', '박상진 교수님', '서울시', 'MEMBER');


-- -----------------------------------------------------
-- Table `ssafyweb`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyweb`.`board` ;

CREATE TABLE IF NOT EXISTS `ssafyweb`.`board` (
  `board_id` INT NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL DEFAULT 1,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `created` datetime null,
  `modified` datetime null,
  PRIMARY KEY (`board_id`),
  INDEX `board_to_member_fk` (`member_id` ASC) VISIBLE,
  CONSTRAINT `board_to_member_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafyweb`.`member` (`member_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `ssafyweb`.`board`
(`member_id`,
`content`,
`title`,
`hit`,
`created`,
`modified`)
VALUES
(1,'집구해 개발진입니다. 서울 12반 정구아 김혜란','[개발진]', 23, now(), now()),
(2,'이미 존재하는 이메일이라는 알림이 뜬다면 비밀번호 찾기 기능을 활용해주세요.
본인의 계정이 아니라면 QnA 게시판으로 문의 부탁드립니다.','같은 이메일로 중복 아이디를 생성하실 수 없습니다.', 42,  now(), now()),
(1,'마이페이지에서 비밀번호 찾기 기능을 이용해 비밀번호를 변경할 수 있습니다.',
'비밀번호 관련 공지', 31, now(), now()),
(4,'사이트 잘 만드셨네요
허허허',
'사이트 점검 왔습니다.', 1023, now(), now());

-- -----------------------------------------------------
-- Table `ssafyweb`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyweb`.`comment` ;

DROP TABLE IF EXISTS comment;
CREATE TABLE IF NOT EXISTS `ssafyweb`.`comment` (
  comment_id int NOT NULL AUTO_INCREMENT,
  member_id int NOT NULL NULL DEFAULT NULL,
  comment varchar(500) NOT NULL,
  `created` datetime null,
  `modified` datetime null,
  board_id int NOT NULL,
  PRIMARY KEY (comment_id),
  KEY comment_board_id_idx (board_id),
  CONSTRAINT comment_board_id FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE
) ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `ssafyweb`.`comment`
(
`member_id`,
`comment`,
`created`,
`modified`,
`board_id`)
VALUES
(
2,
'교수님, 감사합니다!!',
now(),
now(),
4);
INSERT INTO `ssafyweb`.`comment`
(
`member_id`,
`comment`,
`created`,
`modified`,
`board_id`)
VALUES
(
1,
'감동...',
now(),
now(),
4);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


DROP TABLE IF EXISTS fav;
CREATE TABLE IF NOT EXISTS `ssafyweb`.`fav` (
  fav_id int NOT NULL AUTO_INCREMENT,
  member_id int NOT NULL NULL DEFAULT NULL,
  apt_id varchar(20) NOT NULL,
  PRIMARY KEY (fav_id)
) ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `ssafyweb`.`fav`
(
`member_id`,
`apt_id`)
VALUES
(
1,
8564);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

commit;