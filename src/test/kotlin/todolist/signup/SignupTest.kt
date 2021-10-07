package todolist.signup

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import todolist.api.ToDoListApiClient
import todolist.models.domain.users.UserManager

class SignupTest {

    private val newUser = UserManager.getUser()
    private val userApiClient = ToDoListApiClient(newUser)

    @Test
    fun `user can be registered`() {
        userApiClient.auth.signUp()

        assertTrue(userApiClient.isSuccessful())
        assertEquals("OK", userApiClient.extract("status"))
    }

    @Test
    fun `user can login after registration`() {
        userApiClient.auth.signUp()

        val oldToken = newUser.token

        Thread.sleep(2000)
        userApiClient.auth.authorize(version = 1)

        assertTrue(userApiClient.isSuccessful())
        assertEquals("OK", userApiClient.extract("status"))
        assertNotEquals(oldToken, newUser.token)
    }

    @Test
    fun `login already exists error`() {
        userApiClient.auth.signUp()

        val anotherUser = UserManager.emptyUser().apply {
            login = newUser.login
            password = newUser.password
        }
        val anotherUserApiClient = ToDoListApiClient(anotherUser)

        anotherUserApiClient.auth.signUp()

        assertFalse(anotherUserApiClient.isSuccessful())
        assertEquals("Bad Request", anotherUserApiClient.extract("status"))
        assertEquals("Username is already taken!", anotherUserApiClient.extract("message"))
    }

    @Test
    fun `empty login error`() {
        newUser.login = ""
        userApiClient.auth.signUp()

        assertFalse(userApiClient.isSuccessful())
        assertEquals("Bad Request", userApiClient.extract("status"))
        assertTrue(userApiClient.hasNode("message"))
    }

    @Test
    fun `empty password error`() {
        newUser.password = ""
        userApiClient.auth.signUp()

        assertFalse(userApiClient.isSuccessful())
        assertEquals("Bad Request", userApiClient.extract("status"))
        assertTrue(userApiClient.hasNode("message"))
    }
}