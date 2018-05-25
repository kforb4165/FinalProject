package com.finalproject.kieran.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LegislatorAdapter extends ArrayAdapter<Legislator> {
    private static class ViewHolder {
        TextView name, party, role, gender, election;
    }

    public LegislatorAdapter(Context context, ArrayList<Legislator> items) {
        super(context, R.layout.legislator_list_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Legislator leg = getItem(position);
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.legislator_list_item, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.name_text);
            holder.party = (TextView) convertView.findViewById(R.id.party_text);
            holder.role = (TextView) convertView.findViewById(R.id.role_text);
            holder.gender = (TextView) convertView.findViewById(R.id.gender_text);
            holder.election = (TextView) convertView.findViewById(R.id.next_election);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(getContext().getResources().getString(R.string.leg_name, leg.last_name + ", " + leg.first_name));
        holder.party.setText(getContext().getResources().getString(R.string.leg_party, leg.party_label));
        holder.role.setText(getContext().getResources().getString(R.string.leg_role, leg.role));
        holder.gender.setText(getContext().getResources().getString(R.string.leg_gender, leg.gender));
        holder.election.setText(getContext().getResources().getString(R.string.leg_election, leg.election));
        return convertView;
    }
}
