package hu.ait.bookapp.BookScreen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.bookapp.data.Doc
import hu.ait.bookapp.network.BookAPI
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookAPI: BookAPI
) : ViewModel() {

    var bookUIState: BookUIState by mutableStateOf(BookUIState.Init)

    fun getBookInfo(bookTitle:String) {
        // exec network call and change UI states properly..
        bookUIState = BookUIState.Loading

        viewModelScope.launch {
            try {
                val bookResult = bookAPI.getBookInfo(bookTitle)
                val docsList = bookResult.docs
                val itemsToDisplay = (docsList?.size ?: 0) * 0.2
                val subList = docsList?.subList(0, itemsToDisplay.toInt())
                bookUIState = BookUIState.Success(subList)
            } catch (e: Exception) {
                bookUIState = BookUIState.Error(e.message!!)
            }
        }

    }
}


sealed interface BookUIState {
    object Init : BookUIState
    object Loading : BookUIState
    data class Success(val bookResult: List<Doc>?) : BookUIState
    data class Error(val errorMsg:String) : BookUIState

}