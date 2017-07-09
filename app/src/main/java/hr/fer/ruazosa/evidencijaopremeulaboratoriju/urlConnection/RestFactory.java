package hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection;

import android.util.Log;

import org.json.JSONException;

import java.util.List;

import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.LabIthem;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.Posudba;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.representations.LabIthemResource;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection.*;

public class RestFactory {
    public static final String BASE_IP = "10.0.2.2";

    public static RestInterface getInstance() {
       // return new InMemoryRestImplementation();

        // HTTP
       return new HttpsRestUrlConnection();
        //return new RestSpringTemplete();
        //return new RestRetrofit();

        // HTTPS
        //return new HttpsRestUrlConnection("user", "user");
        //return new HttpsRestSpringTemplete("user", "user");
        //return new HttpsRestRetrofit("user", "user");


        //return new RestUrlConnectionXML();
    }

    public static void funkcija() throws JSONException {
        RestInterface ri=getInstance();
        List<LabIthemResource> list=ri.getListOfLabIthems();
        Log.d("tag",list.toString());
    }
}