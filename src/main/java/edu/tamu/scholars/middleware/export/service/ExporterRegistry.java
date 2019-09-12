package edu.tamu.scholars.middleware.export.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.export.exception.UnknownExporterTypeException;

@Service
public class ExporterRegistry {

    @Autowired
    private List<Exporter> exporters;

    public Exporter getExporter(String type) throws UnknownExporterTypeException {
        Optional<Exporter> exporter = exporters.stream().filter(e -> e.type().equals(type)).findAny();
        if (exporter.isPresent()) {
            return exporter.get();
        }
        throw new UnknownExporterTypeException(String.format("Could not find exporter of type %s", type));
    }

}
