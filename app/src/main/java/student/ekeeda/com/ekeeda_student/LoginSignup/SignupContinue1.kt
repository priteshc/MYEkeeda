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
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.signup_continue.*
import kotlinx.android.synthetic.main.signup_continue.schintpho
import kotlinx.android.synthetic.main.signup_continue.scnext
import kotlinx.android.synthetic.main.signup_continue1.*
import student.ekeeda.com.ekeeda_student.HomePage.WebsiteMyview
import student.ekeeda.com.ekeeda_student.R
import student.ekeeda.com.ekeeda_student.customDialog.CustomDialog
import student.ekeeda.com.ekeeda_student.model.Question3Category
import student.ekeeda.com.ekeeda_student.model.SignupScreenModel
import student.ekeeda.com.ekeeda_student.networking.Status
import student.ekeeda.com.ekeeda_student.util.PrefManager
import student.ekeeda.com.ekeeda_student.util.ValidationUtils
import student.ekeeda.com.ekeeda_student.viewmodel.LoginViewModel
import student.ekeeda.com.ekeeda_student.viewmodel.SignupViewModel
import java.util.*
import kotlin.collections.ArrayList

class SignupContinue1 : AppCompatActivity(),OnInterestSelect {

    var mobnum : String? = null
    lateinit var validation : ValidationUtils
    lateinit var viewModel : SignupViewModel
    lateinit var dialog : CustomDialog
    lateinit var mypref : PrefManager
    lateinit var viewModel1 : LoginViewModel
    lateinit var signupScreenModel: SignupScreenModel
    var interestadpater: InterestAdapter? = null

    var counter = 60
    val REQUEST_CODE = 1
    var stateid = 0
    var statename : String? = null
    val otherStrings = arrayOf("a", "b", "c")
    var years = ArrayList<String>()
    var ques11 = ArrayList<String>()
    var ques22 = ArrayList<String>()
    var ques33 = ArrayList<Question3Category>()
    var interestlist: ArrayList<String> = ArrayList()
    var mobile: String? = null
    var otp: String? = null
    var fname: String? = null
    var lname: String? = null
    var email: String? = null
    var password: String? = null
    var degree: String? = null
    var branch: String? = null
    var passyr: String? = null
    var colgname: String? = null
    var interest: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        intent.extras?.let {
            signupScreenModel = intent.extras!!.getSerializable("list") as SignupScreenModel
            mobile = intent.getStringExtra("mobile")
            otp = intent.getStringExtra("otp")
            fname = intent.getStringExtra("fname")
            lname = intent.getStringExtra("lname")
            email = intent.getStringExtra("email")
            password = intent.getStringExtra("password")
            degree = intent.getStringExtra("degree")
            branch = intent.getStringExtra("branch")
            passyr = intent.getStringExtra("passyr")
            colgname = intent.getStringExtra("colgname")

        }
        setContentView(R.layout.signup_continue1)

       // startTimeCounter()
        mypref = PrefManager(this)
        dialog = CustomDialog(this)
        validation = ValidationUtils(this)
        interestadpater = InterestAdapter(this,this)

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        interest_recycler.layoutManager = layoutManager
        interest_recycler.adapter = interestadpater

        signupScreenModel.que3Ans?.forEach {

            var ques = Question3Category(it,false);
            ques33.add(ques)
        }


        interestadpater?.setList(ques33)



        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (i in 1900..thisYear) {
            years.add(Integer.toString(i))
        }

        ques11 = signupScreenModel.que1Ans as ArrayList<String>
        ques22 = signupScreenModel.que2Ans as ArrayList<String>

        ques11.add(0,"Select Option")
        ques22.add(0,"Select Option")
        //  years.add(0,"Select Years")


        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, ques11
        )
        val adapter1 = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, ques22
        )



        ques1.adapter = adapter
        ques2.adapter = adapter1



        setupViewModel()
        setupObserver()

      /*  mobnum?.let {
            scmobile_no.setText(mobnum)
        }*/





       Svalidation()
    }



    fun  Svalidation (){

        scnext.setOnClickListener {

            if (!ques1.selectedItem.equals("Select Option") && !ques2.selectedItem.equals("Select Option") && interestlist.size == 3
            ) {


                scerrorselect.visibility = View.GONE
                scerrorselect1.visibility = View.GONE
                scerrorselect2.visibility = View.GONE

              /*  mypref.username = scmobile_no.text.toString()
                mypref.userpassword = sceditpass.text.toString()
*/
                interestlist.forEach {
                    if(interest != null) {
                        interest = interest + it + ","
                    }
                    else{
                        interest = it+","
                    }
                }

                Log.d("INT",interest!!);

               /* mobile : String,otp:String,fname:String,lname:String,email : String,password:String,platform:String,degree:String,branch:String,
                passoutyr: String,colgname:String,hrarbat:String,visitekeeda:String,carrerintrest:String*/
                viewModel.Signup(mobile!!,otp!!,fname!!,lname!!,email!!,password!!,"Android",degree!!,branch!!,passyr!!,colgname!!,
                    ques1.selectedItem.toString(),ques1.selectedItem.toString(),interest!!.substring(0,interest!!.length - 1)!!)
            }
            else {


                if (ques1.selectedItem.equals("Select Option")) {
                    scerrorselect.visibility = View.VISIBLE
                } else {
                    scerrorselect.visibility = View.GONE
                }

                if (ques2.selectedItem.equals("Select Option")) {
                    scerrorselect1.visibility = View.VISIBLE
                } else {
                    scerrorselect1.visibility = View.GONE
                }


                if (interestlist.size < 3) {
                    scerrorselect2.visibility = View.VISIBLE
                } else {
                    scerrorselect2.visibility = View.GONE
                }
            }


        }

       /* resend.setOnClickListener {

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
        }*//*

        state.setOnClickListener {
            val intent = Intent(this, State_Screen::class.java)
            startActivityForResult(intent, REQUEST_CODE);
        }*/
    }

    fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        viewModel1 = ViewModelProvider(this).get(LoginViewModel::class.java)

       // viewModel1.signupData();
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



                    ques11 = it.data?.que1Ans as ArrayList<String>
                    ques22 = it.data?.que2Ans as ArrayList<String>

                    ques11.add(0,"Select Option")
                    ques22.add(0,"Select Option")
                  //  years.add(0,"Select Years")


                    val adapter = ArrayAdapter(this,
                        android.R.layout.simple_spinner_item, ques11
                    )
                    val adapter1 = ArrayAdapter(this,
                        android.R.layout.simple_spinner_item, ques22
                    )



                    ques1.adapter = adapter
                    ques2.adapter = adapter1

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

    override fun onInterest(position: Int, status: Boolean, intname: String) {
        if(status == false){

            interestlist.add(intname)
            interestadpater?.setClick(position,true)

        }
        else{
            interestlist.remove(intname)
            interestadpater?.setClick(position,false)

        }    }

}