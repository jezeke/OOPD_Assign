/*********************************************************************
 * CLASS StorageFacility
 * AUTHOR Jhi Morris
 * DATE CREATED 05/05/18
 * DATE LAST EDITED 29/05/18
 * PURPOSE Holds array of Foods and allows addition and removal of Food
 *         objects to it. Stores Foods differently based on temperature.
 *********************************************************************/

public class StorageFacility
{
  private Food[][] storage = new Food[3][];

  public final int FREEZER = 0; //magic numbers used for sorting storage
  public final int FRIDGE = 1;
  public final int PANTRY = 2;

  public StorageFacility()
  { //default constructor
    this(new int[] {0, 0, 0});
  }

  public StorageFacility(int[] sizes)
  { //alt constructor
    setStorage(FREEZER, sizes[0]);
    setStorage(FRIDGE, sizes[1]);
    setStorage(PANTRY, sizes[2]);
  }

  public StorageFacility(StorageFacility storFac)
  { //copy constructor
    Food[][] storage = storFac.getStorage();

    for (int i = 0; i < storage.length; i++)
    {
      setStorage(i, storage[i].length);

      for (int j = 0; j < storage[i].length; j++)
      {
        if (storage[i][j] != null)
        {
          addFood(storage[i][j].clone());
        }
      }
    }
  }

  /*####################################################################
   # METHOD setStorage
   # IMPORTS location (Integer), length (Integer)
   # PURPOSE Sets the size of a storage location based on imported length.
   *###################################################################*/

  private void setStorage(int location, int length)
    throws IllegalStateException
  {
    if (length < 0)
    {
      throw new IllegalArgumentException("Error: Cannot create store with" +
        " negative length.");
    }

    if (storage[location] != null)
    {
      throw new IllegalStateException("Error: Cannot make multiple" +
      " instances of location.");
    }

    storage[location] = new Food[length];
  }
  /*####################################################################
   # METHOD getFridge
   # IMPORTS none
   # EXPORTS out (Array of Food objects)
   # PURPOSE Creates a deep copy of the fridge's contents
   #         and exports it all as an array of Food objects.
   *###################################################################*/

  public Food[] getFridge()
  {
    Food[] fridge = storage[FRIDGE];
    Food[] out = new Food[fridge.length];

    for (int i = 0; i < fridge.length; i++)
    {
      if (fridge[i] != null)
      {
        out[i] = fridge[i].clone();
      }
    }

    return out;
  }

  /*####################################################################
   # METHOD getPantry
   # IMPORTS none
   # EXPORTS out (Array of Food objects)
   # PURPOSE Creates a deep copy of the pantry's contents
   #         and exports it all as an array of Food objects.
   *###################################################################*/

  public Food[] getPantry()
  {
    Food[] pantry = storage[PANTRY];
    Food[] out = new Food[pantry.length];

    for (int i = 0; i < pantry.length; i++)
    {
      if (pantry[i] != null)
      {
        out[i] = pantry[i].clone();
      }
    }

    return out;
  }

  /*####################################################################
   # METHOD getFreezer
   # IMPORTS none
   # EXPORTS out (Array of Food objects)
   # PURPOSE Creates a deep copy of the freezer's contents
   #         and exports it all as an array of Food objects.
   *###################################################################*/

  public Food[] getFreezer()
  {
    Food[] freezer = storage[FREEZER];
    Food[] out = new Food[freezer.length];

    for (int i = 0; i < freezer.length; i++)
    {
      if (freezer[i] != null)
      {
        out[i] = freezer[i].clone();
      }
    }

    return out;
  }

  /*####################################################################
   # METHOD getStorage
   # IMPORTS none
   # EXPORTS out (Array of array of Food objects)
   # PURPOSE Creates a deep copy of each storage location's contents
   #         and exports it all as a 2D array of Food objects.
   *###################################################################*/

  public Food[][] getStorage()
  {
    Food[][] out = new Food[this.storage.length][];

    for (int i = 0; i < storage.length; i++)
    {
      out[i] = new Food[this.storage[i].length];

      for (int j = 0; j < this.storage[i].length; j++)
      {
        if (this.storage[i][j] != null)
        {
          out[i][j] = this.storage[i][j].clone();
        }
      }
    }

    return out;
  }

  /*####################################################################
   # METHOD addFood
   # IMPORTS food (Food object)
   # EXPORTS none
   # PURPOSE Adds a Food object to a storage location, checking its
   #         temperature to discern which location is appropriate
   #         for the item. Fails if the storage location is full.
   *###################################################################*/

  public void addFood(Food food) throws IllegalStateException
  {
    if (food == null)
    {
      throw new IllegalStateException("Error: Null food object.");
    }

    switch (checkTemp(food.getStorageTemp()))
    {
      case FREEZER:
        try
        {
          storage[FREEZER][nextNullAt(getFreezer())] = food.clone();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
          throw new IllegalStateException("Error: No space in freezer.");
        }
        break;
      case FRIDGE:
        try
        {
          storage[FRIDGE][nextNullAt(getFridge())] = food.clone();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
          throw new IllegalStateException("Error: No space in fridge.");
        }
        break;
      case PANTRY:
        try
        {
          storage[PANTRY][nextNullAt(getFreezer())] = food.clone();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
          throw new IllegalStateException("Error: No space in pantry.");
        }
    }
  }

  /*####################################################################
   # METHOD nextNullAt
   # IMPORTS array (Array of objects)
   # EXPORTS i (Integer)
   # PURPOSE Searches an array for the first null index and returns it.
   #         This is used to ensure empty locations made by Food deletion
   #         do not go unused. Technically works with non-Food arrays too.
   # ASSERTION The index returned will be the first index at which the
   #             given array has a null index.
   *###################################################################*/

  private static int nextNullAt(Object[] array)
    throws ArrayIndexOutOfBoundsException
  {
    int i = 0;
    while (array[i] != null)
    {
      i++;
    }
    return i;
  }

  /*####################################################################
   # METHOD checkTemp
   # IMPORTS storageTemp (Double)
   # EXPORTS sort (Integer)
   # PURPOSE Compares a temperature to defined bounds and returns storage
   #         location index for appropriate storage location.
   # ASSERTION The returned int will be a valid location in the storage
   #             2D array, and that location will suit the temperature.
   #             Fails if the temperature is outside of all ranges.
   *###################################################################*/

  private int checkTemp(double storageTemp) throws IllegalArgumentException
  {
    int sort;

    if (TerminalUtility.inRange(storageTemp, -27.0, 25.0))
    {
      if (storageTemp < -5.0) //temp freezer
      {
        sort = FREEZER;
      }
      else
      {
        if (storageTemp > 25.0) //temp pantry
        {
          sort = PANTRY;
        }
        else //temp fridge
        {
          sort = FRIDGE;
        }
      }
    }
    else //temp out of range
    {
      throw new IllegalArgumentException("Error: Temperature out of range.");
    }

    return sort;
  }

  /*####################################################################
   # METHOD removeFood
   # IMPORTS location (Integer), element (Integer)
   # EXPORTS none
   # PURPOSE Removes a food from given location at given element.
   # NOTES Does not use array index (counting from 0) rather uses
   #       location element (counted from 1, not counting null indexes)
   *###################################################################*/

  public void removeFood(int location, int element)
    throws IllegalArgumentException
  {
    int elementAt = 0;
    int index = 0;
    boolean found = false;

    if (location > 2 || location < 0)
    {
      throw new IllegalArgumentException("Error: Invalid Location.");
    }

    try
    {
      do
      {
        if (storage[location][index] != null)
        {
          elementAt++;
          if (elementAt == element)
          {
            storage[location][index] = null; //remove food
            found = true;
          }
        }
        index++;
      }
      while (!found);
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new IllegalArgumentException("Error: Element not found.");
    }
  }
}
