package jGame3D.core;

import jGame.core.Position;
import jGame3D.loop.render.Camera3D;

public class Position3D {
    private final double x;
    private final double y;
    private final double z;

    public Position3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public int getIntX() {
        return (int) x;
    }

    public Position3D setX(double x) {
        return new Position3D(
                x,
                this.y,
                this.z
        );
    }

    public double getY() {
        return y;
    }

    public int getIntY() {
        return (int) y;
    }

    public Position3D setY(double y) {
        return new Position3D(
                this.x,
                y,
                this.z
        );
    }

    public double getZ() {
        return z;
    }

    public int getIntZ() {
        return (int) z;
    }

    public Position3D setZ(double z) {
        return new Position3D(
                this.x,
                this.y,
                z
        );
    }

    public double distance(Position3D position3D) {
        return Math.sqrt(
                Math.pow(new Position(this.x, this.y).distance(new Position(position3D.x, position3D.y)), 2) +
                        Math.pow(this.z - position3D.z, 2)
        );
    }

    public static double distance(Position3D position3D1, Position3D position3D2) {
        return Math.sqrt(
                Math.pow(new Position(position3D1.x, position3D1.y).distance(new Position(position3D2.x, position3D2.y)), 2) +
                        Math.pow(position3D1.z - position3D2.z, 2)
        );
    }

    @Override
    public boolean equals(Object object){
        Position3D position3D = (Position3D) object;
        return this.x == position3D.x && this.y == position3D.y && this.z == position3D.z;
    }

    /**
     *
     * position of the camera is at the left bottom corner
     *
     * 0 of the rotationX is straight ahead
     * and 0 of the rotation is above
     *
     * @param camera3D
     * @return position of the point shown on the screen, or null if the object is on camera or the point is behind the view of the camera
     */
    public Position to2D(Camera3D camera3D) {
        if (this.equals(camera3D.getPosition3D())) {
            return null;
        }

        double relativeX = this.x - camera3D.getPosition3D().x;
        double relativeY = this.y - camera3D.getPosition3D().y;
        double relativeZ = this.z - camera3D.getPosition3D().z;

        // Compute rotation angles in radians
        double rotationX = computeRotationAngle(Math.atan2(relativeZ, relativeX), camera3D.getRotationX());
        double rotationY = computeRotationAngle(Math.atan2(relativeY, relativeZ), camera3D.getRotationY());

        if (!isValidRotation(rotationX, rotationY)) {
            return null;
        }

        // Compute new coordinates after rotation
        double distanceXZ = Math.sqrt(relativeX * relativeX + relativeZ * relativeZ);
        double distanceZY = Math.sqrt(relativeZ * relativeZ + relativeY * relativeY);

        double newX = computeNewCoordinate(rotationX, distanceXZ);
        double newY = computeNewCoordinate(rotationY, distanceZY);
        double newZ = distanceXZ;

        // Compute and return the 2D position
        double screenHalfWidth = camera3D.getScreen().getWidth() / 2;
        double screenHalfHeight = camera3D.getScreen().getHeight() / 2;

        double screenX = ((camera3D.getDeep() * newX) + (newZ * screenHalfWidth)) / (camera3D.getDeep() + newZ);
        double screenY = ((camera3D.getDeep() * newY) + (newZ * screenHalfHeight)) / (camera3D.getDeep() + newZ);

        return new Position(screenX, screenY);
    }

    private double computeRotationAngle(double angle, double cameraRotation) {
        double rotation = Math.toDegrees(angle) + cameraRotation;
        rotation %= 360;
        if (rotation < 0) {
            rotation += 360;
        }
        return rotation;
    }

    private boolean isValidRotation(double rotationX, double rotationY) {
        return (rotationX < 90 || rotationX > 270) && rotationY < 180;
    }

    private double computeNewCoordinate(double angle, double distance) {
        return Math.cos(Math.toRadians(angle)) * distance;
    }

}
