package edu.tamu.scholars.middleware.discovery.service.export;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.tamu.scholars.middleware.view.model.ExportField;

public class Export implements Serializable {

    private static final long serialVersionUID = -8746213887078441988L;

    private final List<ExportField> exportFields;

    public Export(String... properties) {
        exportFields = new ArrayList<ExportField>();
        for (String prop : properties) {
            String[] parts = prop.split(",");
            ExportField e = new ExportField();
            e.setValuePath(parts[0]);
            e.setColumnHeader(parts.length > 1 ? parts[1] : parts[0]);
            e.setDelimiter(parts.length > 2 ? parts[2] : "||");
            exportFields.add(e);
        }
    }

    public List<ExportField> getExportFields() {
        return exportFields;
    }

}
