import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class EV3LF {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
		SensorMode mode = colorSensor.getRedMode();

		Sound.twoBeeps();
		LCD.drawString("white", 0, 0);
		Button.ESCAPE.waitForPressAndRelease();
		float[] sampleWhite = new float[1];
		mode.fetchSample(sampleWhite, 0);
		LCD.drawInt(new Float(sampleWhite[0] * 100).intValue(), 2, 2);

		Delay.msDelay(1000);
		Sound.twoBeeps();
		LCD.drawString("Black", 4, 4);
		Button.ESCAPE.waitForPressAndRelease();
		float[] sampleBlack = new float[1];
		mode.fetchSample(sampleBlack, 0);
		LCD.drawInt(new Float(sampleBlack[0] * 100).intValue(), 6, 6);
		Delay.msDelay(3000);

		int defaultPower = 80;
		int multiplyingFactor = 50;

		RegulatedMotor largeMotorB = new EV3LargeRegulatedMotor(MotorPort.B);
		RegulatedMotor largeMotorC = new EV3LargeRegulatedMotor(MotorPort.C);

		float[] color = new float[1];

		while (!Button.ESCAPE.isDown()) {

			float avgLight = (sampleBlack[0] + sampleWhite[0]) / 2;
			float avgColor =(sampleBlack[0] + sampleWhite[0]) / 2;
			mode.fetchSample(color, 0);
			float cSpeed = defaultPower + multiplyingFactor * (avgLight - color[0])
					/ (sampleWhite[0] - sampleBlack[0]);	
			
			float bSpeed = defaultPower - multiplyingFactor * (avgLight - color[0])
					/ (sampleWhite[0] - sampleBlack[0]);
			
			
			if(avgColor <= sampleWhite[0]){
				largeMotorC.setSpeed(20);
				largeMotorC.forward();
				largeMotorB.setSpeed(20);
	 			largeMotorB.forward();
			}
			
			else
			{
				largeMotorC.setSpeed(20);
				largeMotorB.setSpeed(20);
				largeMotorB.backward();
				largeMotorC.backward();
			}
		}

	}
}
