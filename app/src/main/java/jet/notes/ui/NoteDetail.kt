package jet.notes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import jet.notes.data.Note
import jet.notes.viewmodel.NoteViewModel
import kotlinx.coroutines.flow.flowOf // Import this

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetail(
    navController: NavController,
    noteId: String?,
    noteViewModel: NoteViewModel
) {
    // Correctly collect the Flow here
    val noteFlow = if (noteId != null) {
        noteViewModel.getNoteById(noteId)
    } else {
        flowOf(null) // Emit null for new notes
    }
    val note by noteFlow.collectAsState(initial = null) // Collect as state

    // Initialize title and content based on the 'note' state, which now updates reactively
    var title by remember(note) {
        mutableStateOf(note?.title ?: "")
    }
    var content by remember(note) {
        mutableStateOf(note?.content ?: "")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    if (noteId != null) {
                        IconButton(onClick = {
                            noteViewModel.deleteNote(noteId)
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Filled.Delete, contentDescription = null)
                        }
                    }

                    IconButton(onClick = {
                        if (title.isBlank() && content.isBlank()) {
                            // Don't save empty notes if both fields are blank
                            navController.popBackStack()
                            return@IconButton
                        }

                        if (noteId == null) {
                            noteViewModel.addNote(Note(title = title, content = content))
                        } else {
                            note?.let { currentNote ->
                                val updatedNote = currentNote.copy(title = title, content = content)
                                noteViewModel.updateNote(updatedNote)
                            }
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = {
                    title = it
                },
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                placeholder = {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().imePadding(),
                value = content,
                onValueChange = {
                    content = it
                },
                placeholder = {
                    Text("Note")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
        }
    }
}
