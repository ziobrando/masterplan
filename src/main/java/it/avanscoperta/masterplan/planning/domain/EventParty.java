package it.avanscoperta.masterplan.planning.domain;

import it.avanscoperta.masterplan.common.domain.Email;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple class to capture a list of EventParticipants, even if the list is one.
 */
public class EventParty {
    private List<Email> participants = new ArrayList<>();
    public static EventParty of(Email email) {
        EventParty party = new EventParty();
        party.addParticipant(email);
        return party;
    }

    private void addParticipant(Email email) {
        this.participants.add(email);
    }


}
