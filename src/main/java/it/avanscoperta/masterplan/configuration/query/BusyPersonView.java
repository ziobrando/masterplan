package it.avanscoperta.masterplan.configuration.query;

import it.avanscoperta.masterplan.configuration.domain.UserId;
import org.springframework.data.annotation.Id;

/**
 * No strong feeling for using a record instead of a class
 * @param userId    the unique id for the user.
 * @param username  the unique username for the user.
 */
public record BusyPersonView(
        @Id
        UserId userId,
        String username) {
}
