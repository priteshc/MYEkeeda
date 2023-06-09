package student.ekeeda.com.ekeeda_student.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import student.ekeeda.com.ekeeda_student.model.ForgetDataModel
import student.ekeeda.com.ekeeda_student.model.LoginDataModel
import student.ekeeda.com.ekeeda_student.networking.Resource
import student.ekeeda.com.ekeeda_student.networking.RetrofitBuilder

class SignupViewModel : ViewModel() {

     var signupmodel : MutableLiveData<Resource<LoginDataModel>> = MutableLiveData()
    var forgetmodel : MutableLiveData<Resource<ForgetDataModel>> = MutableLiveData()



    fun Signup(mobile : String,otp:String,fname:String,lname:String,email : String,password:String,platform:String,degree:String,branch:String,
    passoutyr: String,colgname:String,hrarbat:String,visitekeeda:String,carrerintrest:String){

        signupmodel.postValue(Resource.loading(null))

        val call: Call<LoginDataModel> = RetrofitBuilder.apiService.SignUp(mobile,email,fname, lname,password,platform,otp,colgname,hrarbat,
        passoutyr,visitekeeda,carrerintrest,degree,branch)
        call.enqueue(object : Callback<LoginDataModel> {

            override fun onResponse(call: Call<LoginDataModel>?, response: Response<LoginDataModel>?) {

                signupmodel.postValue(Resource.success(response?.body()) as Resource<LoginDataModel>?)

            }

            override fun onFailure(call: Call<LoginDataModel>?, t: Throwable?) {
                /*progerssProgressDialog.dismiss()
                println("error")*/
                signupmodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }


    fun Forget(mobile : String?,email : String?,password:String,otp:String){

        forgetmodel.postValue(Resource.loading(null))

        val call: Call<ForgetDataModel> = RetrofitBuilder.apiService1.Forget(mobile,email,password,otp)
        call.enqueue(object : Callback<ForgetDataModel> {

            override fun onResponse(call: Call<ForgetDataModel>?, response: Response<ForgetDataModel>?) {

                Log.d("OUTPUT",response?.body().toString())

                forgetmodel.postValue(Resource.success(response?.body()) as Resource<ForgetDataModel>?)

            }

            override fun onFailure(call: Call<ForgetDataModel>?, t: Throwable?) {
                /*progerssProgressDialog.dismiss()
                println("error")*/
                forgetmodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }




    fun getSignup(): LiveData<Resource<LoginDataModel>> {
        return signupmodel
    }


    fun getForget(): LiveData<Resource<ForgetDataModel>> {
        return forgetmodel
    }

}
