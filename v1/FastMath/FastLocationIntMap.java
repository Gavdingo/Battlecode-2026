// https://github.com/BSreenivas0713/Battlecode2022/blob/main/src/MPBasic/fast/FastLocIntMap.java
package v1.FastMath;

import battlecode.common.MapLocation;

public class FastLocationIntMap {
    public StringBuilder keys;
    public int size;
    private int earliestRemoved;

    public FastLocationIntMap() {
        keys = new StringBuilder();
    }

    private String toString(MapLocation mapLocation) {
        return "^" + (char)(mapLocation.x) + (char)(mapLocation.y);
    }

    public void add(MapLocation mapLocation, int value) {
        String key = toString(mapLocation);
        if (keys.indexOf(key) != -1) return;

        keys.append(key + (char)(value + 0x100));
        size++;
    }

    public void add(int x, int y, int value) {
        String key = "^" + (char)x + (char)y;
        if (keys.indexOf(key) != -1) return;

        keys.append(key + (char)(value + 0x100));
        size++;
    }

    public void remove(MapLocation mapLocation) {
        String key = toString(mapLocation);
        int index;
        if ((index = keys.indexOf(key)) < 0) return;

        keys.delete(index, index + 4);
        size--;

        if(earliestRemoved > index) earliestRemoved = index;
    }

    public void remove(int x, int y) {
        String key = "^" + (char)x + (char)y;
        int index;
        if ((index = keys.indexOf(key)) < 0) return;

        keys.delete(index, index + 4);
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

    public int getValue(MapLocation mapLocation) {
        String key = toString(mapLocation);
        int indexOf = keys.indexOf(key);
        if (indexOf == -1) return -1;

        return (int) keys.charAt(indexOf + 3) - 0x100;
    }

    public MapLocation[] getKeys() {
        MapLocation[] mapLocations = new MapLocation[size];
        for(int i = 1; i < keys.length(); i += 4) {
            mapLocations[i/4] = new MapLocation((int)keys.charAt(i), (int)keys.charAt(i+1));
        }
        return mapLocations;
    }

    public int[] getInts() {
        int[] ints = new int[size];
        for(int i = 3; i < keys.length(); i += 4) {
            ints[i/4] = (int)keys.charAt(i) - 0x100;
        }
        return ints;
    }
}