package hu.ait.bookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.ait.bookapp.BookClubScreen.BookClubScreen
import hu.ait.bookapp.BookClubScreen.SpecBookClubScreen
import hu.ait.bookapp.BookScreen.BookScreen
import hu.ait.bookapp.LanguageScreen.LanguageScreen
import hu.ait.bookapp.login.LoginScreen
import hu.ait.bookapp.navigation.BookDetailNavHost
import hu.ait.bookapp.navigation.bookapp
import hu.ait.bookapp.ui.theme.BookAppTheme

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookAppTheme {
                val items = listOf<BottomNavigationItem>(
                    BottomNavigationItem(
                        title = "Book",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home
                    ),
                    BottomNavigationItem(
                        title = "Language",
                        selectedIcon = Icons.Filled.Favorite,
                        unselectedIcon = Icons.Outlined.FavoriteBorder
                    ),
                    BottomNavigationItem(
                        title = "Book Club",
                        selectedIcon = Icons.Filled.Star,
                        unselectedIcon = Icons.Filled.Star
                    )
                )
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if (selectedItemIndex != -1) {
                                BottomAppBar {
                                    items.forEachIndexed { index, item ->
                                        NavigationBarItem(
                                            selected = selectedItemIndex == index,
                                            onClick = {
                                                selectedItemIndex = index
                                                when (index) {
                                                    0 -> navController.navigate("BookScreen")
                                                    1 -> navController.navigate("LanguageScreen")
                                                    2 -> navController.navigate("BookClubScreen")
                                                }
                                            },
                                            label = {
                                                Text(text = item.title)
                                            },
                                            icon = {
                                                BadgedBox(badge = {}) {
                                                    if (selectedItemIndex == index) {
                                                        Icon(
                                                            imageVector = item.selectedIcon,
                                                            contentDescription = null
                                                        )
                                                    } else {
                                                        Icon(
                                                            imageVector = item.unselectedIcon,
                                                            contentDescription = null
                                                        )
                                                    }
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        Column(modifier = Modifier
                            .padding(innerPadding)){
                            // Use innerPadding to position your content
                            BookAppNavHost(navController = navController)

                        }
                    }
                }
            }
        }
    }

@Composable
fun BookAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = bookapp.login.route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable(bookapp.login.route) {
            LoginScreen(onLoginSuccess = {
                // Navigate to the desired destination after a successful login
                navController.navigate("BookScreen")
            })
        }
        composable("LanguageScreen") {
            LanguageScreen()
        }
        composable("BookScreen") {
            BookDetailNavHost()
        }
        composable("BookClubScreen") {
            BookClubScreen(navController = navController)
        }

        composable("SpecBookClubScreen") {
            SpecBookClubScreen()
        }
    }
}
}