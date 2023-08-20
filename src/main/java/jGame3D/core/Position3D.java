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
    public Position to2D(Camera3D camera3D){
        //return null if it's same point
        if(this.equals(camera3D.getPosition3D())){
            return null;
        }

        //compute relative position
        double relativeX = this.x - camera3D.getPosition3D().x;
        double relativeY = this.y - camera3D.getPosition3D().y;
        double relativeZ = this.z - camera3D.getPosition3D().z;

        //compute rotation
        if(relativeX == 0 && relativeZ == 0){
            return null;
        }
        double rotationX = (relativeX==0) ? ((relativeZ > 0) ? 90 : 270) : Math.atan(relativeZ / relativeX)
                + camera3D.getRotationX()
                % 360;
        if(rotationX < 0){
            rotationX += 360;
        }
        if(!(rotationX < 90 || rotationX > 270)){
            return null;
        }

        if(relativeZ == 0 && relativeY == 0){
            return null;
        }
        double rotationY = ((relativeZ==0) ? ((relativeY > 0) ? 90 : 270) : Math.atan(relativeY / relativeZ)
                + camera3D.getRotationY())
                % 360;
        if(rotationY < 0){
            rotationY += 360;
        }
        if(!(rotationY<180)){
            return null;
        }

        //compute new point(after rotation)
        double newX = 0;
        double newY = 0;
        double newZ = 0;

        if(rotationX < 90){
            newX = Math.cos(90 - rotationX)
                    * new Position(0, 0).distance(new Position(relativeX, relativeZ));
            newZ = Math.sin(90 - rotationX)
                    * new Position(0, 0).distance(new Position(relativeX, relativeZ));
        }else{
            newX = Math.cos(rotationX)
                    * new Position(0, 0).distance(new Position(relativeX, relativeZ));
            newZ = Math.sin(rotationX)
                    * new Position(0, 0).distance(new Position(relativeX, relativeZ));
        }
        if(rotationY > 90){
            newY = Math.sin(180 - rotationY)
                    * new Position(0, 0).distance(new Position(relativeZ, relativeY));
        }else{
            newY = Math.sin(90 - rotationY)
                    * new Position(0, 0).distance(new Position(relativeZ, relativeY));
        }

        //compute and return the 2d position
        return new Position(
                ((((camera3D.getDeep())*(newX))+((newZ)*(camera3D.getScreen().getWidth()/2)))/(camera3D.getDeep()+newZ)),
                ((((camera3D.getDeep())*(newY))+((newZ)*(camera3D.getScreen().getHeight()/2)))/(camera3D.getDeep()+newZ))
        );
    }
}
