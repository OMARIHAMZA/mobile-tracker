package omari.hamza.mobiletracker.core.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import omari.hamza.mobiletracker.R;
import omari.hamza.mobiletracker.controllers.UserController;
import omari.hamza.mobiletracker.core.models.MyResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetContactDialog {

    private Dialog mDialog;

    public GetContactDialog(Activity mActivity, String phone) {
        mDialog = new Dialog(mActivity);
        mDialog.setContentView(R.layout.device_connect_dialog);
        MaterialEditText contactNameEditText = mDialog.findViewById(R.id.phone_editText);
        contactNameEditText.setHint(mActivity.getString(R.string.contact_name));
        contactNameEditText.setHelperText("");
        contactNameEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        Button mButton = mDialog.findViewById(R.id.connect_device_button);
        mButton.setText(mActivity.getString(R.string.send));
        mButton.setOnClickListener(v -> {
            if (!contactNameEditText.getText().toString().isEmpty()){
                DeviceController.sendContactCommand(mActivity, phone, contactNameEditText.getText().toString());
                mDialog.dismiss();
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show() {
        mDialog.show();
    }

}
