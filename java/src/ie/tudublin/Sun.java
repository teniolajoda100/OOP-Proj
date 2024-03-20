package ie.tudublin;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PGraphics;
import ddf.minim.*;


//Can draw an earth planet orbiting the sun
public class Sun extends PApplet {

    Minim minim;
    AudioPlayer player;
    FFT fft;
    PGraphics staticElements;


    

    public void settings() {
        fullScreen(P3D, SPAN);
    } 

    public void setup() {
    minim = new Minim(this);
    player = minim.loadFile("java/data/Jb.mp3");
    fft = new FFT(player.bufferSize(), player.sampleRate());
    player.play();

    staticElements = createGraphics(width, height, P3D); // initialize the buffer so we can make the buildings stop redrawing 
    
    // draw the static elements onto the buffer
    staticElements.beginDraw();
    background(staticElements);
    staticElements.endDraw();
}

// function to draw background
void background(PGraphics pg) {
    pg.beginDraw();
    pg.background(0); // starting with black f
    pg.endDraw();
}



    public void draw() {
     
        image(staticElements, 0, 0); // ensuring it always draws the static elements, and ensures moon and the stars are reset and redrawn
        drawStars(); // drawing the static elements with stars
        drawSun(); // drawing our reacting moon
    }

  private void drawStars() {
    fill(255); //colours of the star
    noStroke();
    int NumOfStars = 300; // number of stars, can change for mroe stars
    for (int i = 0; i < NumOfStars; i++) {
        float x = random(width); // random x position
        float y = random(height); // random y position
        float starSize = random(1, 3); // random star size between 1, 3 can change 
        ellipse(x, y, starSize, starSize); // draw each blinking star
    }
}

public void drawSun() {
    // calculate sun size based on audio level
    float sunBaseSize = 50; // base size of the sun
    float level = player.mix.level(); // get current audio level
    float sunSize = sunBaseSize + level * 200; // dynamic size adjustment
  
    // sun position 
    float sunX = width / 2.0f; // centered across the width
    float sunY = height / 2.0f; // centered vertically
  
    // draw the main sun ellipse with the brightest yellow
    noStroke();
    fill(255, 255, 0); // Brightest yellow
    ellipse(sunX, sunY, sunSize, sunSize);
  
    
  
   
    }
  }
  
  
  

