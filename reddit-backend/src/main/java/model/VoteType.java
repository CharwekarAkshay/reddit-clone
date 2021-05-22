package model;

import java.util.Arrays;

public enum VoteType {
    UP_VOTE(1), DOWN_VOTE(-1),
    ;

    private int direction;

    VoteType(int direction) {
    }
}
