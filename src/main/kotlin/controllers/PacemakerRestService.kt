package controllers

import io.javalin.Context
import models.Activity
import models.Location
import models.Message
import models.User

class PacemakerRestService  {
    val pacemaker = PacemakerAPI()

    fun listUsers(ctx: Context) {
        ctx.json(pacemaker.users)
    }

    //get a specify user
    fun getUser(ctx: Context) {
        val userId: String? = ctx.param("userId")
        val user = pacemaker.getUser(userId!!)
        if (user != null) {
            ctx.json(user)
        } else {
            ctx.status(404)
        }
    }

    fun createUser(ctx: Context) {
        val user = ctx.bodyAsClass(User::class.java)
        val newUser = pacemaker.createUser(user.firstname, user.lastname, user.email, user.password)
        ctx.json(newUser)
    }

    fun deleteUsers(ctx: Context) {
        pacemaker.deleteUsers()
        ctx.status(200)
    }

    fun getActivity(ctx: Context) {
        // val userId: String? = ctx.param("id")
        val activityId: String? = ctx.param("activityId")
        val activity = pacemaker.getActivity(activityId!!)
        if (activity != null) {
            ctx.json(activity)
        } else {
            ctx.status(404)
        }
    }

    fun getActivities(ctx: Context) {
        val id: String? =  ctx.param("id")
        val user = pacemaker.getUser(id!!)
        if (user != null) {
            ctx.json(user.activities)
        } else {
            ctx.status(404)
        }
    }

    fun createActivity(ctx: Context) {
        val id: String? =  ctx.param("id")
        val user = pacemaker.getUser(id!!)
        if (user != null) {
            val activity = ctx.bodyAsClass(Activity::class.java)
            val newActivity = pacemaker.createActivity(user.id, activity.type, activity.location, activity.distance)
            ctx.json(newActivity!!)
        } else {
            ctx.status(404)
        }
    }

    fun deleteActivites(ctx: Context) {
        val id: String? =  ctx.param("id")
        pacemaker.deleteActivities(id!!);
        ctx.status(204)
    }

    //delete a specify user
    fun deleteUser(ctx: Context) {
        val id: String? = ctx.param("id")
        pacemaker.deleteUser(id!!)
        ctx.status(204)
    }

    //add location
    fun addLocation(ctx: Context) {
        val id: String? = ctx.param("id")
        val user = pacemaker.getUser(id!!)
        val activity = pacemaker.getActivity(id)
        if (user != null && activity != null) {
            val location = ctx.bodyAsClass(Location::class.java)
            activity.route.add(location)
            ctx.json(activity)
        } else {
            ctx.status(404)
        }

    }
    //getlocations

    fun getLocations(ctx: Context) {
        val id: String? = ctx.param("id")
        val activity = pacemaker.getActivity(id!!)
        if (activity != null) {
            ctx.json(activity.route)
        } else {
            ctx.status(404)
        }
    }

    //Friend commands
    fun addFriend(ctx: Context) {
        val email: String? = ctx.param("email")
        if (email != null) {
            pacemaker.addFriend(email!!)
            ctx.json(email)
        } else {
            ctx.status(404)
        }
    }

    fun unfollowFriend(ctx: Context) {
        val email: String? = ctx.param("email")
        if (email != null) {
            pacemaker.unfollowFriend(email!!)
            ctx.json(email)
        } else {
            ctx.status(404)
        }
    }

    fun getFriendsList(ctx: Context) {
        val email: String? = ctx.param("email")
        if (email != null) {
            val friendsList = pacemaker.getFriendsList(email)
            ctx.json(friendsList)
        } else {
            ctx.status(404)
        }
    }

    //messages
    fun getMessages(ctx: Context) {
        val email: String? = ctx.param("email")
        if (email != null) {
            val messages = pacemaker.getMessages(email)
            ctx.json(messages!!)
        } else {
            ctx.status(404)
        }

    }

    fun messageFriend(ctx: Context) {
        val email: String? = ctx.param("email")
        if (email != null) {
            val message = ctx.bodyAsClass(Message::class.java)
            val messageForFriend = pacemaker.messageFriend(email, message)
            ctx.json(messageForFriend!!)
        } else {
            ctx.status(404)
        }
    }
}