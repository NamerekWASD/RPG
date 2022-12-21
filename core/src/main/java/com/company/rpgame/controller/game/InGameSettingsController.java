package com.company.rpgame.controller.game;

import com.company.rpgame.controller.GameController;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.action.ActionContainer;

@ViewDialog(id = "inGameController", value = "lml/game/inGameGUI.lml")
public class InGameSettingsController implements ActionContainer {
    @Inject private GameController game;
    @LmlAction("continue")
    public void Continue(){
        game.resume(this.getClass());
    }
}
