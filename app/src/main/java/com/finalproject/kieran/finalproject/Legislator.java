package com.finalproject.kieran.finalproject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Legislator {
    /**
     * Fields
     * */

    // R, D, or I
    public String party;
    // Not part of the API, I made this
    public String party_label;
    // Not part of the API, I made this
    public String gender_label;
    // House only; the number of the district the representative represents - nonexistent for senate members
    public int district;

    public String role;

    public String election;

    Legislator parseJSONObject(final JSONObject obj) {
        try {
            this.party = obj.getString("party");
            switch (this.party.toUpperCase()) {
                case "R":
                    this.party_label = "Republican";
                    break;
                case "D":
                    this.party_label = "Democrat";
                    break;
                case "I":
                    this.party_label = "Independent";
                    break;
                default:
                    this.party_label = this.party;
                    break;
            }
        } catch (JSONException e) {
            this.party = "U";
            this.party_label = "Unknown";
        }
        try {
            this.gender = obj.getString("gender").toUpperCase();
            switch (this.gender) {
                case "M":
                    this.gender_label = "Male";
                    break;
                case "F":
                    this.gender_label = "Female";
                    break;
                default:
                    this.gender_label = "Unknown";
            }
        } catch (JSONException e) {
            this.gender = "U";
            this.gender_label = "Unknown";
        }
        try {
            this.district = obj.getInt("district");
        } catch (JSONException e) {
            this.district = -1;
        }
        try {
            this.role = obj.getString("role");
        } catch (JSONException e) {
            this.role = "Unknown";
        }
        try {
            this.member_id = obj.getString("id");
        } catch (JSONException e) {
            this.member_id = "";
        }
        try {
            this.first_name = obj.getString("first_name");
        } catch (JSONException e) {
            this.first_name = "";
        }
        try {
            this.last_name = obj.getString("last_name");
        } catch (JSONException e) {
            this.last_name = "";
        }
        try {
            this.middle_name = obj.getString("middle_name");
        } catch (JSONException e) {
            this.middle_name = "";
        }
        try {
            this.election = obj.getString("next_election");
        } catch(JSONException e) {
            this.election = "Unknown";
        }

        return this;
    }

    String member_id, first_name, middle_name, last_name, suffix, date_of_birth, gender, url,
            cspan_id, votesmart_id, twitter_account, facebook_account, youtube_account, crp_id,
            current_party, most_recent_vote;
    boolean in_office;

    ArrayList<Role> roleList = new ArrayList<>();

    public static class Role {
        String congress, chamber, title, short_title, state, party, leadership_role, fec_candidate_id,
                seniority, district, ocd_id, start_date, end_date, office, phone, fax, contact_form;
        boolean at_large;
        int bills_sponsored, bills_cosponsored;
        double missed_votes_pct, votes_with_party_pct;

        ArrayList<String> committees = new ArrayList<>(), subcommittees = new ArrayList<>();

        public Role(JSONObject o) {
            try {
                congress = o.getString("congress");
            } catch(JSONException e) {
                congress = "Unknown";
            }

            try {
                chamber = o.getString("chamber");
            } catch(JSONException e) {
                chamber = "Unknown";
            }

            try {
                title = o.getString("title");
            } catch(JSONException e) {
                title = "Unknown";
            }

            try {
                short_title = o.getString("short_title");
            } catch(JSONException e) {
                short_title = "Unknown";
            }

            try {
                state = o.getString("state");
            } catch(JSONException e) {
                state = "Unknown";
            }

            try {
                party = o.getString("party");
            } catch(JSONException e) {
                party = "Unknown";
            }

            try {
                leadership_role = o.getString("leadership_role");
            } catch(JSONException e) {
                leadership_role = "None";
            }

            try {
                fec_candidate_id = o.getString("fec_candidate_id");
            } catch(JSONException e) {
                fec_candidate_id = "Unknown";
            }

            try {
                seniority = o.getString("seniority");
            } catch(JSONException e) {
                seniority = "Unknown";
            }

            try {
                district = o.getString("district");
            } catch(JSONException e) {
                district = "None";
            }

            try {
                ocd_id = o.getString("ocd_id");
            } catch(JSONException e) {
                ocd_id = "Unknown";
            }

            try {
                start_date = o.getString("start_date");
            } catch(JSONException e) {
                start_date = "Unknown";
            }

            try {
                end_date = o.getString("end_date");
            } catch(JSONException e) {
                end_date = "Unknown";
            }

            try {
                office = o.getString("office");
            } catch(JSONException e) {
                office = "N/A";
            }

            try {
                phone = o.getString("phone");
            } catch(JSONException e) {
                phone = "N/A";
            }

            try {
                fax = o.getString("fax");
            } catch(JSONException e) {
                fax = "N/A";
            }

            try {
                contact_form = o.getString("contact_form");
            } catch(JSONException e) {
                contact_form = "N/A";
            }

            try {
                at_large = o.getBoolean("at_large");
            } catch(JSONException e) {
                at_large = false;
            }

            try {
                bills_sponsored = o.getInt("bills_sponsored");
            } catch(JSONException e) {
                bills_sponsored = -1;
            }

            try {
                bills_cosponsored = o.getInt("bills_cosponsored");
            } catch(JSONException e) {
                bills_cosponsored = -1;
            }

            try {
                missed_votes_pct = o.getDouble("missed_votes_pct");
            } catch(JSONException e) {
                missed_votes_pct = -1;
            }

            try {
                votes_with_party_pct = o.getDouble("votes_with_party_pct");
            } catch(JSONException e) {
                votes_with_party_pct = -1;
            }

            JSONArray arr;
            try {
                arr = o.getJSONArray("committees");
            } catch(JSONException e) {
                e.printStackTrace();
                arr = new JSONArray();
            }

            if(arr.length() > 0) {
                for (int i = 0; i < arr.length(); i++) {
                    try {
                        committees.add(arr.getJSONObject(i).getString("name"));
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                arr = o.getJSONArray("subcommittees");
            } catch(JSONException e) {
                e.printStackTrace();
                arr = new JSONArray();
            }

            if(arr.length() > 0) {
                for (int i = 0; i < arr.length(); i++) {
                    try {
                        subcommittees.add(arr.getJSONObject(i).getString("name"));
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Legislator(JSONObject o) {
        JSONArray jsonArray;

        try {
            member_id = o.getString("member_id");
        } catch(JSONException e) {
            member_id = "";
        }

        try {
            first_name = o.getString("first_name");
        } catch(JSONException e) {
            first_name = "unknown";
        }

        try {
            middle_name = o.getString("middle_name");
        } catch(JSONException e) {
            middle_name = "";
        }

        try {
            last_name = o.getString("last_name");
        } catch(JSONException e) {
            last_name = "";
        }

        try {
            suffix = o.getString("suffix");
        } catch(JSONException e) {
            suffix = "";
        }

        try {
            date_of_birth = o.getString("date_of_birth");
        } catch(JSONException e) {
            date_of_birth = "unknown";
        }

        try {
            gender = o.getString("gender");
        } catch(JSONException e) {
            gender = "unknown";
        }

        try {
            url = o.getString("url");
        } catch(JSONException e) {
            url = "";
        }

        try {
            cspan_id = o.getString("cspan_id");
        } catch(JSONException e) {
            cspan_id = "";
        }

        try {
            votesmart_id = o.getString("votesmart_id");
        } catch(JSONException e) {
            votesmart_id = "";
        }

        try {
            twitter_account = o.getString("twitter_account");
        } catch(JSONException e) {
            twitter_account = "Unknown";
        }

        try {
            facebook_account = o.getString("facebook_account");
        } catch(JSONException e) {
            facebook_account = "Unknown";
        }

        try {
            youtube_account = o.getString("youtube_account");
        } catch(JSONException e) {
            youtube_account = "Unknown";
        }

        try {
            crp_id = o.getString("crp_id");
        } catch(JSONException e) {
            crp_id = "Unknown";
        }

        try {
            current_party = o.getString("current_party");
        } catch(JSONException e) {
            current_party = "Unknown";
        }

        try {
            most_recent_vote = o.getString("most_recent_vote");
        } catch(JSONException e) {
            most_recent_vote = "Unknown";
        }

        try {
            in_office = o.getBoolean("in_office");
        } catch(JSONException e) {
            in_office = false;
        }

        try {
            jsonArray = o.getJSONArray("roles");
        } catch(JSONException e) {
            e.printStackTrace();
            jsonArray = new JSONArray();
        }

        if(jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    roleList.add(new Role(jsonArray.getJSONObject(i)));
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Legislator() {}
}
