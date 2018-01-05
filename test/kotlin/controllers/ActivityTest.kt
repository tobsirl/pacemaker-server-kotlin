package kotlin.controllers

import controllers.PacemakerAPI
import models.Activity
import models.Location
import models.User
import org.junit.After
import org.junit.Before
import org.junit.Test


import org.junit.Assert.*
import kotlin.models.Fixtures.locations

class ActivityTest {

    var pacemaker = PacemakerAPI("https://pacific-castle-57153.herokuapp.com/")
    var homer = User("homer", "simpson", "homer@simpson.com", "secret")

    @Before
    fun setup() {
        pacemaker.deleteUsers()
        homer = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testCreateActivity() {
        val (type, location, distance) = Activity("walk", "shop", 2.5F)

        val returnedActivity = pacemaker.createActivity(homer.id, type, location, distance)
        assertEquals(type, returnedActivity!!.type)
        assertEquals(location, returnedActivity.location)
        assertEquals(distance.toDouble(), returnedActivity.distance.toDouble(), 0.001)
        assertNotNull(returnedActivity.id)
    }

    @Test
    fun testGetActivity() {
        val (type, location, distance) = Activity("run", "fridge", 0.5F)
        val returnedActivity1 = pacemaker.createActivity(homer.id, type, location, distance)
        val returnedActivity2 = pacemaker.getActivity(homer.id, returnedActivity1!!.id)
        assertEquals(returnedActivity1, returnedActivity2)
    }

    @Test
    fun testDeleteActivity() {
        val (type, location, distance) = Activity("sprint", "pub", 4.5F)
        var returnedActivity = pacemaker.createActivity(homer.id, type, location, distance)
        assertNotNull(returnedActivity)
        pacemaker.deleteActivities(homer.id)
        returnedActivity = pacemaker.getActivity(homer.id, returnedActivity!!.id)
        assertNull(returnedActivity)
    }

    @Test
    fun testCreateActivityWithSingleLocation() {
        pacemaker.deleteActivities(homer.id)
        val (type, location1, distance) = Activity("walk", "shop", 2.5F)
        val location = Location(12.0, 33.0)

        val returnedActivity = pacemaker.createActivity(homer.id, type, location1, distance)
        pacemaker.addLocation(homer.id, returnedActivity!!.id, location.latitude, location.longitude)

        val locations = pacemaker.getLocations(homer.id, returnedActivity.id)
        assertEquals(locations.size.toLong(), 1)
        assertEquals(locations.get(0), location)
    }

    @Test
    fun testCreateActivityWithMultipleLocation() {
        pacemaker.deleteActivities(homer.id)
        val (type, location, distance) = Activity("walk", "shop", 2.5F)
        val returnedActivity = pacemaker.createActivity(homer.id, type, location, distance)

        locations.forEach { location -> pacemaker.addLocation(homer.id, returnedActivity!!.id, location.latitude, location.longitude) }
        val returnedLocations = pacemaker.getLocations(homer.id, returnedActivity!!.id)
        assertEquals(locations.size, returnedLocations.size)
        assertEquals(locations, returnedLocations)
    }

}