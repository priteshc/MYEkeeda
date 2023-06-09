package student.ekeeda.com.ekeeda_student.networking

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import student.ekeeda.com.ekeeda_student.model.*

interface ApiService {

    @FormUrlEncoded
    @POST("Auth/Auth/Login")
    fun Login(@Field("UserName") uname:String, @Field("Password") pass : String,@Field("IPAddress") ipadress : String,
              @Field("MacAddress") macadress : String,@Field("Platform") platform : String): Call<LoginDataModel>

    @FormUrlEncoded
    @POST("Auth/Auth/OTPSend")
    fun OTP(@Field("Username") uname:String,@Field("OTPType") otpTyp:Int,@Field("IsMobile") ismob:Boolean): Call<OtpDataModel>


    @FormUrlEncoded
    @POST("Auth/Signup")
    fun SignUp(@Field("Mobile") mobile:String, @Field("Email") email : String,
               @Field("FirstName") fname:String, @Field("LastName") lname : String,
               @Field("Password") pass:String, @Field("PlatformType") platform : String,
               @Field("OTP") otp:String,@Field("otherCollege") ocolgname:String,
               @Field("hearabout") hearabout:String,@Field("GraduationYear") passoutyr:String,
               @Field("VisitEkeeda") visit:String,@Field("CareerInterest") interest:String,
               @Field("DegreeName") degname:String,@Field("BranchName") branchname:String
               ): Call<LoginDataModel>


    @FormUrlEncoded
    @POST("Auth/Auth/ForgotPassword")
    fun Forget(@Field("Mobile") mobile: String?, @Field("Email") email: String?,
               @Field("Password") pass:String, @Field("OTP") otp:String): Call<ForgetDataModel>


    @FormUrlEncoded
    @POST("Auth/Auth/Logout")
    fun Logout(@Field("UserId") userid:Int, @Field("LoginHistoryId") loghistoryid : Int): Call<LoginDataModel>

    @FormUrlEncoded
    @POST("State/StateList")
    fun getState(@Field("Mobile") mobile:String): Call<StateModel>

    @GET("Auth/Signup")
    fun SignupData(): Call<SignupScreenModel>

}