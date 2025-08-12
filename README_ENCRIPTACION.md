# 🔐 Encriptación de envio4.png

Este script encripta el archivo `envio4.png` usando el mismo método que los archivos `.bin` de la aplicación Android.

## 📋 Requisitos

- Python 3.6 o superior
- Archivo `envio4.png` en el directorio del script

## 🚀 Instalación y Uso

### 1. Instalar dependencias
```bash
python install_requirements.py
```

### 2. Encriptar envio4.png
```bash
python encriptar_envio4.py
```

### 3. Copiar archivo encriptado
```bash
# Copiar envio4.bin a la carpeta assets de Android
cp envio4.bin app/src/main/assets/
```

## 🔧 Configuración

El script usa la misma configuración que Android:
- **Algoritmo**: AES/CBC/PKCS5Padding
- **Clave**: "ladroneshijueputas" (32 bytes)
- **IV**: 16 bytes de ceros

## 📱 Uso en Android

Una vez que tengas `envio4.bin` en `app/src/main/assets/`, puedes usarlo así:

```kotlin
// Para obtener un Bitmap
val bitmap = desencriptarBin("envio4.bin")

// Para establecer en un ImageView
establecerImagenDesencriptada(imageView, "envio4.bin")

// Para cargar por nombre (sin extensión)
cargarImagenDesdeAssets(imageView, "envio4")
```

## 🔍 Verificación

Para verificar que la encriptación funciona correctamente:

1. Ejecuta el script de encriptación
2. Copia `envio4.bin` a `app/src/main/assets/`
3. Compila y ejecuta la app
4. Usa `desencriptarBin("envio4.bin")` en el código
5. Verifica que la imagen se muestre correctamente

## 📝 Notas

- El archivo original `envio4.png` debe estar en el mismo directorio que el script
- El archivo encriptado se guarda como `envio4.bin`
- La clave de encriptación es la misma que usan todos los archivos `.bin` de la app
- El IV (Vector de Inicialización) es de 16 bytes de ceros

## 🛠️ Solución de Problemas

### Error: "El archivo envio4.png no existe"
- Asegúrate de que `envio4.png` esté en el mismo directorio que el script

### Error: "ModuleNotFoundError: No module named 'cryptography'"
- Ejecuta: `python install_requirements.py`

### Error en Android: "No se pudo desencriptar el archivo .bin"
- Verifica que `envio4.bin` esté en `app/src/main/assets/`
- Verifica que el nombre del archivo sea exactamente `envio4.bin`
