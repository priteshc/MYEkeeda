package student.ekeeda.com.ekeeda_student.application

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import student.ekeeda.com.ekeeda_student.util.PrefManager
import student.ekeeda.com.ekeeda_student.util.Utils

class Myapp : Application(),LifeCycleDelegate {

    var ip : String? = null
    var mac : String? = null
    var mac1 : String? = null
    lateinit var mypref : PrefManager

   lateinit var jobScheduler :JobScheduler
    lateinit var jobScheduler1 :JobScheduler
    lateinit var appLifecycleHandler: AppLifecycleHandler

    companion object{
        var BASE_URL:String="https://testapi.ekeeda.com/api/Auth/"
       // var BASE_URL:String="https://testapi.ekeeda.com/api/Auth/"

        lateinit var instance: Myapp
           // private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
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

        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler1 = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        appLifecycleHandler = AppLifecycleHandler(this)
        registerLifeCycleHandler(appLifecycleHandler)




    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    override fun onAppBackgrounded() {
        if (jobScheduler1 != null) {
            jobScheduler1.cancel(1001)
        }

        Log.d("LOG","LOGOUT")
        jobScheduler1.schedule(
            JobInfo.Builder(
                1001,
                ComponentName(this, SendLogoutInstance::class.java)
            )
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(false)
                .build()
        )
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    override fun onAppForegrounded() {
        if (jobScheduler != null) {
            jobScheduler.cancel(1000)
        }
        Log.d("LOG","LOGIN")

        jobScheduler.schedule(
            JobInfo.Builder(
                1000,
                ComponentName(this, SendLoginInstance::class.java)
            )
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(false)
                .build()
        )    }


   fun registerLifeCycleHandler(lifecycleHandler : AppLifecycleHandler) {
        registerActivityLifecycleCallbacks(lifecycleHandler);
        registerComponentCallbacks(lifecycleHandler);
    }
}