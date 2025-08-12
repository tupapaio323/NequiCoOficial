# 🔒 Configuración de Seguridad Firebase

## Pasos para Ocultar/Proteger la Database:

### 1. **Aplicar Reglas de Firebase (CRÍTICO)**
```bash
# En Firebase Console:
1. Ve a Firebase Console → Database → Rules
2. Copia el contenido de firebase_rules_production.json
3. Reemplaza las reglas actuales
4. Cambia 'TU_UID_ADMIN_AQUI' por tu UID real
5. Publica las reglas
```

### 2. **Habilitar Firebase App Check (MUY RECOMENDADO)**
```bash
# En Firebase Console:
1. Ve a Project Settings → App Check
2. Habilita Play Integrity para Android
3. Configura reCAPTCHA para web (si aplica)
4. Activa "Enforce App Check"
```

### 3. **Configurar Autenticación Segura**
```bash
# En Firebase Console → Authentication:
1. Deshabilita "Anonymous sign-in" si no lo usas
2. Configura solo métodos necesarios
3. Añade dominios autorizados específicos
```

### 4. **Variables de Entorno (IMPLEMENTADO)**
- ✅ URL de database movida a secrets.xml
- ✅ .gitignore configurado
- ✅ google-services.json protegido

### 5. **Compilación Segura (IMPLEMENTADO)**
- ✅ ProGuard habilitado
- ✅ Ofuscación activada
- ✅ Logs eliminados en release
- ✅ Code shrinking habilitado

### 6. **Monitoreo y Alertas**
```bash
# Configura en Firebase Console:
1. Firebase Performance Monitoring
2. Crashlytics para errores
3. Alertas de uso inusual
```

## ⚠️ IMPORTANTE:

1. **NUNCA subas a GitHub:**
   - google-services.json
   - secrets.xml
   - Archivos de reglas de Firebase

2. **Cambia UIDs en las reglas** antes de aplicar

3. **Testa la app** después de aplicar las reglas restrictivas

4. **Habilita App Check** para protección adicional

## 🎯 Nivel de Protección Resultante:

- **Antes**: Database parcialmente expuesta
- **Después**: Database completamente privada y ofuscada
- **Bonus**: App Check previene acceso no autorizado