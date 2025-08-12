import sys
from pathlib import Path

from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import padding


def derive_key_from_password(password: str) -> bytes:
    # Match Android implementation: password bytes, zero-padded/truncated to 32 bytes (AES-256)
    raw = password.encode('utf-8')
    key = bytearray(32)
    key[: min(len(raw), 32)] = raw[:32]
    return bytes(key)


def aes_cbc_pkcs7_encrypt(key: bytes, data: bytes) -> bytes:
    # Zero IV, as used on Android side
    iv = bytes(16)
    padder = padding.PKCS7(128).padder()
    padded = padder.update(data) + padder.finalize()
    cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
    encryptor = cipher.encryptor()
    return encryptor.update(padded) + encryptor.finalize()


def main():
    if len(sys.argv) < 3:
        print("Usage: python scripts/encrypt_video.py <input_mp4_path> <output_bin_path> [password]")
        sys.exit(1)

    input_path = Path(sys.argv[1])
    output_path = Path(sys.argv[2])
    password = sys.argv[3] if len(sys.argv) >= 4 else "ladroneshijueputas"

    if not input_path.exists():
        print(f"Input not found: {input_path}")
        sys.exit(2)

    data = input_path.read_bytes()
    key = derive_key_from_password(password)
    encrypted = aes_cbc_pkcs7_encrypt(key, data)

    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_bytes(encrypted)
    print(f"Encrypted {input_path} -> {output_path} ({len(encrypted)} bytes)")


if __name__ == "__main__":
    main()


