package shootermod.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import shootermod.powers.EvasionPower;

@SpirePatch(clz= AbstractPlayer.class, method="damage")
public class EvasionPatch {
    private static final String[] TEXT = CardCrawlGame.languagePack.getPowerStrings("theShooter:EvasionPower").DESCRIPTIONS;



    public static SpireReturn Prefix(AbstractPlayer self, DamageInfo info) {
        int e = AbstractDungeon.player.hasPower("theShooter:EvasionPower")?AbstractDungeon.player.getPower("theShooter:EvasionPower").amount:0;
        if (info.type == DamageInfo.DamageType.NORMAL && info.output > 0 && self.hasPower("theShooter:EvasionPower") && AbstractDungeon.cardRandomRng.random(99) < e) {
            self.getPower("theShooter:EvasionPower").flash();
            int damageAmount = 0;
            if (info.owner == self) {
                for (AbstractRelic r : self.relics) {
                    r.onAttack(info, damageAmount, self);
                }
            }
            if (info.owner != null) {
                for (AbstractPower p : info.owner.powers) {
                    p.onAttack(info, damageAmount, self);
                }
                for (AbstractPower p : self.powers) {
                    damageAmount = p.onAttacked(info, damageAmount);
                }
                for (AbstractRelic r : self.relics) {
                    damageAmount = r.onAttacked(info, damageAmount);
                }
            }
            AbstractDungeon.effectList.add(new BlockedWordEffect(self, self.hb.cX, self.hb.cY, TEXT[3]));
            return SpireReturn.Return(null);
        } else {
            return SpireReturn.Continue();
        }
    }
}