package jet.notes.navigation

sealed class Screen(val route: String) {
    object NoteHome : Screen("note_home")
    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: String) = "note_detail/$noteId"
    }
    object CreateNote : Screen("create_note")
}
