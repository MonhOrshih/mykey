package com.example.test
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test.ui.theme.TestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var text by remember { mutableStateOf("") }
    var currentButton by remember { mutableStateOf<String?>(null) }
    var hoveredLetters by remember { mutableStateOf<List<String>>(emptyList()) }
    var letterIndex by remember { mutableStateOf(0) }
    var lastPressTime by remember { mutableStateOf(0L) }

    val buttonSize = screenWidth / 5 // Товчлууруудыг багтаахын тулд хэмжээ өөрчлөв

    val buttonMap = mapOf(
        "1" to listOf("А", "Б", "В", "Г", "Д", "1", "9"),
        "2" to listOf("Е", "Ё", "Ж", "З", "И", "2", "0"),
        "3" to listOf("Й", "К", "Л", "М", "Н", "3", "-"),
        "4" to listOf("О", "Ө", "П", "Р", "С", "4", ":"),
        "5" to listOf("Т", "У", "Ү", "Ф", "Х", "5", ";"),
        "6" to listOf("Ц", "Ч", "Ш", "Щ", "Ь", "6", "\""),
        "7" to listOf("Э", "Ю", "Я", "@", "%", "7", "!"),
        "8" to listOf(" ", ".", ",", "?", "!", "Том жижиг үсэг солих товч")
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for ((key, letters) in buttonMap.entries.take(4)) {
                    Button(
                        onClick = {
                            val currentTime = System.currentTimeMillis()
                            if (currentButton == key && (currentTime - lastPressTime) < 500) {
                                letterIndex = (letterIndex + 1) % letters.size
                                text = text.dropLast(1) + letters[letterIndex]
                            } else {
                                currentButton = key
                                letterIndex = 0
                                text += letters[letterIndex]
                            }
                            lastPressTime = currentTime
                        },
                        modifier = Modifier
                            .size(buttonSize)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        currentButton = key
                                        hoveredLetters = letters
                                        letterIndex = 0
                                        tryAwaitRelease()
                                        text += letters[letterIndex]
                                        currentButton = null
                                        hoveredLetters = emptyList()
                                    }
                                )
                            },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C3E50))
                    ) {
                        Text(
                            text = letters[0],
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = text,
            onValueChange = {},
            label = { Text("Enter text") },
            modifier = Modifier.padding(16.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for ((key, letters) in buttonMap.entries.drop(4)) {
                    Button(
                        onClick = {
                            val currentTime = System.currentTimeMillis()
                            if (currentButton == key && (currentTime - lastPressTime) < 500) {
                                letterIndex = (letterIndex + 1) % letters.size
                                text = text.dropLast(1) + letters[letterIndex]
                            } else {
                                currentButton = key
                                letterIndex = 0
                                text += letters[letterIndex]
                            }
                            lastPressTime = currentTime
                        },

                        modifier = Modifier
                            .size(buttonSize * 1.2f)  // Increase size by 1.5x
                            .height(150.dp)  // Increase height to 150.dp
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        currentButton = key
                                        hoveredLetters = letters
                                        letterIndex = 0
                                        tryAwaitRelease()
                                        text += letters[letterIndex]
                                        currentButton = null
                                        hoveredLetters = emptyList()
                                    }
                                )
                            },

                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C3E50))
                    )
                    {
                        Text(
                            text = letters[0],
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
