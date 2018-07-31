import java.util.Calendar;

public interface IFood
{
  public boolean calcExpiry(Calendar today);
    //checks todays date and exports true if this food
    //item has reached its expiry date

  public int calcSpace(Food food);
    //checks attributes of Food class object and
    //exports an integer specifying the volume
    //in litres of storage required.
}
