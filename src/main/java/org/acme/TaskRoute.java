package org.acme;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class TaskRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true");

        rest("/task")
            .post().type(Task.class)
            .to("direct:createTask");

        rest("/task/{id}")
            .get().outType(Task.class)
            .to("direct:getTask");

        rest("/task/{id}")
            .put().type(Task.class)
            .to("direct:updateTask");

        rest("/task/{id}")
            .delete()
            .to("direct:deleteTask");

        rest("/tasks")
            .get().outType(List.class)
            .to("direct:listTasks");

        from("direct:createTask")
            .process(exchange -> {
                Task task = exchange.getIn().getBody(Task.class);
                task.persist();
                exchange.getIn().setBody(task);
            })
            .setHeader("CamelHttpResponseCode", constant(Response.Status.CREATED.getStatusCode()));

        from("direct:getTask")
            .process(exchange -> {
                Long id = exchange.getIn().getHeader("id", Long.class);
                Task task = Task.findById(id);
                exchange.getIn().setBody(task);
            });

        from("direct:updateTask")
            .process(exchange -> {
                Long id = exchange.getIn().getHeader("id", Long.class);
                Task existingTask = Task.findById(id);
                if (existingTask != null) {
                    Task newTask = exchange.getIn().getBody(Task.class);
                    existingTask.title = newTask.title;
                    existingTask.description = newTask.description;
                    existingTask.status = newTask.status;
                    existingTask.duedate = newTask.duedate;
                    existingTask.persist();
                    exchange.getIn().setBody(existingTask);
                }
            });

        from("direct:deleteTask")
            .process(exchange -> {
                Long id = exchange.getIn().getHeader("id", Long.class);
                Task.deleteById(id);
                exchange.getIn().setBody("Task deleted");
            });

        from("direct:listTasks")
            .process(exchange -> {
                List<Task> tasks = Task.listAll();
                exchange.getIn().setBody(tasks);
            });
    }
}
