package com.nns.youcan.Views.profile;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nns.youcan.Models.User;
import com.nns.youcan.R;
import com.nns.youcan.ViewModel.UsersFireStoreViewModel;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;


public class AccountProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE =100 ;
    private CircularImageView profileImage;
    private TextInputEditText profileUserName;
    private TextInputEditText profileUserPassword;
    private TextInputEditText profileUserPhoneNumber;
    private TextInputEditText profileUserHeight;
    private TextInputEditText profileUserWeight;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private Uri selectedImageUri;
    UsersFireStoreViewModel fireStoreViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImage=findViewById(R.id.profile__image);
        profileUserName=findViewById(R.id.user_name_profile);
        profileUserPhoneNumber=findViewById(R.id.phone_number_profile);
        profileUserPassword=findViewById(R.id.password_profile);
        profileUserHeight=findViewById(R.id.user_height_profile);
        profileUserWeight=findViewById(R.id.user_weight_profile);
        maleRadioButton=findViewById(R.id.male_profile_RB);
        femaleRadioButton=findViewById(R.id.female_profile_RB);
        fireStoreViewModel=new UsersFireStoreViewModel();
        MutableLiveData<User>liveData=fireStoreViewModel.getCurrentUserInformation();
        liveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                setUserData(user.getUserImage(),
                        user.getUserName(),
                        user.getUserPhoneNumber(),
                        user.getUserPassword(),
                        user.getUserHeight(),
                        user.getUserWeight(),
                        user.getUserGender());
            }
        });
    }

    private void setUserData(String userImage, String userName, String userPhoneNumber, String userPassword, String userHeight, String userWeight, String userGender) {
       if (userImage!=null&&!userImage.equals("")){
           Picasso.get().load(userImage).into(profileImage);
       }
        if (userName!=null&&!userName.equals("")){
            profileUserName.setText(userName);
        }
        if (userPhoneNumber!=null&&!userPhoneNumber.equals("")){
            profileUserPhoneNumber.setText(userPhoneNumber);
        }
        if (userPassword!=null&&!userPassword.equals("")){
            profileUserPassword.setText(userPassword);
        }
        if (userHeight!=null&&!userHeight.equals("")){
            profileUserHeight.setText(userHeight);
        }
        if (userWeight!=null&&!userWeight.equals("")){
            profileUserWeight.setText(userWeight);
        }
        if (userGender!=null&&!userGender.equals("")){
            if (userGender.equals("male")){
                maleRadioButton.setChecked(true);
            }else {
                femaleRadioButton.setChecked(true);
            }
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE&&resultCode==RESULT_OK){
            selectedImageUri = data.getData();
            profileImage.setImageURI(selectedImageUri);
        }
    }

    public void goToHome(View view) {
        finish();
    }

    public void editProfileImage(View view) {
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_IMAGE);
    }

    public void updateUserInformation(View view) {
        User user=new User();
        findViewById(R.id.update_profile_progress).setVisibility(View.VISIBLE);
        user.setUserName(profileUserName.getText().toString());
        user.setUserPhoneNumber(profileUserPhoneNumber.getText().toString());
        user.setUserPassword(profileUserPassword.getText().toString());
        user.setUserHeight(profileUserHeight.getText().toString());
        user.setUserWeight(profileUserWeight.getText().toString());
        if (maleRadioButton.isChecked()){
            user.setUserGender("male");
        }else if (femaleRadioButton.isChecked()){
            user.setUserGender("female");

        }
        MutableLiveData<String>liveData=fireStoreViewModel.updateUserInfo(profileImage,user);
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) { findViewById(R.id.update_profile_progress).setVisibility(View.GONE);
            }
        });

    }





}

