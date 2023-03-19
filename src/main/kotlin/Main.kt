import kotlinx.coroutines.runBlocking
import ui.UI

fun main(args: Array<String>) {
    val appContainer = AppRunner().run()
    val ui = appContainer.ui
    runBlocking {
        ui.run(numberOfOperations = 20)
    }
}
