package adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mcongress.mobicongress.com.myapplication.R;
import mcongress.mobicongress.com.myapplication.StarterApplication;
import model.Entidad1;


/**
 * Created by Alvaro on 2/21/15.
 */

//ADAPTADOR DE CELDAS MEETING
public class MeetingAppsListViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    StarterApplication myapp;
    String month;

    private List<Entidad1> meetingAppList = null;
    private ArrayList<Entidad1> arraylist;

    public MeetingAppsListViewAdapter(Context context,
                                      List<Entidad1> meetingAppList) {


        this.context = context;
        this.meetingAppList = meetingAppList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(meetingAppList);
        myapp = (StarterApplication) context.getApplicationContext();

    }

    public class ViewHolder {
        TextView name;

    }
    @Override
    public int getCount() {
        return meetingAppList.size();
    }

    @Override
    public Entidad1 getItem(int position) {
        return meetingAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.cell_meetingapp, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }



        holder.name.setText(meetingAppList.get(position).getTexto());

        /*
        if(Locale.getDefault().getLanguage().equals("en")){
            if(meetingAppList.get(position).getName2()!=null && !meetingAppList.get(position).getName2().isEmpty()){
                holder.name.setText(meetingAppList.get(position).getName2());
            }


        }

        else if(Locale.getDefault().getLanguage().equals("pt")){
            if(meetingAppList.get(position).getName3()!=null && !meetingAppList.get(position).getName3().isEmpty()){
                holder.name.setText(meetingAppList.get(position).getName3());
            }

        }
        else{

        }
*/
        //THIS LINE







        return view;
    }






}
