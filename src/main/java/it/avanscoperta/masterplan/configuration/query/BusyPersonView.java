package it.avanscoperta.masterplan.configuration.query;

import it.avanscoperta.masterplan.configuration.domain.UserId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collection;

public class BusyPersonView {
    @Id
    UserId userId;
    String username;
    Collection<Object> externalCalendars = new ArrayList<>();

    public BusyPersonView(UserId userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Collection<Object> externalCalendars() {
        return externalCalendars;
    }

    public UserId getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }
}
