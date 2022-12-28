package com.company.rpgame.controller.dialog;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.company.rpgame.controller.GameController;
import com.company.rpgame.service.InventoryService;
import com.company.rpgame.service.ScreenService;
import com.company.rpgame.service.ViewService;
import com.company.rpgame.service.listeners.ViewControlListener;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewDialogShower;
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.action.ActionContainer;


@ViewDialog(id="inventoryDialog", value = "lml/game/inventory.lml")
public class InventoryViewControllerControl implements ActionContainer, ViewControlListener, ViewDialogShower {
    @Inject
    private GameController game;
    @Inject
    private InterfaceService interfaceService;
    @Inject
    private InventoryService inventory;
    @Inject
    private ScreenService screenService;
    @Inject
    private ViewService viewService;
    @LmlActor("inventoryDialog")
    private static Window dialog;


    @LmlAction("continue")
    public void returnToGame(){
        screenService.resumeGame(this.getClass());
        dialog.clearActions();
        addAction(dialog);
        inventory.destroy();
    }

    private void addAction(Window dialog) {
        dialog.addAction(Actions.sequence(Actions.rotateTo(90, 0.3f, Interpolation.slowFast),
                Actions.removeActor()));
    }
    @LmlAction
    public int getCellCount(){
        return inventory.getCellCount();
    }
    public static Window getDialog(){
        return dialog;
    }

    @Override
    public Class<?> getViewClass() {
        return this.getClass();
    }

    @Override
    public void invoke() {
        game.showInventoryDialog();
    }

    @Override
    public void backAction() {
        returnToGame();
    }

    @Override
    public void doBeforeShow(Window dialog) {
        viewService.setCurrentListener(this);
    }
}
