// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LEDS;
import frc.robot.subsystems.Loader;
import frc.robot.commands.LoadBall;
import frc.robot.commands.ShootBall;
import frc.robot.commands.ShooterOff;
import frc.robot.commands.ShooterOn;
import frc.robot.commands.TankDrive;
import frc.robot.commands.XboxControl;
import frc.robot.commands.FireBall;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {


  private final Joystick m_leftJoystick = new Joystick(0);
  private final Joystick m_rightJoystick = new Joystick(1);

  private final XboxController m_xbox = new XboxController(3);

  private LEDS leds;

  Loader m_loader = new Loader();
  DriveTrain m_DriveTrain = new DriveTrain();
  Shooter m_Shooter = new Shooter();

  Command load = new LoadBall(m_loader);
  Command fire = new FireBall(m_loader);
  Command shootOn = new ShooterOn(m_Shooter);
  Command shootOff = new ShooterOff(m_Shooter); 

  JoystickButton side_button = new JoystickButton(m_rightJoystick, 2);
  JoystickButton trigger = new JoystickButton(m_rightJoystick, 1);

  JoystickButton on = new JoystickButton(m_leftJoystick, 12);
  JoystickButton off = new JoystickButton(m_leftJoystick, 11);

  public RobotContainer() {
    // Configure the button bindings


    configureButtonBindings();
  }

  private void configureButtonBindings() {      
  }

  public Command xboxCommand(){
    // Command xbox = new XboxControl(
    //   m_DriveTrain, m_Shooter, m_loader,
    //   m_xbox.getY(Hand.kLeft), m_xbox.getY(Hand.kRight), aButton(),bButton());

    return null;
  }

  
  public Boolean bButton(){
    return m_xbox.getBButton();
  }

  public Boolean aButton(){
    return m_xbox.getAButton();
  }


  public Command fire(){
    Command fire = new FireBall(m_loader);
    return fire;
  }

  public Command load(){
    Command load = new LoadBall(m_loader);
    return load;
  }

  

  public Command getTeleOp() {

    load.execute();


    Command TeleOpCommand = new TankDrive(
      m_DriveTrain, 
      () -> m_leftJoystick.getY(), 
      () -> m_rightJoystick.getY(), 
      () -> getRightJoyStickThrottle(), 
      () -> getLeftJoyStickThrottle());

      side_button.whenReleased(load);
      trigger.whenReleased(fire);
      on.whenReleased(shootOn);
      off.whenReleased(shootOff);

      leds = new LEDS();

    return TeleOpCommand;
  }



  public Command getShootingCommand(){
    
    
    Command ShootingCommand = new ShootBall(m_Shooter, m_DriveTrain, m_loader);

    return ShootingCommand;
  }



  public ParallelCommandGroup getDriveAndShoot(){
    return new ParallelCommandGroup(getTeleOp(), getShootingCommand());
  }

public Loader getLoader(){
  return m_loader;
}
  
  
  public Command getAutonomousCommand() {
    return null;
  }



  public double getRightJoyStickThrottle() {
    int temp = (int)((((-m_rightJoystick.getThrottle()) + 1) / 2) * 100);
    return (((double)temp) / 100);
  }

  public double getLeftJoyStickThrottle() {
    int temp = (int)((((-m_leftJoystick.getThrottle()) + 1) / 2) * 100);
    return -(((double)temp) / 100);
  }

  public Shooter getShooter(){
    return m_Shooter;
  }

  public DriveTrain getDriveTrain() {
   
   //return m_DriveTrain;
   return m_DriveTrain;
  }
}
