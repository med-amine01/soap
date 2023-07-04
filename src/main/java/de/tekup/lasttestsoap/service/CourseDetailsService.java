package de.tekup.lasttestsoap.service;

import de.tekup.lasttestsoap.bean.Course;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseDetailsService {
    public enum Code {
        SUCCESS, FAILURE;
    }
    private static List<Course> courses = new ArrayList<>();

    static {
        Course course1 = new Course(1,"SpringBoot", "learn spring boot");
        courses.add(course1);

        Course course2 = new Course(2,".Net", "learn .Net");
        courses.add(course2);

        Course course3 = new Course(3,"NodeJs", "learn NodeJs");
        courses.add(course3);
    }

    public Course findById(int id) {
        for(Course c : courses) {
            if(c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public List<Course> findAll() {
        return courses;
    }

    public Code deleteById(int id) {
        Course c = findById(id);
        if(c != null) {
            courses.remove(c);
            return Code.SUCCESS;
        }
        return Code.FAILURE;
    }
}
