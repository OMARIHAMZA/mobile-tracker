package omari.hamza.mobiletracker.views.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import omari.hamza.mobiletracker.R;
import omari.hamza.mobiletracker.core.models.Contact;
import omari.hamza.mobiletracker.views.activities.DeviceDetailsActivity;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.MyViewHolder> {

    private Activity mActivity;
    private ArrayList<Contact> contacts;

    public DevicesAdapter(Activity mActivity, ArrayList<Contact> contacts) {
        this.mActivity = mActivity;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.device_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Contact currentContact = contacts.get(i);

        myViewHolder.itemView.setOnClickListener(v -> {
            Intent mIntent = new Intent(mActivity, DeviceDetailsActivity.class);
            mIntent.putExtra("contact", currentContact);
            mActivity.startActivity(mIntent);
        });

        myViewHolder.infoButton.setOnClickListener(e -> {
            Intent mIntent = new Intent(mActivity, DeviceDetailsActivity.class);
            mIntent.putExtra("contact", currentContact);
            mActivity.startActivity(mIntent);
        });

        myViewHolder.phoneNumberTextView.setText(currentContact.getPhone());
        myViewHolder.phoneTypeTextView.setText(currentContact.getPhoneBrand() + " " + currentContact.getPhoneModel());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView phoneTypeTextView, phoneNumberTextView;
        private Button infoButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.phoneTypeTextView = itemView.findViewById(R.id.phone_model_textView);
            this.phoneNumberTextView = itemView.findViewById(R.id.phone_textView);
            this.infoButton = itemView.findViewById(R.id.info_button);
        }
    }
}
