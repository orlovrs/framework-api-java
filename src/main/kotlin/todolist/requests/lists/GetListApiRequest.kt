package todolist.requests.lists

import todolist.requests.ToDoListApiRequestBase
import todolist.models.domain.lists.List

class GetListApiRequest(private val list: List) : ToDoListApiRequestBase() {
    override var method = "GET"
    override var url = "/lists/${list.id}"
}