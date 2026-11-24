# Recetario Manchego

Aplicación Android nativa desarrollada en **Kotlin** y **Jetpack Compose** que muestra un recetario de cocina tradicional manchega (Proyecto UT2 - DAM).

## Funcionalidades

- **Listado de recetas:** Catálogo con 10 platos tradicionales manchegos con fotografía, tiempo de preparación y dificultad.
- **Tarjetas expandibles animadas:** Despliegue animado (`AnimatedVisibility`, `animateContentSize` y `animateColorAsState`) que muestra la comarca y el acceso a la receta completa.
- **Ficha de detalle adaptativa:** Muestra ingredientes y pasos numerados adaptando el diseño automáticamente a la orientación vertical u horizontal del dispositivo.
- **Recetas favoritas:** Permite marcar o desmarcar recetas como favoritas tanto desde la lista como desde la ficha, manteniendo el estado sincronizado y persistente al rotar la pantalla.
- **Buscador por nombre:** Filtrado en tiempo real insensible a mayúsculas y minúsculas con botón para limpiar la búsqueda.
- **Multiidioma:** Interfaz traducida a español (por defecto), inglés y alemán con selector de idioma en la barra superior.

## Tecnologías y arquitectura

- **Lenguaje:** Kotlin
- **UI:** Jetpack Compose + Material Design 3
- **Arquitectura:** MVVM con Flujo Unidireccional de Datos (UDF) y `StateFlow`
- **Navegación:** Navigation Compose con argumentos tipados
- **Versión mínima:** Android 7.0 (API 24)

## Estructura del proyecto

```text
com.example.recetariomanchego/
├── data/
│   ├── Receta.kt            # Modelo de datos inmutable y enum Dificultad
│   └── RecetasData.kt       # Datos simulados de las 10 recetas
├── ui/
│   ├── RecetasViewModel.kt  # Estado compartido y lógica de negocio
│   ├── ComponentesReceta.kt # Componentes reutilizables sin estado y TarjetaReceta
│   ├── PantallasReceta.kt   # Pantalla principal y ficha adaptativa
│   └── theme/               # Configuración de tema Material 3
└── MainActivity.kt          # Punto de entrada, navegación y cambio de idioma
```

## Cómo ejecutarlo

1. Clona este repositorio:
   ```bash
   git clone [https://github.com/RCLdP/Recetario_manchego.git](https://github.com/RCLdP/Recetario_manchego.git)
   ```
2. Abre la carpeta del proyecto en **Android Studio**.
3. Espera a que finalice la sincronización de Gradle y ejecuta la app en un emulador o dispositivo físico (API 24+).
