package shootermod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AfterImagePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import shootermod.ShooterMod;

public class AnotherQuarter extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, gain 1 strenght (i.e. Varja)
     */


    // ID, images, text.
    public static final String ID = ("theShooter:AnotherQuarter");
    public static final String IMG = ShooterMod.makePath(ShooterMod.EXTRA_QUARTER);
    public static final String OUTLINE = ShooterMod.makePath(ShooterMod.EXTRA_QUARTER_OUTLINE);

    public AnotherQuarter() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
    }


    // Gain 1 energy on eqip.
    public void instantObtain() {
        if (ShooterMod.wealthy()) {
            beginLongPulse(); }
        else {
            stopPulse();
        }
    }
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (ShooterMod.wealthy()) {
            beginLongPulse();
        }
    }
    public void onTrigger() {
        if (ShooterMod.wealthy()) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            int healAmt = AbstractDungeon.player.maxHealth;
            if (AbstractDungeon.player.hasBlight("FullBelly")) {
                healAmt /= 2;
            }

            if (healAmt < 1) {
                healAmt = 1;
            }

            AbstractDungeon.player.heal(healAmt, true);
            AbstractDungeon.player.loseGold(200);
        }
        else {
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
            AbstractDungeon.player.currentHealth = 0;
            if (AbstractDungeon.player.currentBlock > 0) {
                AbstractDungeon.player.loseBlock();
            }
        }
    }

    public void onGainGold() {
        if (ShooterMod.wealthy()) {
            beginLongPulse();
        }
        else {

            stopPulse();
        }
    }
    public void onLoseGold() {
        if (ShooterMod.wealthy()) {
            beginLongPulse();
        }
        else {
            stopPulse();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new AnotherQuarter();
    }
}
