package shootermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import shootermod.ShooterMod;

//Gain 1 dex for the turn for each card played.

public class CURSESPower extends AbstractPower implements OnCardDrawPower {
    public AbstractCreature source;

    public static final String POWER_ID = ("theShooter:CURSESPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ShooterMod.makePath(ShooterMod.CURSES2);

    public CURSESPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
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
        int a = this.amount*3;
        this.description = DESCRIPTIONS[0] + a + DESCRIPTIONS[1];
    }

    @java.lang.Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (drawnCard.type == AbstractCard.CardType.CURSE) {
            int d = this.amount * 3;
            AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(d, true) , DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToTop(new VFXAction(this.owner, new CleaveEffect(), 0));
            AbstractDungeon.actionManager.addToTop(new SFXAction("ATTACK_HEAVY"));
        }
        if (drawnCard.type == AbstractCard.CardType.POWER) {
            AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(drawnCard));
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
        }

    }
}
