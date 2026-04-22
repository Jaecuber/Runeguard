package com.github.Jaecuber.ui.view;

import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.github.Jaecuber.ui.model.GameViewModel;
import com.github.tommyettinger.textra.TypingLabel;

public class GameView extends View<GameViewModel>{
    private ProgressBar healthBar;
    private ProgressBar staminaBar;

    public GameView(Stage stage, Skin skin, GameViewModel viewModel){
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUI(){
        Table table = new Table();
        table.padLeft(20.0f);
        table.padTop(20.0f);
        table.align(Align.topLeft);
        table.setFillParent(true);

        Table table1 = new Table();

        Container container = new Container();
        container.minSize(0, 0);
        container.prefSize(128, 128);
        container.fill();
        container.setBackground(skin.getDrawable("window"));
        Image image = new Image(skin, "playerIcon");
        image.setScaling(Scaling.fill);
        container.setActor(image);
        container.align(Align.center);
        table1.add(container).prefSize(128.0f);

        Table progressTable = new Table();

        healthBar = new ProgressBar(0.0f, 100.0f, 1.0f, false, skin, "healthBar");
        progressTable.add(healthBar).padBottom(10.0f).growX();

        progressTable.row();
        staminaBar = new ProgressBar(0.0f, 100.0f, 1.0f, false, skin, "staminaBar");
        progressTable.add(staminaBar).growX();

        table1.add(progressTable).align(Align.top).prefWidth(400.0f);
        table.add(table1).align(Align.topLeft);
        stage.addActor(table);
        
    }

    @Override
    protected void setupPropertyChanges(){
        viewModel.onPropertyChange(GameViewModel.PLAYER_DAMAGE, Map.Entry.class, this::showDamage);
        viewModel.onPropertyChange(GameViewModel.HEALTH, Integer.class, this::updateHealth);
        viewModel.onPropertyChange(GameViewModel.MAX_HEALTH, Integer.class, this::updateMaxHealth);
        viewModel.onPropertyChange(GameViewModel.STAMINA, Integer.class, this::updateStamina);
        viewModel.onPropertyChange(GameViewModel.MAX_STAMINA, Integer.class, this::updateMaxStamina);
    }

    private void showDamage(Map.Entry<Vector2, Integer> damAndPos){
        final Vector2 position = damAndPos.getKey();
        Integer damage = damAndPos.getValue();

        TypingLabel typingLabel = new TypingLabel("[%20]{SHAKE=1.0;0.8}{JUMP=0.5;0.5;0.9;0.5}{GRADIENT=ff0000ff;984848ff;1.0;5.0}{ENDGRADIENT}{SIZE=150%}" + damage, skin, "titleLabel");
        stage.addActor(typingLabel);

        typingLabel.addAction(
            Actions.parallel(
                Actions.sequence(Actions.delay(1.25f), Actions.removeActor()),
                Actions.forever(Actions.run(()->{
                    Vector2 stageCoords = toStageCoords(position);
                    
                    typingLabel.setPosition(stageCoords.x, stageCoords.y);
                }))
            )
        );
    }

    private void updateHealth(int health){
        healthBar.setValue(health);
    }

    private void updateMaxHealth(int maxHealth){
        healthBar.setRange(0.0f, (float) maxHealth);
    }

    private void updateStamina(int stamina){
        staminaBar.setValue(stamina);
    }

    private void updateMaxStamina(int maxStamina){
        staminaBar.setRange(0.0f, maxStamina);
    }

    private Vector2 toStageCoords(Vector2 gamePosition) {
        Vector2 resultPos = viewModel.toScreenCoords(gamePosition);
        stage.getViewport().unproject(resultPos);
        resultPos.y = stage.getViewport().getWorldHeight() - resultPos.y;
        return resultPos;
    }
}
