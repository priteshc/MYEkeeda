package student.ekeeda.com.ekeeda_student.util

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "Ekeeda_New"
    val MYTEST = "userID"
    val MYID = "userHistoryID"
    val MYUSER = "username"
    val MYPASSWORD = "userpassword"
    val MYIP = "ip"
    val MYMAC = "mac"

    val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)


    var userid: String?
        get() = pref.getString(MYTEST, "")
        set(value) = pref.edit().putString(MYTEST, value).apply()


    var userhistoryid: String?
        get() = pref.getString(MYID, "")
        set(value) = pref.edit().putString(MYID, value).apply()

    var username: String?
        get() = pref.getString(MYUSER, "")
        set(value) = pref.edit().putString(MYUSER, value).apply()

    var userpassword: String?
        get() = pref.getString(MYPASSWORD, "")
        set(value) = pref.edit().putString(MYPASSWORD, value).apply()

    var userip: String?
        get() = pref.getString(MYIP, "")
        set(value) = pref.edit().putString(MYIP, value).apply()

    var usermac: String?
        get() = pref.getString(MYMAC, "")
        set(value) = pref.edit().putString(MYMAC, value).apply()
}