package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.samah.itdlmainversion.ShoppingNoteActivity;

/**
 * Created by samah on 21/03/2016.
 */
public class LocalDataBase extends SQLiteOpenHelper {

     static final String dataBaseName="/mnt/sdcard/ITDL/MYNotes.db";
    static final String tableName="note";
    static final String colnoteID="noteID";
    static final String colnoteContent="noteContent";
    static final String coluserID="userID";
    static final String colcreationDate="creationDate";
    static final String colisDone="isDone";
    static final String colisTextCategorized="isTextCategorized";
    static final String colnoteType="noteType";
    static final String colisActive="isActive";
    static final String colPriority ="Priority";
    static final String colisSynchronized="synchronized";
    static final String colproductCategory="productCategory";
    static final String colproductToBuy="productToBuy";
    static final String  colmeetingNoteDate="meetingNoteDate";
    static final String  colestimatedTransportTime="estimatedTransportTime";
    static final String  colmeetingTitle="meetingTitle";
    static final String  colmeetingPlace="meetingPlace";
    static final String  colmeetingAgenda="meetingAgenda";
    static final String  colprogressPercentage="progressPercentage";
    static final String  coldeadLineTitle="deadLineTitle";
    static final String  coldeadLineDate="deadLineDate";

    static final String queryCreateTableNote ="CREATE TABLE "+tableName +"( " +
                colnoteID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                coluserID +" Numeric NOT NULL," +
                colnoteContent +" TEXT ," +
                colcreationDate+" date NOT NULL," +
                colnoteType+" TEXT NOT NULL ,"+
                colproductCategory+" TEXT,"+
                colproductToBuy+" TEXT,"+
                colmeetingNoteDate+" date,"+
                colestimatedTransportTime+" TEXT ,"+
                colmeetingTitle+" TEXT,"+
                colmeetingPlace+" TEXT ,"+
                colmeetingAgenda+" TEXT ,"+
                colprogressPercentage +" INTEGER"+
                coldeadLineTitle +" TEXT ,"+
                coldeadLineDate+" date,"+
                colisDone+" INTEGER,"+
                colisTextCategorized+" INTEGER,"+
                colisActive+" INTEGER,"+
                colPriority+" TEXT,"+
                colisSynchronized+" INTEGER "+" );" ;

    public LocalDataBase(Context context) {
        super(context, dataBaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(queryCreateTableNote);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+queryCreateTableNote);
        onCreate(sqLiteDatabase);
    }


    public long InsertOrdinaryNote( OrdinaryNoteEntity note ,long userid){
        SQLiteDatabase db=this.getWritableDatabase();
        int isdone=note.isDone()? 1:0;
        int issync=note.isSynchronized()? 1:0;
        int isActive=note.isActive()? 1:0;
        int isTextcat=note.isTextCategorized()? 1:0;


        ContentValues values=new ContentValues();
        values.put(coluserID,userid);
        values.put(colnoteContent,note.getNoteContent());
        values.put(colcreationDate, String.valueOf(note.getNoteDateCreation()));
        values.put(colnoteType,note.getNoteType());
        values.put(colisDone, isdone );
        values.put(colisActive,isActive );
        values.put(colisTextCategorized, isTextcat);
        values.put(colPriority,note.getNotePriority() );
        values.put(colisSynchronized, issync);
        long result=db.insert(tableName, null, values);
        return result;
    }

    public long InsertShoppingNote( ShoppingNoteEntity  note ,long userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isdone = note.isDone() ? 1 : 0;
        int issync = note.isSynchronized() ? 1 : 0;
        int isActive = note.isActive() ? 1 : 0;
        int isTextcat = note.isTextCategorized() ? 1 : 0;


        ContentValues values = new ContentValues();
        values.put(coluserID, userid);
        values.put(colnoteType,note.getNoteType());
        values.put(colisDone, isdone );
        values.put(colisActive,isActive );
        values.put(colisTextCategorized, isTextcat);
        values.put(colPriority,note.getNotePriority() );
        values.put(colisSynchronized, issync);
        values.put(colcreationDate, String.valueOf(note.getNoteDateCreation()));
        values.put(colproductCategory,note.getProductCategory());
        values.put(colproductToBuy,note.getProductToBuy());

        long result=db.insert(tableName, null, values);


        return result;
    }

  public   long InsertMeetingNote(MeetingNoteEntity meetingNoteEntity,long userid){
        SQLiteDatabase db = this.getWritableDatabase();
        int isdone = meetingNoteEntity.isDone() ? 1 : 0;
        int issync = meetingNoteEntity.isSynchronized() ? 1 : 0;
        int isActive = meetingNoteEntity.isActive() ? 1 : 0;
        int isTextcat = meetingNoteEntity.isTextCategorized() ? 1 : 0;


        ContentValues values = new ContentValues();
        values.put(coluserID, userid);
        values.put(colnoteType,meetingNoteEntity.getNoteType());
        values.put(colisDone, isdone );
        values.put(colisActive,isActive );
        values.put(colisTextCategorized, isTextcat);
        values.put(colisSynchronized, issync);
        values.put(colcreationDate, String.valueOf(meetingNoteEntity.getNoteDateCreation()));
        values.put(colmeetingAgenda,meetingNoteEntity.getMeetingAgenda());
        values.put(colmeetingPlace,meetingNoteEntity.getMeetingPlace());
        values.put(colmeetingTitle,meetingNoteEntity.getMeetingTitle());
        values.put(colmeetingNoteDate,String .valueOf(meetingNoteEntity.getMeetingNoteDate()));
        values.put(colestimatedTransportTime,String.valueOf(meetingNoteEntity.getEstimatedTransportTime()));
        long result=db.insert(tableName, null, values);
        return result;
    }

    public   long InsertDeadlineNote(DeadlineNoteEntity  deadlineNoteEntity,long userid){
        SQLiteDatabase db = this.getWritableDatabase();
        int isdone = deadlineNoteEntity.isDone() ? 1 : 0;
        int issync = deadlineNoteEntity.isSynchronized() ? 1 : 0;
        int isActive = deadlineNoteEntity.isActive() ? 1 : 0;
        int isTextcat = deadlineNoteEntity.isTextCategorized() ? 1 : 0;

        ContentValues values = new ContentValues();
        values.put(coluserID, userid);
        values.put(colnoteType,deadlineNoteEntity.getNoteType());
        values.put(colisDone, isdone );
        values.put(colisActive,isActive );
        values.put(colisTextCategorized, isTextcat);
        values.put(colisSynchronized, issync);
        values.put(colcreationDate, String.valueOf(deadlineNoteEntity.getNoteDateCreation()));
        values.put(coldeadLineTitle,deadlineNoteEntity.getDeadLineTitle());
        values.put(coldeadLineDate,String.valueOf(deadlineNoteEntity.getDeadLineDate()));
        values.put(colprogressPercentage,String .valueOf(deadlineNoteEntity.getProgressPercentage()));
        values.put(colPriority,deadlineNoteEntity.getNotePriority());
        long result=db.insert(tableName, null, values);
        return result;
    }
}
