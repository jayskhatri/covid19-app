package com.jayskhatri.covid19_track.ui.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jayskhatri.covid19_track.R;
import com.jayskhatri.covid19_track.adapter.FAQAdapter;
import com.jayskhatri.covid19_track.adapter.StatewiseEntryAdapter;
import com.jayskhatri.covid19_track.api.HttpHandler;
import com.jayskhatri.covid19_track.object.FAQuestion;
import com.jayskhatri.covid19_track.object.StatewiseEntry;
import com.jayskhatri.covid19_track.ui.faqs.FAQsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class StatewiseFragment extends Fragment {

    private ArrayList<StatewiseEntry> entries;
    private RecyclerView stateentries;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private TextView noEntry;


    public StatewiseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statewise, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        stateentries = (RecyclerView) Objects.requireNonNull(getActivity()).findViewById(R.id.recycler_view_statewise_list);
        noEntry = (TextView) getActivity().findViewById(R.id.text_view_no_statewise_entry);
        entries = new ArrayList<StatewiseEntry>();

        mLayoutManager = new LinearLayoutManager(getActivity());
        stateentries.setLayoutManager(mLayoutManager);

        GetStateWiseData get = new GetStateWiseData(getActivity(), (AppCompatActivity) getActivity());
        get.execute();
    }

    public class GetStateWiseData extends AsyncTask<Void, Context, ArrayList<StatewiseEntry>> {
        final private String TAG = HomeFragment.GetStateWiseData.class.getSimpleName();

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
            if (list.size() > 0) {
                if (list.size() > 0) {
                    stateentries.setVisibility(View.VISIBLE);
                    mAdapter = new StatewiseEntryAdapter(list);
                    stateentries.setAdapter(mAdapter);
                    noEntry.setVisibility(View.GONE);
                } else {
                    stateentries.setVisibility(View.GONE);
                    noEntry.setVisibility(View.VISIBLE);
                }
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
}
