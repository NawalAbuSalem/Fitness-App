package com.nns.youcan.ViewModel;



import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nns.youcan.Models.MyGoal;
import com.nns.youcan.Models.User;
import java.io.ByteArrayOutputStream;

public class UsersFireStoreViewModel {
    public static final String LOGIN_SUCCESS = "success login";
    public static final String INVALID_PASSWORD = "invalid password";
    public static final String ACCOUNT_IS_NOT_EXIST = "account is not exist";
    private static final String USERS_COLLECTION="Users";
    private static final String USER_PHONE_NUMBER="userPhoneNumber";
    private static final String USER_PASSWORD="userPassword";
    private static final String USER_ID="userId";
    private static final String MY_GOAL_COLLECTION ="MyGoal" ;
    public static final String OPERATION_SUCCESS = "operation success";
    private static final String  GOAL_CALORIES = "calories";
    private static final String  GOAL_CARBS = "carbs";
    private static final String  GOAL_FAT = "fat";
    private static final String  GOAL_PROTEIN = "protein";
    private static final String  GOAL_WEIGHT = "goalWeight";
    private static final String  CURRENT_WEIGHT = "currentWeight";
    private static final String GOAL_WATER_AMOUNT = "goalWaterAmount";
    private static final String GOAL_DISTANCE = "goalDistance";
    private static final String HEIGHT = "height";
    public static final String REGISTRATION_SUCCESS ="registration success" ;
    private static final String USER_NAME ="userName" ;
    private static final String USER_GENDER = "userGender";
    private static final String USER_IMAGE = "userImage";
    private FirebaseUser currentUser ;
    private FirebaseFirestore db ;
    private MutableLiveData<User>userInformationLiveData;
    private MutableLiveData<MyGoal>myGoalLiveData;
    public UsersFireStoreViewModel() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        userInformationLiveData=new MutableLiveData<>();
        myGoalLiveData=new MutableLiveData<>();
    }
   public MutableLiveData<String> manualLogin(String phoneNumber, String password){
       MutableLiveData<String>liveData=new MutableLiveData<>();
       db.collection(USERS_COLLECTION)
               .whereEqualTo(USER_PHONE_NUMBER, phoneNumber)
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       boolean findAccount=false;
                       if (task.isSuccessful()) {
                           if (!task.getResult().isEmpty()){
                               for (QueryDocumentSnapshot document : task.getResult()) {
                                   String AccountPassword= (String) document.get(USER_PASSWORD);
                                   if (AccountPassword.equals(password)){
                                      findAccount=true;
                                       liveData.setValue(LOGIN_SUCCESS);
                                   }
                               }
                               if (!findAccount){
                                   liveData.setValue(INVALID_PASSWORD);
                               }
                           }else {
                               liveData.setValue(ACCOUNT_IS_NOT_EXIST);
                           }

                       }
                   }
               });
       return liveData;

   }
   public MutableLiveData<String> RegistrationBySocialMedia(){
       MutableLiveData<String>liveData=new MutableLiveData<>();
       currentUser = FirebaseAuth.getInstance().getCurrentUser();
       db.collection(USERS_COLLECTION)
               .whereEqualTo(USER_ID, currentUser.getUid())
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           if (!task.getResult().isEmpty()){
                               liveData.setValue(LOGIN_SUCCESS);
                           }else {
                               User user=new User();
                               user.setUserImage(currentUser.getPhotoUrl().toString()+"?height=1000");
                               user.setUserName(currentUser.getDisplayName());
                               user.setUserPhoneNumber(currentUser.getPhoneNumber());
                               user.setUserPassword(getRandomPassword(8));
                               user.setUserId(currentUser.getUid());
                               db.collection(USERS_COLLECTION).document(currentUser.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       liveData.setValue(REGISTRATION_SUCCESS);
                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       liveData.setValue(e.getMessage());
                                   }
                               });
                           }

                       }
                   }
               });
       return liveData;


}
    public MutableLiveData<String> addNewUser(User user){
        MutableLiveData<String>liveData=new MutableLiveData<>();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setUserPassword(getRandomPassword(8));
        user.setUserId(currentUser.getUid());
        if (currentUser!=null) {
            db.collection(USERS_COLLECTION).document(currentUser.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    liveData.setValue(LOGIN_SUCCESS);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   liveData.setValue(e.getMessage());
                }
            });
        }
        return liveData;


    }
    public MutableLiveData<Boolean> isPhoneNumberIsExist(String phoneNumber){
        MutableLiveData<Boolean>liveData=new MutableLiveData<>();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection(USERS_COLLECTION)
                .whereEqualTo(USER_PHONE_NUMBER, phoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()){
                              liveData.setValue(true);
                            }else {
                                liveData.setValue(false);
                            }

                        }
                    }
                });
        return liveData;


    }
    public MutableLiveData<String> addDefaultGoal(String currentWeight, String goalWeight,String height){
        db.collection(USERS_COLLECTION).document(currentUser.getUid()).update(HEIGHT,height);
        db.collection(USERS_COLLECTION).document(currentUser.getUid()).update(CURRENT_WEIGHT,currentWeight);
        MyGoal myGoal=new MyGoal(currentUser.getUid(),"2000","405","50","50",goalWeight,"5","2000");
        MutableLiveData<String>liveData=new MutableLiveData<>();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser!=null) {
            db.collection(MY_GOAL_COLLECTION).document(currentUser.getUid()).set(myGoal).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    liveData.setValue(OPERATION_SUCCESS);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    liveData.setValue(e.getMessage());
                }
            });
        }
        return liveData;
    }
    public MutableLiveData<MyGoal> getMyGoal(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        MyGoal myGoal=new MyGoal();
        db.collection(MY_GOAL_COLLECTION)
                .whereEqualTo(USER_ID, currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                   myGoal.setCalories((String) document.get(GOAL_CALORIES));
                                   myGoal.setCarbs((String)document.get(GOAL_CARBS));
                                   myGoal.setFat((String)document.get(GOAL_FAT));
                                   myGoal.setProtein((String)document.get(GOAL_PROTEIN));
                                   myGoal.setGoalWeight((String)document.get(GOAL_WEIGHT));
                                   myGoal.setGoalDistance((String)document.get(GOAL_DISTANCE));
                                   myGoal.setGoalWaterAmount((String)document.get(GOAL_WATER_AMOUNT));
                                }
                                myGoalLiveData.setValue(myGoal);
                            }
                        }
                    }
                });
        return myGoalLiveData;
    }
    private  String getRandomPassword(int count) {
        String alphaString = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*alphaString.length());
            builder.append(alphaString.charAt(character));
        }
        return builder.toString();
    }
    public MutableLiveData<User> getCurrentUserInformation(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User user=new User();
        db.collection(USERS_COLLECTION)
                .whereEqualTo(USER_ID, currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    user.setUserImage((String) document.get(USER_IMAGE));
                                    user.setUserName((String) document.get(USER_NAME));
                                    user.setUserPhoneNumber((String) document.get(USER_PHONE_NUMBER));
                                    user.setUserPassword((String) document.get(USER_PASSWORD));
                                    user.setUserHeight((String) document.get(HEIGHT));
                                    user.setUserGender((String) document.get(USER_GENDER));
                                    user.setUserWeight((String) document.get(CURRENT_WEIGHT) );
                                }
                                userInformationLiveData.setValue(user);
                            }
                        }
                    }
                });
        return userInformationLiveData;
    }
    public MutableLiveData<String> updateUserInfo(ImageView profileImage,User user){
        MutableLiveData<String>liveData=new MutableLiveData<>();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection(USERS_COLLECTION).document(currentUser.getUid()).update(USER_PHONE_NUMBER,user.getUserPhoneNumber());
        db.collection(USERS_COLLECTION).document(currentUser.getUid()).update(USER_NAME,user.getUserName());
        db.collection(USERS_COLLECTION).document(currentUser.getUid()).update(USER_PASSWORD,user.getUserPassword());
        db.collection(USERS_COLLECTION).document(currentUser.getUid()).update(USER_GENDER,user.getUserGender());
        db.collection(USERS_COLLECTION).document(currentUser.getUid()).update(HEIGHT,user.getUserHeight());
        db.collection(USERS_COLLECTION).document(currentUser.getUid()).update(CURRENT_WEIGHT,user.getUserWeight());
        StorageReference reference= FirebaseStorage.getInstance().getReference().child(currentUser.getUid());
        // Get the data from an ImageView as bytes
        profileImage.setDrawingCacheEnabled(true);
        profileImage.buildDrawingCache();
        if (profileImage.getDrawable()!=null){
            Bitmap bitmap = ((BitmapDrawable) profileImage.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
            byte[] data = baos.toByteArray();
            reference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            db.collection(USERS_COLLECTION).document(currentUser.getUid()).update(USER_IMAGE,uri.toString());
                            liveData.setValue(OPERATION_SUCCESS);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    liveData.setValue(e.toString());

                }
            });
        }

        return liveData;
    }
    public void updateCurrentWeight(String currentWeight){
        db.collection(USERS_COLLECTION).document(currentUser.getUid()).update(CURRENT_WEIGHT,currentWeight);
    }
    public void updateGoalWeight(String goalWeight){
        db.collection(MY_GOAL_COLLECTION).document(currentUser.getUid()).update(GOAL_WEIGHT,goalWeight);
    }
    public void updateMacro(String calories,String fat,String protein,String carbs){
        db.collection(MY_GOAL_COLLECTION).document(currentUser.getUid()).update(GOAL_CALORIES,calories);
        db.collection(MY_GOAL_COLLECTION).document(currentUser.getUid()).update(GOAL_FAT,fat);
        db.collection(MY_GOAL_COLLECTION).document(currentUser.getUid()).update(GOAL_PROTEIN,protein);
        db.collection(MY_GOAL_COLLECTION).document(currentUser.getUid()).update(GOAL_CARBS,carbs);
    }
    public void updateWaterAmount(String waterAmount){
        db.collection(MY_GOAL_COLLECTION).document(currentUser.getUid()).update(GOAL_WATER_AMOUNT,waterAmount);
    }
    public void updateDistance(String distance){
        db.collection(MY_GOAL_COLLECTION).document(currentUser.getUid()).update(GOAL_DISTANCE,distance);
    }


}
