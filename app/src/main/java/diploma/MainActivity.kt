
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diploma.ui.theme.DiplomaTheme
import com.example.diplom.auth.Auth
import com.example.diplom.instructions.Instructions
import com.example.diplom.menu.Menu
import com.example.diplom.routes.Routes

public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                DiplomaTheme {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        ScreenMain()
                    }
                }
            }
    }
}

@Composable
fun ScreenMain() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "menu") {

        composable(com.example.diplom.routes.Routes.Menu.route) {
            com.example.diplom.menu.Menu(navController = navController)
        }

        composable(com.example.diplom.routes.Routes.Instructions.route) {
            com.example.diplom.instructions.Instructions()
        }

        composable(com.example.diplom.routes.Routes.Auth.route) {
            com.example.diplom.auth.Auth()
        }
    }
}