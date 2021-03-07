package com.example.sosalertdev;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyListViewAdapter extends ArrayAdapter<NewsItem> implements View.OnClickListener {

    private ArrayList<NewsItem> dataSet;
    Context mContext;
    Intent browser;

    public MyListViewAdapter(@NonNull Context context, @NonNull ArrayList<NewsItem> data) {
        super(context, R.layout.my_text_view, data);
        this.dataSet = data;
        this.mContext=context;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView snippet;
        TextView firstParagraph;
        ImageView image;
        TextView url;
    }

//    public void CustomAdapter(ArrayList<NewsItem> data, Context context) {
//        super(context, R.layout.my_text_view, data);
//        this.dataSet = data;
//        this.mContext=context;
//
//    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        NewsItem newsItem=(NewsItem) object;

        switch (v.getId())
        {
            case R.id.snippet:
                Snackbar.make(v, "Article:  " +newsItem.getSnippet(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        NewsItem newsItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.my_text_view, parent, false);
            viewHolder.snippet = (TextView) convertView.findViewById(R.id.snippet);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image_id);
            viewHolder.firstParagraph = (TextView) convertView.findViewById(R.id.first_paragraph);
            viewHolder.url = (TextView) convertView.findViewById(R.id.url);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.snippet.setText(newsItem.getSnippet());
        viewHolder.firstParagraph.setText(newsItem.getLeadPar());
        Picasso.get().load(newsItem.getImgURL()).into(viewHolder.image);
        viewHolder.image.setOnClickListener(this);
        viewHolder.image.setTag(position);
        viewHolder.url.setText(newsItem.getUrl());

//        viewHolder.url.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                browser = new Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.getUrl()));
//                ClickURLActivity urlAct = new ClickURLActivity(browser, getContext());
//                urlAct.startActivity(browser);
//            }
//        });
        // Return the completed view to render on screen
        return convertView;
    }

//    class ClickURLActivity extends AppCompatActivity {
//        Intent intent;
//        Context context;
//
//        public ClickURLActivity(Intent intent, Context context) {
//            this.intent = intent;
//            this.context = context;
//        }
//
//        @Override
//        public void startActivity(Intent intent) {
//            super.startActivity(intent);
//        }
//    }

}



