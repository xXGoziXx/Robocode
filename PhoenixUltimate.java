package GO;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * PhoenixUltimate_GO - a robot by Godstime Osarobo
 * @author Godstime Osarobo
 */
public class PhoenixUltimate extends Robot
{
	
	 
	 // global variables
	boolean dodgeOff = false;//dodge/kiting technique on(false) or off(true)
	boolean forwardMove;//is the robot moving forwards(true) or backwards(false)
	boolean dodgeAhead = false;//dodge/kiting technique using back(false) or using ahead(true)
	boolean center = false;//checks if the robot has made its way to the center of the battlefield
	double gunRotateAngle = 360;//creates the angle for the gunHead to rotate at
	int wallHitCounter = 0;//checks how many times robot has hit the wall
	int hitCounter = 0;// checks if the robot is being hit by more than one robot at the same time
	int fireCounter = 0;
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
			if(((300 < (int)Math.round(getX())) && ((int)Math.round(getX()) < 500)) && ((300 < (int)Math.round(getY())) && ((int)Math.round(getY()) < 500)) && (center == false)){
				center = true;
				setColors(lightBlue,black,white,Color.black,Color.red);
			}
			else if(center == false) {
				goTo(400,400);
			}
			else{
				if(dodgeAhead == true){ //reverse kiting start
					if(dodgeOff == true){ //kiting technique off using ahead
						scan();
						back(50);
						forwardMove = false;
						//back(50);
						turnGunRight(gunRotateAngle);
					}
					dodgeOff = true; //reset the kiting technique to off
					ahead(50);  //kiting technique on using ahead
					forwardMove = true;
					turnGunRight(gunRotateAngle);
				}//reverse kiting end
				else{ //normal kiting start
					if(dodgeOff == false){ //kiting technique off using back
						scan();
						ahead(50);
						forwardMove = true;
						turnGunRight(gunRotateAngle);
					}
					dodgeOff = false; //reset the kiting technique to off
					back(50); //kiting technique on using back
					forwardMove = false;
					turnGunRight(gunRotateAngle);
				} //normal kiting end
			}
		}
	}
	
	/**
	 * goTo: Goes to a certain location
	 */
	public void goTo(double xLoc,double yLoc) {
		setAllColors(Color.BLACK);
		//gets the direction the tank is facing in
		double facing = getHeading();
		//prints the distances the robot is away from the point, on the x and y axis
		double xDist = Math.abs((getX() - xLoc));
		System.out.println("xDist: " + xDist);
		double yDist = Math.abs((getY() - yLoc));
		System.out.println("yDist: " + yDist);
		
		//work out the hypotenuse of the triangle between the x and y axis
		double hyp = Math.sqrt(Math.pow(xLoc-getX(),2)+Math.pow(yLoc-getY(),2));
		System.out.println("Hypotenuse: " + hyp);
		//work out the angle between the tank and the point
		double relAngle = Math.toDegrees(Math.atan2(yLoc - yDist,xLoc - xDist));
		System.out.println("relAngle: " + relAngle);
		boolean firstQ = false, secondQ = false, thirdQ = false, fourthQ = false;
		System.out.println("Facing: " + facing);
		//turn left to the point, if you're on the right-hand side of the map
		if((getX()<=400&&getX()>=0)&&(getY()>=400&&getY()<=800)) {
			firstQ = true;
			//turns at the angle relative to the location (xLoc,yLoc)
			turnRight(relAngle);
			ahead(hyp);
		}
		//turn right to the point, if you're on the left-hand side of the map
		else if ((getX()<=800&getX()>=400)&&(getY()>=400&&getY()<=800)){
			secondQ = true;
		}
		//turn in the opposite direction, if you're facing the opposite direction of the point
		else if ((getX()<=400&&getX()>=0)&&(getY()>=0&&getY()<=400)){
			thirdQ = true;
		}
		else if((getX()<=800&&getX()>=400)&&(getY()>=0&&getY()<=400)){
			fourthQ = true;
		}
		
	}
	
	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		//make sure you're in the center before you do stuff
		if(center == false) {
			setAllColors(Color.BLACK);
		}
		//forwardMove if it's a sentry bot
		else if (e.isSentryRobot()) {
			if(gunRotateAngle == 360) {
				gunRotateAngle = -360;
			}
			else { 
				gunRotateAngle = 360;
			}
		}
		else {
			//probably isn't even moving just destroy it
			if(fireCounter > 1){
				fire(2);//fire normal...
				scan();//scan again
				fire(3);//fire hard!
				//turn perpendicular to the targeted robot
				turnLeft(90 - e.getBearing());
				if(dodgeAhead == false){
					dodgeOff = true; //use kiting technique
				}
				else{
					dodgeOff = false; //use kiting technique
				}
				System.out.println(e.getName());
			}
			else {
				fire(3);//fire hard!
				turnLeft(90 - e.getBearing());//turn perpendicular to the targeted robot
				if(dodgeAhead == false){
					dodgeOff = true; //use kiting technique
				}
				else{
					dodgeOff = false; //use kiting technique
				}
				fireCounter++;
				System.out.println(fireCounter);
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
		System.out.println(e.getName());
		if(center == false) {
			setAllColors(Color.BLACK);
		}
		else if(e.getName().equals("sentry.SentryBot*")){
			
		}
		else{ 
			fireCounter = 0;
			if(hitCounter == 2) {
				if(dodgeAhead == true) {
					forwardMove = true;
					back(100);
				}
				else {
					forwardMove = false;
					ahead(100);
				}
				hitCounter = 0;
			}
			else if(dodgeOff == true){
				hitCounter++;
				scan();
			}
			else {
				hitCounter++;
				turnLeft(90 - e.getBearing());
				if(dodgeAhead == true) {
					forwardMove = true;
					back(50);
				}
				else {
					forwardMove = false;
					ahead(50);
				}
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
			if(forwardMove == false) {
				if(wallHitCounter == 2){
					loc=e.getBearing();
					wallHitCounter = 0;
					if(loc>0){
						turnLeft(90);
						ahead(50);
						forwardMove = true;
						ahead(50);
						ahead(50);
						turnLeft(0);
						dodgeOff = true;
					}
					else{
						turnRight(90);
						ahead(50);
						forwardMove = true;
						ahead(50);
						ahead(50);
						turnRight(0);
						dodgeOff = true;
					}
					wallHitCounter = 0;
				}
				else{
					ahead(200);
					forwardMove = true;
					dodgeOff = true;
					wallHitCounter++;
				}
				dodgeAhead = true;
			}
			else if(forwardMove == true) { 
				if(wallHitCounter == 2){
					loc=e.getBearing();
					if(loc>0){
						turnLeft(90);
						ahead(50);
						forwardMove = true;
						ahead(50);
						ahead(50);
						turnLeft(0);
						dodgeOff = true;
					}
					else{
						turnRight(90);
						ahead(50);
						forwardMove = true;
						ahead(50);
						ahead(50);
						turnRight(0);
						dodgeOff = true;
					}
					wallHitCounter = 0;
				}
				else{ //chances are nearly impossible but if the robot somehow hits the wall with its side
					back(200);
					forwardMove = false;
					dodgeOff = false;
					wallHitCounter++;
				}
				dodgeAhead = false;
			}
			else {
				turnRight(135 - e.getBearing());
				ahead(200);
				forwardMove = true;
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
			if(forwardMove == false){
				turnLeft(90 - e.getBearing());
				ahead(50);
				forwardMove = true;
				ahead(50);
			}
			else if(forwardMove == true){
				turnLeft(90 - e.getBearing());
				back(50);
				forwardMove = false;
				back(50);			
			}
		}
	}
}
