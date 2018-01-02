package controllers

import io.javalin.Javalin

fun main(args: Array<String>) {
    val app = Javalin.create().port(getHerokuAssignedPort()).start()
    val service = PacemakerRestService()
    configRoutes(app, service)
}

private fun getHerokuAssignedPort(): Int {
    val processBuilder = ProcessBuilder()
    return if (processBuilder.environment()["PORT"] != null) {
        Integer.parseInt(processBuilder.environment()["PORT"])
    } else 7000
}

fun configRoutes(app: Javalin, service: PacemakerRestService) {
    app.get("/users") { ctx -> service.listUsers(ctx) }
    //get a specify user
    app.get("/users/:id") { ctx -> service.getUser(ctx) }
    app.post("/users") { ctx -> service.createUser(ctx) }
    app.delete("/users") { ctx -> service.deleteUsers(ctx) }
    //delete a specify user
    app.delete("/users/:id") { ctx -> service.deleteUser(ctx) }
    app.get("/users/:id/activities") { ctx -> service.getActivities(ctx) }
    app.get("/users/:id/activities/:activityId") { ctx -> service.getActivity(ctx) }
    app.post("/users/:id/activities") { ctx -> service.createActivity(ctx) }
    app.delete("/users/:id/activities") { ctx -> service.deleteActivites(ctx) }
    //add a location
    app.post("/users/:id/activities/:activityId/locations") { ctx -> service.addLocation(ctx)}
}

