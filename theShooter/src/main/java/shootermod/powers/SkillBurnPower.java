package shootermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import shootermod.ShooterMod;

//Gain 1 dex for the turn for each card played.

public class SkillBurnPower extends AbstractPower implements OnCardDrawPower {
    public AbstractCreature source;

    public static final String POWER_ID = ("theShooter:SkillBurnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ShooterMod.makePath(ShooterMod.BURN);

    public SkillBurnPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;


    }

    // On use card, apply (amount) of dexterity. (Go to the actual power card for the ammount.)


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            this.description = DESCRIPTIONS[0];
    }

    @java.lang.Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (drawnCard.type == AbstractCard.CardType.SKILL) {
            drawnCard.freeToPlayOnce = true;
            if (drawnCard.canUse(AbstractDungeon.player, AbstractDungeon.getRandomMonster())) {
                AbstractDungeon.player.hand.group.remove(drawnCard);
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));

                drawnCard.exhaustOnUseOnce = true;
                drawnCard.applyPowers();
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(drawnCard, AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true)));


            }
            else {
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
                drawnCard.applyPowers();
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(drawnCard,AbstractDungeon.player.hand));


            }
        }

    }
}
