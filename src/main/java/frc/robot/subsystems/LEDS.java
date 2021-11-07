package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDS extends SubsystemBase{

    

    Servo leds = new Servo(2);

    public LEDS(){
        leds.set(-.83);
    }

    
}
