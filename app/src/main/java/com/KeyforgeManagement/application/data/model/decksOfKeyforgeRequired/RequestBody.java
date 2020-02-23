package com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired;

public class RequestBody {
    private int pageSize = 500;
    private Boolean includeUnregistered = false;
    private String owner;
    private Boolean sellersView = true;
    private String[] houses = {};
    private int page = 0;
    private String[] constraints = {};
    private String[] expansions = {};
    private String title = "";
    private String notes = "";
    private String noteUser = "";
    private String sort = "SAS_RATING";
    private Boolean notForSale = false;
    private Boolean forTrade = false;
    private Boolean forAuction = false;
    private Boolean withOwners = false;
    private Boolean completedAuctions = false;
    private Boolean myFavorites = false;
    private String[] cards = {};
    private String sortDirection = "DESC";

    public RequestBody(String owner) {
        this.owner = owner;
    }

}
