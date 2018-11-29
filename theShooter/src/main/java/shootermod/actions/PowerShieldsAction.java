package shootermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shootermod.powers.GainDexterityPower;

public class PowerShieldsAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int magicNumber;
    private AbstractPlayer p;
    private int energyOnUse;
    private boolean upgraded;

    public PowerShieldsAction(final AbstractPlayer p, final AbstractMonster m,
                              final int magicNumber, final boolean upgraded,
                              final DamageInfo.DamageType damageTypeForTurn, final boolean freeToPlayOnce,
                              final int energyOnUse)

    {
        this.freeToPlayOnce = false;
        this.energyOnUse = -1;
        this.p = p;
        this.magicNumber = magicNumber;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (this.upgraded) {
            ++effect;
        }

        if (effect > 0) {
            for (int i = 0; i < effect; ++i) {

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p,
                        new GainDexterityPower(this.p,this.p, this.magicNumber), this.magicNumber,
                        AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p,
                        new GainDexterityPower(this.p,this.p, this.magicNumber), this.magicNumber,
                        AttackEffect.NONE));

            }
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}
