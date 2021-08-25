package student.ekeeda.com.ekeeda_student.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Item {
    @SerializedName("StateId")
    @Expose
    var stateId: Int? = null

    @SerializedName("StateName")
    @Expose
    var stateName: String? = null

}