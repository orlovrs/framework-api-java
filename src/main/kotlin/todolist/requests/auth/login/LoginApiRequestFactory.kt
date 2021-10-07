package todolist.requests.auth.login

import todolist.models.domain.users.User

class LoginApiRequestFactory(val user: User) {
    fun get(version: Int): LoginApiRequestBase {
        return when (version) {
            2 -> LoginApiV2Request(user)
            else -> LoginApiV1Request(user)
        }
    }
}