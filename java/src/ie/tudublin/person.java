package ie.tudublin;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PGraphics;
import ddf.minim.*;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;

public class person extends PApplet {
    Minim minim;
	AudioPlayer player;
	FFT fft;
    
    // angle for arm and leg movement
    float angle = 0;
    // position for moving the person to the right
    float X1 = 0;
    
    public void settings() {
        fullScreen(P3D, SPAN); //set the size of the window
        
    }

    public void setup() {
        minim = new Minim(this);
        player = minim.loadFile("java/data/Jb.mp3");
        fft = new FFT(player.bufferSize(), player.sampleRate());
        player.play();
        X1 = -100; // starting off-screen for a walking in effect
    }
    public void draw() {
        background(135, 206, 235);// Set background to white
        //incrementing X1 to increase the speed
        X1 += 0.8f;

        // wrap around, incase the character walks off screen
        if (X1 > width) {
            X1 = -100; // Start again from the left when reaching the end of the screen
        }
    
        // get the current amplitude
        float amplitude = player.mix.level() * 100; // scale the amplitude
    
        // adjust the size of the person based on the amplitude
        float headSize = 30 + amplitude;
        float bodyLength = 45 + amplitude;
        float armLegSwingAmplitude = 10 + amplitude / 10;
    
        // calculate arm and leg swing based on angle
        // we used sin and cos to generate values that oscillate between -1 and 1 in a smooth wave-like pattern when given an angle that grows over time. 
        // when you map these values to the swing of the arms and legs, they create a realistic movement
        float armSwing = sin(angle) * armLegSwingAmplitude;
        float legSwing = cos(angle) * armLegSwingAmplitude * 2;
    
        // increment the angle for the next frame
        angle += 0.1;

       
    
    
        // head adjusting the head size based on the beat by just adding the amp
        //0.8f changes the walking speed
        ellipse(X1 + 115, 90, headSize, headSize);
    
        // body
        line(X1 + 115, 105, X1 + 115, 105 + bodyLength);
    
        // arms swinging
        line(X1 + 115, 110, X1 + 105 - armSwing, 130);
        line(X1 + 115, 110, X1 + 125 + armSwing, 130);
    
        // legs moving to simulate walking
        line(X1 + 115, 105 + bodyLength, X1 + 105 - legSwing, 180 + amplitude / 5);
        line(X1 + 115, 105 + bodyLength, X1 + 125 + legSwing, 180 + amplitude / 5);
    }
    

    public static void main(String[] args) {
        PApplet.main("ie.tudublin.person");
    }
}
