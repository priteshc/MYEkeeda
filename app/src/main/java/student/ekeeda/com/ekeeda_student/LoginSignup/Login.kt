package student.ekeeda.com.ekeeda_student.LoginSignup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.loginsignup.*
import student.ekeeda.com.ekeeda_student.HomePage.WebsiteMyview
import student.ekeeda.com.ekeeda_student.HomePage.WebsiteView
import student.ekeeda.com.ekeeda_student.customDialog.CustomDialog
import student.ekeeda.com.ekeeda_student.R
import student.ekeeda.com.ekeeda_student.forgotpassword.ForgotPassword
import student.ekeeda.com.ekeeda_student.networking.Status
import student.ekeeda.com.ekeeda_student.util.PrefManager
import student.ekeeda.com.ekeeda_student.util.Utils
import student.ekeeda.com.ekeeda_student.util.ValidationUtils
import student.ekeeda.com.ekeeda_student.viewmodel.LoginViewModel

class Login : AppCompatActivity() {

    lateinit var validation: ValidationUtils
    var from : String? = null
    lateinit var viewModel : LoginViewModel
    lateinit var dialog : CustomDialog
    lateinit var mypref : PrefManager
    var ip : String? = null
    var mac : String? = null
    var mac1 : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            intent.extras?.let {
              from = intent.extras!!.getString("from","")
            }
        setContentView(R.layout.loginsignup)

        setupViewModel()
        setupObserver()
        mypref = PrefManager(this)

        ip = Utils.getIPAddress(true)
        mac = Utils.getMACAddress("wlan0")
        mac1 = Utils.getMACAddress("eth0")

     //   mypref.userip = ip
        if(!mac.equals("")){
            mypref.usermac = mac
        }
        else{
            mypref.usermac = mac1

        }
        dialog = CustomDialog(this)
        validation = ValidationUtils(this)
        myview()
    }

    fun myview(){

        if(from.equals("login")){
            LCommonfor()
        }

        if(from.equals("signup")){
            SCommonfor()
        }

        logtxt.setOnClickListener {
            LCommonfor()
        }

        sigtxt.setOnClickListener {
            SCommonfor()
        }


        Lcontemail.setOnClickListener {
            lgopho.visibility = View.GONE
            Lgoemail.visibility = View.VISIBLE
        }

        Econtphonenumber.setOnClickListener {
            lgopho.visibility = View.VISIBLE
            Lgoemail.visibility = View.GONE
        }

        forgot.setOnClickListener {

            val intent = Intent(this, ForgotPassword::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        eforgot.setOnClickListener {

            val intent = Intent(this, ForgotPassword::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        Login()
        signup()
    }

    fun LCommonfor(){
        loginlayout.visibility = View.VISIBLE
        signuplayout.visibility = View.GONE
        sigview.visibility = View.GONE
        logview.visibility = View.VISIBLE
    }

    fun  SCommonfor(){
        loginlayout.visibility = View.GONE
        signuplayout.visibility = View.VISIBLE
        sigview.visibility = View.VISIBLE
        logview.visibility = View.GONE
    }

    fun Login() {

        Lnext.setOnClickListener {

            if (validation.isValidMobile(Lmobile_no.text.toString())) {
                lphoerror.visibility = View.GONE
                Lnext.visibility = View.GONE
                Lpassview.visibility = View.VISIBLE
                Llogin.visibility = View.VISIBLE
                Lhintpho.setTextColor(Color.parseColor("#8d9091"))


            } else {
                lphoerror.visibility = View.VISIBLE
                Lhintpho.setTextColor(Color.parseColor("#F31F1F"))
            }
        }

        Enext.setOnClickListener {

            if (validation.isValidEmail(Eemail_id.text.toString())) {
                Eemailerror.visibility = View.GONE
                Enext.visibility = View.GONE
                Epassview.visibility = View.VISIBLE
                Elogin.visibility = View.VISIBLE
                Ehintemil.setTextColor(Color.parseColor("#8d9091"))


            } else {
                Eemailerror.visibility = View.VISIBLE
                Ehintemil.setTextColor(Color.parseColor("#F31F1F"))
            }


        }

        Llogin.setOnClickListener {

            if (validation.isValidMobile(Lmobile_no.text.toString()) && !Lpassword.text.isEmpty()) {
                lphoerror.visibility = View.GONE
                Lpasserror.visibility = View.GONE
                Lhintpho.setTextColor(Color.parseColor("#8d9091"))
                Lpasshint.setTextColor(Color.parseColor("#8d9091"))

                mypref.username = Lmobile_no.text.toString()
                mypref.userpassword = Lpassword.text.toString()

                viewModel.Login(Lmobile_no.text.toString(),Lpassword.text.toString(),
                    mypref.userip!!, mypref.usermac!!
                )

            } else if (!validation.isValidMobile(Lmobile_no.text.toString()) && Lpassword.text.isEmpty()) {

                lphoerror.visibility = View.VISIBLE
                Lpasserror.visibility = View.VISIBLE
                Lhintpho.setTextColor(Color.parseColor("#F31F1F"))
                Lpasshint.setTextColor(Color.parseColor("#F31F1F"))
            } else if (validation.isValidMobile(Lmobile_no.text.toString()) && Lpassword.text.isEmpty()) {

                lphoerror.visibility = View.GONE
                Lpasserror.visibility = View.VISIBLE
                Lhintpho.setTextColor(Color.parseColor("#8d9091"))
                Lpasshint.setTextColor(Color.parseColor("#F31F1F"))

            } else if (!validation.isValidMobile(Lmobile_no.text.toString()) && !Lpassword.text.isEmpty()) {

                lphoerror.visibility = View.VISIBLE
                Lpasserror.visibility = View.GONE
                Lhintpho.setTextColor(Color.parseColor("#F31F1F"))
                Lpasshint.setTextColor(Color.parseColor("#8d9091"))

            }
        }

        Elogin.setOnClickListener {

            if (validation.isValidEmail(Eemail_id.text.toString()) && !Epassword.text.isEmpty()) {
                Eemailerror.visibility = View.GONE
                Eerror.visibility = View.GONE
                Ehintemil.setTextColor(Color.parseColor("#8d9091"))
                Epasshint.setTextColor(Color.parseColor("#8d9091"))

                mypref.username = Eemail_id.text.toString()
                mypref.userpassword = Epassword.text.toString()

                viewModel.Login(Eemail_id.text.toString(),Epassword.text.toString(),mypref.userip!!, mypref.usermac!!)

            } else if (!validation.isValidEmail(Eemail_id.text.toString()) && Epassword.text.isEmpty()) {

                Eemailerror.visibility = View.VISIBLE
                Eerror.visibility = View.VISIBLE
                Ehintemil.setTextColor(Color.parseColor("#F31F1F"))
                Epasshint.setTextColor(Color.parseColor("#F31F1F"))
            } else if (validation.isValidEmail(Eemail_id.text.toString()) && Epassword.text.isEmpty()) {

                Eemailerror.visibility = View.GONE
                Eerror.visibility = View.VISIBLE
                Ehintemil.setTextColor(Color.parseColor("#8d9091"))
                Epasshint.setTextColor(Color.parseColor("#F31F1F"))

            } else if (!validation.isValidEmail(Eemail_id.text.toString()) && !Epassword.text.isEmpty()) {

                Eemailerror.visibility = View.VISIBLE
                Eerror.visibility = View.GONE
                Ehintemil.setTextColor(Color.parseColor("#F31F1F"))
                Epasshint.setTextColor(Color.parseColor("#8d9091"))

            }
        }


    }

    fun signup() {

        snext.setOnClickListener {

            if (validation.isValidMobile(smobile_no.text.toString())) {
                spherror.visibility = View.GONE
                sphint.setTextColor(Color.parseColor("#8d9091"))

                viewModel.Otp(smobile_no.text.toString(),2,true)

            } else {
                spherror.visibility = View.VISIBLE
                sphint.setTextColor(Color.parseColor("#F31F1F"))
            }
        }

    }

    fun setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }


    fun setupObserver(){

        viewModel.getUsers().observe(this, Observer {

            when(it.status){
                Status.SUCCESS ->{
                    dialog.hideDialog()

                    if(it.data?.status?.toLowerCase().equals("success")) {
                        mypref.userid = it.data?.userId.toString()
                        mypref.userhistoryid = it.data?.loginHistoryid.toString()


                        Toast.makeText(this, it.data?.message, Toast.LENGTH_LONG).show()
                        val intent = Intent(this, WebsiteMyview::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, it.data?.message, Toast.LENGTH_LONG).show()

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


        viewModel.getOtp().observe(this, Observer {

            when(it.status){
                Status.SUCCESS ->{
                    dialog.hideDialog()
                    if(it.data!!.isOTPSend) {
                        Toast.makeText(this, it.data?.displayMessage, Toast.LENGTH_LONG).show()
                        val intent = Intent(this, SignupContinue::class.java)
                        intent.putExtra("mobnum", smobile_no.text.toString())
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

    override fun onStop() {
        super.onStop()
        this.viewModelStore.clear()
    }
}