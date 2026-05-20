package com.example.kidsguide.ui.screens.parent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun ProgressReportScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "📋 Full Progress Report",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF03DAC5)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Detailed breakdown of performance",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Report Cards
        ReportCard(
            subject = " Reading & Comprehension",
            lessonsRead = "1/1",
            quizScore = "2/2",
            grade = "A",
            gradeColor = Color(0xFF4CAF50)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReportCard(
            subject = " Mathematics",
            lessonsRead = "1/1",
            quizScore = "1/2",
            grade = "C",
            gradeColor = Color(0xFFFF9800)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReportCard(
            subject = " Social Studies",
            lessonsRead = "1/1",
            quizScore = "2/2",
            grade = "A",
            gradeColor = Color(0xFF4CAF50)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReportCard(
            subject = " Science",
            lessonsRead = "0/1",
            quizScore = "0/2",
            grade = "N/A",
            gradeColor = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReportCard(
            subject = " Creative Arts",
            lessonsRead = "1/1",
            quizScore = "1/2",
            grade = "B",
            gradeColor = Color(0xFF2196F3)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Overall Grade
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF6200EE)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Overall Performance",
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "B+",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "75% Average Score",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text(text = "← Back to Dashboard", color = Color.Gray)
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
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subject,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Lessons: $lessonsRead",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Quiz: $quizScore",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Text(
                text = grade,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = gradeColor
            )
        }
    }
}