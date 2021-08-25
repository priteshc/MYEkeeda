package student.ekeeda.com.ekeeda_student.application;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import student.ekeeda.com.ekeeda_student.model.LoginDataModel;
import student.ekeeda.com.ekeeda_student.networking.RetrofitBuild;
import student.ekeeda.com.ekeeda_student.util.PrefManager;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SendLoginInstance extends JobService {

    private PrefManager prefManager;
    private Call<LoginDataModel> successDetailsCall;

    @Override
    public void onCreate() {
        super.onCreate();
        prefManager = new PrefManager(getApplicationContext());
    }

    @Override
    public boolean onStartJob(JobParameters params) {

      //  Log.d("Time", getCurrntTime());

        successDetailsCall = new RetrofitBuild().allApi().Login(prefManager.getUsername(),prefManager.getUserpassword(),prefManager.getUserip()
        ,prefManager.getUsermac(),"Android");
        successDetailsCall.enqueue(new Callback<LoginDataModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginDataModel> call, @NonNull Response<LoginDataModel> response) {
                if (response.isSuccessful()) {
                    LoginDataModel successDetails = response.body();
                      prefManager.setUserhistoryid(String.valueOf(successDetails.getLoginHistoryid()));

                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginDataModel> call, @NonNull Throwable t) {
                Log.w("Response error", Objects.requireNonNull(t.getMessage()));
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
