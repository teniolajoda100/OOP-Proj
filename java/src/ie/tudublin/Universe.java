package ie.tudublin;
import java.util.ArrayList;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim; 
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Universe extends PApplet {

    Minim minim;
    AudioPlayer player;
    FFT fft;
    PGraphics staticElements;
    Main controller;


    public Universe(Main controller, AudioPlayer player) {
        this.player = player;
        this.controller = controller;
    }
    public void settings() {
        fullScreen(P3D, SPAN);
    } 

    public void setup() {
        minim = new Minim(this);
       // player = minim.loadFile("java/data/myuniverse.mp3");
        fft = new FFT(player.bufferSize(), player.sampleRate());
       // player.play();

        staticElements = createGraphics(width, height, P3D); // initialising the buffer so we can make the buildings stop redrawing 

        // drawing the static elements onto the buffer
        staticElements.beginDraw();
        background(staticElements);
        staticElements.endDraw();
    }

    // function to draw background
    void background(PGraphics pg) {
        pg.beginDraw();
        pg.background(0); //black for universe
        pg.endDraw();
    }

    public void draw() {
        background(0);
        image(staticElements, 0, 0); // draw static elements
        drawStars(); // draw the static elements with stars
        drawSun(); // draw our reacting moon
        // draw planets rotating around the sun
        drawEarth();
        drawVenus(); 
        drawMercury();
        drawMars();
        drawJupiter();
        drawSaturn();
        drawUranus();
        drawNeptune();
        if (millis() > 19000) {
            controller.switchToBuildings2();
            noLoop(); 
            surface.setVisible(false);
        }
    }

    private void drawStars() {
        fill(255); // colors of the star
        noStroke();
        int NumOfStars = 100; 
        for (int i = 0; i < NumOfStars; i++) {
            float x = random(width); 
            float y = random(height); 
            // random star size between 1, 3 
            float starSize = random(1, 3); 
            // draw each blinking star
            ellipse(x, y, starSize, starSize);
        }
    }

    private void drawSun() {
        
        float sunBaseSize = 50; 
        // get current audio level
        float level = player.mix.level();
       
        float sunSize = sunBaseSize + level * 200; 
        
        
        float sunX = width / 2.0f; // centered across the width
        float sunY = height / 2.0f; // centered vertically
    
        
        int points = 16; 
        float angle = TWO_PI / points;
    
        // draw the main sun with jagged edges
        noStroke();
         // brightest yellow
        fill(255, 255, 0); 
        beginShape();
        for (int i = 0; i < points; i++) {
            //calculate outer point
            float outerX = cos(angle * i) * sunSize * 0.5f + sunX;
            float outerY = sin(angle * i) * sunSize * 0.5f + sunY;
            vertex(outerX, outerY);
    
            //calculate inner point
            float innerX = cos(angle * i + angle / 2) * (sunSize * 0.4f) + sunX;
            float innerY = sin(angle * i + angle / 2) * (sunSize * 0.4f) + sunY;
            vertex(innerX, innerY);
        }
        endShape(CLOSE);
    
        // drawing glow 
        int auraLayers = 5; 
        for (int i = 0; i < auraLayers; i++) {
            float auraSize = sunSize * (1 + (i + 1) * 0.1f); 
            float alphaValue = 50 - i * 10; 
            fill(255, 255, 0, alphaValue); 
    
            //jagged edges
            beginShape();
            for (int j = 0; j < points; j++) {
                // calculate outer point
                float outerX = cos(angle * j) * auraSize * 0.5f + sunX;
                float outerY = sin(angle * j) * auraSize * 0.5f + sunY;
                vertex(outerX, outerY);
    
                // calculate inner point
                float innerX = cos(angle * j + angle / 2) * (auraSize * 0.4f) + sunX;
                float innerY = sin(angle * j + angle / 2) * (auraSize * 0.4f) + sunY;
                vertex(innerX, innerY);
            }
            endShape(CLOSE);
        }
    }
    
    
    // To store greenery positions relative to Earth
    ArrayList<PVector> greeneryPositions; 

void initGreenery(float earthSize) {
    greeneryPositions = new ArrayList<PVector>();
    int numOfGreenery = 10; // Number of green points
    for (int i = 0; i < numOfGreenery; i++) {
        float angle = random(TWO_PI);
        float radius = random(earthSize * 0.2f, earthSize * 0.4f); 
        greeneryPositions.add(new PVector(cos(angle) * radius, sin(angle) * radius));
    }
}

//drawEarth function to using stored greenery positions
private void drawEarth() {
    //float level = player.mix.level(); // Get current audio level
    float earthBaseSize = 20; 
    float earthSize = earthBaseSize; 
    float earthX = width / 2.0f + cos(frameCount * 0.01f) * 200; // position of Earth
    float earthY = height / 2.0f + sin(frameCount * 0.01f) * 200;


    // initialize greenery positions if not already done
    if (greeneryPositions == null || greeneryPositions.isEmpty()) {
        initGreenery(earthSize);
    }

    // draw Earth
    fill(135, 206, 235);
    noStroke();
    ellipse(earthX, earthY, earthSize, earthSize);

    // draw greenery
    stroke(34, 139, 34);
    strokeWeight(2);
    for (PVector pos : greeneryPositions) {
        point(earthX + pos.x, earthY + pos.y);
    }
    noStroke();
}
private void drawVenus() {
    //float level = player.mix.level(); // get current audio level
    float venusBaseSize = 18; 
    float venusSize = venusBaseSize; 
    float venusDistanceFromSun = 150; 
    float venusOrbitSpeed = 0.008f; 
    float venusX = width / 2.0f + cos(frameCount * venusOrbitSpeed) * venusDistanceFromSun; // venus position based on orbit
    float venusY = height / 2.0f + sin(frameCount * venusOrbitSpeed) * venusDistanceFromSun;

    
    fill(255, 223, 0); 
    noStroke();
    ellipse(venusX, venusY, venusSize, venusSize); 
}

private void drawMercury() {
   // float level = player.mix.level(); 
    float mercuryBaseSize = 12; 
    float mercurySize = mercuryBaseSize; 
    float mercuryDistanceFromSun = 100; 
    float mercuryOrbitSpeed = 0.015f; 
    float mercuryX = width / 2.0f + cos(frameCount * mercuryOrbitSpeed) * mercuryDistanceFromSun; 
    float mercuryY = height / 2.0f + sin(frameCount * mercuryOrbitSpeed) * mercuryDistanceFromSun;

    
    fill(169, 169, 169);  
    noStroke();
    ellipse(mercuryX, mercuryY, mercurySize, mercurySize); 
}

private void drawMars() {
    //float level = player.mix.level(); 
    float marsBaseSize = 17; 
    float marsSize = marsBaseSize; 
    float marsDistanceFromSun = 250; 
    float marsOrbitSpeed = 0.008f; 
    
    float marsX = width / 2.0f + cos(frameCount * marsOrbitSpeed) * marsDistanceFromSun; 
    float marsY = height / 2.0f + sin(frameCount * marsOrbitSpeed) * marsDistanceFromSun;

    
    fill(205, 133, 63); 
    noStroke();
    ellipse(marsX, marsY, marsSize, marsSize); 
}


private void drawJupiter() {
   // float level = player.mix.level(); 
    float jupiterBaseSize = 35; 
    float jupiterSize = jupiterBaseSize; 
    float jupiterDistanceFromSun = 400; 
    float jupiterOrbitSpeed = 0.007f; 
    float jupiterX = width / 2.0f + cos(frameCount * jupiterOrbitSpeed) * jupiterDistanceFromSun; 
    float jupiterY = height / 2.0f + sin(frameCount * jupiterOrbitSpeed) * jupiterDistanceFromSun;

    
    fill(204, 153, 102); 
    noStroke();
    ellipse(jupiterX, jupiterY, jupiterSize, jupiterSize); 

 
}

private void drawSaturn() {
    //float level = player.mix.level(); 
    float saturnBaseSize = 30; 
    float saturnSize = saturnBaseSize; 
    float saturnDistanceFromSun = 430; 
    float saturnOrbitSpeed = 0.006f; 
    float saturnX = width / 2.0f + cos(frameCount * saturnOrbitSpeed) * saturnDistanceFromSun; 
    float saturnY = height / 2.0f + sin(frameCount * saturnOrbitSpeed) * saturnDistanceFromSun;

    fill(238, 232, 170); 
    noStroke();
    ellipse(saturnX, saturnY, saturnSize, saturnSize);

    // draw saturns Rings reactively
    stroke(218, 165, 32); // gold color for the rings
    strokeWeight(2); 
    noFill();
    //rings' size based on the planet's size for a cohesive reactive effect
    ellipse(saturnX, saturnY, saturnSize * 1.4f, saturnSize * 0.5f * (saturnSize / saturnBaseSize)); // outer ring
    ellipse(saturnX, saturnY, saturnSize * 1.2f, saturnSize * 0.4f * (saturnSize / saturnBaseSize)); // inner ring
    noStroke(); 
}

private void drawUranus() {
    //float level = player.mix.level(); 
    float uranusBaseSize = 25; 
    float uranusSize = uranusBaseSize;
    float uranusDistanceFromSun = 450;
    float uranusOrbitSpeed = 0.005f; 
    float uranusX = width / 2.0f + cos(frameCount * uranusOrbitSpeed) * uranusDistanceFromSun; 
    float uranusY = height / 2.0f + sin(frameCount * uranusOrbitSpeed) * uranusDistanceFromSun;


    fill(173, 216, 230); 
    noStroke();
    ellipse(uranusX, uranusY, uranusSize, uranusSize); 
}

private void drawNeptune() {
    //float level = player.mix.level(); 
    float neptuneBaseSize = 24; 
    float neptuneSize = neptuneBaseSize; 
    float neptuneDistanceFromSun = 500; 
    float neptuneOrbitSpeed = 0.004f; 
    float neptuneX = width / 2.0f + cos(frameCount * neptuneOrbitSpeed) * neptuneDistanceFromSun;
    float neptuneY = height / 2.0f + sin(frameCount * neptuneOrbitSpeed) * neptuneDistanceFromSun;

   
    fill(28, 134, 238); 
    noStroke();
    ellipse(neptuneX, neptuneY, neptuneSize, neptuneSize); 
}

public static void main(String[] args) {
    PApplet.main("ie.tudublin.Universe");
}

}

