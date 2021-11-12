package fr.xp06.go4lunch.controller;

import android.content.Context;
import android.location.Location;
import android.view.View;

import com.google.android.libraries.places.api.model.DayOfWeek;
import fr.xp06.go4lunch.R;
import fr.xp06.go4lunch.model.details.PlaceDetailsResponse;
import fr.xp06.go4lunch.model.firestore.User;
import fr.xp06.go4lunch.model.nearby.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ProcessRestaurantDetails {

    private Result mResult;
    private PlaceDetailsResponse mPlaceDetailsResponse;
    private Context context;
    private int hours;
    private String hoursString, minutesString;
    private int howManyPeople = 0;

    /**
     * Constructor of ProcessRestaurantDetails.
     * @param nearbyResult Restaurant from nearby api.
     * @param placeDetailsResponse Restaurant details from PlaceDetails.
     * @param context Context of application.
     */
    public ProcessRestaurantDetails(Result nearbyResult, PlaceDetailsResponse placeDetailsResponse, Context context) {
        this.mResult = nearbyResult;
        this.mPlaceDetailsResponse = placeDetailsResponse;
        this.context = context;
    }

    /**
     * This method return the name of the restaurant. If the length of the name if too tall, he is cut.
     * @return The name of restaurant.
     */
    public String getRestaurantName() {
        if (mResult.getName().length() >= 20) {
            return mResult.getName().substring(0, 20) + "...";
        } else {
            return mResult.getName();
        }
    }

    /**
     * This method get the address of restaurant.
     * @return Return the address without the city.
     */
    public String getRestaurantAddress() {
        String address = mPlaceDetailsResponse.getAddress();
        return address.substring(0, address.indexOf(","));
    }

    /**
     * This method get schedules of the restaurant.
     * A restaurant can be close, open 24/7 or open with a closing hour.
     * A restaurant can get no schedules define.
     */
    public String getRestaurantOpenHours() {
        if (mPlaceDetailsResponse.getOpeningHours() != null) {
            if (mResult.getOpeningHours().getOpenNow()) {
                if (mPlaceDetailsResponse.getOpeningHours().getPeriods().size() == 1) {
                    return context.getString(R.string.open_24_7);
                } else {
                    Calendar calendar = Calendar.getInstance();
                    int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
                    int currentHours = calendar.get(Calendar.HOUR_OF_DAY);

                    SimpleDateFormat rawFormatHours = new SimpleDateFormat("hhmm", Locale.getDefault());
                    SimpleDateFormat newFormatHours = new SimpleDateFormat(context.getString(R.string.pattern), Locale.getDefault());

                    String day = null;
                    switch (currentDay) {
                        case 0:
                            day = "SUNDAY";
                            break;
                        case 1:
                            day = "MONDAY";
                            break;
                        case 2:
                            day = "TUESDAY";
                            break;
                        case 3:
                            day = "WEDNESDAY";
                            break;
                        case 4:
                            day = "THURSDAY";
                            break;
                        case 5:
                            day = "FRIDAY";
                            break;
                        case 6:
                            day = "SATURDAY";
                            break;
                    }

                    int i = 0;
                    boolean bDay = false;
                    DayOfWeek dayFound;
                    do {
                        dayFound = Objects.requireNonNull(mPlaceDetailsResponse.getOpeningHours().getPeriods().get(i).getClose()).getDay();
                        if (day != null) {
                            if (!day.equals(dayFound.toString())) {
                                bDay = true;
                                if (currentHours > hours) {
                                    i++;
                                }
                                retrievesHoursAndMinutes(i);
                            } else {
                                i++;
                            }
                        }
                    } while (!bDay);

                    Date date = null;
                    try {
                        date = rawFormatHours.parse(hoursString + minutesString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String newHoursAndMinutes = newFormatHours.format(date);
                    return context.getString(R.string.open_until) + newHoursAndMinutes;
                }
            } else {
                return context.getString(R.string.currently_closed);
            }
        } else {
            return context.getString(R.string.no_schedules_defined);
        }
    }

    private void retrievesHoursAndMinutes(int i) {
        hours = Objects.requireNonNull(mPlaceDetailsResponse.getOpeningHours().getPeriods().get(i).getClose()).getTime().getHours();
        int minutes = Objects.requireNonNull(mPlaceDetailsResponse.getOpeningHours().getPeriods().get(i).getClose()).getTime().getMinutes();
        SimpleDateFormat formatHours = new SimpleDateFormat("HH", Locale.getDefault());
        SimpleDateFormat formatMinutes = new SimpleDateFormat("mm", Locale.getDefault());
        try {
            Date dateHour = formatHours.parse(String.valueOf(hours));
            Date dateMinute = formatMinutes.parse(String.valueOf(minutes));
            hoursString = formatHours.format(dateHour);
            minutesString = formatMinutes.format(dateMinute);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method calculate the distance between the user and the restaurant.
     * @return The distance found.
     */
    public String howFarIsThisRestaurant(Double currentLat, Double currentLng) {
        Location myLocation = new Location("My location");
        myLocation.setLatitude(currentLat);
        myLocation.setLongitude(currentLng);

        Location restaurantLocation = new Location("Restaurant location");
        restaurantLocation.setLatitude(mResult.getGeometry().getLocation().getLat());
        restaurantLocation.setLongitude(mResult.getGeometry().getLocation().getLng());

        return Math.round(myLocation.distanceTo(restaurantLocation)) + "m";
    }

    /**
     * This method calculate how many people chose this restaurant.
     * @param usersList List of workmates.
     * @return How many people chose this restaurant.
     */
    public String howManyPeopleChoseThisRestaurant(ArrayList<User> usersList) {
        for (User user : usersList) {
            if (user.getUserChoicePlaceId().equals(mResult.getPlaceId())) {
                howManyPeople++;
            }
        }
        return "(" + howManyPeople + ")";
    }

    /**
     * If one or many workmates chose this restaurant, the number is visible.
     */
    public int therePeopleWhoChoseThisRestaurant() {
        if (howManyPeople != 0) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    /**
     * If the restaurant have a rate egal or more of 2, a star is add.
     */
    public int getRestaurantRate1() {
        if (mResult.getRating() >= 2) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    /**
     * If the restaurant have a rate egal or more of 3, a star is add.
     */
    public int getRestaurantRate2() {
        if (mResult.getRating() >= 3) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    /**
     * If the restaurant have a rate egal or more of 4, a star is add.
     */
    public int getRestaurantRate3() {
        if (mResult.getRating() >= 4) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

}
