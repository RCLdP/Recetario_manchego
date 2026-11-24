package com.example.recetariomanchego

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recetariomanchego.ui.FichaRecetaScreen
import com.example.recetariomanchego.ui.ListaRecetasScreen
import com.example.recetariomanchego.ui.RecetasViewModel
import com.example.recetariomanchego.ui.theme.RecetarioManchegoTheme
import java.util.Locale

sealed class Screen(val route: String) {
    data object Lista : Screen("lista")
    data object Ficha : Screen("ficha/{id}") {
        fun createRoute(id: Int): String = "ficha/$id"
    }
}

@Composable
fun AppNavigation(
    idiomaActual: String,
    onCambiarIdioma: () -> Unit,
    viewModel: RecetasViewModel = viewModel()
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Lista.route
    ) {
        composable(Screen.Lista.route) {
            ListaRecetasScreen(
                viewModel = viewModel,
                idiomaActual = idiomaActual,
                onCambiarIdioma = onCambiarIdioma,
                onVerFicha = { id ->
                    navController.navigate(Screen.Ficha.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.Ficha.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            val recetas by viewModel.recetas.collectAsStateWithLifecycle()
            val receta = recetas.firstOrNull { it.id == id }

            if (receta != null) {
                FichaRecetaScreen(
                    receta = receta,
                    onVolver = { navController.popBackStack() },
                    onAlternarFavorita = { viewModel.alternarFavorita(receta.id) }
                )
            } else {
                Text(
                    text = stringResource(R.string.receta_no_encontrada),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    companion object {
        var idiomaSeleccionado: String = "es"
    }

    override fun attachBaseContext(newBase: Context) {
        val locale = Locale.forLanguageTag(idiomaSeleccionado)
        Locale.setDefault(locale)
        val config = Configuration(newBase.resources.configuration).apply {
            setLocale(locale)
        }
        applyOverrideConfiguration(config)
        super.attachBaseContext(newBase)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecetarioManchegoTheme {
                AppNavigation(
                    idiomaActual = idiomaSeleccionado,
                    onCambiarIdioma = {
                        idiomaSeleccionado = when (idiomaSeleccionado) {
                            "es" -> "en"
                            "en" -> "de"
                            else -> "es"
                        }
                        recreate()
                    }
                )
            }
        }
    }
}