package br.com.conexaozero.prafrentequeseanda_dce2105ufmg;

import android.graphics.Point;

/**
 * Created by User on 20/04/15.
 */
public class CircleAttr extends Point {
    public int radius; //over_radius

    public CircleAttr() {}

    public CircleAttr(int radius) {
        super(0,0);
        this.radius = radius;
    }

    public CircleAttr(int x, int y) {
        this(x, y, 0);
    }

    public CircleAttr(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
    }

    public CircleAttr(CircleAttr src) {
        this.x = src.x;
        this.y = src.y;
        this.radius = src.radius;
    }

    public void set(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CircleAttr circleAttr = (CircleAttr) o;

        if (x != circleAttr.x) return false;
        if (y != circleAttr.y) return false;
        if (radius != circleAttr.radius) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + radius;
        return result;
    }

    @Override
    public String toString() {
        return "CircleAttr (" + x + ", " + y +  ", " + radius + ")";
    }
}
