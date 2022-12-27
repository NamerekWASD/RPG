package com.company.rpgame.service;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.company.rpgame.entity.items.basic.Item;
import com.company.rpgame.entity.player.PlayerInventory;
import com.company.rpgame.ui.inventory.InventoryDragAndDrop;
import com.company.rpgame.ui.inventory.entities.ItemCell;
import com.company.rpgame.ui.inventory.entities.ItemEquipped;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Destroy;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.asset.AssetService;
import lombok.val;

import java.util.Objects;

import static com.company.rpgame.helper.scene.ActorUtil.getActor;

@Component
public class InventoryService {
    @Inject
    private PlayerService playerService;
    @Inject
    private AssetService assetService;
    private Array<ItemCell> itemCellArray;
    private Dialog inventoryDialog;
    private Table equipmentTable;
    private Table itemCellTable;
    private InventoryDragAndDrop inventoryDragAndDrop;
    private Stage stage;

    private enum CellType{
        Default,
        Equip
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void fillInventory(){
        dispose();
        setupTables();
        initCells();
        initDragAndDrop();
    }
    private void setupTables() {
        inventoryDragAndDrop = new InventoryDragAndDrop();
        itemCellArray = new Array<>();
        itemCellTable = (Table) getActor(stage,"itemCellTable");
        inventoryDialog = (Dialog) getActor(stage,"inventoryDialog");
        equipmentTable = (Table) getActor(stage,"equipmentTable");
    }

    private void initCells() {
        int index = 0;
        for (val cell :
                new ArrayIterator<>(itemCellTable.getCells())) {
            index++;
            processCell(cell, index, CellType.Default);
        }
        for (val equipCell :
                new ArrayIterator<>(equipmentTable.getCells())) {
            index++;
            processCell(equipCell, index, CellType.Equip);
        }
        for (val item :
                new ArrayIterator<>(playerService.getAllItems())) {
            processItem(item);
        }
    }

    private void processCell(Cell<?> cell, int index, CellType type){
        ItemCell itemCell;
        Image cellImage = (Image) cell.getActor();
        String cellName = cellImage.getName();
        if(cellName == null){
            return;
        }
        cellImage.setName(cellName + " " + index);
        if (type == CellType.Equip) {
            itemCell = new ItemEquipped();
            ((ItemEquipped) itemCell).setAcceptableClassItem(cellName);
        } else {
            itemCell = new ItemCell();
        }
        itemCell.setCell(cellImage);
        itemCellArray.add(itemCell);
    }

    private void processItem(Item item) {
        for (ItemCell itemCell:
                new ArrayIterator<>(itemCellArray)) {
            if(itemCell.getItem() != null || item == null){
                continue;
            }
            if(item.getSavedCellId() == null){
                item.setSavedCellId(itemCell.getCell().getName());
            }
            if(Objects.equals(item.getSavedCellId(), itemCell.getCell().getName())){
                inventoryDialog.addActor(item.getImage());
                itemCell.setItem(item);
                return;
            }
        }
    }

    private void initDragAndDrop() {
        inventoryDragAndDrop.setItemCellArray(itemCellArray);
        inventoryDragAndDrop.initiate();
    }
    public int getCellCount() {
        return playerService.getMaxItemCount();
    }


    private PlayerInventory collectData(){
        itemCellArray = inventoryDragAndDrop.getItemCells();
        Array<Item> items = new Array<>();
        Array<Item> equipments = new Array<>();
        for (val itemCell :
                new ArrayIterator<>(itemCellArray)) {
            if(itemCell.getItem() == null){
                continue;
            }
            if (itemCell instanceof ItemEquipped) {
                equipments.add(itemCell.getItem());
            }else {
                items.add(itemCell.getItem());
            }
        }
        PlayerInventory playerInventory = new PlayerInventory();
        playerInventory.setItems(items);
        playerInventory.setEquippedItems(equipments);
        return playerInventory;
    }
    public void dispose(){
        if (inventoryDragAndDrop != null) {
            inventoryDragAndDrop.dispose();
            inventoryDragAndDrop = null;
        }
        if (itemCellArray != null) {
            itemCellArray.clear();
            itemCellArray = null;
        }
    }
    @Destroy
    public void destroy(){
        if(inventoryDragAndDrop == null){
            return;
        }
        playerService.updateInventory(collectData());
        dispose();
    }
}
