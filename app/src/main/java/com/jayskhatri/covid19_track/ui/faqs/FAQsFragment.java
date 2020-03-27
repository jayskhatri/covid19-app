package com.jayskhatri.covid19_track.ui.faqs;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jayskhatri.covid19_track.R;
import com.jayskhatri.covid19_track.adapter.FAQAdapter;
import com.jayskhatri.covid19_track.api.HttpHandler;
import com.jayskhatri.covid19_track.object.FAQuestion;
import com.jayskhatri.covid19_track.object.StatewiseEntry;
import com.jayskhatri.covid19_track.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FAQsFragment extends Fragment {

    private String TAG = FAQsFragment.class.getSimpleName();
    private ArrayList<FAQuestion> faqs;
    private RecyclerView faqList;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private TextView noFAQs;

    public void setFaqs(ArrayList<FAQuestion> faqs) {
        this.faqs = faqs;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.fab).setVisibility(View.INVISIBLE);
        return inflater.inflate(R.layout.fragment_faqs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        faqList = (RecyclerView) Objects.requireNonNull(getActivity()).findViewById(R.id.recycler_view_faq_list);
        noFAQs = (TextView) getActivity().findViewById(R.id.text_view_no_faqs);
        faqs = new ArrayList<FAQuestion>();

        mLayoutManager = new LinearLayoutManager(getActivity());
        faqList.setLayoutManager(mLayoutManager);


        faqs.add(new FAQuestion("1", "Are you official?", "No"));
        faqs.add(new FAQuestion("2", "Then who are you?", "We are a group of dedicated volunteers who want to give update regarding COVID-19 cases in India at your fingertips."));
        faqs.add(new FAQuestion("3", "How is the data gathered for this project?", "We are taking data from covid19india.org tracker, and they collect the details from state press releases, official government links and reputable news channels as source." +
                " Data is validated by a group of volunteers and published into a Google sheet and an API, which is available for all."));
//        GetFAQs getFAQs = new GetFAQs(getActivity(), (AppCompatActivity) getActivity());
//        getFAQs.execute();
        Log.e(TAG, "faqs: "+faqs.size());

        if(faqs.size() > 0){
            if(faqs.size()>0) {
                faqList.setVisibility(View.VISIBLE);
                Log.e(TAG, "faq 1: "+faqs.get(1).getQuestion());
                mAdapter = new FAQAdapter(faqs);
                faqList.setAdapter(mAdapter);
                noFAQs.setVisibility(View.GONE);
            }else{
                faqList.setVisibility(View.GONE);
                noFAQs.setVisibility(View.VISIBLE);
            }
        }
    }

    public class GetFAQs extends AsyncTask<Void, Context, ArrayList<FAQuestion>> {
        final private String TAG = HomeFragment.GetStateWiseData.class.getSimpleName();

        private AlertDialog processDialog;
        private Context context;
        private AppCompatActivity activity;

        private String url = "https://api.covid19india.org/faq.json";

        public GetFAQs(Context context, AppCompatActivity activity) {
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
        protected ArrayList<FAQuestion> doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

//            ArrayList<FAQuestion> list = new ArrayList<FAQuestion>();

//        Log.e(TAG, "Response from url: " + jsonStr);

        /*
            {
                "answer": "No",
                "qno": "1",
                "question": "Are you official?"
		    }
        */
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    Log.e(TAG, "jsonStr: "+jsonStr);
                    // Getting JSON Array node
                    JSONArray allfaq = jsonObj.getJSONArray("faq");
                    // looping through All Entries
                    for (int i = 0; i < allfaq.length(); i++) {
                        JSONObject c = allfaq.getJSONObject(i);

                        String answer = c.getString("answer");
                        String qno = c.getString("qno");
                        String question = c.getString("question");
                        Log.e(TAG, "qno: "+qno);
                        FAQuestion que = new FAQuestion(qno, question, answer );
                        faqs.add(que);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return faqs;
        }

        @Override
        protected void onPostExecute(ArrayList<FAQuestion> list) {
            super.onPostExecute(list);
            // Dismiss the progress dialog
            if (processDialog.isShowing())
                processDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            Log.e(TAG, "list size in post execute: "+ list.size());
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
