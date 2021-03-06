package com.saenaegi.lfree;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.annotations.Nullable;


public class MainActivity extends AppCompatActivity {

    private ImageView mainLogo;
    private SignInButton signInButton;

    // Request sign in code. Could be anything as you required.
    public static final int RequestSignInCode = 7;

    // Firebase Auth Object.
    public FirebaseAuth firebaseAuth;

    // Google API Client object.
    public GoogleApiClient googleApiClient;

    private boolean disabled;

    @Override
    public void onStart() {
        super.onStart();
        // 활동을 초기화 할 때 사용자가 현재 로그인되어 있는지 확인
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(currentUser != null) {
            Log.e("[*] TEST // ", "account.getIdToken(): "+account.getIdToken());
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull final Task<Void> task) {
                    String alertMessage;
                    if (false == task.isSuccessful()) {
                        alertMessage = "failed.";
                        alertMessage += "\r\n"+task.getException().toString();
                        Log.i("[*] TEST // ", "alertMessage: "+alertMessage);
                        disabled = true;
                    }else{
                        alertMessage = "success." ;
                        Log.i("[*] TEST // ", "alertMessage: "+alertMessage);
                    }
                }
            });
            signInButton.setVisibility(View.GONE);
            Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if(!disabled) {
                            Toast.makeText(MainActivity.this, "자동로그인!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, ChoiceActivity.class));
                            finish();
                        } else {
                            signOut();
                            Toast.makeText(MainActivity.this,"사용 중지된 회원입니다! 자동 로그아웃 됨.",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                };
            handler.sendEmptyMessageDelayed(0, 3000); //3초후 화면전환
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main);

        mainLogo = (ImageView)findViewById(R.id.main_logo);
        Animation logoMoveAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_move);
        mainLogo.startAnimation(logoMoveAnimation);

        signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("Sign in with Google");
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.res_fade_in);
        signInButton.startAnimation(fadeInAnimation);

        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();

        // Creating and Configuring Google Sign In object.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Creating and Configuring Google Api Client.
        googleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .enableAutoManage(MainActivity.this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                       //Log.e("TAG",""+"12345");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                UserSignInMethod();
            }
        });
    }


    // Sign In function Starts From Here.
    public void UserSignInMethod(){
        // Passing Google Api Client into Intent.
        Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        //Log.e("TAG",""+"1");
        startActivityForResult(AuthIntent, RequestSignInCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("TAG",""+"9");
        if (requestCode == RequestSignInCode){
            //Log.e("TAG",""+"10");
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (googleSignInResult.isSuccess()){
                //Log.e("TAG",""+"2");
                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
                FirebaseUserAuth(googleSignInAccount);
            }
        }
    }

    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        Log.e("TAG",""+"3");
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task AuthResultTask) {
                        if (AuthResultTask.isSuccessful()){
                            Log.e("TAG",""+"4");
                            // Getting Current Login user details.
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            Toast.makeText(MainActivity.this,"로그인 성공!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(MainActivity.this,"사용 중지된 회원입니다!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void signOut() {

        googleApiClient.connect();
        googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

            @Override
            public void onConnected(@Nullable Bundle bundle) {

                firebaseAuth.signOut();
                if (googleApiClient.isConnected()) {

                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {

                        @Override
                        public void onResult(@NonNull Status status) {

                            if (status.isSuccess()) {

                                Log.d("TAG", "User Logged out");

                            } else {

                            }

                            //hideProgressDialog();
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {

                Log.d("TAG", "Google API Client Connection Suspended");

                //hideProgressDialog();

                finish();
            }
        });
    }
}