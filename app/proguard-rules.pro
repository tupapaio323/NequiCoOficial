# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Optimizaciones adicionales para reducir tamaño
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification

# Mantener solo lo necesario de tu app (por ejemplo, Activities/Providers accedidos por manifiesto)
-keep class com.wts.nequicooficial.**Activity { *; }
-keep class com.wts.nequicooficial.**Application { *; }
-keep class androidx.core.content.FileProvider { *; }

# Optimizar strings
-optimizations !code/removal/string

# Remover logs en release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# Optimizar recursos
-keep class **.R$* {
    public static <fields>;
}

# Mantener anotaciones importantes
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions

# Firebase: mantener APIs públicas usadas en runtime, permitir shrink de lo no usado
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Remover warnings irrelevantes
-dontwarn android.support.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**