-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ssafyweb
-- -----------------------------------------------------
-- DROP SCHEMA IF EXISTS `ssafyweb` ;

-- --------------------------------- --------------------
]
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
(`member_id`,
`email`,
`password`,
`name`,
`address`,
`tel`,
`role`)
VALUES
(1,'w.ga@gmail.com',
'1111',
'관리자구아','싸피 어딘가',
'01096121458',
'ADMIN'),
(2,'k.hr@gmail.com',
'1111',
'관리자혜란','싸피 어딘가',
'01033333333',
'ADMIN');

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
`title`)
VALUES
(1,'집구해 개발진입니다. 서울 12반 정구아 김혜란','[개발진]'),
(1,'마이페이지에서 비밀번호 찾기 기능을 이용해 비밀번호를 변경할 수 있습니다.',
'비밀번호 관련 공지');

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

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- insert into board(userid, subject, content) 
-- values('1', 'Vue Axios 연동', 'Vue를 이용한 HTTP 통신'), 
--       ('1', 'Vue를 배워봅시다', 'Vue와 Spring을 연동하자~'),
--       ('2', '뷰와 스프링부트를 이용한 실전 프로젝트', '프로젝트를 직접만드는 내용.'),
--       ('2', '프론트엔드 프레임워크', 'Vue는 프론트엔드의 인기있는 프레임워크 입니다.');

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

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

commit;