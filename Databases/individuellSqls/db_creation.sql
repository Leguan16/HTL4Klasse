USE master
GO

-- looks if the database even exists
if EXISTS (
	SELECT name
	from sys.databases
	where name = 'bernhard_caritas'
)

-- droping and creating the database
DROP DATABASE bernhard_caritas
GO

CREATE DATABASE bernhard_caritas
GO