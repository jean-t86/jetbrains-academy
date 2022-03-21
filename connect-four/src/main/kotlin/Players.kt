package connectfour

const val CIRCLE_DISC = "o"
const val STAR_DISC = "*"

class Player(val disc: String) {
    val name = readLine()!!
    var score = 0
}

class Players {

    lateinit var player1: Player
    lateinit var player2: Player

    private var count = 0

    fun init() {
        println("First player's name:")
        player1 = Player(CIRCLE_DISC)
        println("Second player's name:")
        player2 = Player(STAR_DISC)
    }

    fun nextPlayer(): Player {
        val player = if (count % 2 == 0) {
            player1
        } else {
            player2
        }
        count++
        return player
    }
}