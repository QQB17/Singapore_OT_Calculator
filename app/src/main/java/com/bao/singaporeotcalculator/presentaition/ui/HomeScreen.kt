package com.bao.singaporeotcalculator.presentaition.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bao.singaporeotcalculator.R
import com.bao.singaporeotcalculator.presentaition.ui.theme.SingaporeOtCalculatorTheme
import com.bao.singaporeotcalculator.presentaition.ui.theme.ThemeMode
import com.bao.singaporeotcalculator.presentaition.ui.theme.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel = viewModel()
) {

    val themeModeState = themeViewModel.currentTheme.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text("Singapore OT Calculator")
                },
                actions = {
                    if (themeModeState == ThemeMode.DARK) {
                        IconButton(onClick = { themeViewModel.setTheme(ThemeMode.LIGHT) }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_light_mode_24px),
                                contentDescription = "Localized description"
                            )
                        }
                    } else if (themeModeState == ThemeMode.LIGHT) {
                        IconButton(onClick = {  themeViewModel.setTheme(ThemeMode.DARK)  }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_dark_mode_24px),
                                contentDescription = "Localized description"
                            )
                        }
                    }

                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },

        ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("Hello")
        }

    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    SingaporeOtCalculatorTheme {
        HomeScreen(rememberNavController())
    }
}