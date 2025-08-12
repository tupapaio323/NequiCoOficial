import argparse
from io import BytesIO

from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import padding
from PIL import Image


PASSWORD = "ladroneshijueputas"


def derive_key(password: str) -> bytes:
    b = password.encode("utf-8")
    return (b + b"\0" * 32)[:32]


def decrypt_bin_to_png_bytes(encrypted: bytes, key: bytes) -> bytes:
    iv = b"\x00" * 16
    cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
    decryptor = cipher.decryptor()
    padded = decryptor.update(encrypted) + decryptor.finalize()
    unpadder = padding.PKCS7(128).unpadder()
    data = unpadder.update(padded) + unpadder.finalize()
    return data


def encrypt_png_bytes_to_bin(png_bytes: bytes, key: bytes) -> bytes:
    padder = padding.PKCS7(128).padder()
    padded = padder.update(png_bytes) + padder.finalize()
    iv = b"\x00" * 16
    cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
    encryptor = cipher.encryptor()
    encrypted = encryptor.update(padded) + encryptor.finalize()
    return encrypted


def resize_image_keep_aspect(img: Image.Image, *, width: int | None, height: int | None, max_width: int | None, max_height: int | None, scale: float | None) -> Image.Image:
    if scale and scale > 0:
        target_w = max(1, int(img.width * scale))
        target_h = max(1, int(img.height * scale))
        return img.resize((target_w, target_h), Image.LANCZOS)

    if width and height:
        return img.resize((width, height), Image.LANCZOS)

    # fit into max box preserving aspect
    if max_width or max_height:
        mw = max_width or img.width
        mh = max_height or img.height
        ratio = min(mw / img.width, mh / img.height)
        target_w = max(1, int(img.width * ratio))
        target_h = max(1, int(img.height * ratio))
        return img.resize((target_w, target_h), Image.LANCZOS)

    # nothing to do
    return img


def main():
    ap = argparse.ArgumentParser(description="Resize encrypted .bin image (AES/CBC) preserving app compatibility")
    ap.add_argument("--input", default="app/src/main/assets/qropciones.bin")
    ap.add_argument("--output", default="app/src/main/assets/qropciones.bin")
    ap.add_argument("--password", default=PASSWORD)
    ap.add_argument("--scale", type=float, default=None, help="Scale factor, e.g., 1.2")
    ap.add_argument("--width", type=int, default=None)
    ap.add_argument("--height", type=int, default=None)
    ap.add_argument("--max-width", type=int, default=None)
    ap.add_argument("--max-height", type=int, default=None)
    args = ap.parse_args()

    key = derive_key(args.password)

    with open(args.input, "rb") as f:
        enc_bytes = f.read()

    png_bytes = decrypt_bin_to_png_bytes(enc_bytes, key)

    img = Image.open(BytesIO(png_bytes)).convert("RGBA")
    img_resized = resize_image_keep_aspect(
        img,
        width=args.width,
        height=args.height,
        max_width=args.max_width,
        max_height=args.max_height,
        scale=args.scale,
    )

    out_buf = BytesIO()
    img_resized.save(out_buf, format="PNG")
    out_png_bytes = out_buf.getvalue()

    out_enc = encrypt_png_bytes_to_bin(out_png_bytes, key)

    with open(args.output, "wb") as f:
        f.write(out_enc)

    print(f"✅ Wrote resized bin to {args.output} ({len(out_enc)} bytes)")


if __name__ == "__main__":
    main()


