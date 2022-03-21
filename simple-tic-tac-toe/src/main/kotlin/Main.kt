package tictactoe

import kotlin.math.abs

const val EMPTY_CELL = " "
const val PLAYER_1 = 'X'
const val PLAYER_2 = 'O'

fun main() {
    val grid = EMPTY_CELL.repeat(9).toCharArray()
    printGrid(grid)

    var row1 = grid.slice(0..2).joinToString("")
    var row2 = grid.slice(3..5).joinToString("")
    var row3 = grid.slice(6..8).joinToString("")

    var count = 1
    while (!isDraw(row1, row2, row3) &&
        !hasWon(PLAYER_1, row1, row2, row3) &&
        !hasWon(PLAYER_2, row1, row2, row3)
    ) {
        print("Enter the coordinates: ")
        var coordinates = readLine()!!
        while (!validCoordinates(coordinates, grid)) {
            print("Enter the coordinates: ")
            coordinates = readLine()!!
        }

        val (x, y) = coordinates.split(" ").map(String::toInt)
        val index = getGridIndexForCoordinates(x, y)
        grid[index] = if (count % 2 == 1) PLAYER_1 else PLAYER_2
        printGrid(grid)

        count++

        row1 = grid.slice(0..2).joinToString("")
        row2 = grid.slice(3..5).joinToString("")
        row3 = grid.slice(6..8).joinToString("")
    }

    println(
        when {
            isDraw(row1, row2, row3) -> "Draw"
            hasWon('X', row1, row2, row3) -> "X wins"
            hasWon('O', row1, row2, row3) -> "O wins"
            else -> ""
        }
    )
}

private fun printGrid(grid: CharArray) {
    val row1 = grid.slice(0..2)
    val row2 = grid.slice(3..5)
    val row3 = grid.slice(6..8)

    println("---------")
    println("| ${row1[0]} ${row1[1]} ${row1[2]} |")
    println("| ${row2[0]} ${row2[1]} ${row2[2]} |")
    println("| ${row3[0]} ${row3[1]} ${row3[2]} |")
    println("---------")
}

fun validCoordinates(coordinates: String, grid: CharArray): Boolean {
    val xy = coordinates.split(" ")
    if (xy.size != 2 ||
        xy[0].toIntOrNull() == null ||
        xy[1].toIntOrNull() == null
    ) {
        println("You should enter numbers!")
        return false
    }

    val x = xy[0].toInt()
    val y = xy[1].toInt()
    if (x !in 1..3 || y !in 1..3) {
        println("Coordinates should be from 1 to 3!")
        return false
    }

    val index = getGridIndexForCoordinates(x, y)
    if (grid[index] != EMPTY_CELL.toCharArray()[0]) {
        println("This cell is occupied! Choose another one!")
        return false
    }

    return true
}

fun getGridIndexForCoordinates(x: Int, y: Int) = ((x - 1) * 3) + (y - 1)

fun hasWon(player: Char, row1: String, row2: String, row3: String): Boolean {
    val wonHorizontally = row1[0] == player && row1[1] == player && row1[2] == player ||
        row2[0] == player && row2[1] == player && row2[2] == player ||
        row3[0] == player && row3[1] == player && row3[2] == player

    val wonVertically = row1[0] == player && row2[0] == player && row3[0] == player ||
        row1[1] == player && row2[1] == player && row3[1] == player ||
        row1[2] == player && row2[2] == player && row3[2] == player

    val wonDiagonally = row1[0] == player && row2[1] == player && row3[2] == player ||
        row1[2] == player && row2[1] == player && row3[0] == player

    return wonHorizontally || wonVertically || wonDiagonally
}

fun isImpossible(row1: String, row2: String, row3: String): Boolean {
    val xCount = (row1 + row2 + row3).count { it == PLAYER_1 }
    val oCount = (row1 + row2 + row3).count { it == PLAYER_2 }
    val hasTooManyOfOnePlayer = abs(xCount - oCount) >= 2

    val bothPlayersWon = hasWon(PLAYER_1, row1, row2, row3) &&
        hasWon(PLAYER_2, row1, row2, row3)

    return hasTooManyOfOnePlayer || bothPlayersWon
}

fun isDraw(row1: String, row2: String, row3: String) =
    !isImpossible(row1, row2, row3) &&
        !hasWon(PLAYER_1, row1, row2, row3) &&
        !hasWon(PLAYER_2, row1, row2, row3) &&
        !(row1 + row2 + row3).contains(EMPTY_CELL)