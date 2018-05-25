package com.finalproject.kieran.finalproject;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.finalproject.kieran.finalproject.JSONReader.TYPE.CHAMBER_STATE_LEGISLATORS;

public class JSONReader {
    private static final String TAG = "JSONReader";

    public enum TYPE {
        SINGLE_LEGISLATOR, MULTIPLE_LEGISLATORS, STATE_LEGISLATORS, CHAMBER_STATE_LEGISLATORS,
            CHAMBER_NATIONAL_LEGISLATORS, NEW_LEGISLATORS, DISTRICT_LEGISLATOR, LEAVING_LEGISLATORS;
    }

    private TYPE type;

    private String url, chamber, state, id;
    private int district, congressNumber;
    private boolean current, used;

    public JSONReader(TYPE type, String chamber, String state, String id, int district, int congressNumber, boolean current) {
        this.type = type;

        if(chamber != null && !chamber.equals("")) {
            this.chamber = chamber;
        } else {
            this.chamber = "house";
        }

        if(state != null && !state.equals("")) {
            this.state = state;
        } else {
            this.state = "NC";
        }

        if(id != null && !id.equals("")) {
            this.id = id;
        } else {
            this.id = "";
        }

        if(district != 0) {
            this.district = district;
        } else {
            district = -1;
        }

        if(congressNumber != 0) {
            this.congressNumber = congressNumber;
        } else {
            congressNumber = -1;
        }

        this.current = current;

        switch(type) {
            case SINGLE_LEGISLATOR:
                // add in the member id
                url = String.format("https://api.propublica.org/congress/v1/members/%s.json", id);
                break;
            case STATE_LEGISLATORS:
            case CHAMBER_STATE_LEGISLATORS:
                // supply chamber for 1s and state for 2s
                url = String.format("https://api.propublica.org/congress/v1/members/%1s/%2s/current.json", chamber, state);
                break;
            case CHAMBER_NATIONAL_LEGISLATORS:
                // supply congress number and chamber
                url = String.format("https://api.propublica.org/congress/v1/%d/%s/members.json", congressNumber, chamber);
                break;
            case NEW_LEGISLATORS:
                url = "https://api.propublica.org/congress/v1/members/new.json";
                break;
            case DISTRICT_LEGISLATOR:
                // requires state and district
                url = "https://api.propublica.org/congress/v1/members/house/%s/%d/current.json";
                break;
            case LEAVING_LEGISLATORS:
                // requires congress number and chamber
                url = "https://api.propublica.org/congress/v1/%d/%s/members/leaving.json";
                break;
            default:
                // requires member id
                url = String.format("https://api.propublica.org/congress/v1/members/%s.json", id);
                break;
        }
    }

    static class Builder {
        public Builder() {}

        TYPE type;
        String chamber, state, id;
        int district, congressNumber;
        boolean current;

        public Builder addType(TYPE type) {
            this.type = type;
            return this;
        }

        public Builder addChamber(String chamber) {
            this.chamber = chamber;
            return this;
        }

        public Builder addState(String state) {
            this.state = state;
            return this;
        }

        public Builder addId(String id) {
            this.id = id;
            return this;
        }

        public Builder addDistrict(int district) {
            this.district = district;
            return this;
        }

        public Builder addCongressNumber(int congressNumber) {
            this.congressNumber = congressNumber;
            return this;
        }

        public Builder addCurrent(boolean current) {
            this.current = current;
            return this;
        }

        public JSONReader build() {
            return new JSONReader(type, chamber, state, id, district, congressNumber, current);
        }
    }

    /**
     * @param tClass The class of object to return (person, committee, vote, etc.)
     * @return An object. In order to be used it must be cast to the correct type.
     */
    <T> T returnSingle(Class<T> tClass) {
        JSONObject obj;

        JSONArray array;

        URL urlObj;

        try {
            urlObj = new URL(url);
        } catch(IOException e) {
            e.printStackTrace();
            urlObj = null;
        }

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) urlObj.openConnection();
        } catch(IOException e) {
            e.printStackTrace();
            urlConnection = null;
        }
        urlConnection.setRequestProperty("X-API-Key", "API-KEY");

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            obj = new JSONObject(jsonText);
        } catch(JSONException|IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            urlConnection.disconnect();
        }

        try {
            array = obj.getJSONArray("results");
        } catch(JSONException e) {
            e.printStackTrace();
            array = new JSONArray();
        }

        try {
            obj = array.getJSONObject(0);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return tClass.cast(new Legislator(obj));
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public ArrayList<Legislator> getAllStateLegislators() throws IOException {
        ArrayList<Legislator> objects = new ArrayList<>();

        type = CHAMBER_STATE_LEGISLATORS;
        chamber = "house";

        JSONObject obj;
        JSONArray array;

        URL urlObj = new URL(String.format("https://api.propublica.org/congress/v1/members/%1s/%2s/current.json", chamber, state));

        HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
        urlConnection.setRequestProperty("X-API-Key", "API-KEY");

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            obj = new JSONObject(jsonText);
        } catch(JSONException|IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            urlConnection.disconnect();
        }

        try {
            array = obj.getJSONArray("results");
        } catch(JSONException e) {
            e.printStackTrace();
            array = new JSONArray();
        }

        for(int i = 0; i < array.length(); i++) {
            try {
                obj = array.getJSONObject(i);
            } catch(JSONException e) {
                e.printStackTrace();
                obj = new JSONObject();
            }
            objects.add(new Legislator().parseJSONObject(obj));
        }

        chamber = "senate";

        urlObj = new URL(String.format("https://api.propublica.org/congress/v1/members/%1s/%2s/current.json", chamber, state));

        urlConnection = (HttpURLConnection) urlObj.openConnection();
        urlConnection.setRequestProperty("X-API-Key", "API-KEY");

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            obj = new JSONObject(jsonText);
        } catch(JSONException|IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            urlConnection.disconnect();
        }

        try {
            array = obj.getJSONArray("results");
        } catch(JSONException e) {
            e.printStackTrace();
            array = new JSONArray();
        }

        for(int i = 0; i < array.length(); i++) {
            try {
                obj = array.getJSONObject(i);
            } catch(JSONException e) {
                e.printStackTrace();
                obj = new JSONObject();
            }
            objects.add(new Legislator().parseJSONObject(obj));
        }

        return objects;
    }
}
