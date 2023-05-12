package student.ekeeda.com.ekeeda_student.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Question3Category {
    constructor(name: String?, check: Boolean?) {
        this.name = name
        this.check = check
    }

    var name: String? = null

    var check : Boolean? = false
}