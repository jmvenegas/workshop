
public class Calc 
{
	public static double distance(double x0, double y0, double x1, double y1)
	{
		double xDistance = Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
		return xDistance;
	}
}
