package dev.samu.courutineszerotohero

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.samu.courutineszerotohero.ui.theme.CourutineszerotoheroTheme
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourutineszerotoheroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val users = remember { mutableStateOf<List<UserDataResponse>>(emptyList()) }
                    val retrofit = RetrofitHelper.getInstance()

                    LaunchedEffect(Unit) {
                        val response = retrofit.getUsers()
                        if (response.isSuccessful) {
                            users.value = response.body() ?: emptyList()
                        } else {
                            Log.i("Error", "Error al obtener datos")
                        }
                    }
                    UsersList(users = users.value)
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

