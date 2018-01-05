package controllers

import models.Activity
import models.Location
import models.Message
import models.User
import java.util.*

class PacemakerAPI {

    var userIndex = hashMapOf<String, User>()
    var emailIndex = hashMapOf<String, User>()
    var activitiesIndex = hashMapOf<String, Activity>()
    var users = userIndex.values
    var friendList = hashMapOf<String, User>()
    var friends = friendList.values
    var messageList = hashMapOf<String, Message>()
    var messages = messageList.values

    fun createUser(firstName: String, lastName: String, email: String, password: String): User {
        var user = User(firstName, lastName, email, password)
        userIndex[user.id] = user
        emailIndex[user.email] = user
        return user
    }

    fun deleteUsers() {
        userIndex.clear();
        emailIndex.clear()
    }

    fun getUser(id: String) = userIndex[id]

    fun getUserByEmail(email: String) = emailIndex[email]

    fun createActivity(id: String, type: String, location: String, distance: Float): Activity? {
        var activity:Activity? = null
        var user = userIndex.get(id)
        if (user != null) {
            activity = Activity(type, location, distance)
            user.activities[activity.id] = activity
            activitiesIndex[activity.id] = activity;
        }
        return activity;
    }

    fun getActivity(id: String): Activity? {
        return activitiesIndex[id]
    }

    fun deleteActivities(id: String) {
        require(userIndex[id] != null)
        var user = userIndex[id]
        if (user != null) {
            for ((u, activity) in user.activities) {
                activitiesIndex.remove(activity.id)
            }
            user.activities.clear();
        }
    }

    fun deleteUser(id: String) {
        require(userIndex[id] != null)
        var user = userIndex.get(id)
        if (user != null) {
            userIndex.remove(id)
        }
    }

    fun addLocation(id: String, latitude: Double, longitude: Double): Location? {
        var location:Location? = null
        var user = userIndex.get(id)
        var activity = activitiesIndex.get(id)
        if (user != null && activity != null) {
            location = Location(latitude, longitude)
            user.activities[activity.id] = activity
            activitiesIndex[activity.id] = activity;
        }
        return location;
    }

    //get locations
    fun getLocations(id: String, id1: String): Location? {
        val location = null
        return location
    }

    //friends
    fun addFriend(email: String) {
        //var friendList:User? = null
        var user = userIndex[email]
        if (user != null) {
            var friend = friendList[email]
            friendList.put(user.email, friend!!)
        }
    }

    fun unfollowFriend(email: String) {
        var user = userIndex.get(email)
        if (user != null) {
            var friend = friendList.get(email)
            friendList.remove(user.email, friend!!)
        }
    }

    fun getFriendsList(email: String): MutableCollection<User> {
        var user = userIndex[email]
        var listOfFriends = friends
        if (user != null && listOfFriends != null) {
            listOfFriends.addAll(users)
        }
        return listOfFriends
    }

    //Messages
    fun getMessages(email: String): MutableCollection<Message>  {
        var message = messageList.get(email)
        var listOfMessages = messages
        if (message != null) {
            listOfMessages.addAll(messages)
        }
        return listOfMessages
    }

    fun messageFriend(email: String, message: Message): Message? {

    }
}


