package com.company.rpgame.controller.action;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.company.rpgame.Application;
import com.company.rpgame.helper.Constants;
import com.github.czyzby.autumn.mvc.stereotype.ViewActionContainer;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.action.ActionContainer;
/** Since this class implements ActionContainer and is annotated with ViewActionContainer, its methods will be reflected
	 * and available in all LML templates. Note that this class is a component like any other, so it can inject any fields,
	 * use Initiate-annotated methods, etc. */
	@ViewActionContainer("global")
	public class Global implements ActionContainer {

	@LmlAction("close")
	public void noOp(Actor actor) {
		actor.addAction(Actions.removeActor());
	}

	@LmlAction("applicationWidth")
	public int applicationWidth() {
		return (int) (Application.WIDTH / Constants.PPM);
	}

	@LmlAction("applicationHeight")
	public int applicationHeight() {
		return (int) (Application.HEIGHT / Constants.PPM);
	}

}