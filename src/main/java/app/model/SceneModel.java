package main.java.app.model;

import java.util.ArrayList;
import java.util.List;

public class SceneModel {
    private List<Model3D> models;
    private Model3D activeModel;

    public SceneModel() {
        models = new ArrayList<>();
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

    public List<Model3D> getModels() { return models; }
    public Model3D getActiveModel() { return activeModel; }
    public void setActiveModel(Model3D model) { this.activeModel = model; }
}