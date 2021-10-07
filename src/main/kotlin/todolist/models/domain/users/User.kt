package todolist.models.domain.users

import todolist.config.Config
import todolist.models.domain.lists.List

class User {
    var token: String? = null
    var login = Config.adminLogin
    var password = Config.adminPassword

    val lists = arrayListOf<List>()
}