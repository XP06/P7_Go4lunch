package fr.xp06.go4lunch.utils;

import fr.xp06.go4lunch.model.nearby.NearbyResponse;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleService {

    @GET("nearbysearch/json?")
    Observable<NearbyResponse> getNearbySearch(@Query("location") String location,
                                               @Query("radius") int radius,
                                               @Query("type") String type,
                                               @Query("key") String apiKey);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

}
