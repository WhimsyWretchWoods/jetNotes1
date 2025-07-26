package jet.notes.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import jet.notes.navigation.Screen
import jet.notes.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteHome(navController: NavController, noteViewModel: NoteViewModel) {
    Scaffold(
        topBar = {
            TopAppBar (
                title = { Text("") }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {navController.navigate(Screen.CreateNote.route)},
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon (Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        val notes by noteViewModel.notes.collectAsState()
        if(notes.isEmpty()) {
            
        } else {
            LazyColumn (modifier = Modifier.padding(paddingValues)) {
                items(notes) { note ->
                    OutlinedCard(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { navController.navigate(Screen.NoteDetail.createRoute(note.id)) }
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                            Text(note.title, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    }
}
