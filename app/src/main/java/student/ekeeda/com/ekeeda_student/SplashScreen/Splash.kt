package student.ekeeda.com.ekeeda_student.SplashScreen

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.mysplash.*
import student.ekeeda.com.ekeeda_student.HomePage.WebsiteMyview
import student.ekeeda.com.ekeeda_student.R
import student.ekeeda.com.ekeeda_student.introscreen.IntroActivity
import student.ekeeda.com.ekeeda_student.util.NetworkUtil
import student.ekeeda.com.ekeeda_student.util.PrefManager
import student.ekeeda.com.ekeeda_student.util.Utils
import java.io.IOException
import java.net.URL
import java.util.*


class Splash : AppCompatActivity() {

    lateinit var mypref : PrefManager
    lateinit var ip : String
    var mac : String? = null
    var mac1 : String? = null
    lateinit var getPublicIP: GetPublicIP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mysplash)

        mypref = PrefManager(this)


       // ip = Utils.getIPAddress(true)
        mac = Utils.getMACAddress("wlan0")
        mac1 = Utils.getMACAddress("eth0")

        getPublicIP = GetPublicIP()
        getPublicIP.execute()
        mypref.userip = getPublicIP.get()


        if(!mac.equals("")){
            mypref.usermac = mac
        }
        else{
            mypref.usermac = mac1

        }


        if (NetworkUtil.getConnectivityStatus(this.applicationContext) != 0) {
            myhandle()
        }
        else{
            val snack = Snackbar.make(cord, "Please check your internet connection", Snackbar.LENGTH_LONG)
           /* snack.setAction("Click Me") {
                // TODO when you tap on "Click Me"
            }*/
            snack.show()
        }

    }

    override fun onResume() {
        super.onResume()

        if (NetworkUtil.getConnectivityStatus(this.applicationContext) != 0) {

            myhandle()

        }
        else{
            val snack = Snackbar.make(cord, "Please check your internet connection", Snackbar.LENGTH_LONG)
            /* snack.setAction("Click Me") {
                 // TODO when you tap on "Click Me"
             }*/
            snack.show()
        }
    }

    fun myhandle(){

        val handler = Handler()
        handler.postDelayed({

            if(mypref.userid.equals("")){
                val intent = Intent(this, IntroActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this, WebsiteMyview::class.java)
                startActivity(intent)
                finish()
            }

        }, 2500)


    }


    class GetPublicIP : AsyncTask<String?, String?, String>() {
        override fun doInBackground(vararg params: String?): String {
            var publicIP = ""
            try {
                val s = Scanner(
                    URL(
                        "https://api.ipify.org"
                    )
                        .openStream(), "UTF-8"
                )
                    .useDelimiter("\\A")
                publicIP = s.next()
                Log.d("My current IP", publicIP)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return publicIP
        }

        override fun onPostExecute(publicIp: String) {
            super.onPostExecute(publicIp)
            Log.d("PublicIP", publicIp + "")

            //Here 'publicIp' is your desire public IP
        }


    }


}