package com.mobiletracker.core.utils;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.mobiletracker.R;

public class DeviceController {

    static final int PLAY_SOUND_TAG = 100;
    static final int VIBRATE_TAG = 200;
    static final int GET_CURRENT_LOCATION_TAG = 300;
    static final int GET_SPECIFIC_CONTACT_TAG = 400;
    static final int ERASE_ALL_DATA_TAG = 600;


    static void ringDevice(@NonNull Context context) {
        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE);
        MediaPlayer mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.setOnPreparedListener(e -> mediaPlayer.start());
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }.start();
    }

    public static void sendRingCommand(@NonNull Context context, String phone) {
        sendSms(context, phone, "" + PLAY_SOUND_TAG);
    }

    static void vibrateDevice(@NonNull Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 200, 300, 400, 500, 1000};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0));
        } else {
            vibrator.vibrate(pattern, 0);
        }
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                vibrator.cancel();
            }
        }.start();
    }

    public static void vibrateDeviceCommand(@NonNull Context context, String phone) {
        sendSms(context, phone, "" + VIBRATE_TAG);
    }

    static void sendContactCommand(@NonNull Context context, String phone, String contactName) {
        sendSms(context, phone, GET_SPECIFIC_CONTACT_TAG + "|" + contactName);
    }

    static String findContactByName(@NonNull Context context, String contactName) {
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (name.toLowerCase().replace(" ", "").contains(contactName.toLowerCase().replace(" ", ""))) {
                return name + ": " + phoneNumber;
            }
        }
        phones.close();
        return "Contact not found!";
    }

    public static void sendLocationCommand(@NonNull Context context, String phone) {
        sendSms(context, phone, "" + GET_CURRENT_LOCATION_TAG);
    }

    public static String getDeviceLocation() {
        return "";
    }

    static void erasePhoneData(@NonNull Context context) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        devicePolicyManager.wipeData(0);
    }

    private static void sendSms(Context context, String phone, String body) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.confirmation))
                .setMessage(context.getString(R.string.sms_message_send))
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone, null, body, null, null);
                        Toast.makeText(context, context.getString(R.string.sms_sent), Toast.LENGTH_SHORT).show();
                    } catch (Exception ignored) {
                        Toast.makeText(context, context.getString(R.string.sms_not_sent), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

    }

    public static String getVcardString(Context mContext) {
        String path = null;
        String vfile = "contacts_file.vcf";
        Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, null);
        phones.moveToFirst();
        Log.i("Number of contacts", "cursorCount" + phones.getCount());
        for (int i = 0; i < phones.getCount(); i++) {
            String lookupKey = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
            AssetFileDescriptor fd;
            try {
                fd = mContext.getContentResolver().openAssetFileDescriptor(uri, "r");
                FileInputStream fis = fd.createInputStream();
                byte[] buf = new byte[(int) fd.getDeclaredLength()];
                fis.read(buf);
                String VCard = new String(buf);
                path = Environment.getExternalStorageDirectory().toString() + File.separator + vfile;
                FileOutputStream mFileOutputStream = new FileOutputStream(path, true);
                mFileOutputStream.write(VCard.getBytes());
                phones.moveToNext();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        //Read the file into a String
        return readFile(path);
    }

    private static String readFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
