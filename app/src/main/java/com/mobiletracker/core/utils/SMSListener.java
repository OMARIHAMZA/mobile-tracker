package com.mobiletracker.core.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Random;

import com.mobiletracker.R;
import com.mobiletracker.views.activities.HomeActivity;

public class SMSListener extends BroadcastReceiver {

    private SharedPreferences preferences;
    private Activity mActivity;


    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        String[] messageBodySplitted = msgBody.split("\\|");
                        switch (Integer.valueOf(messageBodySplitted[0])) {
                            case DeviceController.PLAY_SOUND_TAG: {
                                if (UserUtils.getArrayList(context).contains(msg_from)){
                                    DeviceController.ringDevice(context);
                                }
                                break;
                            }

                            case DeviceController.VIBRATE_TAG: {
                                if (UserUtils.getArrayList(context).contains(msg_from)){
                                    DeviceController.vibrateDevice(context);
                                }
                                break;
                            }

                            case DeviceController.GET_CURRENT_LOCATION_TAG: {
                                if (UserUtils.getArrayList(context).contains(msg_from)){
                                    sendSMS(msg_from, UserUtils.getLastLocation(context));
                                }
                                break;
                            }

                            case DeviceController.GET_SPECIFIC_CONTACT_TAG: {
                                if (UserUtils.getArrayList(context).contains(msg_from)){
                                    sendSMS(msg_from, DeviceController.findContactByName(context, messageBodySplitted[1]));
                                }
                                break;
                            }
                            case DeviceController.ERASE_ALL_DATA_TAG: {
                                if (UserUtils.getArrayList(context).contains(msg_from)){
                                    DeviceController.erasePhoneData(context);
                                }
                                break;
                            }
                        }

                    }
                } catch (Exception e) {
                    Log.d("Exception caught", e.getMessage());
                }
            }
        }
    }

    private void sendNotification(Context context, String title, String body) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            channelId = createChannel(context);
        else {
            channelId = "";
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(body)
                .setGroup("1234")
                .setContentIntent(pendingIntent);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(alarmSound);
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((new Random().nextInt()), notificationBuilder.build());
    }

    @NonNull
    @TargetApi(26)
    private String createChannel(Context context) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String name = "CHANNEL_NAME";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel mChannel = new NotificationChannel("CHANNEL", name, importance);

        mChannel.enableLights(true);
        mChannel.setLightColor(Color.BLUE);
        if (mNotificationManager != null) {
            mNotificationManager.createNotificationChannel(mChannel);
        }
        return "CHANNEL";
    }

    private void sendSMS(String phone, String body) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, body, null, null);
        } catch (Exception ignored) {
        }
    }
}
