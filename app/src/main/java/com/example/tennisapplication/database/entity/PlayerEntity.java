package com.example.tennisapplication.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class PlayerEntity implements Comparable{

    private String idPlayer;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String age;
    private String phoneNumber;
    private String status;
    private int nbReservations;
    private int image;

    public PlayerEntity() {

    }

    public PlayerEntity(@NonNull String email, String password, String firstName, String lastName, String age, String phoneNumber, String status, int nbReservations, int image)
    {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.nbReservations = nbReservations;
        this.image = image;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getImage() {
        return image;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

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

    public String  getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String  getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNbReservations() {
        return nbReservations;
    }

    public void setNbReservations(int nbReservations) {
        this.nbReservations = nbReservations;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof PlayerEntity)) return false;
        PlayerEntity o = (PlayerEntity) obj;
        return o.getEmail().equals(this.getEmail());
    }

    @Override
    public String toString() {return firstName + " " + lastName;}

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("age", age);
        result.put("phoneNumber", phoneNumber);

        return result;
    }

}
