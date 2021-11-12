package fr.xp06.go4lunch.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import fr.xp06.go4lunch.R;
import fr.xp06.go4lunch.model.firestore.User;
import fr.xp06.go4lunch.view.WorkmatesRestaurantRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class WorkmatesListRestaurantFragment extends Fragment {

    private ArrayList<User> listOfUserWithSameChoice;
    private static final String ARG_LIST_OF_USERS = "LISTOF_USERS";
    private WorkmatesRestaurantRecyclerViewAdapter mRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WorkmatesListRestaurantFragment() { }

    public static WorkmatesListRestaurantFragment newInstance(ArrayList<User> listOfUserWithSameChoice) {
        WorkmatesListRestaurantFragment fragment = new WorkmatesListRestaurantFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST_OF_USERS, listOfUserWithSameChoice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.listOfUserWithSameChoice = new ArrayList<>();
            this.listOfUserWithSameChoice = getArguments().getParcelableArrayList(ARG_LIST_OF_USERS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workmates_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            this.mRecyclerViewAdapter = new WorkmatesRestaurantRecyclerViewAdapter(this.listOfUserWithSameChoice, Glide.with(this), getContext());
            recyclerView.setAdapter(this.mRecyclerViewAdapter);
        }

        return view;
    }

    /**
     * Notify fragment that the data has changed.
     */
    public void notifyRecyclerView(ArrayList<User> listOfUserWithSameChoice) {
        this.listOfUserWithSameChoice.clear();
        this.listOfUserWithSameChoice.addAll(listOfUserWithSameChoice);
        this.mRecyclerViewAdapter.notifyDataSetChanged();
    }
}
