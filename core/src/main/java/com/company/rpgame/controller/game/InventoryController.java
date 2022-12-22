package com.company.rpgame.controller.game;

import com.company.rpgame.controller.GameController;
import com.company.rpgame.service.ui.InventoryService;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog;
import com.github.czyzby.lml.annotation.*;
import com.github.czyzby.lml.parser.action.ActionContainer;

import static com.company.rpgame.service.ui.InventoryService.*;

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

    @LmlAction
    public static float getCellWidth() {
        return ITEM_CELL_WIDTH;
    }

    @LmlAction
    public static float getCellHeight() {
        return ITEM_CELL_HEIGHT;
    }

    @LmlAction
    public static float getEquipmentCellWidth() {
        return EQUIPMENT_CELL_WIDTH;
    }

    @LmlAction
    public static float getEquipmentCellHeight() {
        return EQUIPMENT_CELL_HEIGHT;
    }
}
