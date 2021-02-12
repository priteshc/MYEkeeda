package student.ekeeda.com.ekeeda_student.SplashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import student.ekeeda.com.ekeeda_student.HomePage.WebsiteMyview
import student.ekeeda.com.ekeeda_student.HomePage.WebsiteView
import student.ekeeda.com.ekeeda_student.R
import student.ekeeda.com.ekeeda_student.introscreen.IntroActivity
import student.ekeeda.com.ekeeda_student.util.PrefManager
import student.ekeeda.com.ekeeda_student.util.Utils

class Splash : AppCompatActivity() {

    lateinit var mypref : PrefManager
    var ip : String? = null
    var mac : String? = null
    var mac1 : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mysplash)

        mypref = PrefManager(this)


        ip = Utils.getIPAddress(true)
        mac = Utils.getMACAddress("wlan0")
        mac1 = Utils.getMACAddress("eth0")

        mypref.userip = ip
        if(!mac.equals("")){
            mypref.usermac = mac
        }
        else{
            mypref.usermac = mac1

        }


        myhandle()
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





}