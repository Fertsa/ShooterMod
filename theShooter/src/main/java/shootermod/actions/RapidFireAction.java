//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package shootermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;


public class RapidFireAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private int numTimes;

    public RapidFireAction(AbstractCreature target, DamageInfo info, int numTimes) {
        this.info = info;
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.BLUNT_LIGHT;
        this.duration = 0.01F;
        this.numTimes = numTimes;
    }


    public void update() {
        if (this.target == null) {
            this.isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if (this.target.currentHealth > 0) {
                this.target.damageFlash = true;
                this.target.damageFlashFrames = 1;
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                this.info.applyPowers(this.info.owner, this.target);
                this.target.damage(this.info);
                if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    --this.numTimes;
                    AbstractDungeon.actionManager.addToTop(new RapidFireAction(AbstractDungeon.getMonsters().getRandomMonster(true), this.info, this.numTimes));

                }
                int effect = (int )(Math.random() * 7 + 1);
                float half = (this.target.hb.cX + AbstractDungeon.player.hb.cX)/2;
                float stop1 = (AbstractDungeon.player.hb.cX + half)/2;
                float stop2 = (this.target.hb.cX + half)/2;
                if (effect == 1){
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY,this.target.hb.cX, this.target.hb.cY), 0f));
                }
                if (effect ==2){
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.target.hb.cX, this.target.hb.cY + (Settings.HEIGHT /15)), 0f));
                }
                if (effect == 3) {
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.target.hb.cX, this.target.hb.cY - (Settings.HEIGHT /15)), 0f));
                }
                if (effect == 4) {
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop2, Settings.HEIGHT, this.target.hb.cX, this.target.hb.cY), 0f));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop1, 0, stop2, Settings.HEIGHT), 0f));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, stop1, 0), 0f));
                }
                if (effect == 5) {
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop2, 0, this.target.hb.cX, this.target.hb.cY), 0f));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(stop1, Settings.HEIGHT, stop2, 0), 0f));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, stop1, Settings.HEIGHT), 0f));
                }
                if (effect == 6) {
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(half, Settings.HEIGHT, this.target.hb.cX, this.target.hb.cY), 0f));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, half, Settings.HEIGHT), 0f));
                }
                if (effect == 7) {
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(half, 0, this.target.hb.cX, this.target.hb.cY), 0f));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, half, 0), 0f));
                }
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.01F));
            }

            this.isDone = true;
        }
    }
}
