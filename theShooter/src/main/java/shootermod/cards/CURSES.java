package shootermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shootermod.ShooterMod;
import shootermod.patches.AbstractCardEnum;
import shootermod.powers.*;

public class CURSES extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Hold Place Gain 1(2) Keywords(s).
     */


    // TEXT DECLARATION

    public static final String ID = ("theShooter:CURSES");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = ShooterMod.makePath(ShooterMod.CURSEES);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.SHOOTER_GRAY;

    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int UPGRADE_COST = 0;

    // /STAT DECLARATION/


    public CURSES() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int e = this.magicNumber;
        while (e > 0) {
            -- e;
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(AbstractDungeon.returnRandomCurse(), 1, true, true));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new CURSESPower(p, p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new SkillBurnPower(p, p, 1), 0));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new StatusBurnPower(p, p, 1), 0));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new AttackBurnPower(p, p, 1), 0));
    }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CURSES();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            this.initializeDescription();
        }
    }
}