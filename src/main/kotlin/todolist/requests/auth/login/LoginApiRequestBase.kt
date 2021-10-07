package todolist.requests.auth.login

import todolist.models.domain.users.User
import todolist.requests.ToDoListApiRequestBase

open class LoginApiRequestBase(val user: User) : ToDoListApiRequestBase() {

    override var method = "POST"
    override var url = "/auth/signin"

    init {
        body = hashMapOf(
            "login" to user.login,
            "password" to user.password
        )
    }
}