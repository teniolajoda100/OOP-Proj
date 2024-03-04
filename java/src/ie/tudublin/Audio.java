package ie.tudublin;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class Audio extends PApplet{

    Minim m;
    AudioInput ai;
    AudioPlayer ap;
    
    AudioBuffer b;

    @Override
    public void settings() {
        // Set the size of the window
       // size(800, 600);
        fullScreen(P3D, SPAN);
    }

    @Override
    public void setup() {
        // Setup code goes here
        m = new Minim(this);
        //ai = m.getLineIn(Minim.MONO, width, 44100, 16);
        ap = m.loadFile("java/data/Jb.mp3",1024);
        ap.play();
        b = ap.mix;
    }


    float y = 400;

    float lerpedAvg = 0;

    @Override
    public void draw() {
        colorMode(HSB);
        background(0);
        stroke(255);


        //Code for the frequency line
        float h = height / 2;
        
        for(int i = 0 ; i < b.size() ; i ++)
        {
            background(0);
            stroke(255);
            colorMode(HSB);
            float lineWidth = width / (float) b.size();
            for(i = 0 ; i < b.size(); i++)
            {
                float hue = map(i,0,b.size(),0,256);
                float amplitude = b.get(i) * h; //the amplitude scaled to half the screen height
                strokeWeight(lineWidth); 
                stroke(hue,255,255);
                line(i * lineWidth, h, i * lineWidth, h + amplitude);  // Adjust the multiplier as needed to see 
        }
    
    }
}
}
