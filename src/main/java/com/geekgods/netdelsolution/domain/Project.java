package com.geekgods.netdelsolution.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<ProjectIssue> issues = new HashSet<>();

    @Column(name = "date")
    private Date date;

    @Column(name = "projectName")
    private String projectName;

    @Column(name = "projectDescription")
    private String projectDescription;

    @Column(name = "projectAddress")
    private String projectAddress;

    @ManyToMany(mappedBy="projects")
    private Set<User> volunteers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ProjectIssue> getIssues() {
        return issues;
    }

    public void setIssues(Set<ProjectIssue> issues) {
        this.issues = issues;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public Set<User> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(Set<User> volunteers) {
        this.volunteers = volunteers;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
}
