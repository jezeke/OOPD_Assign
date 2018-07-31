/*********************************************************************
 * CLASS Grain
 * AUTHOR Jhi Morris
 * DATE CREATED 02/05/18
 * DATE LAST EDITED 25/05/18
 * PURPOSE Creates Grain object, with validation.
 *********************************************************************/

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Grain extends Food
{
  private String type;
  private double volume;
  private Date bestBefore;

  public Grain()
  { //default constructor
    super();
    this.setType("");
    this.setVolume(0.2);
    this.setBestBefore(new Date(9999, 12, 31)); //year 9999, december 31st
  }

  public Grain(String name, String type, double volume, double storageTemp,
    Date bestBefore, String packaging)
  { //alt constructor
    super(name, storageTemp, packaging);
    this.setType(type);
    this.setVolume(volume);
    this.setBestBefore(bestBefore);
  }

  public Grain(Grain grainIn)
  { //copy constructor
    super(grainIn);
    this.setType(grainIn.getType());
    this.setVolume(grainIn.getVolume());
    this.setBestBefore(grainIn.getBestBefore());
  }

  //SETTERS
  public void setType(String type)
  {
    if (type != null)
    {
      this.type = new String(type);
    }
    else
    {
      throw new IllegalArgumentException("Error: Type is null.");
    }
  }

  public void setVolume(double volume)
  {
    if (volume >= 0.2 && volume <= 5.0)
    {
      this.volume = volume;
    }
    else
    {
      throw new IllegalArgumentException("Error: Volume is out of range.");
    }
  }

  public void setBestBefore(Date bestBefore)
  {
    if (!super.isExpired(bestBefore))
    {
      this.bestBefore = (Date)bestBefore.clone();
    }
    else
    {
      throw new IllegalArgumentException("Error: Best-before date in past.");
    }
  }

  //GETTERS
  public String getType()
  {
    return new String(this.type);
  }

  public double getVolume()
  {
    return this.volume;
  }

  public Date getBestBefore()
  {
    return (Date)this.bestBefore.clone();
  }

  /*####################################################################
   # METHOD clone
   # IMPORTS none
   # EXPORTS clone (Grain)
   # PURPOSE Creates a deep-copy clone of the Grain.
   # ASSERTION Returns a clone of the Grain.
   *###################################################################*/

  public Grain clone()
  {
    Grain clone = new Grain(super.getName(), this.getType(), this.getVolume(),
      super.getStorageTemp(), this.getBestBefore(), super.getPackaging());

    return clone;
  }

  /*####################################################################
   # METHOD toString
   # IMPORTS none
   # EXPORTS string (String)
   # PURPOSE Creates a plain-text string describing the Grain.
   # ASSERTION Returns a single-line String.
   *###################################################################*/

  public String toString()
  {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String string = (this.getVolume() + "L of " + super.getName() + ", " +
      this.getType() + ". Packaged in " + super.getPackaging() +
      ", with a best-before date of " + df.format(this.getBestBefore()) +
      " and a storage temperature of " + super.getStorageTemp() + "°C.");

    return string;
  }

  /*####################################################################
   # METHOD equals
   # IMPORTS objIn (Object)
   # EXPORTS equals (Boolean)
   # PURPOSE Checks if objIn is equal to Grain object.
   # ASSERTION Returns true if the given object is a Grain object and if
   #           all aspects of both match.
   *###################################################################*/

  public boolean equals(Object objIn)
  {
    boolean equals = false;

    if (super.equals(objIn) && objIn instanceof Grain)
    {
      Grain grainIn = (Grain)objIn;

      if (grainIn.getType().equals(this.getType()) &&
        grainIn.getBestBefore().equals(this.getBestBefore()) &&
        grainIn.getVolume() == this.getVolume())
        {
          equals = true;
        }
    }

    return equals;
  }

  /*####################################################################
   # METHOD calcExpiry
   # IMPORTS none
   # EXPORTS expired (Boolean)
   # PURPOSE Checks if the Grain's is past expiry, with today's date
   #         generated from the system time.
   # ASSERTION Returns true if today is past the Grain's best before date.
   *###################################################################*/

  public boolean calcExpiry()
  {
    boolean expired;
    Date today = new Date();

    if (this.getBestBefore().after(today))
    {
      expired = false;
    }
    else
    {
      expired = true;
    }
    return expired;
  }

  /*####################################################################
   # METHOD calcExpiry
   # IMPORTS today (Calendar)
   # EXPORTS expired (Boolean)
   # PURPOSE Checks if the Grain is past expiry, with today's date
   #         from the handed argument.
   # ASSERTION Returns true if today is past the Grain's best before date.
   *###################################################################*/

  public boolean calcExpiry(Calendar today)
  {
    Date dateToday = today.getTime();
    boolean expired;
    if (this.bestBefore.after(dateToday))
    {
      expired = false;
    }
    else
    {
      expired = true;
    }
    return expired;
  }

  /*####################################################################
   # METHOD toString
   # IMPORTS none
   # EXPORTS string (String)
   # PURPOSE Creates a csv-compatible string describing the Grain's attributes
   # ASSERTION Returns a comma seperated single-line String.
   *###################################################################*/

  public String toCSV()
  {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String string = ("Grain, " + super.getName() + ", " + this.getType() +
      ", " + this.getVolume() + ", " + super.getStorageTemp() + ", " +
      df.format(this.getBestBefore()) + ", " + super.getPackaging());

    return string;
  }

}
