import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Border
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Ellipse
import java.lang.Math.pow
import java.math.*

class Controller {
    var ellipsies = mutableListOf<MutableList<Ellipse>>()
    var logic = Logic()
    var selected = -1

    @FXML
    lateinit var button: Button

    @FXML
    lateinit var anchorPane: AnchorPane

    @FXML
    lateinit var labelPlayer: Label

    @FXML
    lateinit var labelMove: Label

    fun startGame() {
        if (button.text.equals("Начать игру")) {
            for (i in logic.list.indices) {
                ellipsies.add(mutableListOf())
                for (j in logic.list[i].indices) {
                    val ellipse = Ellipse(12.0, 12.0)
                    if (logic.list[i][j] != 1) {
                        ellipse.fill = Color.GREEN
                    } else {
                        ellipse.fill = Color.BLACK
                    }
                    ellipse.centerX = 100.0 + 50 * j
                    ellipse.centerY = 100.0 + 50 * i
                    ellipse.isVisible = logic.list[i][j] != 0 && logic.list[i][j] != -2
                    ellipsies[i].add(ellipse)
                    anchorPane.children.add(ellipse)
                }
            }
            anchorPane.setOnMouseClicked { mouseClicked(it) }
            labelPlayer.isVisible = true
            labelPlayer.text = "Сейчас ходят: Олени"
            labelMove.isVisible = true
            labelMove.text = "Собакам осталось выставить фишек: 16"
        } else {
            logic = Logic()
            updateScene()
        }
        button.text = "Начать заново"
    }

    private fun mouseClicked(mouseEvent: MouseEvent) {
        for (i in ellipsies.indices) {
            for (j in ellipsies[i].indices) {
                if (pow(mouseEvent.x - ellipsies[i][j].centerX, 2.0) + pow(
                        mouseEvent.y - ellipsies[i][j].centerY, 2.0) < 144) {
                    if (selected == -1) {
                        if (logic.isBlackPut()) {
                            logic.makeMove(i, j)
                            updateScene()
                        } else {
                            selected = i * 9 + j
                            ellipsies[i][j].stroke = Color.RED
                            ellipsies[i][j].strokeWidth = 3.0
                        }
                    } else {
                        logic.makeMove(selected / 9, selected % 9, i, j)
                        selected = -1
                        updateScene()
                    }
                }
            }
        }

    }

    private fun updateScene() {
        for (i in logic.list.indices) {
            for (j in logic.list[i].indices) {
                if (logic.list[i][j] == 2) {
                    ellipsies[i][j].fill = Color.GREEN
                    ellipsies[i][j].stroke = Color.GREEN
                }
                if (logic.list[i][j] == 1) {
                    ellipsies[i][j].fill = Color.BLACK
                    ellipsies[i][j].stroke = Color.BLACK
                }
                ellipsies[i][j].isVisible = logic.list[i][j] != 0 && logic.list[i][j] != -2
            }
        }
        labelPlayer.text = "Сейчас ходят: ${if (logic.player == 1) "Собаки" else "Олени"}"
        labelMove.text = "Собакам осталось выставить фишек: ${16 - logic.countBlack}"
    }

}