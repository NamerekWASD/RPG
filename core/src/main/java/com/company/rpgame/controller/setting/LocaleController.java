package com.company.rpgame.controller.setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer;
import com.github.czyzby.autumn.mvc.stereotype.View;

@View(id="locale",value = "lml/settings/locale.lml")
public class LocaleController implements ViewRenderer {

    @Override
    public void render(Stage stage, float delta) {
        stage.act(delta);
        Gdx.gl.glClearColor(1,1,1,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
}
