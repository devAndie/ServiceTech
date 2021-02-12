package com.example.servicetech;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.LinkedHashMap;
import java.util.Map;

public class BookFragment extends Fragment {
    FragmentActivity listener;
    ListingRecyclerViewAdapter listingRecyclerViewAdapter;
    Context thisContext;
    ListView lv;
    TextView progressTxt;
    ProgressBar progressBar;
    Map<String, Double> eventsMap = new LinkedHashMap<String, Double>();
    String[] items, cost, description;


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Resources res = getResources();
        ListView lv = view.findViewById(R.id.list);
        description = res.getStringArray(R.array.description);
        cost = res.getStringArray(R.array.cost);
        items = res.getStringArray(R.array.items);

        thisContext = getContext();
        progressTxt = view.findViewById(R.id.prog_txt);

        listingRecyclerViewAdapter = new ListingRecyclerViewAdapter(thisContext, items, cost, description);

        lv.setAdapter(listingRecyclerViewAdapter);
        progressTxt.setText("");
        //   GetData retrieveData = new GetData();
       // retrieveData.execute("");

    }

/*
    private class GetData extends AsyncTask<String, String, String>{

        String msg = "";
        static final String JBDC_DRIVER = "com.mysql.cj.jdbc.Driver";
        static final String DB_URL = "cj.jbdc:mysql://" +
                Db_Strings.DATABASE_URL + "/" +
                Db_Strings.DATABASE_NAME;

        @Override
        protected void onPreExecute() {
            progressTxt.setText("connecting to database...");
        }

        @Override
        protected String doInBackground(String... strings) {

            Connection conn = null;
            Statement stmt = null;

            try {
                Class.forName(JBDC_DRIVER);
                conn = getConnection(
                        DB_URL, Db_Strings.USERNAME, Db_Strings.PASSWORD);
                stmt = conn.createStatement();
                String sql = "select * from technicians";

                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String name = rs.getString("name");
                    double cost = rs.getDouble("cost");

                    eventsMap.put(name, cost);
                }
                msg = "Process complete";
                rs.close();
                stmt.close();
                conn.close();

            } catch (SQLException | ClassNotFoundException connError){
                msg = "An exception error was thrown for JBDC.";
                connError.printStackTrace();
            } finally {
                try {
                    if (stmt !=null) {
                        stmt.close();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    if (conn !=null) {
                        conn.close();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressTxt.setText(this.msg);

            if (eventsMap.size() > 0) {
                eventsAdapter = new EventsAdapter(thisContext, eventsMap);
                lv.setAdapter(eventsAdapter);
            }
        }
    }
 */
}
