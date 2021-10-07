package todolist.models.domain.users

import todolist.config.Config

class UserBuilder(private var user: User) {

    fun admin(): UserBuilder {
        user.apply {
            login = Config.adminLogin
            password = Config.adminPassword
        }

        return this
    }

    fun setLogin(login: String): UserBuilder {
        user.login = login
        return this
    }

    fun setPassword(pass: String): UserBuilder {
        user.password = pass
        return this
    }

    fun build(): User {
        return user
    }
}