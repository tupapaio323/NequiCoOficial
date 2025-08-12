@echo off
echo 🎯 Construyendo APK optimizado...

REM Limpiar build anterior
echo 📦 Limpiando build anterior...
call gradlew clean

REM Construir APK optimizado
echo 🔨 Construyendo APK optimizado...
call gradlew assembleRelease

REM Mostrar información del APK
echo 📊 Información del APK generado:
dir app\build\outputs\apk\release\*.apk

echo ✅ Build optimizado completado!
echo 💡 El APK optimizado está en: app\build\outputs\apk\release\
pause
