package com.example.samah.itdlmainversion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import controllers.UserController;

public class HomeActivity extends ActionBarActivity {

    TextView ShowTextView;
    Button UpdateProfile,SignOut;
    Button addNoteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle extras = getIntent().getExtras();

        String status = extras.getString("status");
        String serviceType =extras.getString("serviceType");

        String name = "",welcome="Hello",text="";

        if(serviceType.equals("LoginService")){
            name = extras.getString("name");

            welcome = "Welcome " + name;
            String id=extras.getString("userId");
            text = status + " ... " + welcome+"  , your id is "+	id;
        }
        else if(serviceType.equals("RegistrationService")){
            String id=extras.getString("userId");
            text = status + " ... " + welcome+"  , your id is "+	id;
        }
        else if (serviceType.equals("UpdateProfileService")){
            text = status;
        }
        ShowTextView = (TextView) findViewById(R.id.ShowText);

        ShowTextView.setText(text);
        UpdateProfile = (Button) findViewById(R.id.UpdateProfile);
        SignOut = (Button) findViewById(R.id.SignOut);

        UpdateProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                UserController usercontrol = UserController.getInstance();
                usercontrol.GetUserInformation();

            }
        });

        SignOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                UserController usercontrol = UserController.getInstance();
                usercontrol.SignOut();
            }
        });

        addNoteBtn = (Button) findViewById(R.id.buttonAddNote);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                startActivity(intent);
            }
        });

}
}
