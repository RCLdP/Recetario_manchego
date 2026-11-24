package com.example.recetariomanchego.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recetariomanchego.R
import com.example.recetariomanchego.data.Receta
import com.example.recetariomanchego.data.recetasIniciales
import com.example.recetariomanchego.ui.theme.RecetarioManchegoTheme

@Composable
fun BloqueFoto(
    receta: Receta,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHighest
    ) {
        Image(
            painter = painterResource(id = receta.imagenId),
            contentDescription = stringResource(R.string.cd_foto_receta, receta.nombre),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun BloqueResumen(
    receta: Receta,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(
            R.string.resumen_receta,
            receta.tiempoMinutos,
            stringResource(receta.dificultad.etiquetaRes),
            receta.comarca
        ),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.fillMaxWidth()
    )
}
@Composable
fun BloqueIngredientes(
    ingredientes: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.titulo_ingredientes),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        ingredientes.forEach { ingrediente ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = ingrediente,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
@Composable
fun BloquePasos(
    pasos: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.titulo_elaboracion),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        pasos.forEachIndexed { index, paso ->
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Text(
                    text = paso,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun TarjetaReceta(
    receta: Receta,
    expandida: Boolean,
    onAlternar: () -> Unit,
    onAlternarFavorita: () -> Unit,
    onVerFicha: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorTarjeta by animateColorAsState(
        targetValue = if (expandida) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainerHighest
        },
        label = "colorTarjetaReceta"
    )

    val colorFavorita by animateColorAsState(
        targetValue = if (receta.favorita) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        label = "colorFavoritaTarjeta"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Card(
            onClick = onAlternar,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorTarjeta,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BloqueFoto(
                    receta = receta,
                    modifier = Modifier.size(72.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = receta.nombre,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(
                            R.string.resumen_corto,
                            receta.tiempoMinutos,
                            stringResource(receta.dificultad.etiquetaRes)
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onAlternarFavorita) {
                    Icon(
                        imageVector = if (receta.favorita) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = stringResource(
                            if (receta.favorita) {
                                R.string.quitar_favorita
                            } else {
                                R.string.marcar_favorita
                            }
                        ),
                        tint = colorFavorita
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = expandida,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.comarca_formato, receta.comarca),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onVerFicha,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.ver_receta_completa))
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Claro")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Oscuro")
@Preview(showBackground = true, locale = "en", name = "Inglés")
@Composable
private fun TarjetaRecetaPreview() {
    RecetarioManchegoTheme {
        TarjetaReceta(
            receta = recetasIniciales().first().copy(favorita = true),
            expandida = true,
            onAlternar = {},
            onAlternarFavorita = {},
            onVerFicha = {}
        )
    }
}