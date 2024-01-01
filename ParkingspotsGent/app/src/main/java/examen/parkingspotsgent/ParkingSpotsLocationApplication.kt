package examen.parkingspotsgent

import android.app.Application
import examen.parkingspotsgent.data.AppContainer
import examen.parkingspotsgent.data.DefaultAppContainer

class ParkingSpotsLocationApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}