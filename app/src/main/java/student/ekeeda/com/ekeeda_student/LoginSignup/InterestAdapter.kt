package student.ekeeda.com.ekeeda_student.LoginSignup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.RelativeLayout
import android.widget.TextView

import student.ekeeda.com.ekeeda_student.R
import student.ekeeda.com.ekeeda_student.model.Question3Category


class InterestAdapter(var requireActivity: Context, var oninterest: OnInterestSelect) :
    RecyclerView.Adapter<InterestAdapter.MyViewHolder>() {
    var selectedposition = 0;
     var courselist: List<Question3Category>? = null
    var isClickable = true

    fun setList(courselist1: List<Question3Category?>?){
        this.courselist = courselist1 as List<Question3Category>?
        notifyDataSetChanged()
    }

    fun setClick(pos: Int,click : Boolean){

        courselist?.get(pos)?.check = click
     //   this.isClickable = click
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(requireActivity).inflate(R.layout.item_interest, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.txt_title.setText(courselist!![position].name)
        /*Glide.with(requireActivity).load(courselist!![position].image)
            .into(holder.imgv)*/

        holder.custom_layout.setOnClickListener {

            oninterest.onInterest(position, courselist!![position].check!!,
                courselist!![position].name!!
            )

        }
        if(courselist!![position].check == true){
            holder.custom_layout.setBackground(requireActivity.resources.getDrawable(R.drawable.bg_select_language));
           /* Glide.with(requireActivity).load(courselist!![position].image2)
                .into(holder.imgv)*/
            holder.txt_title.setTextColor(requireActivity.resources.getColor(R.color.white));

        }
        else{

            holder.custom_layout.setBackground(requireActivity.resources.getDrawable(R.drawable.bg_unselect_language));
            /*Glide.with(requireActivity).load(courselist!![position].image)
                .into(holder.imgv)*/
            holder.txt_title.setTextColor(requireActivity.resources.getColor(R.color.black));

        }

        /*if (position == selectedposition) {
            holder.txt_title.setTextColor(requireActivity.resources.getColor(R.color.white));
            holder.custom_layout.setBackground(requireActivity.resources.getDrawable(R.drawable.bg_select_language));
        } else {
            holder.txt_title.setTextColor(requireActivity.resources.getColor(R.color.white));
            holder.custom_layout.setBackground(requireActivity.resources.getDrawable(R.drawable.bg_language_white));

        }
        holder.custom_layout.setOnClickListener {
            if(isClickable == true) {
                onLanguage.onLangSelect(courselist!![position])
                selectedposition = position;
                notifyDataSetChanged()
            }
        }*/
    }

    override fun getItemCount(): Int {
        if(courselist != null) {
            return courselist!!.size
        }
        return 0
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var custom_layout: RelativeLayout
        var txt_title:TextView
       // var imgv:ImageView

        init {
            custom_layout = itemView.findViewById(R.id.item_custom_layout)
            txt_title = itemView.findViewById(R.id.txt_title)
           // imgv = itemView.findViewById(R.id.icn)

        }
    }

}