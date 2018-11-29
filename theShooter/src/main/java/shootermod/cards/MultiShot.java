package shootermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FireBreathingPower;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import shootermod.ShooterMod;
import shootermod.patches.AbstractCardEnum;
import shootermod.powers.CoolDownPower;

public class MultiShot extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = ("theShooter:MultiShot");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = ShooterMod.makePath(ShooterMod.MULTISHOT);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.SHOOTER_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 1;

    // /STAT DECLARATION/


    public MultiShot() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        float half = (m.hb.cX + p.hb.cX)/2;
        float stop1 = (p.hb.cX + half)/2;
        float stop2 = (m.hb.cX + half)/2;
        AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop2, 0, m.hb.cX, m.hb.cY), 0f));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop1, Settings.HEIGHT, stop2, 0), 0f));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, stop1, Settings.HEIGHT), 0f));
        AbstractDungeon.actionManager.addToTop(new WaitAction(0));
        AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop2, Settings.HEIGHT, m.hb.cX, m.hb.cY), 0f));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop1, 0, stop2, Settings.HEIGHT), 0f));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, stop1, 0), 0f));
        AbstractDungeon.actionManager.addToTop(new WaitAction(0));

        if (ShooterMod.healthy()) {
                AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop2, 0, m.hb.cX, m.hb.cY), 0f));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop1, Settings.HEIGHT, stop2, 0), 0f));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, stop1, Settings.HEIGHT), 0f));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0));
                AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop2, Settings.HEIGHT, m.hb.cX, m.hb.cY), 0f));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop1, 0, stop2, Settings.HEIGHT), 0f));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, stop1, 0), 0f));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0));
        }
        if (ShooterMod.wealthy()) {
            AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop2, 0, m.hb.cX, m.hb.cY), 0f));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop1, Settings.HEIGHT, stop2, 0), 0f));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, stop1, Settings.HEIGHT), 0f));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0));
            AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop2, Settings.HEIGHT, m.hb.cX, m.hb.cY), 0f));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop1, 0, stop2, Settings.HEIGHT), 0f));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, stop1, 0), 0f));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0));
        }
        if (ShooterMod.masterful()) {
            AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop2, 0, m.hb.cX, m.hb.cY), 0f));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop1, Settings.HEIGHT, stop2, 0), 0f));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, stop1, Settings.HEIGHT), 0f));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0));
            AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop2, Settings.HEIGHT, m.hb.cX, m.hb.cY), 0f));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop1, 0, stop2, Settings.HEIGHT), 0f));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, stop1, 0), 0f));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0));
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new MultiShot();
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