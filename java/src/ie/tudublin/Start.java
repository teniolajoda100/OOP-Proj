package ie.tudublin;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PGraphics;
import ddf.minim.*;


public class Start extends PApplet {
    Minim minim;
    AudioPlayer player;
    FFT fft;
    PGraphics staticElements;
    float[] fftSmooth; // array to smooth the data
    int bands = 8;
    

    
    public void settings() {
        fullScreen(P3D, SPAN);

    } 
    public void setup() {
        minim = new Minim(this);
        player = minim.loadFile("java/data/Jb.mp3");
        fft = new FFT(player.bufferSize(), player.sampleRate());
        player.play();
        fft = new FFT(player.bufferSize(), player.sampleRate());
        fft.logAverages(22, 3); // averages over logarithmic bands (can custom as needed)
        fft.window(FFT.HAMMING);
        fftSmooth = new float[bands];
    
        staticElements = createGraphics(width, height, P3D);
    
        staticElements.beginDraw();
        staticElements.endDraw();
    }
    
    public void draw() {
        background(135, 206, 235); // clear the main canvas
        image(staticElements, 0, 0);  //draw static variables
        fft.forward(player.mix);
        
        // smooth out the FFT data and draw the buildings
        ReactiveBuildings();
    }
    
    public void ReactiveBuildings() {
        // Get the FFT data
        fft.forward(player.mix);
    
        // Clear previous frame
        staticElements.beginDraw();
        staticElements.clear();
    
        int numberOfBuildings = 8;
        float buildingWidth = staticElements.width / (float) numberOfBuildings;
    
        for (int i = 0; i < numberOfBuildings; i++) {
            // increase the smoothing factor to make transitions more gradual
            fftSmooth[i] = lerp(fftSmooth[i], fft.getAvg(i), 0.2f); // adjust the val here to change smoothness (last val)
    
            // mapping the smoothed FFT value to a height for the building, lower more reactive or reaches the ceiling more
            float buildingHeight = map(fftSmooth[i], 0, 1, 100, staticElements.height / 5);
            //5 seems to be the perfect balance
    
            // drawing the building
            float x = i * buildingWidth;
            float y = staticElements.height - buildingHeight;
    
            staticElements.fill(150, 150, 150, 255); // colour of the buildings
            staticElements.noStroke();
            staticElements.rect(x, y, buildingWidth - 5, buildingHeight);
    
            
            fill(255, 255, 0, 255); // windows colour (yellow)
            int windowsPerRow = 4;
            float windowWidth = buildingWidth / (windowsPerRow + 2); //set window width using a calculation 
            float windowHeight = 20; // set window height
            for (int row = 0; row < (buildingHeight / (windowHeight * 1.5)); row++) {
                for (int col = 0; col < windowsPerRow; col++) {
                    float windowX = x + windowWidth * (col + 1);
                    float windowY = y + windowHeight * 1.5f * row;
                    //staticElements must be infront of fill to ensure every window panel is shaded
                    staticElements.fill(255, 255, 0, 255); //  windows color here
                    staticElements.rect(windowX, windowY, windowWidth - 5, windowHeight - 5); // drawing windows
                }
            }
                // adding roofs, must put staticElements, or the roof wont be stationed to the buildings
                staticElements.fill(120, 120, 120, 255); // roof color
                staticElements.rect(x, y - 20, buildingWidth - 5, 20); // ensuring this draws the roof correctly on top
        }
    
        staticElements.endDraw();
    }
    
    
    
    

}