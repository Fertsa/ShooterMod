package shootermod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.AfterImagePower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shootermod.ShooterMod;

import com.megacrit.cardcrawl.helpers.PowerTip;

import static shootermod.ShooterMod.HULL_BREACH;
import static shootermod.ShooterMod.HULL_BREACH_OUTLINE;

public class HullBreach extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, gain 1 strenght (i.e. Varja)
     */

    // ID, images, text.
    public static final String ID = ("theShooter:HullBreach");
    public static final String IMG = ShooterMod.makePath(ShooterMod.HULL_BREACH);
    public static final String OUTLINE = ShooterMod.makePath(ShooterMod.HULL_BREACH_OUTLINE);
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;

    public HullBreach(int c) {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.FLAT);
        this.setCounter(c);
        this.counter = 1;
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void increment()
    {
        if (counter <= 0) {
            counter = 1;
        }
        setCounter(counter + 1);
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public void instantObtain()
    {
        if (AbstractDungeon.player.hasRelic(this.ID)) {
            HullBreach hullbreach = (HullBreach) AbstractDungeon.player.getRelic(this.ID);
            this.increment();
            hullbreach.flash();
        } else {
            super.instantObtain();
            this.counter = 1;
            description = getUpdatedDescription();
            tips.clear();
            tips.add(new PowerTip(name, description));
            initializeTips();
        }
    }

    @Override
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip)
    {
        if (AbstractDungeon.player.hasRelic(this.ID)) {
            HullBreach hullbreach = (HullBreach) AbstractDungeon.player.getRelic(this.ID);
            hullbreach.increment();
            hullbreach.flash();

            isDone = true;
            isObtained = true;
            discarded = true;
        } else {
            super.instantObtain(p, slot, callOnEquip);
        }
    }

    @Override
    public void obtain()
    {
        if (AbstractDungeon.player.hasRelic(HullBreach.ID)) {
            HullBreach hullbreach = (HullBreach) AbstractDungeon.player.getRelic(HullBreach.ID);
            hullbreach.increment();
            description = getUpdatedDescription();
            hullbreach.flash();
        } else {
            super.obtain();
            description = getUpdatedDescription();
        }
    }
    // Gain 1 energy on eqip.
    @Override
    public void atPreBattle() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, this.counter,false), this.counter));
        if (counter >= 2) {AbstractDungeon.actionManager.addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 2));
        }
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }


    // Description
    public String getUpdatedDescription() {
        if (this.counter < 2) {
            return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
        } else  {
            return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[2];
        }
    }


    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new HullBreach(counter);
    }
}
