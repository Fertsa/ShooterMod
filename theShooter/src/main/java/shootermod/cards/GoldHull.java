package shootermod.cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import shootermod.ShooterMod;
import shootermod.patches.AbstractCardEnum;

public class GoldHull extends CustomCard implements StartupCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = "theShooter:GoldHull";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = ShooterMod.makePath(ShooterMod.GHULL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.SHOOTER_GRAY;

    private static final int COST = -2;
    private static final int MAGIC = 20;
    private static final int UPGRADE_MAGIC = 10;


    // /STAT DECLARATION/


    public GoldHull() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void triggerWhenDrawn() {
        int rv = this.magicNumber / 3;
        AbstractDungeon.player.gainGold(rv);
    }
    @Override
    public boolean atBattleStartPreDraw()
    {
        if (ShooterMod.wealthy()) {
            AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, this.magicNumber));
            AbstractDungeon.player.loseGold(this.magicNumber);
        }
        else {
            AbstractDungeon.player.drawPile.group.remove(this);
        }
        return true;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }



    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GoldHull();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            this.initializeDescription();
        }
    }
}