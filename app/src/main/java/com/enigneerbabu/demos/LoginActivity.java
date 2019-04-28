package com.enigneerbabu.demos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private static final String EMAIL = "email";
    Context mContext;
    int RC_SIGN_IN = 101;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> mTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(mTask);
        } else {
                callbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        mContext = this;
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        printKeyHash(this);
        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        SignInButton signInButton = findViewById(R.id.sign_in_button);


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount mAccount = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(mAccount);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.fblogin_layout:
//
//                loginButton.performClick();
//
//                /*
//                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
//                startActivity(intent);*/
//
//                break;

            case R.id.login_button:
                loginButton.setReadPermissions(Arrays.asList(EMAIL));
                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        Log.d("User ID: ", "User ID: " +
                                loginResult.getAccessToken().getUserId() + "\n" +
                                "Auth Token: " + loginResult.getAccessToken().getToken());

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        // Application code
                                        try {
                                            Log.i("Response", response.toString());

                                            String email = response.getJSONObject().getString("email");
                                            String firstName = response.getJSONObject().getString("first_name");
                                            String lastName = response.getJSONObject().getString("last_name");

                                            Map<String, String> params = new HashMap<>();
                                            params.put("name", "" + firstName + " " + lastName);
                                            params.put("email", "" + email);
                                            params.put("image", "");
                                            params.put("mobile", "");
                                            params.put("loginby", "FACEBOOK");
                                         //   svaeUserData(params);

//
// String gender = response.getJSONObject().getString("gender");
//                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                            intent.putExtra("fb_firstname",firstName);
//                                            intent.putExtra("fb_lastname",lastName);
//                                            intent.putExtra("fb_email",email);
//                                            startActivity(intent);


                                            /*Profile profile = Profile.getCurrentProfile();
                                            String id = profile.getId();
                                            String link = profile.getLinkUri().toString();
                                            Log.i("Link",link);
                                            if (Profile.getCurrentProfile()!=null)
                                            {
                                                Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                                            }*/

                                            Log.i("Login" + "Email", email);
                                            Log.i("Login" + "FirstName", firstName);
                                            Log.i("Login" + "LastName", lastName);
//                                            Log.i("Login" + "Gender", gender);


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,email,first_name,last_name,gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                        // LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
                break;

        }
    }




    //Function to update UI
    public void updateUI(GoogleSignInAccount account){

        if(account != null){
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            //Uri personPhoto = account.getPhotoUrl();
            String personPhoto = String.valueOf(account.getPhotoUrl());

            Intent signInIntent = new Intent(LoginActivity.this, HomeActivity.class);
            signInIntent.putExtra("person_name",personName);
            signInIntent.putExtra("person_given_name", personGivenName);
            signInIntent.putExtra("person_family_name",personFamilyName);
            signInIntent.putExtra("person_email", personEmail);
            signInIntent.putExtra("person_id", personId);
            signInIntent.putExtra("person_photo", personPhoto);

            startActivity(signInIntent);

        }
    }

    //Function to sign in
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //Function to handle sign in result
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask){

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SIGNINFAILED", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }


    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

}
