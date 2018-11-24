package shootermod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AfterImagePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import shootermod.ShooterMod;
import shootermod.powers.EvasionPower;

public class StrongThruster extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * 
     * At the start of each combat, gain 1 strenght (i.e. Varja)
     */
    
    // ID, images, text.
    public static final String ID = ("theShooter:StrongThruster");
    public static final String IMG = ShooterMod.makePath(ShooterMod.PLACEHOLDER_RELIC_2);
    public static final String OUTLINE = ShooterMod.makePath(ShooterMod.PLACEHOLDER_RELIC_OUTLINE_2);

    public StrongThruster() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
    }


    // Gain 1 energy on eqip.
    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EvasionPower(AbstractDungeon.player, AbstractDungeon.player, 40), 30));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new StrongThruster();
    }
}
