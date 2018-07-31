/*********************************************************************
 * CLASS TerminalUtility
 * AUTHOR Jhi Morris
 * DATE CREATED 06/05/18
 * DATE LAST EDITED 29/05/18
 * PURPOSE Handles user input from terminal and terminal output utilities.
 *         This class is generic and was built to be reused easily
 *         in future programs.
 *********************************************************************/

import java.util.*;
import java.io.*;

public class TerminalUtility
{
  public final String YELANSI = "\u001b[33m"; //set text color yellow
  public final String SETANSI = "\u001b[0m"; //resets coloring
  public final String REDANSI = "\u001b[31m"; //set text color red
  private Scanner sc = new Scanner(System.in);

  /*####################################################################
   # METHOD menuSelect
   # IMPORTS description (String), error (String), max (Integer)
   # EXPORTS choice (Integer)
   # PURPOSE Renders the description, takes integer as user input input
   #         and exports selection if valid; as defined as a non-negative
   #         integer less-than or equal to max.
   *###################################################################*/

  public int menuSelect(String desc, String error, int max)
  {
    int choice;
    boolean valid = true;

    if (error == null)
    {
      error = "";
    }

    do
    {
      clearTerminal();
      System.out.println(desc + "\n\n" + REDANSI + error + SETANSI);

      if (!valid)
      {
        System.out.print("\033[1A\033[2K" + YELANSI +
        "<Invalid input. Please enter 0 - " + max + ".> \n\033[2K" + SETANSI);
      } //up line, clearline, message, newline, clearline

      choice = inputInt();

      if (choice <= max && choice >= 0)
      {
        valid = true;
      }
      else
      {
        valid = false;
      }

    }
    while (!valid);

    return choice;
  }

  /*####################################################################
   # METHOD clearTerminal
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Clears the terminal using ANSI escape codes.
   *###################################################################*/

  public static void clearTerminal()
  {
    System.out.print("\033[2J\033[H");
    System.out.flush();
  }

  /*####################################################################
   # METHOD inputInt
   # IMPORTS none
   # EXPORTS input (Integer)
   # PURPOSE Takes input from user, prints relevant error to screen on
   #         non-integer input. Loops until valid integer is input.
   *###################################################################*/

  public int inputInt()
  {
    int input = 0;
    boolean valid;

    do
    {
      try
      {
        input = sc.nextInt();
        sc.nextLine(); //eat EOL token
        valid = true;
      }
      catch(InputMismatchException e)
      {
        String flush = sc.next();
        System.out.print("\033[2A\033[2K" + YELANSI + //move up 2 lines, clear
        "<Invalid input. Please input integer.> \n\033[2K" + SETANSI); //cont
        valid = false; //^message, newline, clear line
      }
    }
    while (!valid);

    return input;
  }

  /*####################################################################
   # METHOD inputDouble
   # IMPORTS none
   # EXPORTS input (Double)
   # PURPOSE Takes input from user, prints relevant error to screen on
   #         non-double input. Loops until valid double is input.
   *###################################################################*/
  public double inputDouble()
  {
    double input = 0.0;
    boolean valid = true;

    do
    {
      try
      {
        input = sc.nextDouble();
        sc.nextLine(); //eat EOL
        valid = true;
      }
      catch(InputMismatchException e)
      {
        String flush = sc.next();
        System.out.print("\033[2A\r" + //mov cursor up 2, return to line start
        "<Invalid input. Please input double.>  \n\033[2K");
        valid = false; //^message, newline, clear line
      }
    }
    while (!valid);

    return input;
  }

  /*####################################################################
   # METHOD inputString
   # IMPORTS output (String)
   # EXPORTS input (Integer)
   # PURPOSE Prints output to screen and takes string input from user.
   *###################################################################*/

  public String inputString(String output)
  {
    System.out.println(output);

    String input = sc.nextLine();
    return input;
  }

  /*####################################################################
   # METHOD waitForEnter
   # IMPORTS none
   # EXPORTS none
   # PURPOSE Acts to pause the program until the user presses enter.
   *###################################################################*/

  public void waitForEnter()
  {
    String flush = sc.nextLine();
  }

  /*####################################################################
   # METHOD inRange
   # IMPORTS value (Integer), bound1 (Integer), bound2 (Integer)
   # EXPORTS out (Boolean)
   # PURPOSE Checks if value is between bounds.
   # ASSERTION Returns true if value is between bound1 and bound2 (inclusive)
   #           regardless of order bound1 and bound2 arguments are passed.
   *###################################################################*/

  public static boolean inRange(int value, int bound1, int bound2)
  {
    boolean out = false;

    if (bound1 >= bound2)
    {
      if (value >= bound2 && value <= bound1)
      {
        out = true;
      }
    }
    else
    {
      if (value >= bound1 && value <= bound2)
      {
        out = true;
      }
    }

    return out;
  }

  /*####################################################################
   # METHOD inRange
   # IMPORTS value (Double), bound1 (Double), bound2 (Double)
   # EXPORTS out (Boolean)
   # PURPOSE Checks if value is between bounds.
   # ASSERTION Returns true if value is between bound1 and bound2 (inclusive)
   #           regardless of order bound1 and bound2 arguments are passed.
   *###################################################################*/

  public static boolean inRange(double value, double bound1, double bound2)
  {
    boolean out = false;

    if (bound1 >= bound2)
    {
      if (value >= bound2 && value <= bound1)
      {
        out = true;
      }
    }
    else
    {
      if (value >= bound1 && value <= bound2)
      {
        out = true;
      }
    }

    return out;
  }
}
