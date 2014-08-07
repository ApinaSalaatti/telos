package com.dogshitempire.firstcause.playerview.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dogshitempire.firstcause.logic.stages.GameStage;

public class ResearchScreen extends GameScreen {
	private Skin skin;
	
	@Override
	public void onAdd(GameStage stage) {
		super.onAdd(stage);
		
		skin = new Skin(Gdx.files.internal("assets/ui/uiskin.json"));
		
		final TextButton button = new TextButton("Click me", skin, "default");
        
        button.setWidth(200f);
        button.setHeight(20f);
        button.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 10f);
        
        button.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	Gdx.app.log("KATOS", "KLIKKAUSSS!!");
                button.setText("You clicked the button");
            }
        });
        
        button.addListener(new ChangeListener(){
            @Override 
            public void changed(ChangeEvent event, Actor a){
            	Gdx.app.log("KATOS", "MUUTOOOOOS!!");
                button.setText("You clicked the button");
            }
        });
        
        addScreenElement(button);
	}
}
