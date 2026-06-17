package com.example.kidsguide.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kidsguide.ui.screens.auth.LoginScreen
import com.example.kidsguide.ui.screens.auth.RoleSelectScreen
import com.example.kidsguide.ui.screens.child.ChildHomeScreen
import com.example.kidsguide.ui.screens.child.LessonScreen
import com.example.kidsguide.ui.screens.child.QuizScreen
import com.example.kidsguide.ui.screens.parent.ParentDashboardScreen
import com.example.kidsguide.ui.screens.parent.ProgressReportScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "role_select"
    ) {
        composable("role_select") {
            RoleSelectScreen(navController)
        }

        composable("child_login") {
            LoginScreen(navController, role = "child")
        }

        composable("parent_login") {
            LoginScreen(navController, role = "parent")
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
            ParentDashboardScreen(navController)
        }

        composable("progress_report") {
            ProgressReportScreen(navController)
        }
    }
}