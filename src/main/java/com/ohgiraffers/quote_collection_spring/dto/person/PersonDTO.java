package com.ohgiraffers.quote_collection_spring.dto.person;

import com.ohgiraffers.quote_collection_spring.dto.category.CountryDTO;
import com.ohgiraffers.quote_collection_spring.dto.category.FieldDTO;
import com.ohgiraffers.quote_collection_spring.dto.category.PeriodDTO;

public class PersonDTO {
    private int id;
    private int countryId;
    private int periodId;
    private int fieldId;
    private String name;

    public PersonDTO() {}

    public PersonDTO(int id, int countryId, int periodId, int fieldId, String name) {
        this.id = id;
        this.countryId = countryId;
        this.periodId = periodId;
        this.fieldId = fieldId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getPeriodId() {
        return periodId;
    }

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", countryId=" + countryId +
                ", periodId=" + periodId +
                ", fieldId=" + fieldId +
                ", name='" + name + '\'' +
                '}';
    }
}
