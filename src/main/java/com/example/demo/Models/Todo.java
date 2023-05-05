package com.example.demo.Models;

import java.sql.Timestamp;
import java.util.Date;

public class Todo {
    int id;

    int uid;

    String name;

    String descr;

    String deadline;

    Boolean isDone;

    Boolean isDeleted;

    Timestamp createdAtTimestamp;

    Timestamp deletedAtTimestamp;

    long daysToDeadline;

    public Todo(int todo_id, int user_id, String todo_name, String todo_descr, String todo_deadline, Boolean todo_isDone, Boolean todo_isDeleted) {
        this.id = todo_id;
        this.uid = user_id;
        this.name = todo_name;
        this.descr = todo_descr;
        this.deadline = todo_deadline;
        this.isDone = todo_isDone;
        this.isDeleted = todo_isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String email) {
        this.descr = descr;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) { this.isDone = done; }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) { this.isDeleted = deleted; }

    public Timestamp getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    public void setCreatedAtTimestamp(Timestamp timestamp) { this.createdAtTimestamp = timestamp; }

    public Timestamp getDeletedAtTimestamp() {
        return deletedAtTimestamp;
    }

    public void setDeletedAtTimestamp(Timestamp timestamp) { this.deletedAtTimestamp = timestamp; }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) { this.deadline = deadline; }


    public void setDaysToDeadline(long daysBetween) {
        this.daysToDeadline = daysBetween;
    }

    public long getDaysToDeadline() {
        return daysToDeadline;
    }
}
