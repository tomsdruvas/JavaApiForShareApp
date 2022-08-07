package com.example.demo.utils;

public enum VoteEnum {
    UP("Up"),
    DOWN("Down");
    private final String voteDirection;

    VoteEnum(String voteDirection) {
        this.voteDirection = voteDirection;
    }

    public String getVoteDirection() {
        return voteDirection;
    }
}
