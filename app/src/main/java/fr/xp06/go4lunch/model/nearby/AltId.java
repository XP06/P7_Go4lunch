package fr.xp06.go4lunch.model.nearby;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AltId implements Parcelable {

    @SerializedName("place_id")
    @Expose
    private String placeId;

    protected AltId(Parcel in) {
        placeId = in.readString();
    }

    public static final Creator<AltId> CREATOR = new Creator<AltId>() {
        @Override
        public AltId createFromParcel(Parcel in) {
            return new AltId(in);
        }

        @Override
        public AltId[] newArray(int size) {
            return new AltId[size];
        }
    };

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeId);
    }
}
