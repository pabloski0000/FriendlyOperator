import ui.UI

fun main(args: Array<String>) {
    val appContainer = AppRunner().run()
    val ui = appContainer.ui
    ui.run(numberOfOperations = 20)
}
