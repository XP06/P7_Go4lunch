package fr.xp06.go4lunch.model.details;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.libraries.places.api.model.OpeningHours;

public class PlaceDetailsResponse implements Parcelable {

    private String name;
    private OpeningHours openingHours;
    private String address;
    private Uri websiteUri;
    private String phoneNumber;

    public PlaceDetailsResponse() {
    }

    protected PlaceDetailsResponse(Parcel in) {
        name = in.readString();
        openingHours = in.readParcelable(OpeningHours.class.getClassLoader());
        address = in.readString();
        websiteUri = in.readParcelable(Uri.class.getClassLoader());
        phoneNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(openingHours, flags);
        dest.writeString(address);
        dest.writeParcelable(websiteUri, flags);
        dest.writeString(phoneNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlaceDetailsResponse> CREATOR = new Creator<PlaceDetailsResponse>() {
        @Override
        public PlaceDetailsResponse createFromParcel(Parcel in) {
            return new PlaceDetailsResponse(in);
        }

        @Override
        public PlaceDetailsResponse[] newArray(int size) {
            return new PlaceDetailsResponse[size];
        }
    };

    //GETTER\\
    public String getName() {
        return name;
    }
    public OpeningHours getOpeningHours() {
        return openingHours;
    }
    public String getAddress() {
        return address;
    }
    public Uri getWebsiteUri() { return websiteUri; }
    public String getPhoneNumber() { return phoneNumber; }

    //SETTER\\
    public void setName(String name) {
        this.name = name;
    }
    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setWebsiteUri(Uri websiteUri) { this.websiteUri = websiteUri; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

}
