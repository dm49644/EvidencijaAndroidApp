package hr.fer.ruazosa.evidencijaopremeulaboratoriju;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.LabIthem;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.Posudba;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection.IonRest;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection.JavaHttpUrlConnectionReader;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection.RestFactory;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection.URLService;


public class LogInActivity extends AppCompatActivity {
    /*public void vrati(String s){
        rezultat=s;
    }

    public String rezultat;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

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


      /*  Ion.with(this)
                .load("http://10.0.2.2:8080/api/posudbas/1")
                .asString()
                .setCallback(
                        new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String data) {
                                Log.d("PODACI:", data);

                                //DELETE dio
                                Ion.with(LogInActivity.this)
                                        .load("DELETE","http://10.0.2.2:8080/api/posudbas/1")
                                        .asString().setCallback(new FutureCallback<String>() {
                                    @Override
                                    public void onCompleted(Exception e, String result) {
                                        Log.d("Result: ", result);
                                    }
                                });

                                IonRest ir=new IonRest(LogInActivity.this);
                                Posudba p=null;
                                try {
                                    p=ir.parsePosudba(new JSONObject(data));
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }


                                p.setProfesor("abc");

                                JsonObject jo=new JsonObject();
                                jo=ir.fromPosudbaToJSON(jo, p);

                                Log.d("Posudba:", p.toString());


                                //Postavljanje Jsona na server
                                Ion.with(LogInActivity.this)
                                        .load("http://10.0.2.2:8080/api/posudbas")
                                        .setJsonObjectBody(jo)
                                        .asJsonObject()
                                        .setCallback(new FutureCallback<JsonObject>() {
                                            @Override
                                            public void onCompleted(Exception e, JsonObject result) {
                                                // Log.d("PA KAZE: ", result.getAsString());
                                            }
                                        });
                                vrati(data);
                            }

                        }
                );*/







        //GET LOGIN AND REGISTER BUTTONS FROM THE VIEW
        Button loginButton = (Button) findViewById(R.id.logInButton);
        Button registerButton = (Button) findViewById(R.id.registerButton);


        //ACTION ON PRESSING THE LOGIN BUTTON
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivityIntent = new Intent(LogInActivity.this, SearchActivity.class);

                //FETCH USERNAME AND PASSWORD
                String username = ((EditText)findViewById(R.id.usernameEditText)).getText().toString();
                String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();


                //TODO: CHECK IF USERNAME AND PASSWORD MATCH (YES - START ACTIVITY, NO - TOAST
                startActivity(searchActivityIntent);
            }
        });

        //ACTION ON PRESSING THE REGISTER BUTTON
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivityIntent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(registerActivityIntent);
            }
        });


    }
}
