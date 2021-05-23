import java.lang.Math.abs

class Logic() {
    var player = 2
    var countBlack = 0
    var countFelledChip = 0
    val list = mutableListOf<MutableList<Int>>()

    init {
        for (i in 0..4) {
            list.add(mutableListOf())
            for (j in 0..8) {
                list[i].add(0)
            }
        }
        list[0][0] = -2
        list[0][1] = -2
        list[1][0] = -2
        list[3][0] = -2
        list[4][0] = -2
        list[4][1] = -2
        list[0][7] = -2
        list[4][7] = -2
        list[1][3] = 1
        list[1][4] = 1
        list[1][5] = 1
        list[2][2] = 2
        list[2][3] = 1
        list[2][5] = 1
        list[2][6] = 2
        list[3][3] = 1
        list[3][4] = 1
        list[3][5] = 1
    }


    fun move(line: Int, column: Int, endLine: Int, endColumn: Int) {
        list[endLine][endColumn] = list[line][column]
        list[line][column] = 0
    }

    fun makeMove(line: Int, column: Int) {
        if (list[line][column] == 0) {
            list[line][column] = 1
            countBlack++
            player = 2
        }
    }

    fun makeMove(line: Int, column: Int, endLine: Int, endColumn: Int) {
        if (list[line][column] == player && list[endLine][endColumn] == 0 &&
            !((line + column) % 2 == 1 && ((endLine != line) && (endColumn != column)))) {
            if (abs(endLine - line) <= 1 && abs(endColumn - column) <= 1) {
                move(line, column, endLine, endColumn)
                player = if (player == 2) 1 else 2
            }
            if (abs(endLine - line) <= 2 && abs(endColumn - column) <= 2 && player == 2 &&
                !((endLine + endColumn) % 2 == 1 && ((endLine != line) && (endColumn != column)))) {
                val deltaLine = (endLine - line) / 2
                val deltaColumn = (endColumn - column) / 2
                if (list[line + deltaLine][column + deltaColumn] == 1) {
                    list[endLine][endColumn] = 2
                    list[line][column] = 0
                    list[line + deltaLine][column + deltaColumn] = 0
                    countFelledChip++
                    player = 1
                }
            }
        }
    }

    fun isBlackPut(): Boolean {
        return player == 1 && countBlack < 16
    }

    fun endGame(): Int {
        if (countFelledChip >= 14) {
            return 2
        }
        var firstChipBlocked = false
        var first = false
        for (i in list.indices) {
            for (j in list[i].indices) {
                if (list[i][j] == 2) {
                    if (!first) {
                        first = true
                        firstChipBlocked = isBlocked(i, j)
                    } else {
                        return if (firstChipBlocked && isBlocked(i, j)) 1 else 3
                    }
                }
            }
        }
        return 3
    }

    private fun isBlocked(i: Int, j: Int): Boolean {
        for (k in i-2..i+2) {
            for (m in j-2..j+2) {
                if (k in 0..4 && m in 0..8 && list[k][m] != -2 && !((i + j) % 2 == 1 && ((k != i) && (m != j))) && list[k][m] == 0) {
                    if (abs(k - i) <= 1 && abs(m - j) <= 1 && list[k][m] == 0) {
                        return false
                    }
                    if (abs(k - i) <= 2 && abs(m - j) <= 2 && !((k + m) % 2 == 1 && ((k != i) && (m != j)))) {
                        val deltaLine = (k - i) / 2
                        val deltaColumn = (m - j) / 2
                        if (list[i + deltaLine][j + deltaColumn] == 1) {
                            return false
                        }
                    }
                }
            }
        }
        return true
    }
}