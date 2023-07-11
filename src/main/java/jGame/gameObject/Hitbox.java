package jGame.gameObject;

import com.sun.istack.internal.NotNull;

/**
 * the object to check if two things hit together.
 */

public class Hitbox<E extends jGame.gameObject.hitboxShape.Shape> {
    private volatile E shape;

    private boolean isNull = false;

    /**
     * create a hittable hitbox
     *
     * @param shape the shape of the hitbox
     *
     * @see jGame.gameObject.hitboxShape.Shape
     */
    public Hitbox(E shape) {
        this.shape = shape;
    }

    /**
     * create a hitbox that won't hit any others
     */
    public Hitbox() {
        isNull = true;
    }

    /**
     * check if two hitbox hit together
     *
     * @param hitbox the other hitbox
     * @return if the two hitbox hit together
     */
    public boolean isHit(@NotNull Hitbox<E> hitbox) {
        if (hitbox.isNull || this.isNull)
            return false;
        else
            return shape.intersects(hitbox.getShape());
    }

    public E getShape() {
        return shape;
    }

    /**
     * check if the hitbox cannot hit by other
     *
     * @return is the hitbox cannot hit by other
     */
    public boolean cannotHit() {
        return isNull;
    }

    /**
     * set the shape of the hitbox
     *
     * @param shape the new shape if the hitbox
     */
    public void setShape(@NotNull E shape) {
        this.shape = shape;
    }
}
