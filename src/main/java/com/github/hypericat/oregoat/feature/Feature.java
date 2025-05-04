package com.github.hypericat.oregoat.feature;

public abstract class Feature {
    private boolean enabled;

    protected abstract void onEnable();
    protected abstract void onDisable();
    protected void onInit() {}

    public abstract String getName();
    public abstract String[] getAliases();

    public void enable() {
        if (enabled) return;
        this.enabled = true;
        onEnable();
    }

    public void disable() {
        if (!enabled) return;
        this.enabled = false;
        onDisable();
    }

    public void toggle() {
        this.enabled = !enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    public void setEnabled(boolean bl) {
        if (enabled == bl) return;
        toggle();
    }
}
