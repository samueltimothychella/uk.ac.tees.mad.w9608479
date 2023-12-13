package uk.ac.tees.mad.w9608479

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.SmsManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import uk.ac.tees.mad.w9608479.data.ContactDatasource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uk.ac.tees.mad.w9608479.databinding.ActivityMainBinding
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var navController: NavController
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // setContentView(R.layout.activity_main)

       setSupportActionBar(binding.appBarMain.toolbar)

        drawerLayout = binding.drawerLayout
        navView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)

       // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_contacts, R.id.nav_add_contact, R.id.nav_nearbyme, R.id.nav_logout
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navigate(R.id.nav_home)
                    true
                }
                R.id.nav_contacts -> {
                    navigate(R.id.nav_contacts)
                    true
                }
                R.id.nav_add_contact -> {
                    navigate(R.id.nav_add_contact)
                    true
                }
                R.id.nav_nearbyme -> {
                    navigate(R.id.nav_nearbyme)
                    true
                }
                R.id.nav_logout -> {
                    doSignout()
                    true
                }
                else -> {
                     false
                }
            }
        }

        init(navView)
    }

    override fun onSupportNavigateUp(): Boolean {
        //val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun init(navView:NavigationView) {

        var firebaseAuth = FirebaseAuth.getInstance();
        var user = firebaseAuth.getCurrentUser()

        val headerView: View = navView.getHeaderView(0)
        val navName:TextView =  headerView.findViewById(R.id.name);
        navName.setText(user?.displayName.toString());
        val navEmail:TextView =  headerView.findViewById(R.id.email);
        navEmail.setText(user?.email.toString());
    }
    private fun doSignout(){

        var firebaseAuth = FirebaseAuth.getInstance();
        var user = firebaseAuth.getCurrentUser()
        if (user != null) {
            firebaseAuth.signOut()

            val intent = Intent(baseContext, SignInActivity::class.java)
            startActivity(intent);
        }
    }

    public  fun helpme() {
        if (isLocationEnabled()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    val map = "https://www.google.com/maps?q="+location?.latitude.toString()+","+location?.longitude.toString()
                    var sms = "Help Me, My Location is https://www.google.com/maps?q="+location?.latitude.toString()+","+location?.longitude.toString()

                    //var smsManager:SmsManager = this.getSystemService(SmsManager::class.java)
                    //System.out.println(">>>>>>>>>>>>>"+Build.VERSION.SDK_INT)
                    val smsManager: SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
                       this.getSystemService(SmsManager::class.java)
                    } else {
                        SmsManager.getDefault()
                    }
                    val list:HashMap<String, String> = ContactDatasource().loadData(this)
                    for ((key, value) in list) {
                        smsManager.sendTextMessage(key, null, sms, null, null)
                    }

                    val location: Uri = Uri.parse(map) // z param is zoom level
                    startActivity(Intent(Intent.ACTION_VIEW, location))
                }

        } else {
            Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

        drawerLayout.closeDrawers()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    public fun navigate(resourceId:Int) {
        navController.navigate(resourceId)
        drawerLayout.closeDrawers()
    }


}