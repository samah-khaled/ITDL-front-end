package controllers;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.samah.itdlmainversion.HomeActivity;
import com.example.samah.itdlmainversion.MainActivity;
import com.example.samah.itdlmainversion.PreferenceActivity;
import com.example.samah.itdlmainversion.ViewUserInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import model.Category;
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
		try {
			String result=new CallWebService().execute("http://fci-gp-intelligent-to-do.appspot.com/rest/RegestrationService", userName,
                    email, password, gender, city, birth_date, twitterAccount, "RegistrationService").get();
			JSONObject object = new JSONObject(result);

			if(!object.has("Status") || object.getString("Status").equals("Failed")){
				Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
				return;
			}
			currentActiveUser = UserEntity.createLoginUser(result);
			currentActiveUserID = currentActiveUser.getUserId();

//			Intent homeIntent = new Intent(MyApplication.getAppContext(),
//					HomeActivity.class);
//			homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			homeIntent.putExtra("status", "Registered successfully");
//			homeIntent.putExtra("userId", object.get("userId").toString());
//			homeIntent.putExtra("serviceType", "RegistrationService");

            Intent perefernces = new Intent(MyApplication.getAppContext(),
                    PreferenceActivity.class);
            perefernces.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            perefernces.putExtra("status", "Registered successfully");
            perefernces.putExtra("userId", object.get("userId").toString());
            perefernces.putExtra("serviceType", "RegistrationService");

			Log.d("user_id ", object.get("userId").toString());
			MyApplication.getAppContext().startActivity(perefernces);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	public void Login(String email, String password) {

		try {
			String result=new CallWebService().execute(
                    "http://fci-gp-intelligent-to-do.appspot.com/rest/LoginService",
                    email, password, "LoginService").get();

			JSONObject object = new JSONObject(result);

			if(!object.has("Status") || object.get("Status").equals("Failed")){
				Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();

				Intent homeIntent = new Intent(MyApplication.getAppContext(),
						MainActivity.class);
				homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				MyApplication.getAppContext().startActivity(homeIntent);
			//return;
			}
else {
				currentActiveUser = UserEntity.createLoginUser(result);
				currentActiveUserID = currentActiveUser.getUserId();
				Intent homeIntent = new Intent(MyApplication.getAppContext(),
						HomeActivity.class);
				//System.out.println("--- " + serviceType + "IN LOGIN " + object.getString("Status"));

				homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				homeIntent.putExtra("status", "Logged in successfully");
				homeIntent.putExtra("name", object.getString("username"));
				homeIntent.putExtra("userId", object.get("userId").toString());
				homeIntent.putExtra("serviceType", "LoginService");

				MyApplication.getAppContext().startActivity(homeIntent);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void GetUserInformation(){
		System.out.print("UserID" + currentActiveUser.getUserId());
		try {
			String result=new CallWebService().execute(
                    "http://fci-gp-intelligent-to-do.appspot.com/rest/GetUserInfoService",
                String.valueOf(currentActiveUser.getUserId()), "GetUserInfoService").get();
			JSONObject object = new JSONObject(result);

			if(!object.has("Status") || object.getString("Status").equals("Failed")){
				Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
				return;
			}
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

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void UpdateProfile(String userName, String email, String password,String gender,
			String city,String birth_date,String twitterAccount) {
		String userId=String.valueOf(currentActiveUser.getUserId());
		try {
			String result=new CallWebService().execute(
                        "http://fci-gp-intelligent-to-do.appspot.com/rest/UpdateProfileService", userId, userName,
                        email, password, gender, city, birth_date, twitterAccount, "UpdateProfileService").get();
			JSONObject object = new JSONObject(result);

			if(!object.has("Status") || object.getString("Status").equals("Failed")){
				Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
				return;
			}
			Intent homeIntent = new Intent(MyApplication.getAppContext(),
					HomeActivity.class);
			homeIntent.putExtra("status", "Profile Updated successfully");
			homeIntent.putExtra("serviceType", "UpdateProfileService");

			homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getAppContext().startActivity(homeIntent);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
 public void UserPreferences(ArrayList<Category> Preferences){
    JSONArray jsonArrayPrfrnce=new JSONArray();
    for (int i=0;i<Preferences.size();i++){
        JSONObject object =new JSONObject();
        try {
            object.put("categoryName",Preferences.get(i).getCategoryName());
            object.put("initialWeight",Preferences.get(i).getCategoryPercentage());
            jsonArrayPrfrnce.put(object);

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

     Log.d("category_Chosenn", String.valueOf(jsonArrayPrfrnce));

     String result= null;
     try {

         result = new CallWebService().execute("http://secondhelloworld-1221.appspot.com/restNotes/enterInitialWeightsForOneUserService",
				 String.valueOf(UserController.getCurrentUserID()), jsonArrayPrfrnce.toString(), "enterInitialWeightsForOneUserService").get();

            Log.i("RRRRRRrresult=",result);

         if(!result.equals("added")){
             Toast.makeText(MyApplication.getAppContext(), "Error occurred", Toast.LENGTH_LONG).show();
             return;
         }

         Intent homeIntent = new Intent(MyApplication.getAppContext(),
					HomeActivity.class);
			homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			homeIntent.putExtra("status", "Registered successfully");
			homeIntent.putExtra("userId", UserController.getCurrentUserID());
			homeIntent.putExtra("serviceType", "UserPreferenceService");

     } catch (InterruptedException e) {
         e.printStackTrace();
     } catch (ExecutionException e) {
         e.printStackTrace();
     }

}

}
