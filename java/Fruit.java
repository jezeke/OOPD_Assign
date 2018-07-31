/*********************************************************************
 * CLASS Fruit
 * AUTHOR Jhi Morris
 * DATE CREATED 02/05/18
 * DATE LAST EDITED 25/05/18
 * PURPOSE Creates Fruit object, with validation.
 *********************************************************************/

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Fruit extends Food
{
  private String type;
  private int numPiece;
  private Date useby;

  public Fruit()
  { //default constructor
    super();
    this.setType("");
    this.setNumPiece(1);
    this.setUseby(new Date(9999, 12, 31)); //year 9999, december 31st
  }

  public Fruit(String name, String type, int numPiece, double storageTemp,
    Date useby, String packaging)
  { //alt constructor
    super(name, storageTemp, packaging);
    this.setType(type);
    this.setNumPiece(numPiece);
    this.setUseby(useby);
  }

  public Fruit(Fruit fruitIn)
  { //copy constructor
    super(fruitIn);
    this.setType(fruitIn.getType());
    this.setNumPiece(fruitIn.getNumPiece());
    this.setUseby(fruitIn.getUseby());
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

  public void setNumPiece(int numPiece)
  {
    if (TerminalUtility.inRange(numPiece, 1, 20))
    {
      this.numPiece = numPiece;
    }
    else
    {
      throw new IllegalArgumentException("Error: Pieces amount out of range.");
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

  //GETTERS
  public String getType()
  {
    return new String(this.type);
  }

  public int getNumPiece()
  {
    return this.numPiece;
  }

  public Date getUseby()
  {
    return (Date)this.useby.clone();
  }

  /*####################################################################
   # METHOD clone
   # IMPORTS none
   # EXPORTS clone (Vegetable)
   # PURPOSE Creates a deep-copy clone of the Fruit.
   # ASSERTION Returns a clone of the Fruit.
   *###################################################################*/

  public Fruit clone()
  {
    Fruit clone = new Fruit(super.getName(), this.getType(),
      this.getNumPiece(), super.getStorageTemp(), this.getUseby(),
      super.getPackaging());

    return clone;
  }

  /*####################################################################
   # METHOD toString
   # IMPORTS none
   # EXPORTS string (String)
   # PURPOSE Creates a plain-text string describing the Fruit.
   # ASSERTION Returns a single-line String.
   *###################################################################*/

  public String toString()
  {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String string = (this.getNumPiece() + " pieces of " + super.getName() +
     ", " + this.getType() + ". Packaged in " + super.getPackaging() +
      ", with use-by date of " + df.format(this.getUseby()) +
      " and a storage temperature of " + super.getStorageTemp() + "°C.");

    return string;
  }

  /*####################################################################
   # METHOD equals
   # IMPORTS objIn (Object)
   # EXPORTS equals (Boolean)
   # PURPOSE Checks if objIn is equal to Fruit object.
   # ASSERTION Returns true if the given object is a Fruit object and if
   #           all aspects of both match.
   *###################################################################*/

  public boolean equals(Object objIn)
  {
    boolean equals = false;

    if (super.equals(objIn) && objIn instanceof Fruit)
    {
      Fruit fruitIn = (Fruit)objIn;

      if (fruitIn.getType().equals(this.getType()) &&
        fruitIn.getUseby().equals(this.getUseby()) &&
        fruitIn.getNumPiece() == this.getNumPiece())
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
   # PURPOSE Checks if the Fruit is past expiry, with today's date
   #         generated from the system time.
   # ASSERTION Returns true if today is past the Fruit's use-by date.
   *###################################################################*/

  public boolean calcExpiry()
  {
    boolean expired;
    Date today = new Date();

    if (this.getUseby().after(today))
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
   # PURPOSE Checks if the Fruit is past expiry, with today's date
   #         from the handed argument.
   # ASSERTION Returns true if today is past the Fruit's best before date.
   *###################################################################*/

  public boolean calcExpiry(Calendar today)
  {
    Date dateToday = today.getTime();
    boolean expired;
    if (this.useby.after(dateToday))
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
   # PURPOSE Creates a csv-compatible string describing the Fruit's attributes
   # ASSERTION Returns a comma seperated single-line String.
   *###################################################################*/

  public String toCSV()
  {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String string = ("Fruit, " + super.getName() + ", " + this.getType() +
      ", " + this.getNumPiece() + ", " + super.getStorageTemp() + ", " +
      df.format(this.getUseby()) + ", " + super.getPackaging());

    return string;
  }
}
