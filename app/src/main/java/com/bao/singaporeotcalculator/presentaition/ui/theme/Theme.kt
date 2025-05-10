package com.bao.singaporeotcalculator.presentaition.ui.theme

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bao.singaporeotcalculator.utils.ThemePreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
    /*   primary = Purple6D63BC,
       secondary = PurpleBlue797dd6,
       tertiary = PurpleLightC4C2F3*/

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// State to hold the current theme mode
enum class ThemeMode {
    LIGHT, DARK
}

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val _currentTheme = MutableStateFlow(ThemeMode.DARK)
    val currentTheme = _currentTheme.asStateFlow()

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext

    init {
        viewModelScope.launch {
            ThemePreferenceManager.getThemeFlow(context).collect { isDark ->
                _currentTheme.update { if (isDark) ThemeMode.DARK else ThemeMode.LIGHT }
            }
        }
    }

    fun setTheme(themeMode: ThemeMode) {
        _currentTheme.update { themeMode }
        val isDarkMode = themeMode != ThemeMode.LIGHT
        viewModelScope.launch {
            ThemePreferenceManager.saveTheme(context, isDarkMode)
        }
    }
}


@Composable
fun SingaporeOtCalculatorTheme(
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    themeViewModel: ThemeViewModel = viewModel(),
    content: @Composable () -> Unit,
) {

    val themeMode by themeViewModel.currentTheme.collectAsState()

    // Determine if dark theme should be used based on themeMode
    val darkTheme = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}