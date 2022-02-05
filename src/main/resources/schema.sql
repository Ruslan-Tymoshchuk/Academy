DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS audiences CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS holidays CASCADE;
DROP TABLE IF EXISTS lessons CASCADE;
DROP TABLE IF EXISTS lessons_times CASCADE;
DROP TABLE IF EXISTS lessons_groups CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS teachers CASCADE;
DROP TABLE IF EXISTS vacations CASCADE;
DROP TABLE IF EXISTS teachers_courses CASCADE;
   
CREATE TABLE addresses (
    address_id SERIAL PRIMARY KEY,
    country VARCHAR NOT NULL,
    region VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    street VARCHAR NOT NULL,
    houseNumber VARCHAR NOT NULL,
    postalCode VARCHAR NOT NULL
);

CREATE TABLE audiences (
    audience_id SERIAL PRIMARY KEY,
    number INTEGER NOT NULL,
    capacity INTEGER NOT NULL
);

CREATE TABLE courses (   
    course_id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR
);

CREATE TABLE groups (
    group_id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE holidays (
    holiday_id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    date DATE
);

CREATE TABLE teachers (
    teacher_id SERIAL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    gender VARCHAR NOT NULL,
    birthDate DATE,
    phone VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    address_id INTEGER REFERENCES addresses(address_id),
    academicDegree VARCHAR NOT NULL
);

CREATE TABLE lessons_times (
    lesson_time_id SERIAL PRIMARY KEY,
    starttime TIME,
    endtime TIME
);

CREATE TABLE lessons (
    lesson_id SERIAL PRIMARY KEY,
    teacher_id INTEGER REFERENCES teachers(teacher_id),
    course_id INTEGER REFERENCES courses(course_id),
    audience_id INTEGER REFERENCES audiences(audience_id),
    date DATE,
    lesson_time_id INTEGER REFERENCES lessons_times(lesson_time_id)
);

CREATE TABLE lessons_groups (
    lesson_id INTEGER REFERENCES lessons(lesson_id),
    group_id INTEGER REFERENCES groups(group_id), 
    UNIQUE (lesson_id, group_id)
);

CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    gender VARCHAR NOT NULL,
    birthDate DATE,
    phone VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    address_id INTEGER REFERENCES addresses(address_id),
    group_id INTEGER REFERENCES groups(group_id)
);

CREATE TABLE teachers_courses(
    teacher_id INTEGER REFERENCES teachers(teacher_id),
    course_id INTEGER REFERENCES courses(course_id),
    UNIQUE (teacher_id, course_id)
);

CREATE TABLE vacations (
    vacation_id SERIAL PRIMARY KEY,
    teacher_id INTEGER REFERENCES teachers(teacher_id) ON DELETE CASCADE ON UPDATE CASCADE,
    startdate DATE,
    enddate DATE
);