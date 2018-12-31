drop table if exists CELL_DIM;
create table CELL_DIM (
	cell_key INTEGER PRIMARY KEY,
	carrier int,
	name varchar(256),
	sector int
);

drop table if exists CUSTOMER_DIM;
create table CUSTOMER_DIM (
	customer_key int PRIMARY KEY,
	mdn varchar(256),
	name varchar(256),
	region varchar(256),
	state varchar(256),
	pre_paid boolean
);

drop table if exists DEVICE_DIM;
create table DEVICE_DIM (
	device_key INTEGER PRIMARY KEY,
	vendor varchar(256),
	model varchar(256),
	marketing_name varchar(256),
	device_os varchar(256),
	device_os_version varchar(256),
	volte boolean
);

drop table if exists DISPOSITION_DIM;
create table DISPOSITION_DIM (
	disposition_key INTEGER PRIMARY KEY,
	sip_code int,
	code_name varchar(256),
	outcome varchar(256),
	description varchar(256),
	failure_due_to_client boolean,
	failure_due_to_server boolean
);

drop table if exists LTE_F;
create table LTE_F (
	id INTEGER PRIMARY KEY,
	customer_key int,
	device_key int,
	disposition_key int,
	record_date datetime,
	start_cell_key int,
	end_cell_key int,
	sinr double,
	rsrp double,
	dropped_call boolean
);