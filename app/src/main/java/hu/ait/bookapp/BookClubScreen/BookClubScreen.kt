package hu.ait.bookapp.BookClubScreen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.ait.bookapp.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookClubScreen(
    navController: NavController,
    bookClubViewModel: BookClubViewModel = hiltViewModel()
) {
    val postListState = bookClubViewModel.postsList().collectAsState(
        initial = BookClubScreenUIState.Init
    )
    var showWord by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Clubs") },
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
                BookClubScreenContent(
                    showWord = showWord,
                    onAddWord = { word, message ->
                        bookClubViewModel.saveWord(word)
                        showWord = false
                    },
                    bookClubViewModel = bookClubViewModel,
                    postListState = postListState.value,
                    navController = navController
                )
            }
        }
    )
}

@Composable
fun BookClubScreenContent(
    navController: NavController,
    showWord: Boolean,
    onAddWord: (String, String) -> Unit,
    bookClubViewModel: BookClubViewModel,
    postListState: BookClubScreenUIState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        if (showWord) {
            addnewWord(
                onAddWord = onAddWord,
                bookClubViewModel = bookClubViewModel
            )
        }

        LazyColumn {
            if (postListState == BookClubScreenUIState.Init) {
                item {
                    Text(text = "")
                }
            } else if (postListState is BookClubScreenUIState.Success) {
                items(postListState.postList) { wordWithId ->
                    WordCard(
                        word = wordWithId.word.word,
                        onRemoveItem = {
                            bookClubViewModel.removeItem(wordWithId.wordId)
                        },
                        onCardClicked = {
                        },
                        navController = navController
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
    bookClubViewModel: BookClubViewModel
) {
    var book by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var pages by remember { mutableStateOf("") }
    var review by remember { mutableStateOf("") }
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
                    value = book,
                    onValueChange = {
                        book = it
                        if (wordError) {
                            wordError = false
                            errorText = ""
                        }
                    },
                    label = { Text("Book") }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = date,
                    onValueChange = {
                        date = it
                        if (wordError) {
                            wordError = false
                            errorText = ""
                        }
                    },
                    label = { Text("Starting Date") }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = pages,
                    onValueChange = {
                        pages = it
                        if (wordError) {
                            wordError = false
                            errorText = ""
                        }
                    },
                    label = { Text("Number of Pages") }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = review,
                    onValueChange = {
                        review = it
                        if (wordError) {
                            wordError = false
                            errorText = ""
                        }
                    },
                    label = { Text("Review") }
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
                        if (book.isNotBlank()) {
                            onAddWord(book, "")
                            book = ""
                        }
                    }
                ) {
                    Text("Create Club")
                }
            }
        }
    }
}

class ComposeFileProvider : FileProvider(
    R.xml.filepaths
)


@Composable
fun WordCard(word: String, onRemoveItem: () -> Unit, onCardClicked: () -> Unit, navController: NavController) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                onCardClicked()
                navController.navigate("SpecBookClubScreen")
            }
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
        }
    }
}

@Composable
fun DeleteButton(word: String, onRemoveItem: () -> Unit) {
    IconButton(onClick = { onRemoveItem() }) {
        Icon(Icons.Filled.Delete, contentDescription = "Delete Word")
    }
}


