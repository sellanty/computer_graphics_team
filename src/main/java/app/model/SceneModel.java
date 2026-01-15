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
        if (model == null) return;
        models.add(model);
        if (activeModel == null) {
            activeModel = model;
        }
    }

    public void removeModel(Model3D model) {
        if (model == null) return;
        models.remove(model);
        if (activeModel == model) {
            activeModel = models.isEmpty() ? null : models.get(0);
        }
    }

    public void removeModel(int index) {
        if (index >= 0 && index < models.size()) {
            Model3D model = models.get(index);
            removeModel(model);
        }
    }

    public List<Model3D> getModels() {
        return new ArrayList<>(models);
    }

    public Model3D getActiveModel() {
        return activeModel;
    }

    public void setActiveModel(Model3D model) {
        if (models.contains(model)) {
            this.activeModel = model;
        }
    }

    public void setActiveModel(int index) {
        if (index >= 0 && index < models.size()) {
            this.activeModel = models.get(index);
        }
    }

    public boolean hasModels() {
        return !models.isEmpty();
    }

    public int getModelCount() {
        return models.size();
    }
}