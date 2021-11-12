package fr.xp06.go4lunch;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import android.content.Context;
import android.view.View;

import org.junit.Before;
import org.junit.Test;

import fr.xp06.go4lunch.controller.ProcessRestaurantDetails;
import fr.xp06.go4lunch.model.details.PlaceDetailsResponse;
import fr.xp06.go4lunch.model.nearby.Result;

public class ProcessRestaurantDetails_Test {

    private Result mResult;
    private ProcessRestaurantDetails mProcessRestaurantDetails;

    @Before
    public void setUp() {
        Double currentLat = 43.6716607;
        Double currentLng = 7.2024406;
        mResult = new Result();
        mResult.setRating(4.6);
        PlaceDetailsResponse mPlaceDetailsResponse = new PlaceDetailsResponse();
        mPlaceDetailsResponse.setAddress("328 Rte de Grenoble, 06200 Nice, France");
        mProcessRestaurantDetails = new ProcessRestaurantDetails(mResult, mPlaceDetailsResponse, mock(Context.class));
    }

    @Test
    public void restaurantName_isCorrect() {
        mResult.setName("Bootgrill BBQ");
        assertEquals("Bootgrill BBQ", mProcessRestaurantDetails.getRestaurantName());
    }

    @Test
    public void restaurantName_isCorrect_whenIsLong() {
        mResult.setName("Beach Club - Restaurant Simple Promenade des flots bleus 06700");
        assertEquals("Beach Club - Restaur...", mProcessRestaurantDetails.getRestaurantName());
    }

    @Test
    public void restaurantAddress_isCorrect() {
        assertEquals("328 Rte de Grenoble", mProcessRestaurantDetails.getRestaurantAddress());
    }

    /**@Test
    public void restaurantDistance_isCorrect() {
    Location location = new Location(43.6716607, 7.2024406);
    Geometry geometry = new Geometry(location);
    mResult.setGeometry(geometry);
    assertEquals("536" , mProcessRestaurantDetails.howFarIsThisRestaurant(parcelableRestaurantDetails.getCurrentLat(), parcelableRestaurantDetails.getCurrentLng()));
    }*/

    @Test
    public void restaurantRating1_isCorrect() {
        assertEquals(View.VISIBLE, mProcessRestaurantDetails.getRestaurantRate1());
    }
    @Test
    public void restaurantRating2_isCorrect() {
        assertEquals(View.VISIBLE, mProcessRestaurantDetails.getRestaurantRate2());
    }
    @Test
    public void restaurantRating3_isCorrect() {
        assertEquals(View.VISIBLE, mProcessRestaurantDetails.getRestaurantRate3());
    }
}