package com.quotehunters.quotecollection.domain.mypage.bookmark.dto;

public class BookmarkDTO {
    private String personName;
    private String quote;

    public BookmarkDTO() {}

    public BookmarkDTO(String personName, String quote) {
        this.personName = personName;
        this.quote = quote;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "BookmarkDTO{" +
                "personName='" + personName + '\'' +
                ", quote='" + quote + '\'' +
                '}';
    }
}
