package todolist.api

import global.ApiClientBase
import global.ApiRequestBase
import todolist.models.domain.users.User

class ToDoListApiClient(val user: User) : ApiClientBase() {
    val auth = AuthApiClientHelper(this)
    val lists = ListsApiClientHelper(this)

    override var baseUrl = "https://orlovrs-todolist.herokuapp.com/api"

    override fun execute(request: ApiRequestBase) {
        authorizeRequest(request)
        super.execute(request)
    }

    private fun authorizeRequest(request: ApiRequestBase) {
        if (user.token != null)
            request.headers["Authorization"] = "Bearer ${user.token}"
    }
}