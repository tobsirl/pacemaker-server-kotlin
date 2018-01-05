package kotlin.controllers

import controllers.PacemakerAPI
import junit.framework.Assert.assertEquals
import models.User
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.models.Fixtures.users



class UserTest {

    var pacemaker = PacemakerAPI("https://pacific-castle-57153.herokuapp.com/")
    var homer = User("homer", "simpson", "homer@simpson.com", "secret")

    @Before
    fun setup() {
        pacemaker.deleteUsers()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testCreateUser() {
        val user = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password)
        assertEquals(user, homer)
        val user2 = pacemaker.getUserByEmail(homer.email)
        assertEquals(user2, homer)
    }

    @Test
    fun testCreateUsers() {
        users.forEach { user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password) }
        val returnedUsers = pacemaker.users
        assertEquals(users.size, returnedUsers.size)
    }
}