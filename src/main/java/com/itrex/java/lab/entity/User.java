package com.itrex.java.lab.entity;

import java.util.List;
import java.util.Objects;

public class User {

    private int id;
    private String name;
    private String password;
    private int role;
    private String email;
    private List<Certificate> certificates;

    public User() {
    }

    public User(int id, String name, String password, int role, String email, List<Certificate> certificates) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.email = email;
        this.certificates = certificates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", certificates=" + certificates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && role == user.role && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(certificates, user.certificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, role, email, certificates);
    }
}
