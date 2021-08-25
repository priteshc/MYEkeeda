package student.ekeeda.com.ekeeda_student.customDialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import student.ekeeda.com.ekeeda_student.R

class CustomDialog(var activity: Activity) {
   lateinit var dialog: Dialog

    fun showDialog() {
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.customdialog)
        dialog.show()
    }

    fun hideDialog() {
        dialog.dismiss()
    }

}