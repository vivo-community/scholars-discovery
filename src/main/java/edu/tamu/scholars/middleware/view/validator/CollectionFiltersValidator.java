package edu.tamu.scholars.middleware.view.validator;

import java.util.List;

import edu.tamu.scholars.middleware.view.annotation.ValidCollectionFilters;
import edu.tamu.scholars.middleware.view.model.CollectionView;
import edu.tamu.scholars.middleware.view.model.Filter;

public class CollectionFiltersValidator extends CollectionFieldValidator<ValidCollectionFilters> {

    @Override
    protected boolean isValidField(CollectionView collectionView, List<String> fieldNames) {
        for (Filter filter : collectionView.getFilters()) {
            boolean facetValid = false;
            for (String fieldName : fieldNames) {
                if (fieldName.equals(filter.getField())) {
                    facetValid = true;
                    break;
                }
            }
            if (!facetValid) {
                return false;
            }
        }
        return true;
    }

}
