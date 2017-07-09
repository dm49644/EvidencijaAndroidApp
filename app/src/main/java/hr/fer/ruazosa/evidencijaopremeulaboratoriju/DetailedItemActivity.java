package hr.fer.ruazosa.evidencijaopremeulaboratoriju;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.LabIthem;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.Posudba;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.representations.LabIthemResource;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection.IonRest;

public class DetailedItemActivity extends AppCompatActivity {
    private LinkedList<Posudba> posudbas;
    private IonRest rest = new IonRest(this);
    private LabIthem labIthem;
    private Posudba temp;
    private String labIthemResourceString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_item);

        TextView availableColorBox = (TextView) findViewById(R.id.availableColorBox);
        TextView unavailableColorBox = (TextView) findViewById(R.id.redColorBox);
        TextView reservedColorBox = (TextView) findViewById(R.id.reservedColorBox);

        availableColorBox.setBackgroundColor(Color.GREEN);
        unavailableColorBox.setBackgroundColor(Color.RED);
        reservedColorBox.setBackgroundColor(Color.YELLOW);

        labIthemResourceString = getIntent().getStringExtra("item");

        Button previousBorrowsButton = (Button) findViewById(R.id.previousBorrowsButton);
        previousBorrowsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedItemActivity.this, PreviousBorrowsActivity.class);
                intent.putExtra("id", labIthem.getId());
                startActivity(intent);
            }
        });

        Button removeItemButton = (Button) findViewById(R.id.removeItemButton);
        removeItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteAsyncTask().execute(labIthem);

            }
        });

        Button reserveButton = (Button) findViewById(R.id.reserveButton);
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedItemActivity.this, ReserveActivity.class);
                intent.putExtra("id", labIthem.getId());
                startActivity(intent);
            }
        });

        Button returnButton = (Button) findViewById(R.id.returnItemButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteAsyncTask().execute(labIthem);
                labIthem.setStatus("available");
                new AddItemAsyncTask().execute(labIthem);
            }
        });

        Button borrowButton = (Button) findViewById(R.id.borrowButton);
        borrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(labIthem.getStatus().toUpperCase().equals("RESERVED")) {
                    new UpdatePosudbaTask().execute(1);
                }
                else {
                    Intent intent = new Intent(DetailedItemActivity.this, ReserveActivity.class);
                    intent.putExtra("id", labIthem.getId());
                    intent.putExtra("borrow", "yes");
                    startActivity(intent);
                }
            }
        });

    }

    protected void onResume() {
        super.onResume();
        new FindDetailedTask().execute(labIthemResourceString);

    }
    private void populateTextViews() {
        TextView id = (TextView) findViewById(R.id.idView);
        TextView number = (TextView) findViewById(R.id.numberOfItemsView);
        TextView status = (TextView) findViewById(R.id.statusColorBox);
        TextView kit = (TextView) findViewById(R.id.kitView);
        TextView description = (TextView) findViewById(R.id.descView);
        TextView location = (TextView) findViewById(R.id.locationNameView);
        TextView type = (TextView) findViewById(R.id.typeView);
        TextView subtype = (TextView) findViewById(R.id.subtypeView);
        TextView device = (TextView) findViewById(R.id.deviceView);

        id.setText(this.labIthem.getId());
        number.setText(this.labIthem.getNumber());
        if(this.labIthem.getStatus().toUpperCase().equals("RESERVED")) {
            status.setBackgroundColor(Color.YELLOW);
        }
        else if(this.labIthem.getStatus().toUpperCase().equals("UNAVAILABLE")) {
            status.setBackgroundColor(Color.RED);
        }
        else status.setBackgroundColor(Color.GREEN);
        id.setText(this.labIthem.getId());
        number.setText(this.labIthem.getNumber());
        kit.setText(this.labIthem.getKit());
        description.setText(this.labIthem.getDescription());
        location.setText(this.labIthem.getLocation());
        type.setText(this.labIthem.getType());
        subtype.setText(this.labIthem.getSubtype());
        device.setText(this.labIthem.getDevice());
    }

    private class UpdatePosudbaTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            Ion.with(DetailedItemActivity.this)
                    .load("http://10.0.2.2:8080/api/posudbas/")
                    .asString()
                    .setCallback(
                            new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String data) {
                                    Log.d("POSUDBE:", data);
                                    try {
                                        posudbas = (LinkedList<Posudba>) rest.getListOfPosudbas(data);
                                        temp = posudbas.getLast();
                                        temp.setDatumPreuz(new SimpleDateFormat("dd.MM.yyyy.").format(new Date()));
                                        new DeletePosudbaTask().execute(1);
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });
            return 1;
        }

    }

    private class DeletePosudbaTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            Ion.with(DetailedItemActivity.this)
                    .load("DELETE", "http://10.0.2.2:8080/api/posudbas/" + temp.getId())
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("Result: ", result);

                    JsonObject json = new JsonObject();
                    json = new IonRest(DetailedItemActivity.this).fromPosudbaToJSON(json, temp);

                    Ion.with(DetailedItemActivity.this)
                            .load("http://10.0.2.2:8080/api/posudbas")
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    Ion.with(DetailedItemActivity.this)
                                            .load("DELETE","http://10.0.2.2:8080/api/labIthems/" + labIthem.getId())
                                            .asString().setCallback(new FutureCallback<String>() {
                                                                    @Override
                                                                    public void onCompleted(Exception e, String result) {Log.d("Result: ", result);
                                                                        JsonObject jsonItem = new JsonObject();
                                                                        labIthem.setStatus("unavailable");
                                                                        jsonItem = new IonRest(DetailedItemActivity.this).fromLabItemToJSON(jsonItem, labIthem);
                                                                        Ion.with(DetailedItemActivity.this)
                                                                                .load("http://10.0.2.2:8080/api/labIthems")
                                                                                .setJsonObjectBody(jsonItem)
                                                                                .asJsonObject()
                                                                                .setCallback(new FutureCallback<JsonObject>() {
                                                                                    @Override
                                                                                    public void onCompleted(Exception e, JsonObject result) {
                                                                                        new FindDetailedTask().execute(labIthemResourceString);
                                                                                    }
                                                                                });

                                                                    }
                                                                });
                                                            }
                                                    });
                }
            });
            return 1;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<LabIthem, Integer, Integer> {
        @Override
        protected Integer doInBackground(LabIthem... params) {
            LabIthem ithem = params[0];
            Ion.with(DetailedItemActivity.this)
                    .load("DELETE","http://10.0.2.2:8080/api/labIthems/" + ithem.getId())
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("Result: ", result);
                    finish();

                }
            });
            return 1;
        }


    }

    private class AddItemAsyncTask extends AsyncTask<LabIthem, Integer, Integer> {


        @Override
        protected Integer doInBackground(LabIthem... params) {
            LabIthem ithem = params[0];
            JsonObject json = new JsonObject();
            json = new IonRest(DetailedItemActivity.this).fromLabItemToJSON(json, ithem);
            Ion.with(DetailedItemActivity.this)
                    .load("http://10.0.2.2:8080/api/labIthems")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            new FindDetailedTask().execute(labIthemResourceString);
                        }
                    });
            return 1;
        }
    }

    private class FindDetailedTask extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            String id = params[0];
            String[] temp = id.split("\\|");
            Ion.with(DetailedItemActivity.this)
                    .load("http://10.0.2.2:8080/api/labIthems/" + temp[0])
                    .asString()
                    .setCallback(
                            new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String data) {
                                    Log.d("PODACI:", data);
                                    IonRest rest = new IonRest(DetailedItemActivity.this);
                                    try {
                                        labIthem = rest.parseLabIthem(new JSONObject(data));
                                        populateTextViews();
                                        Button reserveButton = (Button) findViewById(R.id.reserveButton);
                                        Button returnButton = (Button) findViewById(R.id.returnItemButton);
                                        Button borrowButton = (Button) findViewById(R.id.borrowButton);
                                        if(labIthem.getStatus().toUpperCase().equals("RESERVED") || labIthem.getStatus().toUpperCase().equals("AVAILABLE"))
                                            returnButton.setEnabled(false);
                                        else returnButton.setEnabled(true);
                                        if(labIthem.getStatus().toUpperCase().equals("RESERVED")
                                                || labIthem.getStatus().toUpperCase().equals("UNAVAILABLE")) {
                                            reserveButton.setEnabled(false);
                                        }
                                        else reserveButton.setEnabled(true);
                                        if(labIthem.getStatus().toUpperCase().equals("UNAVAILABLE")) {
                                            borrowButton.setEnabled(false);
                                        }
                                        else borrowButton.setEnabled(true);
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });
            return 1;
        }
    }

    /* Ion.with(this)
                .load("http://10.0.2.2:8080/api/labIthems")
                .asString()
                .setCallback(
                        new FutureCallback<String>() {
                                 @Override
                                 public void onCompleted(Exception e, String data) {
                                     Log.d("PODACI:", data);
                                 }
                        }
                );*/

       /*Posudba posudba = new Posudba();
        posudba.setDatumPreuz("2.2.3023.");
        posudba.setStudent("Luk");
        posudba.setProfesor("Dobrić");
        posudba.setBrDana("10 000");
        //posudba.setId("ovo triba bit autogenerirano");
        posudba.setIdOpreme("neka_Random");

        JsonObject json = new JsonObject();
        json = new IonRest(this).fromPosudbaToJSON(json, posudba);

        Ion.with(this)
                .load("http://10.0.2.2:8080/api/posudbas")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // Log.d("PA KAZE: ", result.getAsString());
                    }
                });
                */


            /*Ion.with(this)
                    .load("DELETE","http://10.0.2.2:8080/api/posudbas/2")
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("Result: ", result);
                }
            });*/
}
