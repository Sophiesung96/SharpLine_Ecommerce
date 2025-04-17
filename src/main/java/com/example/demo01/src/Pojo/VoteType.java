package com.example.demo01.src.Pojo;

public enum VoteType {
    UP{
        @Override
        public String toString() {
            return "up";
        }
    },
    DOWN{
        @Override
        public String toString() {
            return "down";
        }
    };


    public abstract String toString();
}
