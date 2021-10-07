package todolist.models.domain.lists

import global.DataGenerator
import io.qameta.allure.Step

object ListManager {

    @Step("Подготавливаем публичный лист")
    fun getPublic(): List {
        return List().apply {
            name = DataGenerator.getRandomString()
            isPublic = true
            items = arrayListOf(ListItem().apply {
                name = DataGenerator.getRandomString()
                checked = false
            })
        }
    }
}