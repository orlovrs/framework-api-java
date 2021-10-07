package todolist.requests.auth

import todolist.models.domain.users.User
import todolist.requests.ToDoListApiRequestBase

class SignupApiRequest(user: User) : ToDoListApiRequestBase() {
    override var method = "POST"
    override var url = "/auth/signup"

    init {
        body = hashMapOf(
            "login" to user.login,
            "password" to user.password
        )
    }
}