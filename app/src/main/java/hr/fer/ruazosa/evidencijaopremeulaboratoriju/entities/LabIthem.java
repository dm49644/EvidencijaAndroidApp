package hr.fer.ruazosa.evidencijaopremeulaboratoriju.entities;

import java.util.Set;


/**
 * Created by dorotea on 05.07.17..
 * Svaki entitet laboratorijske opreme sadrži iste atribute kao i u predlošku Excel tablice,
 * osim zaduženja koje će biti ostvareno pomoću entiteta posudba.
 * Hashcode entiteta je pomoću id-a.
 *
 *
 */
public class LabIthem {

    private String id; //glavni id
    private String identifier;

    private String inventoryNumber;
    private String kit;
    private String description;



    private String location;
    private String status; //reserved/available/unavailable

    //sljedeca 4 atributa cine identifikator
    private String device;
    private String type;
    private String subtype;
    private String number; //oznacen kao # u tablici

    @Override
    public String toString() {
        return id + "|" + kit + "|" + description;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabIthem labIthem = (LabIthem) o;

        if (!id.equals(labIthem.id)) return false;
        if (identifier != null ? !identifier.equals(labIthem.identifier) : labIthem.identifier != null)
            return false;
        if (inventoryNumber != null ? !inventoryNumber.equals(labIthem.inventoryNumber) : labIthem.inventoryNumber != null)
            return false;
        if (kit != null ? !kit.equals(labIthem.kit) : labIthem.kit != null) return false;
        if (description != null ? !description.equals(labIthem.description) : labIthem.description != null)
            return false;
        if (location != null ? !location.equals(labIthem.location) : labIthem.location != null)
            return false;
        if (status != null ? !status.equals(labIthem.status) : labIthem.status != null)
            return false;
        if (device != null ? !device.equals(labIthem.device) : labIthem.device != null)
            return false;
        if (type != null ? !type.equals(labIthem.type) : labIthem.type != null) return false;
        if (subtype != null ? !subtype.equals(labIthem.subtype) : labIthem.subtype != null)
            return false;
        return number != null ? number.equals(labIthem.number) : labIthem.number == null;

    }

    /**
     * hashCode se radi uz pomoc id-a
     * @return
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public LabIthem(String id, String identifier, String inventoryNumber, String kit, String description, String location, String status, String device, String type, String subtype, String number) {
        this.id = id;
        this.identifier = identifier;
        this.inventoryNumber = inventoryNumber;
        this.kit = kit;
        this.description = description;
        this.location = location;
        this.status = status;
        this.device = device;
        this.type = type;
        this.subtype = subtype;
        this.number = number;
    }

    public  LabIthem(){

    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getId() {
        return id;
    }

    public String getDevice() {
        return device;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getNumber() {
        return number;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public String getKit() {
        return kit;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }
}
