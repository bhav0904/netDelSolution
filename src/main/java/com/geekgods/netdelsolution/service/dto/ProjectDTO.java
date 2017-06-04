package com.geekgods.netdelsolution.service.dto;

public class ProjectDTO {
    private String name;
    private String address;
    private String issue;

    private String description;
    private String createdBy;

    public ProjectDTO() {
    }

    public ProjectDTO(String name, String address, String issue, String description, String createdBy) {
        this.name = name;
        this.address = address;
        this.issue = issue;
        this.description = description;
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
