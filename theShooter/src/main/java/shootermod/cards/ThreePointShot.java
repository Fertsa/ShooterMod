package shootermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import shootermod.ShooterMod;
import shootermod.characters.TheShooter;
import shootermod.patches.AbstractCardEnum;

public class ThreePointShot extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = ("theShooter:ThreePointShot");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = ShooterMod.makePath(ShooterMod.THREESHOT);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.SHOOTER_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 5;
    private static final int MAGIC = 3;

    // /STAT DECLARATION/


    public ThreePointShot() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (ShooterMod.masterful()) {
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage * 3, this.damageTypeForTurn), AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, m.hb.cX, m.hb.cY), 0f));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, m.hb.cX, m.hb.cY + (Settings.HEIGHT /15)), 0f));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, m.hb.cX, m.hb.cY - (Settings.HEIGHT /15)), 0f));
            AbstractDungeon.player.gainGold(this.magicNumber);
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, m.hb.cX, m.hb.cY), 0f));
            AbstractDungeon.player.gainGold(this.magicNumber);
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ThreePointShot();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.initializeDescription();
        }
    }
}