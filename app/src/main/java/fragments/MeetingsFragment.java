package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.annotation.Nullable;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import adapters.MeetingAppsListViewAdapter;
import bolts.Continuation;
import bolts.Task;
import dataFetchers.LocalDataFetcher;
import mcongress.mobicongress.com.myapplication.R;
import model.Entidad1;


public class MeetingsFragment extends Fragment {



    MeetingAppsListViewAdapter adapter;
    public static List<Entidad1> meetingAppList;

    public static MeetingsFragment newInstance() {

        // Instantiate a new fragment

        MeetingsFragment fragment = new MeetingsFragment();


        fragment.setRetainInstance(true);
        return fragment;

    }


    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Restore State Here


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Log.i("CHAO", "rer");

        } else {
            Log.i("HOLA", "rer");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.meeting_listview, container, false);
        ListView listview = (ListView) RootView.findViewById(R.id.commonListView);
        fetchLocalData();
        adapter = new MeetingAppsListViewAdapter(getActivity(), meetingAppList);
        listview.setAdapter(adapter);

        return RootView;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();



        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener


                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onStop() {
        super.onStop();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("true", true);

    }

    public void fetchLocalData(){
        LocalDataFetcher localDataFetcher = new LocalDataFetcher();
        localDataFetcher.parseClass = "Entidad1";
        localDataFetcher.parseClassRelationsName.add("relacionE2Array");
        localDataFetcher.parseClassRelationsName.add("relacionE3Array");
        localDataFetcher.fetchDataFromLocal().continueWith(new Continuation<List<ParseObject>, Object>() {
            @Override
            public Object then(Task<List<ParseObject>> task) throws Exception {

                for(ParseObject object:task.getResult()){
                    meetingAppList.add((Entidad1)object);
                }

                return null;
            }
        });

    }


}