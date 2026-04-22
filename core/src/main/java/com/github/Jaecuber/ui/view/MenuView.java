package com.github.Jaecuber.ui.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.github.Jaecuber.Runeguard.asset.SoundAsset;
import com.github.Jaecuber.ui.model.MenuViewModel;

public class MenuView extends View<MenuViewModel>{

    private Table mainTable;
    private Table settingsTable;
    private Table loadTable;

    public MenuView(Stage stage, Skin skin, MenuViewModel viewModel) {
        super(stage, skin, viewModel);
    }

    @Override
    protected void setupUI(){
        setupMainContent();
        setupSettingsContent();
        setupLoadingContent();
    }

    public void setupMainContent(){
        mainTable = new Table();
        mainTable.setName("Main");
        mainTable.setBackground(skin.getDrawable("tempBkg"));
        mainTable.setFillParent(true);

        Image logoImage = new Image(skin, "RuneguardLogo");
        logoImage.setName("Logo");
        logoImage.setScaling(Scaling.fit);
        mainTable.add(logoImage).expand().prefWidth(1000.0f).prefHeight(500.0f);

        mainTable.row();
        VerticalGroup actionGroup = new VerticalGroup();
        actionGroup.fill();
        actionGroup.space(5.0f);

        TextButton newGameButton = new TextButton(" New Game ", skin, "mainTextButton");
        newGameButton.setName("newGame");
        onClick(newGameButton, viewModel::startGame);
        newGameButton.addListener(new InputListener(){
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
        actionGroup.addActor(newGameButton);

        TextButton loadButton = new TextButton("Load", skin, "mainTextButton");
        loadButton.setName("load");
        loadButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                loadTable.setVisible(true);
                viewModel.playSound(SoundAsset.POPUP);
            }
        });
        loadButton.addListener(new InputListener(){
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
        actionGroup.addActor(loadButton);

        TextButton settingsButton = new TextButton("Settings", skin, "mainTextButton");
        settingsButton.setName("settings");
        actionGroup.addActor(settingsButton);
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                settingsTable.setVisible(true);
                viewModel.playSound(SoundAsset.POPUP);
            }
        });
        settingsButton.addListener(new InputListener(){
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

        TextButton quitButton = new TextButton("Quit", skin, "mainTextButton");
        quitButton.setName("quit");
        
        onClick(quitButton, viewModel::quitGame);
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
        actionGroup.addActor(quitButton);
        mainTable.add(actionGroup).expand().align(Align.top);

        mainTable.row();
        Label creditsLabel = new Label("Jaecuber 2026", skin, "mediumLabel");
        creditsLabel.setAlignment(Align.bottomRight);
        mainTable.add(creditsLabel).expandX().align(Align.bottomRight);
        stage.addActor(mainTable);
    }

    public void setupSettingsContent(){
        settingsTable = new Table();
        settingsTable.setName("Settings");
        settingsTable.setTouchable(Touchable.childrenOnly);
        settingsTable.setFillParent(true);

        Container container = new Container();
        container.setName("settingsContainer");
        container.align(Align.top);
        container.padTop(20.0f);
        container.padBottom(20.0f);

        Table table1 = new Table();

        Label label = new Label("Settings", skin, "titleLabel");
        table1.add(label);

        table1.row();
        label = new Label("Music Volume", skin, "mediumLabel");
        label.setAlignment(Align.bottom);
        table1.add(label).align(Align.bottom).prefHeight(50.0f);

        table1.row();
        Slider musicSlider = new Slider(0.0f, 1.0f, 0.01f, false, skin, "default-horizontal");
        musicSlider.setValue(viewModel.getMusicVolume());
        table1.add(musicSlider).prefWidth(250.0f);
        onChange(musicSlider, (slider) -> viewModel.setMusicVolume(slider.getValue()));

        table1.row();
        label = new Label("Sound Effects", skin, "mediumLabel");
        label.setAlignment(Align.bottom);
        table1.add(label).align(Align.bottom).prefHeight(50.0f);

        table1.row();
        Slider soundSlider = new Slider(0.0f, 1.0f, 0.01f, false, skin, "default-horizontal");
        soundSlider.setValue(viewModel.getSoundVolume());
        table1.add(soundSlider).prefWidth(250.0f);
        onChange(soundSlider, (slider) -> viewModel.setSoundVolume(slider.getValue()));

        table1.row();
        label = new Label("Keybinds", skin, "mediumLabel");
        label.setAlignment(Align.bottom);
        table1.add(label).align(Align.bottom).prefHeight(50.0f);

        table1.row();
        Table table2 = new Table();

        label = new Label("Up", skin, "mediumLabel");
        table2.add(label).pad(5.0f);

        TextField textField = new TextField(null, skin);
        textField.setMaxLength(1);
        table2.add(textField).pad(5.0f);

        table2.row();
        label = new Label("Down", skin, "mediumLabel");
        table2.add(label).pad(5.0f);

        textField = new TextField(null, skin);
        textField.setMaxLength(1);
        table2.add(textField).pad(5.0f);

        table2.row();
        label = new Label("Left", skin, "mediumLabel");
        table2.add(label).pad(5.0f);

        textField = new TextField(null, skin);
        textField.setMaxLength(1);
        table2.add(textField).pad(5.0f);

        table2.row();
        label = new Label("Right", skin, "mediumLabel");
        table2.add(label).pad(5.0f);

        textField = new TextField(null, skin);
        textField.setMaxLength(1);
        table2.add(textField).pad(5.0f);

        table2.row();
        label = new Label("Attack", skin, "mediumLabel");
        table2.add(label).pad(5.0f);

        textField = new TextField(null, skin);
        textField.setMaxLength(1);
        table2.add(textField).pad(5.0f);
        table1.add(table2);

        table1.row();
        TextButton saveButton = new TextButton("Save Settings", skin, "medTextButton");
        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                settingsTable.setVisible(false);
            }
        });
        saveButton.setName("saveSettings");
        table1.add(saveButton);
        settingsTable.setVisible(false);
        container.setBackground(skin.getDrawable("window"));
        container.setActor(table1);
        settingsTable.add(container).prefWidth(400.0f).prefHeight(550.0f);
        stage.addActor(settingsTable);
    }

    public void setupLoadingContent(){         
        loadTable = new Table();
        loadTable.setName("LoadingTable");
        loadTable.setFillParent(true);

        Container container = new Container();

        Table nestedTable = new Table();

        nestedTable.add().grow();

        Button button = new Button(skin, "exitButton");
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                loadTable.setVisible(false);
            }
        });
        nestedTable.add(button).align(Align.topRight);

        nestedTable.row();
        nestedTable.add().grow();

        Label label = new Label("Ha! You thought I would implement \n"
                + "character loading! Unfortunately for\n"
                + "you, I am a lazy bum and will not add\n"
                + "it unless I work on this project over\n"
                + "summer! If there is any glimmer of\n"
                + "for this project, check in at the\n"
                + "repository on github. Who knows,\n"
                + "maybe I'll make this a complete game.\n"
                + "Atleast you can say you were here for\n"
                + "the demo version if you're reading \n"
                + "this.", skin, "mediumLabel");
        nestedTable.add(label).padLeft(45.0f).padRight(45.0f).padBottom(45.0f).grow();
        container.setActor(nestedTable);
        container.setBackground(skin.getDrawable("window"));
        loadTable.setVisible(false);
        loadTable.add(container);
        stage.addActor(loadTable);
    }
}
