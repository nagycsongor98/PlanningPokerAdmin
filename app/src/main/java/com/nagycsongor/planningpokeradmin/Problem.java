package com.nagycsongor.planningpokeradmin;

public class Problem {
    private String name;
    private String grupKey;
    private boolean available;

    public Problem() {
    }

    public Problem(String name,String grupKey ,boolean available) {
        this.name = name;
        this.grupKey = grupKey;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public String getGrupKey() {
        return grupKey;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
