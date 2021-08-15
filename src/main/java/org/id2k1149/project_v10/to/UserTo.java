package org.id2k1149.project_v10.to;

import java.beans.ConstructorProperties;
import java.util.List;

public class UserTo extends BaseTo {
    private final String username;
    private final List<VoterTo> votes;

    @ConstructorProperties({"id", "info"})
    public UserTo(Long id,
                  String username,
                  List<VoterTo> votes) {
        super(id);
        this.username = username;
        this.votes = votes;
    }

    public String getUsername() {
        return username;
    }

    public List<VoterTo> getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "UserVotes{" +
                "votes=" + votes +
                '}';
    }
}
