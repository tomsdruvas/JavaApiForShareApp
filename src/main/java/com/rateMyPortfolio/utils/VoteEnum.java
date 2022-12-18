package com.rateMyPortfolio.utils;

public enum VoteEnum {

    UP("UP"),
    DOWN("DOWN");

    private final String direction;

    VoteEnum(String direction){
        this.direction = direction;

    }

    public String getDirection(){
        return direction;
    }

}
