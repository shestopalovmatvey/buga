import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Test {
    var logic = Logic()

    @BeforeEach
    fun start() {
        logic = Logic()
    }

    @Test
    fun foo() {
        logic.move(2, 2,1, 2)
        assertTrue(logic.list[2][2] == 0 && logic.list[1][2] == 2)
    }

    fun foo1() {
        TODO()
    }

}