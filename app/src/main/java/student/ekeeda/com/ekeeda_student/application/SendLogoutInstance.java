package student.ekeeda.com.ekeeda_student.application;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import student.ekeeda.com.ekeeda_student.model.LoginDataModel;
import student.ekeeda.com.ekeeda_student.networking.RetrofitBuild;
import student.ekeeda.com.ekeeda_student.util.PrefManager;


@SuppressLint("Registered")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SendLogoutInstance extends JobService {

    private PrefManager prefManager;
    private Call<LoginDataModel> successDetailsCall;

    @Override
    public void onCreate() {
        super.onCreate();
        prefManager = new PrefManager(getApplicationContext());
    }

    @Override
    public boolean onStartJob(JobParameters params) {


       /* if(!prefManager.getLastPurchaseAmount().equals("")) {
            MoEHelper.getInstance(getApplicationContext()).setUserAttribute("Last_Purchased_Amount", prefManager.getLastPurchaseAmount());
            prefManager.setLastPurchaseAmount("");
        }
        MoEHelper.getInstance(getApplicationContext()).setUserAttribute("Current_Amount_inCart", prefManager.getCurrentAmountCart());
        prefManager.setCurrentAmountCart(0);

        if(!prefManager.getLastOrderDate().equals("")) {
            MoEHelper.getInstance(getApplicationContext()).setUserAttribute("Last_Order_Date", prefManager.getLastOrderDate());
            prefManager.setLastOrderDate("");
        }
*/
        successDetailsCall = new RetrofitBuild().allApi().Logout(Integer.parseInt(prefManager.getUserid()),Integer.parseInt(prefManager.getUserhistoryid()));
        successDetailsCall.enqueue(new Callback<LoginDataModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginDataModel> call, @NonNull Response<LoginDataModel> response) {
                if (response.isSuccessful()) {
                   // SuccessDetails successDetails = response.body();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginDataModel> call, @NonNull Throwable t) {
            }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (successDetailsCall != null) {
            successDetailsCall.cancel();
        }
        return false;
    }



}
