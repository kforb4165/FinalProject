package com.finalproject.kieran.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class LegislatorRoleAdapter extends ArrayAdapter<Legislator.Role> {
    private static class ViewHolder {
        TextView congressNumber, chamber, title, state, party, leadershipRole, fecId, seniority,
                districtNum, atLarge, startDate, endDate, office, phone, fax, contactForm,
                sponsoredBills, cosponsoredBills, missedVotes, votesWithParty, committees,
                subcommittees;
    }

    public LegislatorRoleAdapter(Context context, ArrayList<Legislator.Role> items) {
        super(context, R.layout.legislator_role_list_item, items);
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        Legislator.Role role = getItem(position);
        ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.legislator_role_list_item, parent, false);
            holder.congressNumber = (TextView) convertView.findViewById(R.id.role_congress_number);
            holder.chamber = (TextView) convertView.findViewById(R.id.role_chamber);
            holder.title = (TextView) convertView.findViewById(R.id.role_title);
            holder.state = (TextView) convertView.findViewById(R.id.role_state);
            holder.party = (TextView) convertView.findViewById(R.id.role_party);
            holder.leadershipRole = (TextView) convertView.findViewById(R.id.role_leadership_role);
            holder.fecId = (TextView) convertView.findViewById(R.id.role_fec_id);
            holder.seniority = (TextView) convertView.findViewById(R.id.role_seniority);
            holder.districtNum = (TextView) convertView.findViewById(R.id.role_district_num);
            holder.atLarge = (TextView) convertView.findViewById(R.id.role_at_large);
            holder.startDate = (TextView) convertView.findViewById(R.id.role_start_date);
            holder.endDate = (TextView) convertView.findViewById(R.id.role_end_date);
            holder.office = (TextView) convertView.findViewById(R.id.role_office);
            holder.phone = (TextView) convertView.findViewById(R.id.role_phone);
            holder.fax = (TextView) convertView.findViewById(R.id.role_fax);
            holder.contactForm = (TextView) convertView.findViewById(R.id.role_contact_form);
            holder.sponsoredBills = (TextView) convertView.findViewById(R.id.role_bills_sponsored);
            holder.cosponsoredBills = (TextView) convertView.findViewById(R.id.role_bills_cosponsored);
            holder.missedVotes = (TextView) convertView.findViewById(R.id.role_missed_votes_pct);
            holder.votesWithParty = (TextView) convertView.findViewById(R.id.role_votes_with_party_pct);
            holder.committees = (TextView) convertView.findViewById(R.id.role_committees);
            holder.subcommittees = (TextView) convertView.findViewById(R.id.role_subcommittees);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.congressNumber.setText("Congress number: " + role.congress);
        holder.chamber.setText("Chamber: " + role.chamber);
        holder.title.setText("Title: " + role.title);
        holder.state.setText("State: " + role.state);
        holder.party.setText("Party: " + role.party);

        if(role.leadership_role != null && !role.leadership_role.equals("") && !role.leadership_role.equals("null")) {
            holder.leadershipRole.setText("Leadership role: " + role.leadership_role);
        } else {
            holder.leadershipRole.setVisibility(View.GONE);
        }

        holder.fecId.setText("FEC ID: " + role.fec_candidate_id);
        holder.seniority.setText("Seniority: " + role.seniority);
        holder.districtNum.setText("District number: " + role.district);
        holder.atLarge.setText("At large: " + role.at_large);
        holder.startDate.setText("Start date: " + role.start_date);
        holder.endDate.setText("End date: " + role.end_date);

        if(role.office != null && !role.office.equals("") && !role.office.equals("null")) {
            holder.office.setText("Office: " + role.office);
        } else {
            holder.office.setVisibility(View.GONE);
        }

        if(role.phone != null && !role.phone.equals("") && !role.phone.equals("null")) {
            holder.phone.setText("Phone: " + role.phone);
        } else {
            holder.phone.setVisibility(View.GONE);
        }

        if(role.fax != null && !role.fax.equals("") && !role.fax.equals("null")) {
            holder.fax.setText("Fax: " + role.fax);
        } else {
            holder.fax.setVisibility(View.GONE);
        }

        if(role.contact_form != null && !role.contact_form.equals("") && !role.contact_form.equals("null")) {
            holder.contactForm.setText("Contact form: " + role.contact_form);
        } else {
            holder.contactForm.setVisibility(View.GONE);
        }

        if(role.bills_sponsored >= 0) {
            holder.sponsoredBills.setText("Number of sponsored bills: " + role.bills_sponsored);
        } else {
            holder.sponsoredBills.setVisibility(View.GONE);
        }

        if(role.bills_cosponsored >= 0) {
            holder.cosponsoredBills.setText("Number of cosponsored bills: " + role.bills_cosponsored);
        } else {
            holder.cosponsoredBills.setVisibility(View.GONE);
        }

        if(role.missed_votes_pct >= 0) {
            holder.missedVotes.setText("Missed votes (%): " + role.missed_votes_pct);
        } else {
            holder.missedVotes.setVisibility(View.GONE);
        }

        if(role.votes_with_party_pct >= 0) {
            holder.votesWithParty.setText("Votes with party (%): " + role.votes_with_party_pct);
        } else {
            holder.votesWithParty.setVisibility(View.GONE);
        }

        if(role.committees != null && role.committees.size() > 0) {
            String committees = "Committees: ";
            for (int i = 0; i < role.committees.size(); i++) {
                committees += role.committees.get(i);
                if(i < role.committees.size() - 1) {
                    committees += ", ";
                }
            }

            holder.committees.setText(committees);
        } else {
            holder.committees.setVisibility(View.GONE);
        }

        if(role.subcommittees != null && role.subcommittees.size() > 0) {
            String subcommittees = "Subcommittees: ";
            for (int i = 0; i < role.subcommittees.size(); i++) {
                subcommittees += role.subcommittees.get(i);
                if(i < role.subcommittees.size() - 1) {
                    subcommittees += ", ";
                }
            }

            holder.subcommittees.setText(subcommittees);
        } else {
            holder.subcommittees.setVisibility(View.GONE);
        }

        return convertView;
    }
}
