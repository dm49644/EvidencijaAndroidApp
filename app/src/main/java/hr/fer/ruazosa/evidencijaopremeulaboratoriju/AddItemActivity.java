package hr.fer.ruazosa.evidencijaopremeulaboratoriju;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.LabIthem;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection.IonRest;

public class AddItemActivity extends AppCompatActivity {
    private String device;
    private String type;
    private String subtype;
    private String kit;
    private String id;
    private String description;
    private String location;
    private String number;
    private String inventoryNumber;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fetchData();
                }catch(Exception e) {
                    Toast mandatoryDataNotInserted = Toast.makeText(AddItemActivity.this, "Only \"Location...\" can be empty!", Toast.LENGTH_LONG);
                    mandatoryDataNotInserted.show();
                    return;
                }
                LabIthem newItem = new LabIthem(id, id, inventoryNumber, kit,
                        description, location, "available", device, type, subtype, number);
                new AddItemAsyncTask().execute(newItem);
            }
        });
    }

    private void fetchData() throws Exception{

        EditText device = (EditText) findViewById(R.id.deviceLetterInputText);
        EditText type = (EditText) findViewById(R.id.typeInputText);
        EditText kit = (EditText) findViewById(R.id.kitInputText);
        EditText id = (EditText) findViewById(R.id.idInputText);
        EditText description = (EditText) findViewById(R.id.descriptionInputText);
        EditText location = (EditText) findViewById(R.id.locationInputText);
        EditText number = (EditText) findViewById(R.id.numberEditText);
        EditText inventoryNumber = (EditText) findViewById(R.id.inventoryNumberEditText);
        EditText subtype = (EditText) findViewById(R.id.subtypeEditText);



        this.location = location.getText().toString();
        this.device = device.getText().toString();
        this.type = type.getText().toString();
        this.kit = kit.getText().toString();
        this.id = id.getText().toString();
        this.description = description.getText().toString();
        this.number = number.getText().toString();
        this.inventoryNumber = inventoryNumber.getText().toString();
        this.subtype = subtype.getText().toString();


        if(this.device.isEmpty() || this.type.isEmpty()
                || this.kit.isEmpty() || this.id.isEmpty()
                || this.description.isEmpty() || this.number.isEmpty()
                || this.subtype.isEmpty()) throw new Exception();



    }


    private class AddItemAsyncTask extends AsyncTask<LabIthem, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            Button okButton = (Button) findViewById(R.id.okButton);
            okButton.setEnabled(false);
        }

        @Override
        protected Integer doInBackground(LabIthem... params) {
            LabIthem ithem = params[0];
            JsonObject json = new JsonObject();
            json = new IonRest(AddItemActivity.this).fromLabItemToJSON(json, ithem);
            Ion.with(AddItemActivity.this)
                    .load("http://10.0.2.2:8080/api/labIthems")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Toast successToast = Toast.makeText(AddItemActivity.this, "Added successfully", Toast.LENGTH_SHORT);
                            successToast.show();
                            Button okButton = (Button) findViewById(R.id.okButton);
                            okButton.setEnabled(true);
                            finish();
                        }
                    });
            return 1;
        }
    }
}
