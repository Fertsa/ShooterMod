package shootermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.GreedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import shootermod.ShooterMod;
import shootermod.patches.AbstractCardEnum;
import shootermod.relics.Broken_Weapons;
import shootermod.relics.FailingShields;

import static com.megacrit.cardcrawl.helpers.controller.CInputActionSet.settings;

public class TrickShot extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = ("theShooter:TrickShot");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = ShooterMod.makePath(ShooterMod.TRICKSHOT);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.SHOOTER_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 35;
    private static final int UPGRADE_PLUS_DMG = 10;
    private static final int MAGIC = 50;

    // /STAT DECLARATION/


    public TrickShot() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        float half = (m.hb.cX + p.hb.cX)/2;
        float stop1 = (p.hb.cX + half)/2;
        float stop2 = (m.hb.cX + half)/2;
        float stop4 = (m.hb.cX /2);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(p.dialogX, p.dialogY, stop1, Settings.HEIGHT), 0f));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(stop1, Settings.HEIGHT, stop2, 0), 0f));
        if (p.flipHorizontal) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(stop2, 0, 0, m.hb.cY), 0f));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(0, m.hb.cY, m.hb.cX - Settings.WIDTH / 15,Settings.HEIGHT), 0f));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(m.hb.cX - Settings.WIDTH / 15, Settings.HEIGHT, m.hb.cX, m.hb.cY), 0f));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(stop2, 0, Settings.WIDTH, m.hb.cY), 0f));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(Settings.WIDTH, m.hb.cY, m.hb.cX + Settings.WIDTH / 15, Settings.HEIGHT), 0f));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(m.hb.cX + Settings.WIDTH / 15, Settings.HEIGHT, m.hb.cX, m.hb.cY), 0f));

        }
        AbstractDungeon.player.gainGold(this.magicNumber);
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2,
                RelicLibrary.getRelic(Broken_Weapons.ID).makeCopy());
        AbstractDungeon.actionManager
                .addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AttackEffect.BLUNT_LIGHT));

    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new TrickShot();
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