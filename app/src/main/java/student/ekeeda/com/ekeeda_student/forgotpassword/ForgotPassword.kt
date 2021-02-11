package student.ekeeda.com.ekeeda_student.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.forgot.*
import kotlinx.android.synthetic.main.loginsignup.*
import student.ekeeda.com.ekeeda_student.LoginSignup.SignupContinue
import student.ekeeda.com.ekeeda_student.R
import student.ekeeda.com.ekeeda_student.customDialog.CustomDialog
import student.ekeeda.com.ekeeda_student.networking.Status
import student.ekeeda.com.ekeeda_student.util.ValidationUtils
import student.ekeeda.com.ekeeda_student.viewmodel.LoginViewModel

class ForgotPassword : AppCompatActivity() {

    lateinit var dialog : CustomDialog
    lateinit var validation: ValidationUtils
    lateinit var viewModel : LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.forgot)

        dialog = CustomDialog(this)
        validation = ValidationUtils(this)
        SetupUI()
        setupViewModel()
        setupObserver()
    }


    fun SetupUI(){

        fotp.setOnClickListener {

            if(!fmobile_no.text.isEmpty()){

                if(validation.isNumber(fmobile_no.text.toString())){

                  if(validation.isValidMobile(fmobile_no.text.toString())){

                      viewModel.Otp(fmobile_no.text.toString(),3,true)

                  }
                    else{
                      Toast.makeText(this, "please enter valid Mobile Number", Toast.LENGTH_LONG).show()

                  }

                }
                else{

                    if(validation.isValidEmail(fmobile_no.text.toString())){

                        viewModel.Otp(fmobile_no.text.toString(),3,false)

                    }
                    else{
                        Toast.makeText(this, "please enter valid Email Id", Toast.LENGTH_LONG).show()

                    }

                }

            }
            else{
                Toast.makeText(this, "please enter registered Email id/ Mobile Number", Toast.LENGTH_LONG).show()
            }

        }

    }

    fun setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    fun setupObserver() {

        viewModel.getOtp().observe(this, Observer {

            when(it.status){
                Status.SUCCESS ->{
                    dialog.hideDialog()
                    if(it.data!!.isOTPSend) {
                        Toast.makeText(this, it.data?.displayMessage, Toast.LENGTH_LONG).show()
                        val intent = Intent(this, ForgetContinue::class.java)
                        intent.putExtra("mobnum", fmobile_no.text.toString())
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, it.data?.displayMessage, Toast.LENGTH_LONG).show()

                    }
                }
                Status.LOADING ->{
                    dialog.showDialog()
                    //   Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
                }
                Status.ERROR ->{
                    dialog.hideDialog()
                    Toast.makeText(this, "Something went wrong,please try again later", Toast.LENGTH_LONG).show()
                }

            }

        })


    }
    }