package de.tekup.lasttestsoap.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

//enable spring web services
@EnableWs
//Spring config
@Configuration
public class WebServiceConfig {

    //MessageDispatcherServlet : identify endpoint, handle requests
        //application Context
        //url : /ws/*


    //ServletRegistrationBean maps servlet to url
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {

        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        //mapping
        return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
    }

    //WSDL config
    //Expose wsdl definition based on our schema
    //url : /ws/courses.wsdl
    @Bean(name = "courses")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema courseSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CoursePort");
        definition.setTargetNamespace("http://www.example.org/course-details");
        definition.setLocationUri("/ws");
        definition.setSchema(courseSchema);

        return definition;
    }

    //we will use schema and generate wsdl using spring webservice (courses.wsdl)
    @Bean
    public XsdSchema courseSchema() {
        // this takes path
        return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
    }
}
