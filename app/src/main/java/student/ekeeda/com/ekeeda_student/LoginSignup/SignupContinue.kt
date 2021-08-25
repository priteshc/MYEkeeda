package student.ekeeda.com.ekeeda_student.LoginSignup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.signup_continue.*
import student.ekeeda.com.ekeeda_student.HomePage.WebsiteMyview
import student.ekeeda.com.ekeeda_student.R
import student.ekeeda.com.ekeeda_student.customDialog.CustomDialog
import student.ekeeda.com.ekeeda_student.networking.Status
import student.ekeeda.com.ekeeda_student.util.PrefManager
import student.ekeeda.com.ekeeda_student.util.ValidationUtils
import student.ekeeda.com.ekeeda_student.viewmodel.LoginViewModel
import student.ekeeda.com.ekeeda_student.viewmodel.SignupViewModel

class SignupContinue : AppCompatActivity() {

    var mobnum : String? = null
    lateinit var validation : ValidationUtils
    lateinit var viewModel : SignupViewModel
    lateinit var dialog : CustomDialog
    lateinit var mypref : PrefManager
    lateinit var viewModel1 : LoginViewModel

    var counter = 60
    val REQUEST_CODE = 1
    var stateid = 0
    var statename : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {
            mobnum = intent.extras!!.getString("mobnum","")
        }
        setContentView(R.layout.signup_continue)

        startTimeCounter()
        mypref = PrefManager(this)
        dialog = CustomDialog(this)
        validation = ValidationUtils(this)
        setupViewModel()
        setupObserver()

        mobnum?.let {
            scmobile_no.setText(mobnum)
        }

        Svalidation()
    }



    fun  Svalidation (){

        scnext.setOnClickListener {

            if (validation.isValidMobile(scmobile_no.text.toString()) && !sceditfanme.text.isEmpty() && !sceditlanme.text.isEmpty() &&
                validation.isValidEmail(sceditemilanme.text.toString()) && !sceditpass.text.isEmpty() && !sceditotp.text.isEmpty()&& !state.text.equals("Select State")
            ) {


                schintpho.setTextColor(Color.parseColor("#8d9091"))
                scphoerror.visibility = View.GONE
                schintfname.setTextColor(Color.parseColor("#8d9091"))
                scerrorfname.visibility = View.GONE
                schintlanme.setTextColor(Color.parseColor("#8d9091"))
                scerrorlname.visibility = View.GONE
                schintemailname.setTextColor(Color.parseColor("#8d9091"))
                scerroremail.visibility = View.GONE
                schintpass.setTextColor(Color.parseColor("#8d9091"))
                scerrorpass.visibility = View.GONE
                schintotp.setTextColor(Color.parseColor("#8d9091"))
                scerrorotp.visibility = View.GONE

                mypref.username = scmobile_no.text.toString()
                mypref.userpassword = sceditpass.text.toString()

                viewModel.Signup(scmobile_no.text.toString(),sceditemilanme.text.toString(),sceditfanme.text.toString(),
                    sceditlanme.text.toString(),sceditpass.text.toString(),"Android",sceditotp.text.toString(),state.text.toString(),stateid)
            }
            else {

                if (!validation.isValidMobile(scmobile_no.text.toString())) {

                    schintpho.setTextColor(Color.parseColor("#F31F1F"))
                    scphoerror.visibility = View.VISIBLE

                } else {
                    schintpho.setTextColor(Color.parseColor("#8d9091"))
                    scphoerror.visibility = View.GONE
                }

                if (sceditfanme.text.isEmpty()) {
                    schintfname.setTextColor(Color.parseColor("#F31F1F"))
                    scerrorfname.visibility = View.VISIBLE
                } else {
                    schintfname.setTextColor(Color.parseColor("#8d9091"))
                    scerrorfname.visibility = View.GONE
                }

                if (sceditlanme.text.isEmpty()) {
                    schintlanme.setTextColor(Color.parseColor("#F31F1F"))
                    scerrorlname.visibility = View.VISIBLE
                } else {
                    schintlanme.setTextColor(Color.parseColor("#8d9091"))
                    scerrorlname.visibility = View.GONE
                }
                if (!validation.isValidEmail(sceditemilanme.text.toString())) {
                    schintemailname.setTextColor(Color.parseColor("#F31F1F"))
                    scerroremail.visibility = View.VISIBLE
                } else {
                    schintemailname.setTextColor(Color.parseColor("#8d9091"))
                    scerroremail.visibility = View.GONE
                }

                if (sceditpass.text.isEmpty()) {
                    schintpass.setTextColor(Color.parseColor("#F31F1F"))
                    scerrorpass.visibility = View.VISIBLE
                } else {
                    schintpass.setTextColor(Color.parseColor("#8d9091"))
                    scerrorpass.visibility = View.GONE
                }

                if (sceditotp.text.isEmpty()) {
                    schintotp.setTextColor(Color.parseColor("#F31F1F"))
                    scerrorotp.visibility = View.VISIBLE
                } else {
                    schintotp.setTextColor(Color.parseColor("#8d9091"))
                    scerrorotp.visibility = View.GONE
                }
            }
        }

        resend.setOnClickListener {

            if (validation.isValidMobile(scmobile_no.text.toString()))
                {
                    schintpho.setTextColor(Color.parseColor("#8d9091"))
                    scphoerror.visibility = View.GONE
                    resend.isEnabled = false
                    viewModel1.Otp(scmobile_no.text.toString(),2,true)
                }
                else{
                schintpho.setTextColor(Color.parseColor("#F31F1F"))
                scphoerror.visibility = View.VISIBLE
            }
        }

        state.setOnClickListener {
            val intent = Intent(this, State_Screen::class.java)
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    fun setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)
        viewModel1 = ViewModelProviders.of(this).get(LoginViewModel::class.java)

    }


    fun setupObserver() {

        viewModel.getSignup().observe(this, Observer {

            when (it.status) {
                Status.SUCCESS -> {
                    dialog.hideDialog()
                  //  mypref.userid = it.data?.userId.toString()
                    if(it.data?.status?.toLowerCase().equals("success")) {
                        mypref.userid = it.data?.userId.toString()
                        mypref.userhistoryid = it.data?.loginHistoryid.toString()
                        Toast.makeText(this, it.data?.message, Toast.LENGTH_LONG).show()
                        val intent = Intent(this, WebsiteMyview::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.putExtra("from", "login")
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, it.data?.message, Toast.LENGTH_LONG).show()

                    }
                }
                Status.LOADING -> {

                    dialog.showDialog()
                   // Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()

                }
                Status.ERROR -> {

                    dialog.hideDialog()
                    Toast.makeText(this, "Something went wrong,please try again later", Toast.LENGTH_LONG).show()

                }

            }

        })

        viewModel1.getOtp().observe(this, Observer {

            when(it.status){
                Status.SUCCESS ->{
                    dialog.hideDialog()
                    if(it.data!!.isOTPSend) {
                        Toast.makeText(this, it.data?.displayMessage, Toast.LENGTH_LONG).show()
                        startTimeCounter()
                    }
                    else{
                        Toast.makeText(this, it.data?.displayMessage, Toast.LENGTH_LONG).show()

                    }
                }
                Status.LOADING ->{

                    dialog.showDialog()
               //     Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()

                }
                Status.ERROR ->{
                    dialog.hideDialog()
                    resend.isEnabled = true
                    Toast.makeText(this, "Something went wrong,please try again later", Toast.LENGTH_LONG).show()

                }

            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if(data != null) {

                stateid = data!!.getIntExtra("stateid", 0)
                state.text = data!!.getStringExtra("statename")
            }
        }

        if (requestCode == 2) {
            if(data != null) {

                //  stateid = data!!.getIntExtra("statename", 0)
                state.text = data!!.getStringExtra("statename")
                stateid = 0
            }
        }

    }

    override fun onStop() {
        super.onStop()
        this.viewModelStore.clear()
    }


    fun startTimeCounter() {
        counter = 60
        resend.isEnabled = false

        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                numtext.text = counter.toString()
                counter--
            }
            override fun onFinish() {
                numtext.text = "00"
                resend.isEnabled = true

            }
        }.start()
    }

    }