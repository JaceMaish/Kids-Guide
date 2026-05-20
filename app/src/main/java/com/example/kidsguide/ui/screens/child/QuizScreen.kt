package com.example.kidsguide.ui.screens.child

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

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

@Composable
fun QuizScreen(navController: NavController, lessonId: String) {
    val questions = quizData[lessonId] ?: emptyList()
    var currentQuestion by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf(-1) }
    var score by remember { mutableStateOf(0) }
    var quizFinished by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "✏️ Quiz Time!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!quizFinished) {
            val question = questions[currentQuestion]

            Text(
                text = "Question ${currentQuestion + 1} of ${questions.size}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF3E5F5)
                )
            ) {
                Text(
                    text = question.question,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            question.options.forEachIndexed { index, option ->
                val bgColor = when {
                    selectedAnswer == -1 -> Color.White
                    index == question.correctAnswer -> Color(0xFF4CAF50)
                    index == selectedAnswer -> Color(0xFFE53935)
                    else -> Color.White
                }

                Button(
                    onClick = {
                        if (selectedAnswer == -1) {
                            selectedAnswer = index
                            if (index == question.correctAnswer) score++
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = bgColor)
                ) {
                    Text(
                        text = option,
                        color = if (bgColor == Color.White) Color.Black else Color.White,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE)
                    )
                ) {
                    Text(
                        text = if (currentQuestion < questions.size - 1)
                            "Next Question →" else "See Results 🎉",
                        fontSize = 16.sp
                    )
                }
            }

        } else {
            // Results Screen
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "🎉 Quiz Complete!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Your Score",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Text(
                text = "$score / ${questions.size}",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = if (score == questions.size) Color(0xFF4CAF50) else Color(0xFF6200EE)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when {
                    score == questions.size -> "Perfect Score! 🌟"
                    score >= questions.size / 2 -> "Good Job! Keep it up! 👍"
                    else -> "Keep practicing! You can do it! 💪"
                },
                fontSize = 18.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("child_home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE)
                )
            ) {
                Text(text = "Back to Lessons 📚", fontSize = 16.sp)
            }
        }
    }
}