package com.ohgiraffers.quote_collection_spring.dto.category;

public class PeriodDTO {
    private int id;
    private String period;

    public PeriodDTO() {}

    public PeriodDTO(int id, String period) {
        this.id = id;
        this.period = period;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "PeriodDTO{" +
                "id=" + id +
                ", period='" + period + '\'' +
                '}';
    }
}
