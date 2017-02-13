package org.usfirst.frc.team2028.robot;

import java.awt.Dimension;

public class VisionTarget {
	
	public static int pixyResx= Parameters.pixyResx,pixyResy = Parameters.pixyResy;
	public static int xFOV = Parameters.xPixyFOV;
	public static int yFOV = Parameters.yPixyFOV;
	
    public int x;

    public int y;

    public int width;

    public int height;
    
    public int area;
    
    //Angle from center
    public double xAFC;
    public double yAFC;

    public VisionTarget(int x, int y, int width, int height)
    {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    	area = width*height;
    	xAFC = ( ( (x+(width/2)) - (pixyResx/2) ) / (pixyResx/2) ) * (xFOV/2);
    	yAFC = ( ( (y+(height/2)) - (pixyResy/2) ) / (pixyResy/2) ) * (yFOV/2);
    	
    }
    
    public int getSize() {
        // TODO Auto-generated method stub
    	return area;
    }

    public double getAngleToCenter() {
        // TODO Auto-generated method stub
    	return xAFC;
    }

}
