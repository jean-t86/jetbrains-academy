package connectfour

const val MIN_GAMES = 1

class ConnectFour(private val players: Players, private val board: Board) {

    private var numberOfGames: Int = MIN_GAMES

    init {
        println("Connect Four")
        players.init()
        board.init()
        getNumberOfGames()

        println("${players.player1.name} VS ${players.player2.name}")
        println("${board.rows} x ${board.columns} board")
        println(if (numberOfGames == MIN_GAMES) "Single game" else "Total $numberOfGames games")

        startGame()
    }

    private fun startGame() {
        gameLoop@ for (i in 1..numberOfGames) {
            if (numberOfGames > MIN_GAMES) println("Game #$i")
            board.draw()

            var hasWon: Boolean
            var isDraw: Boolean
            var player: Player
            do {
                player = players.nextPlayer()
                do {
                    println("${player.name}'s turn:")
                    val input = readLine()!!
                    if (input == "end") break@gameLoop
                    val column = input.toIntOrNull()
                    val validPlay = board.play(column, player.disc)
                } while (!validPlay)

                board.draw()

                hasWon = board.hasWon(player.disc)
                isDraw = board.isDraw()
            } while (!hasWon && !isDraw)

            if (hasWon) {
                println("Player ${player.name} won")
                player.score += 2
            }

            if (isDraw) {
                println("It is a draw")
                players.apply {
                    player1.score++
                    player2.score++
                }
            }

            if (numberOfGames > MIN_GAMES) {
                println(
                    """
                    Score
                    ${players.player1.name}: ${players.player1.score} ${players.player2.name}: ${players.player2.score}
                """.trimIndent()
                )
            }

            board.clear()
        }
        println("Game over!")
    }

    private fun getNumberOfGames() {
        var validNumberOfGames: Boolean
        do {
            println(
                """
                Do you want to play single or multiple games?
                For a single game, input 1 or press Enter
                Input a number of games:
            """.trimIndent()
            )
            validNumberOfGames = try {
                val input = readLine()!!

                if (input.isEmpty()) {
                    numberOfGames = 1
                    break
                }

                numberOfGames = input.toInt()
                if (numberOfGames <= 0) {
                    throw Exception("Number of games must be greater than zero.")
                } else {
                    true
                }
            } catch (e: Exception) {
                println("Invalid input")
                false
            }
        } while (!validNumberOfGames)
    }
}