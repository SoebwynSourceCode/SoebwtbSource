package net.minecraft.src;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiIngame extends Gui
{
    private static final RenderItem itemRenderer = new RenderItem();
    private final Random rand = new Random();
    private final Minecraft mc;

    /** ChatGUI instance that retains all previous chat data */
    private final GuiNewChat persistantChatGUI;
    private int updateCounter = 0;

    /** The string specifying which record music is playing */
    private String recordPlaying = "";

    /** How many ticks the record playing message will be displayed */
    private int recordPlayingUpFor = 0;
    private boolean recordIsPlaying = false;

    /** Previous frame vignette brightness (slowly changes by 1% each frame) */
    public float prevVignetteBrightness = 1.0F;
    private int field_92017_k;
    private ItemStack field_92016_l;

    public GuiIngame(Minecraft par1Minecraft)
    {
        this.mc = par1Minecraft;
        this.persistantChatGUI = new GuiNewChat(par1Minecraft);
    }

    /**
     * Render the ingame overlay with quick icon bar, ...
     */
    public void renderGameOverlay(float par1, boolean par2, int par3, int par4)
    {
        ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int var6 = var5.getScaledWidth();
        int var7 = var5.getScaledHeight();
        FontRenderer var8 = this.mc.fontRenderer;
        this.mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(GL11.GL_BLEND);

        if (Minecraft.isFancyGraphicsEnabled())
        {
            this.renderVignette(this.mc.thePlayer.getBrightness(par1), var6, var7);
        }
        else
        {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        ItemStack var9 = this.mc.thePlayer.inventory.armorItemInSlot(3);

        if (this.mc.gameSettings.thirdPersonView == 0 && var9 != null && var9.itemID == Block.pumpkin.blockID)
        {
            this.renderPumpkinBlur(var6, var7);
        }

        if (!this.mc.thePlayer.isPotionActive(Potion.confusion))
        {
            float var10 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * par1;

            if (var10 > 0.0F)
            {
                this.renderPortalOverlay(var10, var6, var7);
            }
        }

        int var11;
        int var12;
        int var13;
        int var14;
        int var15;
        int var17;
        int var16;
        int var19;
        int var18;
        int var21;
        byte var20;
        int var23;
        int var22;
        byte var31;
        boolean var38;

        if (!this.mc.playerController.enableEverythingIsScrewedUpMode())
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture("/gui/gui.png");
            InventoryPlayer var24 = this.mc.thePlayer.inventory;
            this.zLevel = -90.0F;
            this.drawTexturedModalRect(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(var6 / 2 - 91 - 1 + var24.currentItem * 20, var7 - 22 - 1, 0, 22, 24, 22);
            this.mc.renderEngine.bindTexture("/gui/icons.png");
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
            this.drawTexturedModalRect(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
            GL11.glDisable(GL11.GL_BLEND);
            var38 = this.mc.thePlayer.hurtResistantTime / 3 % 2 == 1;

            if (this.mc.thePlayer.hurtResistantTime < 10)
            {
                var38 = false;
            }

            var11 = this.mc.thePlayer.getHealth();
            var12 = this.mc.thePlayer.prevHealth;
            this.rand.setSeed((long)(this.updateCounter * 312871));
            boolean var25 = false;
            FoodStats var26 = this.mc.thePlayer.getFoodStats();
            var14 = var26.getFoodLevel();
            var13 = var26.getPrevFoodLevel();
            this.mc.mcProfiler.startSection("bossHealth");
            this.renderBossHealth();
            this.mc.mcProfiler.endSection();
            int var27;

            if (this.mc.playerController.shouldDrawHUD())
            {
                var15 = var6 / 2 - 91;
                var27 = var6 / 2 + 91;
                this.mc.mcProfiler.startSection("expBar");
                var16 = this.mc.thePlayer.xpBarCap();

                if (var16 > 0)
                {
                    short var28 = 182;
                    var18 = (int)(this.mc.thePlayer.experience * (float)(var28 + 1));
                    var17 = var7 - 32 + 3;
                    this.drawTexturedModalRect(var15, var17, 0, 64, var28, 5);

                    if (var18 > 0)
                    {
                        this.drawTexturedModalRect(var15, var17, 0, 69, var18, 5);
                    }
                }

                var22 = var7 - 39;
                var18 = var22 - 10;
                var17 = this.mc.thePlayer.getTotalArmorValue();
                var19 = -1;

                if (this.mc.thePlayer.isPotionActive(Potion.regeneration))
                {
                    var19 = this.updateCounter % 25;
                }

                this.mc.mcProfiler.endStartSection("healthArmor");
                int var29;
                int var30;
                int var42;

                for (var42 = 0; var42 < 10; ++var42)
                {
                    if (var17 > 0)
                    {
                        var21 = var15 + var42 * 8;

                        if (var42 * 2 + 1 < var17)
                        {
                            this.drawTexturedModalRect(var21, var18, 34, 9, 9, 9);
                        }

                        if (var42 * 2 + 1 == var17)
                        {
                            this.drawTexturedModalRect(var21, var18, 25, 9, 9, 9);
                        }

                        if (var42 * 2 + 1 > var17)
                        {
                            this.drawTexturedModalRect(var21, var18, 16, 9, 9, 9);
                        }
                    }

                    var21 = 16;

                    if (this.mc.thePlayer.isPotionActive(Potion.poison))
                    {
                        var21 += 36;
                    }
                    else if (this.mc.thePlayer.isPotionActive(Potion.wither))
                    {
                        var21 += 72;
                    }

                    var20 = 0;

                    if (var38)
                    {
                        var20 = 1;
                    }

                    var30 = var15 + var42 * 8;
                    var29 = var22;

                    if (var11 <= 4)
                    {
                        var29 = var22 + this.rand.nextInt(2);
                    }

                    if (var42 == var19)
                    {
                        var29 -= 2;
                    }

                    var31 = 0;

                    if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
                    {
                        var31 = 5;
                    }

                    this.drawTexturedModalRect(var30, var29, 16 + var20 * 9, 9 * var31, 9, 9);

                    if (var38)
                    {
                        if (var42 * 2 + 1 < var12)
                        {
                            this.drawTexturedModalRect(var30, var29, var21 + 54, 9 * var31, 9, 9);
                        }

                        if (var42 * 2 + 1 == var12)
                        {
                            this.drawTexturedModalRect(var30, var29, var21 + 63, 9 * var31, 9, 9);
                        }
                    }

                    if (var42 * 2 + 1 < var11)
                    {
                        this.drawTexturedModalRect(var30, var29, var21 + 36, 9 * var31, 9, 9);
                    }

                    if (var42 * 2 + 1 == var11)
                    {
                        this.drawTexturedModalRect(var30, var29, var21 + 45, 9 * var31, 9, 9);
                    }
                }

                this.mc.mcProfiler.endStartSection("food");

                for (var42 = 0; var42 < 10; ++var42)
                {
                    var21 = var22;
                    var23 = 16;
                    var31 = 0;

                    if (this.mc.thePlayer.isPotionActive(Potion.hunger))
                    {
                        var23 += 36;
                        var31 = 13;
                    }

                    if (this.mc.thePlayer.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (var14 * 3 + 1) == 0)
                    {
                        var21 = var22 + (this.rand.nextInt(3) - 1);
                    }

                    if (var25)
                    {
                        var31 = 1;
                    }

                    var29 = var27 - var42 * 8 - 9;
                    this.drawTexturedModalRect(var29, var21, 16 + var31 * 9, 27, 9, 9);

                    if (var25)
                    {
                        if (var42 * 2 + 1 < var13)
                        {
                            this.drawTexturedModalRect(var29, var21, var23 + 54, 27, 9, 9);
                        }

                        if (var42 * 2 + 1 == var13)
                        {
                            this.drawTexturedModalRect(var29, var21, var23 + 63, 27, 9, 9);
                        }
                    }

                    if (var42 * 2 + 1 < var14)
                    {
                        this.drawTexturedModalRect(var29, var21, var23 + 36, 27, 9, 9);
                    }

                    if (var42 * 2 + 1 == var14)
                    {
                        this.drawTexturedModalRect(var29, var21, var23 + 45, 27, 9, 9);
                    }
                }

                this.mc.mcProfiler.endStartSection("air");

                if (this.mc.thePlayer.isInsideOfMaterial(Material.water))
                {
                    var42 = this.mc.thePlayer.getAir();
                    var21 = MathHelper.ceiling_double_int((double)(var42 - 2) * 10.0D / 300.0D);
                    var23 = MathHelper.ceiling_double_int((double)var42 * 10.0D / 300.0D) - var21;

                    for (var30 = 0; var30 < var21 + var23; ++var30)
                    {
                        if (var30 < var21)
                        {
                            this.drawTexturedModalRect(var27 - var30 * 8 - 9, var18, 16, 18, 9, 9);
                        }
                        else
                        {
                            this.drawTexturedModalRect(var27 - var30 * 8 - 9, var18, 25, 18, 9, 9);
                        }
                    }
                }

                this.mc.mcProfiler.endSection();
            }

            GL11.glDisable(GL11.GL_BLEND);
            this.mc.mcProfiler.startSection("actionBar");
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.enableGUIStandardItemLighting();

            for (var15 = 0; var15 < 9; ++var15)
            {
                var27 = var6 / 2 - 90 + var15 * 20 + 2;
                var16 = var7 - 16 - 3;
                this.renderInventorySlot(var15, var27, var16, par1);
            }

            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            this.mc.mcProfiler.endSection();
        }

        int var39;
        float var40;

        if (this.mc.thePlayer.getSleepTimer() > 0)
        {
            this.mc.mcProfiler.startSection("sleep");
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            var39 = this.mc.thePlayer.getSleepTimer();
            var40 = (float)var39 / 100.0F;

            if (var40 > 1.0F)
            {
                var40 = 1.0F - (float)(var39 - 100) / 10.0F;
            }

            var11 = (int)(220.0F * var40) << 24 | 1052704;
            drawRect(0, 0, var6, var7, var11);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            this.mc.mcProfiler.endSection();
        }

        int var43;
        String var41;

        if (this.mc.playerController.func_78763_f() && this.mc.thePlayer.experienceLevel > 0)
        {
            this.mc.mcProfiler.startSection("expLevel");
            var38 = false;
            var11 = var38 ? 16777215 : 8453920;
            var41 = "" + this.mc.thePlayer.experienceLevel;
            var39 = (var6 - var8.getStringWidth(var41)) / 2;
            var43 = var7 - 31 - 4;
            var8.drawString(var41, var39 + 1, var43, 0);
            var8.drawString(var41, var39 - 1, var43, 0);
            var8.drawString(var41, var39, var43 + 1, 0);
            var8.drawString(var41, var39, var43 - 1, 0);
            var8.drawString(var41, var39, var43, var11);
            this.mc.mcProfiler.endSection();
        }

        if (this.mc.gameSettings.heldItemTooltips)
        {
            this.mc.mcProfiler.startSection("toolHighlight");

            if (this.field_92017_k > 0 && this.field_92016_l != null)
            {
                var41 = this.field_92016_l.getDisplayName();
                var11 = (var6 - var8.getStringWidth(var41)) / 2;
                var12 = var7 - 59;

                if (!this.mc.playerController.shouldDrawHUD())
                {
                    var12 += 14;
                }

                var39 = (int)((float)this.field_92017_k * 256.0F / 10.0F);

                if (var39 > 255)
                {
                    var39 = 255;
                }

                if (var39 > 0)
                {
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    var8.drawStringWithShadow(var41, var11, var12, 16777215 + (var39 << 24));
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }

            this.mc.mcProfiler.endSection();
        }

        if (this.mc.isDemo())
        {
            this.mc.mcProfiler.startSection("demo");
            var41 = "";

            if (this.mc.theWorld.getTotalWorldTime() >= 120500L)
            {
                var41 = StatCollector.translateToLocal("demo.demoExpired");
            }
            else
            {
                var41 = String.format(StatCollector.translateToLocal("demo.remainingTime"), new Object[] {StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime()))});
            }

            var11 = var8.getStringWidth(var41);
            var8.drawStringWithShadow(var41, var6 - var11 - 10, 5, 16777215);
            this.mc.mcProfiler.endSection();
        }

        String var36;

        if (this.mc.gameSettings.showDebugInfo)
        {
            this.mc.mcProfiler.startSection("debug");
            GL11.glPushMatrix();
            var8.drawStringWithShadow("Minecraft 1.5.1(" + this.mc.debug + ")", 2, 2, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 12, 16777215);
            var8.drawStringWithShadow(this.mc.getEntityDebug(), 2, 22, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 32, 16777215);
            StringBuilder var10001 = (new StringBuilder()).append("Version Soebwyn by Engue0774. OS:");
            Minecraft var10002 = this.mc;
            var8.drawStringWithShadow(var10001.append(Minecraft.getOs()).toString(), 2, 42, 16777215);
            long var48 = Runtime.getRuntime().maxMemory();
            long var47 = Runtime.getRuntime().totalMemory();
            long var32 = Runtime.getRuntime().freeMemory();
            long var34 = var47 - var32;
            var36 = "RAM Utilis\u00e9e: " + var34 * 100L / var48 + "% (" + var34 / 1024L / 1024L + "MB) sur " + var48 / 1024L / 1024L + "MB";
            this.drawString(var8, var36, var6 - var8.getStringWidth(var36) - 2, 2, 14737632);
            var36 = "RAM Allou\u00e9: " + var47 * 100L / var48 + "% (" + var47 / 1024L / 1024L + "MB)";
            this.drawString(var8, var36, var6 - var8.getStringWidth(var36) - 2, 12, 14737632);
            var22 = MathHelper.floor_double(this.mc.thePlayer.posX);
            var18 = MathHelper.floor_double(this.mc.thePlayer.posY);
            var17 = MathHelper.floor_double(this.mc.thePlayer.posZ);
            this.drawString(var8, String.format("x: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posX), Integer.valueOf(var22), Integer.valueOf(var22 >> 4), Integer.valueOf(var22 & 15)}), 2, 64, 14737632);
            this.drawString(var8, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[] {Double.valueOf(this.mc.thePlayer.boundingBox.minY), Double.valueOf(this.mc.thePlayer.posY)}), 2, 72, 14737632);
            this.drawString(var8, String.format("z: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posZ), Integer.valueOf(var17), Integer.valueOf(var17 >> 4), Integer.valueOf(var17 & 15)}), 2, 80, 14737632);
            var19 = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            this.drawString(var8, "f: " + var19 + " (" + Direction.directions[var19] + ") / " + MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw), 2, 88, 14737632);

            if (this.mc.theWorld != null && this.mc.theWorld.blockExists(var22, var18, var17))
            {
                Chunk var37 = this.mc.theWorld.getChunkFromBlockCoords(var22, var17);
                this.drawString(var8, "lc: " + (var37.getTopFilledSegment() + 15) + " b: " + var37.getBiomeGenForWorldCoords(var22 & 15, var17 & 15, this.mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + var37.getSavedLightValue(EnumSkyBlock.Block, var22 & 15, var18, var17 & 15) + " sl: " + var37.getSavedLightValue(EnumSkyBlock.Sky, var22 & 15, var18, var17 & 15) + " rl: " + var37.getBlockLightValue(var22 & 15, var18, var17 & 15, 0), 2, 96, 14737632);
            }

            this.drawString(var8, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", new Object[] {Float.valueOf(this.mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(this.mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(this.mc.thePlayer.onGround), Integer.valueOf(this.mc.theWorld.getHeightValue(var22, var17))}), 2, 104, 14737632);
            GL11.glPopMatrix();
            this.mc.mcProfiler.endSection();
        }

        if (this.recordPlayingUpFor > 0)
        {
            this.mc.mcProfiler.startSection("overlayMessage");
            var40 = (float)this.recordPlayingUpFor - par1;
            var11 = (int)(var40 * 256.0F / 20.0F);

            if (var11 > 255)
            {
                var11 = 255;
            }

            if (var11 > 0)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(var6 / 2), (float)(var7 - 48), 0.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                var12 = 16777215;

                if (this.recordIsPlaying)
                {
                    var12 = Color.HSBtoRGB(var40 / 50.0F, 0.7F, 0.6F) & 16777215;
                }

                var8.drawString(this.recordPlaying, -var8.getStringWidth(this.recordPlaying) / 2, -4, var12 + (var11 << 24));
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }

            this.mc.mcProfiler.endSection();
        }

        ScoreObjective var44 = this.mc.theWorld.getScoreboard().func_96539_a(1);

        if (var44 != null)
        {
            this.func_96136_a(var44, var7, var6, var8);
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, (float)(var7 - 48), 0.0F);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GL11.glPopMatrix();
        var44 = this.mc.theWorld.getScoreboard().func_96539_a(0);

        if (this.mc.gameSettings.keyBindPlayerList.pressed && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.playerInfoList.size() > 1 || var44 != null))
        {
            this.mc.mcProfiler.startSection("playerList");
            NetClientHandler var46 = this.mc.thePlayer.sendQueue;
            List var45 = var46.playerInfoList;
            var39 = var46.currentServerMaxPlayers;
            var43 = var39;

            for (var14 = 1; var43 > 20; var43 = (var39 + var14 - 1) / var14)
            {
                ++var14;
            }

            var13 = 300 / var14;

            if (var13 > 150)
            {
                var13 = 150;
            }

            var15 = (var6 - var14 * var13) / 2;
            var31 = 10;
            drawRect(var15 - 1, var31 - 1, var15 + var13 * var14, var31 + 9 * var43, Integer.MIN_VALUE);

            for (var16 = 0; var16 < var39; ++var16)
            {
                var22 = var15 + var16 % var14 * var13;
                var18 = var31 + var16 / var14 * 9;
                drawRect(var22, var18, var22 + var13 - 1, var18 + 8, 553648127);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                if (var16 < var45.size())
                {
                    GuiPlayerInfo var49 = (GuiPlayerInfo)var45.get(var16);
                    ScorePlayerTeam var33 = this.mc.theWorld.getScoreboard().func_96509_i(var49.name);
                    String var51 = ScorePlayerTeam.func_96667_a(var33, var49.name);
                    var8.drawStringWithShadow(var51, var22, var18, 16777215);

                    if (var44 != null)
                    {
                        var21 = var22 + var8.getStringWidth(var51) + 5;
                        var23 = var22 + var13 - 12 - 5;

                        if (var23 - var21 > 5)
                        {
                            Score var35 = var44.func_96682_a().func_96529_a(var49.name, var44);
                            var36 = EnumChatFormatting.YELLOW + "" + var35.func_96652_c();
                            var8.drawStringWithShadow(var36, var23 - var8.getStringWidth(var36), var18, 16777215);
                        }
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.mc.renderEngine.bindTexture("/gui/icons.png");
                    byte var50 = 0;
                    boolean var52 = false;

                    if (var49.responseTime < 0)
                    {
                        var20 = 5;
                    }
                    else if (var49.responseTime < 150)
                    {
                        var20 = 0;
                    }
                    else if (var49.responseTime < 300)
                    {
                        var20 = 1;
                    }
                    else if (var49.responseTime < 600)
                    {
                        var20 = 2;
                    }
                    else if (var49.responseTime < 1000)
                    {
                        var20 = 3;
                    }
                    else
                    {
                        var20 = 4;
                    }

                    this.zLevel += 100.0F;
                    this.drawTexturedModalRect(var22 + var13 - 12, var18, 0 + var50 * 10, 176 + var20 * 8, 10, 8);
                    this.zLevel -= 100.0F;
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    private void func_96136_a(ScoreObjective par1ScoreObjective, int par2, int par3, FontRenderer par4FontRenderer)
    {
        Scoreboard var5 = par1ScoreObjective.func_96682_a();
        Collection var6 = var5.func_96534_i(par1ScoreObjective);

        if (var6.size() <= 15)
        {
            int var7 = par4FontRenderer.getStringWidth(par1ScoreObjective.func_96678_d());
            String var8;

            for (Iterator var9 = var6.iterator(); var9.hasNext(); var7 = Math.max(var7, par4FontRenderer.getStringWidth(var8)))
            {
                Score var10 = (Score)var9.next();
                ScorePlayerTeam var11 = var5.func_96509_i(var10.func_96653_e());
                var8 = ScorePlayerTeam.func_96667_a(var11, var10.func_96653_e()) + ": " + EnumChatFormatting.RED + var10.func_96652_c();
            }

            int var22 = var6.size() * par4FontRenderer.FONT_HEIGHT;
            int var24 = par2 / 2 + var22 / 3;
            byte var23 = 3;
            int var12 = par3 - var7 - var23;
            int var13 = 0;
            Iterator var14 = var6.iterator();

            while (var14.hasNext())
            {
                Score var15 = (Score)var14.next();
                ++var13;
                ScorePlayerTeam var16 = var5.func_96509_i(var15.func_96653_e());
                String var17 = ScorePlayerTeam.func_96667_a(var16, var15.func_96653_e());
                String var18 = EnumChatFormatting.RED + "" + var15.func_96652_c();
                int var19 = var24 - var13 * par4FontRenderer.FONT_HEIGHT;
                int var20 = par3 - var23 + 2;
                drawRect(var12 - 2, var19, var20, var19 + par4FontRenderer.FONT_HEIGHT, 1342177280);
                par4FontRenderer.drawString(var17, var12, var19, 553648127);
                par4FontRenderer.drawString(var18, var20 - par4FontRenderer.getStringWidth(var18), var19, 553648127);

                if (var13 == var6.size())
                {
                    String var21 = par1ScoreObjective.func_96678_d();
                    drawRect(var12 - 2, var19 - par4FontRenderer.FONT_HEIGHT - 1, var20, var19 - 1, 1610612736);
                    drawRect(var12 - 2, var19 - 1, var20, var19, 1342177280);
                    par4FontRenderer.drawString(var21, var12 + var7 / 2 - par4FontRenderer.getStringWidth(var21) / 2, var19 - par4FontRenderer.FONT_HEIGHT, 553648127);
                }
            }
        }
    }

    /**
     * Renders dragon's (boss) health on the HUD
     */
    private void renderBossHealth()
    {
        if (BossStatus.bossName != null && BossStatus.statusBarLength > 0)
        {
            --BossStatus.statusBarLength;
            FontRenderer var1 = this.mc.fontRenderer;
            ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            int var3 = var2.getScaledWidth();
            short var4 = 182;
            int var5 = var3 / 2 - var4 / 2;
            int var6 = (int)(BossStatus.healthScale * (float)(var4 + 1));
            byte var7 = 12;
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);

            if (var6 > 0)
            {
                this.drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
            }

            String var8 = BossStatus.bossName;
            var1.drawStringWithShadow(var8, var3 / 2 - var1.getStringWidth(var8) / 2, var7 - 10, 16777215);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture("/gui/icons.png");
        }
    }

    private void renderPumpkinBlur(int par1, int par2)
    {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        this.mc.renderEngine.bindTexture("%blur%/misc/pumpkinblur.png");
        Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0D, (double)par2, -90.0D, 0.0D, 1.0D);
        var3.addVertexWithUV((double)par1, (double)par2, -90.0D, 1.0D, 1.0D);
        var3.addVertexWithUV((double)par1, 0.0D, -90.0D, 1.0D, 0.0D);
        var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders the vignette. Args: vignetteBrightness, width, height
     */
    private void renderVignette(float par1, int par2, int par3)
    {
        par1 = 1.0F - par1;

        if (par1 < 0.0F)
        {
            par1 = 0.0F;
        }

        if (par1 > 1.0F)
        {
            par1 = 1.0F;
        }

        this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(par1 - this.prevVignetteBrightness) * 0.01D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
        GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
        this.mc.renderEngine.bindTexture("%blur%/misc/vignette.png");
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        var4.addVertexWithUV(0.0D, (double)par3, -90.0D, 0.0D, 1.0D);
        var4.addVertexWithUV((double)par2, (double)par3, -90.0D, 1.0D, 1.0D);
        var4.addVertexWithUV((double)par2, 0.0D, -90.0D, 1.0D, 0.0D);
        var4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var4.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Renders the portal overlay. Args: portalStrength, width, height
     */
    private void renderPortalOverlay(float par1, int par2, int par3)
    {
        if (par1 < 1.0F)
        {
            par1 *= par1;
            par1 *= par1;
            par1 = par1 * 0.8F + 0.2F;
        }

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, par1);
        this.mc.renderEngine.bindTexture("/terrain.png");
        Icon var4 = Block.portal.getBlockTextureFromSide(1);
        float var5 = var4.getMinU();
        float var6 = var4.getMinV();
        float var7 = var4.getMaxU();
        float var8 = var4.getMaxV();
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(0.0D, (double)par3, -90.0D, (double)var5, (double)var8);
        var9.addVertexWithUV((double)par2, (double)par3, -90.0D, (double)var7, (double)var8);
        var9.addVertexWithUV((double)par2, 0.0D, -90.0D, (double)var7, (double)var6);
        var9.addVertexWithUV(0.0D, 0.0D, -90.0D, (double)var5, (double)var6);
        var9.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders the specified item of the inventory slot at the specified location. Args: slot, x, y, partialTick
     */
    private void renderInventorySlot(int par1, int par2, int par3, float par4)
    {
        ItemStack var5 = this.mc.thePlayer.inventory.mainInventory[par1];

        if (var5 != null)
        {
            float var6 = (float)var5.animationsToGo - par4;

            if (var6 > 0.0F)
            {
                GL11.glPushMatrix();
                float var7 = 1.0F + var6 / 5.0F;
                GL11.glTranslatef((float)(par2 + 8), (float)(par3 + 12), 0.0F);
                GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
                GL11.glTranslatef((float)(-(par2 + 8)), (float)(-(par3 + 12)), 0.0F);
            }

            itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);

            if (var6 > 0.0F)
            {
                GL11.glPopMatrix();
            }

            itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);
        }
    }

    /**
     * The update tick for the ingame UI
     */
    public void updateTick()
    {
        if (this.recordPlayingUpFor > 0)
        {
            --this.recordPlayingUpFor;
        }

        ++this.updateCounter;

        if (this.mc.thePlayer != null)
        {
            ItemStack var1 = this.mc.thePlayer.inventory.getCurrentItem();

            if (var1 == null)
            {
                this.field_92017_k = 0;
            }
            else if (this.field_92016_l != null && var1.itemID == this.field_92016_l.itemID && ItemStack.areItemStackTagsEqual(var1, this.field_92016_l) && (var1.isItemStackDamageable() || var1.getItemDamage() == this.field_92016_l.getItemDamage()))
            {
                if (this.field_92017_k > 0)
                {
                    --this.field_92017_k;
                }
            }
            else
            {
                this.field_92017_k = 40;
            }

            this.field_92016_l = var1;
        }
    }

    public void setRecordPlayingMessage(String par1Str)
    {
        this.recordPlaying = "Now playing: " + par1Str;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = true;
    }

    /**
     * returns a pointer to the persistant Chat GUI, containing all previous chat messages and such
     */
    public GuiNewChat getChatGUI()
    {
        return this.persistantChatGUI;
    }

    public int getUpdateCounter()
    {
        return this.updateCounter;
    }
}
