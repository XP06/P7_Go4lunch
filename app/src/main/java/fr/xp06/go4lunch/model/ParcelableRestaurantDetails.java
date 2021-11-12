package fr.xp06.go4lunch.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import fr.xp06.go4lunch.model.details.PlaceDetailsResponse;
import fr.xp06.go4lunch.model.nearby.Result;

import java.util.List;

public class ParcelableRestaurantDetails implements Parcelable {

    private List<Result> nearbyResults;
    private List<PlaceDetailsResponse> placeDetailsResponses;
    private List<Bitmap> mBitmapList;

    public ParcelableRestaurantDetails() {
    }

    protected ParcelableRestaurantDetails(Parcel in) {
        nearbyResults = in.createTypedArrayList(Result.CREATOR);
        placeDetailsResponses = in.createTypedArrayList(PlaceDetailsResponse.CREATOR);
        mBitmapList = in.createTypedArrayList(Bitmap.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(nearbyResults);
        dest.writeTypedList(placeDetailsResponses);
        dest.writeTypedList(mBitmapList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParcelableRestaurantDetails> CREATOR = new Creator<ParcelableRestaurantDetails>() {
        @Override
        public ParcelableRestaurantDetails createFromParcel(Parcel in) {
            return new ParcelableRestaurantDetails(in);
        }

        @Override
        public ParcelableRestaurantDetails[] newArray(int size) {
            return new ParcelableRestaurantDetails[size];
        }
    };

    //GETTER\\
    public List<Result> getNearbyResults() {
        return nearbyResults;
    }
    public List<PlaceDetailsResponse> getPlaceDetailsResponses() {
        return placeDetailsResponses;
    }
    public List<Bitmap> getBitmapList() { return mBitmapList; }

    //SETTER\\
    public void setNearbyResults(List<Result> nearbyResults) {
        this.nearbyResults = nearbyResults;
    }
    public void setPlaceDetailsResponses(List<PlaceDetailsResponse> placeDetailsResponses) { this.placeDetailsResponses = placeDetailsResponses; }
    public void setBitmapList(List<Bitmap> bitmapList) { mBitmapList = bitmapList; }

}
