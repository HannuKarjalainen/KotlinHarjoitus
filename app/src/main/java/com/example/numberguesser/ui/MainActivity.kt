package com.example.numberguesser.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.numberguesser.ui.theme.NumberguesserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberguesserTheme {
                ScaffoldNavigationApp()
            }
        }
    }
}