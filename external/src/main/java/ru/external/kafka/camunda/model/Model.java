package ru.external.kafka.camunda.model;

public class Model {

    private String candidateName;
    private long appNumber;
    private String bornDate;
    private long experience;
    private String lastWorkPlace;

    public Model() {
    }

    public Model(String candidateName, long appNumber, String bornDate, long experience, String lastWorkPlace) {
        this.candidateName = candidateName;
        this.appNumber = appNumber;
        this.bornDate = bornDate;
        this.experience = experience;
        this.lastWorkPlace = lastWorkPlace;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public Long getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(Long appNumber) {
        this.appNumber = appNumber;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public String getLastWorkPlace() {
        return lastWorkPlace;
    }

    public void setLastWorkPlace(String lastWorkPlace) {
        this.lastWorkPlace = lastWorkPlace;
    }

    @Override
    public String toString() {
        return "Model{" +
                "candidateName='" + candidateName + '\'' +
                ", appNumber=" + appNumber +
                ", bornDate='" + bornDate + '\'' +
                ", experience=" + experience +
                ", lastWorkPlace='" + lastWorkPlace + '\'' +
                '}';
    }
}
