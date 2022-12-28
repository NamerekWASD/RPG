package com.company.rpgame.controller.dialog;

import com.company.rpgame.controller.MenuController;
import com.company.rpgame.service.ViewService;
import com.company.rpgame.service.listeners.ViewControlListener;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.action.ActionContainer;

@ViewDialog(id="sureDialog", value = "lml/dialog/sure.lml")
public class SureDialogController implements ActionContainer, ViewControlListener {

    @Inject private MenuController menuController;
    @Inject private InterfaceService interfaceService;

    @Inject private ViewService viewService;
    @Override
    public Class<?> getViewClass() {
        return this.getClass();
    }

    @Override
    public void invoke() {
        menuController.showSureDialog();
    }

    @Override
    public void backAction() {
        closeDialog();
    }
    @LmlAction
    private void closeDialog(){
        interfaceService.destroyDialog(this.getClass());
        viewService.setCurrentListener(menuController);
    }
}
