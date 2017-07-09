package hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection;

import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.LabIthem;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.Posudba;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.representations.LabIthemResource;

public class HttpsRestUrlConnection implements RestInterface {
    private final String baseURL;


    public HttpsRestUrlConnection() {

        baseURL = "http://" + RestFactory.BASE_IP + ":8080/api";

    }


    @Override
    public List<Posudba> getListOfPosudbas() {
        try {
            String textURL = baseURL + "/posudbas";
            String posudbasText = loadData(textURL);
            Log.d("RUAZOSA", "GET " + textURL + " : Response: " + posudbasText);

            JSONArray arrayOfPosudbas = new JSONArray(posudbasText);

            List<Posudba> list = new LinkedList<>();
            for (int i = 0; i < arrayOfPosudbas.length(); i++) {
                Posudba c = parsePosudba((JSONObject)arrayOfPosudbas.get(i));
                list.add(c);
            }

            return list;
        } catch (JSONException e) {
            Log.d("RUAZOSA", "problem parsing json", e);
            return new LinkedList<>();
        }
    }

    @Override
    public Posudba getPosudba(String id) {
        try {
            String textURL = baseURL + "/posudbas/" + id;
            String posudbaText = loadData(textURL);
            Log.d("RUAZOSA", "GET " + textURL + " : Response: " + posudbaText);

            JSONObject jsonPosudba = new JSONObject(posudbaText);
            Posudba posudba =parsePosudba(jsonPosudba);
            return posudba;
        } catch (JSONException e) {
            Log.d("RUAZOSA", "problem parsing json", e);
            return null;
        }
    }





    public Posudba parsePosudba(JSONObject jsonPosudba) {
        try {
            Posudba posudba = new Posudba();
            posudba.setId(jsonPosudba.getString("id"));
            posudba.setIdOpreme(jsonPosudba.getString("id_opreme"));
            posudba.setBrDana(jsonPosudba.getString("br_dana"));
            posudba.setProfesor(jsonPosudba.getString("profesor"));
            posudba.setStudent(jsonPosudba.getString("student"));
            posudba.setDatumPreuz(jsonPosudba.getString("datum_preuz"));

            return posudba;
        } catch (JSONException e) {
            Log.d("RUAZOSA", "problem parsing json", e);
            return null;
        }
    }




    private String loadData(String textURL) {
        try {
            URL url = new URL(textURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() != 200) {
                return "" + connection.getResponseCode();
            }

            InputStream contentStream = connection.getInputStream();
            String text = readTextFromStream(contentStream);
            connection.disconnect();
            Log.d("RUAZOSA", "učitao sam: " + text);
            return text;
        } catch (MalformedURLException e) {
            Log.i("RUAZOSA", "Try to load: " + textURL, e);
        } catch (ProtocolException e) {
            Log.i("RUAZOSA", "Try to load: " + textURL, e);
        } catch (IOException e) {
            Log.i("RUAZOSA", "Try to load: " + textURL, e);
        } catch (RuntimeException e){
            Log.i("error", "", e);
        }
        return null;
    }

    private String readTextFromStream(InputStream contentStream)
            throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(contentStream));

        StringBuffer sb = new StringBuffer();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = reader.readLine();
        }
        String text = sb.toString();
        return text;
    }

    @Override
    public List<LabIthemResource> getListOfLabIthems() {
        try {
            String textURL = baseURL + "/labIthems";
            String labIthemsText = loadData(textURL);
            Log.d("RUAZOSA", "GET " + textURL + " : Response: " + labIthemsText);

            JSONArray arrayOfLabIthems = new JSONArray(labIthemsText);

            List<LabIthemResource> list = new LinkedList<>();
            for (int i = 0; i < arrayOfLabIthems.length(); i++) {
                LabIthemResource c = parseLabIthemR((JSONObject)arrayOfLabIthems.get(i));
                list.add(c);
            }

            return list;
        } catch (JSONException e) {
            Log.d("RUAZOSA", "problem parsing json", e);
            return new LinkedList<>();
        }
    }


    @Override
    public LabIthem getLabIthem(String id) {
        try {
            String textURL = baseURL + "/labIthems/" + id;
            String labItemText = loadData(textURL);
            Log.d("RUAZOSA", "GET " + textURL + " : Response: " + labItemText);

            JSONObject jsonLabItem = new JSONObject(labItemText);
            LabIthem labIthem =parseLabIthem(jsonLabItem);
            return labIthem;
        } catch (JSONException e) {
            Log.d("PARSING", "problem parsing json", e);
            return null;
        }    }


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

    public LabIthem parseLabIthem(JSONObject jsonLabItem) {
        try {
            LabIthem labItem = new LabIthem();
            labItem.setId(jsonLabItem.getString("id"));
            labItem.setKit(jsonLabItem.getString("kit"));
            labItem.setDescription(jsonLabItem.getString("description"));
            labItem.setDevice(jsonLabItem.getString("device"));
            labItem.setInventoryNumber(jsonLabItem.getString("inventory_number"));//???
            labItem.setStatus(jsonLabItem.getString("status"));
            labItem.setSubtype(jsonLabItem.getString("subtype"));
            labItem.setType(jsonLabItem.getString("typpe"));
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
