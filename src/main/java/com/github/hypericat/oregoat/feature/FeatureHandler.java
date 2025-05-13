package com.github.hypericat.oregoat.feature;

import com.github.hypericat.oregoat.feature.features.StructureLocator;
import com.github.hypericat.oregoat.feature.features.Routes;
import com.github.hypericat.oregoat.feature.features.Template;

import java.util.HashMap;

public class FeatureHandler {
    private FeatureHandler() {

    }

    private static final HashMap<Class<? extends Feature>, Feature> features = new HashMap<>();

    public static void initFeatures() {
        if (!features.isEmpty()) throw new IllegalStateException("Initialized existing features!");

        register(new Template());
        register(new Routes());
        register(new StructureLocator());
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

    private static void enableAll() {
        features.values().forEach(Feature::enable);
    }

    public static void debug() {
        enableAll();
    }
}
