package hu.ait.bookapp.navigation

sealed class bookapp(val route: String) {
    object login : bookapp("loginscreen")
    object BookClubScreen : bookapp("bookclubscreen")
    object LanguageScreen : bookapp("languagescreen")
    object BookScreen : bookapp("BookScreen")
//    object navigation : bookapp("MainNavigation")
}