package edu.tamu.scholars.middleware.config.model;

import edu.tamu.scholars.middleware.service.TDBTriplestore;
import edu.tamu.scholars.middleware.service.Triplestore;

public class TriplestoreConfig {

    private Class<? extends Triplestore> type = TDBTriplestore.class;

    private String directory = "triplestore";

    private String layoutType = "layout2/hash";

    private String databaseType = "MySQL";

    private String datasourceUrl;

    private String username;

    private String password;

    private boolean jdbcStream = true;

    private int jdbcFetchSize = 8;

    private boolean streamGraphAPI = true;

    private boolean annotateGeneratedSQL = false;

    public TriplestoreConfig() {

    }

    public Class<? extends Triplestore> getType() {
        return type;
    }

    public void setType(Class<? extends Triplestore> type) {
        this.type = type;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public void setDatasourceUrl(String datasourceUrl) {
        this.datasourceUrl = datasourceUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isJdbcStream() {
        return jdbcStream;
    }

    public void setJdbcStream(boolean jdbcStream) {
        this.jdbcStream = jdbcStream;
    }

    public int getJdbcFetchSize() {
        return jdbcFetchSize;
    }

    public void setJdbcFetchSize(int jdbcFetchSize) {
        this.jdbcFetchSize = jdbcFetchSize;
    }

    public boolean isStreamGraphAPI() {
        return streamGraphAPI;
    }

    public void setStreamGraphAPI(boolean streamGraphAPI) {
        this.streamGraphAPI = streamGraphAPI;
    }

    public boolean isAnnotateGeneratedSQL() {
        return annotateGeneratedSQL;
    }

    public void setAnnotateGeneratedSQL(boolean annotateGeneratedSQL) {
        this.annotateGeneratedSQL = annotateGeneratedSQL;
    }

}
