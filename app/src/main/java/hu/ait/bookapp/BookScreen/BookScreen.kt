package hu.ait.bookapp.BookScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hu.ait.bookapp.data.Doc


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen (
    bookViewModel: BookViewModel = hiltViewModel(),
    onNavigateToBookDetails: (String) -> Unit = {},
    modifier: Modifier = Modifier
){
    var searchQuery by rememberSaveable { mutableStateOf("") }


    Scaffold(
        topBar = {
            // Search bar
            TextField(
                value = searchQuery,
                onValueChange = { newValue ->
                    searchQuery = newValue
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Handle search action
                        if (searchQuery.isNotEmpty()) {
                            bookViewModel.getBookInfo(searchQuery)
                        }
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = {
                    Text(text = "Search for a book")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },

                trailingIcon = {
                    if (searchQuery.isNotEmpty())
                    {
                        Icon(
                            modifier = Modifier.clickable {
                                searchQuery = ""
                            },
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear"
                        )
                    }

                },

                singleLine = true,
            )
        }
    ) {innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding), verticalArrangement = Arrangement.Center)
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                when (bookViewModel.bookUIState) {
                    //TODO implement a screen with trending books
                    is BookUIState.Init -> Text(text = "Search for a Book!", modifier = Modifier.padding(16.dp))
                    is BookUIState.Loading -> CircularProgressIndicator()
                    is BookUIState.Success ->
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items((bookViewModel.bookUIState as BookUIState.Success).bookResult ?: emptyList()) { bookItem ->
                                BookCard(bookItem, onNavigateToBookDetails)
                            }
                        }

                    is BookUIState.Error ->
                        Text(
                            text = "Error:" +
                                    " ${(bookViewModel.bookUIState as BookUIState.Error).errorMsg}"
                        )
                }
            }
        }
    }
}








