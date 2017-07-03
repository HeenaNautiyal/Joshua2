package com.heena.testingfirebasecloud;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heena.testingfirebasecloud.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Heena on 1/18/2017.
 */
public class TestAdapter extends ArrayAdapter<Actors> {
    ArrayList<Actors> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public TestAdapter(Context context, int resource, ArrayList<Actors> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);

            holder.tvName = (TextView) v.findViewById(R.id.line1);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.tvName.setText(actorList.get(position).getName());
        return v;

    }

    static class ViewHolder {
       public TextView tvName;
         }

    public void updateAdapter(ArrayList<Actors> actorList){
        this.actorList=actorList;
    }
}
