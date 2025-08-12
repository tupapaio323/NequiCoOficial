import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        VStack(spacing: 12) {
            Text("Nequi Co iOS")
                .font(.headline)
            Text(SharedPlatformKt.sharedGreeting())
                .font(.subheadline)
        }
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}


