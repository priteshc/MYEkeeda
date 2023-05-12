package student.ekeeda.com.ekeeda_student.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class SignupScreenModel : Serializable {
    @SerializedName("Degeree")
    @Expose
    var degeree: List<String>? = null

    @SerializedName("Branch")
    @Expose
    var branch: List<String>? = null

    @SerializedName("allQuestions")
    @Expose
    var allQuestions: List<String>? = null

    @SerializedName("que1Ans")
    @Expose
    var que1Ans: List<String>? = null

    @SerializedName("que2Ans")
    @Expose
    var que2Ans: List<String>? = null

    @SerializedName("que3Ans")
    @Expose
    var que3Ans: List<String>? = null

}