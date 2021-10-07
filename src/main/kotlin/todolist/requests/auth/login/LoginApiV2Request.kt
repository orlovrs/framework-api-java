package todolist.requests.auth.login

import todolist.models.domain.users.User

class LoginApiV2Request(user: User) : LoginApiRequestBase(user) {
    init {
        headers["version"] = "2"
    }
}