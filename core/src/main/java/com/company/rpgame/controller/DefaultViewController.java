package com.company.rpgame.controller;

import com.company.rpgame.service.listeners.ViewControlListener;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer;
import com.github.czyzby.lml.parser.action.ActionContainer;

public interface DefaultViewController extends ViewControlListener, ViewRenderer, ActionContainer {
}
