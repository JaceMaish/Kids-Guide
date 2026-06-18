package com.example.kidsguide.navigation


import androidx.compose.runtime.Composable
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kidsguide.ui.screens.auth.LoginScreen
import com.example.kidsguide.ui.screens.auth.RegisterScreen
import com.example.kidsguide.ui.screens.auth.RoleSelectScreen
import com.example.kidsguide.ui.screens.child.ChildHomeScreen
import com.example.kidsguide.ui.screens.child.LessonScreen
import com.example.kidsguide.ui.screens.child.QuizScreen
import com.example.kidsguide.ui.screens.parent.ParentDashboardScreen
import com.example.kidsguide.ui.screens.parent.ProgressReportScreen
import com.example.kidsguide.ui.viewmodels.AuthState
import com.example.kidsguide.ui.viewmodels.AuthViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: AuthViewModel = viewModel()
    val authState by viewModel.authState


    // Global back handling: always return to start screen when back is pressed
    BackHandler {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        if (currentRoute != "role_select") {
            navController.navigate("role_select") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    // Reset to start screen on app resume (new or continuing instance)
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val cur = navController.currentBackStackEntry?.destination?.route
                if (cur != "role_select") {
                    navController.navigate("role_select") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    NavHost(
        navController = navController,
        startDestination = "role_select"
    ) {


        composable("role_select") {
            RoleSelectScreen(navController, viewModel = viewModel)
        }

        composable("child_login") {
            LoginScreen(navController, role = "child", viewModel = viewModel)
        }

        composable("parent_login") {
            LoginScreen(navController, role = "parent", viewModel = viewModel)
        }

        composable(
            route = "register/{role}",
            arguments = listOf(navArgument("role") { type = NavType.StringType })
        ) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "child"
            RegisterScreen(navController, role, viewModel = viewModel)
        }

        composable("child_home") {
            ChildHomeScreen(navController)
        }

        composable(
            route = "lesson/{lessonId}",
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: "1"
            LessonScreen(navController, lessonId)
        }

        composable(
            route = "quiz/{lessonId}",
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: "1"
            QuizScreen(navController, lessonId)
        }

        composable("parent_dashboard") {
            ParentDashboardScreen(navController, authViewModel = viewModel)
        }

        composable("progress_report") {
            ProgressReportScreen(navController)
        }
    }
}