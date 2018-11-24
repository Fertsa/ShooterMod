package shootermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import shootermod.ShooterMod;

public class BarrierFormPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = "TheShooter:BarrierFormPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ShooterMod.makePath(ShooterMod.RARE_POWER);

    public BarrierFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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

    @Override
    public void atStartOfTurn() { // At the start of your turn
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
                new BufferPower(this.owner,1 ), 1)); // Play the card on the target.
        if (this.amount > 1) {
            int stacks = this.amount - 1;
            int apply = stacks * 2;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
                    new PlatedArmorPower(this.owner, apply), apply)); // Play the card on the target.
        }
    }
    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else if (this.amount > 1) {
            int stacks = this.amount - 1;
            int apply = stacks * 2;
            this.description = DESCRIPTIONS[0] + apply + DESCRIPTIONS[2];
        }

    }

}