package ie.tudublin;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import ddf.minim.*;

public class Audio extends PApplet {

    Minim minim;
    AudioPlayer player;
    FFT fft;

    public void settings() {
        fullScreen(P3D, SPAN);
    }

    public void setup() {
        minim = new Minim(this);
        player = minim.loadFile("java/data/Jb.mp3");
        fft = new FFT(player.bufferSize(), player.sampleRate());
        player.play();

        background(255);
        drawBuildings();
    }

    public void draw() {
        
    }

    private void drawBuildings() {
      int numberOfBuildings = 10; //number of buildings
      float buildingWidth = width / (float) numberOfBuildings;
      for (int i = 0; i < numberOfBuildings; i++) {
          // building parameters
          float x = i * buildingWidth;
          float buildingHeight = random(100, 300); // random height 
          float y = height - buildingHeight;
          fill(150); // Building color
          noStroke();
          rect(x, y, buildingWidth - 5, buildingHeight); // Draw the building body
  
          // add windows
          fill(255, 255, 0); // yellow windows
          int windowsPerRow = 4;
          float windowWidth = buildingWidth / (windowsPerRow + 2);
          float windowHeight = 20;
          for (int row = 0; row < buildingHeight / (windowHeight * 1.5); row++) {
              for (int col = 0; col < windowsPerRow; col++) {
                  float windowX = x + windowWidth * (col + 1);
                  float windowY = y + windowHeight * 1.5f * row;
                  rect(windowX, windowY, windowWidth - 5, windowHeight - 5);
              }
          }
  
          // adding roofs
          fill(120);
          // for a pitched roof
         // triangle(x, y, x + buildingWidth / 2 - 2.5f, y - 30, x + buildingWidth - 5, y);
          // for a a flat roof
          rect(x, y - 20, buildingWidth - 5, 20); // flat roof
      }
  }
  

}
