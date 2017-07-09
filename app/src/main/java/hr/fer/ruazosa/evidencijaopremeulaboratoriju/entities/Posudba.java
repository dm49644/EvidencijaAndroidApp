package hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities;



/**
 * Created by Domagoj on 05/07/2017.
 */

public class Posudba {
    private String id;
    private String idOpreme;
    private String profesor;
    private String student;
    private String datumPreuz;
    private String brDana;


    public Posudba() {
    }

    public Posudba(String idOpreme, String profesor, String student, String datumPreuz, String brDana) {
        this.idOpreme = idOpreme;
        this.profesor = profesor;
        this.student = student;
        this.datumPreuz = datumPreuz;
        this.brDana = brDana;
    }

    public String getIdOpreme() {
        return idOpreme;
    }

    public void setIdOpreme(String idOpreme) {
        this.idOpreme = idOpreme;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getDatumPreuz() {
        return datumPreuz;
    }

    public void setDatumPreuz(String datumPreuz) {
        this.datumPreuz = datumPreuz;
    }

    public String getBrDana() {
        return brDana;
    }

    public void setBrDana(String brDana) {
        this.brDana = brDana;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Posudba posudba = (Posudba) o;

        return id.equals(posudba.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return  idOpreme +", " + profesor
                + ", " + student
                + ", " + datumPreuz + ", " + brDana;
    }
}
