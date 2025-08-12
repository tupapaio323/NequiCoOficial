#!/usr/bin/env python3
"""
Encripta drawables PNG/WebP a .bin usando AES/CBC/PKCS5Padding con la
misma configuración que la app Android.

Uso:
  python encrypt_drawables_to_assets.py listo.png listopantalla.png comproqr.png

Entrada:  app/src/main/res/drawable/<nombre>.png|webp
Salida:   app/src/main/assets/<nombre>.bin
"""

import os
import sys
from typing import List

try:
    from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
    from cryptography.hazmat.backends import default_backend
    from cryptography.hazmat.primitives import padding
except Exception as import_error:  # pragma: no cover
    sys.stderr.write(
        "\n[!] Falta dependencia 'cryptography'. Instala con:\n"
        "    python -m pip install --user cryptography\n\n"
    )
    raise


ENCRYPTION_PASSWORD = "ladroneshijueputas"
DRAWABLE_DIR = os.path.join("app", "src", "main", "res", "drawable")
ASSETS_DIR = os.path.join("app", "src", "main", "assets")


def generate_aes_key(password: str) -> bytes:
    key_bytes = password.encode("utf-8")
    key = bytearray(32)
    if len(key_bytes) < 32:
        key[: len(key_bytes)] = key_bytes
    else:
        key[:32] = key_bytes[:32]
    return bytes(key)


def encrypt_bytes(data: bytes, key: bytes) -> bytes:
    iv = b"\x00" * 16
    cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
    encryptor = cipher.encryptor()
    padder = padding.PKCS7(128).padder()
    padded = padder.update(data) + padder.finalize()
    return encryptor.update(padded) + encryptor.finalize()


def encrypt_file(input_path: str, output_path: str, key: bytes) -> None:
    with open(input_path, "rb") as f:
        original = f.read()
    encrypted = encrypt_bytes(original, key)
    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    with open(output_path, "wb") as f:
        f.write(encrypted)


def resolve_input_path(filename: str) -> str:
    # Permite pasar nombres con o sin ruta. Busca en DRAWABLE_DIR si es relativo.
    candidate = filename
    if not os.path.isabs(candidate):
        candidate = os.path.join(DRAWABLE_DIR, filename)
    if not os.path.exists(candidate):
        raise FileNotFoundError(f"No existe: {candidate}")
    return candidate


def output_path_for(input_path: str) -> str:
    base = os.path.basename(input_path)
    name, _ext = os.path.splitext(base)
    return os.path.join(ASSETS_DIR, f"{name}.bin")


def run(filenames: List[str]) -> int:
    if not filenames:
        sys.stderr.write(
            "Especifica al menos un archivo (p.ej., listo.png listopantalla.png comproqr.png)\n"
        )
        return 2

    key = generate_aes_key(ENCRYPTION_PASSWORD)
    processed = 0
    for filename in filenames:
        try:
            src = resolve_input_path(filename)
            dst = output_path_for(src)
            encrypt_file(src, dst, key)
            print(f"✅ {os.path.relpath(src)} -> {os.path.relpath(dst)}")
            processed += 1
        except Exception as e:  # pragma: no cover
            print(f"❌ Error con {filename}: {e}")

    print(f"\nHecho. Archivos generados: {processed}/{len(filenames)}")
    return 0 if processed == len(filenames) else 1


if __name__ == "__main__":
    sys.exit(run(sys.argv[1:]))


