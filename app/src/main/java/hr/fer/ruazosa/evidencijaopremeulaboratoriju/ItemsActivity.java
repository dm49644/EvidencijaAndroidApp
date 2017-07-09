package hr.fer.ruazosa.evidencijaopremeulaboratoriju;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.LabIthem;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.representations.LabIthemResource;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection.IonRest;

public class ItemsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList;
    ArrayList<LabIthem> detailedItemsList = new ArrayList<>();
    LinkedList<LabIthemResource> labIthemResources = new LinkedList<>();
    IonRest rest = new IonRest(ItemsActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailedItemActivity = new Intent(ItemsActivity.this, DetailedItemActivity.class);

                String itemString = (String) listView.getItemAtPosition(position);
                detailedItemActivity.putExtra("item", itemString);
                startActivity(detailedItemActivity);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        populateList();
    }
    private void populateList() {
        arrayList = new ArrayList<>();
        getData();
    }


    private void getData() {
        new UpdateListTask().execute(1);
    }

    private void applyFilters(LabIthem item, String searchWord, String filter, String status) {
            if(item.getStatus().toUpperCase().equals(status.toUpperCase())  || status.equals("all") || (status.equals("available") && item.getStatus() == null)) {
                switch (filter.toUpperCase()) {
                    case "TYPE": {
                        if (item.getType().toUpperCase().contains(searchWord.toUpperCase())) {
                            arrayList.add(item.toString());
                        }
                        break;
                    }
                    case "KIT": {
                        if (item.getKit().toUpperCase().contains(searchWord.toUpperCase())) {
                            arrayList.add(item.toString());
                        }
                        break;
                    }
                    case "SUBTYPE": {
                        if (item.getSubtype().toUpperCase().contains(searchWord.toUpperCase())) {
                            arrayList.add(item.toString());
                        }
                        break;
                    }
                    case "ID": {
                        if (item.getId().toUpperCase().contains(searchWord.toUpperCase())) {
                           arrayList.add(item.toString());
                        }
                        break;
                    }
                    case "DEVICE": {
                        if (item.getDevice().toUpperCase().contains(searchWord.toUpperCase())) {
                            arrayList.add(item.toString());
                        }
                        break;
                    }
                    case "DESCRIPTION": {
                        if (item.getDescription().toUpperCase().contains(searchWord.toUpperCase())) {
                            arrayList.add(item.toString());
                        }
                        break;
                    }
                }
        }
    }



        private class UpdateListTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            Ion.with(ItemsActivity.this)
                    .load("http://10.0.2.2:8080/api/labIthems")
                    .asString()
                    .setCallback(
                            new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String data) {
                                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_spinner_item, arrayList);
                                    Log.d("PODACI:", data);
                                    try {
                                        labIthemResources = (LinkedList<LabIthemResource>) rest.getListOfLabIthems(data);

                                        Intent intent = getIntent();



                                        final String searchWord = intent.getStringExtra("search");
                                        final String filter = intent.getStringExtra("filter");
                                        final String status = intent.getStringExtra("status");
                                        for(LabIthemResource resource : labIthemResources) {
                                            Ion.with(ItemsActivity.this)
                                                    .load("http://10.0.2.2:8080/api/labIthems/" + resource.getId())
                                                    .asString()
                                                    .setCallback(
                                                            new FutureCallback<String>() {
                                                                @Override
                                                                public void onCompleted(Exception e, String data) {
                                                                    Log.d("PODACI:", data);
                                                                    try {
                                                                        LabIthem temp = rest.parseLabIthem(new JSONObject(data));
                                                                        detailedItemsList.add(temp);


                                                                        applyFilters(temp, searchWord, filter, status);


                                                                    } catch (JSONException e1) {
                                                                        e1.printStackTrace();
                                                                    }
                                                                    listView.setClickable(true);

                                                                    listView.setAdapter(adapter);

                                                                    adapter.notifyDataSetChanged();
                                                                }
                                                            });
                                        }




                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                            });
            return 1;
        }
    }

}
