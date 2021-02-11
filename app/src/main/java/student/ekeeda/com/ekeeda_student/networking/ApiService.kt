package student.ekeeda.com.ekeeda_student.networking

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import student.ekeeda.com.ekeeda_student.model.ForgetDataModel
import student.ekeeda.com.ekeeda_student.model.LoginDataModel
import student.ekeeda.com.ekeeda_student.model.OtpDataModel

interface ApiService {

    @FormUrlEncoded
    @POST("Login")
    fun Login(@Field("UserName") uname:String, @Field("Password") pass : String,@Field("IPAddress") ipadress : String,
              @Field("MacAddress") macadress : String,@Field("Platform") platform : String): Call<LoginDataModel>

    @FormUrlEncoded
    @POST("OTPSend")
    fun OTP(@Field("Username") uname:String,@Field("OTPType") otpTyp:Int,@Field("IsMobile") ismob:Boolean): Call<OtpDataModel>


    @FormUrlEncoded
    @POST("Signup")
    fun SignUp(@Field("Mobile") mobile:String, @Field("Email") email : String,
               @Field("FirstName") fname:String, @Field("LastName") lname : String,
               @Field("Password") pass:String, @Field("PlatformType") platform : String,
               @Field("OTP") otp:String): Call<LoginDataModel>


    @FormUrlEncoded
    @POST("ForgotPassword")
    fun Forget(@Field("Mobile") mobile: String?, @Field("Email") email: String?,
               @Field("Password") pass:String, @Field("OTP") otp:String): Call<ForgetDataModel>


    @FormUrlEncoded
    @POST("Logout")
    fun Logout(@Field("UserId") userid:Int, @Field("LoginHistoryId") loghistoryid : Int): Call<LoginDataModel>
}