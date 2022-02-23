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
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    country VARCHAR NOT NULL,
    region VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    street VARCHAR NOT NULL,
    house_number VARCHAR NOT NULL,
    postal_code VARCHAR NOT NULL
);

CREATE TABLE audiences (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    number INTEGER NOT NULL,
    capacity INTEGER NOT NULL
);

CREATE TABLE courses (   
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR
);

CREATE TABLE groups (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE holidays (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL,
    date DATE
);

CREATE TABLE teachers (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    gender VARCHAR NOT NULL,
    birth_date DATE,
    phone VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    address_id INTEGER REFERENCES addresses(id),
    academic_degree VARCHAR NOT NULL
);

CREATE TABLE lessons_times (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    start_time TIME,
    end_time TIME
);

CREATE TABLE lessons (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    teacher_id INTEGER REFERENCES teachers(id),
    course_id INTEGER REFERENCES courses(id),
    audience_id INTEGER REFERENCES audiences(id),
    date DATE,
    lesson_time_id INTEGER REFERENCES lessons_times(id)
);

CREATE TABLE lessons_groups (
    lesson_id INTEGER NOT NULL REFERENCES lessons(id) ON DELETE CASCADE ON UPDATE CASCADE,
    group_id INTEGER NOT NULL REFERENCES groups(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE(lesson_id, group_id)
);

CREATE TABLE students (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    gender VARCHAR NOT NULL,
    birth_date DATE,
    phone VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    address_id INTEGER REFERENCES addresses(id),
    group_id INTEGER REFERENCES groups(id)
);

CREATE TABLE teachers_courses(
    teacher_id INTEGER NOT NULL REFERENCES teachers(id) ON UPDATE CASCADE,
    course_id INTEGER NOT NULL REFERENCES courses(id) ON UPDATE CASCADE,
    UNIQUE(teacher_id, course_id)
);

CREATE TABLE vacations (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    teacher_id INTEGER REFERENCES teachers(id) ON DELETE CASCADE ON UPDATE CASCADE,
    start_date DATE,
    end_date DATE
);