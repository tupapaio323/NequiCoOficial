#!/usr/bin/env python3
"""
Script para instalar dependencias necesarias para la encriptación
"""

import subprocess
import sys

def instalar_dependencias():
    """Instalar dependencias necesarias"""
    try:
        print("📦 Instalando dependencias (cryptography, Pillow)...")
        
        # Instalar cryptography y Pillow
        subprocess.check_call([sys.executable, "-m", "pip", "install", "cryptography", "Pillow"])
        
        print("✅ Dependencias instaladas exitosamente")
        print("🔐 Ahora puedes ejecutar: python optimize_app.py")
        return True
        
    except subprocess.CalledProcessError as e:
        print(f"❌ Error instalando dependencias: {e}")
        return False
    except Exception as e:
        print(f"❌ Error inesperado: {e}")
        return False

if __name__ == "__main__":
    instalar_dependencias()
