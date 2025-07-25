package jet.notes.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

import androidx.lifecycle.viewmodel.compose.viewModel

import jet.notes.navigation.Screen
import jet.notes.ui.NoteHome
import jet.notes.ui.NoteDetail
import jet.notes.viewmodel.NoteViewModel

@Composable
fun NoteApp(noteViewModel: NoteViewModel) {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.NoteHome.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(Screen.NoteHome.route) {
            NoteHome(navController = navController, noteViewModel = noteViewModel)
        }
        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(navArgument("noteId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            NoteDetail(navController = navController, noteId = noteId, noteViewModel = noteViewModel)
        }
        composable(Screen.CreateNote.route) {
            NoteDetail(navController = navController, noteId = null, noteViewModel = noteViewModel)
        }
    }
}
