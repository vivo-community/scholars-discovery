package edu.tamu.scholars.middleware.discovery.comparator;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ORDERED_DELIMITER;

import java.util.Comparator;

public class OrderedComparator implements Comparator<String> {

    @Override
    public int compare(String a, String b) {
        final String[] left = a.split(ORDERED_DELIMITER, 2);
        final String[] right = b.split(ORDERED_DELIMITER, 2);

        return Integer.valueOf(left[0]).compareTo(Integer.valueOf(right[0]));
    }
}
