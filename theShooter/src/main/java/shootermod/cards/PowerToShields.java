package shootermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomParticle;
import shootermod.ShooterMod;
import shootermod.actions.PowerShieldsAction;
import shootermod.actions.PowerWeaponsAction;
import shootermod.patches.AbstractCardEnum;

public class PowerToShields extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Weirdness Apply X (+1) keywords to yourself.
     */

    // TEXT DECLARATION

    public static final String ID = ("theShooter:PowerToShields");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = ShooterMod.makePath(ShooterMod.PTSHIELDS);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.SHOOTER_GRAY;

    private static final int COST = -1;
    private static final int MAGIC = 1;

    // /STAT DECLARATION/

    public PowerToShields() {

        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;

    }
    
    // Actions the card should do.
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        AbstractDungeon.actionManager.addToTop(new VFXAction(new IntenseZoomParticle(p.dialogX, p.dialogY,false), 0f));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new IntenseZoomEffect(p.dialogX, p.dialogY,false), 0f));
        AbstractDungeon.actionManager.addToBottom(new PowerShieldsAction(p, m, this.magicNumber,
                this.upgraded, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));

    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new PowerToShields();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}