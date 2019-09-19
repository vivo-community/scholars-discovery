package edu.tamu.scholars.middleware.discovery.argument;

import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.findProperty;

public abstract class MappingArg {

    private final String path;

    public MappingArg(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getProperty() {
        return findProperty(path);
    }

}
