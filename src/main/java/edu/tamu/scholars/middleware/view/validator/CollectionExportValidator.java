package edu.tamu.scholars.middleware.view.validator;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.EXPORT_INDIVIDUAL_KEY;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.PATH_DELIMETER_REGEX;
import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.findField;
import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.getCollectionType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import edu.tamu.scholars.middleware.discovery.exception.InvalidValuePathException;
import edu.tamu.scholars.middleware.view.annotation.ValidCollectionExport;
import edu.tamu.scholars.middleware.view.model.CollectionView;
import edu.tamu.scholars.middleware.view.model.ExportField;

public class CollectionExportValidator extends CollectionFieldValidator<ValidCollectionExport> {

    @Override
    protected boolean isValidField(CollectionView collectionView, List<String> fieldNames) {
        Optional<Class<?>> type = getCollectionType(collectionView.getCollection());
        if (type.isPresent()) {
            for (ExportField export : collectionView.getExport()) {
                if (export.getValuePath().equals(EXPORT_INDIVIDUAL_KEY)) {
                    continue;
                }
                String[] path = export.getValuePath().split(PATH_DELIMETER_REGEX);
                boolean exportValid = false;
                for (String fieldName : fieldNames) {
                    if (path[0].length() > 0 && fieldName.equals(path[0])) {
                        try {
                            Field field = findField(type.get(), path);
                            exportValid = field != null;
                        } catch (InvalidValuePathException e) {

                        }
                        break;
                    }
                }
                if (!exportValid) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
