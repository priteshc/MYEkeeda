package student.ekeeda.com.ekeeda_student.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForgetDataModel(@SerializedName("UserId")
                          @Expose
                          val userId:Integer,
                           @SerializedName("Name")
                          @Expose
                          val name:String,
                           @SerializedName("Email")
                          @Expose
                          val email:String,
                           @SerializedName("Mobile")
                          @Expose
                          val mobile:String,
                           @SerializedName("PurchaseProductIds")
                          @Expose
                          val purchaseProductIds:String,
                           @SerializedName("Message")
                          @Expose
                          val message:String,
                           @SerializedName("Status")
                          @Expose
                          val status:String)