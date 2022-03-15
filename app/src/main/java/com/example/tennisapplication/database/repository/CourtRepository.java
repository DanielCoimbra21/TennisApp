package com.example.tennisapplication.database.repository;

public class CourtRepository {

    private static CourtRepository instance;

    private CourtRepository(){}

    public static CourtRepository getInstance() {
        if (instance == null){
            synchronized (ReservationRepository.class) {
                if (instance == null){
                    instance = new CourtRepository();
                }
            }
        }
        return instance;
    }

}
