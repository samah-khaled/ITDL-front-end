package controllers;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.example.samah.itdlmainversion.HomeActivity;
import com.example.samah.itdlmainversion.MainActivity;
import com.example.samah.itdlmainversion.ViewUserInfoActivity;

import model.UserEntity;

public class UserController {

	private static UserEntity currentActiveUser;
	private static UserController userController;
	private static long currentActiveUserID;

	public static long getCurrentUserID() {

		return currentActiveUserID;
	}
	public static UserController getInstance() {
		if (userController == null)
		{
			userController = new UserController();


		}

		return userController;
	}

	public void signUp(String userName, String email, String password,String gender,
			String city,String birth_date,String twitterAccount) {
		new Connection().execute(
				"http://fci-gp-intelligent-to-do.appspot.com/rest/RegestrationService", userName,
				email, password,gender,city,birth_date,twitterAccount, "RegistrationService");
	}
	
	public void Login(String email, String password) {
		new Connection().execute(
				"http://fci-gp-intelligent-to-do.appspot.com/rest/LoginService",
				email, password, "LoginService");
	}
	
	public void GetUserInformation(){
		System.out.print("UserID"+currentActiveUser.getUserId());
		new Connection().execute(
				"http://fci-gp-intelligent-to-do.appspot.com/rest/GetUserInfoService",
			String.valueOf(currentActiveUser.getUserId()), "GetUserInfoService");
	}
	
	public void UpdateProfile(String userName, String email, String password,String gender,
			String city,String birth_date,String twitterAccount) {
		String userId=String.valueOf(currentActiveUser.getUserId());
		new Connection().execute(
				"http://fci-gp-intelligent-to-do.appspot.com/rest/UpdateProfileService", userId, userName,
				email, password, gender, city, birth_date, twitterAccount, "UpdateProfileService");
	}
	public void SignOut(){
		currentActiveUser=null;
		userController=null;
		Intent mainIntent = new Intent(MyApplication.getAppContext(),
				MainActivity.class);
		mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		MyApplication.getAppContext().startActivity(mainIntent);

		
	}

	public  boolean isNetworkConnected(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = connectivity.getActiveNetworkInfo();
		if(nInfo!=null && nInfo.isConnected()) {

			return true;
		}
		else{
			return false;
		}
	}




	public static class Connection extends AsyncTask<String, String, String> {

		String serviceType;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			URL url;
			serviceType = params[params.length - 1];
			String urlParameters="";
			if (serviceType.equals("LoginService"))
				urlParameters = "useremail=" + params[1] + "&userpassword=" + params[2];
			
			else if(serviceType.equals("RegistrationService"))
				urlParameters = "username=" + params[1] + "&useremail=" + params[2]
						+ "&userpassword=" + params[3]+"&gender="+params[4] +"&city="+params[5]
								+"&birth_date="+params[6]+"&Twitter_Account="+params[7];
			
			else if (serviceType.equals("GetUserInfoService")){
				urlParameters="userId="+params[1];
				
			}
			else if(serviceType.equals("UpdateProfileService")){
				urlParameters="userId="+params[1]+"&username=" + params[2] + "&useremail=" + params[3]
								+ "&userpassword=" + params[4]+"&gender="+params[5] +"&city="+params[6]
								+"&birth_date="+params[7]+"&Twitter_Account="+params[8];

				
			}
			HttpURLConnection connection;
			try {
				url = new URL(params[0]);

				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setInstanceFollowRedirects(false);
				connection.setRequestMethod("POST");
				connection.setConnectTimeout(60000); // 60 Seconds
				connection.setReadTimeout(60000); // 60 Seconds

				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				OutputStreamWriter writer = new OutputStreamWriter(
						connection.getOutputStream());
				writer.write(urlParameters);
				writer.flush();
				String line, retJson = "";
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));

				while ((line = reader.readLine()) != null) {
					retJson += line;
				}
				return retJson;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				JSONObject object = new JSONObject(result);
				
				if(!object.has("Status") || object.getString("Status").equals("Failed")){
					Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
					return;
				}
				
				if (serviceType.equals("LoginService")) {
					
					currentActiveUser = UserEntity.createLoginUser(result);
					currentActiveUserID = currentActiveUser.getUserId();
					Intent homeIntent = new Intent(MyApplication.getAppContext(),
							HomeActivity.class);
					System.out.println("--- " + serviceType + "IN LOGIN " + object.getString("Status"));
					
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					/* here you should initialize user entity */
					homeIntent.putExtra("status", "Logged in successfully");
					homeIntent.putExtra("name", object.getString("username"));
					homeIntent.putExtra("userId", object.get("userId").toString());
					homeIntent.putExtra("serviceType", "LoginService");

					MyApplication.getAppContext().startActivity(homeIntent);
					
				}
				else if(serviceType.equals("RegistrationService")){
					//Application AP= new Application(this);
					currentActiveUser = UserEntity.createLoginUser(result);
					currentActiveUserID = currentActiveUser.getUserId();

					Intent homeIntent = new Intent(MyApplication.getAppContext(),
							HomeActivity.class);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					homeIntent.putExtra("status", "Registered successfully");
					homeIntent.putExtra("userId", object.get("userId").toString());
					homeIntent.putExtra("serviceType", "RegistrationService");

					Log.d("user_id ",object.get("userId").toString());
					MyApplication.getAppContext().startActivity(homeIntent);
				}
				else if(serviceType.equals("GetUserInfoService")){
					Intent viewIntent = new Intent(MyApplication.getAppContext(),
							ViewUserInfoActivity.class);
					
					viewIntent.putExtra("username", object.get("username").toString());
					viewIntent.putExtra("useremail", object.get("useremail").toString());
					viewIntent.putExtra("userpassword", object.get("userpassword").toString());
					viewIntent.putExtra("usergender", object.get("usergender").toString());
					viewIntent.putExtra("usercity", object.get("usercity").toString());
					viewIntent.putExtra("usertwiterAcc", object.get("usertwiterAcc").toString());
					viewIntent.putExtra("userbirthdate", object.get("userbirthdate").toString());

					viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					MyApplication.getAppContext().startActivity(viewIntent);

				}
				
				else if(serviceType.equals("UpdateProfileService")){
					Intent homeIntent = new Intent(MyApplication.getAppContext(),
							HomeActivity.class);
					homeIntent.putExtra("status", "Profile Updated successfully");
					homeIntent.putExtra("serviceType", "UpdateProfileService");

					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					MyApplication.getAppContext().startActivity(homeIntent);

				}

					
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
