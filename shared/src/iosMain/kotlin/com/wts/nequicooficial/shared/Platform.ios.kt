package com.wts.nequicooficial.shared

import platform.UIKit.UIDevice

actual class Platform {
    actual val name: String = "iOS ${UIDevice.currentDevice.systemName()} ${UIDevice.currentDevice.systemVersion}"
}


