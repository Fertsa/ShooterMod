package shootermod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shootermod.ShooterMod;

public class Broken_Weapons extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, gain 1 strenght (i.e. Varja)
     */

    // ID, images, text.
    public static final String ID = ("theShooter:Broken_Weapons");
    public static final String IMG = ShooterMod.makePath(ShooterMod.BROKEN_WEAPONS);
    public static final String OUTLINE = ShooterMod.makePath(ShooterMod.BROKEN_WEAPONS_OUTLINE);
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;

    public Broken_Weapons(int c) {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.MAGICAL);
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
            Broken_Weapons brokenweapons = (Broken_Weapons) AbstractDungeon.player.getRelic(this.ID);
            this.increment();
            brokenweapons.flash();
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
            Broken_Weapons brokenweapons = (Broken_Weapons) AbstractDungeon.player.getRelic(this.ID);
            brokenweapons.increment();
            brokenweapons.flash();

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
        if (AbstractDungeon.player.hasRelic(Broken_Weapons.ID)) {
            Broken_Weapons brokenweapons = (Broken_Weapons) AbstractDungeon.player.getRelic(Broken_Weapons.ID);
            brokenweapons.increment();
            description = getUpdatedDescription();
            brokenweapons.flash();
        } else {
            super.obtain();
            description = getUpdatedDescription();
        }
    }
    // Gain 1 energy on eqip.
    @Override
    public void atPreBattle() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, this.counter,false), this.counter));
        if (counter >= 2) {AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -2)));
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
        return new Broken_Weapons(counter);
    }
}
