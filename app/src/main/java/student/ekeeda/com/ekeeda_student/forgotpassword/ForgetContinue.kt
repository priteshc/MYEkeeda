package student.ekeeda.com.ekeeda_student.forgotpassword

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.forget_continue.*
import kotlinx.android.synthetic.main.forgot.*
import kotlinx.android.synthetic.main.signup_continue.*
import kotlinx.android.synthetic.main.signup_continue.numtext
import kotlinx.android.synthetic.main.signup_continue.resend
import kotlinx.android.synthetic.main.signup_continue.schintpho
import kotlinx.android.synthetic.main.signup_continue.scnext
import student.ekeeda.com.ekeeda_student.LoginSignup.Login
import student.ekeeda.com.ekeeda_student.R
import student.ekeeda.com.ekeeda_student.customDialog.CustomDialog
import student.ekeeda.com.ekeeda_student.networking.Status
import student.ekeeda.com.ekeeda_student.util.PrefManager
import student.ekeeda.com.ekeeda_student.util.ValidationUtils
import student.ekeeda.com.ekeeda_student.viewmodel.LoginViewModel
import student.ekeeda.com.ekeeda_student.viewmodel.SignupViewModel

class ForgetContinue : AppCompatActivity() {

    var mobnum : String? = null
    lateinit var validation : ValidationUtils
    lateinit var viewModel : SignupViewModel
    lateinit var dialog : CustomDialog
    lateinit var mypref : PrefManager
    lateinit var viewModel1 : LoginViewModel

    var counter = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {
            mobnum = intent.extras!!.getString("mobnum","")
        }
        setContentView(R.layout.forget_continue)

        startTimeCounter()
        mypref = PrefManager(this)
        dialog = CustomDialog(this)
        validation = ValidationUtils(this)
        setupViewModel()
        setupObserver()
        mobnum?.let {
            fcmobile_no.setText(mobnum)
        }

        Svalidation()
    }

    fun  Svalidation (){

        fcnext.setOnClickListener {

            if(!fcmobile_no.text.isEmpty()&& !fceditpass.text.isEmpty() && !fceditpassc.text.isEmpty() && !fceditotp.text.isEmpty()){

                if(fceditpass.text.toString().equals(fceditpassc.text.toString())) {

                    if (validation.isNumber(fcmobile_no.text.toString())) {

                        if (validation.isValidMobile(fcmobile_no.text.toString())) {
                            viewModel.Forget(
                                fcmobile_no.text.toString(), null,
                                fceditpass.text.toString(),
                                fceditotp.text.toString()
                            )
                        } else {
                            Toast.makeText(
                                this,
                                "Please enter valid Mobile Number",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    } else {

                        if (validation.isValidEmail(fcmobile_no.text.toString())) {
                            viewModel.Forget(
                                null,
                                fcmobile_no.text.toString(),
                                fceditpass.text.toString(),
                                fceditotp.text.toString()
                            )
                        } else {
                            Toast.makeText(this, "please enter valid Eamil Id", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this, "Entered password and confirm password doesn't match", Toast.LENGTH_LONG).show()
                }
                }
            else{

                Toast.makeText(this, "Please Fill above all details", Toast.LENGTH_LONG).show()
            }


        }

        resend.setOnClickListener {

            if(!fcmobile_no.text.isEmpty()){
                if(validation.isNumber(fcmobile_no.text.toString())){
                    if(validation.isValidMobile(fcmobile_no.text.toString())){
                        viewModel1.Otp(fcmobile_no.text.toString(),3,true)
                    }
                    else{
                        Toast.makeText(this, "please enter valid Mobile Number", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    if(validation.isValidEmail(fcmobile_no.text.toString())){
                        viewModel1.Otp(fcmobile_no.text.toString(),3,false)
                    }
                    else{
                        Toast.makeText(this, "please enter valid Eamil Id", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                Toast.makeText(this, "please enter registered Email id/ Mobile Number", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)
        viewModel1 = ViewModelProviders.of(this).get(LoginViewModel::class.java)

    }


    fun setupObserver() {

        viewModel.getForget().observe(this, Observer {

            when (it.status) {
                Status.SUCCESS -> {
                    dialog.hideDialog()
                  //  mypref.userid = it.data?.userId.toString()
                    if(it.data?.status?.toLowerCase().equals("success")) {
                        Toast.makeText(this, it.data?.message, Toast.LENGTH_LONG).show()
                        mypref.userid = ""
                        val intent = Intent(this, Login::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.putExtra("from", "login")
                        startActivity(intent)
                        finish()
                    }
                    else{

                       // Toast.makeText(this, it.data?.status, Toast.LENGTH_LONG).show()

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
                    Toast.makeText(this, "Something went wrong,please try again later", Toast.LENGTH_LONG).show()

                }

            }

        })

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