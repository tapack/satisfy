package io.tapack.satisfy.rest.fake;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace="http://www.example.org/bar")
public class User {
    private String firstName;
    private String lastName;
    private Long id;
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}