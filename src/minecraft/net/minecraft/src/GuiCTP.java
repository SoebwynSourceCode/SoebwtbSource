package net.minecraft.src;

import java.awt.Desktop;
import java.awt.Menu;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiCTP extends GuiScreen
{
    private int xSize = 230;
    private int ySize = 162;
    protected String screenTitle = "Soebwyn";
    protected GuiScreen guiScreen;
    private World world;

    public GuiCTP(Minecraft var1, World var2)
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
        this.buttonList.add(new GuiButton(15, this.width / 2 - 70, this.height / var2 + 80, 140, 20, var1.translateKey("Rejoindre le CTP")));
        this.buttonList.add(new GuiButton(16, this.width / 2 - 70, this.height / var2 + 105, 140, 20, var1.translateKey("Rejoindre la partie en cours")));
        this.buttonList.add(new GuiButton(17, this.width / 2 - 70, this.height / var3 + 130, 140, 20, var1.translateKey("Aide sur le CTP")));
        this.buttonList.add(new GuiButton(18, this.width / 2 - 70, this.height / var3 + 155, 140, 20, var1.translateKey("Quitter la partie")));
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
                this.mc.thePlayer.sendChatMessage("/ctp join");
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 16)
            {
                this.mc.thePlayer.sendChatMessage(" /ctp rejoin");
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 17)
            {
                Desktop var2 = null;

                try
                {
                    URI var3 = new URI("https://soebwyn.fr/wiki/");

                    if (Desktop.isDesktopSupported())
                    {
                        var2 = Desktop.getDesktop();
                        var2.browse(var3);
                    }
                }
                catch (Exception var5)
                {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, (String)null, var5);
                }
            }

            if (var1.id == 18)
            {
                this.mc.thePlayer.sendChatMessage("/ctp leave");
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 19)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
            }
        }
    }
}
