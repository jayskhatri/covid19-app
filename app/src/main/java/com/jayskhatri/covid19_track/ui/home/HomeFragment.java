package com.jayskhatri.covid19_track.ui.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.jayskhatri.covid19_track.R;
import com.jayskhatri.covid19_track.api.HttpHandler;
import com.jayskhatri.covid19_track.object.StatewiseEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private TextView totalConfirmedCount;
    private TextView totalRecoveredCount;
    private TextView totalActiveCount;
    private TextView totalDeathCount;
    private String TAG = HomeFragment.class.getSimpleName();
    private Button statewiseReport;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        totalActiveCount = (TextView) Objects.requireNonNull(getActivity()).findViewById(R.id.text_view_active_count);
        totalConfirmedCount = (TextView) getActivity().findViewById(R.id.text_view_confirmed_count);
        totalRecoveredCount = (TextView) getActivity().findViewById(R.id.text_view_recovered_count);
        totalDeathCount  = (TextView) getActivity().findViewById(R.id.text_view_deceased_count);
//        statewiseReport = (Button) getActivity().findViewById(R.id.btn_statewise_entries);


        GetStateWiseData obj = new GetStateWiseData(getActivity(), (AppCompatActivity) getActivity());
        obj.execute();
    }

    public class GetStateWiseData extends AsyncTask<Void, Context, ArrayList<StatewiseEntry>> {
        final private String TAG = GetStateWiseData.class.getSimpleName();

        private AlertDialog processDialog;
        private Context context;
        private AppCompatActivity activity;

        private String url = "https://api.covid19india.org/data.json";

        public GetStateWiseData(Context context, AppCompatActivity activity) {
            this.context = (Context) context;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            processDialog = createDialog(context);
            processDialog.show();
        }

        @Override
        protected ArrayList<StatewiseEntry> doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            ArrayList<StatewiseEntry> list = new ArrayList<StatewiseEntry>();

//        Log.e(TAG, "Response from url: " + jsonStr);

        /*
            {
                "active": "626",
                "confirmed": "681",
                "deaths": "12",
                "delta": {
                    "active": 24,
                    "confirmed": 24,
                    "deaths": 0,
                    "recovered": 0
                },
                "lastupdatedtime": "26/03/2020 13:41:24",
                "recovered": "43",
                "state": "Total"
            }
        */
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray stateentries = jsonObj.getJSONArray("statewise");
                    // looping through All Entries
                    for (int i = 0; i < stateentries.length(); i++) {
                        JSONObject c = stateentries.getJSONObject(i);

                        String active = c.getString("active");
                        String confirmed = c.getString("confirmed");
                        String deaths = c.getString("deaths");
                        String recovered = c.getString("recovered");
                        String state = c.getString("state");
                        String lastUpdatedTime = c.getString("lastupdatedtime");
                        Log.e(TAG, "active: " + active + "in state: " + state + "\n");
                        // Delta node is JSON Object
                        JSONObject delta = c.getJSONObject("delta");
                        String delta_active = delta.getString("active");
                        String delta_confirmed = delta.getString("confirmed");
                        String delta_recovered = delta.getString("recovered");
                        String delta_deaths = delta.getString("deaths");

                        StatewiseEntry entry = new StatewiseEntry(active, confirmed, deaths, recovered, state, lastUpdatedTime, delta_active, delta_confirmed, delta_recovered, delta_deaths);
                        list.add(entry);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<StatewiseEntry> list) {
            super.onPostExecute(list);
            // Dismiss the progress dialog
            if (processDialog.isShowing())
                processDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            if(list.size() > 0){
                totalActiveCount.setText(list.get(0).getActive());
                totalConfirmedCount.setText(list.get(0).getConfirmed());
                totalRecoveredCount.setText(list.get(0).getRecovered());
                totalDeathCount.setText(list.get(0).getDeaths());
            }
        }


        private AlertDialog createDialog(Context context){
            LayoutInflater factory = LayoutInflater.from(context);
            final View dialogView = factory.inflate(R.layout.custom_dialogue,null);
            final AlertDialog processDialog = new AlertDialog.Builder(context).create();
            processDialog.setView(dialogView);
            return processDialog;
        }
    }



    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}


