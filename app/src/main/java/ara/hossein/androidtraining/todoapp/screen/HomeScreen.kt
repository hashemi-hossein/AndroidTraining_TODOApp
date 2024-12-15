package ara.hossein.androidtraining.todoapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ara.hossein.androidtraining.todoapp.data.TodoDatabase
import ara.hossein.androidtraining.todoapp.data.TodoEntity
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val text = remember { mutableStateOf("") }
    val items = remember { mutableStateListOf<TodoEntity>() }

    LaunchedEffect(true) {
        TodoDatabase.getDatabase(context).todoDao().getAll().collect { todoItems->
            items.clear()
            items.addAll(todoItems)
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(value = text.value, onValueChange = { text.value = it })
            Button(onClick = {
                scope.launch {
                    TodoDatabase.getDatabase(context).todoDao()
                        .insert(TodoEntity(id = 0,text = text.value, isDone = false))
                    text.value = ""
                }
            }) { Text("Add") }
        }
        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(items) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(it.text, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}