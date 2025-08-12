import os
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import padding

def encrypt_file(input_path, output_path, password):
    try:
        # Read the PNG file
        with open(input_path, 'rb') as file:
            png_data = file.read()
        
        print(f"📦 Bytes PNG leídos: {len(png_data)}")
        
        # Convert password to bytes and create key (same as Android app)
        password_bytes = password.encode('utf-8')
        key = password_bytes.ljust(32, b'\0')[:32]  # Pad to 32 bytes
        
        # Create IV with zeros (same as Android app)
        iv = b'\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00'
        
        # Create cipher
        cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
        encryptor = cipher.encryptor()
        
        # Add padding
        padder = padding.PKCS7(128).padder()
        padded_data = padder.update(png_data) + padder.finalize()
        
        # Encrypt
        encrypted_data = encryptor.update(padded_data) + encryptor.finalize()
        
        print(f"🔐 Bytes encriptados: {len(encrypted_data)}")
        
        # Save encrypted file
        with open(output_path, 'wb') as file:
            file.write(encrypted_data)
        
        print(f"✅ Archivo encriptado guardado como: {output_path}")
        return True
        
    except Exception as e:
        print(f"❌ Error encriptando archivo: {e}")
        return False

# File paths
input_file = "app/src/main/res/drawable/base2.png"
output_file = "app/src/main/res/drawable/base2.bin"
password = "ladroneshijueputas"

# Check if input file exists
if os.path.exists(input_file):
    print(f"🔍 Encriptando archivo: {input_file}")
    success = encrypt_file(input_file, output_file, password)
    if success:
        print("✅ base2.png encriptado correctamente como base2.bin en drawable")
        print("📁 El archivo base2.bin está en app/src/main/res/drawable/ para que lo muevas")
    else:
        print("❌ Error encriptando base2.png")
else:
    print(f"❌ Error: No se encontró el archivo {input_file}")
