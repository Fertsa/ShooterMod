package shootermod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shootermod.ShooterMod;

public class BackDoor extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = ("theShooter:BackDoor");
    public static final String IMG = ShooterMod.makePath(ShooterMod.BACKDOOR);
    public static final String OUTLINE = ShooterMod.makePath(ShooterMod.BACKDOOR_OUTLINE);

    public BackDoor() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
    }


    public void instantObtain() {
        if (ShooterMod.healthy()) {
            beginLongPulse(); }
        else {
            stopPulse();
        }
    }
    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        if (ShooterMod.healthy()) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(AbstractDungeon.player));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (ShooterMod.healthy()) {
            beginLongPulse();
        }
    }
    public void onLoseHp(int damageAmount) {
        if (ShooterMod.healthy()) {
            beginLongPulse();
        }
        else {

            stopPulse();
        }
    }
    public int onPlayerHeal(int healAmount) {
        if (ShooterMod.healthy()) {
            beginLongPulse();
        }
        else {

            stopPulse();
        }
        return healAmount;
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
        return new BackDoor();
    }
}
