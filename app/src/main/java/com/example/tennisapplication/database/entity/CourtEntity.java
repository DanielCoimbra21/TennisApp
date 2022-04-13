package com.example.tennisapplication.database.entity;

import androidx.annotation.NonNull;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CourtEntity implements Comparable{

    private String idCourt;
    private boolean openAir;

    public CourtEntity(){}

    @Exclude
    public String getIdCourt() {
        return idCourt;
    }

    public void setIdCourt(String idCourt) {
        this.idCourt = idCourt;
    }

    public boolean isOpenAir() {
        return openAir;
    }

    public void setOpenAir(boolean openAir) {
        this.openAir = openAir;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CourtEntity)) return false;
        CourtEntity o = (CourtEntity) obj;
        if(o.getIdCourt() == this.getIdCourt()){
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
        result.put("idCourt", idCourt);
        result.put("openAir", openAir);
        return result;
    }
}
