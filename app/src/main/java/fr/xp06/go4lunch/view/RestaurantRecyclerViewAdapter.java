package fr.xp06.go4lunch.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import fr.xp06.go4lunch.R;
import fr.xp06.go4lunch.controller.ProcessRestaurantDetails;
import fr.xp06.go4lunch.controller.fragment.BaseFragment;
import fr.xp06.go4lunch.model.ParcelableRestaurantDetails;
import fr.xp06.go4lunch.model.details.PlaceDetailsResponse;
import fr.xp06.go4lunch.model.firestore.User;
import fr.xp06.go4lunch.model.nearby.Result;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.ViewHolder> {

    private Double currentLat;
    private Double currentLng;
    private ParcelableRestaurantDetails mParcelableRestaurantDetails;
    private ArrayList<User> usersList;
    private Context context;
    private BaseFragment.OnListFragmentInteractionListener mListener;

    public RestaurantRecyclerViewAdapter(Context context,
                                         BaseFragment.OnListFragmentInteractionListener listener) {
        this.usersList = new ArrayList<>();
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.nearbyResult = mParcelableRestaurantDetails.getNearbyResults().get(position);
        holder.placeDetailsResponse = mParcelableRestaurantDetails.getPlaceDetailsResponses().get(position);
        holder.mBitmap = mParcelableRestaurantDetails.getBitmapList().get(position);

        ProcessRestaurantDetails restaurantDetails = new ProcessRestaurantDetails(holder.nearbyResult,
                holder.placeDetailsResponse, context);

        holder.restaurantName.setText(restaurantDetails.getRestaurantName());
        holder.restaurantAddress.setText(restaurantDetails.getRestaurantAddress());
        holder.restaurantOpenHours.setText(restaurantDetails.getRestaurantOpenHours());
        holder.restaurantDistance.setText(restaurantDetails.howFarIsThisRestaurant(
                currentLat,
                currentLng));
        holder.restaurantNumberOfPerson.setText(restaurantDetails.howManyPeopleChoseThisRestaurant(usersList));
        holder.restaurantNumberOfPerson.setVisibility(restaurantDetails.therePeopleWhoChoseThisRestaurant());
        if (holder.nearbyResult.getRating() != null) {
            holder.restaurantRate1.setVisibility(restaurantDetails.getRestaurantRate1());
            holder.restaurantRate2.setVisibility(restaurantDetails.getRestaurantRate2());
            holder.restaurantRate3.setVisibility(restaurantDetails.getRestaurantRate3());
        }
        holder.restaurantImage.clearColorFilter();
        if (holder.mBitmap != null) {
            holder.restaurantImage.setImageBitmap(holder.mBitmap);
        } else {
            holder.restaurantImage.setImageResource(R.drawable.baseline_restaurant_menu_24);
            holder.restaurantImage.setColorFilter(ContextCompat.getColor(context, R.color.quantum_grey));
        }

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.nearbyResult, holder.placeDetailsResponse);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mParcelableRestaurantDetails != null) {
            return mParcelableRestaurantDetails.getNearbyResults().size();
        } else {
            return 0;
        }
    }

    public void updateResources(Double currentLat,
                                Double currentLng,
                                ParcelableRestaurantDetails mParcelableRestaurantDetails,
                                ArrayList<User> usersList
    ) {
        this.currentLat = currentLat;
        this.currentLng = currentLng;
        this.mParcelableRestaurantDetails = mParcelableRestaurantDetails;
        this.usersList.clear();
        this.usersList.addAll(usersList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.restaurant_name) TextView restaurantName;
        @BindView(R.id.restaurant_address) TextView restaurantAddress;
        @BindView(R.id.restaurant_open_hours) TextView restaurantOpenHours;
        @BindView(R.id.restaurant_distance) TextView restaurantDistance;
        @BindView(R.id.restaurant_number_of_person) TextView restaurantNumberOfPerson;
        @BindView(R.id.restaurant_rate_1) ImageView restaurantRate1;
        @BindView(R.id.restaurant_rate_2) ImageView restaurantRate2;
        @BindView(R.id.restaurant_rate_3) ImageView restaurantRate3;
        @BindView(R.id.restaurant_image) ImageView restaurantImage;
        public Result nearbyResult;
        public PlaceDetailsResponse placeDetailsResponse;
        public Bitmap mBitmap;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }

}
