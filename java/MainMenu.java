/*********************************************************************
 * CLASS MainMenu
 * AUTHOR Jhi Morris
 * DATE CREATED 02/05/18
 * DATE LAST EDITED 29/05/18
 * PURPOSE Displays menu options and calls on operations to allow user
 *         to control all facilities of the program.
 *********************************************************************/

import java.text.ParseException;
import java.io.IOException;

public class MainMenu
{
  private static TerminalUtility util;
  private static FileIO fio;

  public MainMenu()
  { //default constuctor
    util = new TerminalUtility();
    fio = new FileIO();
  }

  /*####################################################################
   # METHOD menuStart
   # IMPORTS none
   # EXPORTS none
   # PURPOSE The starting point of the FoodVend program. Renders the main
   #         menu and brings the user to the selected submenu.
   #         All roads lead back to here.
   *###################################################################*/

  public void menuStart()
  {
    int choice;
    boolean exit = false;

    do
    {
      choice = util.menuSelect(util.YELANSI +
      "+|FoodVend Management Terminal|+\n" + util.SETANSI +
      "Please select a menu option from below:\n\n" +
      util.REDANSI + "[1]" + util.SETANSI + " Add Food\n" +
      util.REDANSI + "[2]" + util.SETANSI + " Remove Food\n" +
      util.REDANSI + "[3]" + util.SETANSI + " Display Contents\n" +
      util.REDANSI + "[4]" + util.SETANSI + " Find Expired\n" +
      util.REDANSI + "[5]" + util.SETANSI + " Read IN Storage\n" +
      util.REDANSI + "[6]" + util.SETANSI + " Write OUT Storage\n" +
      util.REDANSI + "[0]" + util.SETANSI + " Exit", null, 6);

      switch (choice)
      {
        case 1:
          addFood();
          break;
        case 2:
          removeFood();
          break;
        case 3:
          displayContents();
          break;
        case 4:
          findExpired();
          break;
        case 5:
          readIn();
          break;
        case 6:
          writeOut();
          break;
        case 0:
          exit = true;
          TerminalUtility.clearTerminal();
          System.out.println("Exiting FoodVend Management Terminal...");
          break;
      }

    }
    while (!exit);

  }

  /*####################################################################
   # METHOD addFood
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Renders the submenu of options used to add foods and
   #         directs the user to the appropriate sub-submenu based
   #         on their selection. It's submenus return back here.
   *###################################################################*/

  private static void addFood()
  {
    boolean exit = false;
    String err = null;

    if (fio.isLoaded())
    {
      int choice;

      do
      {
        if (err != null && err.equals("")) //if it worked last loop
        {
          TerminalUtility.clearTerminal();
          System.out.println("Food added successfully.\nPress Enter" +
            " to return to menu.");
          util.waitForEnter();
        }

        choice = util.menuSelect(util.YELANSI + "+|Add Food|+\n" +
        util.SETANSI + "Please select a menu option from below:\n\n" +
        util.REDANSI + "[1]" + util.SETANSI + " Meat\n" +
        util.REDANSI + "[2]" + util.SETANSI + " Vegetables\n" +
        util.REDANSI + "[3]" + util.SETANSI + " Grain\n" +
        util.REDANSI + "[4]" + util.SETANSI + " Fruit\n" +
        util.REDANSI + "[0]" + util.SETANSI + " Return to Main Menu", err, 4);
        err = ""; //reset

        try
        {
          switch (choice)
          {
            case 1:
              addMeat();
              break;
            case 2:
              addVege();
              break;
            case 3:
              addGrain();
              break;
            case 4:
              addFruit();
              break;
            case 0:
              exit = true;
              break;
          }
        }
        catch (ParseException ex)
        {
          err = "Error: Invalid date.";
        }
        catch (NumberFormatException exc)
        {
          err = "Error: Invalid number input.";
        }
        catch (Exception e)
        { //validation at each step of input would be nice but i'm behind :(
          err = e.getMessage();
        }
      }
      while (!exit);
    }
    else
    {
      storageLackError();
    }
  }

  /*####################################################################
   # METHOD addMeat
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Takes user input for each parameter of meat and attempts to
   #         create an object from them and add them to the storage facility.
   *###################################################################*/

  private static void addMeat()
    throws IllegalArgumentException, ParseException
  {
    TerminalUtility.clearTerminal();

    String name = util.inputString("Please input meat name:\n");

    String cut = util.inputString("Please input meat cut:\n");

    String weight = util.inputString("Please input meat weight:\n");

    String temp = util.inputString("Please input storage temperature:\n");

    String useby = util.inputString("Please input use-by date DD/MM/YYYY:\n");

    String packaging = util.inputString("Please input meat packaging:\n");

    fio.addFood(new String[] {"Meat", name, cut, weight, temp,
      useby, packaging}); //lazy code reuse shortcut
  }

  /*####################################################################
   # METHOD addVege
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Takes user input for each parameter of vegetables and attempts to
   #         create an object from them and add them to the storage facility.
   *###################################################################*/

  private static void addVege()
    throws IllegalArgumentException, ParseException
  {
    TerminalUtility.clearTerminal();

    String name = util.inputString("Please input vegetable name:\n");

    String weight = util.inputString("Please input vegetable weight:\n");

    String temp = util.inputString("Please input storage temperature:\n");

    String bestBefore = util.inputString( //long string literals are long
      "Please input best-before date DD/MM/YYYY:\n");

    String packaging = util.inputString("Please input vegetable packaging:\n");

    fio.addFood(new String[] {"Vegetable", name, weight, temp,
      bestBefore, packaging}); //lazy code reuse shortcut
  }

  /*####################################################################
   # METHOD addGrain
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Takes user input for each parameter of grain and attempts to
   #         create an object from them and add them to the storage facility.
   *###################################################################*/

  private static void addGrain()
    throws IllegalArgumentException, ParseException
  {
    TerminalUtility.clearTerminal();

    String name = util.inputString("Please input grain name:\n");

    String type = util.inputString("Please input grain type:\n");

    String volume = util.inputString("Please input grain volume:\n");

    String temp = util.inputString("Please input storage temperature:\n");

    String bestBefore = util.inputString( //long string literals are long
      "Please input best-before date DD/MM/YYYY:\n");

    String packaging = util.inputString("Please input grain packaging:\n");

    fio.addFood(new String[] {"Grain", name, type, volume, temp,
      bestBefore, packaging}); //lazy code reuse shortcut
  }

  /*####################################################################
   # METHOD addFruit
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Takes user input for each parameter of fruit and attempts to
   #         create an object from them and add them to the storage facility.
   *###################################################################*/

  private static void addFruit()
    throws IllegalArgumentException, ParseException
  {
    TerminalUtility.clearTerminal();

    String name = util.inputString("Please input fruit name:\n");

    String type = util.inputString("Please input fruit type:\n");

    String numPiece = util.inputString("Please input number of pieces:\n");

    String temp = util.inputString("Please input storage temperature:\n");

    String useby = util.inputString("Please input use-by date DD/MM/YYYY:\n");

    String packaging = util.inputString("Please input fruit packaging:\n");

    fio.addFood(new String[] {"Fruit", name, type, numPiece, temp,
                             useby, packaging}); //lazy code reuse shortcut
  }

  /*####################################################################
   # METHOD removeFood
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Renders the submenu of options used to remove foods and
   #         directs the user to the appropriate sub-submenu based
   #         on their selection. It's submenus return back here.
   *###################################################################*/

  private static void removeFood()
  {
    int element, choice;
    String err = null;
    boolean exit = false;

    if (fio.isLoaded())
    {
      do
      {
        choice = util.menuSelect(util.YELANSI + "+|Remove Food|+\n" +
        util.SETANSI + "Please select a location from below:\n\n" +
        util.REDANSI + "[1]" + util.SETANSI + " Freezer\n" +
        util.REDANSI + "[2]" + util.SETANSI + " Fridge\n" +
        util.REDANSI + "[3]" + util.SETANSI + " Pantry\n" +
        util.REDANSI + "[0]" + util.SETANSI + " Return to Main Menu", err, 3);
        err = "";

        if (choice == 0)
        {
          exit = true;
        }
        else
        {
          TerminalUtility.clearTerminal();
          System.out.println("Enter element number of food to be" +
            " removed:\n\n");
          element = util.inputInt();

          try
          {
            fio.passRemove(choice - 1, element); //-1 because 0 = freezer, etc
          }
          catch (IllegalArgumentException e)
          {
            err = e.getMessage();
            exit = false;
          }

          if (err != null && err.equals(""))
          {
            TerminalUtility.clearTerminal();
            System.out.println("Food removed successfully.\nPress Enter" +
              " to return to menu.");
            util.waitForEnter();
          }
        }
      }
      while (!exit);
    }
    else
    {
      storageLackError();
    }

  }

  /*####################################################################
   # METHOD displayContents
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Renders the submenu of options used to display the
   #         contents of the storage facility and directs the user to
   #         the appropriate sub-submenu based on their selection.
   #         It's submenus return back here.
   *###################################################################*/

  private static void displayContents()
  {
    if (fio.isLoaded())
    {
      Food[] array;
      int choice;
      boolean exit = false;

      do
      {
        choice = util.menuSelect(util.YELANSI + "+|Display Contents|+\n" +
        util.SETANSI + "Please select a location from below:\n\n" +
        util.REDANSI + "[1]" + util.SETANSI + " Freezer\n" +
        util.REDANSI + "[2]" + util.SETANSI + " Fridge\n" +
        util.REDANSI + "[3]" + util.SETANSI + " Pantry\n" +
        util.REDANSI + "[0]" + util.SETANSI + " Return to Main Menu", null, 3);
        array = null;

        switch (choice)
        {
          case 0:
            exit = true;
            break;
          case 1:
            array = fio.passFreezer();
            break;
          case 2:
            array = fio.passFridge();
            break;
          case 3:
            array = fio.passPantry();
            break;
        }

        if (array != null)
        {
          TerminalUtility.clearTerminal();

          printElements(array);

          System.out.println("Press Enter to return to menu.");
          util.waitForEnter();
        }
      }
      while (!exit);
    }
    else
    {
      storageLackError();
    }
  }

  /*####################################################################
   # METHOD printElements
   # IMPORTS array (Array of Objects)
   # EXPORTS none
   # PURPOSE Outputs the toString method's export for each object in an array
   #         along with a numbering (not counting null elements)
   *###################################################################*/

  private static void printElements(Object[] array)
  {
    int element = 0;

    for (int i = 0; i < array.length; i++)
    {
      if (array[i] != null)
      {
        element++;
        System.out.println(element + ": " + array[i].toString());
      }
    }

    if (array.length == 0 || element == 0)
    {
      System.out.println(util.YELANSI +
        "+|No elements found.|+" + util.SETANSI);
    }
  }

  /*####################################################################
   # METHOD findExpired
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Organizes displaying the expired contents of storage
   *###################################################################*/

  private static void findExpired()
  {
    if (fio.isLoaded())
    {
      boolean exit = false;
      int choice = util.menuSelect(util.YELANSI + "+|Find Expired|+\n" +
      util.SETANSI + "Please select a menu option from below:\n\n" +
      util.REDANSI + "[1]" + util.SETANSI + " Find Expired\n" +
      util.REDANSI + "[0]" + util.SETANSI + " Return to Main Menu", null, 1);

      if (choice == 1)
      {
        Food[] fridge = fio.passFridge();
        Food[] pantry = fio.passPantry();
        Food[] freezer = fio.passFreezer();

        TerminalUtility.clearTerminal();
        System.out.println(util.YELANSI + "+|Freezer: " + util.SETANSI);
        printExpired(freezer);
        System.out.println(util.YELANSI + "+|Fridge: " + util.SETANSI);
        printExpired(fridge);
        System.out.println(util.YELANSI + "+|Pantry: " + util.SETANSI);
        printExpired(pantry);

        System.out.println("\nPress Enter to continue to main menu.");
        util.waitForEnter();
      }
    }
    else
    {
      storageLackError();
    }
  }

  /*####################################################################
   # METHOD printExpired
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Scans through an array of Foods and prints their toString
   #         method along with the number of which element it is.
   #         The element count is distinct to the index, as the index count
   #         also counts null/empty indices and counts from 0.
   *###################################################################*/

  private static void printExpired(Food[] array)
  {
    int elementAt = 0;

    for (int i = 0; i < array.length; i++)
    {
      if (array[i] != null)
      {
        elementAt++;
        if (array[i].calcExpiry())
        {
          System.out.println(elementAt + ": " + array[i].toString());
        }
      }
    }
  }

  /*####################################################################
   # METHOD readIn
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Takes string input for a filename and organizes loading the
   #         file at that filename.
   *###################################################################*/

  private static void readIn()
  {
    if (!fio.isLoaded())
    {
      boolean exit = false;
      int choice;
      int skippedLines = 0;
      String err = null;

      do
      {
        choice = util.menuSelect(util.YELANSI + "+|Read IN Storage|+\n" +
        util.SETANSI + "Please select a menu option from below:\n\n" +
        util.REDANSI + "[1]" + util.SETANSI + " Load From File\n" +
        util.REDANSI + "[0]" + util.SETANSI + " Return to Main Menu", err, 1);
        err = null;

        switch (choice)
        {
          case 0:
            exit = true;
            break;
          case 1:
            TerminalUtility.clearTerminal();
            String input = util.inputString("Please input filename to load:");

            try
            {
              skippedLines = fio.parseFile(input);
              exit = true;
            }
            catch (Exception e)
            {
              err = e.getMessage();
              exit = false;
            }
            break;
        }
      }
      while (!exit);

      if (fio.isLoaded())
      {
        TerminalUtility.clearTerminal();

        System.out.println("File load complete.\n" +
        skippedLines + " elements skipped due to errors.\n\n" +
        "Press Enter to return to main menu.");
        util.waitForEnter();
      }
    }
    else
    {
      storagePresentError();
    }
  }

  /*####################################################################
   # METHOD writeOut
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Takes string input for a filename and organizes writing
   #         out storage contents to a file at that filename.
   *###################################################################*/

  private static void writeOut()
  {
    if (fio.isLoaded())
    {
      int choice;
      boolean exit = false;
      String err = null;

      do
      {
        if (err != null && err.equals("")) //if it worked last loop
        {
          TerminalUtility.clearTerminal();

          System.out.println("File save complete.\n" +
          "Press Enter to return to menu.");
          util.waitForEnter();
        }

        choice = util.menuSelect(util.YELANSI + "+|Write OUT Storage|+\n" +
        util.SETANSI + "Please select a menu option from below:\n\n" +
        util.REDANSI + "[1]" + util.SETANSI + " Write to File\n" +
        util.REDANSI + "[0]" + util.SETANSI + " Return to Main Menu", err, 1);
        err = "";

        switch (choice)
        {
          case 0:
            exit = true;
            break;
          case 1:
            TerminalUtility.clearTerminal();
            String filename = util.inputString(
              "Please input filename to write to:");

            try
            {
              fio.writeStorageCSV(filename);
            }
            catch (IOException e)
            {
              err = e.getMessage();
              exit = false;
            }
            break;
        }
      }
      while (!exit);
    }
    else
    {
      storageLackError();
    }
  }

  /*####################################################################
   # METHOD storageLackError
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Prints a message stating that the storage facility has not
   #         been loaded yet and pauses until enter is pressed before
   #         returning to the previous submenu.
   *###################################################################*/

  private static void storageLackError()
  {
    TerminalUtility.clearTerminal();
    System.out.println(util.YELANSI +
    "+|The storage facility has not been loaded.|+\n" + util.SETANSI +
    "Please load the storage facility before attempting this task.\n\n" +
    "Press Enter to return to menu");
    util.waitForEnter();
  }

  /*####################################################################
   # METHOD storageLackError
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Prints a message stating that the storage facility has already
   #         been loaded yet and pauses until enter is pressed before
   #         returning to the previous submenu.
   *###################################################################*/

  private static void storagePresentError()
  {
    TerminalUtility.clearTerminal();
    System.out.println(util.YELANSI +
    "+|The storage facility has already been loaded.|+\n" + util.SETANSI +
    "Proceed with operation, or restart to load a different facility\n\n" +
    "Press Enter to return to menu.");
    util.waitForEnter();
  }

}
