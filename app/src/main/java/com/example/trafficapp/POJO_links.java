package com.example.trafficapp;

public class POJO_links {
    public String link_one;
    public String link_two;
    public String link_three;
    public String link_four;
    public String frame_interval;


    public POJO_links() {
    }

    public String getFrame_interval() {
        return frame_interval;
    }

    public void setFrame_interval(String frame_interval) {
        this.frame_interval = frame_interval;
    }

    public String getLink_one() {
        return link_one;
    }

    public void setLink_one(String link_one) {
        this.link_one = link_one;
    }

    public String getLink_two() {
        return link_two;
    }

    public void setLink_two(String link_two) {
        this.link_two = link_two;
    }

    public String getLink_three() {
        return link_three;
    }

    public void setLink_three(String link_three) {
        this.link_three = link_three;
    }

    public String getLink_four() {
        return link_four;
    }

    public void setLink_four(String link_four) {
        this.link_four = link_four;
    }

    public POJO_links(String link_one, String link_two, String link_three, String link_four, String frame_interval) {
        this.link_one = link_one;
        this.link_two = link_two;
        this.link_three = link_three;
        this.link_four = link_four;
        this.frame_interval = frame_interval;
    }
}
