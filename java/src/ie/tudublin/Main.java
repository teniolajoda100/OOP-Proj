package ie.tudublin;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;

public class Main
{
	Minim minim;
	AudioPlayer song;
	FFT fft;
	public void settings() {
		minim = new Minim(this);
		song = minim.loadFile("java/data/Jb.mp3", 1024);
		fft = new FFT(song.bufferSize(), song.sampleRate());
		song.play();
	   
	  
	  }

	public void helloProcessing()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new person());
    }

	public static void main(String[] args)
	{
		System.out.println("This is a test");
		
		
		Main m = new Main();
		m.helloProcessing();
	}


}