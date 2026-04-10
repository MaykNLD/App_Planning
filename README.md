# App Planning

![Estado](https://img.shields.io/badge/estado-activo-brightgreen)
![Stack](https://img.shields.io/badge/stack-Android%20%2B%20Firebase-orange)
![Versión](https://img.shields.io/badge/versión-1.0-informational)
![Autor](https://img.shields.io/badge/autor-Michael-blue)

## ¿Qué hace este proyecto?

App Android nativa (Java) para la planificación personal por categorías de tiempo (mañana, tarde, noche) y gestión de citas médicas. Cada usuario accede únicamente a su propia información gracias a la autenticación y las reglas de seguridad de Firebase.

## Para el evaluador técnico

> Este proyecto demuestra:
> - **Firebase Authentication**: registro e inicio de sesión con correo y contraseña, con persistencia de sesión automática.
> - **Firebase Realtime Database y Firestore**: CRUD completo de actividades sincronizado en tiempo real, con aislamiento de datos por usuario.
> - **Seguridad aplicada**: las contraseñas son gestionadas íntegramente por Firebase Authentication y **nunca se almacenan en la base de datos**. El build de producción activa R8/ProGuard para ofuscación del código.
> - **Arquitectura MVC nativa de Android**: Activities, Adapters y modelos de datos bien separados.
> - **UI con Lottie**: animaciones fluidas integradas sin librerías pesadas.

---

## Stack tecnológico

| Capa            | Tecnología                    | Versión  |
|-----------------|-------------------------------|----------|
| Lenguaje        | Java                          | 8        |
| Plataforma      | Android                       | SDK 21+  |
| Autenticación   | Firebase Authentication       | BoM 32.7 |
| Base de datos   | Firebase Realtime DB + Firestore | BoM 32.7 |
| UI adicional    | Lottie Animations             | 6.0.0    |
| Seguridad build | R8 / ProGuard                 | Activo   |

---

## Cómo ejecutar el proyecto

> ⚠️ Esta es una aplicación Android nativa. Requiere **Android Studio** para compilarla y ejecutarla.

**Requisitos:**
- Android Studio Hedgehog o superior
- JDK 8+
- Conexión a Internet (para la sincronización con Firebase)

```bash
# 1. Clona el repositorio
git clone https://github.com/tu-usuario/App_Planning.git

# 2. Ábrelo en Android Studio:
#    File → Open → selecciona la carpeta del proyecto

# 3. Espera a que Gradle sincronice las dependencias automáticamente

# 4. Ejecuta en un emulador Android (API 21+) o dispositivo físico
#    usando el botón ▶ Run
```

> 💡 **Nota para el evaluador**: Si no dispones de Android Studio, puedes revisar directamente la arquitectura del código en el repositorio. Los flujos principales están en `app/src/main/java/com/example/planing1/`.

---

## Estructura del proyecto

```
app/src/main/java/com/example/planing1/
├── MainActivity.java          # Splash screen + verificación de sesión activa
├── Menu.java                  # Menú principal con navegación por categorías
├── login/
│   ├── PreLogin.java          # Pantalla de bienvenida (Login / Registro)
│   ├── Login.java             # Autenticación de usuarios existentes
│   └── Registro.java          # Alta de nuevos usuarios (sin almacenamiento de contraseña)
├── lista/
│   ├── ListaDia.java          # Lista de actividades de mañana
│   ├── ListaTarde.java        # Lista de actividades de tarde
│   ├── ListaNoche.java        # Lista de actividades de noche
│   └── ListaCitasMedicas.java # Gestión de citas médicas
└── adapter/                   # Adaptadores RecyclerView para cada categoría
```

---

## Decisión de diseño: Seguridad en el registro

La contraseña del usuario se gestiona íntegramente a través de **Firebase Authentication** y no se persiste en ningún nodo de la base de datos. En la colección `Usuarios` solo se almacenan datos de perfil no sensibles: `uid`, `nombre` y `correo`. Esta decisión refleja el principio de mínima exposición de datos (principio de menor privilegio).
