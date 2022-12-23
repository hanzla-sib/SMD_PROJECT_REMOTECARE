

CREATE TABLE user(
	  id INT NOT NULL AUTO_INCREMENT,  
  	name VARCHAR(64),
  	email VARCHAR(128) UNIQUE,
  	password VARCHAR(255),
    user_type VARCHAR(1),
    gender VARCHAR(10),
    imageurl text NULL,
  	PRIMARY KEY(id)
);

CREATE TABLE Daily_steps(
	id INT NOT NULL AUTO_INCREMENT,  
  	Demail VARCHAR(128),
    steps_daily int,
  	PRIMARY KEY(id)
);

CREATE TABLE Weekly_steps(
	id INT NOT NULL AUTO_INCREMENT,  
  	Memail VARCHAR(128),
    date_log date,
    steps_monthly int,
  	PRIMARY KEY(id)
);

CREATE TABLE Test_record(
	id INT NOT NULL AUTO_INCREMENT,  
  	Temail VARCHAR(128),
    imageurl text NULL,
	details VARCHAR(1000),
  	PRIMARY KEY(id)
);


CREATE TABLE Patient_Appointment (
	p_name VARCHAR(128),
	p_email VARCHAR(128),
	appoint_status VARCHAR(128),
	d_name VARCHAR(128),
	d_email VARCHAR(128),
	PRIMARY KEY(p_email,d_email)
);

CREATE TABLE Doctor_Appointment (
	d_name VARCHAR(128),
	d_email VARCHAR(128),
	appoint_status VARCHAR(128),
	p_name VARCHAR(128),
	p_email VARCHAR(128),
	PRIMARY KEY(d_email,p_email)
);


CREATE TABLE Consumed_Calories (
    id INT NOT NULL AUTO_INCREMENT,  
	p_email VARCHAR(128),
    date_log date,
	Calories INT NOT NULL,
	PRIMARY KEY(id)
);
