package model;

import java.sql.Timestamp;

/**
 * Created by samah on 21/03/2016.
 */
public abstract class NoteEntity {

    protected long noteId;
    protected  long userId;
    protected Timestamp noteDateCreation;
    protected  String notePriority;
    protected String noteType;
    protected boolean isDone;
    protected boolean isActive;
    protected boolean isTextCategorized;
    protected  boolean isSynchronized;

    public NoteEntity(String noteType , long userId ,Timestamp noteDateCreation,
                      boolean isDone ,boolean isActive,boolean isTextCategorized,boolean isSynchronized) {
        this.noteDateCreation=noteDateCreation;
        this.noteType=noteType;
        this.isActive=isActive;
        this.isDone=isDone;
        this.isTextCategorized=isTextCategorized;
        this.userId=userId;
        this.isSynchronized=isSynchronized;
    }
    public NoteEntity(String noteType , String notePriority, long userId ,Timestamp noteDateCreation,
                      boolean isDone ,boolean isActive,boolean isTextCategorized,boolean isSynchronized) {
        this.noteDateCreation=noteDateCreation;
        this.noteType=noteType;
        this.isActive=isActive;
        this.isDone=isDone;
        this.isTextCategorized=isTextCategorized;
        this.notePriority = notePriority;
        this.userId=userId;
        this.isSynchronized=isSynchronized;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public Timestamp getNoteDateCreation() {
        return noteDateCreation;
    }

    public void setNoteDateCreation(Timestamp noteDateCreation) {
        this.noteDateCreation = noteDateCreation;
    }

    public String getNotePriority() {
        return notePriority;
    }

    public void setNotePriority(String notePriority) {
        this.notePriority = notePriority;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isTextCategorized() {
        return isTextCategorized;
    }

    public void setIsTextCategorized(boolean isTextCategorized) {
        this.isTextCategorized = isTextCategorized;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setIsSynchronized(boolean isSynchronized) {
        this.isSynchronized = isSynchronized;
    }
}
