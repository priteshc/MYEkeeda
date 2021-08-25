package student.ekeeda.com.ekeeda_student.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import student.ekeeda.com.ekeeda_student.model.LoginDataModel
import student.ekeeda.com.ekeeda_student.model.OtpDataModel
import student.ekeeda.com.ekeeda_student.model.StateModel
import student.ekeeda.com.ekeeda_student.networking.Resource
import student.ekeeda.com.ekeeda_student.networking.RetrofitBuilder

class LoginViewModel : ViewModel() {

     var loginmodel : MutableLiveData<Resource<LoginDataModel>> = MutableLiveData()
    var optmodel : MutableLiveData<Resource<OtpDataModel>> = MutableLiveData()
    var stateModel : MutableLiveData<Resource<StateModel>> = MutableLiveData()


    fun Login(username : String,password : String, ip: String,mac : String){

        loginmodel.postValue(Resource.loading(null))

        val call: Call<LoginDataModel> = RetrofitBuilder.apiService.Login(username,password,ip,mac,"Android")
        call.enqueue(object : Callback<LoginDataModel> {

            override fun onResponse(call: Call<LoginDataModel>?, response: Response<LoginDataModel>?) {

                loginmodel.postValue(Resource.success(response?.body()) as Resource<LoginDataModel>?)

            }

            override fun onFailure(call: Call<LoginDataModel>?, t: Throwable?) {
                /*progerssProgressDialog.dismiss()
                println("error")*/
                 loginmodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }

    fun Otp(username : String,otptype : Int,ismobemail:Boolean){

        optmodel.postValue(Resource.loading(null))

        val call: Call<OtpDataModel> = RetrofitBuilder.apiService.OTP(username,otptype,ismobemail)
        call.enqueue(object : Callback<OtpDataModel> {

            override fun onResponse(call: Call<OtpDataModel>?, response: Response<OtpDataModel>?) {

                optmodel.postValue(Resource.success(response?.body()) as Resource<OtpDataModel>?)

            }

            override fun onFailure(call: Call<OtpDataModel>?, t: Throwable?) {
                /*progerssProgressDialog.dismiss()
                println("error")*/
                optmodel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }


    fun GetState(){

        stateModel.postValue(Resource.loading(null))

        val call: Call<StateModel> = RetrofitBuilder.apiService.getState("767")
        call.enqueue(object : Callback<StateModel> {

            override fun onResponse(call: Call<StateModel>?, response: Response<StateModel>?) {

                stateModel.postValue(Resource.success(response?.body()) as Resource<StateModel>?)

            }

            override fun onFailure(call: Call<StateModel>?, t: Throwable?) {
                /*progerssProgressDialog.dismiss()
                println("error")*/
                stateModel.postValue(Resource.error(null,"something went wrong"))
            }

        })

    }



    fun getUsers(): LiveData<Resource<LoginDataModel>> {
        return loginmodel
    }

    fun getOtp(): LiveData<Resource<OtpDataModel>> {
        return optmodel
    }

    fun getstate(): LiveData<Resource<StateModel>> {
        return stateModel
    }
}
