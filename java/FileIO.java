/*********************************************************************
 * CLASS FileIO
 * AUTHOR Jhi Morris
 * DATE CREATED 10/05/18
 * DATE LAST EDITED 29/05/18
 * PURPOSE Creates and handles storage facility, manages reading and
 *         writing from/to files.
 *********************************************************************/

import java.io.*;
import java.util.Date;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.text.ParseException;

class FileIO
{
  private StorageFacility storFac;

  //PASSERS (See Design Philosophy document.)
  public Food[] passFreezer()
  {
    return storFac.getFreezer();
  }

  public Food[] passFridge()
  {
    return storFac.getFridge();
  }

  public Food[] passPantry()
  {
    return storFac.getPantry();
  }

  public void passRemove(int index, int location)
  {
    storFac.removeFood(index, location);
  }

  public void passAdd(Food food)
  {
    storFac.addFood(food);
  }

  /*####################################################################
   # METHOD isLoaded
   # IMPORTS none
   # EXPORTS loaded (Boolean)
   # ASSERTION Returns true if the storage facility exists.
   *###################################################################*/

  public boolean isLoaded()
  {
    boolean loaded = false;

    if (storFac != null)
    {
      loaded = true;
    }

    return loaded;
  }

  /*####################################################################
  # METHOD parseFile
  # IMPORTS filename (String)
  # EXPORTS skippedLines (Integer)
  # PURPOSE Organizes loading a file from csv, reading its header data,
  #         creating a storage facility with appropriate sizes, filling the
  #         facility's storage locations with data from the CSV rows.
  # ASSERTION Will load and process file & contents at given filename if
  #             contents are valid. Returns reported number of skipped data.
  *###################################################################*/

  public int parseFile(String filename) throws IOException
  {
    int skippedLines = 0; //counts errors during construction
    int[] sizes;
    String[][] csv;

    csv = loadCSV(filename);
    sizes = readHeader(csv);
    this.storFac = new StorageFacility(sizes);

    for (int i = 3; i < csv.length; i++) //start at 4th line
    {
      try
      {
        addFood(csv[i]);
      }
      catch (Exception e)
      {
        skippedLines++;
      }
    }

    return skippedLines;
  }

  /*####################################################################
   # METHOD loadCSV
   # IMPORTS filename (String)
   # EXPORTS array (2D array of strings)
   # PURPOSE Loads a single CSV file
   # ASSERTION Array with contents of CSV at filename will be returned
   #             (rows as rows, comma seperated values as column contents)
   *###################################################################*/

  public String[][] loadCSV(String filename) throws IOException
  {
    FileInputStream fileStrm = null;
    InputStreamReader rdr;
    BufferedReader bufRdr;
    int lineCount;
    String line;
    String[][] array;

    try
    {
      fileStrm = new FileInputStream(filename);
      rdr = new InputStreamReader(fileStrm);
      bufRdr = new BufferedReader(rdr);

      lineCount = 0;
      line = bufRdr.readLine();
      while (line != null)
      { //count the number of lines in the file
        lineCount++;
        line = bufRdr.readLine();
      }
      fileStrm.close();
    }
    catch (IOException e)
    {
      if (fileStrm != null)
      {
        try
        {
          fileStrm.close();
        }
        catch (IOException ex)
        {
          //move along, nothing to see here
        }
      }
      throw new IOException("Error: File not file found.");
    }

    try
    {
      fileStrm = new FileInputStream(filename);
      rdr = new InputStreamReader(fileStrm);
      bufRdr = new BufferedReader(rdr);

      array = new String[lineCount][];
      line = bufRdr.readLine();
      for(int i = 0; i < lineCount; i++)
      { //process each line in the file
        array[i] = processLine(line);
        line = bufRdr.readLine();
      }
      fileStrm.close();
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      if (fileStrm != null)
      {
        try
        {
          fileStrm.close();
        }
        catch (IOException ex)
        {
          //who cares?
        }
      }
      throw new IOException("Error: Non-CSV file found.");
    }
    return array;
  }

  /*####################################################################
   # METHOD processLine
   # IMPORTS csvRow (String)
   # EXPORTS line (Array of strings)
   # PURPOSE Seperates a string of comma seperated values into an array of
   #         those seperate values, seperated. S E P E R A T E
   *###################################################################*/

  private String[] processLine(String csvRow)
  {
    String thisToken = null;
    StringTokenizer strTok;
    int columnNum = 0;

    strTok = new StringTokenizer(csvRow, ",");
    while (strTok.hasMoreTokens())
    { //count the number of comma seperated values in the line
      strTok.nextToken();
      columnNum++;
    }

    String[] line = new String[columnNum];
    strTok = new StringTokenizer(csvRow, ",");
    for(int i = 0; i < columnNum; i++)
    { //process the values into an array
      line[i] = strTok.nextToken();
    }

    return line;
  }

  /*####################################################################
   # METHOD readHeader
   # IMPORTS array (2D array of strings)
   # EXPORTS sizes (Array of integers)
   # PURPOSE Parses header data of given compatible array and validates
   #         that no data is missing or invalid.
   # ASSERTION Returns the sizes of the storage compartments reported.
   *###################################################################*/

  private int[] readHeader(String[][] array) throws IllegalArgumentException
  {
    int freezer = -1; //init
    int fridge = -1;
    int pantry = -1;
    int sizes[];

    for (int i = 0; i < 3; i++)
    {
      try
      {
        if (Integer.parseInt(array[i][1].trim()) < 0)
        {
          throw new IllegalArgumentException("Error: Negative store size on" +
            " line " + i + ".");
        }

        if (array[i][0].trim().toLowerCase().equals("freezer"))
        {
          freezer = Integer.parseInt(array[i][1].trim());
        }
        else
        {
          if (array[i][0].trim().toLowerCase().equals("fridge"))
          {
            fridge = Integer.parseInt(array[i][1].trim());
          }
          else
          {
            if (array[i][0].trim().toLowerCase().equals("pantry"))
            {
              pantry = Integer.parseInt(array[i][1].trim());
            }
            else
            {
              throw new IllegalArgumentException("Error: Cannot parse data" +
                " on line " + i + ".");
            }
          }
        }
      } //would use switch if supported for strings in java 6
      catch (NumberFormatException e)
      {
        throw new IllegalArgumentException("Error: Cannot parse data on"+
          " line " + i + ".");
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
        throw new IllegalArgumentException("Error: Invalid header data.");
      }
    }

    if (fridge == -1 || freezer == -1 || pantry == -1)
    { //if unchanged from init then data is lacking
      throw new IllegalArgumentException("Error: Invalid header data.");
    }

    sizes = new int[]{freezer, fridge, pantry};
    return sizes;
  }

  /*####################################################################
   # METHOD addFood
   # IMPORTS line (Array of strings)
   # EXPORTS none
   # PURPOSE Parses an array of strings as arguments to construct an
   #         object based on its type (as defined in array's first string)
   *###################################################################*/

  public void addFood(String[] line)
    throws ParseException, IllegalArgumentException
  {
    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
    dt.setLenient(false);

    if (line[0].trim().toLowerCase().equals("meat")) //sort meat
    {
      Meat meat = new Meat();

      meat.setName(line[1].trim());
      meat.setCut(line[2].trim());
      meat.setWeight(Double.parseDouble(line[3].trim()));
      meat.setStorageTemp(Double.parseDouble(line[4].trim()));
      meat.setUseby(dt.parse(line[5].trim()));
      meat.setPackaging(line[6].trim());

      storFac.addFood(meat);
    }
    else
    {
      if (line[0].trim().toLowerCase().equals("grain")) //sort grain
      {
        Grain grain = new Grain();

        grain.setName(line[1].trim());
        grain.setType(line[2].trim());
        grain.setVolume(Double.parseDouble(line[3].trim()));
        grain.setStorageTemp(Double.parseDouble(line[4].trim()));
        grain.setBestBefore(dt.parse(line[5].trim()));
        grain.setPackaging(line[6].trim());

        storFac.addFood(grain);
      }
      else
      {
        if (line[0].trim().toLowerCase().equals("fruit")) //type fruit
        {
          Fruit fruit = new Fruit();

          fruit.setName(line[1].trim());
          fruit.setType(line[2].trim());
          fruit.setNumPiece(Integer.parseInt(line[3].trim()));
          fruit.setStorageTemp(Double.parseDouble(line[4].trim()));
          fruit.setUseby(dt.parse(line[5].trim()));
          fruit.setPackaging(line[6].trim());

          storFac.addFood(fruit);
        }
        else
        {
          if (line[0].trim().toLowerCase().equals("vegetable")) //type vege
          {
            Vegetable vegetable = new Vegetable();

            vegetable.setName(line[1].trim());
            vegetable.setWeight(Double.parseDouble(line[2].trim()));
            vegetable.setStorageTemp(Double.parseDouble(line[3].trim()));
            vegetable.setBestBefore(dt.parse(line[4].trim()));
            vegetable.setPackaging(line[5].trim());

            storFac.addFood(vegetable);
          }
          else
          {
            throw new IllegalArgumentException("Error: Invalid sort.");
          }
        }
      }
    } //would use switch if supported for strings in java 6
  }

  /*####################################################################
   # METHOD writeStorageCSV
   # IMPORTS filename (String)
   # EXPORTS none
   # PURPOSE Writes a CSV file containing information on the foods
   #         contained within the storage locations inside of the
   #         storage facility, along with an appropriate file header.
   *###################################################################*/

  public void writeStorageCSV(String filename) throws IOException
  {
    Food[][] storage = storFac.getStorage();
    int freezerLength = storage[storFac.FREEZER].length;
    int fridgeLength = storage[storFac.FRIDGE].length;
    int pantryLength = storage[storFac.PANTRY].length;

    FileOutputStream fileStrm = null;
    PrintWriter pw;

    try
    {
      fileStrm = new FileOutputStream(filename);
      pw = new PrintWriter(fileStrm);

      pw.println("Freezer, " + freezerLength);
      pw.println("Fridge, " + fridgeLength);
      pw.println("Pantry, " + pantryLength);

      for (int i = 0; i < storage.length; i++)
      {
        for (int j = 0; j < storage[i].length; j++)
        {
          if (storage[i][j] != null)
          {
            pw.println(storage[i][j].toCSV());
          }
        }
      }
      pw.close();
    }
    catch (IOException e)
    {
      if (fileStrm != null)
      {
        try
        {
          fileStrm.close();
        }
        catch (IOException ex2)
        {
          //these aren't the exceptions you're looking for, move along
        }
      }
      throw new IOException("Error: Unable to write to file.");
    }
  }
}
