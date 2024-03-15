package ie.tudublin;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PGraphics;
import ddf.minim.*;

public class Main
{
	

	public void helloProcessing()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Buildings());
    }

	public static void main(String[] args)
	{
		System.out.println("This is a test");
		
		
		Main m = new Main();
		m.helloProcessing();
	}


}