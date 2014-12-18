package com.app.musicplayer.Custom;

import android.app.FragmentManager;
import android.content.Context;

import android.graphics.Typeface;
import android.view.Gravity;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.musicplayer.Util.MediaFragment;
import com.app.musicplayer.R;
import com.app.musicplayer.UI.MyActivity;
import com.app.musicplayer.Custom.Objects.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward Onochie on 15/11/14.
 */
public class VideoListAdapter extends ArrayAdapter<Video> {
    private ArrayList<Video> videoList;
    private Context context;
    private Typeface openFont;
    public VideoListAdapter(Context context, int resource, List<Video> objects) {
        super(context, resource, objects);
        videoList = (ArrayList) objects;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Video item = getItem(position);
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.package_row, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.example_row_tv_title);
        textView.setText(item.videoTitle);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.thumbnail_image);
        imageView.setImageBitmap(item.thumbNail);
        openFont = Typeface.createFromAsset(getContext().getAssets(), "SourceSansPro-Light.otf");

        textView.setTypeface(openFont);
        textView.setGravity(Gravity.CENTER);
        int dps = 40;
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        textView.setHeight(pixels);
        textView.setPadding(0,0,0,0);
        convertView.setTag(position);
        Button playButton = (Button) convertView.findViewById(R.id.example_row_b_action_1);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((MyActivity) context).getFragmentManager();
                Bundle bundle = new Bundle();
                MediaFragment fragment = new MediaFragment();
                bundle.putString("video_id",item.videoID);
                ((MyActivity) context).playSong(view);
                fragment.setArguments(bundle);

                fragmentManager.beginTransaction().add(fragment,fragment.getTag()).commit();
            }
        });
        Button addButton = (Button) convertView.findViewById(R.id.example_row_b_action_3);
        addButton.setOnClickListener(new View.OnClickListener(){
            MyActivity myActivity = (MyActivity) getContext();
            @Override
            public void onClick(View v) {
                myActivity.showAddPopup(v,item.videoTitle, item.videoID);
            }
        });

        return convertView;
    }
}