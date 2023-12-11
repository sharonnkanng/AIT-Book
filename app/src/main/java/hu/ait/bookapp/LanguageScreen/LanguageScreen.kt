package hu.ait.bookapp.LanguageScreen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.rememberFlipController
import hu.ait.bookapp.R
import java.io.File

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val postListState = languageViewModel.postsList().collectAsState(
        initial = LanguageScreenUIState.Init
    )
    var showWord by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dictionary") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                actions = {
                    IconButton(onClick = {
                        showWord = true
                    }) {
                        Icon(Icons.Filled.AddCircle, null)
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                LanguageScreenContent(
                    showWord = showWord,
                    onAddWord = { word, message ->
                        languageViewModel.saveWord(word)
                        showWord = false
                    },
                    languageViewModel = languageViewModel,
                    postListState = postListState.value
                )
            }

        }
    )
}

@Composable
fun LanguageScreenContent(
    showWord: Boolean,
    onAddWord: (String, String) -> Unit,
    languageViewModel: LanguageViewModel,
    postListState: LanguageScreenUIState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        if (showWord) {
            addnewWord(
                onAddWord = onAddWord,
                languageViewModel = languageViewModel
            )
        }

        LazyColumn {
            if (postListState == LanguageScreenUIState.Init) {
                item {
                    Text(text = "")
                }
            } else if (postListState is LanguageScreenUIState.Success) {
                items(postListState.postList) { wordWithId ->
                    FlipCard(
                        wordWithId = wordWithId,
                        languageViewModel = languageViewModel
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addnewWord(
    onAddWord: (String, String) -> Unit,
    languageViewModel: LanguageViewModel
) {
    var newWord by remember { mutableStateOf("") }
    var wordError by remember { mutableStateOf(false) }
    var errorText by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        item {
            Column(
                Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(10.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = newWord,
                    onValueChange = {
                        newWord = it
                        if (wordError) {
                            wordError = false
                            errorText = ""
                        }
                    },
                    label = { Text("enter a word") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (wordError) {
                    Text(
                        text = errorText,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        if (newWord.isNotBlank()) {
                            val message = languageViewModel.getMessage(newWord)
                            onAddWord(newWord, message)
                            newWord = ""
                        }
                    }
                ) {
                    Text("Add Word")
                }
            }
        }
    }
}
class ComposeFileProvider : FileProvider(
    R.xml.filepaths
)

@Composable
fun FlipCard(wordWithId: WordWithId, languageViewModel: LanguageViewModel) {
    val controller = rememberFlipController()

    Flippable(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f),
        frontSide = {
            WordCard(
                title = "Card Front",
                background = MaterialTheme.colorScheme.surfaceVariant,
                word = wordWithId.word.word,
                message = "",
                onRemoveItem = { languageViewModel.removeItem(wordWithId.wordId) }
            )
        },
        backSide = {
            WordCard(
                title = "Card Back",
                background = MaterialTheme.colorScheme.error,
                word = wordWithId.word.word,
                message = languageViewModel.getMessage(wordWithId.word.word),
                onRemoveItem = { languageViewModel.removeItem(wordWithId.wordId) }
            )
        },
        flipController = controller,
        flipOnTouch = true
    )
}


@Composable
fun WordCard(title: String, background : Color, word: String, message: String, onRemoveItem: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = word,
                    fontWeight = FontWeight.Bold
                )
                DeleteButton(word, onRemoveItem)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(message)
        }
    }
}

@Composable
fun DeleteButton(word: String, onRemoveItem: () -> Unit) {
    IconButton(onClick = { onRemoveItem() }) {
        Icon(Icons.Filled.Delete, contentDescription = "Delete Word")
    }
}
