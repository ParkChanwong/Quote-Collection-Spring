package com.ohgiraffers.quote_collection_spring.domain.category.dto;

public class PeriodDTO {
    private int id;
    private String name;

    public PeriodDTO() {}

    public PeriodDTO(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "PeriodDTO{" +
                "id=" + id +
                ", period='" + name + '\'' +
                '}';
    }
}
