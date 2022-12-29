package com.company.rpgame.controller.dialog;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ObjectMap;
import com.company.rpgame.service.controls.PlayerControl;
import com.company.rpgame.service.controls.ScreenControl;
import com.company.rpgame.service.controls.controlAbstract.ControlType;
import com.company.rpgame.service.controls.controlAbstract.controlType.KeyboardControl;
import com.company.rpgame.service.controls.controlAbstract.controlType.PlayerControlKey;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewDialogShower;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.autumn.mvc.stereotype.ViewStage;
import com.github.czyzby.kiwi.util.gdx.scene2d.range.FloatRange;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.action.ActionContainer;
import lombok.val;

import static com.company.rpgame.helper.ArrayUtils.getItems;

/** Allows to edit chosen controls. */
@View(id = "edit", value = "lml/edit.lml")
public class ControlsEditController implements ActionContainer, ViewDialogShower {
    @ViewStage private Stage stage;
    private PlayerControl playerControl;
    private ScreenControl screenControl;

    @LmlActor("mock") private Image mockUpEntity;
    @LmlActor("mainTable") private Table mainTable;
    @LmlActor("TOUCH;KEYBOARD") private ObjectMap<String, Actor> views;
    private TextButton checkedButton;
    private final MockUpdateAction updateAction = new MockUpdateAction();

    // Keyboard widgets:
    @LmlActor("keys") private Array<TextButton> keys;
    private final Actor keyboardListener = new Actor();

    public ControlsEditController() {
        // Allows to change current keyboard controls:
        IntSet storedKeys = new IntSet(2);
        keyboardListener.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                storedKeys.add(keycode);
                if(storedKeys.size != 0){
                    checkedButton.setText(checkedButton.getText() + " + ");
                }
                checkedButton.setText(checkedButton.getText() + Keys.toString(keycode));
                if(storedKeys.size == 3){
                   return keyUp(event, keycode);
                }
                return true;
            }

            @Override
            public boolean keyUp(final InputEvent event, final int keycode) {
                if (checkedButton == null) {
                    keyboardListener.remove();
                    return false;
                }
                final KeyboardControl keyboardControl = (KeyboardControl) playerControl;
                for (TextButton key:
                        new Array.ArrayIterable<>(keys)) {
                    if(checkedButton == key){
                        keyboardControl.setPlayerKey(keyboardControl.getPlayerKey(storedKeys), storedKeys);
                    }
                }
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
        playerControl.attachInputListener(stage);

        mockUpEntity.addAction(Actions.sequence(Actions.fadeOut(0.1f),
                Actions.fadeIn(0.1f)));

        Gdx.input.setInputProcessor(stage);
    }

    private void setCurrentControls() {
        if (playerControl.getType() == ControlType.KEYBOARD) {
            final KeyboardControl keyboardControl = (KeyboardControl) playerControl;

            PlayerControlKey[] values = PlayerControlKey.values();
            for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
                PlayerControlKey boundKey = values[i];
                for (val comboKey :
                        getItems(keyboardControl.getPlayerKey(boundKey))) {
                    keys.items[i].setText(Keys.toString(comboKey));
                }
            }
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