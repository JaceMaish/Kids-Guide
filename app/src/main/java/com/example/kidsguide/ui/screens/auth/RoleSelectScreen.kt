package com.example.kidsguide.ui.screens.auth

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kidsguide.ui.viewmodels.AuthViewModel

@Composable
fun RoleSelectScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        visible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + expandVertically()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "KidsGuide",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF6200EE)
                )

                Text(
                    text = "Empowering young minds",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Select your role to continue",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Child Button
        RoleButton(
            title = "I am a Child",
            subtitle = "Start learning and taking quizzes",
            icon = "👦",
            color = Color(0xFF6200EE),
            onClick = { 
                if (!viewModel.tryAutoLogin("child")) {
                    navController.navigate("child_login") 
                } else {
                    navController.navigate("child_home") {
                        popUpTo("role_select") { inclusive = true }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Parent Button
        RoleButton(
            title = "I am a Parent",
            subtitle = "Monitor progress and reports",
            icon = "👨‍👩‍👧",
            color = Color(0xFF03DAC5),
            textColor = Color.Black,
            onClick = { 
                if (!viewModel.tryAutoLogin("parent")) {
                    navController.navigate("parent_login") 
                } else {
                    navController.navigate("parent_dashboard") {
                        popUpTo("role_select") { inclusive = true }
                    }
                }
            }
        )
    }
}

@Composable
fun RoleButton(
    title: String,
    subtitle: String,
    icon: String,
    color: Color,
    textColor: Color = Color.White,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = icon,
                fontSize = 40.sp,
                modifier = Modifier.padding(end = 16.dp)
            )
            Column {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = textColor.copy(alpha = 0.8f)
                )
            }
        }
    }
}
