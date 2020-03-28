package com.jayskhatri.covid19_track.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jayskhatri.covid19_track.R;
import com.jayskhatri.covid19_track.object.StatewiseEntry;

import java.util.List;

public class StatewiseEntryAdapter extends RecyclerView.Adapter<StatewiseEntryAdapter.MyViewHolder> {
    private String TAG = StatewiseEntryAdapter.class.getSimpleName();
    private List<StatewiseEntry> entries;

    @NonNull
    @Override
    public StatewiseEntryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_state_entry, parent, false);

        return new StatewiseEntryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatewiseEntryAdapter.MyViewHolder holder, int position) {
        String state = entries.get(position).getState();
        String confirmed = entries.get(position).getConfirmed();
        String active = entries.get(position).getActive();
        String recovered = entries.get(position).getRecovered();
        String deaths = entries.get(position).getDeaths();
        String delta_active = entries.get(position).getDelta_active();
        String delta_confirm = entries.get(position).getDelta_confirmed();
        String delta_recover = entries.get(position).getDelta_recovered();
        String delta_death = entries.get(position).getDelta_deceased();

        Log.e(TAG, "Namaste from state : "+state);
//
        holder.state.setText(state);

        holder.active.setText(active);
        holder.confirmed.setText(confirmed);
        holder.recovered.setText(recovered);
        holder.deaths.setText(deaths);

        if(delta_confirm.equals("+0")){
            Log.e(TAG, "VISIBILITY set to 0 for confirm in state "+state);
            holder.dconfirmed.setVisibility(View.GONE);
        }else {
            Log.e(TAG, "Delta value for confirm in state "+state + " delta: " + delta_confirm);
            holder.dconfirmed.setText(delta_confirm);
        }
        if(delta_active.equals("+0")){
            holder.dactive.setVisibility(View.GONE);
        }else {
            holder.dactive.setText(delta_active);
        }

        if(delta_recover.equals("+0")){
            holder.drecovered.setVisibility(View.GONE);
        }else {
            holder.drecovered.setText(delta_recover);
        }

        if(delta_death.equals("+0")){
            holder.ddeaths.setVisibility(View.GONE);
        }else {
            holder.ddeaths.setText(delta_death);
        }
    }
    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView state, confirmed, active, recovered, deaths, dconfirmed, dactive, drecovered, ddeaths;

        public MyViewHolder(View view) {
            super(view);
            state = view.findViewById(R.id.text_view_state_name);
            confirmed = view.findViewById(R.id.text_view_state_confirmed);
            active = view.findViewById(R.id.text_view_state_active);
            recovered = view.findViewById(R.id.text_view_state_recovered);
            deaths = view.findViewById(R.id.text_view_state_deceased);

            dconfirmed = view.findViewById(R.id.text_view_state_dconfirmed);
            dactive = view.findViewById(R.id.text_view_state_dactive);
            drecovered  = view.findViewById(R.id.text_view_state_drecovered);
            ddeaths = view.findViewById(R.id.text_view_state_ddeaths);
        }
    }
    public StatewiseEntryAdapter(List<StatewiseEntry> entries) {
        this.entries = entries;
    }

}
