package model;

import java.sql.Timestamp;

/**
 * Created by samah on 04/04/2016.
 */
public class DeadlineNoteEntity extends NoteEntity{

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getDeadLineTitle() {
        return deadLineTitle;
    }

    public void setDeadLineTitle(String deadLineTitle) {
        this.deadLineTitle = deadLineTitle;
    }

    public Timestamp getDeadLineDate() {
        return deadLineDate;
    }

    public void setDeadLineDate(Timestamp deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    private int progressPercentage;
    private String deadLineTitle;
    private Timestamp deadLineDate;

    public DeadlineNoteEntity(String noteType, long userId, Timestamp noteDateCreation, boolean isDone,
                              boolean isActive, boolean isTextCategorized, boolean isSynchronized,
                              int progressPercentage,String deadLineTitle ,Timestamp deadLineDate,String priority) {
        super( noteType ,  priority,  userId , noteDateCreation, isDone , isActive, isTextCategorized, isSynchronized);

        this.progressPercentage=progressPercentage;
        this.deadLineTitle=deadLineTitle;
        this.deadLineDate=deadLineDate;
    }

}
