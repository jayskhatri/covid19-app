package com.imperfect.covid19_track.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.imperfect.covid19_track.R;
import com.imperfect.covid19_track.SelfTestActivity;
import com.imperfect.covid19_track.api.HttpHandler;
import com.imperfect.covid19_track.object.StatewiseEntry;
import com.imperfect.covid19_track.utils.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private TextView totalConfirmedCount, delta_confirm;
    private TextView totalRecoveredCount, delta_recover;
    private TextView totalActiveCount, delta_active;
    private TextView totalDeathCount, delta_deaths;
    private TextView hourView;
    private LinearLayout countView;
    private TextView noInternet;
    private String TAG = HomeFragment.class.getSimpleName();
    private Button symptomchecker;
    private ConnectionDetector connectionDetector;
    private ConstraintLayout constraintLayout;

    public HomeFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!connectionDetector.isInternetAvailble()){
            //generating dialog
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(R.id.btn_symptom_checker, ConstraintSet.TOP,R.id.text_view_no_internet,ConstraintSet.BOTTOM,0);
            constraintSet.applyTo(constraintLayout);
            countView.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);

            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Please turn your Internet on or connect to WIFI to statistics.")
                    .setTitle("No Internet Connection");

            builder.setNeutralButton("OK", (dialogInterface, i) -> {

            });

            // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(dialogInterface -> {
                dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.colorPrimary));
            });
            dialog.show();
        }
        else {
            GetKeyValues obj = new GetKeyValues(getActivity(), (AppCompatActivity) getActivity());
            obj.execute();
            countView.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.GONE);
        }
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
        totalActiveCount = (TextView) Objects.requireNonNull(getActivity()).findViewById(R.id.text_view_active_count);
        totalConfirmedCount = (TextView) getActivity().findViewById(R.id.text_view_confirmed_count);
        totalRecoveredCount = (TextView) getActivity().findViewById(R.id.text_view_recovered_count);
        totalDeathCount  = (TextView) getActivity().findViewById(R.id.text_view_deceased_count);
        connectionDetector = new ConnectionDetector(getActivity());
        countView = getActivity().findViewById(R.id.linear_layout_counts_view);
        noInternet = getActivity().findViewById(R.id.text_view_no_internet);
        constraintLayout = getActivity().findViewById(R.id.parent_constraint);

        hourView = (TextView) getActivity().findViewById(R.id.text_view_hour);

        delta_active = (TextView) getActivity().findViewById(R.id.text_view_dactive);
        delta_recover =  (TextView) getActivity().findViewById(R.id.text_view_drecovered);
        delta_confirm = (TextView) getActivity().findViewById(R.id.text_view_dconfirmed);
        delta_deaths = (TextView) getActivity().findViewById(R.id.text_view_ddeaths);

        symptomchecker = (Button) getActivity().findViewById(R.id.btn_symptom_checker);
        symptomchecker.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), SelfTestActivity.class);
            startActivity(i);
        });
    }

    public class GetKeyValues extends AsyncTask<Void, Context, ArrayList<StatewiseEntry>> {
        final private String TAG = GetKeyValues.class.getSimpleName();

        private AlertDialog processDialog;
        private Context context;
        private AppCompatActivity activity;

        private String url = "https://api.covid19india.org/data.json";

        public GetKeyValues(Context context, AppCompatActivity activity) {
            this.context = (Context) context;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            countView.setVisibility(View.INVISIBLE);
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
                    String active, confirmed, deaths, recovered, state, lastUpdatedTime; //delta_active, delta_confirmed, delta_recovered, delta_deaths;
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray stateentries = jsonObj.getJSONArray("statewise");
                    // looping through All Entries
                    for (int i = 0; i < stateentries.length(); i++) {
                        JSONObject c = stateentries.getJSONObject(i);

                        deaths = c.getString("deaths");
                        active = c.getString("active");
                        confirmed = c.getString("confirmed");
                        recovered = c.getString("recovered");
                        state = c.getString("state");
                        lastUpdatedTime = c.getString("lastupdatedtime");
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
            countView.setVisibility(View.VISIBLE);
            Log.e(TAG, "in post execute");
            if(list.size() > 0){
                totalActiveCount.setText(list.get(0).getActive());
                totalConfirmedCount.setText(list.get(0).getConfirmed());
                totalRecoveredCount.setText(list.get(0).getRecovered());
                totalDeathCount.setText(list.get(0).getDeaths());

                delta_active.setText(list.get(0).getDelta_active());
                delta_confirm.setText(list.get(0).getDelta_confirmed());
                delta_recover.setText(list.get(0).getDelta_recovered());
                delta_deaths.setText(list.get(0).getDelta_deceased());

                try {
                    hourView.setText(getTimeDifference(list.get(0).getLastUpdatedTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    hourView.setVisibility(View.INVISIBLE);
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

    private String getTimeDifference(String date) throws ParseException {

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date1 = format.parse(date);
            Date current = Calendar.getInstance().getTime();
            Log.e(TAG, "date1: " +date1.toString()+" date2: "+current.toString());
            long mills = current.getTime() - date1.getTime();
            Log.v("Data1", ""+date1.getTime());
            Log.v("Data2", ""+current.getTime());
            int hours = (int) (mills/(1000 * 60 * 60));
            int mins = (int) (mills/(1000*60)) % 60;

            Log.e(TAG, "hours: "+hours + "  mins: "+mins);
            if (hours==0) {
                return mins + " minutes ago";
            } else {
                return hours + " hours ago";
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


