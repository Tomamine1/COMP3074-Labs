package ca.gbc.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ca.gbc.lab3.ui.theme.Lab3Theme
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Surface
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight

data class Message(
    val name: String,
    val text: String
)

@Composable
fun MessageCard(
    message: Message,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = Color.LightGray,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.android_avatar),
                contentDescription = "Android",
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = message.name,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.White
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val messages = listOf(
            Message("Joe", "Hi!"),
            Message("Tom", "How are you ?"),
            Message("Joe", "Test... 1..2..3"),
            Message("Tom", "I hate coding"),

            Message("Joe", "Hi!"),
            Message("Tom", "How are you ?"),
            Message("Joe", "Test... 1..2..3"),
            Message("Tom", "I hate coding"),

            Message("Joe", "Hi!"),
            Message("Tom", "How are you ?"),
            Message("Joe", "Test... 1..2..3"),
            Message("Tom", "I hate coding")
        )
        setContent {
            Lab3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(messages) { message ->
                            MessageCard(message)

                            Spacer(modifier = Modifier.height(18.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab3Theme {
        Greeting("Android")
    }
}