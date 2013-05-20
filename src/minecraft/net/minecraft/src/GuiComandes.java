package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiComandes extends GuiScreen
{
    private int xSize = 230;
    private int ySize = 162;
    protected String screenTitle = "Soebwyn";
    protected GuiScreen guiScreen;
    private World world;

    public GuiComandes(Minecraft var1, World var2)
    {
        this.mc = var1;
        this.world = var2;
        this.xSize = 256;
        this.ySize = 256;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate var1 = StringTranslate.getInstance();
        this.screenTitle = var1.translateKey("Menu Personnaliser");
        int var2 = this.height / 4 + 100;
        int var3 = this.height / 4 + 140;
        this.buttonList.add(new GuiButton(15, this.width / 2 + 10, this.height / var2 + 80, 100, 20, var1.translateKey("Nouveaut\u00e9es")));
        this.buttonList.add(new GuiButton(16, this.width / 2 + 10, this.height / var2 + 105, 100, 20, var1.translateKey("D\u00e9finir sa maison")));
        this.buttonList.add(new GuiButton(17, this.width / 2 + 10, this.height / var3 + 130, 100, 20, var1.translateKey("Aller a sa Maison")));
        this.buttonList.add(new GuiButton(18, this.width / 2 + 10, this.height / var3 + 155, 100, 20, var1.translateKey("Boutique/Banque")));
        this.buttonList.add(new GuiButtonTp(19, this.width / 2 - 75, this.height / var3 + 205, 150, 20, var1.translateKey("Retour")));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int var1, int var2, float var3)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        this.drawDefaultBackground();
        this.drawBackgroundImage();
        super.drawScreen(var1, var2, var3);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    protected void drawGuiContainerForegroundLayer(int var1, int var2)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("Argent:"), 90, 24, 4210752);
    }

    protected void drawBackgroundImage()
    {
        int var1 = (this.width - this.xSize) / 2;
        int var2 = (this.height - this.ySize) / 2;
        int var3 = this.mc.renderEngine.getTexture("/soebwyn/gui/tp.png");
        this.mc.renderEngine.bindTexture(var3);
        this.drawTexturedModalRect(var1, var2, 0, 0, this.xSize, this.ySize);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton var1)
    {
        if (var1.enabled)
        {
            if (var1.id == 15)
            {
                this.mc.thePlayer.sendChatMessage("/warp Dev");
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 16)
            {
                this.mc.thePlayer.sendChatMessage("/sethome");
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 17)
            {
                this.mc.thePlayer.sendChatMessage("/home");
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 18)
            {
                this.mc.thePlayer.sendChatMessage("/warp Boutique");
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 19)
            {
                this.mc.displayGuiScreen(new GuiTp(this.mc, this.world));
            }
        }
    }
}
