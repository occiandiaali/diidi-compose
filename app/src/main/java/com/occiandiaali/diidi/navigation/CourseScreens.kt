package com.occiandiaali.diidi.navigation

enum class CourseScreens {
    HomeScreen,
    DetailScreen;
    companion object {
        fun fromRoute(route: String?) : CourseScreens
        = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            DetailScreen.name -> DetailScreen
            null -> HomeScreen
            else -> throw java.lang.IllegalArgumentException("$route route is unknown")
        }
    }
}