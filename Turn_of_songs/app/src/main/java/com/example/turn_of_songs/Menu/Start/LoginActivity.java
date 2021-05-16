package com.example.turn_of_songs.Menu.Start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.turn_of_songs.Menu.Profile.MainActivity;
import com.example.turn_of_songs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {

    //creation of data
    private FirebaseAuth authen;
    private ProgressDialog loadingCircle;
    Intent intentProfile;

    //Data we want to get out of the design
    private Button btnLog,btnResend;
    private Toolbar toolbar;
    private TextInputLayout password, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //initialization of the firebase authentication
        authen = FirebaseAuth.getInstance();

        //initialization of the data
        btnLog = findViewById(R.id.log_btn_login);
        toolbar = findViewById(R.id.toolbar);
        password = findViewById(R.id.menu_login_password);
        email = findViewById(R.id.menu_login_usermail);
        loadingCircle = new ProgressDialog(this);
        btnResend=findViewById(R.id.log_btn_resend_email);

        //we want to hide the button to resend an email for user not to spam
        btnResend.setVisibility(View.INVISIBLE);

        //adding a event listener to btnLog
        btnLog.setOnClickListener(this);

        //doesn't work!!
        //making the toolbar invisible
        //toolbar.setVisibility(View.INVISIBLE);

    }

    //if we click on a view this function is called
    @Override
    public void onClick(View v) {
        //if the user clicked on the login button of this activity
        if(v.getId() == R.id.log_btn_login) {
            //we initialize the intent to launch the next activity
            intentProfile = new Intent(this, MainActivity.class);
            //we verify the email and the password correspond to an existing account
            checkExistingAccount(email.getEditText().getText().toString(),password.getEditText().getText().toString());
        }

    }

    private void checkExistingAccount(String email, String password){
        authen.signInWithEmailAndPassword(email,password).addOnCompleteListener(this);
    }

    //if we want to insure a task is successful or not
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        //we can hide the loading bar
        loadingCircle.dismiss();
        //if the task is a success
        //(here task = check if the account exist)
        if(task.isSuccessful()){
           /* //if the current user has verified its email
            if(authen.getCurrentUser().isEmailVerified()){
                //the user is logged in its session
                startActivity(intentProfile);
            }
            else{
                //else we make the button to resend an email visible
                btnResend.setVisibility(View.VISIBLE);
                //and we display a message explaining that we couldn't find an account matching
                Toast.makeText(this,"Confirm your email address OR send a new verification mail",Toast.LENGTH_LONG).show();
            }*/

        }
        else{
            //else we display a message explaining that we couldn't find an account matching
            Toast.makeText(this,"Sorry we couldn't find any account corresponding",Toast.LENGTH_SHORT).show();
        }
    }
}