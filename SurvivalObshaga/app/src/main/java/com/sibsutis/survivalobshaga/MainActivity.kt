package com.sibsutis.survivalobshaga

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.sibsutis.survivalobshaga.game.GameScreen
import com.sibsutis.survivalobshaga.ui.theme.SurvivalObshagaTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SurvivalObshagaTheme {
                GameScreen()
            }
        }
    }
}