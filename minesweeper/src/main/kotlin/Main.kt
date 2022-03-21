package minesweeper

enum class Action {
    FREE, MINE
}

fun main() {
    print("How many mines do you want on the field? ")
    val mineField = MineField(readLine()!!.toInt())
    mineField.printMinefield()

    do {
        print("Set/unset mines marks or claim a cell as free: ")
        val userInput = readLine()!!.split(" ")
        val (y, x) = userInput.take(2).map { it.toInt() - 1 }
        when (userInput.last().let { Action.valueOf(it.uppercase()) }) {
            Action.FREE -> {
                if (!mineField.exploreCell(x, y)) {
                    mineField.printMinefield(true)
                    println("You stepped on a mine and failed!")
                    break
                } else {
                    mineField.printMinefield()
                }
            }
            Action.MINE -> {
                if (mineField.placeMine(x, y)) {
                    mineField.printMinefield()
                } else {
                    println("There is a number here!")
                }
            }
        }
    } while (!mineField.hasWon())

    if (mineField.hasWon()) {
        println("Congratulations! You found all the mines!")
    }
}
