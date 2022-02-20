package ua.com.rtim.academy.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
import ua.com.rtim.academy.dao.mapper.AddressMapper;
import ua.com.rtim.academy.dao.mapper.AudienceMapper;
import ua.com.rtim.academy.dao.mapper.CourseMapper;
import ua.com.rtim.academy.dao.mapper.GroupMapper;
import ua.com.rtim.academy.dao.mapper.HolidayMapper;
import ua.com.rtim.academy.dao.mapper.LessonMapper;
import ua.com.rtim.academy.dao.mapper.LessonTimeMapper;
import ua.com.rtim.academy.dao.mapper.StudentMapper;
import ua.com.rtim.academy.dao.mapper.TeacherMapper;
import ua.com.rtim.academy.dao.mapper.VacationMapper;

@Configuration
@PropertySource("classpath:db.properties")
@Import({ AddressDao.class, AddressMapper.class, AudienceDao.class, AudienceMapper.class, CourseDao.class,
        CourseMapper.class, GroupDao.class, GroupMapper.class, HolidayDao.class, HolidayMapper.class, LessonDao.class,
        LessonMapper.class, LessonTimeDao.class, LessonTimeMapper.class, StudentDao.class, StudentMapper.class,
        TeacherDao.class, TeacherMapper.class, VacationDao.class, VacationMapper.class })
public class AppConfig {

    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String userName;
    @Value("${db.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}