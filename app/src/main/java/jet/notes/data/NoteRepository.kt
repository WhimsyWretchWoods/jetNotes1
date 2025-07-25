package jet.notes.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    fun getNoteById(noteId: String): Flow<Note?> {
        return noteDao.getNoteById(noteId)
    }

    suspend fun deleteNoteById(noteId: String) {
        noteDao.deleteNoteById(noteId)
    }

    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}
