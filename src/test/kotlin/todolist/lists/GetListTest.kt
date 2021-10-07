package todolist.lists

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import todolist.api.ToDoListApiClient
import todolist.models.domain.lists.ListManager
import todolist.models.domain.users.User
import todolist.models.domain.users.UserManager

class GetListTest {

    private lateinit var user: User
    private lateinit var adminApiClient: ToDoListApiClient

    @BeforeEach
    fun setup() {
        user = UserManager.getAdmin()
        adminApiClient = ToDoListApiClient(user)
    }

    @Test
    fun `user can get own lists`() {
        val list = ListManager.getPublic()
        user.lists.add(list)

        adminApiClient.auth.authorize()
            .lists.create(user.lists[0])
            .lists.get(user.lists[0])

        assertTrue(adminApiClient.isSuccessful())
        assertEquals(200, adminApiClient.statusCode())
        assertEquals(list.id, adminApiClient.extract("data.id").toInt())
        assertEquals(list.name, adminApiClient.extract("data.name"))
        assertEquals(list.isPublic, adminApiClient.extract("data.isPublic").toBoolean())
        assertEquals(list.items[0].name, adminApiClient.extract("data.items[0].name"))
    }
}