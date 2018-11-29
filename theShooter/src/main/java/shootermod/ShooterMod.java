package shootermod;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;

import shootermod.potions.*;
import shootermod.patches.*;
import shootermod.relics.*;
import shootermod.variables.*;
import shootermod.cards.*;
import shootermod.characters.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* 
 * Welcome to this mildly over-commented Slay the Spire modding base. 
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (Character, 
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, 1 relic, etc.
 * If you're new to modding, I highly recommend going though the BaseMod wiki for whatever you wish to add 
 * https://github.com/daviscook477/BaseMod/wiki  and work your way thought your made with this base. 
 * Feel free to use this in any way you like, of course. Happy modding!
 */

@SpireInitializer
public class ShooterMod
        implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
        EditCharactersSubscriber, PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(ShooterMod.class.getName());

    //This is for the in-game mod settings pannel.
    private static final String MODNAME = "Shooter Mod";
    private static final String AUTHOR = "Fertsa, special mention to Gremious";
    private static final String DESCRIPTION = "Adds the Shooter, a spaceship with many video game inspirations.";

    // =============== IMPUT TEXTURE LOCATION =================

    // Colors (RGB)
        // Character Color
        public static final Color SHOOTER_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);

        // Potion Colors in RGB
        public static final Color PLACEHOLDER_POTION_lIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red 
        public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
        public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
        
    // Image folder name
    private static final String SHOOTER_MOD_ASSETS_FOLDER = "shooterModResources/images";

    // Card backgrounds
    private static final String ATTACK_DEAFULT_GRAY = "512/bg_attack_shooter_gray.png";
    private static final String POWER_DEAFULT_GRAY = "512/bg_power_shooter_gray.png";
    private static final String SKILL_DEAFULT_GRAY = "512/bg_skill_shooter_gray.png";
    private static final String ENERGY_ORB_DEAFULT_GRAY = "512/card_shooter_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "512/card_small_orb.png";

    private static final String ATTACK_DEAFULT_GRAY_PORTRAIT = "1024/bg_attack_shooter_gray.png";
    private static final String POWER_DEAFULT_GRAY_PORTRAIT = "1024/bg_power_shooter_gray.png";
    private static final String SKILL_DEAFULT_GRAY_PORTRAIT = "1024/bg_skill_shooter_gray.png";
    private static final String ENERGY_ORB_DEAFULT_GRAY_PORTRAIT = "1024/card_shooter_gray_orb.png";

    // Card images
    public static final String SHOOTER_COMMON_ATTACK = "cards/Attack.png";
    public static final String SHOOTER_COMMON_SKILL = "cards/Skill.png";
    public static final String SHOOTER_COMMON_POWER = "cards/Power.png";
    public static final String SHOOTER_UNCOMMON_ATTACK = "cards/Attack.png";
    public static final String SHOOTER_UNCOMMON_SKILL = "cards/Skill.png";
    public static final String SHOOTER_UNCOMMON_POWER = "cards/Power.png";
    public static final String SHOOTER_RARE_ATTACK = "cards/Attack.png";
    public static final String SHOOTER_RARE_SKILL = "cards/Skill.png";
    public static final String SHOOTER_RARE_POWER = "cards/Power.png";
    public static final String STRIKE = "cards/Strike_G.png";
    public static final String DEFEND = "cards/Defend_G.png";
    public static final String SHOT = "cards/Shot.png";
    public static final String TRICKSHOT = "cards/TrickShot.png";
    public static final String THREESHOT = "cards/ThreeShot.png";
    public static final String MULTISHOT = "cards/MultiShot.png";
    public static final String BIGSHOT = "cards/BigShot.png";
    public static final String EVERYTHING = "cards/FireEverything.png";
    public static final String EVASIVE = "cards/Evasive.png";
    public static final String SRUSH = "cards/SkillRush.png";
    public static final String ARUSH = "cards/AttackRush.png";
    public static final String STRUSH = "cards/StatusRush.png";
    public static final String RFEXSHOT = "cards/Reflex.png";
    public static final String DDEVIL = "cards/DareDevil.png";
    public static final String HHULL = "cards/HeavyHull.png";
    public static final String GHULL = "cards/GoldHull.png";
    public static final String OHULL = "cards/OpticHull.png";
    public static final String VCITY = "cards/Velocity.png";
    public static final String PTWEAPONS = "cards/PTWeapons.png";
    public static final String PTSHIELDS = "cards/PTShields.png";
    public static final String BFORM = "cards/BarrierForm.png";
    public static final String HREPAIRS = "cards/HullRepairs.png";
    public static final String RREPAIRS = "cards/RushedRepairs.png";
    public static final String AMB = "cards/Anti-MatterBeam.png";
    public static final String FSHIELD = "cards/FlashShield.png";
    public static final String NULLIFY = "cards/Nullify.png";
    public static final String GRZED = "cards/Grazed.png";
    public static final String INSIST = "cards/Insist.png";
    public static final String MSKLZ = "cards/MadSkill.png";
    public static final String FTRWR = "cards/FlameThrower.png";
    public static final String ICE = "cards/IceMissiles.png";
    public static final String LSHIELD = "cards/LShield.png";
    public static final String MARMOR = "cards/MArmor.png";
    public static final String PRSHIELD = "cards/PreShield.png";
    public static final String RFHULL = "cards/RForceH.png";
    public static final String WCHARGE = "cards/WeaponCharge.png";
    public static final String SCHARGE = "cards/ShieldCharge.png";
    public static final String AREPAIR = "cards/AutoRepairs.png";
    public static final String SPOWER = "cards/StorePower.png";
    public static final String WARP = "cards/WarpDrive.png";
    public static final String NOTOUCH = "cards/Untouchable.png";
    public static final String WSHOT = "cards/WardingShot.png";
    public static final String CURSEES = "cards/CURSES.png";

    // Power images
    public static final String COMMON_POWER = "powers/velocity_power.png";
    public static final String UNCOMMON_POWER = "powers/velocity_power.png";
    public static final String RARE_POWER = "powers/Barrier_Form.png";
    public static final String Flame_Thrower = "powers/Flame_Thrower.png";
    public static final String SHACKLP = "powers/GainDexPower.png";
    public static final String DARDVL = "powers/DareDevil.png";
    public static final String REFLEX = "powers/Reflex.png";
    public static final String GRAZED = "powers/Graze.png";
    public static final String FLASH = "powers/Flash.png";
    public static final String EVADE = "powers/Evasion.png";
    public static final String BURN = "powers/CardBurn.png";
    public static final String CURSES2 = "powers/CURSES.png";
    // Relic images  
    public static final String SHIELD_GENERATOR = "relics/Shield_Generator.png";
    public static final String SHIELD_GENERATOR_OUTLINE = "relics/outline/Shield_Generator.png";

    public static final String DARKHULL = "relics/DarkHull.png";
    public static final String DARKHULL_OUTLINE = "relics/outline/DarkHull.png";

    public static final String PLACEHOLDER_RELIC_2 = "relics/placeholder_relic2.png";
    public static final String PLACEHOLDER_RELIC_OUTLINE_2 = "relics/outline/placeholder_relic2.png";

    public static final String HULL_BREACH = "relics/Hull_Breach.png";
    public static final String HULL_BREACH_OUTLINE = "relics/outline/Hull_Breach.png";

    public static final String FAILING_SHIELDS = "relics/FailingShields.png";
    public static final String FAILING_SHIELDS_OUTLINE = "relics/outline/FailingShields.png";

    public static final String BROKEN_WEAPONS = "relics/Broken_Weapons.png";
    public static final String BROKEN_WEAPONS_OUTLINE = "relics/outline/Broken_Weapons.png";

    public static final String EXTRA_QUARTER = "relics/Another_Quarter.png";
    public static final String EXTRA_QUARTER_OUTLINE = "relics/outline/Another_Quarter.png";

    public static final String BACKDOOR = "relics/BackDoor.png";
    public static final String BACKDOOR_OUTLINE = "relics/outline/BackDoor.png";

    public static final String DARKSTAR = "relics/DarkStar.png";
    public static final String DARKSTAR_OUTLINE = "relics/outline/DarkStar.png";

    public static final String WRENCH = "relics/NiftyWrench.png";
    public static final String WRENCH_OUTLINE = "relics/outline/NiftyWrench.png";

    public static final String CRADLE = "relics/Inertia.png";
    public static final String CRADLE_OUTLINE = "relics/outline/Inertia.png";
    
    // Character assets
    private static final String THE_SHOOTER_BUTTON = "charSelect/ShooterCharacterButton.png";
    private static final String THE_SHOOTER_PORTRAIT = "charSelect/ShooterCharacterPortraitBG.png";
    public static final String THE_SHOOTER_SHOULDER_1 = "char/shooterCharacter/shoulder.png";
    public static final String THE_SHOOTER_SHOULDER_2 = "char/shooterCharacter/shoulder2.png";
    public static final String THE_SHOOTER_CORPSE = "char/shooterCharacter/corpse.png";

    //Mod Badge
    public static final String BADGE_IMAGE = "Badge.png";

    // Animations atlas and JSON files
    public static final String THE_SHOOTER_SKELETON_ATLAS = "char/shooterCharacter/skeleton.atlas";
    public static final String THE_SHOOTER_SKELETON_JSON = "char/shooterCharacter/skeleton.json";

    // =============== /IMPUT TEXTURE LOCATION/ =================

    /**
     * Makes a full path for a resource path
     * 
     * @param resource the resource, must *NOT* have a leading "/"
     * @return the full path
     */
    public static final String makePath(String resource) {
        return SHOOTER_MOD_ASSETS_FOLDER + "/" + resource;
    }

    // =============== SUBSCRIBE, CREATE THE COLOR, INITIALIZE =================

    public ShooterMod() {
        logger.info("Subscribe to basemod hooks");

        BaseMod.subscribe(this);

        logger.info("Done subscribing");

        logger.info("Creating the color " + AbstractCardEnum.SHOOTER_GRAY.toString());

        BaseMod.addColor(AbstractCardEnum.SHOOTER_GRAY, SHOOTER_GRAY, SHOOTER_GRAY, SHOOTER_GRAY,
                SHOOTER_GRAY, SHOOTER_GRAY, SHOOTER_GRAY, SHOOTER_GRAY, makePath(ATTACK_DEAFULT_GRAY),
                makePath(SKILL_DEAFULT_GRAY), makePath(POWER_DEAFULT_GRAY),
                makePath(ENERGY_ORB_DEAFULT_GRAY), makePath(ATTACK_DEAFULT_GRAY_PORTRAIT),
                makePath(SKILL_DEAFULT_GRAY_PORTRAIT), makePath(POWER_DEAFULT_GRAY_PORTRAIT),
                makePath(ENERGY_ORB_DEAFULT_GRAY_PORTRAIT), makePath(CARD_ENERGY_ORB));

        logger.info("Done Creating the color");
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Shooter Mod. Hi. =========================");
        ShooterMod shootermod = new ShooterMod();
        logger.info("========================= /Shooter Mod Initialized/ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================

    
    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters. " + "Add " + TheShooterEnum.THE_SHOOTER.toString());

        BaseMod.addCharacter(new TheShooter("the Shooter", TheShooterEnum.THE_SHOOTER),
                makePath(THE_SHOOTER_BUTTON), makePath(THE_SHOOTER_PORTRAIT), TheShooterEnum.THE_SHOOTER);
        
        receiveEditPotions();
        logger.info("done editing characters");
    }

    // =============== /LOAD THE CHARACTER/ =================

    
    // =============== POST-INITIALIZE =================

    
    @Override
    public void receivePostInitialize() {

        logger.info("Load Badge Image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = new Texture(makePath(BADGE_IMAGE));
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("ShooterMod doesn't have any settings!", 400.0f, 700.0f,
                settingsPanel, (me) -> {
                }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");

       }

    // =============== / POST-INITIALIZE/ =================

    
    // ================ ADD POTIONS ===================

       
    public void receiveEditPotions() {
        logger.info("begin editing potions");
       
        // Class Specific Potion If you want your potion to not be class-specific, just remove the player class at the end (in this case the "TheShooterEnum.THE_SHOOTER")
        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_lIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheShooterEnum.THE_SHOOTER);
      
        logger.info("end editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================

    
    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Add relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new Shield_Generator(), AbstractCardEnum.SHOOTER_GRAY);
        BaseMod.addRelicToCustomPool(new AnotherQuarter(), AbstractCardEnum.SHOOTER_GRAY);
        BaseMod.addRelicToCustomPool(new BackDoor(), AbstractCardEnum.SHOOTER_GRAY);
        BaseMod.addRelicToCustomPool(new DarkHull(), AbstractCardEnum.SHOOTER_GRAY);
        BaseMod.addRelicToCustomPool(new NiftyWrench(), AbstractCardEnum.SHOOTER_GRAY);
        BaseMod.addRelicToCustomPool(new InertiaCradle(), AbstractCardEnum.SHOOTER_GRAY);

        BaseMod.addRelic(new HullBreach(1), RelicType.SHARED);
        BaseMod.addRelic(new FailingShields(1), RelicType.SHARED);
        BaseMod.addRelic(new Broken_Weapons(1), RelicType.SHARED);

        // This adds a relic to the Shared pool. Every character can find this relic.
        BaseMod.addRelic(new StrongThruster(), RelicType.SHARED);
        //BaseMod.addRelic(new DarkStar(), RelicType.SHARED);




        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================

    
    
    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Add Variables");
        // Add the Custom Dynamic Variables
        BaseMod.addDynamicVariable(new ShooterCustomVariable());
        BaseMod.addDynamicVariable(new ShooterHealthVariable());
        BaseMod.addDynamicVariable(new ShooterRepairVariable());
        BaseMod.addDynamicVariable(new ShooterGoldVariable());
        BaseMod.addDynamicVariable(new ShooterTHPVariable());
        
        logger.info("Add Cards");
        // Add the cards
        BaseMod.addCard(new Strike_Gray());
        BaseMod.addCard(new FireEverything());
        BaseMod.addCard(new Defend_Gray());
        BaseMod.addCard(new Velocity());
        BaseMod.addCard(new ReinforceHull());
        BaseMod.addCard(new Shot());
        BaseMod.addCard(new TrickShot());
        BaseMod.addCard(new PowerToWeapons());
        BaseMod.addCard(new PowerToShields());
        BaseMod.addCard(new BigShot());
        BaseMod.addCard(new MeltArmor());
        BaseMod.addCard(new BarrierForm());
        BaseMod.addCard(new EvasiveManeuvers());
        BaseMod.addCard(new FlameThrower());
        BaseMod.addCard(new RushedRepairs());
        BaseMod.addCard(new Nullify());
        BaseMod.addCard(new IceMissiles());
        BaseMod.addCard(new LuxuryShield());
        BaseMod.addCard(new MultiShot());
        BaseMod.addCard(new HullRepairs());
        BaseMod.addCard(new ThreePointShot());
        BaseMod.addCard(new xXM4d5k1llZXx());
        BaseMod.addCard(new StorePower());
        BaseMod.addCard(new DareDevil());
        BaseMod.addCard(new ReflexShot());
        BaseMod.addCard(new PredictiveShield());
        BaseMod.addCard(new WarpDrive());
        BaseMod.addCard(new Grazed());
        BaseMod.addCard(new FlashShield());
        BaseMod.addCard(new InsistedMastery());
        BaseMod.addCard(new Untouchable());
        BaseMod.addCard(new AntiMatterBeam());
        BaseMod.addCard(new WardingShot());
        BaseMod.addCard(new SkillRush());
        BaseMod.addCard(new StatusRush());
        BaseMod.addCard(new HeavyHull());
        BaseMod.addCard(new GoldHull());
        BaseMod.addCard(new OpticHull());
        BaseMod.addCard(new AutoRepairs());
        BaseMod.addCard(new WeaponCharge());
        BaseMod.addCard(new ShieldCharge());
        BaseMod.addCard(new AttackRush());
        BaseMod.addCard(new CURSES());


        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        UnlockTracker.unlockCard(Strike_Gray.ID);
        UnlockTracker.unlockCard(FireEverything.ID);
        UnlockTracker.unlockCard(Defend_Gray.ID);
        UnlockTracker.unlockCard(Velocity.ID);
        UnlockTracker.unlockCard(ReinforceHull.ID);
        UnlockTracker.unlockCard(Shot.ID);
        UnlockTracker.unlockCard(TrickShot.ID);
        UnlockTracker.unlockCard(PowerToWeapons.ID);
        UnlockTracker.unlockCard(PowerToShields.ID);
        UnlockTracker.unlockCard(BigShot.ID);
        UnlockTracker.unlockCard(MeltArmor.ID);
        UnlockTracker.unlockCard(BarrierForm.ID);
        UnlockTracker.unlockCard(EvasiveManeuvers.ID);
        UnlockTracker.unlockCard(FlameThrower.ID);
        UnlockTracker.unlockCard(RushedRepairs.ID);
        UnlockTracker.unlockCard(Nullify.ID);
        UnlockTracker.unlockCard(IceMissiles.ID);
        UnlockTracker.unlockCard(LuxuryShield.ID);
        UnlockTracker.unlockCard(MultiShot.ID);
        UnlockTracker.unlockCard(HullRepairs.ID);
        UnlockTracker.unlockCard(ThreePointShot.ID);
        UnlockTracker.unlockCard(xXM4d5k1llZXx.ID);
        UnlockTracker.unlockCard(StorePower.ID);
        UnlockTracker.unlockCard(DareDevil.ID);
        UnlockTracker.unlockCard(ReflexShot.ID);
        UnlockTracker.unlockCard(PredictiveShield.ID);
        UnlockTracker.unlockCard(WarpDrive.ID);
        UnlockTracker.unlockCard(Grazed.ID);
        UnlockTracker.unlockCard(FlashShield.ID);
        UnlockTracker.unlockCard(InsistedMastery.ID);
        UnlockTracker.unlockCard(Untouchable.ID);
        UnlockTracker.unlockCard(AntiMatterBeam.ID);
        UnlockTracker.unlockCard(WardingShot.ID);
        UnlockTracker.unlockCard(SkillRush.ID);
        UnlockTracker.unlockCard(StatusRush.ID);
        UnlockTracker.unlockCard(HeavyHull.ID);
        UnlockTracker.unlockCard(GoldHull.ID);
        UnlockTracker.unlockCard(OpticHull.ID);
        UnlockTracker.unlockCard(AutoRepairs.ID);
        UnlockTracker.unlockCard(WeaponCharge.ID);
        UnlockTracker.unlockCard(ShieldCharge.ID);
        UnlockTracker.unlockCard(AttackRush.ID);
        UnlockTracker.unlockCard(CURSES.ID);

        logger.info("Cards - added!");
    }

    // ================ /ADD CARDS/ ===================

    
    
    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Begin editting strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "shooterModResources/localization/ShooterMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "shooterModResources/localization/ShooterMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "shooterModResources/localization/ShooterMod-Relic-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                "shooterModResources/localization/ShooterMod-Potion-Strings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    public static boolean healthy() {
        int max = AbstractDungeon.player.maxHealth;
        double check = 9.0/10;
        float f = (float)check;
        int floor = MathUtils.floor((AbstractDungeon.player.maxHealth * (f)));
        if (AbstractDungeon.player.currentHealth >= floor){
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean wealthy() {
        int gold = AbstractDungeon.player.gold;
        if (gold >= 250){
            return true;
        }
        else {
            return false;
        }
    }


    public static boolean masterful() {

        if (AbstractDungeon.player.damagedThisCombat != 0) {
            return false;
        }
        else {
            return true;
        }
    }





    @Override
    public void receiveEditKeywords() {
        final String[] placeholder = { "Penalty", "penalty" };
        BaseMod.addKeyword(placeholder, "Whenever you play a Penalty card, gain a negative relic.");

        final String[] master = { "Masterful", "masterful" };
        BaseMod.addKeyword(master, "Has an effect when you haven't lost health yet this fight.");

        final String[] wealth = { "Wealthy", "wealthy" };
        BaseMod.addKeyword(wealth, "Has an effect when you have 250 or more gold.");

        final String[] health = { "Healthy", "healthy" };
        BaseMod.addKeyword(health, "Has an effect when you are at or above 90% Health.");

        final String[] evade = { "Evasion", "evasion" };
        BaseMod.addKeyword(evade, "Each point gives you a 1% chance to dodge attacks. NL Removed at the start of your turn.");

        final String[] fade = { "Fading", "fading" };
        BaseMod.addKeyword(fade, "Counts down by one after the enemy's turn, enemy dies when it reaches 0.");

    }
    // ================ /LOAD THE KEYWORDS/ ===================    

    // this adds "ModName: " before the ID of any card/relic/power etc.
    // in order to avoid conflics if any other mod uses the same ID.
    public static String makeID(String idText) {
        return "theShooter: " + idText;
    }

}
