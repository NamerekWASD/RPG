package com.company.rpgame.controller.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Disposable;
import com.company.rpgame.controller.GameController;
import com.company.rpgame.service.GameScreenService;
import com.company.rpgame.service.ViewService;
import com.company.rpgame.service.listeners.ViewControlListener;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewDialogShower;
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.action.ActionContainer;

@ViewDialog(id="settingsDialog", value = "lml/game/inGameGUI.lml")
public class SettingsDialogController implements ActionContainer, ViewControlListener, ViewDialogShower, Disposable {
    @Inject private GameScreenService gameScreenService;
    @Inject private GameController game;
    @Inject private ViewService viewService;
    @LmlActor
    private static Window dialog;

    @LmlAction("continue")
    public void Continue(){
        gameScreenService.resumeGame(this.getClass());
        dialog.remove();
    }

    @Override
    public Class<?> getViewClass() {
        return this.getClass();
    }

    @Override
    public void invoke() {
        if(dialog == null){
            game.showSettings();
        }
    }

    @Override
    public void backAction() {
        Continue();
    }
    @Override
    public void doBeforeShow(Window dialog) {
        viewService.setCurrentListener(this);
    }

    @Override
    public void dispose() {
        dialog.remove();
    }
}
