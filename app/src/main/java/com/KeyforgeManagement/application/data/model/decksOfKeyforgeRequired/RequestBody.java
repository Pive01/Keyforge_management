package com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired;

public class RequestBody {
    private final int pageSize = 500;
    private final Boolean includeUnregistered = false;
    private final String owner;
    private final Boolean sellersView = true;
    private final String[] houses = {};
    private final int page = 0;
    private final String[] constraints = {};
    private final String[] expansions = {};
    private final String title = "";
    private final String notes = "";
    private final String noteUser = "";
    private final String sort = "SAS_RATING";
    private final Boolean notForSale = false;
    private final Boolean forTrade = false;
    private final Boolean forAuction = false;
    private final Boolean withOwners = false;
    private final Boolean completedAuctions = false;
    private final Boolean myFavorites = false;
    private final String[] cards = {};
    private final String sortDirection = "DESC";

    public RequestBody(String owner) {
        this.owner = owner;
    }

}
