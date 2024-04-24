package ie.tudublin;

import processing.core.PApplet;
import processing.core.PImage;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class person2 extends PApplet {
    Main controller;
    PImage earthTexture;
    float rotationX = 0;
    float rotationY = 0;

    float moonRotation = 0;
    float moonOrbitRadius = 300;
    float moonOrbitSpeed = 0.01f;
    

    AudioPlayer song;
    BeatDetect beat;
    float baseRadius = 200;
    float moonRadius = 50;  
    int baseColor = color(255);

    int numStars = 1000; 
    float[][] stars;

    float viewerX = 0;
    float viewerY = 0;
    float viewerSpeedX = 3; 
    float viewerSpeedY = 1; 

    public person2(Main controller, AudioPlayer player) {
        this.controller = controller;
        this.song = player;
    }
    public void settings() {
        fullScreen(P3D, SPAN);
    }

    public void setup() {
        earthTexture = loadImage("java/data/earthtexture.jpeg"); 
        textureMode(NORMAL);

        Minim minim = new Minim(this);

        stars = new float[numStars][2];
        for (int i = 0; i < numStars; i++) {
            stars[i][0] = random(-width, width); 
            stars[i][1] = random(-height, height);
    }
}

    public void draw() {
        background(10, 20, 40);

        drawStars();

        camera(width / 2.0f, height / 2.0f, (height / 2.0f) / tan(PI * 30.0f / 180.0f),
               width / 2.0f, height / 2.0f, 0, 0, 1, 0);
    
        rotationX += 0.01;
        rotationY += 0.01;
    
        translate(width / 2.0f, height / 2.0f, 0);
        rotateX(rotationX);
        rotateY(rotationY);
    
        tint(baseColor); 
        drawEarth();
        noTint(); 

        float moonX = moonOrbitRadius * cos(moonRotation);
        float moonY = moonOrbitRadius * sin(moonRotation);

        pushMatrix();
        translate(moonX, moonY, 0);
        drawMoon();
        popMatrix();

        
        moonRotation += moonOrbitSpeed;
        if (millis() > 27000) {
            controller.switchToUniverse3();
            noLoop(); 
            surface.setVisible(false);
        }

    }

    private void drawStars() {
        fill(255);
        noStroke();
        for (int i = 0; i < numStars; i++) {
            float x = (stars[i][0] + viewerX + width) % width; 
            float y = (stars[i][1] + viewerY + height) % height;
            float size = random(1, 3);
            ellipse(x, y, size, size);
        }
    }

    private void drawEarth() {
        beginShape(TRIANGLE_STRIP);
        texture(earthTexture);
        float detail = 100;

        for (float lat = -HALF_PI; lat < HALF_PI; lat += PI / detail) {
            for (float lon = -PI; lon < PI; lon += TWO_PI / detail) {
                sphereVertex(lat, lon, baseRadius);
                sphereVertex(lat + PI / detail, lon, baseRadius);
            }
        }
        endShape();
    }

    private void drawMoon() {
        noStroke();
        float detail = 80;  
        beginShape(TRIANGLE_STRIP);
        for (float lat = -HALF_PI; lat < HALF_PI; lat += PI / detail) {
            for (float lon = -PI; lon < PI; lon += TWO_PI / detail) {
                float offset = map(noise(cos(lat) * cos(lon), cos(lat) * sin(lon), sin(lat)), 0, 1, -5, 5);
                int craterColor = color(200 - offset * 20); // Darker for deeper areas
                fill(craterColor);
                sphereVertex(lat, lon, moonRadius + offset);
                sphereVertex(lat + PI / detail, lon, moonRadius + offset);
            }
        }
        endShape();
    }

    private void sphereVertex(float lat, float lon, float r) {
        float x = r * cos(lat) * cos(lon);
        float y = r * cos(lat) * sin(lon);
        float z = r * sin(lat);
        float u = (lon + PI) / TWO_PI;
        float v = (lat + HALF_PI) / PI;
        vertex(x, y, z, u, v);
    }

    public void mouseDragged() {
        float rate = 0.01f;
        rotationY += (pmouseX - mouseX) * rate;
        rotationX -= (pmouseY - mouseY) * rate;
    }

    public static void main(String[] args) {
        PApplet.main("ie.tudublin.person");
    }
}