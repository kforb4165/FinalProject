package com.finalproject.kieran.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView senHeader;
    private ListView statesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        senHeader = (TextView) findViewById(R.id.message);
        statesList = (ListView) findViewById(R.id.states_list);

        String[] stateNames = getResources().getStringArray(R.array.state_names);
        final String[] stateAbbrs = getResources().getStringArray(R.array.states);

        statesList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stateNames));

        statesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AsyncTask<String, Void, ArrayList<Legislator>>() {
                    @Override
                    protected ArrayList<Legislator> doInBackground(String... state) {
                        ArrayList<Legislator> stateLegislators = new ArrayList<>();
                        JSONReader reader = new JSONReader.Builder()
                                .addType(JSONReader.TYPE.CHAMBER_STATE_LEGISLATORS)
                                .addState(state[0])
                                .build();

                        try {
                            stateLegislators = reader.getAllStateLegislators();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }

                        return stateLegislators;
                    }

                    @Override
                    protected void onPostExecute(final ArrayList<Legislator> legislators) {
                        senHeader.setText("State legislators");
                        statesList.setAdapter(new LegislatorAdapter(getBaseContext(), legislators));

                        statesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getBaseContext(), LegislatorFocus.class);
                                intent.putExtra("member_id", legislators.get(position).member_id);
                                startActivity(intent);
                            }
                        });
                    }
                }.execute(stateAbbrs[position]);
            }
        });
    }

}
