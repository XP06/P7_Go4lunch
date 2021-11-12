package fr.xp06.go4lunch.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fr.xp06.go4lunch.R;
import fr.xp06.go4lunch.model.firestore.User;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolderWorkmates extends RecyclerView.ViewHolder {
    final View mView;
    @BindView(R.id.workmate_image) ImageView workmateImage;
    @BindView(R.id.workmate_text) TextView workmateText;
    public User user;

    ViewHolderWorkmates(View view) {
        super(view);
        mView = view;
        ButterKnife.bind(this, view);
    }
}
