package ie.tudublin;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PGraphics;
import ddf.minim.*;

public class Buildings extends PApplet {

    Minim minim;
    AudioPlayer player;
    FFT fft;
    PGraphics staticElements;
    boolean runOtherClass = true;

    

    public void settings() {
        fullScreen(P3D, SPAN);
    } 
    public void setup() {
        minim = new Minim(this);
        player = minim.loadFile("java/data/Jb.mp3");
        fft = new FFT(player.bufferSize(), player.sampleRate());
        player.play();

      
        staticElements = createGraphics(width, height, P3D); // here we initialised the buffer so we can make the buildings stop redrawing 
    
        // here we draw the static elements onto the buffer
        staticElements.beginDraw();
        staticElements.background(48, 25, 52); // dark purp
        drawBuildings(staticElements); // passing the buffer to the method
        staticElements.endDraw();
    }

    public void draw() {
     
          image(staticElements, 0, 0); // ensuring it always draws the static elements
          drawStars(); // drawing the static elements with stars
        drawMoon(); // drawing our reacting moon
    }
    

    private void drawBuildings(PGraphics pg) {
      int numberOfBuildings = 10; // number of buildings we want
      float buildingWidth = pg.width / (float) numberOfBuildings; // using pg.width so we access the buffer
      for (int i = 0; i < numberOfBuildings; i++) {
          // parameters for the building
          float x = i * buildingWidth;
          float buildingHeight = random(100, 300); //using random() directly, not pg.random() so we dont get error
          float y = pg.height - buildingHeight; // using pg.height
  
          pg.fill(150,150,150,255); // colour of the buildings
          pg.noStroke();
          pg.rect(x, y, buildingWidth - 5, buildingHeight); // drawing the building body using pg
  
          // adding windows
          pg.fill(255, 255, 0,255); // windows colour, (yellow)
          int windowsPerRow = 4;
          float windowWidth = buildingWidth / (windowsPerRow + 2);
          float windowHeight = 20;
          for (int row = 0; row < buildingHeight / (windowHeight * 1.5); row++) {
              for (int col = 0; col < windowsPerRow; col++) {
                  float windowX = x + windowWidth * (col + 1);
                  float windowY = y + windowHeight * 1.5f * row;
                  pg.rect(windowX, windowY, windowWidth - 5, windowHeight - 5); // drawing windows using pg
              }
          }
  
          // adding roofs
          pg.fill(120,120,120,255); // roof colour
          pg.rect(x, y - 20, buildingWidth - 5, 20); // flat roof with pg
      }
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

private void drawMoon() {
  // calculate moon size based on audio level
  float moonBaseSize = 50; // base size of the moon
  float level = player.mix.level(); // get current audio level
  float moonSize = moonBaseSize + level * 200; // dynamic size adjustment

  // moon position 
  float moonX = width / 2.0f; // centered across the width
  float moonY = height / 3.0f; // positioned 1/3 down from the top

  // drawing the moon
  fill(255, 250, 205); // pale  moon color
  noStroke();
  ellipse(moonX, moonY, moonSize, moonSize);

  // static crater design based on moonBaseSize, ensure it doesnt react like the moon
  float[] craterSizes = {moonBaseSize / 6, moonBaseSize / 8, moonBaseSize / 10, moonBaseSize / 12, moonBaseSize / 14};
  float[] craterOffsetsX = {moonBaseSize / 4, -moonBaseSize / 3, moonBaseSize / 5, -moonBaseSize / 6, moonBaseSize / 8};
  float[] craterOffsetsY = {moonBaseSize / 5, -moonBaseSize / 4, moonBaseSize / 6, -moonBaseSize / 7, moonBaseSize / 9};

  // add craters with fixed sizes and positions depending to the moons center
  for (int i = 0; i < craterSizes.length; i++) {
      fill(200 - i * 10, 200 - i * 10, 170 - i * 5);
      float craterX = moonX + craterOffsetsX[i] * (moonSize / moonBaseSize); // scale position by current moon size
      float craterY = moonY + craterOffsetsY[i] * (moonSize / moonBaseSize);
      ellipse(craterX, craterY, craterSizes[i] * (moonSize / moonBaseSize), craterSizes[i] * (moonSize / moonBaseSize));
  }
}

}
  
  


