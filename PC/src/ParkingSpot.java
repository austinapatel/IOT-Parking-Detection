//package com.amazonaws.samples;// Created by Austin Patel
// 10/21/2017

public class ParkingSpot {

    private int x, y, w, h;

    public ParkingSpot(int x1, int y1, int x2, int y2) {
        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);
        this.w  = Math.max(x1, x2) - Math.min(x1, x2);
        this.h = Math.max(y1, y2) - Math.min(y1, y2);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String toString() {
        return (new int[]{x, y, w, h}).toString();
    }

}
