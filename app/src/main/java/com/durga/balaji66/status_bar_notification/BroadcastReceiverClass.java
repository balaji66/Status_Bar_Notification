package com.durga.balaji66.status_bar_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.durga.balaji66.status_bar_notification.Adpaters.CandidateListAdapter;
import com.durga.balaji66.status_bar_notification.Apis.APIUrl;
import com.durga.balaji66.status_bar_notification.Models.CandidateListModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BroadcastReceiverClass extends BroadcastReceiver {
    Context context;
    private static int mArrayListSize =0;
    private static int mCount=0;
    private List<CandidateListModel> mList  =new ArrayList<>();

    @Override
    public void onReceive(final Context context, Intent intent) {

        this.context=context;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        Toast.makeText(context,"checking connection",Toast.LENGTH_LONG).show();

        //if there is a network
        if (activeNetwork != null) {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //if connected to wifi or mobile data plan
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        Toast.makeText(context,"connected",Toast.LENGTH_LONG).show();
                        getCandidateList();
                    }
                }
            }, 2000);

        }
    }

    public void getCandidateList()
    {
//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Signing Up...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        Call<ResponseCategory> call = APIUrl.getmInstance().getApi().CandidateList();

        call.enqueue(new Callback<ResponseCategory>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCategory> call, @NonNull Response<ResponseCategory> response) {

                if(response.code() == 200)
                {
                    //progressDialog.dismiss();
                    try {
                        // Here fetching date and storing in arrayList.
                        mList = new ArrayList<CandidateListModel>(Objects.requireNonNull(response.body()).getCategories());
                        if(mCount ==0)
                        {
                            mArrayListSize=mList.size();
                        }
                        if(mList.size() > mArrayListSize)
                        {
                            mArrayListSize =mList.size();
                            displayNotification();
                        }
                        mCount +=1;
                    } catch (Exception e) {
                        System.out.println("hai" + e);
                    }

                }
                else if( response.code() == 401)
                {
                    //progressDialog.dismiss();
                    Toast.makeText(context,"Invalid User Id or password",Toast.LENGTH_LONG).show();
                }
                else
                {
                    //progressDialog.dismiss();
                    Toast.makeText(context,"Please Check After Some Time",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCategory> call, @NonNull Throwable t) {
                //progressDialog.dismiss();

            }
        });
    }

    public void displayNotification()
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);


        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true)
                .setTicker("Hearty365")
                //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Aptitude Candidate")
                .setContentText("New Candidate Registered")
                .setContentInfo("Info");

        //This for opening new activity
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);


        notificationManager.notify(/*notification id*/1, notificationBuilder.build());

    }
}
