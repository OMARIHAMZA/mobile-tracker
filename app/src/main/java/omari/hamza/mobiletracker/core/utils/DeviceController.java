package omari.hamza.mobiletracker.core.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DeviceController {

    public static final int PLAY_SOUND_TAG = 100;
    public static final int VIBRATE_TAG = 200;
    public static final int GET_CURRENT_LOCATION_TAG = 300;
    public static final int GET_SPECIFIC_CONTACT_TAG = 400;
    public static final int GET_ALL_CONTACTS_TAG = 500;
    public static final int ERASE_ALL_DATA_TAG = 600;


    public static void ringDevice(@NonNull Context context) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        MediaPlayer mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.setOnPreparedListener(e -> {
            mediaPlayer.start();
        });
    }

    public static void vibrateDevice(@NonNull Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 200, 300, 400, 500, 1000};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0));
        } else {
            vibrator.vibrate(pattern, 0);
        }
    }

    public static String findContactByName(@NonNull Context context, String contactName) {
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (contactName.toLowerCase().trim().equals(name.toLowerCase().trim())) {
                return phoneNumber;
            }
        }
        phones.close();
        return "null";
    }

    public static String getAllContacts(@NonNull Context context) {
        readContactsCSV(context);
        return "";
    }

    public static String getDeviceLocation() {
        return "";
    }

    public static void erasePhoneData() {

    }

    private static void readContactsCSV(@NonNull Context mContext) {
        String path = null;
        String vfile = null;

        vfile = "Contacts_.vcf";
        Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, null);

        phones.moveToFirst();
        Log.i("Number of contacts", "cursorCount" + phones.getCount());
        for (int i = 0; i < phones.getCount(); i++) {

            String lookupKey = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
            Log.i("lookupKey", " " + lookupKey);
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
        Log.i("TAG", "No Contacts in Your Phone");
    }

}
