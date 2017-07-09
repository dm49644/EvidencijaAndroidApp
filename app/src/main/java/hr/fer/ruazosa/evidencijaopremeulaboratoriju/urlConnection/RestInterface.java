package hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection;

import org.json.JSONException;

import java.util.List;

import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.LabIthem;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.Posudba;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.representations.LabIthemResource;

/**
 * Created by Domagoj on 06/07/2017.
 */

public interface RestInterface {
    public List<Posudba> getListOfPosudbas() throws JSONException;
    public Posudba getPosudba(String id) throws JSONException;

    public List<LabIthemResource> getListOfLabIthems() throws JSONException;
    public LabIthem getLabIthem(String id) throws JSONException;
}
