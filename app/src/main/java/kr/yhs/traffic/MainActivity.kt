package kr.yhs.traffic

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.common.api.Api
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kr.yhs.traffic.client.TrafficClient
import kr.yhs.traffic.models.StationInfo
import kr.yhs.traffic.ui.BaseApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    var fusedLocationClient: FusedLocationProviderClient? = null
    var client: TrafficClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.yhs.kr")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        client = retrofit.create(TrafficClient::class.java)

        setContent {
            BaseApplication(this)
        }

        if (!hasGPS()) {
            Log.i("MainActivity", "This Device has not GPS")
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        }
    }

    fun hasGPS(): Boolean = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
}