package fr.xp06.go4lunch.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import fr.xp06.go4lunch.R;
import fr.xp06.go4lunch.utils.UserHelper;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.settings_user_image) ImageView userImage;
    @BindView(R.id.notification_switch) Switch notificationSwitch;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        updateUi();
    }

    //USER IMAGE\\
    private void updateUi() {
        if (this.getCurrentUser() != null) {
            if (this.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(this.getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(userImage);
            }
        }
        mSharedPreferences = getSharedPreferences("go4lunch", MODE_PRIVATE);
        boolean notificationBoolean = mSharedPreferences.getBoolean("notificationBoolean", false);
        if (notificationBoolean) {
            notificationSwitch.setChecked(true);
        }
    }

    //DELETE ACCOUNT BOUTON\\
    /**
     * Call when the user click on the delete button.
     * This function delete user information and this account in firebase.
     */
    @OnClick(R.id.settings_button_delete)
    public void onClickDeleteButton() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.popup_message_confirmation_delete_account)
                .setPositiveButton(R.string.popup_message_choice_yes, (dialogInterface, i) -> deleteUserFromFirebase())
                .setNegativeButton(R.string.popup_message_choice_no, null)
                .show();
    }

    private void deleteUserFromFirebase(){
        if (this.getCurrentUser() != null) {
            UserHelper.deleteUser(this.getCurrentUser().getUid())
                    .addOnFailureListener(this.onFailureListener());

            AuthUI.getInstance()
                    .delete(this)
                    .addOnSuccessListener(this, aVoid -> {
                        Intent intent = new Intent(SettingsActivity.this, AuthActivity.class);
                        startActivity(intent);
                        finish();
                    });
        }
    }

    //NOTIFICATION\\
    /**
     * Call when the user click on the switch.
     * If the switch is checked or unchecked a boolean is saved in shared preferences to indicate that
     * the user wants to receive notifications or not.
     */
    @OnCheckedChanged(R.id.notification_switch)
    public void onCheckedChangeListener (CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            this.setNotificationsTrueInSharedPreferences();
        } else {
            this.setNotificationsFalseInSharedPreferences();
        }
    }

    private void setNotificationsTrueInSharedPreferences() {
        mSharedPreferences.edit().putBoolean("notificationBoolean", true).apply();
    }

    private void setNotificationsFalseInSharedPreferences() {
        mSharedPreferences.edit().putBoolean("notificationBoolean", false).apply();
    }

}
