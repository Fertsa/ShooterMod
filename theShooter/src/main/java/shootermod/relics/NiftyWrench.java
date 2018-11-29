package shootermod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shootermod.ShooterMod;
import shootermod.powers.EvasionPower;

public class NiftyWrench extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, gain 1 strenght (i.e. Varja)
     */

    // ID, images, text.
    public static final String ID = ("theShooter:NiftyWrench");
    public static final String IMG = ShooterMod.makePath(ShooterMod.WRENCH);
    public static final String OUTLINE = ShooterMod.makePath(ShooterMod.WRENCH_OUTLINE);

    public NiftyWrench() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK);
    }


    // Gain 1 energy on eqip.
    @Override
    public void onCardDraw(final AbstractCard drawnCard) {

        if (drawnCard.type == AbstractCard.CardType.POWER && drawnCard.cost > -1 && drawnCard.cost < 2) {
            drawnCard.freeToPlayOnce = true;
            if (drawnCard.canUse(AbstractDungeon.player, AbstractDungeon.getRandomMonster())) {
                this.flash();
                AbstractDungeon.player.hand.group.remove(drawnCard);
                drawnCard.applyPowers();
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new QueueCardAction(drawnCard, AbstractDungeon.getCurrRoom().monsters.monsters.get(0)));
            }
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
        return new NiftyWrench();
    }
}
