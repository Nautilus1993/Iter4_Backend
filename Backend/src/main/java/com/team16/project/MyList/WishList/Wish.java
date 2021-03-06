package com.team16.project.MyList.WishList;

/**
* This class is the data model of individual wish
* @author  Team 16
*/
public class Wish {
    private String name;
    private String url;
    private String condition;
    private String price;
    private String availableDate;
    private String expirationDate;
    private String itemId;

    public Wish(String name, String url, String condition, String price, String availableDate, String expirationDate, String itemId) {
        this.name = name;
        this.url = url;
        this.condition = condition;
        this.price = price;
        this.availableDate = availableDate;
        this.expirationDate = expirationDate;
        this.itemId = itemId;
    }
}
