package org.pyrrhic.eatwatch;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.setUpFile();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            WeightsDataSource datasource = new WeightsDataSource(getActivity());
            datasource.open();

            List<WeightInfo> values = datasource.getAllWeights();
            ArrayAdapter<WeightInfo> adapter;
            adapter = new ArrayAdapter<WeightInfo>(getActivity(),android.R.layout.simple_expandable_list_item_1,values);
            ListView listview1 = (ListView) rootView.findViewById(R.id.lstData);
            listview1.setAdapter(adapter);
            datasource.close();

            EditText editDate = (EditText) rootView.findViewById(R.id.edtDate);
            DateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String today = dateFormat.format(date);

            editDate.setText(today);
            EditText editWeight = (EditText) rootView.findViewById(R.id.edtWeight);
            editWeight.setText("220");

            return rootView;

        }

    }

    public void buildGraph(View v){
        Intent intent = new Intent(this,showGraph.class);
        startActivity(intent);
    }

    public void buildStats(View v){
        Intent intent = new Intent(this,Stats.class);
        startActivity(intent);
    }

    public void addRecord(View v){
        String mweight;
        String wDate;
        String mAve;

        EditText editText = (EditText) findViewById(R.id.edtWeight);
        mweight = editText.getText().toString();
        editText = (EditText) findViewById(R.id.edtDate);
        wDate = editText.getText().toString();
        mAve = "223";

        WeightsDataSource db = new WeightsDataSource(this);
        db.open();

        WeightInfo weight = null;

        ListView lv = (ListView) findViewById(R.id.lstData);
        ArrayAdapter<WeightInfo> adapter = (ArrayAdapter<WeightInfo>) lv.getAdapter();
        weight = db.createWeight(wDate,mweight,mAve);
        adapter.add(weight);
        adapter.notifyDataSetChanged();
        db.close();

    }

}
