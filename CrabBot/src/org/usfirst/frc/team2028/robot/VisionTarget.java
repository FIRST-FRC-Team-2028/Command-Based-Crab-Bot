package org.usfirst.frc.team2028.robot;

import java.awt.Dimension;

public class VisionTarget {
	
	public static double pixyResx= Parameters.pixyResx,pixyResy = Parameters.pixyResy;
	public static double xFOV = Parameters.xPixyFOV;
	public static double yFOV = Parameters.yPixyFOV;
	
    public double x;

    public double y;

    public double width;

    public double height;
    
    public double area;
    
    //Angle from center
    public double xAFC;
    public double yAFC;

    public VisionTarget(double x, double y, double width, double height)
    {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    	System.out.println("\nX: "+x);
    	System.out.println("Y: "+y);
    	System.out.println("W: "+width);
    	System.out.println("H: "+height);
    	area = width*height;
    	xAFC = (x+(width/2))-(pixyResx/2);
    	System.out.println(xAFC);
    	xAFC = ( xAFC / (pixyResx/2) );
    	yAFC = ( ( (y+(height/2)) - (pixyResy/2) ) / (pixyResy/2) ) * (yFOV/2);
    	
    }
    
    public double getSize() {
        // TODO Auto-generated method stub
    	return area;
    }

    public double getAngleToCenter() {
        // TODO Auto-generated method stub
    	return xAFC;
    }

}
