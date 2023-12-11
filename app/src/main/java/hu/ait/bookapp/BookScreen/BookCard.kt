package hu.ait.bookapp.BookScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hu.ait.bookapp.R
import hu.ait.bookapp.data.Doc

@Composable
fun BookCard(
    bookItem: Doc,
    onNavigateToBookDetails: (String) -> Unit,
) {

    var isbn = bookItem.isbn?.get(0)
    val shortenedTitle = if (bookItem.title?.length ?: 0 < 30) {
        bookItem.title!!
    } else {
        "${bookItem.title?.substring(0, 20)}..."
    }


    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        "https://covers.openlibrary.org/b/isbn/${
                            bookItem.isbn?.get(0)}-S.jpg?default=false"
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = "Book Cover",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(50.dp)
            )
            Text(
                // cut this after 30 characters
                text = shortenedTitle,
                style = MaterialTheme.typography.titleMedium,
            )
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                TextButton(onClick = {
                    if (isbn != null) {
                        onNavigateToBookDetails(isbn)
                    }
                }) {
                    Text(
                        text = "Details",
                        color = Color.Blue
                    )
                }
            }
        }
    }
}