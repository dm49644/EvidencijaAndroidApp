package hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.LabIthem;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.Posudba;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.representations.LabIthemResource;

/**
 * Created by Domagoj on 07/07/2017.
 */

public class IonRest implements RestInterface {
    private Context context;
    String rezultat;

    public IonRest(Context context) {
        this.context = context;
    }

    public void callServer(String url) {
        Ion.with(context)
                .load(url)
                .asString()
                .setCallback(
                        new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String data) {
                                Log.d("PODACI:", data);
                                resultString(data);
                            }
                        }
                );

    }

    public void resultString(String s) {
        rezultat = s;
    }

    @Override
    public List<Posudba> getListOfPosudbas() throws JSONException {
        callServer("http://10.0.2.2:8080/api/posudbas");
        JSONArray arrayOfPosudbas = new JSONArray(rezultat);

        List<Posudba> list = new LinkedList<>();
        for (int i = 0; i < arrayOfPosudbas.length(); i++) {
            Posudba c = parsePosudba((JSONObject) arrayOfPosudbas.get(i));
            list.add(c);
        }

        return list;

    }

    public List<Posudba> getListOfPosudbas(String data) throws JSONException {
        JSONArray arrayOfPosudbas = new JSONArray(data);

        List<Posudba> list = new LinkedList<>();
        for (int i = 0; i < arrayOfPosudbas.length(); i++) {
            Posudba c = parsePosudba((JSONObject) arrayOfPosudbas.get(i));
            list.add(c);
        }

        return list;

    }

    @Override
    public Posudba getPosudba(String id) throws JSONException {
        callServer("http://10.0.2.2:8080/api/posudbas/" + id);

        JSONObject jsonPosudba = new JSONObject(rezultat);
        Posudba posudba = parsePosudba(jsonPosudba);
        return posudba;
    }


    /**
     * iz json objekta u Posudba
     * @param jsonPosudba
     * @return
     */
    public Posudba parsePosudba(JSONObject jsonPosudba) {
        try {
            Posudba posudba = new Posudba();
            posudba.setId(jsonPosudba.getString("id"));
            posudba.setIdOpreme(jsonPosudba.getString("idOpreme"));
            posudba.setBrDana(jsonPosudba.getString("brDana"));
            posudba.setProfesor(jsonPosudba.getString("profesor"));
            posudba.setStudent(jsonPosudba.getString("student"));
            posudba.setDatumPreuz(jsonPosudba.getString("datumPreuz"));

            return posudba;
        } catch (JSONException e) {
            Log.d("RUAZOSA", "problem parsing json", e);
            return null;
        }
    }

    public List<LabIthemResource> getListOfLabIthems(String s) throws JSONException {


        JSONArray arrayOfLabIthems = new JSONArray(s);

        List<LabIthemResource> list = new LinkedList<>();
        for (int i = 0; i < arrayOfLabIthems.length(); i++) {
            LabIthemResource c = parseLabIthemR((JSONObject) arrayOfLabIthems.get(i));
            list.add(c);
        }

        return list;
    }
    @Override
    public List<LabIthemResource> getListOfLabIthems() throws JSONException {


        JSONArray arrayOfLabIthems = new JSONArray(rezultat);

        List<LabIthemResource> list = new LinkedList<>();
        for (int i = 0; i < arrayOfLabIthems.length(); i++) {
            LabIthemResource c = parseLabIthemR((JSONObject) arrayOfLabIthems.get(i));
            list.add(c);
        }

        return list;
    }

    @Override
    public LabIthem getLabIthem(String id) throws JSONException {
        //callServer("http://10.0.2.2:8080/api/labIthems/" + id);

        JSONObject jsonLabItem = new JSONObject(rezultat);
        LabIthem labIthem = parseLabIthem(jsonLabItem);
        return labIthem;
    }

    /**
     * json objekt pretvara u labIthemResource (kraća verzija LabIthema)
     * @param jsonLabItem
     * @return
     */
    public LabIthemResource parseLabIthemR(JSONObject jsonLabItem) {
        try {
            LabIthemResource labItem = new LabIthemResource();
            labItem.setId(jsonLabItem.getString("id"));
            labItem.setKit(jsonLabItem.getString("kit"));
            labItem.setDescription(jsonLabItem.getString("description"));

            return labItem;
        } catch (JSONException e) {
            Log.d("PARSING", "problem parsing json", e);
            return null;
        }
    }


    /**metoda puni json objekt atributima posudbe
     *
     * @param objekt
     * @param posudba
     * @return
     */
    public JsonObject fromPosudbaToJSON(JsonObject objekt, Posudba posudba){
        objekt.addProperty("datumPreuz", posudba.getDatumPreuz());
        objekt.addProperty("idOpreme", posudba.getIdOpreme());
        objekt.addProperty("profesor", posudba.getProfesor());
        objekt.addProperty("student", posudba.getStudent());
        objekt.addProperty("brDana", posudba.getBrDana());
        return objekt;
    }


    /**
     * metoda puni json objekt atributima labItema i vraća objekt
     * @param object
     * @param item
     * @return
     */
    public JsonObject fromLabItemToJSON(JsonObject object, LabIthem item){
        object.addProperty("id", item.getId());
        object.addProperty("kit", item.getKit());
        object.addProperty("identifier", item.getIdentifier());
        object.addProperty("inventoryNumber", item.getInventoryNumber());
        object.addProperty("description", item.getDescription());
        object.addProperty("location", item.getLocation());
        object.addProperty("status", item.getStatus());
        object.addProperty("device", item.getDevice());
        object.addProperty("type", item.getType());
        object.addProperty("subtype", item.getSubtype());
        object.addProperty("number", item.getNumber());

        return object;
    }


    /**
     * Iz json objeksta u LabIthem
     * @param jsonLabItem
     * @return
     */
    public LabIthem parseLabIthem(JSONObject jsonLabItem) {
        try {
            LabIthem labItem = new LabIthem();
            labItem.setId(jsonLabItem.getString("id"));
            labItem.setKit(jsonLabItem.getString("kit"));
            labItem.setDescription(jsonLabItem.getString("description"));
            labItem.setDevice(jsonLabItem.getString("device"));
            labItem.setInventoryNumber(jsonLabItem.getString("inventoryNumber"));//???
            labItem.setStatus(jsonLabItem.getString("status"));
            labItem.setSubtype(jsonLabItem.getString("subtype"));
            labItem.setType(jsonLabItem.getString("type"));
            labItem.setIdentifier(jsonLabItem.getString("identifier"));
            labItem.setNumber(jsonLabItem.getString("number"));
            labItem.setLocation(jsonLabItem.getString("location"));

            return labItem;
        } catch (JSONException e) {
            Log.d("PARSING", "problem parsing json", e);
            return null;
        }
    }
}

