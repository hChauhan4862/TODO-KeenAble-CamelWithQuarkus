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

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    @Transactional
    public User createUser(User user) {
        user.persist();
        return user;
    }

    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") Long id) {
        return User.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public User updateUser(@PathParam("id") Long id, User user) {
        User existingUser = User.findById(id);
        if (existingUser != null) {
            existingUser.email = user.email;
            existingUser.password = user.password;
            existingUser.username = user.username;
            existingUser.firstName = user.firstName;
            existingUser.lastName = user.lastName;
            existingUser.persist();
            return existingUser;
        }
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public String deleteUser(@PathParam("id") Long id) {
        User.deleteById(id);
        return "User deleted";
    }

    @GET
    @Path("/all")
    public List<User> listAllUsers() {
        return User.listAll();
    }
}
