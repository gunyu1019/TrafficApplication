package kr.yhs.traffic

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.security.crypto.MasterKey
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kr.yhs.traffic.module.TrafficClient
import kr.yhs.traffic.ui.ComposeApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    var fusedLocationClient: FusedLocationProviderClient? = null
    var client: TrafficClient? = null
    private val sharedPreference = BaseEncryptedSharedPreference(this)

    fun getPreferences(filename: String) = sharedPreference.getPreferences(filename)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference.masterKeyBuild()
        val httpClient = OkHttpClient.Builder()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.yhs.kr")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        client = retrofit.create(TrafficClient::class.java)
        setContent {
            ComposeApp(this).Content()
        }

        if (!hasGPS()) {
            Log.i("MainActivity", "This Device has not GPS")
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        }
    }

    private fun hasGPS(): Boolean =
        packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
}