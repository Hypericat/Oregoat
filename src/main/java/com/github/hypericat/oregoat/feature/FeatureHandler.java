package com.github.hypericat.oregoat.feature;

import com.github.hypericat.oregoat.feature.features.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeatureHandler {
    private FeatureHandler() {

    }

    private static final HashMap<Class<? extends Feature>, Feature> features = new HashMap<>();

    public static void initFeatures() {
        if (!features.isEmpty()) throw new IllegalStateException("Initialized existing features!");

        register(new Template());
    }

    private static void register(Feature feature) {
        if (features.containsKey(feature.getClass())) throw new IllegalStateException("Initialized an already existing feature");
        features.put(feature.getClass(), feature);
        feature.onInit();
    }

    public static<T extends Feature> T getByClass(Class<T> clss) {
        if (!features.containsKey(clss)) throw new IllegalStateException("Requested non-existing feature!");
        return (T) features.get(clss);
    }

    public static void debug() {
        getByClass(Template.class).enable();
    }
}
