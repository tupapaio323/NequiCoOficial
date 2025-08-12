#!/usr/bin/env python3
"""
Script para analizar el tamaño de los archivos de la app
"""

import os
import sys
from pathlib import Path

def analyze_directory(directory, extensions=None):
    """Analiza el tamaño de archivos en un directorio"""
    total_size = 0
    file_count = 0
    large_files = []
    
    for root, dirs, files in os.walk(directory):
        for file in files:
            if extensions is None or any(file.endswith(ext) for ext in extensions):
                file_path = os.path.join(root, file)
                file_size = os.path.getsize(file_path)
                total_size += file_size
                file_count += 1
                
                # Archivos grandes (>100KB)
                if file_size > 100 * 1024:
                    large_files.append((file_path, file_size))
    
    return total_size, file_count, large_files

def main():
    """Función principal"""
    print("📊 Analizando tamaño de la app...")
    
    # Directorios a analizar
    directories = [
        "app/src/main/res/drawable",
        "app/src/main/assets",
        "app/src/main/res/mipmap-xxxhdpi",
        "app/src/main/res/mipmap-xxhdpi",
        "app/src/main/res/mipmap-xhdpi",
        "app/src/main/res/mipmap-hdpi",
        "app/src/main/res/mipmap-mdpi"
    ]
    
    total_app_size = 0
    all_large_files = []
    
    for directory in directories:
        if os.path.exists(directory):
            size, count, large = analyze_directory(directory)
            total_app_size += size
            
            print(f"\n📁 {directory}:")
            print(f"   📄 Archivos: {count}")
            print(f"   📦 Tamaño: {size/1024/1024:.2f}MB")
            
            if large:
                print(f"   ⚠️  Archivos grandes:")
                for file_path, file_size in large:
                    rel_path = os.path.relpath(file_path)
                    print(f"      - {rel_path}: {file_size/1024:.1f}KB")
                    all_large_files.append((rel_path, file_size))
    
    print(f"\n🎯 RESUMEN:")
    print(f"   📦 Tamaño total de recursos: {total_app_size/1024/1024:.2f}MB")
    print(f"   📄 Total de archivos: {sum(analyze_directory(d)[1] for d in directories if os.path.exists(d))}")
    
    if all_large_files:
        print(f"\n🚨 ARCHIVOS GRANDES (recomendado optimizar):")
        for file_path, file_size in sorted(all_large_files, key=lambda x: x[1], reverse=True):
            print(f"   - {file_path}: {file_size/1024:.1f}KB")
    
    # Recomendaciones
    print(f"\n💡 RECOMENDACIONES:")
    print(f"   - Optimizar imágenes PNG > 100KB")
    print(f"   - Usar WebP en lugar de PNG cuando sea posible")
    print(f"   - Comprimir archivos .bin grandes")
    print(f"   - Eliminar recursos no utilizados")

if __name__ == "__main__":
    main()
