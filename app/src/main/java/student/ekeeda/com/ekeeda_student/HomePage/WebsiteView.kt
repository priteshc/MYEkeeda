package student.ekeeda.com.ekeeda_student.HomePage

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.myhome_view.*
import student.ekeeda.com.ekeeda_student.R
import student.ekeeda.com.ekeeda_student.customDialog.CustomDialog
import student.ekeeda.com.ekeeda_student.util.PrefManager

class WebsiteView : AppCompatActivity() {

    lateinit  var dialog : CustomDialog
    lateinit var mypref : PrefManager
     var doubleBackToExitPressedOnce : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.myhome_view)

        var ua = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.63 Safari/537.31";
        mypref = PrefManager(this)
        dialog = CustomDialog(this)

        wv_customization.clearHistory()
        wv_customization.getSettings().setAppCacheEnabled(false)
        wv_customization.getSettings().setJavaScriptEnabled(true)
        wv_customization.getSettings().setUseWideViewPort(true);
       /* wv_customization.webChromeClient = object :WebChromeClient(){



        }
*/
        wv_customization.webViewClient = object :WebViewClient(){

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?
            ) {

                super.onReceivedError(view, request, error)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                dialog.hideDialog()

            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

            }

        }

        wv_customization.loadUrl("https://ekeeda.com/mobile/index?key=${mypref.userid}")
      //  wv_customization.loadUrl("https://dev.ekeeda.com/")

        dialog.showDialog()


    }
    

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.getAction() === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (wv_customization.canGoBack()) {
                        wv_customization.goBack()
                    } else {
                        if (doubleBackToExitPressedOnce) {
                            super.onBackPressed()
                            return true
                        }
                        doubleBackToExitPressedOnce = true
                        Toast.makeText(this, "Do you want to say googbye so soon?", Toast.LENGTH_SHORT).show()

                        Handler()
                            .postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Do you want to say googbye so soon?", Toast.LENGTH_SHORT).show()

        Handler()
            .postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

    }

  /*public class FullScreen(parent : ViewGroup,content : ViewGroup) : WebChromeClient(){


      lateinit var content: ViewGroup
      lateinit var parent: ViewGroup
      lateinit var customview : View

      internal var matchParentLayout: ViewGroup.LayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT)

      init{
          this.parent = parent
          this.content = content
      }
      override fun onHideCustomView() {
          content.visibility = ViewGroup.VISIBLE
          parent.removeViews
          customView = null
      }

      override fun onShowCustomView(view: View?, requestedOrientation: Int, callback: CustomViewCallback?
      ) {
          customView = view
          view.LayoutParameters = matchParentLayout
          parent.AddView(view)
          content.Visibility = ViewStates.Gone      }
  }
*/
}

