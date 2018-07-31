/*********************************************************************
 * CLASS Meat
 * AUTHOR Jhi Morris
 * DATE CREATED 02/05/18
 * DATE LAST EDITED 29/05/18
 * PURPOSE Creates Meat object, with validation.
 *********************************************************************/

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Meat extends Food
{
  private String cut;
  private double weight;
  private Date useby;

  public Meat()
  { //default constructor
    super();
    this.setCut("");
    this.setWeight(0.2);
    this.setUseby(new Date(9999, 12, 31)); //year 9999, december 31st
  }

  public Meat(String name, String cut, double weight, double storageTemp,
    Date useby, String packaging)
  { //alt constructor
    super(name, storageTemp, packaging);
    this.setCut(cut);
    this.setWeight(weight);
    this.setUseby(useby);
  }

  public Meat(Meat meatIn)
  { //copy constructor
    super(meatIn);
    this.setCut(meatIn.getCut());
    this.setWeight(meatIn.getWeight());
    this.setUseby(meatIn.getUseby());
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

  public void setUseby(Date useby)
  {
    if (!super.isExpired(useby))
    {
      this.useby = (Date)useby.clone();
    }
    else
    {
      throw new IllegalArgumentException("Error: Useby date in the past.");
    }
  }

  public void setCut(String cut)
  {
    if (cut != null)
    {
      this.cut = new String(cut);
    }
    else
    {
      throw new IllegalArgumentException("Error: Cut is null.");
    }
  }

  //GETTERS
  public String getCut()
  {
    return new String(cut);
  }

  public double getWeight()
  {
    return weight;
  }

  public Date getUseby()
  {
    return (Date)useby.clone();
  }

  /*####################################################################
   # METHOD clone
   # IMPORTS none
   # EXPORTS clone (Meat)
   # PURPOSE Creates a deep-copy clone of the Meat.
   # ASSERTION Returns a clone of the Meat.
   *###################################################################*/

  public Meat clone()
  {
    Meat clone = new Meat(super.getName(), this.getCut(), this.getWeight(),
      super.getStorageTemp(), this.getUseby(),
      super.getPackaging());

    return clone;
  }

  /*####################################################################
   # METHOD toString
   # IMPORTS none
   # EXPORTS string (String)
   # PURPOSE Creates a plain-text string describing the Meat.
   # ASSERTION Returns a single-line String.
   *###################################################################*/

  public String toString()
  {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String string = (this.getWeight() + "g of " + super.getName() + " " +
    this.getCut() + ". Packaged in " + super.getPackaging() +
      ", with use-by date of " + df.format(this.getUseby()) +
      " and a storage temperature of " + super.getStorageTemp() + "°C.");

    return string;
  }

  /*####################################################################
   # METHOD equals
   # IMPORTS objIn (Object)
   # EXPORTS equals (Boolean)
   # PURPOSE Checks if objIn is equal to Meat object.
   # ASSERTION Returns true if the given object is a Meat object and if
   #           all aspects of both match.
   *###################################################################*/

  public boolean equals(Object objIn)
  {
    boolean equals = false;

    if (super.equals(objIn) && objIn instanceof Meat)
    {
      Meat meatIn = (Meat)objIn;

      if (meatIn.getCut().equals(this.getCut()) &&
        meatIn.getUseby().equals(this.getUseby()) &&
        meatIn.getWeight() == this.getWeight())
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
   # PURPOSE Checks if the Meat is past expiry, with today's date
   #         generated from the system time.
   # ASSERTION Returns true if today is past the Meat's use-by date.
   *###################################################################*/

  public boolean calcExpiry()
  {
    Date today = new Date();
    boolean expired;

    if (useby.after(today))
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
   # PURPOSE Checks if the Meat is past expiry, with today's date
   #         from the handed argument.
   # ASSERTION Returns true if today is past the Meat's best before date.
   *###################################################################*/

  public boolean calcExpiry(Calendar today)
  { //use this if you can't trust system time
    Date dateToday = today.getTime();
    boolean expired;
    if (useby.after(dateToday))
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
   # PURPOSE Creates a csv-compatible string describing the Meat's attributes
   # ASSERTION Returns a comma seperated single-line String.
   *###################################################################*/

  public String toCSV()
  {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String string = ("Meat, " + super.getName() + ", " + this.getCut() +
      ", " + this.getWeight() + ", " + super.getStorageTemp() + ", " +
      df.format(useby) + ", " + super.getPackaging());

    return string;
  }

}
