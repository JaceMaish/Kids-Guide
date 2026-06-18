package com.example.kidsguide.ui.screens.parent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kidsguide.ui.viewmodels.QuizViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressReportScreen(
    navController: NavController,
    quizViewModel: QuizViewModel = viewModel()
) {
    val results by quizViewModel.quizResults
    

    val avgScore = if (results.isNotEmpty()) {
        (results.sumOf { it.score }.toFloat() / results.sumOf { it.totalQuestions }) * 100
    } else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Progress Report", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share functionality */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Overall Grade Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF6200EE), Color(0xFF9C27B0))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Overall Performance",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = when {
                            avgScore >= 90 -> "A"
                            avgScore >= 80 -> "B+"
                            avgScore >= 70 -> "B"
                            avgScore >= 60 -> "C"
                            avgScore > 0 -> "D"
                            else -> "N/A"
                        },
                        fontSize = 72.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "${avgScore.toInt()}% Average Score",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Subject Breakdown",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val subjects = mapOf(
                "1" to Pair("Reading & Comprehension", Color(0xFF4CAF50)),
                "2" to Pair("Mathematics", Color(0xFFFF9800)),
                "3" to Pair("Social Studies", Color(0xFF4CAF50)),
                "4" to Pair("Science", Color(0xFF2196F3)),
                "5" to Pair("Creative Arts", Color(0xFFE91E63))
            )

            subjects.forEach { (id, data) ->
                val (name, color) = data
                val subjectResults = results.filter { it.lessonId == id }
                if (subjectResults.isNotEmpty()) {
                    val maxScore = subjectResults.maxOf { it.score }
                    val total = subjectResults.first().totalQuestions
                    val grade = when {
                        maxScore.toFloat() / total >= 0.9 -> "A"
                        maxScore.toFloat() / total >= 0.8 -> "B+"
                        maxScore.toFloat() / total >= 0.7 -> "B"
                        else -> "C"
                    }
                    ReportCard(name, "1/1", "$maxScore/$total", grade, color)
                } else {
                    ReportCard(name, "0/1", "0/0", "N/A", Color.Gray)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ReportCard(
    subject: String,
    lessonsRead: String,
    quizScore: String,
    grade: String,
    gradeColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subject,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Lessons: $lessonsRead", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Quiz: $quizScore", fontSize = 12.sp, color = Color.Gray)
                }
            }
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(gradeColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = grade,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = gradeColor
                )
            }
        }
    }
}
