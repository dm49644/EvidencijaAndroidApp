package hr.fer.ruazosa.evidencijaopremeulaboratoriju.representations;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dorotea on 05.07.17..
 */

public class LabIthemResource implements Parcelable{

    private String id;
    private String kit;
    private String description;

    public LabIthemResource(){

    }

    public LabIthemResource(String id, String kit, String description) {
        this.id = id;
        this.kit = kit;
        this.description = description;
    }

    protected LabIthemResource(Parcel in) {
        id = in.readString();
        kit = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(kit);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LabIthemResource> CREATOR = new Creator<LabIthemResource>() {
        @Override
        public LabIthemResource createFromParcel(Parcel in) {
            return new LabIthemResource(in);
        }

        @Override
        public LabIthemResource[] newArray(int size) {
            return new LabIthemResource[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getKit() {
        return kit;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return id + "|" + kit + "|" + description;
    }
}
