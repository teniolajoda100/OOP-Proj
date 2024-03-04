package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class Audio1 extends PApplet
{
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    int mode = 0;

    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;
    

    public void keyPressed() {
		if (key >= '0' && key <= '9') {
			mode = key - '0';
		}
		if (keyCode == ' ') {
            if (ap.isPlaying()) {
                ap.pause();
            } else {
                ap.rewind();
                ap.play();
            }
        }
	}

    public void settings()
    {
        //size(1024, 1000, P3D);
        fullScreen(P3D, SPAN);
    }

    public void setup()
    {
        minim = new Minim(this);
        // Uncomment this to use the microphone
         ai = minim.getLineIn(Minim.MONO, width, 44100, 16);
         ab = ai.mix; 
        ap = minim.loadFile("tomp3.cc - 08 PsychNerD and Marco G  More Cowbell.mp3", 1024);
        ap.play();
        ab = ap.mix;
        colorMode(HSB);

        y = height / 2;
        smoothedY = y;

        lerpedBuffer = new float[width];
    }

    float off = 0;

    public void draw()
    {
        //background(0);
        float halfH = height / 2;
        float average = 0;
        float sum = 0;
        off += 1;
        // Calculate sum and average of the samples
        // Also lerp each element of buffer;
        for(int i = 0 ; i < ab.size() ; i ++)
        {
            sum += abs(ab.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.05f);
        }
        average= sum / (float) ab.size();

        smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);
        
        float cx = width / 2;
        float cy = height / 2;

        switch (mode) {
		case 0: // incomplete
        

    
                // Code goes here
                break;
        case 1:

            background(0);
            stroke(255);
            colorMode(HSB);
            float h = height /2;
            float lineWidth = width / (float) ab.size();
            for(int i = 0 ; i < ab.size(); i++)
            {
                float hue = map(i,0,ab.size(),0,256);
                float amplitude = ab.get(i) * h; //the amplitude scaled to half the screen height
                strokeWeight(lineWidth); 
                stroke(hue,255,255);
                line(i * lineWidth, h, i * lineWidth, h + amplitude);  // Adjust the multiplier as needed to see 
            }
            break;
        case 2:
        background(0);
        colorMode(HSB);
        float lineWidth2 = 2; //a fixed line width or base it on screen size
        strokeWeight(lineWidth2);
    
        // calculate the number of samples per screen edge
        int samplesPerEdge = ab.size() / 4;
    
        //the bottom edge
        for (int i = 0; i < samplesPerEdge; i++) {
            float x = map(i, 0, samplesPerEdge, 0, width);
            float amplitude = ab.get(i) * 0.25f * height; 
            float hue = map(i, 0, samplesPerEdge, 0, 255);
            stroke(hue, 255, 255);
            line(x, height, x, height - amplitude);
        }
    
        // draw the right edge
        for (int i = 0; i < samplesPerEdge; i++) {
            float y = map(i, 0, samplesPerEdge, height, 0);
            float amplitude = ab.get(i + samplesPerEdge) * 0.25f * width;
            float hue = map(i, 0, samplesPerEdge, 0, 255);
            stroke(hue, 255, 255);
            line(width, y, width - amplitude, y);
        }
    
        // draw the top edge
        for (int i = 0; i < samplesPerEdge; i++) {
            float x = map(i, 0, samplesPerEdge, width, 0);
            float amplitude = ab.get(i + 2 * samplesPerEdge) * 0.25f * height;
            float hue = map(i, 0, samplesPerEdge, 0, 255);
            stroke(hue, 255, 255);
            line(x, 0, x, amplitude);
        }
    
        // draw the left edge
        for (int i = 0; i < samplesPerEdge; i++) {
            float y = map(i, 0, samplesPerEdge, 0, height);
            float amplitude = ab.get(i + 3 * samplesPerEdge) * 0.25f * width;
            float hue = map(i, 0, samplesPerEdge, 0, 255);
            stroke(hue, 255, 255);
            line(0, y, amplitude, y);
        }
    
        break;
        case 3:
        background(0);
        colorMode(HSB);
        float sum1 = 0;
    
        // he sum of the absolute values of the samples in the audio buffer
        for (int i = 0; i < ab.size(); i++) {
            sum1 += abs(ab.get(i));
        }
    
        // calculating the average amplitude
        float averageAmplitude = sum / ab.size();
    
        // Use lerp to smoothly transition the smoothedAmplitude towards the averageAmplitude
        smoothedAmplitude = lerp(smoothedAmplitude, averageAmplitude, 0.1f);
    
        // map the smoothed amplitude to a circle diameter
        float diameter = map(smoothedAmplitude, 0, 1, 0, width);
    
        
        float hue1 = map(smoothedAmplitude, 0, 1, 0, 255);
        stroke(hue1, 255, 255);
        noFill(); // disabling filling for shapes
    
        // draw the circle at the center of the screen
        ellipse(width / 2, height / 2, diameter, diameter);
        break;

        //used similar method to what i used in case 3
        case 4:
        background(0);
        colorMode(HSB);
        float sum2 = 0;

        for (int i = 0; i < ab.size(); i++) {
        sum2 += abs(ab.get(i));
        }

    // calculate the average amplitude
        float averageAmplitude2 = sum / ab.size();

        // using lerp to smoothly transition the smoothedAmplitude towards the averageAmplitude
        smoothedAmplitude = lerp(smoothedAmplitude, averageAmplitude2, 0.1f);

    // mapping the smoothed amplitude to a square size
        float squareSize = map(smoothedAmplitude, 0, 1, 0, width * 0.5f); // Adjust the multiplier for size as needed

    // setting the colour based on the smoothed amplitude
    float hue2 = map(smoothedAmplitude, 0, 1, 0, 255);
    stroke(hue2, 255, 255);
    noFill(); 

    //centering the rectangle 
    rect((width - squareSize) / 2, (height - squareSize) / 2, squareSize, squareSize);
    break;

        case 5:
        background(0);
        colorMode(HSB);
        strokeWeight(2);

        // waveform for the bottom and top
        for (int i = 0; i < ab.size(); i++) {
                float x = map(i, 0, ab.size(), 0, width);
                float yBottom = map(ab.get(i), -1, 1, height, height * 0.75f); // Bottom waveform
                float yTop = map(ab.get(i), -1, 1, 0, height * 0.25f); // Top waveform
    
             // set color based on amplitude
                float hue = map(ab.get(i), -1, 1, 0, 255);
                 stroke(hue, 255, 255);
    
                // draw the bottom and top waveforms
                if (i > 0) { // ensure we have a previous point to draw from
                         float xPrev = map(i - 1, 0, ab.size(), 0, width);
                         float yBottomPrev = map(ab.get(i - 1), -1, 1, height, height * 0.75f);
                         float yTopPrev = map(ab.get(i - 1), -1, 1, 0, height * 0.25f);
        
                        line(xPrev, yBottomPrev, x, yBottom); // connect bottom waveform points
                        line(xPrev, yTopPrev, x, yTop); // connect top waveform points
    }

}         
       // the waveform for the left and right sides
       for (int i = 0; i < ab.size(); i++) {
                float y = map(i, 0, ab.size(), height, 0);
                float xLeft = map(ab.get(i), -1, 1, 0, width * 0.25f); // left waveform
                float xRight = map(ab.get(i), -1, 1, width, width * 0.75f); // right waveform
    
                // set color based on amplitude
                float hue = map(ab.get(i), -1, 1, 0, 255);
                stroke(hue, 255, 255);
    
                // draw the left and right waveforms
                if (i > 0) { // ensuring we have a previous point to draw from
                        float yPrev = map(i - 1, 0, ab.size(), height, 0);
                        float xLeftPrev = map(ab.get(i - 1), -1, 1, 0, width * 0.25f);
                        float xRightPrev = map(ab.get(i - 1), -1, 1, width, width * 0.75f);
        
                        line(xLeftPrev, yPrev, xLeft, y); // connecting left waveform points
                        line(xRightPrev, yPrev, xRight, y); // connecting right waveform points
    }
}
                break;
        }
        


        
        // Other examples we made in the class
        /*
        stroke(255);
        fill(100, 255, 255);        
        
        circle(width / 2, halfH, lerpedA * 100);

        circle(100, y, 50);
        y += random(-10, 10);
        smoothedY = lerp(smoothedY, y, 0.1f);        
        circle(200, smoothedY, 50);
        */

    }        
}
