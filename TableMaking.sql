use everytime;

CREATE TABLE sell #게시판 테이블
(	sell_no	INT auto_increment primary key,
	title		VARCHAR(100),
	content	VARCHAR(2000),
    photo	VARCHAR(2000),
    photo_url	VARCHAR(2000),
    price	int,
    buyornot	VARCHAR(1),
    finornot	VARCHAR(1),
	write_date	timestamp DEFAULT current_timestamp,
    update_date	timestamp,
	write_id		VARCHAR(50)
);
alter table sell add view int default 0;

select * from sell;

CREATE TABLE sell_comment #댓글 테이블
(	
	id INT auto_increment primary key,
    sell_no_sc INT,
	write_id_sc VARCHAR(50),
	content_sc	VARCHAR(2000),
	write_date_sc	timestamp DEFAULT current_timestamp,
    update_date_sc	timestamp
);

CREATE TABLE User #계정 저장 테이블
(	
	id INT auto_increment primary key,
	username VARCHAR(200) unique,
	password	VARCHAR(200),
    email varchar(200) unique,
	register_date	timestamp DEFAULT current_timestamp
);
