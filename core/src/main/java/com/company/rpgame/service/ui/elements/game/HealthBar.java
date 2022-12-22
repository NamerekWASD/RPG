package com.company.rpgame.service.ui.elements.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.company.rpgame.entity.player.Player;
import com.company.rpgame.entity.data.EntityData;
import com.company.rpgame.service.ui.elements.game.base.Bar;

public class HealthBar extends Bar {
    private final EntityData data;
    public HealthBar(Player player, Drawable externalTexture, float animationDuration) {
        super(externalTexture, animationDuration);
        this.data = player.getData();
    }

    public void update(){
        setCurrentWidth(data.getPercentageOf(EntityData.EntityDataEnum.healthPoints));
    }
}
