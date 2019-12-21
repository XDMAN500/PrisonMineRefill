package me.varmetek.plugin.prisonminerefill.util;

import org.bukkit.util.Vector;


/**
 * A class designed to combat the mutable nature of bukkit's vectors
 * */
public final class ImmutableVector
{


  private final double x,y,z;



  /**
   * Constructs the {@link ImmutableVector }
   * @param x the x coordinate
   * @param y the x coordinate
   * @param z the x coordinate
   * **/
  public ImmutableVector(double x, double y, double z){
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Copies the mutable vector's coordinates
   * @param vector the vector to copy
   * **/
  public ImmutableVector(Vector vector){
    this(vector.getX(),vector.getY(),vector.getZ());
  }


  /**
   * get the x coordinate
   * @return  X coordinate
   * **/
  public double getX(){
    return x;
  }


  /**
   * get the y coordinate
   * @return  Y coordinate
   * **/
  public double getY(){
    return y;
  }


  /**
   * get the z coordinate
   * @return  Z coordinate
   * **/
  public double getZ(){
    return z ;
  }


  /**
   * Creates a mutable bukkit vector with the held coordinate values
   * @return  a bukkit vector copy
   * **/
  public Vector toVector(){
    return new Vector(x,y,z);
  }





}
