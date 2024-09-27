package org.acme;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class RoleRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true");

        // Create role
        rest("/role").post().type(Role.class)
            .to("direct:createRole");

        // Read role by id
        rest("/role/{id}").get().outType(Role.class)
            .to("direct:getRole");

        // Update role by id
        rest("/role/{id}").put().type(Role.class)
            .to("direct:updateRole");

        // Delete role by id
        rest("/role/{id}").delete()
            .to("direct:deleteRole");

        // List all roles
        rest("/roles").get().outType(List.class)
            .to("direct:listRoles");

        // Define routes for CRUD operations

        // Create role route
        from("direct:createRole")
            .process(exchange -> {
                Role role = exchange.getIn().getBody(Role.class);
                role.persist();
                exchange.getIn().setBody(role);
            })
            .setHeader("CamelHttpResponseCode", constant(Response.Status.CREATED.getStatusCode()));

        // Read role by ID route
        from("direct:getRole")
            .process(exchange -> {
                Long id = exchange.getIn().getHeader("id", Long.class);
                Role role = Role.findById(id);
                exchange.getIn().setBody(role);
            });

        // Update role route
        from("direct:updateRole")
            .process(exchange -> {
                Long id = exchange.getIn().getHeader("id", Long.class);
                Role existingRole = Role.findById(id);
                if (existingRole != null) {
                    Role newRole = exchange.getIn().getBody(Role.class);
                    existingRole.name = newRole.name;
                    existingRole.persist();
                    exchange.getIn().setBody(existingRole);
                }
            });

        // Delete role route
        from("direct:deleteRole")
            .process(exchange -> {
                Long id = exchange.getIn().getHeader("id", Long.class);
                Role.deleteById(id);
                exchange.getIn().setBody("Role deleted");
            });

        // List all roles route
        from("direct:listRoles")
            .process(exchange -> {
                List<Role> roles = Role.listAll();  // Get all roles
                exchange.getIn().setBody(roles);
            });
    }
}
