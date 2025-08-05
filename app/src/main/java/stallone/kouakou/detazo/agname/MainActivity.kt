package stallone.kouakou.detazo.agname

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import stallone.kouakou.detazo.agname.data.session.UserSessionManager
import stallone.kouakou.detazo.agname.ui.components.BottomNavBar
import stallone.kouakou.detazo.agname.ui.navigation.AppNavGraph
import stallone.kouakou.detazo.agname.ui.theme.MyTikleTheme
import stallone.kouakou.detazo.agname.viewmodel.ArticleViewModel
import stallone.kouakou.detazo.agname.viewmodel.CartViewModel
import stallone.kouakou.detazo.agname.viewmodel.SaleViewModel
import stallone.kouakou.detazo.agname.viewmodel.UserViewModel

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    private val articleViewModel: ArticleViewModel by viewModels()
    private val saleViewModel: SaleViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userSessionManager: UserSessionManager
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userSessionManager = UserSessionManager(applicationContext) // <-- initialisation ici

        userViewModel.createDefaultAdminIfNotExists()

        setContent {
            MyTikleTheme {
                val navController = rememberNavController()

                val username by userSessionManager.userNameFlow.collectAsState(initial = null)
                val isAdmin by userSessionManager.isAdminFlow.collectAsState(initial = false)

                val startDestination = when {
                    username == null -> "login"
                    isAdmin -> "article_list"
                    else -> "article_list"
                }

                Scaffold(
                    bottomBar = {
                        if (username != null) BottomNavBar(navController = navController, isAdmin = isAdmin)
                    }
                ) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        viewModel = articleViewModel,
                        saleViewModel = saleViewModel,
                        userViewModel = userViewModel,
                        userSessionManager = userSessionManager,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = startDestination,
                        cartViewModel = cartViewModel,
                        isAdmin = isAdmin,
                    )
                }
            }
        }
    }
}