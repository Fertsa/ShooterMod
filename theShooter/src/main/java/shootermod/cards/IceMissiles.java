package shootermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FireBreathingPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import shootermod.ShooterMod;
import shootermod.patches.AbstractCardEnum;
import shootermod.powers.CoolDownPower;

public class IceMissiles extends CustomCard {


    public static final String ID = ("theShooter:IceMissiles");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = ShooterMod.makePath(ShooterMod.ICE);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.SHOOTER_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;
    private static final int UPGRADE_DAMAGE = 1;


    public IceMissiles() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.isMultiDamage = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new VFXAction(new BlizzardEffect(this.damage, p.flipHorizontal), 0f));
        AbstractDungeon.actionManager
                .addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn,
                        AttackEffect.BLUNT_LIGHT, true));
        if (ShooterMod.masterful()) {
            int a = this.magicNumber;
            while (a > 0) {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new BlizzardEffect(this.damage, p.flipHorizontal), 0f));
                AbstractDungeon.actionManager
                        .addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn,
                                AttackEffect.BLUNT_LIGHT, true));
                a--;
            }
        }
    }



    @Override
    public AbstractCard makeCopy() {
        return new IceMissiles();
    }
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.initializeDescription();
        }
    }
}