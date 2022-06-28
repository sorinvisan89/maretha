-- demo.contents definition

CREATE TABLE `contents` (
  `content_name` varchar(255) NOT NULL,
  PRIMARY KEY (`content_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- demo.countries definition

CREATE TABLE `countries` (
  `country_name` varchar(255) NOT NULL,
  PRIMARY KEY (`country_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- demo.prerolls definition

CREATE TABLE `prerolls` (
  `preroll_name` varchar(255) NOT NULL,
  PRIMARY KEY (`preroll_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- demo.videos definition

CREATE TABLE `videos` (
  `video_name` varchar(255) NOT NULL,
  `aspect_ratio` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`video_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- demo.content_prerolls definition

CREATE TABLE `content_prerolls` (
  `content_id` varchar(255) NOT NULL,
  `preroll_id` varchar(255) NOT NULL,
  KEY `FKjahpk5cg2ieox33aah78d423t` (`preroll_id`),
  KEY `FKljjb1d8t88w10pgfae6wjt44j` (`content_id`),
  CONSTRAINT `FKjahpk5cg2ieox33aah78d423t` FOREIGN KEY (`preroll_id`) REFERENCES `prerolls` (`preroll_name`),
  CONSTRAINT `FKljjb1d8t88w10pgfae6wjt44j` FOREIGN KEY (`content_id`) REFERENCES `contents` (`content_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- demo.content_videos definition

CREATE TABLE `content_videos` (
  `content_id` varchar(255) NOT NULL,
  `video_id` varchar(255) NOT NULL,
  KEY `FKkcp9sgf60s7gkkqu5ald7jb7e` (`video_id`),
  KEY `FK6ioibv9cp8r75v0lg1xqgokkf` (`content_id`),
  CONSTRAINT `FK6ioibv9cp8r75v0lg1xqgokkf` FOREIGN KEY (`content_id`) REFERENCES `contents` (`content_name`),
  CONSTRAINT `FKkcp9sgf60s7gkkqu5ald7jb7e` FOREIGN KEY (`video_id`) REFERENCES `videos` (`video_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- demo.preroll_videos definition

CREATE TABLE `preroll_videos` (
  `content_id` varchar(255) NOT NULL,
  `video_id` varchar(255) NOT NULL,
  KEY `FKjytgbxtn7h9bul7m2qqhjr2lp` (`video_id`),
  KEY `FKi62uca7m091owm6ou356pgfy0` (`content_id`),
  CONSTRAINT `FKi62uca7m091owm6ou356pgfy0` FOREIGN KEY (`content_id`) REFERENCES `prerolls` (`preroll_name`),
  CONSTRAINT `FKjytgbxtn7h9bul7m2qqhjr2lp` FOREIGN KEY (`video_id`) REFERENCES `videos` (`video_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- demo.videos_countries definition

CREATE TABLE `videos_countries` (
  `video_id` varchar(255) NOT NULL,
  `country_id` varchar(255) NOT NULL,
  KEY `FK20d5ro0n0fp4doj1jcp9hnuf9` (`country_id`),
  KEY `FKo4doyp5miir8kkmyq9fo2dv9t` (`video_id`),
  CONSTRAINT `FK20d5ro0n0fp4doj1jcp9hnuf9` FOREIGN KEY (`country_id`) REFERENCES `countries` (`country_name`),
  CONSTRAINT `FKo4doyp5miir8kkmyq9fo2dv9t` FOREIGN KEY (`video_id`) REFERENCES `videos` (`video_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;