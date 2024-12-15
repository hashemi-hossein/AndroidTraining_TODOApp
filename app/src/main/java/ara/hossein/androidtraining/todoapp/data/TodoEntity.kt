package ara.hossein.androidtraining.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val isDone: Boolean,
)
