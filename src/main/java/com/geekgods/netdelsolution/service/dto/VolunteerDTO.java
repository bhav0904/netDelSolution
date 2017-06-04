package com.geekgods.netdelsolution.service.dto;

public class VolunteerDTO {

    private String login;
    private String name;
    private String email;

    public VolunteerDTO() {
    }

    public VolunteerDTO(String name, String email, String login) {
        this.login = login;
        this.name = name;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
