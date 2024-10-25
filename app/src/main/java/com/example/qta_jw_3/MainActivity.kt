package com.example.qta_jw_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qta_jw_3.ui.theme.Qta_JW_3Theme
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Qta_JW_3Theme {
                // Estado para manejar las pantallas (bienvenida o login)
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Welcome) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (currentScreen) {
                        Screen.Welcome -> {
                            WelcomeScreen(
                                onLoginClick = { currentScreen = Screen.Login },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        Screen.Login -> {
                            LoginScreen(
                                onLoginSuccess = { /* Aquí puedes manejar lo que sucede al loguearse */ },
                                onBackClick = { currentScreen = Screen.Welcome }, // Regresar a Welcome
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen {
    object Welcome : Screen()
    object Login : Screen()
}

@Composable
fun WelcomeScreen(onLoginClick: () -> Unit, modifier: Modifier = Modifier) {
    // Pantalla de bienvenida con un botón para ir al login
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido a la Aplicación",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = { onLoginClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp), // Ajusta la altura del botón
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00D084) // Verde del botón
            )
        ) {
            Text(text = "Iniciar sesión", fontSize = 14.sp) // Opcional: ajustar el tamaño de la fuente
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val validUsername = "admin"
    val validPassword = "1234"

    Box(modifier = modifier.fillMaxSize()) {
        // Barra superior fija
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF00D084)) // Fondo verde de la barra
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Para alinear el contenido
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
            }
            Text(
                text = "Iniciar sesión",
                color = Color.Black, // Cambiar color a negro
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f) // Permitir que el texto ocupe el espacio disponible
                    .padding(start = 8.dp), // Espaciado a la izquierda
                textAlign = TextAlign.Center // Centrar el texto
            )
            // Espacio adicional para el ícono de retroceso
            Spacer(modifier = Modifier.width(48.dp)) // Espacio para el ícono de retroceso
        }

        // Contenedor para centrar el formulario
        Box(modifier = Modifier.fillMaxSize()) {
            // Contenido principal de la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0)) // Fondo verde claro
                    .padding(horizontal = 16.dp, vertical = 200.dp) // Espaciado adicional
                    .align(Alignment.Center), // Centrar la columna
            ) {
                // Espaciado superior
                Spacer(modifier = Modifier.height(16.dp)) // Espaciado para separar del borde superior

                // Campo de texto para el nombre de usuario
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = Color(0xFF00D084),
                        focusedBorderColor = Color(0xFF00D084),
                        unfocusedBorderColor = Color(0xFF00D084),
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de texto para la contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = Color(0xFF00D084),
                        focusedBorderColor = Color(0xFF00D084),
                        unfocusedBorderColor = Color(0xFF00D084),
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón de inicio de sesión
                Button(
                    onClick = {
                        when {
                            username.isEmpty() -> errorMessage = "Por favor, ingresa un nombre de usuario"
                            password.isEmpty() -> errorMessage = "Por favor, ingresa una contraseña"
                            username == validUsername && password == validPassword -> onLoginSuccess()
                            else -> errorMessage = "Nombre de usuario o contraseña incorrectos"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00D084) // Verde del botón
                    )
                ) {
                    Text(text = "Ingresar", color = Color.White)
                }

                // Mostrar mensaje de error si es necesario
                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    Qta_JW_3Theme {
        WelcomeScreen(onLoginClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Qta_JW_3Theme {
        LoginScreen(onLoginSuccess = {}, onBackClick = {})
    }
}
