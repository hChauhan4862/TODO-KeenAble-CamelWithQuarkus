package org.acme;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class StudentRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true");

        // Create student
        rest("/student").post().type(Student.class)
            .to("direct:createStudent");

        // Read student by id
        rest("/student/{id}").get().outType(Student.class)
            .to("direct:getStudent");

        // Update student by id
        rest("/student/{id}").put().type(Student.class)
            .to("direct:updateStudent");

        // Delete student by id
        rest("/student/{id}").delete()
            .to("direct:deleteStudent");

        // List all students
        rest("/students").get().outType(List.class)  // Return a list of students
            .to("direct:listStudents");

        // Define routes for CRUD operations

        // Create student route
        from("direct:createStudent")
            .process(exchange -> {
                Student student = exchange.getIn().getBody(Student.class);
                student.persist();
                exchange.getIn().setBody(student);
            })
            .setHeader("CamelHttpResponseCode", constant(Response.Status.CREATED.getStatusCode()));

        // Read student by ID route
        from("direct:getStudent")
            .process(exchange -> {
                Long id = exchange.getIn().getHeader("id", Long.class);
                Student student = Student.findById(id);
                exchange.getIn().setBody(student);
            });

        // Update student route
        from("direct:updateStudent")
            .process(exchange -> {
                Long id = exchange.getIn().getHeader("id", Long.class);
                Student student = Student.findById(id);
                if (student != null) {
                    Student newStudent = exchange.getIn().getBody(Student.class);
                    student.name = newStudent.name;
                    student.age = newStudent.age;
                    student.course = newStudent.course;
                    student.email = newStudent.email;
                    student.persist();
                    exchange.getIn().setBody(student);
                }
            });

        // Delete student route
        from("direct:deleteStudent")
            .process(exchange -> {
                Long id = exchange.getIn().getHeader("id", Long.class);
                Student.deleteById(id);
                exchange.getIn().setBody("Student deleted");
            });

        // List all students route
        from("direct:listStudents")
            .process(exchange -> {
                List<Student> students = Student.listAll();  // Get all students
                exchange.getIn().setBody(students);
            });
    }
}
