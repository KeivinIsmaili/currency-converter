package com.themobilelife.currencyconverter.cache;

import java.time.Duration;
import java.time.LocalTime;
import com.github.benmanes.caffeine.cache.Expiry;

public class CustomExpiry<K, V> implements Expiry<K, V> {

    @Override
    public long expireAfterCreate(K key, V value, long currentTime) {
        Duration durationUntilExpiration = calculateExpiration();
        return durationUntilExpiration.toNanos(); // Return the expiration time in nanoseconds
    }

    @Override
    public long expireAfterUpdate(K key, V value, long currentTime, long currentDuration) {
        // No need to change expiration time on update
        return currentDuration;
    }

    @Override
    public long expireAfterRead(K key, V value, long currentTime, long currentDuration) {
        // No need to change expiration time on read
        return currentDuration;
    }

    private Duration calculateExpiration() {
        LocalTime now = LocalTime.now();
        LocalTime expirationTime = LocalTime.of(23, 59, 59);

        // If the current time is after 23:59:59, set expiration time to 23:59:59 of the next day
        if (now.isAfter(expirationTime)) {
            expirationTime = expirationTime.plusHours(24);
        }

        return Duration.between(now, expirationTime);
    }
}