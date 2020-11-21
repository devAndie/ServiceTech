package com.example.servicetech;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class StakesFragment extends Fragment {
    StakesAdapter stakesAdapter;
    TextView details, cost;
    Map<String, String>stakeMap = new LinkedHashMap<String, String>();
    Context thisContext;
    ListView listv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stakes, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Resources res = getResources();
        listv = view.findViewById(R.id.list);

        new getEvents();

        //super.onViewCreated(view, savedInstanceState);
    }
    private class getEvents extends AsyncTask<String, String, String> {

        String msg =" ";
        static final String JBDC_DRIVER = "";
        static final String DB_URL = " ://"+
                DB_Strings.DATABASE_URL + "/" +
                DB_Strings.DATABASE_NAME;


        @Override
        protected String doInBackground(String... strings) {

            Connection conn = null;
            Statement stmt = null;

            try {
                Class.forName(JBDC_DRIVER);
                conn = DriverManager.getConnection(DB_URL,
                        DB_Strings.USERNAME, DB_Strings.PASSWORD);

                stmt = conn.createStatement();
                String sql = "select * from events";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()){
                    String event = rs.getString("event");
                    String status = rs.getString("string");

                    stakeMap.put(event, status);
                }

                msg = "process complete.";

                rs.close();
                stmt.close();
                conn.close();

            }catch (SQLException connError){
                msg = "an exception was thrown for JBDC.";
                connError.printStackTrace();;
            } catch (ClassNotFoundException e) {
                msg = "Class not found ";
                e.printStackTrace();
            }
            finally {
                try{
                    if (stmt !=null){
                        stmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try{
                    if (conn !=null){
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String msg) {

            if (stakeMap.size() > 0) {
                stakesAdapter = new StakesAdapter(thisContext, stakeMap);
                listv.setAdapter(stakesAdapter);
            }
        }
    }
}


