package com.example.kidsguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kidsguide.navigation.NavGraph
import com.example.kidsguide.ui.theme.KidsGuideTheme
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KidsGuideTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}