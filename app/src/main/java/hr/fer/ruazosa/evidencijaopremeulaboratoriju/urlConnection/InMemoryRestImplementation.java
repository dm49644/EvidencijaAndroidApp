package hr.fer.ruazosa.evidencijaopremeulaboratoriju.urlConnection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.LabIthem;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities.Posudba;
import hr.fer.ruazosa.evidencijaopremeulaboratoriju.representations.LabIthemResource;


public class InMemoryRestImplementation implements RestInterface {
    private List<Posudba> persons;


    @Override
    public List<Posudba> getListOfPosudbas() {
        return null;
    }

    @Override
    public Posudba getPosudba(String id) {
        return null;
    }

    @Override
    public List<LabIthemResource> getListOfLabIthems() {
        return null;
    }


    @Override
    public LabIthem getLabIthem(String id) {
        return null;
    }
}
