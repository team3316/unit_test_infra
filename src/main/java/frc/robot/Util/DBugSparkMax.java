
package frc.robot.Util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.wpilibj.RobotBase;

public class DBugSparkMax extends CANSparkMax implements DBugMotorController {
  private CANEncoder _encoder;
  private CANPIDController _pidController;
  private double _distPerRevolution;

  /**
  * Testing stuff
  */
  private SimDevice _simMotor;
  private SimDouble _simPercent, _simVelocity, _simPosition;
  private double _demand;
  private boolean _isSimulation;

  /**
   * Create a new SPARK MAX Controller
   *
   * @param deviceNumber The device ID.
   * @param type         The motor type connected to the controller. Brushless
   *                     motors must be connected to their matching color and the
   *                     hall sensor plugged in. Brushed motors must be connected
   *                     to the Red and
   */
  public DBugSparkMax(int deviceNumber, MotorType type) {
    super(deviceNumber, type);

    this._encoder = this.getEncoder();
    this._pidController = this.getPIDController();

    this._isSimulation = RobotBase.isSimulation();

    if (this._isSimulation) {
      this._demand = 0.0;
      this._simMotor = SimDevice.create("Dbug Talon", deviceNumber);
      this._simPercent = this._simMotor.createDouble("Motor Precent", false, 0.0);
      this._simVelocity = this._simMotor.createDouble("Motor Velocity", false, 0.0);
      this._simPosition = this._simMotor.createDouble("Motor Position", false, 0.0);
    }

    this.configure();
  }

  /**
   * Create a new Spark MAX controller. This constructor uses the brushless
   * configuration by default since we usually use CTRE controllers for brushed
   * motors
   * 
   * @param deviceNumber The CAN device ID.
   */
  public DBugSparkMax(int deviceNumber) {
    this(deviceNumber, MotorType.kBrushless);
  }

  public void closeSims() {
    this._simMotor.close();
  }

  @Override
  public void configure() {
    this.restoreFactoryDefaults();
    this.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void setDistancePerRevolution(double dpr, int upr) {
    // No need for UPR because the Spark Max gives values from -1 to 1 on his own
    this._distPerRevolution = dpr;
  }

  /**
   * @return the encoder's value in rotations
   */
  @Override
  public double getEncoderValue() {
    return this._encoder.getPosition();
  }

  /**
   * @return the encoder's velocity in RPM
   */
  @Override
  public double getEncoderRate() {
    return this._encoder.getVelocity();
  }

  @Override
  public double getDistance() {
    if (this._isSimulation) return this._simPosition.get();
    return this._distPerRevolution * this.getEncoderValue();
  }

  @Override
  public void setDistance(double distance) {
    if (this._isSimulation) {
      this._simPosition.set(distance);
      return;
    }
    this._encoder.setPosition(distance / this._distPerRevolution);
  }

  @Override
  public double getVelocity() {
    if (this._isSimulation) return this._simVelocity.get();
    return this._distPerRevolution * this.getEncoderRate();
  }

  @Override
  public double getOutputCurrent() {
    return super.getOutputCurrent();
  }

  @Override
  public void setupPIDF(double kP, double kI, double kD, double kF) {
    this._pidController.setP(kP);
    this._pidController.setI(kI);
    this._pidController.setD(kD);
    this._pidController.setFF(kF);
    this._pidController.setOutputRange(-1.0, 1.0);
  }

  @Override
  public void zeroEncoder() {
    if (this._isSimulation) {
      this._simPosition.set(0.0);
      return;
    }
    this._encoder.setPosition(0);
  }

  @Override
  public void setNeutralMode (NeutralMode mode) {
    IdleMode idleModeFromNeutralMode = mode == NeutralMode.Brake ? IdleMode.kBrake : IdleMode.kCoast;
    this.setIdleMode(idleModeFromNeutralMode);
  }

  public void set(ControlMode mode, double outputValue) {
    this._demand = outputValue;
    switch (mode) {
    case Position:
      this._pidController.setReference(outputValue, ControlType.kPosition);
      break;
    case Velocity:
      this._pidController.setReference(outputValue, ControlType.kVelocity);
      break;
    case Current:
      this._pidController.setReference(outputValue, ControlType.kCurrent);
      break;
    case PercentOutput:
      this._simPercent.set(outputValue);
      this.set(outputValue);
      break;
    default:
      throw new Error("Control Mode " + mode.toString() + " isn't supported by the Spark MAX at the moment.");
    }
  }

  @Override
  public double get() {
    if (this._isSimulation) return this._simPercent.get();
    return super.get();
  }

  public double getDemand() {
    return this._demand;
  }
}