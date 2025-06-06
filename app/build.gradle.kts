import java.util.Properties
import java.io.File

plugins {
    //alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.android.application")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

val localProperties = Properties().apply {
    load(File(rootDir, "local.properties").inputStream())
}
val groqApiKey = localProperties.getProperty("GROQ_API_KEY") ?: ""

android {
    namespace = "com.example.mytfg"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mytfg"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        // Cargar GROQ_API_KEY desde local.properties
        /*
        val properties = Properties()
        properties.load(File(rootDir, "local.properties").inputStream())
        val groqApiKey = properties.getProperty("GROQ_API_KEY") ?: ""
        buildConfigField("String", "GROQ_API_KEY", "\"$groqApiKey\"")
        */
        buildConfigField("String", "GROQ_API_KEY", "$groqApiKey")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true // 👈 Habilita BuildConfig
    }
}

dependencies {
    // Ejemplo de dependencias. Usa solo las que necesitas realmente.
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    // OkHttp (para interceptores)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    //
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.play.services.auth)
    implementation(libs.play.services.basement)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3.vlatestversion)
    implementation(libs.androidx.navigation.compose.vlatestversion)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.coil.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.material3.material3)
    implementation (libs.androidx.material.icons.extended)

    implementation(libs.reorderable)

    implementation("io.github.cdimascio:java-dotenv:5.2.2")

//<<<<<<< HEAD
    // Import the Firebase BoM
    //implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    // https://firebase.google.com/docs/android/setup#available-libraries


    implementation("com.google.firebase:firebase-analytics")


    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries

    //AÑADIMOS ESTAS LINEAS PARA QUE FUNCIONE EL PLUGIN DE GOOGLE
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.google.android.gms:play-services-basement:18.2.")
    //implementation ("androidx.compose.material3:material3:1.2.0-alpha02")
    implementation ("com.google.accompanist:accompanist-pager:0.30.1")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.30.1")
    implementation("androidx.compose.foundation:foundation:<latest_version>")
    implementation("androidx.compose.material3:material3:<latest_version>")
    implementation("androidx.navigation:navigation-compose:<latest_version>")

    // AÑADIMOS ESTAS LINEAS PARA QUE FUNCIONEN LOS ICONOS
    implementation ("androidx.compose.material:material-icons-extended:1.0.0")
}
//=======
//}
//>>>>>>> a7e6f5bcdb4137a3851b44c88108aa7aa42992a8
