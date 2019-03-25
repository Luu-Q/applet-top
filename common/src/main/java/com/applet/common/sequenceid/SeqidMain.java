package com.applet.common.sequenceid;

public class SeqidMain {
    public static void main(String[] args) {
        Sequence sq = new Sequence(11,22);
        for(Integer i=0;i<Integer.MAX_VALUE;i++){
            System.out.println(sq.nextId());
        }

    }
}
