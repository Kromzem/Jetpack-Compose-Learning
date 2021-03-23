package com.kromzem.jetpackcomposelayoutscodelab

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kromzem.jetpackcomposelayoutscodelab.ui.theme.JetpackComposeLayoutsCodelabTheme
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeLayoutsCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PhotographerCard()
                }
            }
        }
    }
}

@Composable
fun PhotographerCard() {
    Row {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            //Image goes here
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun LayoutsCodelab() {
    val favorite = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    FavoriteButton(isFavorite = favorite.value, onFavoriteChanged = { favorite.value = it })
                }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun FavoriteButton(isFavorite: Boolean, onFavoriteChanged: (Boolean) -> Unit) {
    IconButton(onClick = { onFavoriteChanged(!isFavorite) }) {
        Icon(
            if (isFavorite)
                Icons.Filled.Favorite
            else
                Icons.Outlined.FavoriteBorder,
            contentDescription = null
        )
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column {
        Column(modifier = modifier) {
            Text(text = "Hi there")
            Text(text = "Thanks for going through the Layouts codelab")
        }
        SimpleList(modifier)
    }
}

@Composable
fun SimpleList(modifier: Modifier = Modifier) {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Row {
        Button(onClick = { coroutineScope.launch { scrollState.animateScrollToItem(0) } }) {
            Text(text = "Scroll to top")
        }

        Button(onClick = { coroutineScope.launch { scrollState.animateScrollToItem(listSize - 1) } }) {
            Text(text = "Scroll to bottom")
        }
    }

    LazyColumn(state = scrollState) {
        items(listSize) {
            ImageListItem(index = it)
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CoilImage(
            data = "https://developer.android.com/images/brand/Android_Robot.png",
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        ) 
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@Preview
@Composable
fun PhotographerCardPreview() {
    JetpackComposeLayoutsCodelabTheme {
        PhotographerCard()
    }
}

@Preview
@Composable
fun LayoutsCodelabPreview() {
    JetpackComposeLayoutsCodelabTheme {
        LayoutsCodelab()
    }
}