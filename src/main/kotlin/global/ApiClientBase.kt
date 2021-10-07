package global

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.qameta.allure.Allure
import io.qameta.allure.Step
import khttp.responses.Response

abstract class ApiClientBase {
    abstract var baseUrl: String
    private lateinit var response: Response

    private val mapper = ObjectMapper()

    private fun logRequest(request: ApiRequestBase) {
        val log = "Url: ${request.method} ${baseUrl}${request.url}\n\n" +
                "Headers:\n" +
                request.headers.map { "${it.key}: ${it.value}\n" } +
                "\n" +
                "Body:\n" +
                mapper.writeValueAsString(request.body).prependIndent()

        Allure.addAttachment("Request", log)
        println(log)
    }

    private fun logResponse(response: Response) {
        val log = "Code: ${response.statusCode}\n\n" +
                "Headers:\n" +
                response.headers.map { "${it.key}: ${it.value}\n" } +
                "\n" +
                "Body:\n" +
                response.text

        Allure.addAttachment("Response", log)
        println(log)
    }

    @Step("Выполняем запрос")
    open fun execute(request: ApiRequestBase) {
        logRequest(request)
        response = when (request.method) {
            "GET" -> khttp.get("${baseUrl}${request.url}", request.headers, request.params)
            "POST" -> khttp.post("${baseUrl}${request.url}", request.headers, request.params, data = mapper.writeValueAsString(request.body))
            "PUT" -> khttp.put("${baseUrl}${request.url}", request.headers, request.params, data = mapper.writeValueAsString(request.body))
            "PATCH" -> khttp.patch("${baseUrl}${request.url}", request.headers, request.params, data = mapper.writeValueAsString(request.body))
            "DELETE" -> khttp.delete("${baseUrl}${request.url}", request.headers, request.params)
            else -> khttp.request(request.method,"${baseUrl}${request.url}", request.headers, request.params, data = mapper.writeValueAsString(request.body))
        }
        logResponse(response)
    }

    @Step("Достаем значение по пути {0}")
    fun extract(path: String): String {
        return extractPath(path)!!.asText()
    }

    @Step("Проверяем, что запрос выполнен успешно")
    fun isSuccessful(): Boolean {
        return response.statusCode in 200..299
    }

    fun statusCode(): Int {
        return response.statusCode
    }

    @Step("Проверяем, что в ответе есть нода по пути {0}")
    fun hasNode(path: String): Boolean {
        val node = extractPath(path)
        return node != null
    }

    @Step("Достаем значение по пути {0}")
    private fun extractPath(path: String): JsonNode? {
        var current = mapper.readTree(response.text)
        val parts = path.split(".")
        parts.forEach {
            current = if (it.contains("[") && it.contains("]")) {
                val arrName = it.substringBefore("[")
                val index = it.substringAfter("[")
                    .substringBefore("]")
                    .toInt()
                current.get(arrName)[index]
            } else {
                current.get(it)
            }
        }

        return current
    }

    fun extractAsJsonNode(): JsonNode {
        return mapper.readTree(response.text)
    }
}