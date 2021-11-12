package fr.xp06.go4lunch.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.xp06.go4lunch.R;
import fr.xp06.go4lunch.view.RestaurantRecyclerViewAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RestaurantListFragment extends BaseFragment {

    private RestaurantRecyclerViewAdapter mRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantListFragment() { }

    public static RestaurantListFragment newInstance() {
        return new RestaurantListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            this.mRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(context, mListener);
            recyclerView.setAdapter(this.mRecyclerViewAdapter);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recoversData();
    }

    /**
     * Notify fragment that the data has changed.
     */
    @Override
    protected void notifyFragment() {
        if (mParcelableRestaurantDetails != null && !usersList.isEmpty()) {
            this.mRecyclerViewAdapter.updateResources(currentLat,
                    currentLng,
                    mParcelableRestaurantDetails,
                    usersList);
        }
    }

    @Override
    protected void updateWithPosition() { }

}
