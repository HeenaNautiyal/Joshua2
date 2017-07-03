package com.example.administrator.testing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 8/31/2016.
 */
public class ActorAdapter extends ArrayAdapter<Actors>{

    ArrayList<Actors> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    boolean[] checkBoxState;
    private SparseBooleanArray checkedItemPositions;

    public ActorAdapter(Context context, int row, ArrayList<Actors> object) {
        super(context,row,object);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = row;
        actorList = object;

    }

    public void updateAdapter(ArrayList<Actors> actorsList) {
        this.actorList=actorsList;
    }

    public void selectAll() {
        holder.chb.setChecked(true);
    }

    private class ViewHolder {
        public TextView tvName;
        public TextView tvDOB;
        CheckBox chb;
        public TextView tvCountry;
        public TextView tvHeight;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.tvDOB = (TextView) v.findViewById(R.id.tvDateOfBirth);
            holder.chb = (CheckBox) v.findViewById(R.id.checkBox1);
            v.setTag(holder);
            holder.chb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox cb=(CheckBox)view;
                    Actors actors=(Actors)cb.getTag();
                    Log.e("main1", (String) cb.getText());
                    actors.isSelected(cb.isChecked());


                }
            });
        } else {
            holder = (ViewHolder) v.getTag();
        }

        //  holder.tvDOB.setText( actorList.get(position).getDob());
        Actors actors = actorList.get(position);
        holder.tvDOB.setText(actors.getDob());
        holder.chb.setChecked(actors.isSelected());
        holder.chb.setTag(actors);

        return v;

    }
}