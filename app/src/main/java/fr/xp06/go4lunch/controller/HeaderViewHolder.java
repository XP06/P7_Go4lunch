package fr.xp06.go4lunch.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.xp06.go4lunch.R;

public class HeaderViewHolder extends BaseActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.user_image)
    ImageView userImage;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.user_name) TextView userName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.user_mail)
    TextView userMail;

    private Context context;

    /**
     * empty constructor
     */
    public HeaderViewHolder() {
    }

    /**
     * Constructor of the header of menu drawer.
     */
    public HeaderViewHolder(Context context, View view) {
        this.context = context;
        ButterKnife.bind(this, view);
    }

    protected void updateMainMenuWithUserInfo() {

        if (this.getCurrentUser() != null) {

            if (this.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(context)
                        .load(this.getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(userImage);
            }

            String email = TextUtils.isEmpty(getCurrentUser().getEmail()) ? getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();
            String name = TextUtils.isEmpty(getCurrentUser().getDisplayName()) ? getString(R.string.info_no_name_found) : this.getCurrentUser().getDisplayName();

            userMail.setText(email);
            userName.setText(name);
        }
    }

}
