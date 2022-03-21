package minesweeper

import java.util.*
import kotlin.random.Random

class MineField(private val numberOfMines: Int) {

    private var firstCellExplored = true

    private val field = mutableListOf<MutableList<Char>>()

    private val mines = mutableSetOf<Pair<Int, Int>>()

    private val hints = mutableMapOf<Pair<Int, Int>, Int>()

    init {
        initializeField()
    }

    private fun initializeField() {
        for (i in 0 until FIELD_ROWS) {
            field.add(mutableListOf())
            for (j in 0 until FIELD_COLS) {
                field[i].add(DEFAULT_CELL)
            }
        }
    }

    private fun addMines() {
        repeat(numberOfMines) {
            do {
                val randomRow = Random.nextInt(FIELD_ROWS)
                val randomCol = Random.nextInt(FIELD_COLS)

                if (!mines.contains(Pair(randomRow, randomCol))) {
                    mines.add(Pair(randomRow, randomCol))
                    break
                }
            } while (mines.contains(Pair(randomRow, randomCol)))
        }
    }

    private fun countMines() {
        for (i in 0 until mines.size) {
            val (x, y) = mines.elementAt(i)
            val neighbouringCells = getNeighbouringCells(x, y)
            neighbouringCells.forEach { (x, y) ->
                if (field.getOrNull(y)?.getOrNull(x) != null && !mines.contains(Pair(x, y))) {
                    if (!hints.contains(Pair(x, y))) {
                        hints[Pair(x, y)] = 1
                    } else if (hints.contains(Pair(x, y))) {
                        val currentHint = hints[Pair(x, y)]
                        if (currentHint != null) {
                            hints[Pair(x, y)] = currentHint + 1
                        }
                    }
                }
            }
        }
    }

    private fun getNeighbouringCells(row: Int, col: Int): List<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>().apply {
        add(Pair((row - 1), (col - 1)))
        add(Pair((row - 1), col))
        add(Pair((row - 1), (col + 1)))
        add(Pair((row), (col + 1)))
        add(Pair((row + 1), (col + 1)))
        add(Pair((row + 1), col))
        add(Pair((row + 1), (col - 1)))
        add(Pair((row), (col - 1)))
    }.toList()

    private fun getNeighbouringCells(cell: Pair<Int, Int>) = getNeighbouringCells(cell.first, cell.second)

    fun printMinefield(showMines: Boolean = false) {
        println(
            """
                
             │123456789│
            —│—————————│
        """.trimIndent()
        )

        for (i in 0 until FIELD_ROWS) {
            print("${i + 1}│")
            for (j in 0 until FIELD_COLS) {
                val cell = field.getOrNull(j)?.getOrNull(i)
                if (showMines) {
                    if (cell != null && mines.contains(Pair(i, j))) {
                        print(MINE_CELL)
                    } else {
                        print(cell)
                    }
                } else {
                    print(cell)
                }
            }
            print("│")
            println()
        }

        println(
            """
            —│—————————│
        """.trimIndent()
        )
    }

    fun placeMine(x: Int, y: Int): Boolean {
        if (field[y][x].isDigit()) return false

        if (field[y][x] == MARKED_CELL) {
            field[y][x] = DEFAULT_CELL
        } else {
            field[y][x] = MARKED_CELL
        }

        return true
    }

    fun exploreCell(x: Int, y: Int): Boolean {
        if (firstCellExplored) {
            firstCellExplored = false
            addMines()
            countMines()
        }

        if (mines.contains(Pair(x, y))) return false

        val frontier: Queue<Pair<Int, Int>> = LinkedList()
        frontier.add(Pair(x, y))
        while (frontier.isNotEmpty()) {
            val current = frontier.remove()
            if (hints.contains(current)) {
                field[current.second][current.first] = hints.getValue(current).digitToChar()
                break
            } else if (field.getOrNull(current.second)?.getOrNull(current.first) != null) {
                field[current.second][current.first] = NO_MINE_CELL
            }

            for (cell in getNeighbouringCells(current)) {
                if (!mines.contains(cell)) {
                    if (hints.contains(cell)) {
                        field[cell.second][cell.first] = hints.getValue(cell).digitToChar()
                    } else {
                        val currentCell = field.getOrNull(cell.second)?.getOrNull(cell.first)
                        if (currentCell != null && (currentCell == DEFAULT_CELL || currentCell == MARKED_CELL)) {
                            field[cell.second][cell.first] = NO_MINE_CELL
                            frontier.add(cell)
                        }
                    }
                }
            }
        }

        return true
    }

    fun hasWon(): Boolean {
        val correctMinesCount = field.flatten().count { it == MARKED_CELL || it == DEFAULT_CELL } == numberOfMines
        if (!correctMinesCount) return false

        var correctlyPlacedMine = true
        for (i in 0 until FIELD_ROWS) {
            for (j in 0 until FIELD_COLS) {
                correctlyPlacedMine = !mines.contains(Pair(i, j))
            }
        }

        return correctlyPlacedMine
    }

    companion object {
        const val FIELD_ROWS = 9
        const val FIELD_COLS = 9

        const val DEFAULT_CELL = '.'
        const val MARKED_CELL = '*'
        const val MINE_CELL = 'X'
        const val NO_MINE_CELL = '/'
    }
}