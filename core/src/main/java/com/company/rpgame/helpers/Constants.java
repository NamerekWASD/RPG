package com.company.rpgame.helpers;

import com.badlogic.gdx.math.Vector2;
import com.github.czyzby.kiwi.util.gdx.GdxUtilities;

public final class Constants {
    public static final float APPLICATION_SCALE = GdxUtilities.isMobile() ? 1.5f : 1f;
    public static final float PPM = 64;
    public static final float DEFAULT_STEP = 1f/60f;
    public static final Vector2 GRAVITY = new Vector2(0, -18.8f);
    public static final short BIT_WALL = 1;
    public static final short BIT_PLAYER = 2;
    public static final short BIT_SENSOR = 4;
    public static final short BIT_NO_LIGHT = 8;
    public static final short BIT_BREAKABLE = 16;
    public static final String IMAGES_PATH = "images";
}
