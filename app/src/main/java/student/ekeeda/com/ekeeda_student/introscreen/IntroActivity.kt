package student.ekeeda.com.ekeeda_student.introscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.intro_main.*
import student.ekeeda.com.ekeeda_student.LoginSignup.Login
import student.ekeeda.com.ekeeda_student.LoginSignup.SignupContinue
import student.ekeeda.com.ekeeda_student.R

class IntroActivity:AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_main)

        setupViewPager(viewpager)
        viewpager.setCurrentItem(0)
        myview()
    }


    fun myview(){

        button_view.setOnCheckedChangeListener { radioGroup, i ->

            if (i == R.id.radio1){
                viewpager.currentItem = 0
            }
            else if (i == R.id.radio2){
                viewpager.currentItem = 1
            }
            else if (i == R.id.radio3){
                viewpager.currentItem = 2
            }
            else if (i == R.id.radio4){
                viewpager.currentItem = 3
            }

        }

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    radio1.isChecked = true
                } else if (position == 1) {
                    radio2.isChecked = true
                } else if (position == 2) {
                    radio3.isChecked = true
                } else if (position == 3) {
                    radio4.isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        Isignup.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            intent.putExtra("from","signup")
            startActivity(intent)
            finish()
        }

        Ilogin.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            intent.putExtra("from","login")
            startActivity(intent)
            finish()
        }


    }


    fun setupViewPager(viewpager : ViewPager){

        if(viewpager != null) {
            val adapter = ViewPagerAdapter(supportFragmentManager)
            adapter.addFragment(Screen1())
            adapter.addFragment(Screen2())
            adapter.addFragment(Screen3())
            adapter.addFragment(Screen4())
            viewpager.adapter = adapter
        }

    }

}