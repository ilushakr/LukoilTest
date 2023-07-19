package com.example.lukoiltest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.lukoiltest.presentation.navigation.Navigation
import com.example.lukoiltest.presentation.ui.theme.LukoilTestTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LukoilTestTheme {
                Navigation()
            }
        }
    }


}
