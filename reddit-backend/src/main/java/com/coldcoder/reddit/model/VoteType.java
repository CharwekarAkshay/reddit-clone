package com.coldcoder.reddit.model;

public enum VoteType {
    UP_VOTE(1), DOWN_VOTE(-1),;

    private int direction;

    VoteType(int direction) {
    }
}
