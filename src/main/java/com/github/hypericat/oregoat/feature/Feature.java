package com.github.hypericat.oregoat.feature;

public abstract class Feature {
    protected abstract void onEnable();

    protected abstract void onDisable();
    protected abstract void onInit();

}
