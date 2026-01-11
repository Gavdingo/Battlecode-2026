// https://github.com/chenyx512/battlecode24/blob/main/src/bot1/fast/FastLocSet.java
package v1.FastMath;

import battlecode.common.MapLocation;

public class FastLocationSet {
    public StringBuilder keys;
    public int size;

    public FastLocationSet() {
        keys = new StringBuilder();
        size = 0;
    }

    public int size() {
        return size;
    }

    private String toString(MapLocation mapLocation) {
        return "^" + (char) (mapLocation.x) + (char) (mapLocation.y);
    }

    public void add(MapLocation mapLocation) {
        String key = "^" + (char) (mapLocation.x) + (char) (mapLocation.y);
        // String key = locToStr(mapLocation);
        if (keys.indexOf(key) != -1) return;

        keys.append(key);
        size++;
    }

    public void add(int x, int y) {
        String key = "^" + (char) x + (char) y;
        if (keys.indexOf(key) != -1) return;

        keys.append(key);
        size++;
    }

    public void remove(MapLocation mapLocation) {
        String key = "^" + (char) (mapLocation.x) + (char) (mapLocation.y);
        // String key = locToStr(mapLocation);
        int index;
        if ((index = keys.indexOf(key)) < 0) return;

        keys.delete(index, index + 3);
        size--;
    }

    public void remove(int x, int y) {
        String key = "^" + (char) x + (char) y;
        int index;
        if ((index = keys.indexOf(key)) < 0) return;

        keys.delete(index, index + 3);
        size--;
    }

    public boolean contains(MapLocation mapLocation) {
        // return keys.indexOf(locToStr(mapLocation)) >= 0;
        return keys.indexOf("^" + (char) (mapLocation.x) + (char) (mapLocation.y)) >= 0;
    }

    public boolean contains(int x, int y) {
        return keys.indexOf("^" + (char) x + (char) y) >= 0;
    }

    public void clear() {
        size = 0;
        keys = new StringBuilder();
    }

    public void replace(String newSet) {
        keys.replace(0, keys.length(), newSet);
        size = newSet.length() / 3;
    }

    public void union(FastLocationSet locationSet) {
        for (int i = 1; i < locationSet.keys.length(); i += 3) {
            add((int) locationSet.keys.charAt(i), (int) locationSet.keys.charAt(i + 1));
        }
    }

    // note that this op is expensive
    public MapLocation[] getKeys() {
        MapLocation[] mapLocations = new MapLocation[size];
        for (int i = 1; i < keys.length(); i += 3) {
            mapLocations[i / 3] = new MapLocation((int) keys.charAt(i), (int) keys.charAt(i + 1));
        }
        return mapLocations;
    }

    public MapLocation pop() {
        if (size == 0)
            return null;
        MapLocation mapLocation = new MapLocation((int) keys.charAt(1), (int) keys.charAt(2));
        remove(mapLocation);
        return mapLocation;
    }
}