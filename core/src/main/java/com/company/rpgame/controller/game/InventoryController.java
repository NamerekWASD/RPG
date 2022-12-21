package com.company.rpgame.controller.game;

import com.company.rpgame.controller.GameController;
import com.company.rpgame.service.ui.InventoryService;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog;
import com.github.czyzby.lml.annotation.*;
import com.github.czyzby.lml.parser.action.ActionContainer;

@ViewDialog(id="inventory", value = "lml/game/inventory.lml")
public class InventoryController implements ActionContainer {
    @Inject
    private GameController game;
    @Inject
    private InventoryService inventory;

    @LmlAction
    public void Continue(){
        game.resume(this.getClass());
        inventory.onClose();
    }

    @LmlAction
    public int getCellCount(){
        return inventory.getCellCount();
    }
}
