package com.qa.app.utilities;

public class Userdetails {
    private int id;
    private String name;
    private String email;
    private String status;
    private String gender;

    public Userdetails()
    {}
    public Userdetails(String name, String email, String status, String gender) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.gender = gender;
    }

    public void setId(int id) { this.id = id;   }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Userdetails{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
