/*********************************************************************
 * CLASS Vegetable
 * AUTHOR Jhi Morris
 * DATE CREATED 02/05/18
 * DATE LAST EDITED 29/05/18
 * PURPOSE Creates Vegetabe object, with validation.
 *********************************************************************/

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Vegetable extends Food
{
  private double weight;
  private Date bestBefore;

  public Vegetable()
  { //default constructor
    super();
    this.setWeight(0.2);
    this.setBestBefore(new Date(9999, 12, 31)); //year 9999, december 31st
  }

  public Vegetable(String name, double weight, double storageTemp,
               Date bestBefore, String packaging)
  { //alt constructor
    super(name, storageTemp, packaging);
    this.setWeight(weight);
    this.setBestBefore(bestBefore);
  }

  public Vegetable(Vegetable vegetableIn)
  { //copy constructor
    super(vegetableIn);
    this.setWeight(vegetableIn.getWeight());
    this.setBestBefore(vegetableIn.getBestBefore());
  }

  //SETTERS
  public void setWeight(double weight)
  {
    if (weight >= 0.2 && weight <= 5.0)
    {
      this.weight = weight;
    }
    else
    {
      throw new IllegalArgumentException("Error: Weight out of bounds.");
    }
  }

  public void setBestBefore(Date bestBefore)
  {
    if (!isExpired(bestBefore))
    {
      this.bestBefore = (Date)bestBefore.clone();
    }
    else
    {
      throw new IllegalArgumentException("Error: Best-before date in past.");
    }
  }
  //GETTERS
  public double getWeight()
  {
    return this.weight;
  }

  public Date getBestBefore()
  {
    return (Date)this.bestBefore.clone();
  }

  /*####################################################################
   # METHOD clone
   # IMPORTS none
   # EXPORTS clone (Vegetable)
   # PURPOSE Creates a deep-copy clone of the Vegetable.
   # ASSERTION Returns a clone of the Vegetable.
   *###################################################################*/

  public Vegetable clone()
  {
    Vegetable clone = new Vegetable(super.getName(), this.getWeight(),
      super.getStorageTemp(), this.getBestBefore(), super.getPackaging());

    return clone;
  }

  /*####################################################################
   # METHOD toString
   # IMPORTS none
   # EXPORTS string (String)
   # PURPOSE Creates a plain-text string describing the Vegetable.
   # ASSERTION Returns a single-line String.
   *###################################################################*/

  public String toString()
  {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String string = (weight + "g of " + super.getName() + ". Packaged in " +
      super.getPackaging() + ", with best before date of " +
      df.format(bestBefore) + " and a storage temperature of " +
      super.getStorageTemp() + "°C.");

    return string;
  }

  /*####################################################################
   # METHOD equals
   # IMPORTS objIn (Object)
   # EXPORTS equals (Boolean)
   # PURPOSE Checks if objIn is equal to Vegetable object.
   # ASSERTION Returns true if the given object is a Vegetable object and if
   #           all aspects of both match.
   *###################################################################*/

  public boolean equals(Object objIn)
  {
    boolean equals = false;

    if (super.equals(objIn) && objIn instanceof Vegetable)
    {
      Vegetable vegeIn = (Vegetable)objIn;

      if (vegeIn.getWeight() == this.getWeight() &&
        vegeIn.getBestBefore().equals(this.getBestBefore()))
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
   # PURPOSE Checks if the Vegetable is past expiry, with today's date
   #         generated from the system time.
   # ASSERTION Returns true if today is past the Vegetable's best before date.
   *###################################################################*/

  public boolean calcExpiry()
  {
    boolean expired;
    Date today = new Date();

    if (this.bestBefore.after(today))
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
   # PURPOSE Checks if the Vegetable is past expiry, with today's date
   #         from the handed argument.
   # ASSERTION Returns true if today is past the Vegetable's best before date.
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
   # METHOD toCSV
   # IMPORTS none
   # EXPORTS string (String)
   # PURPOSE Creates a csv-compatible string describing the Vegetable's
   #         attributes.
   # ASSERTION Returns a comma seperated single-line String.
   *###################################################################*/

  public String toCSV()
  {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String string = ("Vegetable, " + super.getName() + ", " +
    this.getWeight() + ", " + super.getStorageTemp() + ", " +
    df.format(this.getBestBefore()) + ", " + super.getPackaging());

    return string;
  }
}
