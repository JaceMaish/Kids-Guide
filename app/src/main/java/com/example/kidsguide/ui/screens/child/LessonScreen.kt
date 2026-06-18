package com.example.kidsguide.ui.screens.child

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

val lessons =mapOf(
      "1" to Pair(
          "Reading & Comprehension",
          "Reading comprehension is the ability to read text, process it and understand its meaning.\n\n" +
          "Key Skills:\n" +
          "- Understanding the main idea\n" +
          "- Identifying key details\n" +
          "- Making inferences\n" +
          "- Summarizing what you have read\n" +
          "Tip: Always try to read a passage twice before answering questions!"
      ),
    "2" to Pair(
        "Mathematics",
        "Mathematics is the study of numbers, shapes and patterns.\n\n" +
                "Topics Covered:\n" +
                "• Algebra and equations\n" +
                "• Geometry and shapes\n" +
                "• Statistics and probability\n" +
                "• Problem solving strategies\n\n" +
                "Tip: Practice daily to improve your speed and accuracy!"
    ),
    "3" to Pair(
        " Social Studies",
        "Social Studies helps us understand the world around us.\n\n" +
                "Topics Covered:\n" +
                "• History of our nation\n" +
                "• Geography and maps\n" +
                "• Government and citizenship\n" +
                "• Culture and society\n\n" +
                "Tip: Connect what you learn to real life events!"
    ),
    "4" to Pair(
        "Science",
        "Science is the study of the natural world through observation and experiment.\n\n" +
                "Topics Covered:\n" +
                "• Biology - living things\n" +
                "• Chemistry - matter and reactions\n" +
                "• Physics - forces and energy\n" +
                "• Environmental science\n\n" +
                "Tip: Always ask WHY and HOW things happen!"
    ),
    "5" to Pair(
        "Creative Arts",
        "Creative Arts develops your imagination and self expression.\n\n" +
                "Topics Covered:\n" +
                "• Drawing and painting\n" +
                "• Music and rhythm\n" +
                "• Drama and storytelling\n" +
                "• Design and craft\n\n" +
                "Tip: There are no mistakes in art, only new ideas!"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(navController: NavController, lessonId: String) {
    val lesson = lessons[lessonId]
    val title = lesson?.first ?: "Lesson"
    val content = lesson?.second ?: "Content not found"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lesson Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = Color(0xFF6200EE).copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Book, contentDescription = null, tint = Color(0xFF6200EE))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6200EE)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = content,
                    modifier = Modifier.padding(24.dp),
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("quiz/$lessonId") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(text = "Start Quiz ✏️", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
