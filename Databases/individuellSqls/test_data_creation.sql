delete from devices
delete from clients
delete from departments
delete from branches
delete from employees

-- insert into employees (id, employee_name, date_of_birth, job, supervisor, entry_date, salary) 
insert into employees values (1, 'Blair McLevie', '1989-11-11', 'CEO', 1, '2010-12-31', 6320)
insert into employees values (2, 'Dorie Simpson', '1987-07-25', 'Senior Programmer', 1, '2014-09-21', 2354)
insert into employees values (3, 'Vannie Lehrian', '1987-05-04', 'Helper', 1, '2017-04-04', 5318)
insert into employees values (4, 'Delphine Legg', '1998-01-04', 'Human Resources Assistant', 1, '2013-02-10', 1585)
insert into employees values (5, 'Silvie Lawland', '1991-06-25', 'Assistant Programmer', 2, '2013-12-06', 1553)
insert into employees values (6, 'Sauncho Storer', '2000-10-17', 'Print Master', 1, '2015-12-15', 1673)
insert into employees values (7, 'Aryn Athow', '1979-06-21', 'Legal Assistant', 1, '2015-02-23', 5412)
insert into employees values (8, 'Christiana Stickens', '1989-10-18', 'Head of Recycling', 1, '2016-10-04', 4425)
insert into employees values (9, 'Read Myers', '1999-02-21', 'IT Specialist', 5, '2015-02-20', 3548)
insert into employees values (10, 'Ingrid Cumbes', '1985-03-16', 'Copy Master', 6, '2015-10-23', 2581)

-- insert into devices (id, category, date_of_acquisition, device_owner)
insert into devices values (1, 'PC', '2021-07-17', 6)
insert into devices values (2, 'PC', '2021-07-17', 1)
insert into devices values (3, 'PC', '2021-07-17', 3)
insert into devices values (4, 'PC', '2021-07-17', 7)
insert into devices values (6, 'Notebook', '2018-03-24', 5)
insert into devices values (7, 'Notebook', '2018-03-24', 2)
insert into devices values (8, 'Notebook', '2018-03-24', 8)
insert into devices values (9, 'Notebook', '2018-03-24', 9)

-- insert into branches (id, name, address, manager)
insert into branches values (1, 'Zentrale', '68 Waubesa Parkway', 2)
insert into branches values (2, 'Copyservice', '3630 Eastlawn Road', 4)
insert into branches values (3, 'Recycling Poechlarn', '796 Utah Circle', 1)
insert into branches values (4, 'Werkstatt Lilienfeld', '0838 Lighthouse Bay Terrace', 3)
insert into branches values (5, 'Haus und Gartenservice Lilienfeld', '1238 Starling Pass', 5)

-- insert into departments (id, name, manager, branch)
insert into departments values (1, 'Administration', 2, 1);
insert into departments values (2, 'IT', 9, 1);
insert into departments values (3, 'Holzverarbeitung', 3, 4);
insert into departments values (4, 'Werbedruck', 6, 2);
insert into departments values (5, 'Rasenmaehen', 7, 5);
insert into departments values (6, 'Verwaltung', 8, 3);

-- insert into clients (id, name, date_of_birth, department)
insert into clients values (1, 'Tabina Ranking', '2003-10-16', 3);
insert into clients values (2, 'Torre Josipovitz', '2001-09-20', 3);
insert into clients values (3, 'Sioux Sworne', '2000-07-31', 4);
insert into clients values (4, 'Charity Maving', '1999-03-01', 4);
insert into clients values (5, 'Georgette Barson', '1998-12-09', 5);
insert into clients values (6, 'Rey Wigan', '2001-06-20', 5);
insert into clients values (7, 'Renaldo Swallwell', '2002-07-12', 4);
insert into clients values (8, 'Kenyon Lennard', '1998-06-01', 2);
insert into clients values (9, 'Arlan Watmough', '1995-02-15', 2);
insert into clients values (10, 'Coleman Champken', '1999-08-30', 5);
