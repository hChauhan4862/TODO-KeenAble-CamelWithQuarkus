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

@Path("/api/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoleResource {

    @POST
    @Transactional
    public Role createRole(Role role) {
        role.persist();
        return role;
    }

    @GET
    @Path("/{id}")
    public Role getRole(@PathParam("id") Long id) {
        return Role.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Role updateRole(@PathParam("id") Long id, Role role) {
        Role existingRole = Role.findById(id);
        if (existingRole != null) {
            existingRole.name = role.name;
            existingRole.persist();
            return existingRole;
        }
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public String deleteRole(@PathParam("id") Long id) {
        Role.deleteById(id);
        return "Role deleted";
    }

    @GET
    @Path("/all")
    public List<Role> listAllRoles() {
        return Role.listAll();
    }
}
