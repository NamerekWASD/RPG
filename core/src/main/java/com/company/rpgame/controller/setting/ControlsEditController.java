package com.company.rpgame.controller.setting;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ObjectMap;
import com.company.rpgame.service.ControlService;
import com.company.rpgame.service.ViewService;
import com.company.rpgame.service.controls.Control;
import com.company.rpgame.service.controls.controlAbstract.ControlType;
import com.company.rpgame.service.controls.controlAbstract.controlType.ControlKey;
import com.company.rpgame.service.controls.controlAbstract.controlType.KeyboardControl;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.autumn.mvc.stereotype.ViewStage;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.annotation.LmlAfter;
import com.github.czyzby.lml.parser.action.ActionContainer;
import lombok.val;

import static com.company.rpgame.helper.ArrayUtils.getIterator;

/** Allows to edit chosen controls. */
@View(id = "editControls", value = "lml/settings/editControls.lml")
public class ControlsEditController implements ActionContainer {
    @ViewStage private Stage stage;
    @Inject private ViewService viewService;
    @Inject private ControlService controlService;
    private Control control;
    @LmlActor("mainTable") private Table mainTable;
    @LmlActor("TOUCH;KEYBOARD") private ObjectMap<String, Actor> views;
    private TextButton checkedButton;

    // Keyboard widgets:
    @LmlActor private Table keysTable;
    private final Array<TextButton> keysArray = new Array<>();
    private final Actor keyboardListener = new Actor();

    public ControlsEditController() {
        // Allows to change current keyboard controls:
        keyboardListener.addListener(new InputListener() {
            IntSet storedKeys = null;
            final IntSet typedKeys = new IntSet(2);
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(checkedButton == null) {
                    return false;
                }
                if(storedKeys == null && checkedButton.getUserObject() instanceof IntSet set){
                    storedKeys = set;
                    checkedButton.setText(null);
                }
                if(typedKeys.size != 0){
                    checkedButton.setText(checkedButton.getText() + " + ");
                }
                typedKeys.add(keycode);

                checkedButton.setText(checkedButton.getText() + Keys.toString(keycode));

                if(typedKeys.size >= 3){
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
                final KeyboardControl keyboardControl = (KeyboardControl) control;

                keyboardControl.setNewValue(keyboardControl.getKey(storedKeys), typedKeys);

                checkedButton.setUserObject(typedKeys);
                checkedButton.setChecked(false);
                checkedButton = null;
                typedKeys.clear();
                storedKeys = null;
                keyboardListener.remove();
                return false;
            }
        });
    }

    @LmlAfter
    public void doBeforeShow() {
        viewService.dispose();
        control = controlService.getGeneralControl();
        fillButtonArray();
        attachListeners();
        setCurrentControls();
        changeView();
    }
    private void fillButtonArray(){
        for (Cell<?> cell :
                new Array.ArrayIterable<>(keysTable.getCells())) {
            if (cell.getActor() instanceof TextButton textButton) {
                keysArray.add(textButton);
            }
        }
    }

    private void attachListeners() {
        // Allowing controls to listen to input:
        control.attachInputListener(stage);
        Gdx.input.setInputProcessor(stage);
    }

    ControlKey[] controlKeys = ControlKey.values();
     private void setCurrentControls() {
         if (control.getType() == ControlType.KEYBOARD) {
             int index = 0;
             for (val textButton : new Array.ArrayIterator<>(keysArray)) {
                 final KeyboardControl keyboardControl = (KeyboardControl) control;
                 val key = controlKeys[index];
                 val iterator = getIterator(keyboardControl.getKey(key));

                 while (iterator.hasNext) {
                     int comboKey = iterator.next();
                     textButton.setText(textButton.getText() + Keys.toString(comboKey));

                     if(iterator.hasNext){
                         textButton.setText(textButton.getText() + " + ");
                     }
                 }
                 textButton.setUserObject(keyboardControl.getKey(key));
                 index++;
             }
         }
    }


    private void changeView() {
        mainTable.clearChildren();
        // Finding view relevant to the controls:
        final Actor view = views.get(control.getType().name());
        mainTable.add(view).grow();
        mainTable.pack();
    }

    @LmlAction("close")
    public void close() {
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
    @LmlAction
    public ControlKey[] getKeys(){
        return controlKeys;
    }
    @LmlAction
    public void setControlKey(Label label){
         String value = controlKeys[Integer.parseInt(label.getName())].name();
         label.setText(value);
    }

    /*private class MockUpdateAction extends Action {
        private final FloatRange x = new FloatRange(0f, 0.2f); // 0.2 is transition length (smoothness).
        private final FloatRange y = new FloatRange(0f, 0.2f);
        private float parentSize;
        private float size;
        private final Vector2 position = new Vector2();

        @Override
        public void reset() {
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
            return false;
        }

        // X and Y are in range of [-1, 1] - converting to [0, 1].
        private float getX() {
            return (((PlayerControl)control).getMovementDirection().x + 1f) / 2f;
        }

        private float getY() {
            return (((PlayerControl)control).getMovementDirection().y + 1f) / 2f;
        }
    }*/
}