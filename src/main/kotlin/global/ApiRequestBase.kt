package global

abstract class ApiRequestBase {
    abstract var url: String
    open lateinit var method: String
    open lateinit var headers: MutableMap<String, String>

    var params: MutableMap<String, String> = hashMapOf()
    var body: Any? = null
}