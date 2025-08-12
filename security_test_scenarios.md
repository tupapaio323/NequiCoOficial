# 🧪 Pruebas de Seguridad

## Escenarios que tus reglas ACTUALES permiten (MALO):

### ❌ Escenario 1: Robo de datos
```javascript
// Usuario malicioso autenticado puede hacer:
firebase.database().ref('users').once('value').then(snapshot => {
  console.log(snapshot.val()); // Ve TODOS los usuarios, PINs, saldos
});
```

### ❌ Escenario 2: Modificación de saldos
```javascript
// Usuario A puede cambiar saldo de Usuario B:
firebase.database().ref('users/USER_B_UID/saldo').set('999999999');
```

### ❌ Escenario 3: Acceso a PINs
```javascript
// Usuario malicioso puede leer PINs de otros:
firebase.database().ref('users/VICTIM_UID/pin').once('value').then(pin => {
  console.log('PIN robado:', pin.val());
});
```

## Escenarios que las reglas SEGURAS previenen (BUENO):

### ✅ Escenario 1: Solo tus datos
```javascript
// Usuario A solo puede leer SUS datos:
firebase.database().ref('users/USER_A_UID').once('value') // ✅ Funciona
firebase.database().ref('users/USER_B_UID').once('value') // ❌ DENEGADO
firebase.database().ref('users').once('value')            // ❌ DENEGADO
```

### ✅ Escenario 2: Validación de datos
```javascript
// Intento de crear usuario inválido:
firebase.database().ref('users/MY_UID').set({
  number: "123",        // ❌ DENEGADO: No tiene 10 dígitos
  pin: "12345",         // ❌ DENEGADO: PIN debe tener 4 caracteres
  saldo: 999999         // ❌ DENEGADO: Debe ser string
});
```

### ✅ Escenario 3: Solo tu UID
```javascript
// Solo puedes modificar tu propio UID:
firebase.database().ref('users/MI_UID_REAL/saldo').set('500000')     // ✅ Funciona
firebase.database().ref('users/UID_DE_OTRO/saldo').set('999999999')  // ❌ DENEGADO
```