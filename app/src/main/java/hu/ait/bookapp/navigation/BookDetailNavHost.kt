package hu.ait.bookapp.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.ait.bookapp.BookScreen.BookDetailScreen
import hu.ait.bookapp.BookScreen.BookScreen


@Composable
fun BookDetailNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "book-list"
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = "book-list",
        ) {
            BookScreen(onNavigateToBookDetails = {
                navController.navigate("book-details/${it}")
            })
        }

        composable(route = "book-details/{isbn}",
            arguments = listOf(navArgument("isbn") { type = NavType.StringType})

        ) {
            val bookName = it.arguments?.getString("isbn")!!
            BookDetailScreen(bookName)
        }
    }
}


