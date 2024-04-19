package ie.tudublin;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;



public class person extends PApplet {
    Minim minim;
    AudioPlayer player;
    FFT fft;

    //position for the cloud
    float cloudX;
    float cloudY;

    //position for the girl
    float girlX;
    float girlY;

    //base size of the hearts
    float heartBaseSize = 20;

    //array to store hearts' positions
    float[][] hearts = {
        {430, 200},  
        {575, 300},  
        {650, 450},
        {450, 350} 
    };

    //array to store question marks' positions
    float[][] questionMarks = {
        {200, 150},
        {250, 180},
        {235, 130}
    };

    public void settings() {
        fullScreen(P3D, SPAN); 
    }

    public void setup() {
        minim = new Minim(this);
        player = minim.loadFile("java/data/myuniverse.mp3");
        fft = new FFT(player.bufferSize(), player.sampleRate());
        player.play();

        //set positions for the cloud and the girl
        cloudX = width / 4;
        cloudY = height / 2;
        girlX = cloudX + 130; // adjusted to be at the edge of the cloud
        girlY = cloudY - 20; //adjusted to make her appear seated on the cloud
    }

    public void draw() {
        background(135, 206, 235); //set background to sky blue

        float sunX = width - 150; // Adjust as necessary for the position
        float sunY = 150; // Adjust as necessary for the position

    // draw the reactive sun
        drawSun(sunX, sunY); // Modify this method if you need a different position for the sun

        // draw the cloud
        drawCloud(cloudX, cloudY);

        // draw the girl
        drawGirl(girlX, girlY);

        // draw hearts
        drawHearts();

        // draw question marks
        drawQuestionMarks();
        
    }




    void drawCloud(float x, float y) {
        fill(255); // White color
        noStroke();

        // draw clouds
        ellipse(x, y, 300, 200);
        ellipse(x - 80, y - 50, 150, 100);
        ellipse(x + 80, y - 50, 150, 100);
        ellipse(x - 150, y + 20, 200, 100);
        ellipse(x + 150, y + 20, 200, 100);
    }
    
    void drawGirl(float x, float y) {
        // draw girl's legs 
        float legLength = 30; // Length
        fill(255, 218, 185); // Peach color for legs
        stroke(0);

        // adjust the angles of the legs to point towards the right
        float legAngle = PI/12; 

        // calculate the positions of the feet3
        float leftFootX = x - 10 + cos(legAngle) * legLength;
        float leftFootY = y + 50 + sin(legAngle) * legLength;
        float rightFootX = x + 10 + cos(legAngle) * legLength;
        float rightFootY = y + 50 + sin(legAngle) * legLength;

        // draw left leg
        line(x - 10, y + 50, leftFootX, leftFootY);

        // draw right leg
        line(x + 10, y + 50, rightFootX, rightFootY);

        // draw girl's dress (pink triangle)
        fill(255, 192, 203); 
        noStroke(); 
        triangle(x, y - 30, x - 20, y + 50, x + 20, y + 50);

        // draw girl's head (right side profile)
        fill(255, 218, 185); 
        ellipse(x + 10, y - 45, 30, 30); 

        // Draw girl's head (right side profile)
    fill(255, 218, 185); 
    ellipse(x + 10, y - 45, 30, 30); 


    


    }

    void drawHearts() {
        fill(255, 0, 0); // Red color
        for (int i = 0; i < hearts.length; i++) {
            float level = player.mix.level(); 
            float heartSize = heartBaseSize + level * 200; 
            pushMatrix();
            translate(hearts[i][0], hearts[i][1]);
            beginShape();
            float curveAmt = 10; // Adjust the curve for the heart shape
            float x = 0;
            float y = (float) (-heartSize * 0.5); // adjust the y-position of the top of the heart
            vertex(x, y);
            bezierVertex(x - curveAmt, y - curveAmt, x - curveAmt * 2, y + curveAmt, x, y + heartSize);
            bezierVertex(x + curveAmt * 2, y + curveAmt, x + curveAmt, y - curveAmt, x, y);
            endShape(CLOSE);
            popMatrix();
        }
    }

    void drawQuestionMarks() {
        fill(0); // Black color 
        for (int i = 0; i < questionMarks.length; i++) {
            float x = questionMarks[i][0];
            float y = questionMarks[i][1];
            float pulse = sin((float) (frameCount * 0.05)) * 3; // adjust the pulse magnitude
            
            pushMatrix();
            translate(x, y + pulse); // adjust the vertical position based on pulse
            textSize(32);
            text("?", x, y);
            popMatrix();
        }
    }
    void drawSun(float x, float y) {
 float sunBaseSize = 50;
    float level = player.mix.level(); // Audio reaction
    float songProgress = map(player.position(), 0, player.length(), 0, 1); // Song progress mapped to a range of 0 to 1
    float sunGrowthFactor = 200; // How much the sun will grow over the course of the song
    float sunSize = sunBaseSize + songProgress * sunGrowthFactor + level * 200; // Combine growth over time with audio reaction
    
        // Draw the main sun with jagged edges
        noStroke();
        fill(255, 255, 0);
        beginShape();
        int points = 16;
        float angle = TWO_PI / points;
        for (int i = 0; i < points; i++) {
            float outerX = cos(angle * i) * sunSize * 0.5f + x;
            float outerY = sin(angle * i) * sunSize * 0.5f + y;
            vertex(outerX, outerY);
    
            float innerX = cos(angle * i + angle / 2) * sunSize * 0.4f + x;
            float innerY = sin(angle * i + angle / 2) * sunSize * 0.4f + y;
            vertex(innerX, innerY);
        }
        endShape(CLOSE);
    
        // Draw the glow aura
        int auraLayers = 5;
        for (int i = 0; i < auraLayers; i++) {
            float auraSize = sunSize * (1 + (i + 1) * 0.1f);
            float alphaValue = 50 - i * 10;
            fill(255, 255, 0, alphaValue);
            beginShape();
            for (int j = 0; j < points; j++) {
                float outerX = cos(angle * j) * auraSize * 0.5f+ x;
                float outerY = sin(angle * j) * auraSize * 0.5f + y;
                vertex(outerX, outerY);
    
                float innerX = cos(angle * j + angle / 2) * auraSize * 0.4f+ x;
                float innerY = sin(angle * j + angle / 2) * auraSize * 0.4f + y;
                vertex(innerX, innerY);
            }
            endShape(CLOSE);
        }
    }
    
    public static void main(String[] args) {
        PApplet.main("person");
    }
}
