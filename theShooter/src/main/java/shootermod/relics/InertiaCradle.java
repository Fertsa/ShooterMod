package shootermod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shootermod.ShooterMod;

public class InertiaCradle extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = ("theShooter:InertiaCradle");
    public static final String IMG = ShooterMod.makePath(ShooterMod.CRADLE);
    public static final String OUTLINE = ShooterMod.makePath(ShooterMod.CRADLE_OUTLINE);

    public InertiaCradle() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.CLINK);
    }




    // Gain 1 energy on eqip.
   // @Override
   // public void onEquip() {
   //     AbstractDungeon.player.energy.energyMaster += 1;
  //  }

    // Lose 1 energy on unequip.
  //  @Override
    //public void onUnequip() {
    //    AbstractDungeon.player.energy.energyMaster -= 1;
    //}

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new InertiaCradle();
    }
}
