package todolist.models.domain.lists

class List {
    var id: Int = 0
    var name: String? = null
    var isPublic: Boolean = false
    var items: MutableList<ListItem> = ArrayList()
}