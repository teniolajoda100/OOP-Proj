package ie.tudublin;

import processing.core.PApplet;

public class person extends PApplet {

    public void settings() {
        fullScreen(P3D, SPAN);
    }

    public void setup() {
  
        
    }

    public void draw() {
        background(255); // white
        // Head
        ellipse(100, 50, 30, 30); // draw the head (circle)
        
        // body
        line(115, 80, 115, 130); // draw the body (line)
        
        // arms
        line(115, 85, 95, 110); // draw the left arm
        line(115, 85, 135, 110); // draw the right arm
        
        // legs - in a walking position
        line(115, 130, 105, 160); // left leg
        line(115, 130, 125, 160); // right leg
    }

    public static void main(String[] args) {
        // use the PApplet  method to run the sketch
        PApplet.main("ie.tudublin.person");
    }
}
