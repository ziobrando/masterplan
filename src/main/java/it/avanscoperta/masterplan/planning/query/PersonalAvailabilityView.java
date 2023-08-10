package it.avanscoperta.masterplan.planning.query;

import it.avanscoperta.masterplan.configuration.domain.UserId;
import it.avanscoperta.masterplan.planning.domain.PersonalAvailabilityId;
import org.springframework.data.annotation.Id;

public class PersonalAvailabilityView {

    @Id
    private PersonalAvailabilityId personalAvailabilityId;
    private UserId userId;

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
