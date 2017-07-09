package hr.fer.ruazosa.evidencijaopremeulaboratoriju;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.Posudba;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.representations.LabIthemResource;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection.IonRest;

public class PreviousBorrowsActivity extends AppCompatActivity {
    IonRest rest = new IonRest(this);
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    LinkedList<Posudba> posudbas;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_borrows);
        listView = (ListView) findViewById(R.id.borrowsList);
        id = getIntent().getStringExtra("id");
        new UpdateListTask().execute(1);
    }

    private class UpdateListTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            Ion.with(PreviousBorrowsActivity.this)
                    .load("http://10.0.2.2:8080/api/posudbas/")
                    .asString()
                    .setCallback(
                            new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String data) {
                                    Log.d("POSUDBE:", data);
                                    try {
                                        posudbas = (LinkedList<Posudba>) rest.getListOfPosudbas(data);
                                        for(Posudba posudba : posudbas) {
                                            if(posudba.getIdOpreme().equals(id)) {
                                                arrayList.add(posudba.toString());
                                            }
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                                android.R.layout.simple_spinner_item, arrayList);

                                        listView.setClickable(false);

                                        listView.setAdapter(adapter);

                                        adapter.notifyDataSetChanged();
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });
            return 1;
        }

    }
}
