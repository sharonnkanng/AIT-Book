package hu.ait.bookapp.BookClubScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookClubViewModel @Inject constructor(
) : ViewModel() {

    var writePostUiState: AddNewClubUiState
            by mutableStateOf(AddNewClubUiState.Init)
    private lateinit var auth: FirebaseAuth

    init {
        auth = Firebase.auth
    }

    companion object {
        const val COLLECTION_CLUBS= "clubs"
    }

    fun removeItem(wordId: String) {
        val postCollection =
            FirebaseFirestore.getInstance().collection(
                COLLECTION_CLUBS
            )
        postCollection.document(wordId).delete()
    }

    fun postsList() = callbackFlow {
        val snapshotListener =
            FirebaseFirestore.getInstance().collection(COLLECTION_CLUBS)
                .addSnapshotListener() { snapshot, e ->
                    val response = if (snapshot != null) {
                        val wordList = snapshot.toObjects(Word::class.java)
                        val wordWithIdList = mutableListOf<WordWithId>()

                        wordList.forEachIndexed { index, word ->
                            wordWithIdList.add(WordWithId(snapshot.documents[index].id, word))
                        }

                        BookClubScreenUIState.Success(
                            wordWithIdList
                        )
                    } else {
                        BookClubScreenUIState.Error(e?.message.toString())
                    }

                    trySend(response) // emit this value through the flow
                }
        awaitClose {
            snapshotListener.remove()
        }
    }

    fun saveWord(
        word: String
    ) {
        writePostUiState = AddNewClubUiState.LoadingPostUpload

        val myWord = Word(
            word = word
        )

        val postCollection =
            FirebaseFirestore.getInstance().collection(
                COLLECTION_CLUBS
            )

        postCollection.add(myWord).addOnSuccessListener {
            writePostUiState = AddNewClubUiState.PostUploadSuccess
        }.addOnFailureListener {
            writePostUiState = AddNewClubUiState.ErrorDuringPostUpload(it.message)
        }
    }
}

data class Word(
    var word: String = "",
)

data class WordWithId(
    val wordId: String,
    val word: Word
)


sealed interface AddNewClubUiState {
    object Init : AddNewClubUiState
    object LoadingPostUpload : AddNewClubUiState
    object PostUploadSuccess : AddNewClubUiState
    data class ErrorDuringPostUpload(val error: String?) : AddNewClubUiState

}

sealed interface BookClubScreenUIState {
    object Init : BookClubScreenUIState
    data class Success(val postList: List<WordWithId>) : BookClubScreenUIState
    data class Error(val error: String?) : BookClubScreenUIState
}