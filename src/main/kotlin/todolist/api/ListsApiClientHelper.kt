package todolist.api

import io.qameta.allure.Step
import todolist.models.domain.lists.List
import todolist.requests.lists.CreateListApiRequest
import todolist.requests.lists.GetListApiRequest

class ListsApiClientHelper(private val client: ToDoListApiClient) {

    @Step("Создаем лист")
    fun create(list: List): ToDoListApiClient {
        client.execute(CreateListApiRequest(list))
        list.id = client.extract("data.id").toInt()

        return client
    }

    @Step("Получаем лист с id = {list.id}")
    fun get(list: List): ToDoListApiClient {
        client.execute(GetListApiRequest(list))
        return client
    }
}