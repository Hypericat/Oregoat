package com.github.hypericat.oregoat.gui;

import io.github.moulberry.moulconfig.Config;
import io.github.moulberry.moulconfig.annotations.*;
import org.lwjgl.input.Keyboard;

public class OreConfig extends Config {
    @Override
    public String getTitle() {
        return "§bOregoat Conf";
    }

    @Category(name = "Goat", desc = "goat addons")
    public MyCategory myCategory = new MyCategory();

    public static class MyCategory {
        @Category(name = "SubCategory", desc = "Sub category description")
        public MySubCategory subCategory = new MySubCategory();

        public class MySubCategory {
            @ConfigOption(name = "Text Test", desc = "Text Editor Test")
            @ConfigEditorText
            public String text = "Text";

            @ConfigOption(name = "Number", desc = "Slider test")
            @ConfigEditorSlider(minValue = 0, maxValue = 10, minStep = 1)
            public int slider = 0;


            @ConfigOption(name = "Key Binding", desc = "Key Binding")
            @ConfigEditorKeybind(defaultKey = Keyboard.KEY_F)
            public int keyBoard = Keyboard.KEY_F;
        }
    }


}
