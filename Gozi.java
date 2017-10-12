package GO;
import robocode.*;
import java.awt.Color;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Gozi - a robot by (your name here)
 */
public class Gozi extends Robot
{
	/**
	 * run: Gozi's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar
		setColors(Color.blue, Color.red, Color.gray, Color.black, Color.blue);
		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			ahead(100);
			scan();
			turnGunRight(360);
			back(100);
			scan();
			turnGunRight(360);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		fire(1);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		turnLeft(90);
		ahead(100);
		turnGunRight(360);
		scan();
		turnRight(90);
		ahead(100);
		turnGunLeft(360);
		scan();
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(100);
	}
	public void onBulletMissed(BulletMissedEvent e) {
		turnLeft(90);
		ahead(100);
		turnGunRight(360);
		scan();
		turnRight(90);
		ahead(100);
		turnGunLeft(360);
		scan();
		}

}
																																																