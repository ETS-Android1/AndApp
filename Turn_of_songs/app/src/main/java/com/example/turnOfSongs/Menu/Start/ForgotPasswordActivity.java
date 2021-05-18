package com.example.turnOfSongs.Menu.Start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.turnOfSongs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordActivity extends AppCompatActivity implements OnCompleteListener<AuthResult>, View.OnClickListener {


    //Data we want to get out of the design
    private Button btnVerify, btnBack;
    private TextInputLayout email;

    //Data
    private FirebaseAuth authen;
    private Intent intentChangePassword;
    private String mail;
    private ProgressDialog loadingCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Initialization of the data
        btnVerify = findViewById(R.id.forgot_password_btn_verify);
        email = findViewById(R.id.forgot_password_mail);
        btnBack = findViewById(R.id.forgot_password_btn_back);
        authen = FirebaseAuth.getInstance();
        mail = email.getEditText().getText().toString();
        loadingCircle = new ProgressDialog(this);

        //adding an onclick listener to buttons
        btnVerify.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.forgot_password_btn_verify){
            intentChangePassword = new Intent(this, ChangePasswordActivity.class);
            Toast.makeText(this,"clic on verify",Toast.LENGTH_SHORT).show();
            //we verify the email and the password correspond to an existing account
            checkExistingAccount(mail);
            //here we resend a verification email to the address mail that user fill in
            authen.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //we test if the sending of email verification was successful
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "check your email for verification", Toast.LENGTH_LONG).show();
                        startActivity(intentChangePassword);
                    }
                    else {
                        //we show to the user the error we get
                        Toast.makeText(getApplicationContext(), "ERROR: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        if(v.getId() == R.id.forgot_password_btn_back){
            //go back
        }
    }


    private void checkExistingAccount(String email){
        Toast.makeText(this,"check if the account is existing",Toast.LENGTH_SHORT).show();
        //we try to create a new user with the email the user has given us
        authen.createUserWithEmailAndPassword(email,"aaaaaaa").addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        Toast.makeText(this,"try create user",Toast.LENGTH_SHORT).show();
        //if the task is a success
        //(here task = check if the email is not used)
        if(task.isSuccessful()){
            //the email is not used
            // we display a message explaining that we couldn't find an account matching
            Toast.makeText(this,"Sorry we couldn't find any account corresponding to this email address",Toast.LENGTH_SHORT).show();
            //we delete the account we just created
            deleteAccount(mail);
        }
        else {
            //(task.getException().getMessage()=="authen/email-already-in-use")
            //the email match with an account we go to the next activity to change the password
            Toast.makeText(this,"mail already in use",Toast.LENGTH_SHORT).show();

        }
       /*else{
            Toast.makeText(this,"ERROR:"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
        }*/
    }

    private void deleteAccount(String mail) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication
        AuthCredential credential = EmailAuthProvider.getCredential(mail,EmailAuthProvider.PROVIDER_ID);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //we try to delete the user we just create
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //if the user is delete
                                        if (task.isSuccessful()) {

                                        }
                                    }
                                });

                    }
                });
    }


}