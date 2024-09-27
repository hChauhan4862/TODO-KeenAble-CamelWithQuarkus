// package org.acme;

// import java.util.List;

// import jakarta.ws.rs.Consumes;
// import jakarta.ws.rs.DELETE;
// import jakarta.ws.rs.GET;
// import jakarta.ws.rs.POST;
// import jakarta.ws.rs.PUT;
// import jakarta.ws.rs.Path;
// import jakarta.ws.rs.PathParam;
// import jakarta.ws.rs.Produces;
// import jakarta.ws.rs.core.MediaType;

// @Path("/api/student")
// @Produces(MediaType.APPLICATION_JSON)
// @Consumes(MediaType.APPLICATION_JSON)
// public class StudentResource {

//     @POST
//     public Student createStudent(Student student) {
//         student.persist();
//         return student;
//     }

//     @GET
//     @Path("/{id}")
//     public Student getStudent(@PathParam("id") Long id) {
//         return Student.findById(id);
//     }

//     @PUT
//     @Path("/{id}")
//     public Student updateStudent(@PathParam("id") Long id, Student student) {
//         Student existingStudent = Student.findById(id);
//         if (existingStudent != null) {
//             existingStudent.name = student.name;
//             existingStudent.age = student.age;
//             existingStudent.course = student.course;
//             existingStudent.email = student.email;
//             existingStudent.persist();
//             return existingStudent;
//         }
//         return null;
//     }

//     @DELETE
//     @Path("/{id}")
//     public String deleteStudent(@PathParam("id") Long id) {
//         Student.deleteById(id);
//         return "Student deleted";
//     }

//     @GET
//     @Path("/all")
//     public List<Student> listAllStudents() {
//         return Student.listAll();
//     }
// }



package org.acme;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/student")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    @POST
    @Transactional
    public Student createStudent(Student student) {
        // Ensure the student is new (no ID)
        if (student.isPersistent()) {
            // If the student is persistent, use merge instead of persist
            student = student.getEntityManager().merge(student);
        } else {
            student.persist();
        }
        return student;
    }

    @GET
    @Path("/{id}")
    public Student getStudent(@PathParam("id") Long id) {
        return Student.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Student updateStudent(@PathParam("id") Long id, Student student) {
        Student existingStudent = Student.findById(id);
        if (existingStudent != null) {
            existingStudent.name = student.name;
            existingStudent.age = student.age;
            existingStudent.course = student.course;
            existingStudent.email = student.email;
            existingStudent.persist();
            return existingStudent;
        }
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public String deleteStudent(@PathParam("id") Long id) {
        Student.deleteById(id);
        return "Student deleted";
    }

    @GET
    @Path("/all")
    public List<Student> listAllStudents() {
        return Student.listAll();
    }
}
