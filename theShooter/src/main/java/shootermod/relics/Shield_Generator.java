package shootermod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shootermod.ShooterMod;

public class Shield_Generator extends CustomRelic {
    
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * 
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = ("theShooter:Shield_Generator");
    public static final String IMG = ShooterMod.makePath(ShooterMod.SHIELD_GENERATOR);
    public static final String OUTLINE = ShooterMod.makePath(ShooterMod.SHIELD_GENERATOR_OUTLINE);

    public Shield_Generator() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
        beginLongPulse();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IntangiblePlayerPower(AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    public void onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            stopPulse();
        }
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
        return new Shield_Generator();
    }
}
