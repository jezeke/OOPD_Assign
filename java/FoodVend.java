/*********************************************************************
 * CLASS FoodVend
 * AUTHOR Jhi Morris
 * DATE CREATED 02/05/18
 * DATE LAST EDITED 24/05/18
 * PURPOSE Main class for the FoodVend program.
 *********************************************************************/

public class FoodVend
{
  public static void main(String[] args)
  {
    MainMenu main = new MainMenu();

    try
    {
      main.menuStart();
    }
    catch (Exception e) //avoids printing ugly stacktrace to user.
    {  //should never be reached. if it is, i am a bad programmer :(
      TerminalUtility.clearTerminal();
      System.out.println("Exiting with unexpected error: " + e.getMessage());
    }
  }
}
