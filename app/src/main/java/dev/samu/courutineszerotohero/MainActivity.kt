package dev.samu.courutineszerotohero

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.samu.courutineszerotohero.ui.theme.CourutineszerotoheroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourutineszerotoheroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val users = remember { mutableStateOf<List<UserDataResponse>?>(null) }
                    val retrofit = RetrofitHelper.getInstance()

                    lifecycleScope.launch(Dispatchers.IO) {
                        val response = retrofit.getUsers()
                        if (response.isSuccessful) {
                            users.value = response.body()
                        } else {
                            Log.i("Error", "Error al obtener datos")
                        }
                    }

                    users.value?.let {
                        UsersList(users = it)
                    } ?: run {
                        Text("Cargando datos...")
                    }
                }
            }
        }
    }
}

@Composable
fun UsersList(users: List<UserDataResponse>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(users) { user ->
            Text(
                text = "${user.name} - ${user.email}",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

