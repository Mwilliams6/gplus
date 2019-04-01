use gplus;

create database gplus;

create table gplus_album_data (

  pk int AUTO_INCREMENT,
  thumbnail_url VARCHAR(200),
  url VARCHAR(200),
  thumbnail_data MEDIUMBLOB,
  title VARCHAR(200),
  primary key(pk)

);

create table gplus_photo_data (

  pk int AUTO_INCREMENT,
  thumbnail_url VARCHAR(200),
  image_url VARCHAR(200),
  thumbnail_data MEDIUMBLOB,
  image_data MEDIUMBLOB,
  image_title VARCHAR(200),
  album int,
  primary key(pk)

);

select count(*) from gplus_album_data;

select * from gplus_album_data;