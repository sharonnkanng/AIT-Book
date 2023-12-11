package hu.ait.bookapp.LanguageScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
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
class LanguageViewModel @Inject constructor(
) : ViewModel() {

    var writePostUiState: AddNewWordUiState
            by mutableStateOf(AddNewWordUiState.Init)
    private lateinit var auth: FirebaseAuth

    init {
        auth = Firebase.auth
    }

    companion object {
        const val COLLECTION_WORDS= "words"
    }

    fun removeItem(wordId: String) {
        val postCollection =
            FirebaseFirestore.getInstance().collection(
                COLLECTION_WORDS
            )
        postCollection.document(wordId).delete()
    }
    fun getMessage(word:String): String{
        return when (word.toLowerCase()){
            "happy" -> "Delighted, pleased, or glad, as over a particular thing:"
            "idea" -> "Any conception existing in the mind as a result of mental understanding, awareness, or activity."
            "apple" -> "The usually round, red or yellow, edible fruit of a small tree, Malus sylvestris, of the rose family."
            "teaching" -> "The act or professihon of a person who teaches."
            "ice cream" -> "A frozen food containing cream or milk and butterfat, sugar, flavoring, and sometimes eggs."
            else -> "Message not found for $word"
        }
    }
    fun postsList() = callbackFlow {
        val snapshotListener =
            FirebaseFirestore.getInstance().collection(LanguageViewModel.COLLECTION_WORDS)
                .addSnapshotListener() { snapshot, e ->
                    val response = if (snapshot != null) {
                        val wordList = snapshot.toObjects(Word::class.java)
                        val wordWithIdList = mutableListOf<WordWithId>()

                        wordList.forEachIndexed { index, word ->
                            wordWithIdList.add(WordWithId(snapshot.documents[index].id, word))
                        }

                        LanguageScreenUIState.Success(
                            wordWithIdList
                        )
                    } else {
                        LanguageScreenUIState.Error(e?.message.toString())
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
        writePostUiState = AddNewWordUiState.LoadingPostUpload

        val myWord = Word(
            word = word
        )

        val postCollection =
            FirebaseFirestore.getInstance().collection(
                COLLECTION_WORDS
            )

        postCollection.add(myWord).addOnSuccessListener {
            writePostUiState = AddNewWordUiState.PostUploadSuccess
        }.addOnFailureListener {
            writePostUiState = AddNewWordUiState.ErrorDuringPostUpload(it.message)
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


sealed interface AddNewWordUiState {
    object Init : AddNewWordUiState
    object LoadingPostUpload : AddNewWordUiState
    object PostUploadSuccess : AddNewWordUiState
    data class ErrorDuringPostUpload(val error: String?) : AddNewWordUiState

}

sealed interface LanguageScreenUIState {
    object Init : LanguageScreenUIState
    data class Success(val postList: List<WordWithId>) : LanguageScreenUIState
    data class Error(val error: String?) : LanguageScreenUIState
}