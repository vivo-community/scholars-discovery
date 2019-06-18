package edu.tamu.scholars.middleware.view.validator;

import java.util.List;

import edu.tamu.scholars.middleware.view.annotation.ValidCollectionFacets;
import edu.tamu.scholars.middleware.view.model.CollectionView;
import edu.tamu.scholars.middleware.view.model.Facet;

public class CollectionFacetsValidator extends CollectionFieldValidator<ValidCollectionFacets> {

    @Override
    protected boolean isValidField(CollectionView collectionView, List<String> fieldNames) {
        List<Facet> facets = collectionView.getFacets();
        for (Facet facet : facets) {
            boolean facetValid = false;
            for (String fieldName : fieldNames) {
                if (fieldName.equals(facet.getField())) {
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
