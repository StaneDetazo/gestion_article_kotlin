package stallone.kouakou.detazo.agname.ui.components

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import stallone.kouakou.detazo.agname.ui.navigation.Routes

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun BottomNavBar(navController: NavHostController, isAdmin: Boolean) {
    val items = if (isAdmin) {
        listOf(
            BottomNavItem("Articles", Icons.Default.List, Routes.LIST),
            BottomNavItem("Ventes", Icons.Default.ShoppingCart, "sales"),
            BottomNavItem("Profil", Icons.Default.AccountCircle, "admin_home"),
        )
    } else {
        listOf(
            BottomNavItem("Articles", Icons.Default.List, "article_list"),
            BottomNavItem("Panier", Icons.Default.ShoppingCart, "cart"),
            BottomNavItem("Profil", Icons.Default.AccountCircle, "client_home")
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                }
            )
        }
    }
}
