#!/usr/bin/env python3
"""
Script para encriptar envio4.png directamente usando la contraseña ladroneshijueputas
"""

import os
import sys
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend

# Configuración de encriptación (debe coincidir con Android)
ENCRYPTION_PASSWORD = "ladroneshijueputas"

def generar_clave_aes(password):
    """Generar clave AES de 32 bytes desde la contraseña"""
    key_bytes = password.encode('utf-8')
    key = bytearray(32)  # 32 bytes = 256 bits
    
    # Si la clave es menor a 32 bytes, rellenar con ceros
    if len(key_bytes) < 32:
        key[:len(key_bytes)] = key_bytes
        # Los bytes restantes ya están en 0 por defecto
    else:
        # Si es mayor a 32 bytes, truncar
        key[:32] = key_bytes[:32]
    
    return bytes(key)

def encriptar_archivo(archivo_entrada, archivo_salida):
    """Encriptar archivo usando AES/CBC/PKCS5Padding"""
    try:
        print(f"🔐 Encriptando {archivo_entrada}...")
        
        # Leer archivo de entrada
        with open(archivo_entrada, 'rb') as f:
            datos_originales = f.read()
        
        print(f"📦 Bytes originales leídos: {len(datos_originales)}")
        
        # Generar clave AES
        key = generar_clave_aes(ENCRYPTION_PASSWORD)
        print(f"🔑 Clave AES generada: {len(key)} bytes")
        
        # Crear IV de ceros (16 bytes)
        iv = b'\x00' * 16
        
        # Crear cipher
        cipher = Cipher(
            algorithms.AES(key),
            modes.CBC(iv),
            backend=default_backend()
        )
        encryptor = cipher.encryptor()
        
        # Encriptar datos
        # PKCS5Padding: rellenar hasta múltiplo de 16
        padding_length = 16 - (len(datos_originales) % 16)
        datos_con_padding = datos_originales + bytes([padding_length] * padding_length)
        
        datos_encriptados = encryptor.update(datos_con_padding) + encryptor.finalize()
        
        print(f"🔒 Bytes encriptados: {len(datos_encriptados)}")
        
        # Guardar archivo encriptado
        with open(archivo_salida, 'wb') as f:
            f.write(datos_encriptados)
        
        print(f"✅ Archivo encriptado guardado como: {archivo_salida}")
        return True
        
    except Exception as e:
        print(f"❌ Error encriptando archivo: {e}")
        return False

def main():
    """Función principal"""
    archivo_entrada = "app/src/main/res/drawable/envio4.png"
    archivo_salida = "app/src/main/assets/envio4.bin"
    
    # Verificar que el archivo de entrada existe
    if not os.path.exists(archivo_entrada):
        print(f"❌ Error: El archivo {archivo_entrada} no existe")
        return False
    
    # Crear directorio assets si no existe
    os.makedirs("app/src/main/assets", exist_ok=True)
    
    # Encriptar archivo
    if encriptar_archivo(archivo_entrada, archivo_salida):
        print("\n🎉 ¡Encriptación completada exitosamente!")
        print(f"📁 Archivo original: {archivo_entrada}")
        print(f"🔐 Archivo encriptado: {archivo_salida}")
        print("\n📋 Ahora puedes usar en Android:")
        print("- desencriptarBin('envio4.bin')")
        print("- establecerImagenDesencriptada(imageView, 'envio4.bin')")
        return True
    else:
        print("❌ Error en la encriptación")
        return False

if __name__ == "__main__":
    main()
