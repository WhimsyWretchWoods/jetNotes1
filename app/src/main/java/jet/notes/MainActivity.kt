package jet.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import jet.notes.data.NoteDatabase
import jet.notes.navigation.NoteApp
import jet.notes.ui.theme.AppTheme
import jet.notes.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val noteDao = NoteDatabase.getDatabase(applicationContext).noteDao()
                val noteViewModel: NoteViewModel = viewModel(
                    factory = NoteViewModel.provideFactory(noteDao)
                )
                Surface(modifier = Modifier.fillMaxSize()) {
                    NoteApp(noteViewModel = noteViewModel)
                }
            }
        }
    }
}
