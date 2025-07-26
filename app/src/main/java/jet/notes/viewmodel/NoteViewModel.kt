package jet.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jet.notes.data.Note
import jet.notes.data.NoteDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(private val noteDao: NoteDao) : ViewModel() {

    val notes: StateFlow<List<Note>> = noteDao.getAllNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getNoteById(noteId: String?): Note? {
        if (noteId.isNullOrEmpty()) {
            return null
        }
        var note: Note? = null
        viewModelScope.launch {
            note = noteDao.getNoteById(noteId)
        }
        return note
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    fun updateNote(updatedNote: Note) {
        viewModelScope.launch {
            noteDao.update(updatedNote)
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            noteDao.getNoteById(noteId)?.let { noteDao.delete(it) }
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            noteDao.deleteAll()
        }
    }

    companion object {
        fun provideFactory(noteDao: NoteDao): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                        return NoteViewModel(noteDao) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}
