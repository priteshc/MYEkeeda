package student.ekeeda.com.ekeeda_student.LoginSignup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import student.ekeeda.com.ekeeda_student.Interface.CallBackListener;
import student.ekeeda.com.ekeeda_student.R;

import student.ekeeda.com.ekeeda_student.model.Item;
import student.ekeeda.com.ekeeda_student.model.StateModel;

import static android.app.Activity.RESULT_OK;

public class AllClgListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
private Context context;
private List<Item>collegeModels;
private CallBackListener dataCallBack;

    public AllClgListAdapter(Context c, CallBackListener dataCallBack1) {
        context =c;
        this.dataCallBack = dataCallBack1;

    }

    public void setList(List<Item> collegeModels1){
        this.collegeModels = collegeModels1;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dropdwn, viewGroup, false);
        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final  int i) {

        ((MovieHolder)viewHolder).bindData(collegeModels.get(i),context);
        viewHolder.itemView.setClickable(true);
        viewHolder.itemView.setOnClickListener(view -> {

            dataCallBack.onCallBack(collegeModels.get(i).getStateId(),
                                    collegeModels.get(i).getStateName());



        });

    }

    @Override
    public int getItemCount() {
        return collegeModels.size();
    }


    static class MovieHolder extends RecyclerView.ViewHolder{
        TextView clgname;

        public MovieHolder(View itemView) {
            super(itemView);

            clgname = itemView.findViewById(R.id.edit);


        }

        void bindData(Item movieModel,Context context){

            clgname.setText(movieModel.getStateName());

        }


    }

}
