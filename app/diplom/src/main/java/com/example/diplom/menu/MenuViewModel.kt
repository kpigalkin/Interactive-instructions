
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.diplom.navigation.Routes

class MenuViewModel(navController: NavHostController): ViewModel() {
    private lateinit var navController: NavHostController

    init {
        this.navController = navController
    }

    fun onScanButtonClick() {
        navController.navigate(Routes.Scan.route)
    }

    fun onInstructionsButtonClick() {
        navController.navigate(Routes.Instructions.route)
    }

    fun onLogsButtonClick() {
        navController.navigate(Routes.Logs.route)
    }
}