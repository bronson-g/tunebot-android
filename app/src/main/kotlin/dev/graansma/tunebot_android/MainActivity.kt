package dev.graansma.tunebot_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val controller = Controller(PromotionLevel.DEVELOPMENT) // TODO change this based on gradle/string xml values
    private val network = object: Network() {
        override fun onScanComplete(macs: Set<String>) {
            controller.updateMasterList(macs)
            // todo rebuild the queue
        }
    }
    private var scheduler: ScheduledExecutorService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // TODO a mediaplayer with a song queue. next, back, pause, play

    override fun onResume() {
        super.onResume()
        scheduler = Executors.newSingleThreadScheduledExecutor()
        scheduler?.scheduleWithFixedDelay(network::startScan, 0, 30, TimeUnit.MINUTES)
    }

    override fun onStop() {
        scheduler?.shutdownNow()
        network.endScan()
        super.onStop()
    }
}