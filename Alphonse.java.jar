package KKJ;
import robocode.*;
import java.awt.Color;

/**
 * Alphonse - a robot by Kevin, Kieran, & Joe
 */
public class Alphonse extends Robot
{
	public void run() {
		
		setBodyColor(new Color(140, 172, 183));
		setGunColor(new Color(94, 125, 127));
		setRadarColor(new Color(139, 172, 181));
		setScanColor(new Color(153, 217, 234));
		setBulletColor(new Color(227, 66, 52));
		
		int count = 0;
		turnRight(360-getHeading());
		ahead((getBattleFieldHeight()-getY())-100);
		
		turnRight(90);
		ahead((getBattleFieldWidth()-getX())-100);
		turnGunRight(90);
		
		while(true) {	
			if (count == 0)
			{
				turnRight(90);
				ahead(getY()-100);
				count++;
			}
			if (count == 1)
			{
				turnRight(90);
				ahead(getX()-100);
				count++;
			}	
			if (count == 2)
			{
				turnRight(90);
				ahead((getBattleFieldHeight()-getY())-100);
				count++;
			}
			if (count == 3)
			{
				turnRight(90);
				ahead((getBattleFieldWidth()-getX())-100);
				count = 0;
			}
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		fire(2);
	}

	/*public void onHitRobot(HitRobotEvent e){
		
	}*/
}
