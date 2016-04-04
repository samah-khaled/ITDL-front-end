package model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by samah on 25/03/2016.
 */
public class MeetingNoteEntity extends NoteEntity {
    protected Date meetingNoteDate;
    protected Time estimatedTransportTime;
    protected String meetingTitle;
    protected String meetingPlace;
    protected String meetingAgenda;

    public MeetingNoteEntity(String noteType, long userId, Timestamp noteDateCreation,
                             boolean isDone, boolean isActive, boolean isTextCategorized, boolean isSynchronized,
                        String meetingtitle ,String meetingplace,String agenda, Date meetingNoteDate,Time estimatedTransportTime) {
        super(noteType, userId, noteDateCreation, isDone, isActive, isTextCategorized, isSynchronized);

        this.estimatedTransportTime=estimatedTransportTime;
        this.meetingPlace=meetingplace;
        this.meetingAgenda=agenda;
        this.meetingTitle=meetingtitle;
        this.meetingNoteDate=meetingNoteDate;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public String getMeetingAgenda() {
        return meetingAgenda;
    }

    public void setMeetingAgenda(String meetingAgenda) {
        this.meetingAgenda = meetingAgenda;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public Time getEstimatedTransportTime() {
        return estimatedTransportTime;
    }

    public void setEstimatedTransportTime(Time estimatedTransportTime) {
        this.estimatedTransportTime = estimatedTransportTime;
    }

    public Date getMeetingNoteDate() {
        return meetingNoteDate;
    }

    public void setMeetingNoteDate(Date meetingNoteDate) {
        this.meetingNoteDate = meetingNoteDate;
    }
}
