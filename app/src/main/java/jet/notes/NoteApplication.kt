package jet.notes

import android.app.Application
import jet.notes.data.NoteDatabase
import jet.notes.data.NoteRepository

class NoteApplication : Application() {
    val database: NoteDatabase by lazy { NoteDatabase.getDatabase(this) }
    val repository: NoteRepository by lazy { NoteRepository(database.noteDao()) }

    override fun onCreate() {
        super.onCreate()
    }
}
