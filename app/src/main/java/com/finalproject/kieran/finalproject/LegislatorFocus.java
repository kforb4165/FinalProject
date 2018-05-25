package com.finalproject.kieran.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class LegislatorFocus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislator_focus);

        Intent intent = getIntent();

        final TextView name = (TextView) findViewById(R.id.leg_name);
        final TextView birth = (TextView) findViewById(R.id.leg_dob);
        final TextView gender = (TextView) findViewById(R.id.leg_gender);
        final TextView link = (TextView) findViewById(R.id.leg_link);
        final TextView twitter = (TextView) findViewById(R.id.leg_twitter);
        final TextView facebook = (TextView) findViewById(R.id.leg_facebook);
        final TextView youtube = (TextView) findViewById(R.id.leg_youtube);
        final TextView inOffice = (TextView) findViewById(R.id.leg_in_office);
        final TextView currentParty = (TextView) findViewById(R.id.leg_current_party);
        final TextView mostRecentVote = (TextView) findViewById(R.id.leg_most_recent_vote);

        final ListView roles = (ListView) findViewById(R.id.legislator_roles);

        new AsyncTask<String, Void, Legislator>() {
            @Override
            protected Legislator doInBackground(String... id) {
                Legislator leg;
                JSONReader reader = new JSONReader.Builder()
                        .addType(JSONReader.TYPE.SINGLE_LEGISLATOR)
                        .addId(id[0])
                        .build();

                leg = reader.returnSingle(Legislator.class);

                return leg;
            }

            @Override
            protected void onPostExecute(Legislator leg) {
                name.setText("Name: " + leg.first_name + " " + leg.last_name);
                birth.setText("Date of birth: " + leg.date_of_birth);
                gender.setText("Gender: " + leg.gender);
                link.setText("Link to website: " + leg.url);
                twitter.setText("Twitter account: " + leg.twitter_account);
                facebook.setText("Facebook account: " + leg.facebook_account);
                youtube.setText("Youtube account: " + leg.youtube_account);
                inOffice.setText("Currently in office: " + leg.in_office);
                currentParty.setText("Current party: " + leg.current_party);
                mostRecentVote.setText("Most recent vote: " + leg.most_recent_vote);

                roles.setAdapter(new LegislatorRoleAdapter(getBaseContext(), leg.roleList));
            }
        }.execute(intent.getStringExtra("member_id"));
    }
}