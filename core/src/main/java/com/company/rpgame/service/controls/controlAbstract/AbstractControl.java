package com.company.rpgame.service.controls.controlAbstract;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.company.rpgame.service.controls.PlayerControl;
import com.company.rpgame.service.controls.ScreenControl;
import com.company.rpgame.service.listeners.PlayerControlListener;
import com.company.rpgame.service.listeners.ViewControlListener;
import lombok.val;

public abstract class AbstractControl implements PlayerControl, ScreenControl {
/*    /** Sin value at NE corner. *//*
    protected static final float SIN = MathUtils.sin(MathUtils.atan2(1f, 1f));
    *//** Cos value at NE corner. *//*
    protected static final float COS = MathUtils.cos(MathUtils.atan2(1f, 1f));*/

    private PlayerControlListener listener;
    protected Vector2 movement = new Vector2();

    @Override
    public Vector2 getMovementDirection() {
        return movement;
    }

    @Override
    public void setControlListener(final PlayerControlListener listener) {
        this.listener = listener;
    }
    private final Array<ViewControlListener> viewControlListeners = new Array<>();
    private ViewControlListener currentView;
    @Override
    public void setCurrentView(ViewControlListener viewControlListeners) {
        this.currentView = viewControlListeners;
    }

    @Override
    public void addListeners(ViewControlListener... viewControlListeners) {
        this.viewControlListeners.addAll(viewControlListeners);
    }

    protected Array<ViewControlListener> getListeners() {
        return viewControlListeners;
    }
    protected ViewControlListener getListener(Class<?> tclass) {
        for (val listener :
                new Array.ArrayIterator<>(viewControlListeners)) {
            if (listener.getViewClass().equals(tclass)){
                return listener;
            }
        }
        return null;
    }

    protected ViewControlListener getCurrentView() {
        return currentView;
    }


    /** @return should be notified about game events. */
    protected PlayerControlListener getListener() {
        return listener;
    }

    /** @param angle in radians. */
    protected void updateMovementWithAngle(final float angle) {
        movement.x = MathUtils.cos(angle);
        movement.y = MathUtils.sin(angle);
    }

    /** Stops movement. */
    protected void stop() {
        movement.set(0f, 0f);
    }
    @Override
    public void reset() {
        movement.set(0f, 0f);
    }

    @Override
    public void clear() {
        if(viewControlListeners.size > 0){
            viewControlListeners.clear();
        }
        currentView = null;
    }
}
