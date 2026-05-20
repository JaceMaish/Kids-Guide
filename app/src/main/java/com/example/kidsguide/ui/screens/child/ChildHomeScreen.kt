package com.example.kidsguide.ui.screens.child

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ChildHomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "My Lessons",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Choose a lesson to Start reading!",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))

        LessonCard(
            title = "Reading & Comprehesion",
            description = "Improve your reading and understanding skills",
            onClick = { navController.navigate("lesson/1") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LessonCard(
            title = "Mathematics",
            description = "Practice numbers, algebra and problem solving",
            onClick = { navController.navigate("lesson/2") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LessonCard(
            title = "Science",
            description = "Explore biology, chemistry and physics",
            onClick = { navController.navigate("lesson/3") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LessonCard(
            title = "Social Studies",
            description = "Learn about society, history and Geography",
            onClick = { navController.navigate("lesson/4") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LessonCard(
            title = "Creative Arts",
            description = "Express yourself through art and creativity",
            onClick = { navController.navigate("lesson/5") }
        )
        Spacer(modifier = Modifier.height(32.dp))

    }
}

@Composable
fun LessonCard(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor =Color(0xFFF3E5F5)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}