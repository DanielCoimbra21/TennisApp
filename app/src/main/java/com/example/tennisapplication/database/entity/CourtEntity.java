package com.example.tennisapplication.database.entity;

import androidx.annotation.NonNull;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CourtEntity implements Comparable{

    private boolean openAir;
    private int courtNum;

    public CourtEntity(){}

    public CourtEntity(boolean openAir, int courtNum){
        this.courtNum=courtNum;
        this.openAir=openAir;
    }

    public boolean isOpenAir() {
        return openAir;
    }

    public void setOpenAir(boolean openAir) {
        this.openAir = openAir;
    }

    public int getCourtNum() {
        return courtNum;
    }

    public void setCourtNum(int courtNum) {
        this.courtNum = courtNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CourtEntity)) return false;
        CourtEntity o = (CourtEntity) obj;
        if(o.getCourtNum() == this.getCourtNum()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int compareTo(Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("openAir", openAir);
        return result;
    }
}
