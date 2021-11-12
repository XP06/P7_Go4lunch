package fr.xp06.go4lunch.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.xp06.go4lunch.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapterWorkmates extends RecyclerView.Adapter<ViewHolderWorkmates>{

    @NonNull
    @Override
    public ViewHolderWorkmates onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_workmate, parent, false);
        return new ViewHolderWorkmates(view);
    }

}
