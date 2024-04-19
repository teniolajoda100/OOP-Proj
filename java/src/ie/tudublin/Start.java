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
        fft.logAverages(22, 3); // averages over logarithmic bands (can custom as needed)
        fft.window(FFT.HAMMING);
        fftSmooth = new float[bands];

        staticElements = createGraphics(width, height, P3D);

        staticElements.beginDraw();
        staticElements.endDraw();
    }

    public void draw() {
        background(135, 206, 235); 
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

        // Define a set of shades of grey and black
        int[][] colors = new int[][]{
            {50, 50, 50},   // Dark Grey
            {80, 80, 80},   // Less Dark Grey
            {110, 110, 110},// Medium Grey
            {140, 140, 140},// Medium Light Grey
            {170, 170, 170},// Light Grey
            {200, 200, 200},// Lighter Grey
            {230, 230, 230},// Very Light Grey
            {0, 0, 0}       // Black
        };

        for (int i = 0; i < numberOfBuildings; i++) {
            fftSmooth[i] = lerp(fftSmooth[i], fft.getAvg(i), 0.25f);
            float buildingHeight = map(fftSmooth[i], 0, 1, 100, staticElements.height / 5);
            float x = i * buildingWidth;
            float y = staticElements.height - buildingHeight;

            // Set building color from the predefined array
            staticElements.fill(colors[i % colors.length][0], colors[i % colors.length][1], colors[i % colors.length][2], 255);
            staticElements.noStroke();
            staticElements.rect(x, y, buildingWidth - 5, buildingHeight);

            fill(255, 255, 0, 255);
            int windowsPerRow = 4;
            float windowWidth = buildingWidth / (windowsPerRow + 2);
            float windowHeight = 20;
            for (int row = 0; row < (buildingHeight / (windowHeight * 1.5)); row++) {
                for (int col = 0; col < windowsPerRow; col++) {
                    float windowX = x + windowWidth * (col + 1);
                    float windowY = y + windowHeight * 1.5f * row;
                    staticElements.fill(255, 255, 0, 255);
                    staticElements.rect(windowX, windowY, windowWidth - 5, windowHeight - 5);
                }
            }

            staticElements.fill(120, 120, 120, 255);
            staticElements.rect(x, y - 20, buildingWidth - 5, 20);
        }

        staticElements.endDraw();
    }
}
