package hu.ait.bookapp.BookScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hu.ait.bookapp.data.Doc


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    isbn: String,
    bookViewModel: BookDetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = Unit, block = {
        bookViewModel.getBookInfo(isbn)
    })

    when (bookViewModel.singleBookUIState) {
        //TODO implement a screen with trending books
        is SingleBookUIState.Init -> Text(text = "Search for a Book!", modifier = Modifier.padding(16.dp))
        is SingleBookUIState.Loading -> CircularProgressIndicator()
        is SingleBookUIState.Success -> {
            val bookResult = (bookViewModel.singleBookUIState as SingleBookUIState.Success).bookResult
            BookDetail(bookResult)

        }
        is SingleBookUIState.Error ->
            Text(
                text = "Error:" +
                        " ${(bookViewModel.singleBookUIState as BookUIState.Error).errorMsg}"
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetail(bookResult: Doc) {

    var myRating by remember { mutableIntStateOf(0) }
    var reviewText by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .padding(),
        verticalArrangement = Arrangement.Center
    ) {
        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        "https://covers.openlibrary.org/b/isbn/${
                            bookResult!!.isbn?.get(0)
                        }-L.jpg?default=false"
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = "Book Cover",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(
                    text = "${bookResult.title}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(text = "${bookResult.authorName?.get(0)}")
                Spacer(modifier = Modifier.padding(16.dp))
                Text(text = "Book Synopsis")
                Text(
                    text = "${bookResult.firstSentence}",
                    fontStyle = FontStyle.Italic
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Add to Favorites")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Check Availability")
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RatingBar(
                    currentRating = myRating,
                    onRatingChanged = { myRating = it }
                )
                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    label = { Text(text = "Leave a Review") },
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Submit")
                }
            }
        }
    }

    }






@Composable
fun RatingBar(
    maxRating: Int = 5,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit,
    starsColor: Color = Color.Yellow
) {
    Row {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= currentRating) Icons.Filled.Star
                else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (i <= currentRating) starsColor
                else Color.Unspecified,
                modifier = Modifier
                    .clickable { onRatingChanged(i) }
                    .padding(4.dp)
            )
        }
    }
}
