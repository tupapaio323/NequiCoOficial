iOS App (Kotlin Multiplatform + SwiftUI)

Este repositorio incluye un módulo `shared` (KMM) y un esqueleto de app iOS en `iosApp` listo para XcodeGen.

1) Requisitos (macOS)
- Xcode (última versión estable)
- XcodeGen: `brew install xcodegen`
- JDK 17+ y Gradle

2) Generar `shared.xcframework`
- Desde la raíz del repo:
  `./gradlew :shared:syncFramework`
- Resultado: `shared/build/XCFrameworks/{Debug|Release}/shared.xcframework`

3) Preparar `iosApp`
- Copia `shared/build/XCFrameworks/Debug/shared.xcframework` a `iosApp/Frameworks/` (crea la carpeta si no existe)
- Genera el proyecto Xcode: `cd iosApp && xcodegen generate`
- Abre `NequiCoiOS.xcodeproj` en Xcode y ejecuta en simulador o dispositivo

4) UI
- El `ContentView` usa `sharedGreeting()` desde el framework KMM para verificar el enlace.
- A partir de aquí puedes replicar pantallas con SwiftUI o emplear Compose Multiplatform.

Nota: En Windows no se puede compilar iOS; utiliza macOS para los pasos 2–4.

