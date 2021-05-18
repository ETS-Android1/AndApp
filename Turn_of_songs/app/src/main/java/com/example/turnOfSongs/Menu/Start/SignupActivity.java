package com.example.turnOfSongs.Menu.Start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.turnOfSongs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {

    private FirebaseAuth authen;
    private ProgressDialog loadingCircle;

    //Get data out of the design
    private TextInputLayout username, password, confirmPassword, email;
    private Button btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        //initialization of the firebase authentication
        authen = FirebaseAuth.getInstance();

        //Initialization of the data
        username = findViewById(R.id.signup_username);
        password = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signup_password_confirm);
        email = findViewById(R.id.signup_email);
        btnLog = findViewById(R.id.signup_btn_log);
        loadingCircle = new ProgressDialog(this);

        //add an event listener to the button "login"
        btnLog.setOnClickListener(this);
    }

    //method that evaluate the validity of all the fields in the sign up registration form
    private boolean validateFields() {
        Boolean res = false;
        String user = username.getEditText().getText().toString();
        String mail = email.getEditText().getText().toString();
        //if the field user is empty
        if (user.isEmpty()) {
            //we launch an error message
            username.setError("can not be empty");
            res = false;
        }
        else {
            //if user is longer than 15 char
            if (user.length() > 15) {
                //we launch an error message
                username.setError("too long");
                res = false;
            }
            //if the password is under 6 char it is consider as too weak
            if(password.getEditText().getText().toString().length()<5){
                //we launch an error message
                password.setError("weak password");
                confirmPassword.setError("weak password");
                res=false;
            }
            //if the email doesn't match the valid pattern of mail
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                //we launch an error message
                email.setError("not valid");
                res = false;
            }
            //if the confirmation of the password differ from the password
            if (!((password.getEditText().getText().toString()).equals(confirmPassword.getEditText().getText().toString()))) {
                //we launch an error message
                confirmPassword.setError("password are not equals");
                res = false;
            }
            //else every fields are valid we return true
            else {
                res = true;
            }
        }
        return res;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signup_btn_log) {
            //we create an intent to launch the next activity
            Intent intentMenu = new Intent(SignupActivity.this, MenuActivity.class);
            //we launch the method that verify all of the fields
            //if the result of the method is true we start the next activity
            if (validateFields() == true) {
                //we get the new user email and password that have been verified
                String emailUser = email.getEditText().getText().toString().trim();
                String passwordUser = password.getEditText().getText().toString().trim();
                //showing the progression of the creation of the user's account
                loadingCircle.setTitle("creating an account");
                loadingCircle.setMessage("wait while your account is created");
                loadingCircle.setCanceledOnTouchOutside(true);
                loadingCircle.show();
                //we now call the function to create a new account
                CreateAccount(emailUser,passwordUser);
                startActivity(intentMenu);
            }
            else if(validateFields()==false){
                //else we launch a message explaining that something failed
                Toast.makeText(this,"Sorry we couldn't sign you up",Toast.LENGTH_SHORT).show();
            }
        }
    }

    //function that create the account from the registration form and add it to the firebase
    private void CreateAccount(String email, String password){
        authen.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        //we can hide the loading circle
        loadingCircle.dismiss();
        if(task.isSuccessful()){
            //here we send a verification email to the address mail that user fill in
            authen.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //we test if the email verification was successful
                    if(task.isSuccessful()){
                        //we show to the user that he need to verify his email
                        Toast.makeText(getApplicationContext(),"check your email for verification",Toast.LENGTH_LONG).show();
                    }
                    else{
                        //we show to the user that he need to verify his email
                        Toast.makeText(getApplicationContext(),"ERROR: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
            Toast.makeText(this, "account created",Toast.LENGTH_SHORT).show();
        }
        else{
            String errorMessage = task.getException().toString();
            //and now show the error message
            Toast.makeText(this,"ERROR: "+errorMessage,Toast.LENGTH_SHORT).show();

        }
    }


}