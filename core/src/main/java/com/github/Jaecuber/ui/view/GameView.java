package com.github.Jaecuber.ui.view;

import java.util.Map;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.Jaecuber.ui.model.GameViewModel;
import com.github.tommyettinger.textra.TypingLabel;

public class GameView extends View<GameViewModel>{

    public GameView(Stage stage, Skin skin, GameViewModel viewModel){
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUI(){

    }

    @Override
    protected void setupPropertyChanges(){
        viewModel.onPropertyChange(GameViewModel.PLAYER_DAMAGE, Map.Entry.class, this::showDamage);
    }

    private void showDamage(Map.Entry<Vector2, Integer> damAndPos){
        final Vector2 position = damAndPos.getKey();
        Integer damage = damAndPos.getValue();

        TypingLabel typingLabel = new TypingLabel("[%20]{SHAKE=1.0;0.8}{JUMP=0.5;0.5;0.9;0.5}{GRADIENT=ff0000ff;984848ff;1.0;5.0}{ENDGRADIENT}" + damage, skin, "titleLabel");
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

    private Vector2 toStageCoords(Vector2 gamePosition) {
        Vector2 resultPos = viewModel.toScreenCoords(gamePosition);
        stage.getViewport().unproject(resultPos);
        resultPos.y = stage.getViewport().getWorldHeight() - resultPos.y;
        return resultPos;
    }
}
