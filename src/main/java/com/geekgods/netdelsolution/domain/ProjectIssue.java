package com.geekgods.netdelsolution.domain;

import javax.persistence.*;

@Entity
@Table(name = "project_issue")
public class ProjectIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "issue")
    private String issue;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
