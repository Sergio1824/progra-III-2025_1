##  **Cómo usar la app**

En esta sección se explica cómo navegar y utilizar las funciones principales de la aplicación.  
La app está diseñada para ser fácil de usar, permitiendo crear, buscar y guardar recetas de forma práctica y rápida.  

---

### 1.  **Registro e inicio**
- Al abrir la app, puedes **crear una cuenta** o **entrar con una que ya tengas**.  
- Después de esto, verás la **pantalla principal**.

---

### 2.  **Pantalla principal**
Aquí hay **tres opciones principales**:

- **Ver Categorías:** Muestra los diferentes tipos de recetas que hay en la app.  
- **Favoritos:** Aquí están las recetas que guardaste.  
- **Agregar receta:** Puedes crear una receta nueva. Debes poner el **nombre**,el **tiempo de preparación** la **foto**, **cómo se hace (Ingredientes, Instrucciones)**, **qué tan difícil es**, y la **categoría** a la que pertenecerá esta receta.

---

### 3.  **Categorías de recetas**
- Si tocas **“Ver categorías”**, verás una lista con los tipos de recetas disponibles.  
- En cada tipo, se muestran las recetas que pertenecen a esa categoría.  
- Si eliges una receta, se abre el **Detalle de Receta**.

---

### 4.  **Detalle de la receta**
Aquí puedes ver:
- La **foto**  
- El **nombre**  
- El **nivel de dificultad**  
- Los **ingredientes**  
- Cómo se **prepara(Instrucciones)**

Desde aquí puedes:
- **Editar** la receta.  
- **Guardar o quitarla de favoritos Marcando o desmarcando el boton de Favorito.**

---

### 5.  **Buscar**
- En la parte de la categoría de las recetas, hay un botón que dice **“Buscar receta”**, que te lleva a una pantalla de búsqueda.  
- Escribe el **nombre** de la receta o algo relacionado.  
- Abajo verás **sugerencias de recetas** para ayudarte a encontrar lo que buscas más rápido.

---

### 6.  **Favoritos**
- Aquí están **todas las recetas que guardaste como favoritos**.  
- Puedes ver los **detalles de cada receta** o **quitarlas de tus favoritos** cuando quieras.

# SEGUNDA PARTE - PASOS PARA LA COMPILACION E INSTALACION


#  Instalación y Compilación

Sigue estos pasos para clonar, configurar y compilar el proyecto en **Android Studio**.

---

## 1. Prerrequisitos

- Tener **Android Studio** instalado (preferiblemente una versión reciente).  
- Contar con el archivo **`google-services.json`**, necesario para la conexión con Firebase.  
   Descárgalo desde la carpeta **"final"** del Google Drive del curso.

---

##  2. Clonar el Repositorio

1. Abre **Android Studio**.  
2. Si tienes un proyecto abierto, ciérralo (`File > Close Project`).  
3. En la ventana de bienvenida, selecciona **“Get from VCS” (Obtener desde Control de Versiones)**.  
4. Pega la siguiente URL del repositorio y haz clic en **“Clone”**:

   ```bash
   https://github.com/Sergio1824/progra-III-2025_1.git
   ```

5. Espera a que Android Studio descargue el proyecto y lo abra.

---

##  3. Configuración de Firebase

1. En el panel **Project** (a la izquierda), cambia la vista de **“Android”** a **“Project”**.  
   Esto te permitirá ver la estructura real de carpetas.
2. Busca el archivo **`google-services.json`** que descargaste en el paso 1.  
3. Arrastra y suelta el archivo **dentro de la carpeta `app/`** en la vista “Project”.  
4. Puedes volver a cambiar la vista a **“Android”** para continuar.

---

##  4. Configuración y Sincronización de Gradle

Antes de compilar, asegúrate de que los archivos de Gradle estén correctamente configurados.

###  a) Archivo `build.gradle.kts (Project: progra-III-2025_1)`

Verifica que el plugin de **Google Services** esté añadido:

```kotlin
plugins {
    // ... otros plugins
    id("com.google.gms.google-services") version "4.4.4" apply false
}
```

---

###  b) Archivo `build.gradle.kts (Module :app)`

Verifica que los siguientes plugins estén añadidos en la parte superior:

```kotlin
plugins {
    // ... otros plugins
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "1.9.0"
}
```

Asegúrate también de que **viewBinding** esté activado dentro del bloque `android`:

```kotlin
android {
    // ...
    buildFeatures {
        viewBinding = true
    }
}
```

Finalmente, verifica que las siguientes dependencias estén incluidas dentro de `dependencies`:

```kotlin
dependencies {
    // ... otras dependencias
    implementation(platform("com.google.firebase:firebase-bom:34.4.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.github.bumptech.glide:glide:5.0.5")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}
```

Después de verificar (o añadir) estas líneas, haz clic en **“Sync Now”** cuando aparezca la barra superior.

---

##  5. Ejecutar la Aplicación

1. Espera a que la sincronización de Gradle finalice sin errores (puede tardar unos segundos).  
2. El botón **“Run app”** (triángulo verde ▶) se activará en la barra superior.  
3. Selecciona un **emulador** de Android o un **dispositivo físico conectado**.  
4. Haz clic en **“Run app”**.  

 ¡Listo! La aplicación se compilará y se abrirá en tu dispositivo o emulador.


