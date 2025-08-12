#!/usr/bin/env python3
"""
Script para optimizar imágenes y reducir el tamaño del APK
"""

import os
import subprocess
from PIL import Image
import sys
from pathlib import Path
import tempfile
import shutil
import io

try:
    from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
    from cryptography.hazmat.backends import default_backend
    from cryptography.hazmat.primitives import padding
except Exception:
    print("⚠️ Falta 'cryptography'. Ejecuta: python install_requirements.py")
    Cipher = None

def optimize_image(input_path, output_path, quality=85, max_size=(1920, 1080)):
    """Optimiza una imagen reduciendo su calidad y tamaño"""
    try:
        with Image.open(input_path) as img:
            # Convertir a RGB si es necesario
            if img.mode in ('RGBA', 'LA', 'P'):
                img = img.convert('RGB')
            
            # Redimensionar si es muy grande
            if img.size[0] > max_size[0] or img.size[1] > max_size[1]:
                img.thumbnail(max_size, Image.Resampling.LANCZOS)
            
            # Guardar optimizado
            # Elegir formato destino: WebP si es posible, si no JPEG
            ext = Path(output_path).suffix.lower()
            if ext == ".webp":
                img.save(output_path, 'WEBP', quality=quality, method=6)
            else:
                img.save(output_path, 'JPEG', quality=quality, optimize=True)
            
            original_size = os.path.getsize(input_path)
            optimized_size = os.path.getsize(output_path)
            reduction = ((original_size - optimized_size) / original_size) * 100
            
            print(f"OK {os.path.basename(input_path)}: {original_size/1024:.1f}KB -> {optimized_size/1024:.1f}KB ({reduction:.1f}% reduction)")
            
    except Exception as e:
        print(f"❌ Error optimizando {input_path}: {e}")

def main():
    """Función principal"""
    print("Optimizing app images...")
    is_aggressive = os.environ.get("AGGRESSIVE", "") == "1"
    
    # Directorios a optimizar
    res_dir = "app/src/main/res"
    drawable_dir = os.path.join(res_dir, "drawable")
    assets_dir = "app/src/main/assets"
    backup_root = ".image_backups"
    os.makedirs(backup_root, exist_ok=True)
    
    # Imágenes grandes que necesitan optimización
    large_images = [
        "base2.png",
        "layout_nequi.png",
        "guia_1.png",
        "guia_2.png",
        "guia_3.png",
        "guia_4.png",
        "guia_5.png",
        "guia_6.png",
        "guia_7.png",
        "guia_8.png",
        "guia_9.png",
        "guia_10.png"
    ]
    
    total_saved = 0
    
    for image_name in large_images:
        input_path = os.path.join(drawable_dir, image_name)
        if os.path.exists(input_path):
            # Crear backup fuera de res para no romper el build
            backup_path = os.path.join(backup_root, os.path.basename(input_path) + ".backup")
            if not os.path.exists(backup_path):
                try:
                    shutil.copy2(input_path, backup_path)
                except Exception:
                    pass
            
            # Optimizar
            # Convertir a WebP para mayor reducción
            webp_target = input_path[:-4] + ".webp" if input_path.lower().endswith('.png') else input_path + ".webp"
            max_w, max_h = (1280, 720) if is_aggressive else (1920, 1080)
            q = 65 if is_aggressive else 75
            optimize_image(backup_path, webp_target, quality=q, max_size=(max_w, max_h))
            # Opcional: eliminar PNG original si el WebP existe
            try:
                if os.path.exists(webp_target):
                    os.remove(input_path)
            except Exception:
                pass
            
            original_size = os.path.getsize(backup_path)
            # El archivo original puede haber sido eliminado; usa el destino webp si existe
            optimized_path = webp_target if os.path.exists(webp_target) else (input_path if os.path.exists(input_path) else backup_path)
            optimized_size = os.path.getsize(optimized_path)
            total_saved += max(0, original_size - optimized_size)
    
    # Convertir TODOS los drawables (drawable, drawable-*) a WebP si mejora
    converted_saved = 0
    for root, dirs, files in os.walk(res_dir):
        base = os.path.basename(root)
        if not base.startswith("drawable"):
            continue
        for fname in files:
            fpath = os.path.join(root, fname)
            name_lower = fname.lower()
            # Ignorar xml y webp existentes
            if name_lower.endswith('.xml') or name_lower.endswith('.webp'):
                continue
            # Ignorar nine-patch
            if name_lower.endswith('.9.png'):
                continue
            # Solo PNG/JPG/JPEG
            if not (name_lower.endswith('.png') or name_lower.endswith('.jpg') or name_lower.endswith('.jpeg')):
                continue
            try:
                original_size = os.path.getsize(fpath)
                # Backup fuera de res
                rel_path = os.path.relpath(fpath, res_dir).replace(os.sep, '_')
                backup_path = os.path.join(backup_root, rel_path + ".backup")
                if not os.path.exists(backup_path):
                    try:
                        shutil.copy2(fpath, backup_path)
                    except Exception:
                        pass
                # Abrir y convertir
                with Image.open(fpath) as img:
                    has_alpha = (img.mode in ('RGBA', 'LA')) or ('transparency' in img.info)
                    if has_alpha and img.mode not in ('RGBA', 'LA'):
                        img = img.convert('RGBA')
                    if (not has_alpha) and img.mode not in ('RGB',):
                        img = img.convert('RGB')
                    # Redimensionar si es agresivo y supera 1280x720
                    if is_aggressive:
                        max_w, max_h = 1280, 720
                        if img.size[0] > max_w or img.size[1] > max_h:
                            img.thumbnail((max_w, max_h), Image.Resampling.LANCZOS)
                    webp_target = os.path.splitext(fpath)[0] + '.webp'
                    if has_alpha:
                        img.save(webp_target, 'WEBP', lossless=True, method=6)
                    else:
                        q = 65 if is_aggressive else 75
                        img.save(webp_target, 'WEBP', quality=q, method=6)
                if os.path.exists(webp_target):
                    optimized_size = os.path.getsize(webp_target)
                    if optimized_size < original_size:
                        converted_saved += (original_size - optimized_size)
                        # Eliminar original para evitar duplicado en recursos
                        try:
                            os.remove(fpath)
                        except Exception:
                            pass
                        print(f"OK {os.path.relpath(fpath, res_dir)}: {original_size/1024:.1f}KB -> {optimized_size/1024:.1f}KB (-{(original_size-optimized_size)/1024:.1f}KB)")
                    else:
                        # No mejora, borrar webp generado
                        try:
                            os.remove(webp_target)
                        except Exception:
                            pass
                        print(f"INFO {os.path.relpath(fpath, res_dir)}: no improvement, kept original")
            except Exception as e:
                print(f"ERROR converting {os.path.relpath(fpath, res_dir)}: {e}")
    
    # Comprimir todos los assets .bin re-encriptando desde versión recomprimida si es posible
    ahorro_assets = 0
    if Cipher is not None and os.path.isdir(assets_dir):
        import io
        print("\nRecompressing ALL .bin assets...")
        password = "ladroneshijueputas"

        def decrypt_bytes(encrypted: bytes) -> bytes:
            key = password.encode('utf-8').ljust(32, b'\0')[:32]
            iv = b'\x00' * 16
            cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
            decryptor = cipher.decryptor()
            decrypted_padded = decryptor.update(encrypted) + decryptor.finalize()
            # remover PKCS7 padding
            unpadder = padding.PKCS7(128).unpadder()
            return unpadder.update(decrypted_padded) + unpadder.finalize()

        def encrypt_bytes(raw: bytes) -> bytes:
            key = password.encode('utf-8').ljust(32, b'\0')[:32]
            iv = b'\x00' * 16
            cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
            encryptor = cipher.encryptor()
            padder = padding.PKCS7(128).padder()
            raw_padded = padder.update(raw) + padder.finalize()
            return encryptor.update(raw_padded) + encryptor.finalize()

        for name in sorted(os.listdir(assets_dir)):
            if not name.lower().endswith('.bin'):
                continue
            p = os.path.join(assets_dir, name)
            try:
                original_size = os.path.getsize(p)
                with open(p, 'rb') as f:
                    enc = f.read()
                # Intentar desencriptar; si falla, continuar
                try:
                    dec = decrypt_bytes(enc)
                except Exception as e:
                    print(f"WARN {name}: could not decrypt, skipping ({e})")
                    continue

                # Intentar abrir como imagen
                try:
                    img = Image.open(io.BytesIO(dec))
                    # Elegir modo conservando transparencia
                    has_alpha = img.mode in ('RGBA', 'LA') or ('transparency' in img.info)
                    if has_alpha and img.mode not in ('RGBA', 'LA'):
                        img = img.convert('RGBA')
                    if (not has_alpha) and img.mode not in ('RGB',):
                        img = img.convert('RGB')

                    with tempfile.TemporaryDirectory() as td:
                        webp_path = os.path.join(td, 'raw.webp')
                        if has_alpha:
                            # Mantener transparencia sin perder detalle
                            img.save(webp_path, 'WEBP', lossless=True, method=6)
                        else:
                            img.save(webp_path, 'WEBP', quality=70, method=6)
                        with open(webp_path, 'rb') as wf:
                            recompressed = wf.read()

                    # Re-encriptar y sobrescribir solo si mejora
                    if len(recompressed) < len(dec):
                        reenc = encrypt_bytes(recompressed)
                        with open(p, 'wb') as f:
                            f.write(reenc)
                        new_size = os.path.getsize(p)
                        ahorro = max(0, original_size - new_size)
                        ahorro_assets += ahorro
                        print(f"OK {name}: {original_size/1024:.1f}KB -> {new_size/1024:.1f}KB (-{ahorro/1024:.1f}KB)")
                    else:
                        print(f"INFO {name}: no improvement when recompressing, keeping original")
                except Exception:
                    # Si no es imagen válida (p.ej. video/audio), omitir
                    print(f"INFO {name}: not a compatible image, skipping")
                    continue
            except Exception as e:
                print(f"ERROR processing {name}: {e}")

    print(f"\nOptimization completed!")
    print(f"Total saved in large drawables: {total_saved/1024/1024:.2f}MB")
    print(f"Total saved converting all drawables: {converted_saved/1024/1024:.2f}MB")
    if Cipher is not None:
        print(f"Saved in .bin assets: {ahorro_assets/1024/1024:.2f}MB")
    print(f"Original images were backed up as .backup")

if __name__ == "__main__":
    main()
