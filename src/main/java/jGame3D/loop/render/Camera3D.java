package jGame3D.loop.render;

import jGame.core.Size;
import jGame3D.core.Position3D;

public class Camera3D {
    private Position3D position3D;
    private Size screen;
    private double rotationX;
    private double rotationY;
    private double deep;

    public Camera3D(Position3D position3D, Size screen, double rotationX, double rotationY, double deep){
        this.position3D = position3D;
        this.screen = screen;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.deep = deep;
    }

    public Position3D getPosition3D() {
        return position3D;
    }

    public void setPosition3D(Position3D position3D) {
        this.position3D = position3D;
    }

    public Size getScreen() {
        return screen;
    }

    public void setScreen(Size screen) {
        this.screen = screen;
    }

    public double getRotationX() {
        return rotationX;
    }

    public void setRotationX(double rotationX) {
        this.rotationX = rotationX;
    }

    public double getRotationY() {
        return rotationY;
    }

    public void setRotationY(double rotationY) {
        this.rotationY = rotationY;
    }

    public double getDeep() {
        return deep;
    }

    public void setDeep(double deep) {
        this.deep = deep;
    }
}
