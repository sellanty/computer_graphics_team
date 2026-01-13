package main.java.app.model;

import java.util.ArrayList;
import java.util.List;

public class SceneModel {
    private List<Model3D> models;
    private List<Camera> cameras;
    private Model3D activeModel;
    private Camera activeCamera;

    public SceneModel() {
        models = new ArrayList<>();
        cameras = new ArrayList<>();

        // Добавляем начальную камеру
        Camera defaultCamera = new Camera();
        cameras.add(defaultCamera);
        activeCamera = defaultCamera;
    }

    public void addModel(Model3D model) {
        models.add(model);
        if (activeModel == null) {
            activeModel = model;
        }
    }

    public void removeModel(Model3D model) {
        models.remove(model);
        if (activeModel == model) {
            activeModel = models.isEmpty() ? null : models.get(0);
        }
    }

    // Геттеры и сеттеры
    public List<Model3D> getModels() { return models; }
    public List<Camera> getCameras() { return cameras; }
    public Model3D getActiveModel() { return activeModel; }
    public void setActiveModel(Model3D model) { this.activeModel = model; }
    public Camera getActiveCamera() { return activeCamera; }
    public void setActiveCamera(Camera camera) { this.activeCamera = camera; }
}