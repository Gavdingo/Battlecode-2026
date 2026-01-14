// https://github.com/chenyx512/battlecode24/blob/main/src/bot1/fast/FastIterableLocSet.java
package v1.FastMath;

import battlecode.common.MapLocation;

public class FastIterableLocationSet {
    public StringBuilder keys;
    public int maxLength;
    public MapLocation[] locations;
    public int size;
    private int earliestRemoved;

    public FastIterableLocationSet() {
        this(100);
    }

    public FastIterableLocationSet(int length) {
        keys = new StringBuilder();
        maxLength = length;
        locations = new MapLocation[maxLength];
    }

    private String toString(MapLocation mapLocation) {
        return "^" + (char)(mapLocation.x) + (char)(mapLocation.y);
    }

    public void add(MapLocation mapLocation) {
        String key = toString(mapLocation);
        if (keys.indexOf(key) != -1) return;
        if (size == maxLength) return;

        keys.append(key);
        size++;

    }

    public void add(int x, int y) {
        String key = "^" + (char)x + (char)y;
        if (keys.indexOf(key) != -1) return;

        keys.append(key);
        size++;
    }

    public void remove(MapLocation mapLocation) {
        String key = toString(mapLocation);
        int index;
        if ((index = keys.indexOf(key)) < 0) return;

        keys.delete(index, index + 3);
        size--;
        if(earliestRemoved > index) earliestRemoved = index;
    }

    public void remove(int x, int y) {
        String key = "^" + (char)x + (char)y;
        int index;
        if ((index = keys.indexOf(key)) < 0) return;

        keys.delete(index, index + 3);
        size--;
        if(earliestRemoved > index) earliestRemoved = index;
    }

    public boolean contains(MapLocation mapLocation) {
        return keys.indexOf(toString(mapLocation)) >= 0;
    }

    public boolean contains(int x, int y) {
        return keys.indexOf("^" + (char)x + (char)y) >= 0;
    }

    public void clear() {
        size = 0;
        keys = new StringBuilder();
        earliestRemoved = 0;
    }

    public void updateIterable() {
        for (int i = earliestRemoved / 3; i < size; i++) {
            locations[i] = new MapLocation(keys.charAt(i*3+1), keys.charAt(i*3+2));
        }
        earliestRemoved = size * 3;
    }

    public void replace(String newSet) {
        keys.replace(0, keys.length(), newSet);
        size = newSet.length() / 3;
    }
}