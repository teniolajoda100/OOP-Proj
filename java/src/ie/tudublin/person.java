package ie.tudublin;

import processing.core.PApplet;
import processing.core.PImage;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class person extends PApplet {
    Main controller;

    
    

    PImage earthTexture;
    float rotationX = 0;
    float rotationY = 0;
    AudioPlayer song;
    BeatDetect beat;
    float baseRadius = 200;
    float currentRadius = baseRadius;
    int baseColor = color(255);
    int beatColor = color(255, 0, 0);  // Red color on beat
    int currentColor = baseColor;

    public person(Main controller, AudioPlayer player) {
        this.controller = controller;
        this.song = player;  // Ensure the player is passed and used correctly
    }

    public void settings() {
        fullScreen(P3D, SPAN);
    }

    public void setup() {
        earthTexture = loadImage("java/data/earthtexture.jpeg"); // Ensure you have an Earth texture image
        textureMode(NORMAL);

        Minim minim = new Minim(this);
        //song = minim.loadFile("java/data/myuniverse.mp3", 1024);
       // beat = new BeatDetect();
        //song.play();
    }

    public void draw() {
        background(10, 20, 40); // Dark blue background

        // Draw stars in the background
        drawStars(500);

        // Camera controls
        camera(width / 2.0f, height / 2.0f, (height / 2.0f) / tan(PI * 30.0f / 180.0f),
               width / 2.0f, height / 2.0f, 0, 0, 1, 0);
    
            currentRadius = baseRadius;
            currentColor = baseColor;
            rotationX += 0.01;  // Slow rotation normally
            rotationY += 0.01;
        
    
        // Translate and rotate globe
        translate(width / 2.0f, height / 2.0f, 0);
        rotateX(rotationX);
        rotateY(rotationY);
    
        // Apply current color tint and draw the textured Earth
        tint(currentColor);  // Apply tint based on beat
        drawEarth();
        noTint();  // Remove tint after drawing

        //Switching to the universe
        if (millis() > 9400) {
            controller.switchToUniverse();
            noLoop(); 
            surface.setVisible(false);
        }
    }

    private void drawEarth() {
        beginShape(TRIANGLE_STRIP);
        texture(earthTexture);
        float detail = 100;

        for (float lat = -HALF_PI; lat < HALF_PI; lat += PI / detail) {
            for (float lon = -PI; lon < PI; lon += TWO_PI / detail) {
                sphereVertex(lat, lon, currentRadius);
                sphereVertex(lat + PI / detail, lon, currentRadius);
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

    private void drawStars(int numStars) {
        randomSeed(0); // Ensure consistent random pattern
        fill(255); // White stars
        for (int i = 0; i < numStars; i++) {
            float x = random(-width, width);
            float y = random(-height, height);
            float z = random(-1000, 1000);
            point(x, y, z);
        }
    }
}