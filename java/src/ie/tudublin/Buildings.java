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
    PGraphics staticElements;
    Main controller;
    
    float[] buildingHeights;
    int numberOfBuildings = 8;
    float buildingWidth;

    float moonDiameter = 250;
    float moonPulseTime = 0;

    float carX;
    float carY;
    float carSpeed = 2;
    
    float redCarX;
    float redCarY;
    float redCarSpeed = 2;
    
    float pinkCarX;
    float pinkCarY;
    float pinkCarSpeed = 2;


    public Buildings(Main controller, AudioPlayer player) {
        this.player = player;
        this.controller = controller;
    }
    public void settings() {
        fullScreen(P3D, SPAN);
    }

    public void setup() {
        minim = new Minim(this);
       /*  player = minim.loadFile("java/data/myuniverse.mp3");
        player.play(); */

        staticElements = createGraphics(width, height, P3D);

        staticElements.beginDraw();
        staticElements.background(48, 0, 48);
        staticElements.endDraw();

        buildingHeights = new float[numberOfBuildings];
        buildingWidth = width / (float) numberOfBuildings;

        carX = 0;
        carY = height - 20;
        
        redCarX = -110;
        redCarY = height - 20;
        
        pinkCarX = redCarX - 110;
        pinkCarY = height - 20;
    }

    public void draw() {
        background(8, 32, 48);
        image(staticElements, 0, 0);
        updateBuildingHeights();
        drawBuildings();
        drawCityElements();
        drawMoon();
        
        carX += carSpeed;
        if (carX > width + 110) {
            carX = -110;
        }
        
        drawCar(carX, carY, 0, 128, 255);
        
        if (frameCount > 150) {
            redCarX += redCarSpeed;
            if (redCarX > width + 110) {
                redCarX = -110;
            }
        }
        
        drawCar(redCarX, redCarY, 255, 0, 0);
        
        if (frameCount > 310) {
            pinkCarX += pinkCarSpeed;
            if (pinkCarX > width + 110) {
                pinkCarX = -110;
            }
        }
        
        drawCar(pinkCarX, pinkCarY, 255, 105, 180);
        //Switching to the other 
        if (millis() > 17500) {
            controller.switchToPerson();
            noLoop(); 
            surface.setVisible(false);
        }
    }

    private void drawCar(float x, float y, int r, int g, int b) {
        fill(r, g, b);
        rect(x, y - 20, 110, 40, 20);

        fill(200);
        rect(x + 15, y - 10, 30, 20);
        rect(x + 60, y - 10, 30, 20);

        fill(0);
        ellipse(x + 25, y + 10, 30, 30);
        ellipse(x + 85, y + 10, 30, 30);

        fill(r, g, b);
        arc(x + 55, y - 20, 80, 60, PI, TWO_PI);

        fill(220);
        arc(x + 55, y - 20, 60, 40, PI, TWO_PI);
    }

    private void drawCityElements() {
        float streetLampX = buildingWidth * 2 + buildingWidth / 2;
        drawStreetlamp(streetLampX, height);
        
        streetLampX = buildingWidth * 3 + buildingWidth / 2;
        drawStreetlamp(streetLampX, height);
    
        streetLampX = buildingWidth * 4 + buildingWidth / 2;
        drawStreetlamp(streetLampX, height);
    
        streetLampX = buildingWidth * 5 + buildingWidth / 2;
        drawStreetlamp(streetLampX, height);
    }
    
    private void drawStreetlamp(float x, float y) {
        float amplitude = player.mix.level() * 1000;
        
        float lampPostHeight = 150;
        fill(100);
        rect(x - 5, y - lampPostHeight, 10, lampPostHeight);
    
        fill(200);
        ellipse(x, y - lampPostHeight, 50, 50);
    
        float brightness = map(amplitude, 0, 1000, 100, 255);
    
        fill(255, 255, 0, brightness);
        float lightSize = 80 + sin((float) (frameCount * 0.05)) * 10;
        ellipse(x, y - lampPostHeight, lightSize, lightSize);
    }
    
    public void updateBuildingHeights() {
        for (int i = 0; i < numberOfBuildings; i++) {
            buildingHeights[i] = lerp(buildingHeights[i], height * 0.9f, 0.0005f);
        }
    }

    public void drawBuildings() {
        staticElements.beginDraw();
        staticElements.clear();

        int[][] colors = new int[][]{
            {50, 50, 50},
            {80, 80, 80},
            {80, 80, 80},
            {50, 50, 50}
        };

        for (int i = 0; i < numberOfBuildings; i++) {
            if (i == 2 || i == 3 || i == 4 || i == 5) continue;

            float x = i * buildingWidth;
            float y = height - buildingHeights[i];

            staticElements.fill(colors[i % colors.length][0], colors[i % colors.length][1], colors[i % colors.length][2], 255);
            staticElements.noStroke();
            staticElements.rect(x, y, buildingWidth - 5, buildingHeights[i]);
            drawWindows(staticElements, x, y, buildingWidth, buildingHeights[i]);

            staticElements.fill(120, 120, 120, 255);
            staticElements.triangle(
                    x, y,
                    x + buildingWidth / 2, y - 30,
                    x + buildingWidth, y
            );
        }
        staticElements.endDraw();
    }

    private void drawWindows(PGraphics canvas, float x, float y, float buildingWidth, float buildingHeight) {
        canvas.fill(255, 255, 0, 255);
        int windowsPerRow = 3;
        float windowHeight = 20;
        int maxRows = (int)((buildingHeight - 30) / (windowHeight * 1.5));

        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < windowsPerRow; col++) {
                float windowX = x + buildingWidth / 6 + col * buildingWidth / 4;
                float windowY = y + 30 + row * windowHeight * 1.5f;
                if (frameCount % 70 < 30) {
                    canvas.fill(255, 165, 0, 255);
                } else {
                    canvas.fill(255, 255, 0, 255);
                }
                canvas.rect(windowX, windowY, buildingWidth / 4 - 5, windowHeight - 5);
            }
        }
    }


    public void drawMoon() {
        float moonAngle = frameCount * 0.01f;

        pushMatrix();
        translate(width / 2, height / 4);
        rotate(moonAngle);

        float pulseAmplitude = 20;
        moonPulseTime += 0.05;
        float currentPulse = pulseAmplitude * sin(moonPulseTime);
        float pulsingMoonDiameter = moonDiameter + currentPulse;

        int glowRadius = 30;
        noStroke();
        for (int i = glowRadius; i > 0; i--) {
            float alpha = 30 * exp((float) (-i / 15.0));
            fill(255, 255, 204, alpha);
            ellipse(0, 0, pulsingMoonDiameter + i * 2, pulsingMoonDiameter + i * 2);
        }

        fill(230, 230, 180);
        ellipse(0, 0, pulsingMoonDiameter, pulsingMoonDiameter);

        fill(150, 150, 110);
        ellipse(-30, -50, 40, 40);
        ellipse(50, 20, 30, 30);
        ellipse(0, 50, 20, 20);
        ellipse(-60, 30, 25, 25);
        ellipse(40, -40, 15, 15);
        ellipse(-20, 70, 10, 10);

        popMatrix();
    }

}