package student.ekeeda.com.ekeeda_student.LoginSignup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skydoves.elasticviews.ElasticFinishListener
import kotlinx.android.synthetic.main.profile_bottom_sheet1.*
import kotlinx.android.synthetic.main.stateprofile.*
import student.ekeeda.com.ekeeda_student.Interface.CallBackListener
import student.ekeeda.com.ekeeda_student.R
import student.ekeeda.com.ekeeda_student.customDialog.CustomDialog
import student.ekeeda.com.ekeeda_student.model.Item
import student.ekeeda.com.ekeeda_student.networking.Status
import student.ekeeda.com.ekeeda_student.viewmodel.LoginViewModel


class State_Screen : AppCompatActivity(),CallBackListener {

    lateinit var viewModel : LoginViewModel
    lateinit var dialog : CustomDialog
    lateinit var incomingOrderAdapter: AllClgListAdapter
    lateinit var collegeModels: ArrayList<Item>
    lateinit var filtercollegeModels: ArrayList<Item>
    lateinit var sheetBehavior1: BottomSheetBehavior<LinearLayout>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.stateprofile)
        dialog = CustomDialog(this)
      collegeModels = ArrayList()
        filtercollegeModels = ArrayList()
        sheetBehavior1 = BottomSheetBehavior.from(bottom_sheet1)


        rec1.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
           incomingOrderAdapter = AllClgListAdapter(this,this)

        setupViewModel()
        setupObserver()

        clg.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                getfilter(s.toString())

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
              filtercollegeModels.clear()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })



    }

    fun getfilter(text : String){

        filtercollegeModels.clear()

        if(!text.trim().equals(" ")) {
            for (d: Item in collegeModels) {
                if (d.stateName!!.toLowerCase().contains(text.toLowerCase())) {
                    filtercollegeModels.add(d)
                }
            }
        }
        // Log.d("AFTER",filterrecentCalls.size.toString())


        if(filtercollegeModels.size > 0){

            rec1.setVisibility(View.VISIBLE);
            notfound.setVisibility(View.GONE);

            incomingOrderAdapter.setList(filtercollegeModels)


            if(sheetBehavior1.getState() == BottomSheetBehavior.STATE_EXPANDED){

                sheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }

        }
        else {

            notfound.setVisibility(View.VISIBLE);
            rec1.setVisibility(View.GONE);

        }

        continue1.setOnClickListener {
            val inputMethodManager: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.getApplicationWindowToken(), 0)
            sheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);

        }

        elasticbutton1.setOnFinishListener(object : ElasticFinishListener{
            override fun onFinished() {

                val intent = Intent()
                intent.putExtra("statename", statename.getText().toString())
                setResult(3, intent)
                finish()

            }

        })


    }

    fun setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.GetState()
    }


    fun setupObserver() {

        viewModel.getstate().observe(this, Observer {

            when (it.status) {
                Status.SUCCESS -> {
                    dialog.hideDialog()

                    if (it.data?.message?.toLowerCase().equals("success")) {

                        Toast.makeText(this, it.data?.message, Toast.LENGTH_LONG).show()
                     /*   val intent = Intent(this, WebsiteMyview::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()*/
                        collegeModels = it.data!!.items as ArrayList<Item>
                        incomingOrderAdapter.setList(it.data!!.items)
                        rec1.adapter = incomingOrderAdapter

                    } else {
                        Toast.makeText(this, it.data?.message, Toast.LENGTH_LONG).show()

                    }
                }
                Status.LOADING -> {

                    dialog.showDialog()
                    //   Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()

                }
                Status.ERROR -> {
                    dialog.hideDialog()
                    Toast.makeText(
                        this,
                        "Something went wrong,please try again later",
                        Toast.LENGTH_LONG
                    ).show()

                }

            }

        })
    }

    override fun onCallBack(tid: Int, state: String?) {
        val intent = Intent()
        intent.putExtra("statename", state)
        intent.putExtra("stateid", tid)
        setResult(1, intent)
        finish()
    }


}