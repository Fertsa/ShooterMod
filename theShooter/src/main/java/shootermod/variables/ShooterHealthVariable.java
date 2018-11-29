package shootermod.variables;

import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShooterHealthVariable extends DynamicVariable
{   // Custom Dynamic Variables are what you do if you need your card text to display a cool, changing number that the base game doesn't provide.
    // If the !D! and !B! and etc. are not enough for you, this is how you make your own one. It Changes In Real Time! 
  
    
    // This is what you type in your card string to make the variable show up. Remember to encase it in "!"'s in the json!
    @Override
    public String key()
    {
        return "HT";
    }

    // Checks whether the current value is different than the base one. 
    // For example, this will check whether your damage is modified (i.e. by strength) and color the variable appropriately (Green/Red).

    // The value the variable should display.
    // In our case, it displays the damage the card would do, multiplied by the amount of energy the player currently has.
    @Override
    public int value(AbstractCard card)
    {
        int max = AbstractDungeon.player.maxHealth;
        double check = 9.0/10;
        float f = (float)check;
        int floor = MathUtils.floor((max * (f)));
        return floor;
    }
    @Override
    public boolean isModified(AbstractCard card)
    {
        return card.isMagicNumberModified;
    }
    @Override
    public int baseValue(AbstractCard card)
    {
        int max = AbstractDungeon.player.maxHealth;
        double check = 9.0/10;
        float f = (float)check;
        int floor = MathUtils.floor((max * (f)));
        return floor;
    }
    @Override
    public boolean upgraded(AbstractCard card)
    {
        return card.upgradedMagicNumber;
    }
    
    // The baseValue the variable should display.
    // just like baseBlock or baseDamage, this is what the variable should reset to by shooter. (the base value before any modifications)

}