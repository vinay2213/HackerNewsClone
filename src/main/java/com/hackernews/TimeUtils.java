package com.hackernews;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeUtils {
    public static String formatTimeAgo(Instant timestamp) {
        Instant now = Instant.now();
        Duration duration = Duration.between(timestamp, now);

        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return seconds + " seconds ago";
        }

        long minutes = duration.toMinutes();
        if (minutes < 60) {
            return minutes + " minutes ago";
        }

        long hours = duration.toHours();
        if (hours < 24) {
            return hours + " hours ago";
        }

        long days = duration.toDays();
        if (days < 7) {
            return days + " days ago";
        }

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(timestamp, ZoneId.systemDefault());
        return zonedDateTime.format(DateTimeFormatter.ofPattern("d MMM uuuu, HH:mm", Locale.ENGLISH));
    }
}