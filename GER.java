package GER;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * GER_GER - a robot by The NUI Maynooth Robocode Team 2015
 * @author Godstime Osarobo, Eric Cassells, Ross Cawley
 */
public class GER extends Robot
{
	
	 
	 // global variables
	boolean swap = false;//dodge/kiting technique on(false) or off(true)
	boolean check;//is the robot moving forwards(true) or backwards(false)
	boolean alternate = false;//dodge/kiting technique using back(false) or using ahead(true)
	boolean center = false;//checks if the robot has made its way to the center of the battlefield
	double ang = 360;//creates the ang for revAngle
	int counter = 0;//checks how many times robot has hit the wall
	int counter1 = 0;// checks if the robot is being hit by more than one robot at the same time
	int counter2 = 0;
	/**
	 * run: PhoenixUltimate's default behavior
	 */
	
	public void run() {		
		
		// Initialization of the robot should be put here
		Color lightBlue = new Color(0,67,201,255);
		Color black = new Color(0,0,0,255);
		Color white = new Color(255,255,255,255);
		setColors(lightBlue,black,white,Color.black,Color.red); // body color,gun color,radar color,bullet color,scanArc color
		// Robot main loop
		while(true) {
			if(((300 < (int)Math.round(getX())) && ((int)Math.round(getX()) < 500)) && ((300 < (int)Math.round(getY())) && ((int)Math.round(getY()) < 500)) && (center == false)) {
			center = true;
			setColors(lightBlue,black,white,Color.black,Color.red);
			}
			else if(center == false) {
					goTo(400,400);
			}
			else {
				
				if(alternate == true){ //reverse kiting start
					if(swap == true){ //kiting technique off using ahead
						scan();
						back(50);
						check = false;
						//back(50);
						turnGunRight(ang);
					}
					
						swap = true; //reset the kiting technique to off
						ahead(50);  //kiting technique on using ahead
						check = true;
						//ahead(50);
						turnGunRight(ang);
				} //reverse kiting end
			
				else{ //normal kiting start
					if(swap == false){ //kiting technique off using back
						scan();
						ahead(50);
						check = true;
						//ahead(50);
						turnGunRight(ang);
					}
					swap = false; //reset the kiting technique to off
					back(50); //kiting technique on using back
					check = false;
					//back(50);
					turnGunRight(ang);
					
				} //normal kiting end
			
			}
		}
		
	}
	/**
	 * goTo: Goes to a certain location
	 */
		public void goTo(double xLoc,double yLoc) {
		setAllColors(Color.BLACK);
		double facing = getHeading();
		System.out.println("Facing: " + facing);
		if(facing<180) {
			turnLeft(360 + facing);
		}
		else if (facing>180){
			turnRight(360 - facing);
		}
		else {
			turnRight(180);
		}
		double xDist = Math.abs((getX() - xLoc));
		System.out.println("xDist: " + xDist);
		double yDist = Math.abs((getY() - yLoc));
		System.out.println("yDist: " + yDist);
		if(getX()<400) { 
			turnRight(90);
			ahead(xDist);
		}
		else if(getX()>400){
			turnLeft(90);
			ahead(xDist);
			}
		else {
			
		}
		if(getY()<400) { 
			turnRight(90);
			ahead(yDist);
		}
		else if(getY()>400){
			turnLeft(90);
			ahead(yDist);
			}
		else {
			
		}	
	}
	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if(center == false) {
			setAllColors(Color.BLACK);
		}
		else if (e.isSentryRobot()) {
			if(alternate == true) {
				/*ahead(50);
				check = true;
				turnLeft(90 - e.getBearing());//turn perpendicular to the targeted robot
				scan();*/
				if(ang == 360) {
					ang = -360;
				}
				else { 
					ang = 360;
				}
			}
			else {
				/*back(50);
				check = false;
				turnLeft(90 - e.getBearing());//turn perpendicular to the targeted robot
				scan();*/
				if(ang == 360) {
					ang = -360;
				}
				else { 
					ang = 360;
				}
			}
		}
		else {
			if(counter2 > 1){
				fire(2);
				doNothing();
				scan();
				fire(3);//fire hard!
		turnLeft(90 - e.getBearing());//turn perpendicular to the targeted robot
		if(alternate == false){
			swap = true; //use kiting technique
		}
		else{
			swap = false; //use kiting technique
		}
		System.out.println(e.getName());
			}
			else {
				fire(3);//fire hard!
		turnLeft(90 - e.getBearing());//turn perpendicular to the targeted robot
		if(alternate == false){
			swap = true; //use kiting technique
		}
		else{
			swap = false; //use kiting technique
		}
		counter2++;
		System.out.println(counter2);
		System.out.println(e.getName());
			}
		}
	}
	/**
	 * onBulletMissed: What to do when one of your bullets hits another robot.
	 */
		public void onBulletMissed(BulletMissedEvent e) {
			if(center == false){ 
			setAllColors(Color.BLACK);
			
			}
			else{ 
			scan();
			}
		}
		

	/**
	 * onBulletHit:What to do when you one of your bullets hits another robot
	*/
	public void onBulletHit(BulletHitEvent e) {
			if(center == false){ 
			setAllColors(Color.BLACK);
			
			}
			else{ 
			scan();
			}
	}
	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		if(center == false) {
			setAllColors(Color.BLACK);
			
		}
		else{ 
			counter2 = 0;
			if(swap == true) {
				scan();
				counter1++;
			}
		else if(counter1 == 2){
			turnLeft(90 - e.getBearing());
				if(alternate == true) {
					ahead(50);
					check = true;
					back(100);
				}
				
				else {
					back(50);
					check = false;
					ahead(100);
				}
			}
		else {
			turnLeft(90 - e.getBearing());
			if(alternate == true) {
			ahead(50);
			check = true;
			ahead(50);
			ahead(50);
			}
			
			else {
			back(50);
			check = false;
			back(50);
			back(50);
			}
			counter1++;
		}
		}
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		if(center == false) { 
			setAllColors(Color.BLACK);
			
		}
		else {
			double loc;
		if(check == false) {
			if(counter == 2){
			loc=e.getBearing();
				if(loc>0){
					turnLeft(90);
					ahead(50);
					check = true;
					ahead(50);
					ahead(50);
					turnLeft(0);
					swap = true;
				}
				else{
					turnRight(90);
					ahead(50);
					check = true;
					ahead(50);
					ahead(50);
					turnRight(0);
					swap = true;
				}
				counter = 0;
			}
			else{
				ahead(200);
				check = true;
				swap = true;
				counter++;
			}
			alternate = true;
		}
		else if(check == true) { 
			if(counter == 2){
				loc=e.getBearing();
				if(loc>0){
					turnLeft(90);
					ahead(50);
					check = true;
					ahead(50);
					ahead(50);
					turnLeft(0);
					swap = true;
				}
				else{
					turnRight(90);
					ahead(50);
					check = true;
					ahead(50);
					ahead(50);
					turnRight(0);
					swap = true;
				}
				counter = 0;
			}
					
			else{ //chances are nearly impossible but if the robot somehow hits the wall with its side
			back(200);
			check = false;
			swap = false;
			counter++;
			}
			alternate = false;
		}
		else {
			turnRight(135 - e.getBearing());
			ahead(200);
			check = true;
		}
		}
	}	
	
	/**
	 * onHitRobot: What to do when you hit a robot
	 */
	public void onHitRobot(HitRobotEvent e) {
		if(center == false) {
			setAllColors(Color.BLACK);
			
		}
		else {
			if(check == false){
			turnLeft(90 - e.getBearing());
			ahead(50);
			check = true;
			ahead(50);
			
		}
		else if(check == true){
			turnLeft(90 - e.getBearing());
			back(50);
			check = false;
			back(50);			
		}
		}
	}
}
