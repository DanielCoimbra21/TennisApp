package com.example.tennisapplication.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "player")
public class PlayerEntity implements Comparable{

    @PrimaryKey(autoGenerate = true)
    private int idPlayer;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "age")
    private int age;

    @ColumnInfo(name = "phoneNumber")
    private int phoneNumber;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "nbReservations")
    private int nbReservations;

    @ColumnInfo(name = "image")
    private int image;

    @Ignore
    public PlayerEntity() {

    }

    public PlayerEntity(@NonNull String email, String password, String firstName, String lastName, int age, int phoneNumber, String status, int nbReservations, int image)
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

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
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
}
