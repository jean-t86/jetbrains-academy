package connectfour

const val DEFAULT_ROWS = 6
const val DEFAULT_COLS = 7

class Board {

    var rows: Int = DEFAULT_ROWS
    var columns: Int = DEFAULT_COLS

    private val board = mutableListOf<MutableList<String>>()

    fun init() {
        readBoardDimensions()
        for (i in 1..columns) {
            board.add(mutableListOf())
        }
    }

    private fun readBoardDimensions() {
        var boardDimensions = mutableListOf<Int>()
        var validBoard: Boolean

        do {
            println(
                """
            Set the board dimensions (Rows x Columns)
            Press Enter for default (6 x 7)
        """.trimIndent()
            )
            try {
                val dimensionsInput = readLine()!!

                if (dimensionsInput.isEmpty()) {
                    boardDimensions.add(rows)
                    boardDimensions.add(columns)
                    break
                } else {
                    boardDimensions = dimensionsInput
                        .lowercase()
                        .split("x")
                        .map(String::trim)
                        .map(String::toInt)
                        .toMutableList()
                }

                validBoard = if (boardDimensions[0] !in 5..9) {
                    println("Board rows should be from 5 to 9")
                    false
                } else if (boardDimensions[1] !in 5..9) {
                    println("Board columns should be from 5 to 9")
                    false
                } else {
                    true
                }
            } catch (e: Exception) {
                println("Invalid input")
                validBoard = false
            }
        } while (!validBoard)

        rows = boardDimensions[0]
        columns = boardDimensions[1]
    }

    fun draw() {
        for (i in 1..columns) {
            print(" $i")
        }
        println()

        for (i in rows - 1 downTo 0) {
            for (j in 0..columns) {
                print("║")
                val elementAt = board.elementAtOrNull(j)?.elementAtOrNull(i)
                if (elementAt != null) {
                    print(elementAt)
                } else {
                    print(" ")
                }
            }
            println()
        }

        for (i in 1..columns + 1) {
            when (i) {
                1 -> print("╚")
                columns + 1 -> print("═╝")
                else -> print("═╩")
            }
        }
        println()
    }

    fun play(column: Int?, disc: String): Boolean {
        if (column == null) {
            println("Incorrect column number")
            return false
        } else if (column !in 1..columns) {
            println("The column number is out of range (1 - $columns)")
            return false
        } else if (board[column - 1].size == rows) {
            println("Column $column is full")
            return false
        }

        board[column - 1].add(disc)

        return true
    }

    fun hasWon(disc: String): Boolean {
        val horizontalDiscs = mutableListOf<String>()
        for (i in 0..board.lastIndex) {
            if (board[i].joinToString("").contains(disc.repeat(4))) return true

            horizontalDiscs.clear()
            for (j in 0..columns) {
                val element = board.elementAtOrNull(j)?.elementAtOrNull(i)
                if (element != null && element == disc) {
                    horizontalDiscs.add(element)
                    if (horizontalDiscs.size == 4) return true
                } else {
                    horizontalDiscs.clear()
                }

                var element2 = board.elementAtOrNull(j + 1)?.elementAtOrNull(i + 1)
                var element3 = board.elementAtOrNull(j + 2)?.elementAtOrNull(i + 2)
                var element4 = board.elementAtOrNull(j + 3)?.elementAtOrNull(i + 3)
                if (element != null && element2 != null && element3 != null && element4 != null &&
                    element == disc && element2 == disc && element3 == disc && element4 == disc
                ) {
                    return true
                }

                element2 = board.elementAtOrNull(j - 1)?.elementAtOrNull(i + 1)
                element3 = board.elementAtOrNull(j - 2)?.elementAtOrNull(i + 2)
                element4 = board.elementAtOrNull(j - 3)?.elementAtOrNull(i + 3)
                if (element != null && element2 != null && element3 != null && element4 != null &&
                    element == disc && element2 == disc && element3 == disc && element4 == disc
                ) {
                    return true
                }
            }
        }

        return false
    }

    fun isDraw(): Boolean {
        for (i in 0 until rows) {
            if (board[i].size != columns) return false
        }
        return true
    }

    fun clear() {
        for (i in 0 until columns) {
            board[i].clear()
        }
    }
}