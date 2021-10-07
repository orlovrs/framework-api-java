package todolist.models.domain.users

import global.DataGenerator
import io.qameta.allure.Step

object UserManager {

    @Step("Создаем админа")
    fun getAdmin(): User {
        return UserBuilder(User())
            .admin()
            .build()
    }

    @Step("Создаем пользователя")
    fun getUser(): User {
        return UserBuilder(User())
            .setLogin(DataGenerator.getRandomString())
            .setPassword(DataGenerator.getRandomString())
            .build()
    }

    @Step("Создаем пустого пользователя")
    fun emptyUser(): User {
        return UserBuilder(User()).build()
    }
}