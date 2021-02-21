package com.kromzem.jetpackcomposebasicscodelab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animatedColor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kromzem.jetpackcomposebasicscodelab.ui.theme.JetpackComposeBasicsCodelabTheme


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App {
                ScreenContent()
            }
        }
    }
}

@Composable
fun App(content: @Composable () -> Unit) {
    JetpackComposeBasicsCodelabTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = Color.Green) {
            content()
        }
    }
}

@Composable
fun ScreenContent(names: List<String> = List(1000) { "Hello Android #$it" }) {
    val count = remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxHeight()) {
        NameList(names = names, modifier = Modifier.weight(1f))
        Counter(
            count = count.value,
            updateCount = { newCount -> count.value = newCount }
        )
    }
}

@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) { name ->
            Greeting(name = name)
            Divider(color = Color.Black)
        }
    }
}

@Composable
fun Greeting(name: String) {
    val isSelected = remember { mutableStateOf(false) }

    Text(
        text = "Hello $name!",
        modifier = Modifier
            .padding(24.dp)
            .background(color = if(isSelected.value) Color.Red else Color.Transparent)
            .clickable { isSelected.value = !isSelected.value }
    )
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = { updateCount(count + 1) },
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = if (count > 5) Color.Green else Color.White
        )
    ) {
        Text(text = "I've been clicked $count times")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    App {
        ScreenContent()
    }
}