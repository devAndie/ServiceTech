package com.example.servicetech;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import static java.sql.DriverManager.*;

public class BookFragment extends Fragment {
    FragmentActivity listener;
    EventsAdapter eventsAdapter;
    Context thisContext;
    ListView lv;
    TextView progressTxt;
    ProgressBar progressBar;
    Map<String, Double> eventsMap = new LinkedHashMap<String, Double>();

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> details = new ArrayList<String>();
        ArrayList<Double> cost = new ArrayList<Double>();
//        stakesAdapter = new StakesAdapter(getActivity(), (Map) details);
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
        lv.setAdapter(eventsAdapter);
        thisContext = getContext();
        progressTxt = view.findViewById(R.id.prog_txt);

        progressTxt.setText("");
        GetData retrieveData = new GetData();
        retrieveData.execute("");
        DBHelper.getQuotations()

//        getQuotations();

    }

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
}
