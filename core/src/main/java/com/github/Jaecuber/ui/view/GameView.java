package com.github.Jaecuber.ui.view;

import java.util.Map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.Jaecuber.Runeguard.asset.MusicAsset;
import com.github.Jaecuber.Runeguard.asset.SoundAsset;
import com.github.Jaecuber.Runeguard.component.UpgradeTags;
import com.github.Jaecuber.Runeguard.data.UpgradeClass;
import com.github.Jaecuber.Runeguard.data.UpgradeEntry;
import com.github.Jaecuber.ui.model.GameViewModel;
import com.github.tommyettinger.textra.TypingLabel;

public class GameView extends View<GameViewModel>{
    private ProgressBar healthBar;
    private ProgressBar staminaBar;
    private Table gameOverTable;
    private Label gameOverLabel;
    private Label levelLabel;
    private Label waveLabel;
    private Label timerLabel;
    private Table transitionTable;

    //Upgrade Screen
    private Table upgradeTable;
    private Table upg1Table;
    private Table upg2Table;
    private Table upg3Table;
    private Label upg1Title;
    private Label upg2Title;
    private Label upg3Title;
    private Label upg1Desc;
    private Label upg2Desc;
    private Label upg3Desc;

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

        Table statsTable = new Table();
        statsTable.padRight(20.0f);
        statsTable.padTop(20.0f);
        statsTable.align(Align.topRight);
        statsTable.setFillParent(true);

        levelLabel = new Label("Level : 1", skin, "mediumLabel");
        levelLabel.setColor(skin.getColor("White"));
        statsTable.add(levelLabel).spaceTop(10.0f).spaceBottom(10.0f).align(Align.left);;

        statsTable.row();
        waveLabel = new Label("Wave : 1", skin, "mediumLabel");
        waveLabel.setColor(skin.getColor("White"));
        statsTable.add(waveLabel).spaceTop(10.0f).spaceBottom(10.0f).align(Align.left);;

        statsTable.row();
        timerLabel = new Label("00:00", skin, "mediumLabel");
        timerLabel.setColor(skin.getColor("White"));
        statsTable.add(timerLabel).spaceTop(10.0f).spaceBottom(10.0f).align(Align.left);;
        stage.addActor(statsTable);
        setupGameOver();
        setupUpgradeScreen();
        setupTransition();
    }

    @Override
    protected void setupPropertyChanges(){
        viewModel.onPropertyChange(GameViewModel.PLAYER_DAMAGE, Map.Entry.class, this::showDamage);
        viewModel.onPropertyChange(GameViewModel.HEALTH, Integer.class, this::updateHealth);
        viewModel.onPropertyChange(GameViewModel.MAX_HEALTH, Integer.class, this::updateMaxHealth);
        viewModel.onPropertyChange(GameViewModel.STAMINA, Integer.class, this::updateStamina);
        viewModel.onPropertyChange(GameViewModel.MAX_STAMINA, Integer.class, this::updateMaxStamina);
        viewModel.onPropertyChange(GameViewModel.GAME_OVER, Boolean.class, this::gameOverScreen);
        viewModel.onPropertyChange(GameViewModel.UPGRADE, Boolean.class, this::upgradeScreen);
        viewModel.onPropertyChange(GameViewModel.LEVEL, Integer.class, this::updateLevel);
        viewModel.onPropertyChange(GameViewModel.WAVE, Integer.class, this::updateWave);
        viewModel.onPropertyChange(GameViewModel.TIMER, Integer.class, this::updateTimer);
        viewModel.onPropertyChange(GameViewModel.TRANSITION, Boolean.class, this::transition);
    };

    private void showDamage(Map.Entry<Vector2, Integer> damAndPos){
        final Vector2 position = damAndPos.getKey();
        Integer damage = damAndPos.getValue();

        TypingLabel typingLabel = new TypingLabel("[%20]{SHAKE=1.0;0.8}{JUMP=0.5;0.5;0.9;0.5}{GRADIENT=ff0000ff;984848ff;1.0;5.0}{ENDGRADIENT}{SIZE=125%}" + damage, skin, "titleLabel");
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

    private void setupTransition(){
        transitionTable = new Table();
        transitionTable.setTouchable(Touchable.disabled);
        transitionTable.setVisible(true);
        transitionTable.setBackground(skin.getDrawable("transition"));
        transitionTable.setFillParent(true);
        transitionTable.getColor().a = 1f;

        transitionTable.add();
        stage.addActor(transitionTable);
        viewModel.playMusic(MusicAsset.BEGINNING_AMBIENCE);
        transitionTable.addAction(Actions.sequence(
            Actions.delay(2.0f),
            Actions.fadeOut(2.0f)
        ));
    }

    private void setupGameOver(){
        gameOverTable = new Table();
        gameOverTable.setTouchable(Touchable.disabled);
        gameOverTable.setBackground(skin.getDrawable("gameOverBkg"));
        gameOverTable.setFillParent(true);

        gameOverLabel = new Label("Game Over", skin, "LargeTitle");
        gameOverLabel.setAlignment(Align.center);
        gameOverLabel.getColor().a = 0.0f;
        gameOverTable.add(gameOverLabel).grow();

        gameOverTable.row();
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(10.0f);

        TextButton continueButton = new TextButton(" Continue ", skin, "mainTextButton");
        verticalGroup.addActor(continueButton);
        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                viewModel.continueGame();
            }
        });
        continueButton.addListener(new InputListener(){
            long lastEnterTime = 0;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastEnterTime > 50){
                    viewModel.playSound(SoundAsset.HOVER);
                    lastEnterTime = currentTime;
                }
            }
        });

        TextButton quitButton = new TextButton(" Quit ", skin, "mainTextButton");
        verticalGroup.addActor(quitButton);
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                viewModel.quitGame();
            }
        });
        quitButton.addListener(new InputListener(){
            long lastEnterTime = 0;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastEnterTime > 50){
                    viewModel.playSound(SoundAsset.HOVER);
                    lastEnterTime = currentTime;
                }
            }
        });
        gameOverTable.add(verticalGroup).grow();
        gameOverTable.setVisible(false);
        gameOverTable.getColor().a = 0f;
        stage.addActor(gameOverTable);
    }

    private void setupUpgradeScreen(){
        upgradeTable = new Table();
        upgradeTable.setBackground(skin.getDrawable("upgradeScreenBkg"));
        upgradeTable.setFillParent(true);
        upgradeTable.setTouchable(Touchable.disabled);

        Label label = new Label("Choose One", skin, "titleLabel");
        upgradeTable.add(label);

        upgradeTable.row();
        upg1Table = new Table();
        upg1Table.setBackground(skin.getDrawable("BasicUpgradeBkg"));
        upg1Table.padLeft(230.0f);
        upg1Table.align(Align.left);

        upg1Title = new Label("PLACEHOLDER", skin, "titleLabel");
        upg1Table.add(upg1Title).padBottom(15.0f).align(Align.left);

        upg1Table.row();
        upg1Desc = new Label("PLACEHOLDER", skin, "mediumLabel");
        upg1Desc.setWrap(true);
        upg1Table.add(upg1Desc).padRight(20.0f).growX().align(Align.left);
        upgradeTable.add(upg1Table);

        upgradeTable.row();
        upg2Table = new Table();
        upg2Table.setBackground(skin.getDrawable("BasicUpgradeBkg"));
        upg2Table.padLeft(230.0f);
        upg2Table.align(Align.left);

        upg2Title = new Label("PLACEHOLDER", skin, "titleLabel");
        upg2Table.add(upg2Title).padBottom(15.0f).align(Align.left);

        upg2Table.row();
        upg2Desc = new Label("PLACEHOLDER", skin, "mediumLabel");
        upg2Desc.setWrap(true);
        upg2Table.add(upg2Desc).padRight(20.0f).growX().align(Align.left);
        upgradeTable.add(upg2Table);

        upgradeTable.row();
        upg3Table = new Table();
        upg3Table.setBackground(skin.getDrawable("BasicUpgradeBkg"));
        upg3Table.padLeft(230.0f);
        upg3Table.align(Align.left);

        upg3Title = new Label("PLACEHOLDER", skin, "titleLabel");
        upg3Table.add(upg3Title).padBottom(15.0f).align(Align.left);

        upg3Table.row();
        upg3Desc = new Label("PLACEHOLDER", skin, "mediumLabel");
        upg3Desc.setWrap(true);
        upg3Table.add(upg3Desc).padRight(20.0f).growX().align(Align.left);
        upgradeTable.add(upg3Table);
        upgradeTable.setVisible(false);
        upgradeTable.getColor().a = 0.0f;
        stage.addActor(upgradeTable);

    }

    private void gameOverScreen(boolean bool){
        gameOverTable.setVisible(bool);
        gameOverTable.setTouchable(Touchable.enabled);
        gameOverTable.addAction(Actions.sequence(
            Actions.delay(1.0f),
            Actions.fadeIn(1.0f)
        ));
        gameOverLabel.addAction(Actions.sequence(
            Actions.delay(1.5f),
            Actions.fadeIn(0.25f)
        ));
    }

    private void transition(boolean bool){
        transitionTable.setVisible(bool);
        viewModel.playSound(SoundAsset.BOOM);
        transitionTable.addAction(Actions.fadeIn(0.5f));
        upgradeTable.addAction(Actions.sequence(
            Actions.delay(1.5f),
            Actions.fadeOut(0.01f)
        ));
        transitionTable.addAction(Actions.sequence(
            Actions.delay(3.0f),
            Actions.fadeOut(2.0f)
        ));
        upgradeTable.setTouchable(Touchable.disabled);
    }

    private void upgradeScreen(boolean bool){
        UpgradeClass upgradeClasses = viewModel.loadUpgradeClasses();
        UpgradeEntry upgrade1 = getUpgrade1(upgradeClasses);
        UpgradeEntry upgrade2 = getUpgrade2(upgradeClasses);
        UpgradeEntry upgrade3 = getUpgrade3(upgradeClasses);
        upgradeTable.setTouchable(Touchable.disabled);

        Entity playerEntity = viewModel.getPlayerEntity();
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(playerEntity);
        if(upgradeTags == null) throw new GdxRuntimeException("No upgrade tags for player entity");

        //Setting table backgrounds
        switch (upgrade1.getRarity()) {
            case "Basic" -> {upg1Table.setBackground(skin.getDrawable("BasicUpgradeBkg"));}
            case "Rare" -> {upg1Table.setBackground(skin.getDrawable("RareUpgradeBkg"));}
            case "Legendary" -> {upg1Table.setBackground(skin.getDrawable("LegendaryUpgradeBkg"));}
            case "Cursed" -> {upg1Table.setBackground(skin.getDrawable("CursedUpgradeBkg"));}
        }
        switch (upgrade2.getRarity()) {
            case "Basic" -> {upg2Table.setBackground(skin.getDrawable("BasicUpgradeBkg"));}
            case "Rare" -> {upg2Table.setBackground(skin.getDrawable("RareUpgradeBkg"));}
            case "Legendary" -> {upg2Table.setBackground(skin.getDrawable("LegendaryUpgradeBkg"));}
            case "Cursed" -> {upg2Table.setBackground(skin.getDrawable("CursedUpgradeBkg"));}
        }
        switch (upgrade3.getRarity()) {
            case "Basic" -> {upg3Table.setBackground(skin.getDrawable("BasicUpgradeBkg"));}
            case "Rare" -> {upg3Table.setBackground(skin.getDrawable("RareUpgradeBkg"));}
            case "Legendary" -> {upg3Table.setBackground(skin.getDrawable("LegendaryUpgradeBkg"));}
            case "Cursed" -> {upg3Table.setBackground(skin.getDrawable("CursedUpgradeBkg"));}
        }

        //Setting table titles
        upg1Title.setText(upgrade1.getName() + " [" + upgrade1.getRarity() + "]");
        upg2Title.setText(upgrade2.getName() + " [" + upgrade2.getRarity() + "]");
        upg3Title.setText(upgrade3.getName() + " [" + upgrade3.getRarity() + "]");

        //Setting table descriptions
        upg1Desc.setText(upgrade1.getDescription());
        upg2Desc.setText(upgrade2.getDescription());
        upg3Desc.setText(upgrade3.getDescription());

        //Setting Listeners
        upg1Table.clearListeners();
        upg2Table.clearListeners();
        upg3Table.clearListeners();

        upg1Table.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                viewModel.addUpgradeTag(upgrade1.getName(), 1);
            }
        });
        upg1Table.addListener(new InputListener(){
            long lastEnterTime = 0;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastEnterTime > 50){
                    viewModel.playSound(SoundAsset.HOVER);
                    lastEnterTime = currentTime;
                }
            }
        });

        upg2Table.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                viewModel.addUpgradeTag(upgrade2.getName(), 1);
            }
        });
        upg2Table.addListener(new InputListener(){
            long lastEnterTime = 0;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastEnterTime > 50){
                    viewModel.playSound(SoundAsset.HOVER);
                    lastEnterTime = currentTime;
                }
            }
        });

        upg3Table.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                viewModel.addUpgradeTag(upgrade3.getName(), 1);
            }
        });
        upg3Table.addListener(new InputListener(){
            long lastEnterTime = 0;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastEnterTime > 50){
                    viewModel.playSound(SoundAsset.HOVER);
                    lastEnterTime = currentTime;
                }
            }
        });

        upgradeTable.setVisible(bool);
        upgradeTable.setTouchable(Touchable.enabled);
        upgradeTable.addAction(Actions.sequence(
            Actions.delay(1.0f),
            Actions.fadeIn(1.0f)
        ));
    }

    private UpgradeEntry getUpgrade1(UpgradeClass upgradeClasses) {
        int classDec = MathUtils.random(0, 100);
        Array<UpgradeEntry> entryBag;
        if(classDec < 30){
            entryBag = upgradeClasses.getRareUpgrades();
        }else{
            entryBag = upgradeClasses.getBasicUpgrades();
        }

        int upgDec = MathUtils.random(0, entryBag.size - 1);

        return entryBag.get(upgDec);
    }

    private UpgradeEntry getUpgrade2(UpgradeClass upgradeClasses) {
        int classDec = MathUtils.random(0, 100);
        Array<UpgradeEntry> entryBag;
        if(classDec < 5){
            entryBag = upgradeClasses.getLegendaryUpgrades();
        }else if(classDec < 30){
            entryBag = upgradeClasses.getRareUpgrades();
        }else{
            entryBag = upgradeClasses.getBasicUpgrades();
        }

        int upgDec = MathUtils.random(0, entryBag.size - 1);

        return entryBag.get(upgDec);
    }

    private UpgradeEntry getUpgrade3(UpgradeClass upgradeClasses) {
        int classDec = MathUtils.random(0, 100);
        Array<UpgradeEntry> entryBag;
        if(classDec < 10){
            entryBag = upgradeClasses.getLegendaryUpgrades();
        }else if(classDec < 70){
            entryBag = upgradeClasses.getBasicUpgrades();
        }else{
            entryBag = upgradeClasses.getCursedUpgrades();
        }

        int upgDec = MathUtils.random(0, entryBag.size - 1);

        return entryBag.get(upgDec);
    }

    private void updateLevel(int level){
        levelLabel.setText("Level : " + level);
    }

    private void updateWave(int wave){
        waveLabel.setText("Wave : " + wave);
    }

    private void updateTimer(int time){
        if(time < 0) return;
        if(time > 60){
            if(time/60 < 10){
                if(time % 60 < 10){
                    timerLabel.setText("0" + time/60 + ":0" + time % 60);
                }
                timerLabel.setText("0" + time/60 + ":" + time % 60);
            }
            timerLabel.setText(time/60 + ":" + time % 60);
        }else if(time < 60 && time >= 10){
            timerLabel.setText("00:" + time);
        }else{
            timerLabel.setText("00:" + "0" + time);
        }
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
