package com.github.hypericat.oregoat.gui.hud;

import cc.polyfrost.oneconfig.hud.SingleTextHud;

public class TestHud extends SingleTextHud {
    public TestHud() {
        super("Test", true);
    }

    @Override
    public String getText(boolean example) {
        return "I'm an example HUD";
    }
}
