package com.example.norona;

public class Symptom {
    String name;
    boolean present;

    public Symptom(String name, boolean present) {
        this.name = name;
        this.present = present;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}
