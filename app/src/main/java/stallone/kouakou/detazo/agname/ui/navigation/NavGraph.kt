package stallone.kouakou.detazo.agname.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import stallone.kouakou.detazo.agname.data.session.UserSessionManager
import stallone.kouakou.detazo.agname.ui.screens.AdminHomeScreen
import stallone.kouakou.detazo.agname.ui.screens.ArticleFormScreen
import stallone.kouakou.detazo.agname.ui.screens.ArticleListScreen
import stallone.kouakou.detazo.agname.ui.screens.ClientHomeScreen
import stallone.kouakou.detazo.agname.ui.screens.LoginScreen
import stallone.kouakou.detazo.agname.ui.screens.RegisterScreen
import stallone.kouakou.detazo.agname.ui.screens.SaleScreen
import stallone.kouakou.detazo.agname.ui.screens.cart.CartScreen
//import stallone.kouakou.detazo.agname.ui.screens.sales.SalesScreen
import stallone.kouakou.detazo.agname.viewmodel.ArticleViewModel
import stallone.kouakou.detazo.agname.viewmodel.CartViewModel
import stallone.kouakou.detazo.agname.viewmodel.SaleViewModel
import stallone.kouakou.detazo.agname.viewmodel.UserViewModel

object Routes {
    const val LIST = "article_list"
    const val FORM = "article_form"
    const val EDIT = "article_edit"
    const val LOGIN = "login"
    const val REGISTER = "register"
}

@ExperimentalMaterial3Api
@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: ArticleViewModel,
    saleViewModel: SaleViewModel,
    userViewModel: UserViewModel,
    userSessionManager: UserSessionManager,
    modifier: Modifier = Modifier,
    startDestination: String = "login",
    cartViewModel: CartViewModel,
    isAdmin: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.LOGIN) {
           // val userSessionManager = UserSessionManager(context = LocalContext.current)
            LoginScreen(navController, userViewModel, userSessionManager)
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController, userViewModel)
        }

        composable("admin_home") {
            // Écran admin simple pour l’instant
            AdminHomeScreen(navController, userViewModel, userSessionManager)
        }

        composable("client_home") {
            // Écran client simple pour l’instant
            ClientHomeScreen(navController, userViewModel, userSessionManager)
        }

        composable("cart") {
            CartScreen(
                cartViewModel = cartViewModel,
                articleViewModel = viewModel,
                saleViewModel = saleViewModel
            ) {
                // Logique de paiement ici
            }
        }

        composable(Routes.LIST) {
            ArticleListScreen(
                articleViewModel = viewModel,
                cartViewModel = cartViewModel,
                onAddClick = { navController.navigate(Routes.FORM) },
                onArticleClick = { id -> navController.navigate("${Routes.EDIT}/$id") },
                isAdmin = isAdmin // <-- ajoute-le ici
            )
        }
        composable(Routes.FORM) {
            ArticleFormScreen(viewModel = viewModel, onArticleSaved = { navController.popBackStack() }, onNavigateBack = { navController.popBackStack() })
        }

        composable("${Routes.EDIT}/{articleId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("articleId")?.toIntOrNull()
            val article = viewModel.allArticles.value?.find { it.id == id }
            if (article != null) {
                ArticleFormScreen(viewModel = viewModel, existingArticle = article, onArticleSaved = { navController.popBackStack() })
            }
        }

        composable("sales") {
            SaleScreen(saleViewModel = saleViewModel)
        }
//
//        composable("vente") {
//            SaleScreen(navController = navController, articleViewModel = viewModel, saleViewModel = saleViewModel)
//        }
    }
}
