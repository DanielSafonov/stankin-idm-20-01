java -jar project.jar ru.stankin.project.ProjectApplication

psql postgres
create role dbuser with login password 'password';
create database project;
grant all privileges on database project to dbuser;
\c project
grant all on all tables in schema public to dbuser

create table if not exists data (
    id SERIAL PRIMARY KEY,
    model VARCHAR(64) NOT NULL,
    details VARCHAR(256),
    year VARCHAR(4) 
);

insert into data(model, details, year) values
('Jaguar F-Type', '2 doors coupe', '2013'),
('Jaguar XJ', '4 doors sedan', '1968'),
('Jaguar XF', '4 doors sedan, 5 door wagon', '2008'),
('Jaguar F-Pace', '5 doors suv', '2016'),
('Jaguar F-Pace', '5 doors suv, first suv of jaguar', '2016'),
('Jaguar E-Pace', '5 doors suv, e-car', '2017');