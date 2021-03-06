package ru.tulupov.nsuconnect.model;


import java.util.List;

public class SearchParameters {
    private List<Integer> yourUniversity;
    private List<Integer> targetUniversity;
    private List<Integer> yourGender;
    private List<Integer> targetGender;
    private List<Integer> yourAge;
    private List<Integer> targetAge;

    public List<Integer> getTargetUniversity() {
        return targetUniversity;
    }

    public void setTargetUniversity(List<Integer> targetUniversity) {
        this.targetUniversity = targetUniversity;
    }

    public List<Integer> getYourUniversity() {
        return yourUniversity;
    }

    public void setYourUniversity(List<Integer> yourUniversity) {
        this.yourUniversity = yourUniversity;
    }

    public List<Integer> getYourGender() {
        return yourGender;
    }

    public void setYourGender(List<Integer> yourGender) {
        this.yourGender = yourGender;
    }

    public List<Integer> getTargetGender() {
        return targetGender;
    }

    public void setTargetGender(List<Integer> targetGender) {
        this.targetGender = targetGender;
    }

    public List<Integer> getYourAge() {
        return yourAge;
    }

    public void setYourAge(List<Integer> yourAge) {
        this.yourAge = yourAge;
    }

    public List<Integer> getTargetAge() {
        return targetAge;
    }

    public void setTargetAge(List<Integer> targetAge) {
        this.targetAge = targetAge;
    }

    @Override
    public String toString() {
        return String.format("%s; %s; %s; %s; %s", yourUniversity, yourGender, targetGender, yourAge, targetGender);
    }
}
