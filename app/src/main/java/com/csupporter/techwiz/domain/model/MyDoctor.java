package com.csupporter.techwiz.domain.model;

public class MyDoctor extends BaseModel{
    private String id;
    private String userId;
    private String doctorId;

    public MyDoctor(){

    }

    public MyDoctor(String id,String userId, String doctorId) {
        this.id = id;
        this.userId = userId;
        this.doctorId = doctorId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
