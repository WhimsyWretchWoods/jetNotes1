package jet.notes.ui

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect

import jet.notes.viewmodel.NoteViewModel
import jet.notes.data.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetail(
    navController: NavController,
    noteId: String?,
    noteViewModel: NoteViewModel
) {
    // Observe the currentNote from the ViewModel
    val currentNote by noteViewModel.currentNote.collectAsState()

    // Use remember to keep the state across recompositions,
    // initialized from currentNote or empty if new
    var title by remember(currentNote) { mutableStateOf(currentNote?.title ?: "") }
    var content by remember(currentNote) { mutableStateOf(currentNote?.content ?: "") }

    // Trigger loading the note when noteId changes
    LaunchedEffect(noteId) {
        noteViewModel.loadNote(noteId)
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
                    // Show delete only if it's an existing note
                    if (noteId != null && currentNote != null) {
                        IconButton(onClick = {
                            noteViewModel.deleteNote(noteId)
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Filled.Delete, contentDescription = null)
                        }
                    }

                    IconButton(onClick = {
                        // Use currentNote.id for updating or generating a new ID for adding
                        val noteToSave = currentNote?.copy(title = title, content = content)
                            ?: Note(UUID.randomUUID().toString(), title, content) // New note if currentNote is null

                        if (noteToSave.id == currentNote?.id && currentNote != null) {
                            // If IDs match, it's an update of an existing note
                            noteViewModel.updateNote(noteToSave)
                        } else {
                            // It's a new note, or a new ID was generated
                            noteViewModel.addNote(noteToSave)
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
