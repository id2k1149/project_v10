package org.id2k1149.dinerVoting.util;

import org.id2k1149.dinerVoting.model.User;
import org.id2k1149.dinerVoting.model.Voter;
import org.id2k1149.dinerVoting.to.VoterTo;
import java.util.List;
import java.util.stream.Collectors;

public class VoterUtil {
    public static List<VoterTo> getVoterTo(User user, List<Voter> voterList) {
        return voterList
                .stream()
                .filter(voter -> voter.getUser() == user)
                .map(voter -> new VoterTo(voter.getId(), voter.getDate(), voter.getDiner()))
                .collect(Collectors.toList());
    }
}