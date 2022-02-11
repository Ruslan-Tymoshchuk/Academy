package ua.com.rtim.academy.ui;

import static java.lang.System.lineSeparator;

import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.com.rtim.academy.config.AppConfig;
import ua.com.rtim.academy.dao.AddressDao;
import ua.com.rtim.academy.dao.AudienceDao;
import ua.com.rtim.academy.dao.CourseDao;
import ua.com.rtim.academy.dao.GroupDao;
import ua.com.rtim.academy.dao.HolidayDao;
import ua.com.rtim.academy.dao.LessonDao;
import ua.com.rtim.academy.dao.LessonTimeDao;
import ua.com.rtim.academy.dao.StudentDao;
import ua.com.rtim.academy.dao.TeacherDao;
import ua.com.rtim.academy.dao.VacationDao;

public class Main {

    public static void main(String[] args) {
        System.out.println("a: Audience" + lineSeparator() + "b: Course" + lineSeparator() + "c: Group"
                + lineSeparator() + "d: Holiday" + lineSeparator() + "e: Lesson" + lineSeparator() + "f: Student"
                + lineSeparator() + "g: Teacher"
        + lineSeparator() + "h: Vacation");
        try (Scanner scanner = new Scanner(System.in);
                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            AudienceDao audienceDao = context.getBean(AudienceDao.class);
            CourseDao courseDao = context.getBean(CourseDao.class);
            GroupDao groupDao = context.getBean(GroupDao.class);
            HolidayDao holidayDao = context.getBean(HolidayDao.class);
            LessonDao lessonDao = context.getBean(LessonDao.class);
            TeacherDao teacherDao = context.getBean(TeacherDao.class);
            LessonTimeDao lessonTimeDao = context.getBean(LessonTimeDao.class);
            StudentDao studentDao = context.getBean(StudentDao.class);
            AddressDao addressDao = context.getBean(AddressDao.class);
            VacationDao vacationDao = context.getBean(VacationDao.class);
            while (scanner.hasNext()) {
                switch (scanner.next()) {
                case "a":
                    new AudienceMenuItem(audienceDao, scanner);
                    break;
                case "b":
                    new CourseMenuItem(courseDao, scanner);
                    break;
                case "c":
                    new GroupMenuItem(groupDao, scanner);
                    break;
                case "d":
                    new HolidayMenuItem(holidayDao, scanner);
                    break;
                case "e":
                    new LessonMenuItem(lessonDao, teacherDao, courseDao, audienceDao, lessonTimeDao, groupDao, scanner);
                    break;
                case "f":
                    new StudentMenuItem(studentDao, addressDao, groupDao, scanner);
                    break;
                case "g":
                    new TeacherMenuItem(teacherDao, addressDao, courseDao, scanner);
                    break;
                case "h":
                    new VacationMenuItem(vacationDao, teacherDao, scanner);
                    break;
                default:
                    break;
                }
            }
        }
    }
}