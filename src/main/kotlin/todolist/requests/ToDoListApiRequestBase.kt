package todolist.requests

import global.ApiRequestBase

abstract class ToDoListApiRequestBase : ApiRequestBase() {

    override var headers: MutableMap<String, String> = hashMapOf(
        "Content-Type" to "application/json"
    )
}