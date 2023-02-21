package edu.tamu.scholars.middleware.defaults;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import edu.tamu.scholars.middleware.view.model.DiscoveryView;
import edu.tamu.scholars.middleware.view.model.repo.DiscoveryViewRepo;

@Service
public class DiscoveryViewsDefaults extends AbstractDefaults<DiscoveryView, DiscoveryViewRepo> {

    public DiscoveryViewsDefaults() {
        super();
    }

    @Override
    public String path() {
        return "classpath:defaults/discoveryViews.yml";
    }

    @Override
    public List<DiscoveryView> read(InputStream is) throws IOException {
        List<DiscoveryView> views = mapper.readValue(is, new TypeReference<List<DiscoveryView>>() {});
        for (DiscoveryView view : views) {
            loadTemplateMap(view.getTemplates());
        }
        return views;
    }

}
