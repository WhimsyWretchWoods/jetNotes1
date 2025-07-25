package jet.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jet.notes.data.Note
import jet.notes.data.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val notes: StateFlow<List<Note>> = repository.allNotes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // This will hold the note currently being viewed/edited in NoteDetail
    private val _currentNote = MutableStateFlow<Note?>(null)
    val currentNote: StateFlow<Note?> = _currentNote.asStateFlow()

    // Function to load a note into _currentNote state
    fun loadNote(noteId: String?) {
        viewModelScope.launch {
            if (noteId.isNullOrEmpty()) {
                // It's a new note, create a fresh one with a new ID
                _currentNote.value = Note(
                    id = UUID.randomUUID().toString(), // Generate a new ID for a new note
                    title = "",
                    content = "",
                    timestamp = System.currentTimeMillis()
                )
            } else {
                // Load existing note from the repository
                repository.getNoteById(noteId).collectLatest { note ->
                    _currentNote.value = note
                }
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun updateNote(updatedNote: Note) {
        viewModelScope.launch {
            repository.update(updatedNote)
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            repository.deleteNoteById(noteId)
        }
    }

    companion object {
        fun provideFactory(repository: NoteRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                        return NoteViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}
