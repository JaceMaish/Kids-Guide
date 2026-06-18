package com.example.kidsguide.ui.screens.parent


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kidsguide.ui.viewmodels.AuthViewModel
import com.example.kidsguide.ui.viewmodels.QuizViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentDashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    quizViewModel: QuizViewModel = viewModel()
) {
    val results by quizViewModel.quizResults
    
    val avgScore = if (results.isNotEmpty()) {
        (results.sumOf { it.score }.toFloat() / results.sumOf { it.totalQuestions }) * 100
    } else 0f

    val lessonsCompleted = results.map { it.lessonId }.distinct().size

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text("Parent Dashboard", fontWeight = FontWeight.Bold)
                        Text("Monitor your child's growth", fontSize = 14.sp, fontWeight = FontWeight.Normal)
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        authViewModel.logout()
                        navController.navigate("role_select") {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
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

            // Summary Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Completed",
                    value = "$lessonsCompleted/5",
                    icon = Icons.Default.CheckCircle,
                    color = Color(0xFF6200EE),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Avg. Score",
                    value = "${avgScore.toInt()}%",
                    icon = Icons.Default.Star,
                    color = Color(0xFF03DAC5),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            SectionHeader(title = "Subject Progress", icon = Icons.Default.BarChart)

            Spacer(modifier = Modifier.height(16.dp))

            val subjects = mapOf(
                "1" to "Reading & Comprehension",
                "2" to "Mathematics",
                "3" to "Social Studies",
                "4" to "Science",
                "5" to "Creative Arts"
            )

            subjects.forEach { (id, name) ->
                val subjectResults = results.filter { it.lessonId == id }
                val progress = if (subjectResults.isNotEmpty()) {
                    subjectResults.maxOf { it.score }.toFloat() / subjectResults.first().totalQuestions
                } else 0f
                
                ProgressCard(name, progress, "${(progress * 100).toInt()}%", Color(0xFF6200EE))
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("progress_report") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5))
            ) {
                Icon(Icons.Default.Description, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generate Detailed Report", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = color)
            Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = color)
            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
        }
    }
}

@Composable
fun SectionHeader(title: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}

@Composable
fun ProgressCard(subject: String, progress: Float, percentage: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = subject, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Surface(
                    color = color.copy(alpha = 0.1f),
                    shape = CircleShape
                ) {
                    Text(
                        text = percentage,
                        fontSize = 12.sp,
                        color = color,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = color,
                trackColor = color.copy(alpha = 0.1f)
            )
        }
    }
}
