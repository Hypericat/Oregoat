package com.github.hypericat.oregoat.feature;

import com.github.hypericat.oregoat.feature.features.Template;

import java.util.ArrayList;
import java.util.List;

public class FeatureHandler {
    private FeatureHandler() {

    }

    private static final List<Feature> features = new ArrayList<>();

    public static void initFeatures() {
        if (!features.isEmpty()) throw new IllegalStateException("Initialized existing features!");

        register(new Template());


    }

    private static void register(Feature feature) {
        features.add(feature);
        feature.onInit();
    }

}
