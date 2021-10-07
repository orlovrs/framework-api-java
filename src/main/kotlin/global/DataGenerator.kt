package global

import kotlin.random.Random

object DataGenerator {

    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun getRandomString(len: Int = 10): String {
        return (1..len)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun getRandomNumber(start: Int = 0, end: Int = Int.MAX_VALUE): Int {
        return Random.nextInt(start, end)
    }
}