package com.example.demo.Models;

import javafx.scene.Scene;

public class SceneModel {
    public static Scene myScene;

    public static Scene getMyScene() {
        return myScene;
    }

    public static void setMyScene(Scene myScene) {
        SceneModel.myScene = myScene;
    }
}
