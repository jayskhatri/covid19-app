package com.jayskhatri.covid19_track.adapter;

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

        holder.state.setText(state);

        holder.active.setText(active);
        holder.confirmed.setText(confirmed);
        holder.recovered.setText(recovered);
        holder.deaths.setText(deaths);

        holder.dconfirmed.setText(delta_confirm);
        holder.dactive.setText(delta_active);
        holder.drecovered.setText(delta_recover);
        holder.ddeaths.setText(delta_death);
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
