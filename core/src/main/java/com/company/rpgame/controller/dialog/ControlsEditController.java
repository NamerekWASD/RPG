package com.company.rpgame.controller.dialog;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.ObjectMap;
import com.company.rpgame.service.controls.PlayerControl;
import com.company.rpgame.service.controls.ControlListener;
import com.company.rpgame.service.controls.ControlType;
import com.company.rpgame.service.controls.controlType.KeyboardPlayerControl;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewDialogShower;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.autumn.mvc.stereotype.ViewStage;
import com.github.czyzby.kiwi.util.gdx.scene2d.range.FloatRange;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.action.ActionContainer;

/** Allows to edit chosen controls. */
@View(id = "edit", value = "ui/templates/edit.lml")
public class ControlsEditController implements ActionContainer, ViewDialogShower {
    @ViewStage private Stage stage;
    private PlayerControl playerControl;

    @LmlActor("mock") private Image mockUpEntity;
    @LmlActor("mainTable") private Table mainTable;
    @LmlActor("TOUCH;KEYBOARD") private ObjectMap<String, Actor> views;
    private TextButton checkedButton;
    private final MockUpdateAction updateAction = new MockUpdateAction();

    // Keyboard widgets:
    @LmlActor("keyUp") private TextButton keyUp;
    @LmlActor("keyDown") private TextButton keyDown;
    @LmlActor("keyLeft") private TextButton keyLeft;
    @LmlActor("keyRight") private TextButton keyRight;
    @LmlActor("keyJump") private TextButton keyJump;
    private final Actor keyboardListener = new Actor();

    public ControlsEditController() {
        // Allows to change current keyboard controls:
        keyboardListener.addListener(new InputListener() {
            @Override
            public boolean keyUp(final InputEvent event, final int keycode) {
                if (checkedButton == null) {
                    keyboardListener.remove();
                    return false;
                }
                final KeyboardPlayerControl keyboardControl = (KeyboardPlayerControl) playerControl;
                if (checkedButton == keyUp) {
                    keyboardControl.setUp(keycode);
                } else if (checkedButton == keyDown) {
                    keyboardControl.setDown(keycode);
                } else if (checkedButton == keyLeft) {
                    keyboardControl.setLeft(keycode);
                } else if (checkedButton == keyRight) {
                    keyboardControl.setRight(keycode);
                } else if (checkedButton == keyJump) {
                    keyboardControl.setJump(keycode);
                }
                checkedButton.setText(Keys.toString(keycode));
                checkedButton.setChecked(false);
                checkedButton = null;
                keyboardListener.remove();
                return false;
            }
        });
    }

    /** @param playerControl will be edited by this screen. */
    public void setControl(final PlayerControl playerControl) {
        this.playerControl = playerControl;
    }

    @Override
    public void doBeforeShow(final Window dialog) {
        attachListeners();
        setCurrentControls();
        changeView();
        updateAction.reset();
        mockUpEntity.setColor(Color.WHITE);
        mockUpEntity.addAction(Actions.forever(updateAction));
    }

    private void attachListeners() {
        // Allowing controls to listen to input:
        final InputMultiplexer inputMultiplexer = new InputMultiplexer();
        playerControl.attachInputListener(inputMultiplexer);

        playerControl.setControlListener(() ->
                mockUpEntity.addAction(Actions.sequence(Actions.fadeOut(0.1f),
                        Actions.fadeIn(0.1f))));

        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void setCurrentControls() {
        if (playerControl.getType() == ControlType.KEYBOARD) {
            final KeyboardPlayerControl keyboardControl = (KeyboardPlayerControl) playerControl;
            keyUp.setText(Keys.toString(keyboardControl.getUp()));
            keyDown.setText(Keys.toString(keyboardControl.getDown()));
            keyLeft.setText(Keys.toString(keyboardControl.getLeft()));
            keyRight.setText(Keys.toString(keyboardControl.getRight()));
            keyJump.setText(Keys.toString(keyboardControl.getJump()));
        }
    }

    private void changeView() {
        mainTable.clearChildren();
        // Finding view relevant to the controls:
        final Actor view = views.get(playerControl.getType().name());
        mainTable.add(view).grow();
        mainTable.pack();
    }

    @LmlAction("hide")
    public void hide() {
        mockUpEntity.clearActions();
        keyboardListener.remove();
        if (checkedButton != null) {
            checkedButton.setChecked(false);
            checkedButton = null;
        }
        Gdx.input.setInputProcessor(stage);
    }

    @LmlAction("setKey")
    public void setKeyboardShortcut(final TextButton button) {
        if (button.isChecked()) {
            if (checkedButton != null) {
                checkedButton.setChecked(false);
            }
            checkedButton = button;
            stage.addActor(keyboardListener);
            stage.setKeyboardFocus(keyboardListener);
        } else {
            checkedButton = null;
            keyboardListener.remove();
        }
    }
    /** Updates position of mock up entity. */
    private class MockUpdateAction extends Action {
        private final FloatRange x = new FloatRange(0f, 0.2f); // 0.2 is transition length (smoothness).
        private final FloatRange y = new FloatRange(0f, 0.2f);
        private float parentSize;
        private float size;
        private final Vector2 position = new Vector2();

        @Override
        public void reset() {
            parentSize = ((Layout) mockUpEntity.getParent()).getPrefWidth();
            size = mockUpEntity.getWidth();
            x.setCurrentValue(getX() * (parentSize - size));
            y.setCurrentValue(getY() * (parentSize - size));
            act(0f);
        }

        @Override
        public boolean act(final float delta) {
            x.setTargetValue(getX() * (parentSize - size));
            y.setTargetValue(getY() * (parentSize - size));
            x.update(delta);
            y.update(delta);
            position.set(mockUpEntity.getParent().getX() + (parentSize - size) / 2f,
                    mockUpEntity.getParent().getY() + (parentSize - size) / 2f);
            mockUpEntity.getParent().localToStageCoordinates(position);
            playerControl.update(stage.getViewport(), position.x, position.y);
            mockUpEntity.setPosition(x.getCurrentValue(), y.getCurrentValue());
            return false;
        }

        // X and Y are in range of [-1, 1] - converting to [0, 1].
        private float getX() {
            return (playerControl.getMovementDirection().x + 1f) / 2f;
        }

        private float getY() {
            return (playerControl.getMovementDirection().y + 1f) / 2f;
        }
    }
}