drop table if exists devices
drop table if exists clients
drop table if exists departments
drop table if exists branches
drop table if exists employees

create table employees (
	id				        int not null primary key, -- number of employee
	employee_name	        varchar(255) not null,
	date_of_birth	        date not null,
	job				        varchar(255),
	supervisor		        int,
	entry_date		        date not null,
	salary			        int	,
	constraint FK_supervisor foreign key (supervisor) references employees(id)
)


create table devices (
	id				        int not null primary key,
	category		        varchar(255) not null, -- at the moment pc or laptop
	date_of_acquisition		date not null, -- when did the company buy the device
	device_owner	        int, -- current owner of the device
	constraint FK_device_owner foreign key (device_owner) references employees(id)
)

create table branches (
	id				        int not null primary key,
	name			        varchar(255),
	address			        varchar(255), -- address of branch
	manager			        int, -- current manager of branch
	constraint FK_branch_manager foreign key (manager) references employees(id)
)

create table departments (
	id				        int not null primary key,
	name			        varchar(255),
	manager			        int, -- current manager of department
	branch			        int, -- in which branch is the department located
	constraint FK_department_manager foreign key (manager) references employees(id),
	constraint FK_branch foreign key (branch) references branches(id) on delete cascade
)

create table clients (
	id int not null primary key,
	name varchar(255),
	date_of_birth date,
	department int,
	constraint FK_department foreign key (department) references departments(id)
)

