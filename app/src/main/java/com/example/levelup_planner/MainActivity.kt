package com.example.levelup_planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.levelup_planner.ui.theme.LevelUp_PlannerTheme

//Added Imports
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            LevelUp_PlannerTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
            MyTextBox()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//Username Textbox Test
@Composable
fun MyTextBox() {
    //hold typing name in variable
    var typingName by remember { mutableStateOf("") }

    //hold final inputted name
    var savedName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier.weight(.5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            //input text field
            TextField(
                value = typingName, //what is currently shown
                onValueChange = { newText -> typingName = newText }, //update
                label = { Text("Enter your name") } //floating label
            )

        }
        //button to submit name
        Button(onClick = {
            savedName = typingName
        }) {
            Text("Sumbit")
        }
        Text(
            text = "Username: $savedName"
        )
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LevelUp_PlannerTheme {
        Greeting("Android")
    }
}