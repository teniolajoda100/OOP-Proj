package ie.tudublin;
// Packages must match the folder structure


public class Main
{
	

	public void helloProcessing()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Audio());
    }

	public static void main(String[] args)
	{
		System.out.println("This is a test");
		
		
		Main m = new Main();
		m.helloProcessing();
	}



}