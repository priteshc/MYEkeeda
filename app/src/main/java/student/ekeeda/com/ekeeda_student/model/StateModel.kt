package student.ekeeda.com.ekeeda_student.model

import android.content.ClipData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class StateModel {
    @SerializedName("Items")
    @Expose
    var items: List<Item>? = null

    @SerializedName("Status")
    @Expose
    var status: Boolean? = null

    @SerializedName("Message")
    @Expose
    var message: String? = null

}