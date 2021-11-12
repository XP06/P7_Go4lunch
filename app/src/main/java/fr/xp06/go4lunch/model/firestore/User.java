package fr.xp06.go4lunch.model.firestore;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import javax.annotation.Nullable;

public class User implements Parcelable {

    private String uid;
    private String userName;
    @Nullable
    private String userUrlImage;
    private String userChoicePlaceId = "";
    private String userChoiceRestaurantName = "";
    private String userChoiceRestaurantAddress = "";
    private List<String> userLike;

    public User () { }

    public User(String uid, String userName, String userUrlImage) {
        this.uid = uid;
        this.userName = userName;
        this.userUrlImage = userUrlImage;
    }

    //PARCELABLE\\
    protected User(Parcel in) {
        uid = in.readString();
        userName = in.readString();
        userUrlImage = in.readString();
        userChoicePlaceId = in.readString();
        userChoiceRestaurantName = in.readString();
        userChoiceRestaurantAddress = in.readString();
        userLike = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(userName);
        dest.writeString(userUrlImage);
        dest.writeString(userChoicePlaceId);
        dest.writeString(userChoiceRestaurantName);
        dest.writeString(userChoiceRestaurantAddress);
        dest.writeStringList(userLike);
    }

    //GETTER\\
    public String getUid() { return uid; }
    public String getUserName() { return userName; }
    public String getUserUrlImage() { return userUrlImage; }
    public String getUserChoicePlaceId() { return userChoicePlaceId; }
    public String getUserChoiceRestaurantName() { return userChoiceRestaurantName; }
    public String getUserChoiceRestaurantAddress() { return userChoiceRestaurantAddress; }
    public List<String> getUserLike() { return userLike; }

    //SETTER\\
    public void setUid(String uid) { this.uid = uid; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserUrlImage(String userUrlImage) { this.userUrlImage = userUrlImage; }
    public void setUserChoicePlaceId(String userChoicePlaceId) { this.userChoicePlaceId = userChoicePlaceId; }
    public void setUserChoiceRestaurantName(String userChoiceRestaurantName) { this.userChoiceRestaurantName = userChoiceRestaurantName; }
    public void setUserChoiceRestaurantAddress(String userChoiceRestaurantAddress) { this.userChoiceRestaurantAddress = userChoiceRestaurantAddress; }
    public void setUserLike(List<String> userLike) { this.userLike = userLike; }
}
