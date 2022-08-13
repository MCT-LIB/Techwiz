package com.csupporter.techwiz.domain.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Account extends BaseModel  {

    public static final int TYPE_USER = 1;
    public static final int TYPE_DOCTOR = 2;
    public static final int STATUS_INACTIVE = 0;
    public static final int STATUS_ACTIVE = 1;

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private int gender;     // 0 -> male, 1 -> female
    private int age;
    private String avatar;
    private int type;       // 0 -> admin, 1 -> user, 2 -> doctor
    private int status;     // 0 -> inactive, 1 -> active
    private int department = -1;
    private String certificationUrl;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getCertificationUrl() {
        return certificationUrl;
    }

    public void setCertificationUrl(String certificationUrl) {
        this.certificationUrl = certificationUrl;
    }

    @Exclude
    public boolean isUser() {
        return type == TYPE_USER;
    }

}
