import os
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import padding

def decrypt_file(input_path, password):
    try:
        # Read the encrypted file
        with open(input_path, 'rb') as file:
            encrypted_data = file.read()
        
        print(f"📦 Bytes encriptados leídos: {len(encrypted_data)}")
        
        # Convert password to bytes and create key (same as Android app)
        password_bytes = password.encode('utf-8')
        key = password_bytes.ljust(32, b'\0')[:32]  # Pad to 32 bytes
        
        # Create IV (same as Android app)
        iv = b'\x00\x01\x02\x03\x04\x05\x06\x07\x08\x09\x0A\x0B\x0C\x0D\x0E\x0F'
        
        # Create cipher
        cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
        decryptor = cipher.decryptor()
        
        # Decrypt
        decrypted_data = decryptor.update(encrypted_data) + decryptor.finalize()
        
        # Remove padding
        unpadder = padding.PKCS7(128).unpadder()
        unpadded_data = unpadder.update(decrypted_data) + unpadder.finalize()
        
        print(f"🔓 Bytes desencriptados: {len(unpadded_data)}")
        
        # Save decrypted data to verify it's a valid PNG
        output_path = input_path.replace('.bin', '_decrypted.png')
        with open(output_path, 'wb') as file:
            file.write(unpadded_data)
        
        print(f"✅ Archivo desencriptado guardado como: {output_path}")
        
        # Check if it's a valid PNG (should start with PNG signature)
        if unpadded_data.startswith(b'\x89PNG\r\n\x1a\n'):
            print("✅ El archivo desencriptado es un PNG válido")
            return True
        else:
            print("❌ El archivo desencriptado NO es un PNG válido")
            print(f"   Primeros bytes: {unpadded_data[:10]}")
            return False
            
    except Exception as e:
        print(f"❌ Error desencriptando archivo: {e}")
        return False

# File paths
input_file = "app/src/main/assets/base2.bin"
password = "ladroneshijueputas"

# Check if input file exists
if os.path.exists(input_file):
    print(f"🔍 Verificando archivo: {input_file}")
    success = decrypt_file(input_file, password)
    if success:
        print("✅ base2.bin se puede desencriptar correctamente")
    else:
        print("❌ base2.bin NO se puede desencriptar correctamente")
else:
    print(f"❌ Error: No se encontró el archivo {input_file}")
