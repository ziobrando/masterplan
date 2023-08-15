package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.configuration.domain.UserId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class PersonalAvailabilityView {

    @Id
    private String personalAvailabilityId;
    private UserId userId;
    List<AvailableDay> availableDays = new ArrayList<AvailableDay>();

    /**
     * TODO: this shouldn't be here.
     * @param userId the <code>UserId</code> of the BusyPerson
     * @return an instance to <code>this</code>
     */
    public PersonalAvailabilityView withUserId(UserId userId) {
        this.userId = userId;
        return this;
    }
}
