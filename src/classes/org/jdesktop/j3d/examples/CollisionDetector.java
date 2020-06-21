package org.jdesktop.j3d.examples;

import com.sun.j3d.utils.geometry.Sphere;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.vecmath.Point3d;
import java.util.Enumeration;


public  class CollisionDetector extends Behavior {

    Sphere element;
    public static boolean inCollision = false;
    private WakeupOnCollisionEntry wEnter;
    private WakeupOnCollisionExit wExit;


/** Konstruktor klasy CollisonDetector.
 * @param obiekt Obiekt typu Sphere. To na nim będzie sprawdzane czy zachodzi kolizja.
 * @param sphere  Obiekty typu BoundingSphere. Wewnątrz tego obszaru będą sprawdzane kolizje.
 */

    public CollisionDetector(Sphere obiekt){

        inCollision = false;
        element = obiekt;
        BoundingSphere ogr= new BoundingSphere(new Point3d(), 0.005d);
        element.setCollisionBounds(ogr);
    }

    /** Metoda inicjalizująca. */
    @Override
    public void initialize(){
        wEnter = new WakeupOnCollisionEntry(element);
        wExit = new WakeupOnCollisionExit(element);
        wakeupOn(wEnter);

    }

    /** Reaguje na pojawienie się lub zniknięcie kolizji.*/
    @Override
    public void processStimulus(Enumeration criteria){

        inCollision = !inCollision;

        if (inCollision) {
            System.out.println("Weszlo");
            wakeupOn(wExit);
        }
        else {
            System.out.println("Wyszlo");
            wakeupOn(wEnter);
        }

    }


}
