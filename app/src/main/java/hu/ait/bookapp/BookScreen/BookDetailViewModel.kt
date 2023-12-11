package hu.ait.bookapp.BookScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.bookapp.data.Doc
import hu.ait.bookapp.network.BookAPI
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookAPI: BookAPI
) : ViewModel() {

    var singleBookUIState: SingleBookUIState by mutableStateOf(SingleBookUIState.Init)

    fun getBookInfo(bookISBN:String) {
        // exec network call and change UI states properly..
        singleBookUIState = SingleBookUIState.Loading

        viewModelScope.launch {
            try {
                val bookResult = bookAPI.getBookInfo(bookISBN)

                singleBookUIState = SingleBookUIState.Success(bookResult.docs?.get(0)!!)
            } catch (e: Exception) {
                singleBookUIState = SingleBookUIState.Error(e.message!!)
            }
        }

    }
}


sealed interface SingleBookUIState {
    object Init : SingleBookUIState
    object Loading : SingleBookUIState
    data class Success(val bookResult:Doc) : SingleBookUIState
    data class Error(val errorMsg:String) : SingleBookUIState

}
