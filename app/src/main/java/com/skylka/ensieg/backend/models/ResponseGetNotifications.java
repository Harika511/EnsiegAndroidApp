package com.skylka.ensieg.backend.models;

import com.skylka.ensieg.model.NotificationModel;

import java.util.List;

/**
 * Created by aleena on 7/11/15.
 */
public class ResponseGetNotifications {

    int error;
    String message;

    List<NotificationModel> invited;
    List<NotificationModel> hosted;
    List<NotificationModel> interested;

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<NotificationModel> getInvited() {
        return invited;
    }

    public List<NotificationModel> getInterested() {
        return interested;
    }

    public List<NotificationModel> getHosted() {
        return hosted;
    }
}
