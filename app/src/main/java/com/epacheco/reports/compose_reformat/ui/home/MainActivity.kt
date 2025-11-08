package com.epacheco.reports.compose_reformat.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReportsGoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

// Define your navigation destinations
sealed class Screen(val route: String, val icon: @Composable () -> Unit, val label: String) {
    fun fromPath(path: String): Screen? {
        val name = if (path.contains("/")) {
            path.split("/").first()
        } else if (path.contains("?")) {
            path.split("?").first()
        } else path
        return when (name) {
            "Home" -> Home
            "Favorites" -> Favorites
            "Settings" -> Settings
            else -> null
        }
    }

    object Home : Screen("home", { Icon(Icons.Filled.Home, contentDescription = "Home") }, "Home")
    object Favorites : Screen(
        "favorites",
        { Icon(Icons.Filled.Favorite, contentDescription = "Favorites") },
        "Favorites"
    )


    object Settings : Screen(
        "settings",
        { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
        "Settings"
    )

    object Profile : Screen(
        "profile",
        { Icon(Icons.Filled.AddCircle, contentDescription = "profile") },
        "profile"
    )
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Favorites, Screen.Settings)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute.toString() in listOf(
        Screen.Home.fromPath("Home")?.route,
        Screen.Favorites.fromPath("Favorites")?.route,
        Screen.Settings.fromPath("Settings")?.route
    )



    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }), // Slide in from bottom
                exit = slideOutVertically(targetOffsetY = { it }) // Slide out to bottom
            ) {
                NavigationBar {

                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = screen.icon,
                            label = { Text(screen.label) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }


        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Favorites.route) { FavoritesScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Profile Screen Content")
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Home Screen Content")
        Button({
            navController.navigate(Screen.Profile.route)
        }) {
            Text("Profile")
        }

    }
}

@Composable
fun FavoritesScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Favorites Screen Content")
    }
}

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Settings Screen Content")
    }
}