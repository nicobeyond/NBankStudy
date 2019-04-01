package com.nbank.study;

public class Base<T> {

    boolean inverted() {
        return true;
    }

    void f() {
        if (!inverted()) {
            return;
        }
    }
    boolean member = !inverted();


    private static void as() {

    }
}
