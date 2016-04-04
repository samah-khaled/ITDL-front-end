package controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.samah.itdlmainversion.MeetingNoteActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.DeadlineNoteEntity;
import model.LocalDataBase;
import model.MeetingNoteEntity;
import model.NoteEntity;
import model.OrdinaryNoteEntity;
import model.ShoppingNoteEntity;
import model.UserEntity;

/**
 * Created by Esraa on 13-Mar-16.
 */
public class NoteController {

    static final public  String ordinaryNote = "Ordinary";
    static final public String meetingNote = "Meeting";
    static final public String deadlineNote = "Deadline";
    static final public String shoppingNote = "Shopping";

    public NoteController() {
    }

    public void addOrdinaryNoteToServer(String noteContent,String priority)
    {
        UserController userController = UserController.getInstance();
      String userID=String.valueOf(userController.getCurrentUserID());

        new Connection().execute("http://2-dot-secondhelloworld-1221.appspot.com/rest/addOrdinaryNoteService",
                noteContent,userID, "addOrdinaryNoteService");
    }
    public void addShoppingNoteToServer(String productToBuy,String priority,String category){

        UserController userController = UserController.getInstance();
        String userID=String.valueOf(userController.getCurrentUserID());

        new Connection().execute("http://2-dot-secondhelloworld-1221.appspot.com/rest/addShoppingNoteService",
                productToBuy,category,userID, "addShoppingNoteService");

    }

    public void addDeadlineNoteToServer(String title,String priority,String deadlinedate_time ,int progressValue)
    {
        UserController userController = UserController.getInstance();
        String userID=String.valueOf(userController.getCurrentUserID());

        new Connection().execute("http://2-dot-secondhelloworld-1221.appspot.com/rest/addDeadLineNoteService",
                title,deadlinedate_time,String.valueOf(progressValue),userID, "addDeadLineNoteService");
    }
    public void addMeetingNoteToServer(String title, String place, String agenda, String date, String time) {
        UserController userController = UserController.getInstance();
        String userID=String.valueOf(userController.getCurrentUserID());

        new Connection().execute("http://2-dot-secondhelloworld-1221.appspot.com/rest/addMeetingNoteService",
                title,place,agenda,date,time,userID, "addMeetingNoteService");

    }
    public void addOrdinaryNoteInLoacalDB(String notecontent,String Priority,boolean issync){

        boolean isDone=false;
        boolean isActive=true;
        boolean isTextCategorized =false;

        UserController userController = UserController.getInstance();

        OrdinaryNoteEntity noteEntity =new OrdinaryNoteEntity(ordinaryNote,Priority,userController.getCurrentUserID(),
               getCurrentDate(),isDone,isActive,isTextCategorized,notecontent,issync);

        LocalDataBase localDataBase =new LocalDataBase(MyApplication.getAppContext());
     long id =  localDataBase.InsertOrdinaryNote(noteEntity, userController.getCurrentUserID());
        if (id==-1){
            Toast.makeText(MyApplication.getAppContext(), " Error while inserting ", Toast.LENGTH_LONG).show();

        }
          else{
            Toast.makeText(MyApplication.getAppContext(), " Inserted done note id is "+String.valueOf(id), Toast.LENGTH_LONG).show();
        }
    }

    public void AddShoppingNoteInLocalDB(String productToBuy,String priority,String category,boolean issync){

        boolean isDone=false;
        boolean isActive=true;
        boolean isTextCategorized =false;


        UserController userController = UserController.getInstance();

        ShoppingNoteEntity shoppingNoteEntity =new ShoppingNoteEntity(shoppingNote,priority,userController.getCurrentUserID(),
                getCurrentDate(),isDone,isActive,isTextCategorized,issync,productToBuy,category);

        LocalDataBase localDataBase =new LocalDataBase(MyApplication.getAppContext());
        long id=localDataBase.InsertShoppingNote(shoppingNoteEntity, userController.getCurrentUserID());
        if (id==-1){
            Toast.makeText(MyApplication.getAppContext(), " Error while inserting ", Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(MyApplication.getAppContext(), " Inserted done note id is "+String.valueOf(id), Toast.LENGTH_LONG).show();
        }
    }
    public void addMeetingNoteInLoacalDB(String title,String place,String agenda,String meetingNoteDate,
                                         String estimatedTransportTime,boolean issync){

        boolean isDone=false;
        boolean isActive=true;
        boolean isTextCategorized =false;

        UserController userController = UserController.getInstance();

        MeetingNoteEntity noteEntity =new MeetingNoteEntity(meetingNote,userController.getCurrentUserID(),
                getCurrentDate(),isDone,isActive,isTextCategorized,issync,title,place,agenda,
                java.sql.Date.valueOf(meetingNoteDate),Time.valueOf(estimatedTransportTime));

        LocalDataBase localDataBase =new LocalDataBase(MyApplication.getAppContext());
      long id =  localDataBase.InsertMeetingNote(noteEntity, userController.getCurrentUserID());
        if (id==-1){
            Toast.makeText(MyApplication.getAppContext(), " Error while inserting ", Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(MyApplication.getAppContext(), " Inserted done note id is "+String.valueOf(id), Toast.LENGTH_LONG).show();
        }
    }
    public void addDeadlineNoteInLoacalDB(String title,String priority,String deadlinedate_time ,int progressValue,boolean issync){

        boolean isDone=false;
        boolean isActive=true;
        boolean isTextCategorized =false;

        UserController userController = UserController.getInstance();
        DeadlineNoteEntity  noteEntity =new DeadlineNoteEntity(deadlineNote,userController.getCurrentUserID(),
                getCurrentDate(),isDone,isActive,isTextCategorized,issync,progressValue,title,Timestamp.valueOf(deadlinedate_time),priority);

        LocalDataBase localDataBase =new LocalDataBase(MyApplication.getAppContext());
        long id =  localDataBase.InsertDeadlineNote(noteEntity, userController.getCurrentUserID());
        if (id==-1){
            Toast.makeText(MyApplication.getAppContext(), " Error while inserting ", Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(MyApplication.getAppContext(), " Inserted done note id is "+String.valueOf(id), Toast.LENGTH_LONG).show();
        }
    }
    private  Timestamp getCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTimeStamp = dateFormat.format(new Date()); // Find todays date
        return Timestamp.valueOf(currentTimeStamp);
    }



    public static class Connection extends AsyncTask<String, String, String> {

        String serviceType;

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            URL url;
            serviceType = params[params.length - 1];
            String urlParameters="";

            if(serviceType.equals("addOrdinaryNoteService"))
            {

                java.util.Date date = new java.util.Date();
                boolean isDone = false;
                boolean isTextCategorized = false;

                urlParameters = "ordinaryNoteContent=" + params[1] + "&creationDate="
                        + String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
                        + isTextCategorized + "&noteType=" + ordinaryNote + "&userID=" + params[2];
            }
           else  if(serviceType.equals("addShoppingNoteService")){
                java.util.Date date = new java.util.Date();
                boolean isDone = false;
                boolean isTextCategorized = false;

                urlParameters = "productToBuy=" + params[1] + "&productCategory="+params[2]+"&creationDate="
                        + String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
                        + isTextCategorized + "&noteType=" + shoppingNote + "&userID=" + params[3];


            }
            else if(serviceType.equals("addDeadLineNoteService")){
                java.util.Date date = new java.util.Date();
                boolean isDone = false;
                boolean isTextCategorized = false;
                urlParameters = "deadLineTitle=" + params[1] + "&deadLineDate="+params[2]+"&progressPercentage="+params[3]+"&creationDate="
                        + String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
                        + isTextCategorized + "&noteType=" + deadlineNote + "&userID=" + params[4];


            }
            //title,place,agenda,date,time
            else if(serviceType.equals("addMeetingNoteService")){
                java.util.Date date = new java.util.Date();
                boolean isDone = false;
                boolean isTextCategorized = false;
                urlParameters = "meetingTitle=" + params[1] + "&meetingPlace="+params[2]+"&meetingAgenda="+params[3]+"&meetingNoteDate="
                        +params[4]+"&estimatedTransportTime=" +params[5]
                        + "&creationDate="+String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
                        + isTextCategorized + "&noteType=" + meetingNote + "&userID=" + params[6];


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
                   // return;
                }
                else if(serviceType.equals("addOrdinaryNoteService"))
                {
                    Toast.makeText(MyApplication.getAppContext(),"Ordinary note is added successfully",Toast.LENGTH_LONG).show();

                }
                else if (serviceType.equals("addShoppingNoteService")){

                    Toast.makeText(MyApplication.getAppContext(),"Shopping note is added successfully",Toast.LENGTH_LONG).show();
                }
                else if (serviceType.equals("addDeadLineNoteService")){

                    Toast.makeText(MyApplication.getAppContext(),"deadline note is added successfully",Toast.LENGTH_LONG).show();

                }
                else if (serviceType.equals("addMeetingNoteService")){

                    Toast.makeText(MyApplication.getAppContext(),"Meeting note is added successfully",Toast.LENGTH_LONG).show();


                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
