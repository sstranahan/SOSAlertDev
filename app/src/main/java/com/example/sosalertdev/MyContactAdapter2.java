package com.example.sosalertdev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyContactAdapter2 extends ArrayAdapter {

    private List contactsInfoList;
    private Context context;

    public <MyCustomAdapter> MyContactAdapter2(Context context, int resource, ArrayList<ContactsInfo> contactsInfoList) {
        super(context, resource, contactsInfoList);
        this.contactsInfoList = contactsInfoList;
        this.context = context;
    }

    private class ViewHolder {
        TextView displayName;
        TextView phoneNumber;
        CheckBox checkBox;
        TextView rmvText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.contact_info_2, null);

            holder = new ViewHolder();
            holder.displayName = (TextView) convertView.findViewById(R.id.displayName);
            holder.phoneNumber = (TextView) convertView.findViewById(R.id.phoneNumber);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.rmvText = (TextView) convertView.findViewById(R.id.rmvText);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ContactsActivity.contactsDispList.remove(contactsInfoList.get(position));
                }
            }
        });

        ContactsInfo contactsInfo = (ContactsInfo) contactsInfoList.get(position);
        holder.displayName.setText(contactsInfo.getDisplayName());
        holder.phoneNumber.setText(contactsInfo.getPhoneNumber());


        return convertView;
    }
}