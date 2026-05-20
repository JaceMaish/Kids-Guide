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
fun ParentDashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = " Parent Dashboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF03DAC5)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Monitor your child's progress",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Summary Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryCard(
                title = "Lessons\nCompleted",
                value = "3/5",
                color = Color(0xFF6200EE)
            )
            SummaryCard(
                title = "Average\nScore",
                value = "75%",
                color = Color(0xFF03DAC5)
            )
            SummaryCard(
                title = "Quizzes\nTaken",
                value = "3",
                color = Color(0xFFFF6D00)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "📊 Progress by Subject",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProgressCard(" Reading & Comprehension", 0.8f, "80%")
        Spacer(modifier = Modifier.height(12.dp))
        ProgressCard(" Mathematics", 0.6f, "60%")
        Spacer(modifier = Modifier.height(12.dp))
        ProgressCard(" Social Studies", 0.9f, "90%")
        Spacer(modifier = Modifier.height(12.dp))
        ProgressCard(" Science", 0.5f, "50%")
        Spacer(modifier = Modifier.height(12.dp))
        ProgressCard(" Creative Arts", 0.7f, "70%")

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("progress_report") },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF03DAC5)
            )
        ) {
            Text(text = "View Full Report 📋", fontSize = 16.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("role_select") }) {
            Text(text = "← Logout", color = Color.Gray)
        }
    }
}

@Composable
fun SummaryCard(title: String, value: String, color: Color) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(90.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = title,
                fontSize = 11.sp,
                color = Color.White,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun ProgressCard(subject: String, progress: Float, percentage: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = subject, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = percentage, fontSize = 14.sp, color = Color(0xFF6200EE))
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(0xFF6200EE),
                trackColor = Color(0xFFE0E0E0)
            )
        }
    }
}