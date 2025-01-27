package student.ekeeda.com.ekeeda_student.LoginSignup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.loginsignup.*
import kotlinx.android.synthetic.main.signup_continue.*
import student.ekeeda.com.ekeeda_student.HomePage.WebsiteMyview
import student.ekeeda.com.ekeeda_student.R
import student.ekeeda.com.ekeeda_student.customDialog.CustomDialog
import student.ekeeda.com.ekeeda_student.model.SignupScreenModel
import student.ekeeda.com.ekeeda_student.networking.Status
import student.ekeeda.com.ekeeda_student.util.PrefManager
import student.ekeeda.com.ekeeda_student.util.ValidationUtils
import student.ekeeda.com.ekeeda_student.viewmodel.LoginViewModel
import student.ekeeda.com.ekeeda_student.viewmodel.SignupViewModel
import java.util.*
import kotlin.collections.ArrayList

class SignupContinue : AppCompatActivity() {

    var mobnum : String? = null
    lateinit var validation : ValidationUtils
    lateinit var viewModel : SignupViewModel
    lateinit var dialog : CustomDialog
    lateinit var mypref : PrefManager
    lateinit var viewModel1 : LoginViewModel
    lateinit var signupScreenModel: SignupScreenModel

    var counter = 60
    val REQUEST_CODE = 1
    var stateid = 0
    var statename : String? = null
    val otherStrings = arrayOf("a", "b", "c")
    var years = ArrayList<String>()
    var degree1 = ArrayList<String>()
    var branch1 = ArrayList<String>()

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

        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (i in 2000..thisYear) {
            years.add(Integer.toString(i))
        }

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
                validation.isValidEmail(sceditemilanme.text.toString()) && !sceditpass.text.isEmpty() && !sceditotp.text.isEmpty() &&
                !clgfanme.text.isEmpty() && !degree.selectedItem.equals("Select Degree") && !branch.selectedItem.equals("Select Branch") &&
                 !passoutyr.selectedItem.equals("Select Years")
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
                schintdegree.setTextColor(Color.parseColor("#8d9091"))
                scerrordegree.visibility = View.GONE
                schintbranch.setTextColor(Color.parseColor("#8d9091"))
                scerrorbranch.visibility = View.GONE
                schintpassoutyr.setTextColor(Color.parseColor("#8d9091"))
                scerroryear.visibility = View.GONE
                schintcolgname.setTextColor(Color.parseColor("#8d9091"))
                scerrorcollegename.visibility = View.GONE

                mypref.username = scmobile_no.text.toString()
                mypref.userpassword = sceditpass.text.toString()

                val intent = Intent(this, SignupContinue1::class.java)
                intent.putExtra("mobile",scmobile_no.text.toString())
                intent.putExtra("otp",sceditotp.text.toString())
                intent.putExtra("fname",sceditfanme.text.toString())
                intent.putExtra("lname",sceditlanme.text.toString())
                intent.putExtra("email",sceditemilanme.text.toString())
                intent.putExtra("password",sceditpass.text.toString())
                intent.putExtra("degree",degree.selectedItem.toString())
                intent.putExtra("branch",branch.selectedItem.toString())
                intent.putExtra("passyr",passoutyr.selectedItem.toString())
                intent.putExtra("colgname",clgfanme.text.toString())
                intent.putExtra("list", signupScreenModel)
                startActivity(intent)
/*
                viewModel.Signup(scmobile_no.text.toString(),sceditemilanme.text.toString(),sceditfanme.text.toString(),
                    sceditlanme.text.toString(),sceditpass.text.toString(),"Android",sceditotp.text.toString(),state.text.toString(),stateid)
   */
            }
            else {

                Log.d("GGG","come")

                if (!validation.isValidMobile(scmobile_no.text.toString())) {

                  //  schintpho.setTextColor(Color.parseColor("#F31F1F"))
                   // scphoerror.visibility = View.VISIBLE
                    scmobile_no.background = getDrawable(R.drawable.editbg1)


                } else {
                 //   schintpho.setTextColor(Color.parseColor("#8d9091"))
                    scphoerror.visibility = View.GONE
                    scmobile_no.background = getDrawable(R.drawable.editbg)

                }

                if (sceditfanme.text.isEmpty()) {
                 //   schintfname.setTextColor(Color.parseColor("#F31F1F"))
                  //  scerrorfname.visibility = View.VISIBLE
                    sceditfanme.background = getDrawable(R.drawable.editbg1)

                } else {
                  //  schintfname.setTextColor(Color.parseColor("#8d9091"))
                  //  scerrorfname.visibility = View.GONE
                    sceditfanme.background = getDrawable(R.drawable.editbg)

                }

                if (sceditlanme.text.isEmpty()) {
                  //  schintlanme.setTextColor(Color.parseColor("#F31F1F"))
                   // scerrorlname.visibility = View.VISIBLE
                    sceditlanme.background = getDrawable(R.drawable.editbg1)

                } else {
                  //  schintlanme.setTextColor(Color.parseColor("#8d9091"))
                    scerrorlname.visibility = View.GONE
                    sceditlanme.background = getDrawable(R.drawable.editbg1)

                }
                if (!validation.isValidEmail(sceditemilanme.text.toString())) {
                  //  schintemailname.setTextColor(Color.parseColor("#F31F1F"))
                 //   scerroremail.visibility = View.VISIBLE
                    sceditemilanme.background = getDrawable(R.drawable.editbg1)

                } else {
                  //  schintemailname.setTextColor(Color.parseColor("#8d9091"))
                    scerroremail.visibility = View.GONE
                    sceditemilanme.background = getDrawable(R.drawable.editbg)

                }

                if (sceditpass.text.isEmpty()) {
                   // schintpass.setTextColor(Color.parseColor("#F31F1F"))
                    //scerrorpass.visibility = View.VISIBLE
                    sceditpass.background = getDrawable(R.drawable.editbg1)

                } else {
                   // schintpass.setTextColor(Color.parseColor("#8d9091"))
                    scerrorpass.visibility = View.GONE
                    sceditpass.background = getDrawable(R.drawable.editbg)

                }

                if (sceditotp.text.isEmpty()) {
                  //  schintotp.setTextColor(Color.parseColor("#F31F1F"))
                   // scerrorotp.visibility = View.VISIBLE
                    sceditotp.background = getDrawable(R.drawable.editbg1)

                } else {
                   // schintotp.setTextColor(Color.parseColor("#8d9091"))
                    scerrorotp.visibility = View.GONE
                    sceditotp.background = getDrawable(R.drawable.editbg)

                }

                if (degree.selectedItem.equals("Select Degree")) {
                   // schintdegree.setTextColor(Color.parseColor("#F31F1F"))
                  //  scerrordegree.visibility = View.VISIBLE
                    degree.background = getDrawable(R.drawable.dropdownselector1)

                } else {
                  //  schintdegree.setTextColor(Color.parseColor("#8d9091"))
                    scerrordegree.visibility = View.GONE
                    degree.background = getDrawable(R.drawable.dropdownselector)

                }

                if (branch.selectedItem.equals("Select Branch")) {
                  //  schintbranch.setTextColor(Color.parseColor("#F31F1F"))
                  //  scerrorbranch.visibility = View.VISIBLE
                    branch.background = getDrawable(R.drawable.dropdownselector1)

                } else {
                   // schintbranch.setTextColor(Color.parseColor("#8d9091"))
                    scerrorbranch.visibility = View.GONE
                    branch.background = getDrawable(R.drawable.dropdownselector1)

                }

                if (passoutyr.selectedItem.equals("Select Years")) {
                  //  schintpassoutyr.setTextColor(Color.parseColor("#F31F1F"))
                   // scerroryear.visibility = View.VISIBLE
                    passoutyr.background = getDrawable(R.drawable.dropdownselector1)

                } else {
                  //  schintpassoutyr.setTextColor(Color.parseColor("#8d9091"))
                    scerroryear.visibility = View.GONE
                    passoutyr.background = getDrawable(R.drawable.dropdownselector)

                }

                if (clgfanme.text.isEmpty()) {
                  //  schintcolgname.setTextColor(Color.parseColor("#F31F1F"))
                   // scerrorcollegename.visibility = View.VISIBLE
                    clgfanme.background = getDrawable(R.drawable.editbg1)

                } else {
                   // schintcolgname.setTextColor(Color.parseColor("#8d9091"))
                    scerrorcollegename.visibility = View.GONE
                    clgfanme.background = getDrawable(R.drawable.editbg)

                }
            }
            /*val intent = Intent(this, SignupContinue1::class.java)
            intent.putExtra("list", signupScreenModel)
            startActivity(intent)*/
        }

        resend.setOnClickListener {

            if (validation.isValidMobile(scmobile_no.text.toString()))
                {
                   // schintpho.setTextColor(Color.parseColor("#8d9091"))
                    scphoerror.visibility = View.GONE
                    resend.isEnabled = false
                    viewModel1.Otp(scmobile_no.text.toString(),2,true)
                }
                else{
               // schintpho.setTextColor(Color.parseColor("#F31F1F"))
                scphoerror.visibility = View.VISIBLE
            }
        }

        state.setOnClickListener {
            val intent = Intent(this, State_Screen::class.java)
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        viewModel1 = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel1.signupData();
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

        viewModel1.getSignupData().observe(this, Observer {

            when(it.status){
                Status.SUCCESS ->{
                    dialog.hideDialog()

                    signupScreenModel = it.data!!

                    degree1 = it.data?.degeree as ArrayList<String>
                    branch1 = it.data?.branch as ArrayList<String>

                    degree1.add(0,"Select Degree")
                    branch1.add(0,"Select Branch")
                    years.add(0,"Select Years")


                    val adapter = ArrayAdapter(this,
                        android.R.layout.simple_spinner_item, degree1
                    )
                    val adapter1 = ArrayAdapter(this,
                        android.R.layout.simple_spinner_item, branch1
                    )

                    val adapter2 = ArrayAdapter(this,
                        android.R.layout.simple_spinner_item,years
                    )

                    degree.adapter = adapter
                    branch.adapter = adapter1
                    passoutyr.adapter = adapter2
                   /* if(it.data!!.isOTPSend) {
                        Toast.makeText(this, it.data?.displayMessage, Toast.LENGTH_LONG).show()
                        startTimeCounter()
                    }
                    else{
                        Toast.makeText(this, it.data?.displayMessage, Toast.LENGTH_LONG).show()

                    }*/
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