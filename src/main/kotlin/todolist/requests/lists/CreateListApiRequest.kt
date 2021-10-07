package todolist.requests.lists

import todolist.requests.ToDoListApiRequestBase
import todolist.models.domain.lists.List

class CreateListApiRequest(private val list: List) : ToDoListApiRequestBase() {
    override var method = "POST"
    override var url = "/lists"

    init {
        body = hashMapOf(
            "name" to list.name,
            "isPublic" to list.isPublic,
            "items" to list.items
        )
    }
}