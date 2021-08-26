package org.id2k1149.dinerVoting.util;

import org.id2k1149.dinerVoting.exception.TimeException;

import java.time.LocalTime;

public class TimeUtil {
    public static void checkTime() {
        int lastHourToVoteAgain = 23;
        if (LocalTime.now().getHour() > lastHourToVoteAgain) throw new TimeException("You can't vote again after 11am");
    }
}
