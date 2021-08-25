package student.ekeeda.com.ekeeda_student.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OtpDataModel(@SerializedName("UserId")
                        @Expose
                        val userId:Integer,
                        @SerializedName("Email")
                        @Expose
                        val email:String,
                        @SerializedName("Mobile")
                        @Expose
                        val mobile:String,
                        @SerializedName("OTP")
                        @Expose
                        val oTP:String,
                        @SerializedName("IsUsed")
                        @Expose
                        val isUsed:Integer,
                        @SerializedName("ExpiryTime")
                        @Expose
                        val expiryTime:String,
                        @SerializedName("GenerateTime")
                        @Expose
                        val generateTime:String,
                        @SerializedName("IsOTPSend")
                        @Expose
                        val isOTPSend:Boolean,
                        @SerializedName("IsRegistered")
                        @Expose
                        val isRegistered:Boolean,
                        @SerializedName("DisplayMessage")
                        @Expose
                        val displayMessage:String)