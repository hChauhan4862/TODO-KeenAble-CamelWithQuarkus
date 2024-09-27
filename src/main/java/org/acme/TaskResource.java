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

@Path("/api/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @POST
    @Transactional
    public Task createTask(Task task) {
        task.persist();
        return task;
    }

    @GET
    @Path("/{id}")
    public Task getTask(@PathParam("id") Long id) {
        return Task.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Task updateTask(@PathParam("id") Long id, Task task) {
        Task existingTask = Task.findById(id);
        if (existingTask != null) {
            existingTask.title = task.title;
            existingTask.description = task.description;
            existingTask.status = task.status;
            existingTask.duedate = task.duedate;
            existingTask.persist();
            return existingTask;
        }
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public String deleteTask(@PathParam("id") Long id) {
        Task.deleteById(id);
        return "Task deleted";
    }

    @GET
    @Path("/all")
    public List<Task> listAllTasks() {
        return Task.listAll();
    }
}
