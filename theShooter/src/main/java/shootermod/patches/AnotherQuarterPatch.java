package shootermod.patches;

        import com.evacipated.cardcrawl.modthespire.lib.*;
        import com.megacrit.cardcrawl.cards.DamageInfo;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.relics.MarkOfTheBloom;
        import com.megacrit.cardcrawl.rooms.AbstractRoom;
        import javassist.CtBehavior;
import shootermod.relics.AnotherQuarter;

@SpirePatch(
        clz=AbstractPlayer.class,
        method="damage",
        paramtypez={DamageInfo.class}
)
public class AnotherQuarterPatch
{
    @SpireInsertPatch(
            locator=Locator.class
    )
    public static SpireReturn Insert(AbstractPlayer __instance, DamageInfo info)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !__instance.hasRelic(MarkOfTheBloom.ID)) {
            if (__instance.hasRelic(AnotherQuarter.ID)) {
                    __instance.currentHealth = 0;
                    __instance.getRelic(AnotherQuarter.ID).onTrigger();
                    return SpireReturn.Return(null);

            }
        }
        return SpireReturn.Continue();
    }
    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "isDead");

            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }

}