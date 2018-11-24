package shootermod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AfterImagePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shootermod.ShooterMod;

import static shootermod.ShooterMod.masterful;


public class DarkHull extends CustomRelic {

    private boolean isActive = false;

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = ("theShooter:DarkHull");
    public static final String IMG = ShooterMod.makePath(ShooterMod.DARKHULL);
    public static final String OUTLINE = ShooterMod.makePath(ShooterMod.DARKHULL_OUTLINE);

    public DarkHull() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.player.damagedThisCombat = 0;
        beginLongPulse();
    }

    public void onLoseHp(int damageAmount) {
        if (damageAmount > 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            flash();
            AbstractDungeon.player.damagedThisCombat = 0;
            beginLongPulse();
        }

    }
    public void onCardDraw(final AbstractCard drawnCard) {
        if (drawnCard == null) {
            return;
        }
        AbstractDungeon.player.damagedThisCombat = 0;
    }

    public void onUseCard(final AbstractCard card, final UseCardAction action) {
            AbstractDungeon.player.damagedThisCombat = 0;

    }


    // Flash at the start of Battle.


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
        return new DarkHull();
    }
}
