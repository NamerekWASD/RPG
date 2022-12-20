package com.company.rpgame.controller.action;

	import com.company.rpgame.Application;
	import com.company.rpgame.helpers.Constants;
	import com.github.czyzby.autumn.mvc.stereotype.ViewActionContainer;
	import com.github.czyzby.lml.annotation.LmlAction;
	import com.github.czyzby.lml.parser.action.ActionContainer;
/** Since this class implements ActionContainer and is annotated with ViewActionContainer, its methods will be reflected
	 * and available in all LML templates. Note that this class is a component like any other, so it can inject any fields,
	 * use Initiate-annotated methods, etc. */
	@ViewActionContainer("global")
	public class Global implements ActionContainer {
	/**
	 * This is a mock-up method that does nothing. It will be available in LML templates through "close" (annotation
	 * argument) and "noOp" (method name) IDs.
	 */
	@LmlAction("close")
	public void noOp() {
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