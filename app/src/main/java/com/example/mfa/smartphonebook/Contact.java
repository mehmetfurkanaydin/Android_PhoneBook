package com.example.mfa.smartphonebook;

/**
 * Created by mfa on 02.01.2016.
 */
public class Contact {

    private int _id;
    private String name;
    private String phone;
    private String email;
    private String work_phone;
    private String home_x;
    private String home_y;
    private String work_x;
    private String work_y;
    private String duration_in;
    private String duration_out;
    private String number_in;
    private String number_out;
    private String miss_call;
    private String sent_message;
    private String rec_message;


    public Contact(int _id, String name, String phone, String email) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWork_phone() {
        return work_phone;
    }

    public void setWork_phone(String work_phone) {
        this.work_phone = work_phone;
    }

    public String getHome_x() {
        return home_x;
    }

    public void setHome_x(String home_x) {
        this.home_x = home_x;
    }

    public String getHome_y() {
        return home_y;
    }

    public void setHome_y(String home_y) {
        this.home_y = home_y;
    }

    public String getWork_x() {
        return work_x;
    }

    public void setWork_x(String work_x) {
        this.work_x = work_x;
    }

    public String getWork_y() {
        return work_y;
    }

    public void setWork_y(String work_y) {
        this.work_y = work_y;
    }

    public String getDuration_in() {
        return duration_in;
    }

    public void setDuration_in(String duration_in) {
        this.duration_in = duration_in;
    }

    public String getDuration_out() {
        return duration_out;
    }

    public void setDuration_out(String duration_out) {
        this.duration_out = duration_out;
    }

    public String getNumber_in() {
        return number_in;
    }

    public void setNumber_in(String number_in) {
        this.number_in = number_in;
    }

    public String getNumber_out() {
        return number_out;
    }

    public void setNumber_out(String number_out) {
        this.number_out = number_out;
    }

    public String getMiss_call() {
        return miss_call;
    }

    public void setMiss_call(String miss_call) {
        this.miss_call = miss_call;
    }

    public String getSent_message() {
        return sent_message;
    }

    public void setSent_message(String sent_message) {
        this.sent_message = sent_message;
    }

    public String getRec_message() {
        return rec_message;
    }

    public void setRec_message(String rec_message) {
        this.rec_message = rec_message;
    }
}