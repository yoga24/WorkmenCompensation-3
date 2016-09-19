package com.cyorg.uiic.workmencompensationrainbow3.model;

public class SingleEntryModel {
    private double salary;
    private int noPersons;
    private double annSalary;
    private double sumInsured;
    private double premium;
    private boolean delCheck;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getNoPersons() {
        return noPersons;
    }

    public void setNoPersons(int noPersons) {
        this.noPersons = noPersons;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public double getAnnSalary() {
        return annSalary;
    }

    public void setAnnSalary(double annSalary) {
        this.annSalary = annSalary;
    }

    public double getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public boolean isDelCheck() {
        return delCheck;
    }

    public void setDelCheck(boolean delCheck) {
        this.delCheck = delCheck;
    }
}
