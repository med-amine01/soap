package de.tekup.lasttestsoap.soap;

import org.example.course_details.CourseDetails;
import org.example.course_details.GetCourseDetailsRequest;
import org.example.course_details.GetCourseDetailsResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CourseDetailsEndpoint {

    //input REQUEST
    //output RESPONSE
    //http://www.example.org/course-details
    @PayloadRoot(namespace = "http://www.example.org/course-details", localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse courseEndpoint(@RequestPayload GetCourseDetailsRequest request) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setId(request.getId());
        courseDetails.setName("Soap course");
        courseDetails.setDescription("this is soap course description");

        response.setCourseDetails(courseDetails);
        return response;
    }
}
