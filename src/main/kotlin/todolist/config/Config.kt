package todolist.config

import io.github.cdimascio.dotenv.Dotenv

object Config {

    private val dotenv = Dotenv.load()
    val adminLogin: String = dotenv.get("adminLogin")
    val adminPassword: String = dotenv.get("adminPassword")
}