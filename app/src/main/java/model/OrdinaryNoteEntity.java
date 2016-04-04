package model;

import java.sql.Timestamp;

/**
 * Created by samah on 22/03/2016.
 */
public class OrdinaryNoteEntity extends  NoteEntity{
    private String noteContent;

    public OrdinaryNoteEntity(String noteType, String notePriority, long userId, Timestamp noteDateCreation,
                              boolean isDone, boolean isActive, boolean isTextCategorized,String noteContent,boolean isSync) {
        super(noteType, notePriority, userId, noteDateCreation, isDone, isActive, isTextCategorized,isSync);
        this.noteContent=noteContent;

    }


    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
