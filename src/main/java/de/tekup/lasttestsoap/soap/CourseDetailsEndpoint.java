package de.tekup.lasttestsoap.soap;

import de.tekup.lasttestsoap.bean.Course;
import de.tekup.lasttestsoap.service.CourseDetailsService;
import de.tekup.lasttestsoap.soap.exception.CourseNotFoundException;
import org.example.course_details.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {

    @Autowired
    private CourseDetailsService courseDetailsService;

    //input REQUEST
    //output RESPONSE
    //http://www.example.org/course-details
    @PayloadRoot(namespace = "http://www.example.org/course-details", localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse courseEndpoint(@RequestPayload GetCourseDetailsRequest request) {

        Course course = courseDetailsService.findById(request.getId());
        if(course == null) {
            throw new CourseNotFoundException("Invalid Id " + request.getId());
        }
        return mapCourse(course);
    }

    private GetCourseDetailsResponse mapCourse(Course course) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        CourseDetails courseDetails = mapCourseDetails(course);
        response.setCourseDetails(courseDetails);

        return response;
    }

    private CourseDetails mapCourseDetails(Course course) {
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        courseDetails.setDescription(course.getDescription());

        return courseDetails;
    }

    @PayloadRoot(namespace = "http://www.example.org/course-details", localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse allCoursesEndPoint(@RequestPayload GetAllCourseDetailsRequest request) {
        List<Course> courses = courseDetailsService.findAll();

        return mapAllDetailsCourses(courses);
    }

    private GetAllCourseDetailsResponse mapAllDetailsCourses(List<Course> courses) {
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();

        for(Course c : courses) {
            CourseDetails cd = mapCourseDetails(c);
            response.getCourseDetails().add(cd);
        }
        return response;
    }


    @PayloadRoot(namespace = "http://www.example.org/course-details", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse deleteCoursesEndPoint(@RequestPayload DeleteCourseDetailsRequest request) {
        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
        CourseDetailsService.Code code = courseDetailsService.deleteById(request.getId());
        response.setCode(mapCode(code));

        return response;
    }

    private Code mapCode(CourseDetailsService.Code code) {
        if(code.name().equals("SUCCESS")) {
            return Code.SUCCESS;
        }
        return Code.FAILURE;
    }

}
