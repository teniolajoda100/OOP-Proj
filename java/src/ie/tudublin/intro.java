package ie.tudublin;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PFont;

public class intro extends PApplet {
    Minim minim;
    AudioPlayer player;
    FFT fft;
    PFont font;
    //text to display
    String textToShow; 
    //start time when the sketch begins
    int startTime; 

    Main controller;

    int numStars = 1000; 
    float[][] stars;

    // the viewer's position and speed
    float viewerX = 0;
    float viewerY = 0;
    float viewerSpeedX = 3; 
    float viewerSpeedY = 1; 

    public void settings() {
        fullScreen(P3D, 0); // Adjust the parameters as needed
    }
    //assists with switching visual and getting audio from main
  
    public intro(Main controller, AudioPlayer player) {
        this.controller = controller;
        this.player = player;  // Ensure the player is passed and used correctly
    
    }

    public void setup() {
        minim = new Minim(this);
        //player = minim.loadFile("java/data/myuniverse.mp3");
        //fft = new FFT(player.bufferSize(), player.sampleRate());
       // player.play();

        stars = new float[numStars][2];
        for (int i = 0; i < numStars; i++) {
            stars[i][0] = random(-width, width); // random
            stars[i][1] = random(-height, height); 
        }

        font = createFont("Arial", 64);
        textFont(font);
        textToShow = "     ";
        startTime = millis();
    }

    public void draw() {
        background(8, 32, 50);

        // update the position based on speed
        viewerX += viewerSpeedX;
        viewerY += viewerSpeedY;

        drawStars();
        //LYRICS HERE
        if (millis() - startTime >= 2290) {
            textToShow = "YOU";
        }
        if (millis() - startTime >= 3250) {
            textToShow = "YOU ARE";
        }
        if (millis() - startTime >= 4300) {
            textToShow = "MY UNIVERSE";
        }

        if (millis() - startTime >= 6000) {
            textToShow = "AND...";
            
        }

        if (millis() - startTime >= 6790) {
            textToShow = "I";

        }

        if (millis() - startTime >= 7500) {
            textToShow = "JUST ";
            
        }
        if (millis() - startTime >= 7800) {
            textToShow = "WANT";
            
        }

        if (millis() - startTime >= 8500) {
            textToShow = "TO PUT YOU FIRST..";
           
        }

        //YOU YOU ARE MY UNIVERSE 
        if (millis() - startTime >= 11200) {
            textToShow = "YOU";
            
        }
        if (millis() - startTime >= 12100) {
            textToShow = "YOU ARE";
            
        }
        if (millis() - startTime >= 13350) {
            textToShow = "MY UNIVERSE";
            
        }
        if (millis() - startTime >= 14900) {
            textToShow = "AND";
            
        }
        if (millis() - startTime >= 15599) {
            textToShow = "I....";
            
        }
        drawGlowingText(textToShow, width / 2, height/2);
        
        if (millis() > 21000) {
            controller.switchToBuildings();
            noLoop(); 
            surface.setVisible(false);
        }

       
        
    }
    

    void drawGlowingText(String text, float x, float y) {
        //lighting
        lights();
        directionalLight(102, 255, 255, -1, 0, -0.5f); 
        ambientLight(50, 50, 100); 
    
     
        textFont(font, 64); 
        textAlign(CENTER, CENTER);
        fill(255, 255, 255); 
        noStroke();
    
        //3D rotation effect
        pushMatrix();
        translate(x, y);
        rotateX(-PI / 6); // rotate making the text look 3D
        rotateY(PI / 5);
    
        
        float depth = 20; 
        for (int i = 0; i < depth; i++) {
            pushMatrix();
            translate(0, 0, -i * 2); // moving text back in Z plane
            text(text, 0, 0); // Draw the text
            popMatrix();
        }
    
        popMatrix();
    }
    
    

    void drawStars() {
        fill(255);
        noStroke();
        for (int i = 0; i < numStars; i++) {
            float x = (stars[i][0] + viewerX + width) % width; // wrap around X direction
            float y = (stars[i][1] + viewerY + height) % height; // wrap around Y direction
            float size = random(1, 3);
            ellipse(x, y, size, size);
            float elapsed = millis() / 1000.0f;
            if (elapsed > 25) {  // After 25 seconds
                controller.switchToBuildings();  // Switch to Buildings sketch
                noLoop();  // Stop the draw loop
                //surface.setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("intro");
    }
}