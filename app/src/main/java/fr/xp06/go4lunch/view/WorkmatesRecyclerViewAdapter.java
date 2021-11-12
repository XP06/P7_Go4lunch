package fr.xp06.go4lunch.view;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import fr.xp06.go4lunch.R;
import fr.xp06.go4lunch.controller.fragment.BaseFragment;
import fr.xp06.go4lunch.model.ParcelableRestaurantDetails;
import fr.xp06.go4lunch.model.details.PlaceDetailsResponse;
import fr.xp06.go4lunch.model.firestore.User;
import fr.xp06.go4lunch.model.nearby.Result;

import java.util.ArrayList;

public class WorkmatesRecyclerViewAdapter extends BaseRecyclerViewAdapterWorkmates {

    private ParcelableRestaurantDetails mParcelableRestaurantDetails;
    private ArrayList<User> usersList;
    private BaseFragment.OnListFragmentInteractionListener mListener;
    private RequestManager glide;
    private Context context;

    public WorkmatesRecyclerViewAdapter(BaseFragment.OnListFragmentInteractionListener listener,
                                        RequestManager glide,
                                        Context context) {
        this.usersList =  new ArrayList<>();
        this.mListener = listener;
        this.glide = glide;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderWorkmates holder, int position) {
        holder.user = usersList.get(position);

        holder.workmateImage.clearColorFilter();

        if (holder.user.getUserChoicePlaceId().equals("")) {
            String workmateChoice = holder.user.getUserName() + context.getString(R.string.hasnt_decided_yet);
            holder.workmateText.setText(workmateChoice);
            holder.workmateText.setTextColor(ContextCompat.getColor(context, R.color.quantum_grey));
            if (holder.user.getUserUrlImage() == null) {
                holder.workmateImage.setColorFilter(ContextCompat.getColor(context, R.color.quantum_grey));
            }
        } else {
            String workmateChoice = holder.user.getUserName() + context.getString(R.string.wants_to_eat_at) + holder.user.getUserChoiceRestaurantName() + ".";
            holder.workmateText.setText(workmateChoice);
        }

        if (holder.user.getUserUrlImage() != null) {
            glide.load(holder.user.getUserUrlImage()).apply(RequestOptions.circleCropTransform()).into(holder.workmateImage);
        }

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                boolean restaurantInThisArea = false;
                for (Result result : mParcelableRestaurantDetails.getNearbyResults()) {
                    if (result.getPlaceId().equals(holder.user.getUserChoicePlaceId())) {
                        restaurantInThisArea = true;
                        break;
                    }
                }
                if (restaurantInThisArea && !holder.user.getUserChoicePlaceId().equals("")) {
                    int j = 0;
                    Result result;
                    PlaceDetailsResponse placeDetailsResponse;
                    do {
                        result = mParcelableRestaurantDetails.getNearbyResults().get(j);
                        placeDetailsResponse = mParcelableRestaurantDetails.getPlaceDetailsResponses().get(j);
                        j++;
                    } while (!result.getPlaceId().equals(holder.user.getUserChoicePlaceId()));
                    mListener.onListFragmentInteraction(result, placeDetailsResponse);
                } else if (!restaurantInThisArea && !holder.user.getUserChoicePlaceId().equals("")) {
                    Toast.makeText(context, context.getString(R.string.this_restaurant_isnt_in_your_area), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateResources(ParcelableRestaurantDetails mParcelableRestaurantDetails,
                                ArrayList<User> usersList) {
        this.mParcelableRestaurantDetails = mParcelableRestaurantDetails;
        this.usersList.clear();
        this.usersList.addAll(usersList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

}
