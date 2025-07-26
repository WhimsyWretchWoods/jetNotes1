package jet.notes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var content: String,
    val timestamp: Long = System.currentTimeMillis()
)
