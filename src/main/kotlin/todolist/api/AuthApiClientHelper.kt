package todolist.api

import io.qameta.allure.Step
import todolist.requests.auth.login.LoginApiRequestBase
import todolist.requests.auth.SignupApiRequest
import todolist.requests.auth.login.LoginApiRequestFactory

class AuthApiClientHelper(private val client: ToDoListApiClient) {

    lateinit var loginRequest: LoginApiRequestBase

    @Step("Авторизуем пользователя, версия запроса - {0}")
    fun authorize(version: Int = 2): ToDoListApiClient {
        loginRequest = LoginApiRequestFactory(client.user).get(version)

        this.client.execute(loginRequest)

        if (client.isSuccessful()) {
            client.user.token = client.extract("data.token")
        }

        return client
    }

    @Step("Регистрируем пользователя")
    fun signUp(): ToDoListApiClient {
        client.execute(SignupApiRequest(client.user))

        if (client.isSuccessful()) {
            client.user.token = client.extract("data.token")
        }

        return client
    }
}