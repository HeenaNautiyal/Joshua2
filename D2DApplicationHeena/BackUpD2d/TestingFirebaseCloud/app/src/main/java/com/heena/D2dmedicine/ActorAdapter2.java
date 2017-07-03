package com.heena.D2dmedicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heena.testingfirebasecloud.R;

import java.util.ArrayList;

/**
 * Created by Heena on 12/30/2016.
 */
public class ActorAdapter2 extends ArrayAdapter<Chemist> {
    ArrayList<Chemist> actorList;
    LayoutInflater vi;
    boolean[] checkBoxState;
    int Resource;
    ViewHolder holder;

    public ActorAdapter2(Context context, int resource, ArrayList<Chemist> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            //holder.chb = (CheckBox) v.findViewById(R.id.chkbox);
            holder.tvName = (TextView) v.findViewById(R.id.tvName);
            holder.tvDOB = (TextView) v.findViewById(R.id.tvmanufacture);
            holder.tvHeight = (TextView) v.findViewById(R.id.tvdescription);
            holder.addby=(TextView)v.findViewById(R.id.added);
            v.setTag(holder);
           /* holder.chb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox cb=(CheckBox)view;
                    Chemist actors=(Chemist)cb.getTag();
                    Log.e("main1", (String) cb.getText());
                    actors.isSelected(cb.isChecked());
                }
            });*/
        } else {
            holder = (ViewHolder) v.getTag();
        }
        Chemist actors = actorList.get(position);
        holder.tvName.setText(actorList.get(position).getMedicine());
        holder.tvDOB.setText("Manufactured By: " + actorList.get(position).getMenufacuter());
        holder.tvHeight.setText("Composition: "+actorList.get(position).getCompostion());
        holder.addby.setText("Description: "+actorList.get(position).getDescription());
       // holder.chb.setChecked(actors.isSelected());
        return v;
    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvName;
        public TextView tvDOB;
        public TextView addby;
        public TextView tvHeight;
        CheckBox chb;
    }



    public void updateAdapter(ArrayList<Chemist> actorList){
        this.actorList=actorList;
    }
}
