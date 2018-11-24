package shootermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.ClashAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import shootermod.ShooterMod;
import shootermod.patches.AbstractCardEnum;
import java.util.Iterator;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;


public class WarpDrive extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = "theShooter:WarpDrive";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = ShooterMod.makePath(ShooterMod.WARP);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String NOUSE = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.SHOOTER_GRAY;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;


    // /STAT DECLARATION/


    public WarpDrive() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        GraveField.grave.set(this, true);
        this.isEthereal = true;
        this.cantUseMessage = NOUSE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (canUse(p,m)) {
            AbstractDungeon.actionManager.addToNextCombat(new LoseEnergyAction(999));
            AbstractCreature target = AbstractDungeon.player;
            AbstractDungeon.getCurrRoom().smoked = true;
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new WhirlwindEffect()));
            AbstractDungeon.player.hideHealthBar();
            AbstractDungeon.player.isEscaping = true;
            AbstractDungeon.player.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
            AbstractDungeon.overlayMenu.endTurnButton.disable();
            AbstractDungeon.player.escapeTimer = 2.5F;
        }
        else {
            AbstractDungeon.actionManager.addToTop(new TalkAction(p,this.cantUseMessage));
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean fightingBoss = false;
        Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while(var2.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var2.next();
            if (mo.hasPower("BackAttack")) {
                return false;
            }
            if (mo.type == AbstractMonster.EnemyType.BOSS) {
                fightingBoss = true;
                break;
            }
        }
        if (!fightingBoss) {
            return true;
        }
        return false;
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new WarpDrive();
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