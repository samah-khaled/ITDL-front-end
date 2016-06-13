package com.example.samah.itdlmainversion;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.InetAddress;

import controllers.ApplicationService;
import controllers.UserController;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button LogInButton,SignUpButton,GoToHomeButton;
    EditText Email ,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogInButton=(Button) findViewById(R.id.buttonLogIn);
        SignUpButton=(Button) findViewById(R.id.buttonSignUp);
        GoToHomeButton= (Button) findViewById(R.id.buttonGoToHome);
        Email= (EditText)findViewById(R.id.editTextEmail);
        Password=(EditText)findViewById(R.id.editTextPassword);

        LogInButton.setOnClickListener(this);
        SignUpButton.setOnClickListener(this);
        GoToHomeButton.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v == GoToHomeButton) {

            Intent i = new Intent(getApplicationContext(), PreferenceActivity.class);
            i.putExtra("name", "Sam");
            i.putExtra("userId", "5634472569470976");
            i.putExtra("serviceType", "LoginService");
            startActivity(i);
        } else if (v == SignUpButton) {
            Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(i);
        } else if (v == LogInButton) {

            String email = Email.getText().toString();
            String pas = Password.getText().toString();
            if (email.equals(""))
                Toast.makeText(getApplicationContext(), "please Enter Your Email ", Toast.LENGTH_LONG).show();
            else if (pas.equals(""))
                Toast.makeText(getApplicationContext(), "please Enter Your Password ", Toast.LENGTH_LONG).show();
            else {
                UserController usercontrol = UserController.getInstance();

                if (usercontrol.isNetworkConnected(getApplicationContext()))
                    usercontrol.Login(email, pas);

                else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
