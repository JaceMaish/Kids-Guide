package com.example.kidsguide.ui.screens.child

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kidsguide.ui.viewmodels.QuizViewModel

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: Int
)

val quizData = mapOf(
    "1" to listOf(
        QuizQuestion(
            "What is the main idea of a passage?",
            listOf("The first sentence", "The central topic", "The last sentence", "The title"),
            1
        ),
        QuizQuestion(
            "What does comprehension mean?",
            listOf("Reading fast", "Writing well", "Understanding what you read", "Memorizing text"),
            2
        )
    ),
    "2" to listOf(
        QuizQuestion(
            "What is algebra?",
            listOf("Study of shapes", "Study of numbers and symbols", "Study of graphs", "Study of angles"),
            1
        ),
        QuizQuestion(
            "What is the value of x if x + 5 = 10?",
            listOf("3", "4", "5", "6"),
            2
        )
    ),
    "3" to listOf(
        QuizQuestion(
            "What does geography study?",
            listOf("History", "Earth's features", "Science experiments", "Mathematics"),
            1
        ),
        QuizQuestion(
            "What is citizenship?",
            listOf("Being a student", "Being a member of a country", "Traveling abroad", "Studying maps"),
            1
        )
    ),
    "4" to listOf(
        QuizQuestion(
            "What does biology study?",
            listOf("Forces", "Matter", "Living things", "Energy"),
            2
        ),
        QuizQuestion(
            "What is a chemical reaction?",
            listOf("Mixing colors", "Change producing new substances", "Drawing molecules", "Measuring mass"),
            1
        )
    ),
    "5" to listOf(
        QuizQuestion(
            "What is the purpose of Creative Arts?",
            listOf("Memorizing facts", "Self expression", "Solving equations", "Reading maps"),
            1
        ),
        QuizQuestion(
            "Which of these is a Creative Art?",
            listOf("Algebra", "Geography", "Drama", "Biology"),
            2
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    navController: NavController,
    lessonId: String,
    quizViewModel: QuizViewModel = viewModel()
) {
    val questions = quizData[lessonId] ?: emptyList()
    var currentQuestion by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf(-1) }
    var score by remember { mutableStateOf(0) }
    var quizFinished by remember { mutableStateOf(false) }

    LaunchedEffect(quizFinished) {
        if (quizFinished) {
            quizViewModel.saveResult(lessonId, score, questions.size)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = Color(0xFFFBFBFE) // Soft, off-white background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!quizFinished) {
                val question = questions[currentQuestion]

                LinearProgressIndicator(
                    progress = { (currentQuestion + 1).toFloat() / questions.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFF6200EE),
                    trackColor = Color(0xFFE0E0E0)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Question ${currentQuestion + 1} of ${questions.size}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = question.question,
                        modifier = Modifier.padding(24.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                question.options.forEachIndexed { index, option ->
                    val isCorrect = index == question.correctAnswer
                    val isSelected = index == selectedAnswer
                    
                    val containerColor = when {
                        selectedAnswer == -1 -> Color.White
                        isCorrect -> Color(0xFFE8F5E9) // Soft green
                        isSelected -> Color(0xFFFFEBEE) // Soft red
                        else -> Color.White
                    }
                    
                    val contentColor = when {
                        selectedAnswer == -1 -> Color.DarkGray
                        isCorrect -> Color(0xFF2E7D32)
                        isSelected -> Color(0xFFC62828)
                        else -> Color.LightGray
                    }

                    val borderColor = when {
                        selectedAnswer == -1 -> Color(0xFFEEEEEE)
                        isCorrect -> Color(0xFF4CAF50)
                        isSelected -> Color(0xFFE53935)
                        else -> Color(0xFFEEEEEE)
                    }

                    Surface(
                        onClick = {
                            if (selectedAnswer == -1) {
                                selectedAnswer = index
                                if (isCorrect) score++
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = containerColor,
                        border = androidx.compose.foundation.BorderStroke(2.dp, borderColor),
                        shadowElevation = if (selectedAnswer == -1) 1.dp else 0.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${'A' + index}.",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = contentColor
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = option,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = contentColor
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                if (selectedAnswer != -1) {
                    Button(
                        onClick = {
                            if (currentQuestion < questions.size - 1) {
                                currentQuestion++
                                selectedAnswer = -1
                            } else {
                                quizFinished = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6200EE)
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text(
                            text = if (currentQuestion < questions.size - 1)
                                "Next Question →" else "Finish Quiz 🎉",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            } else {
                // Results Screen
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        modifier = Modifier.size(120.dp),
                        shape = androidx.compose.foundation.shape.CircleShape,
                        color = Color(0xFF6200EE).copy(alpha = 0.1f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "🎯", fontSize = 64.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Quiz Complete!",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF6200EE)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "You scored",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = "$score / ${questions.size}",
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Black,
                        color = if (score == questions.size) Color(0xFF4CAF50) else Color(0xFF6200EE)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = when {
                            score == questions.size -> "Perfect Score! You're a star! 🌟"
                            score >= questions.size / 2 -> "Great effort! Keep it up! 👍"
                            else -> "Don't give up! Keep practicing! 💪"
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    Button(
                        onClick = { navController.navigate("child_home") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6200EE)
                        )
                    ) {
                        Text(text = "Back to Adventures 🚀", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}