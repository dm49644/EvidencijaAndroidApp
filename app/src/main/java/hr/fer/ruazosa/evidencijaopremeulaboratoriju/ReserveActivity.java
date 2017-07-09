package hr.fer.ruazosa.evidencijaopremeulaboratoriju;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.LabIthem;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.Posudba;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection.IonRest;

public class ReserveActivity extends AppCompatActivity {
    private Posudba posudba;
    private LabIthem labItem;
    private String id;
    private IonRest rest = new IonRest(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        id = getIntent().getStringExtra("id");

        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText studentName = (EditText) findViewById(R.id.studentEditText);
                EditText professorName = (EditText) findViewById(R.id.profesorEditText);
                EditText days = (EditText) findViewById(R.id.durationPeriodEditText);

                String student = studentName.getText().toString();
                String professor = professorName.getText().toString();
                String duration = days.getText().toString();

                if (student.isEmpty() || professor.isEmpty() || duration.isEmpty()) {
                    Toast toast = Toast.makeText(ReserveActivity.this, "Fields shouldn't be blank!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    if(getIntent().getStringExtra("borrow") != null) {
                        if (getIntent().getStringExtra("borrow").equals("yes"))
                            posudba = new Posudba(id, professor, student, new SimpleDateFormat("dd.MM.yyyy.").format(new Date()), duration + " dana");
                    }
                    else posudba = new Posudba(id, professor, student, null, duration + " dana");
                    new AddPosudbaAsyncTask().execute(1);
                }
            }
        });
    }

    private class AddPosudbaAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            Button doneButton = (Button) findViewById(R.id.doneButton);
            doneButton.setEnabled(false);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            JsonObject json = new JsonObject();
            json = new IonRest(ReserveActivity.this).fromPosudbaToJSON(json, posudba);
            Ion.with(ReserveActivity.this)
                    .load("http://10.0.2.2:8080/api/posudbas")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            Ion.with(ReserveActivity.this)
                                    .load("http://10.0.2.2:8080/api/labIthems/" + posudba.getIdOpreme())
                                    .asString()
                                    .setCallback(
                                            new FutureCallback<String>() {
                                                @Override
                                                public void onCompleted(Exception e, String data) {
                                                    Log.d("PODACI:", data);
                                                    try {
                                                        labItem = rest.parseLabIthem(new JSONObject(data));
                                                        Ion.with(ReserveActivity.this)
                                                                .load("DELETE","http://10.0.2.2:8080/api/labIthems/" + posudba.getIdOpreme())
                                                                .asString()
                                                                .setCallback(new FutureCallback<String>() {
                                                                 @Override
                                                                 public void onCompleted(Exception e, String result) {
                                                                    Log.d("Result: ", result);
                                                                     JsonObject jsonItem = new JsonObject();
                                                                     if(getIntent().getStringExtra("borrow") != null) {
                                                                         if (getIntent().getStringExtra("borrow").equals("yes"))
                                                                             labItem.setStatus("unavailable");
                                                                     }
                                                                     else labItem.setStatus("reserved");
                                                                     jsonItem = new IonRest(ReserveActivity.this).fromLabItemToJSON(jsonItem, labItem);
                                                                     Ion.with(ReserveActivity.this)
                                                                             .load("http://10.0.2.2:8080/api/labIthems")
                                                                             .setJsonObjectBody(jsonItem)
                                                                             .asJsonObject()
                                                                             .setCallback(new FutureCallback<JsonObject>() {
                                                                                 @Override
                                                                                 public void onCompleted(Exception e, JsonObject result) {
                                                                                     Button doneButton = (Button) findViewById(R.id.doneButton);
                                                                                     doneButton.setEnabled(true);
                                                                                     finish();
                                                                                 }
                                                                             });

                                                                 }
                                                        });
                                                    } catch (JSONException e1) {
                                                        e1.printStackTrace();
                                                    }

                                                }
                                            });




                        }
                    });
            return 1;
        }
    }
}
