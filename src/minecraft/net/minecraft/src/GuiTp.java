package net.minecraft.src;

import java.awt.Desktop;
import java.awt.Menu;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiTp extends GuiScreen
{
    private float xSize_lo;
    private float ySize_lo;
    private int xSize = 230;
    private int ySize = 162;
    String pseudo;
    protected String screenTitle;
    protected GuiScreen guiScreen;
    private World world;

    public GuiTp(Minecraft var1, World var2)
    {
        this.pseudo = Minecraft.getMinecraft().thePlayer.username;
        this.screenTitle = "Soebwyn";
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
        String var4 = this.pseudo;
        String var5 = FontColors.GOLD + this.mc.Money;
        this.buttonList.add(new GuiButton(15, this.width / 2 - 90, this.height / var3 + 95, 65, 20, var1.translateKey("Spawn")));
        this.buttonList.add(new GuiButton(16, this.width / 2 - 90, this.height / var3 + 120, 65, 20, var1.translateKey("Commandes")));
        this.buttonList.add(new GuiButton(17, this.width / 2 - 90, this.height / var3 + 145, 65, 20, var1.translateKey("Staff")));
        this.buttonList.add(new GuiButton(18, this.width / 2 - 90, this.height / var3 + 170, 65, 20, var1.translateKey("TeamSpeak")));
        this.buttonList.add(new GuiButtonTp(19, this.width / 2 - 75, this.height / var3 + 200, 150, 20, var1.translateKey("Retour")));
        this.buttonList.add(new GuiButton(20, this.width / 2 + 20, this.height / var3 + 95, 90, 20, var1.translateKey(var4)));
        this.buttonList.add(new GuiButton(21, this.width / 2 + 20, this.height / var3 + 125, 90, 20, var1.translateKey(var5 + " Soeb")));
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
                this.mc.thePlayer.sendChatMessage("/spawn");
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (var1.id == 16)
            {
                this.mc.displayGuiScreen(new GuiComandes(this.mc, this.world));
            }

            if (var1.id == 17)
            {
                this.mc.displayGuiScreen(new GuiStaff(this.mc, this.world));
            }

            if (var1.id == 18)
            {
                Desktop var2 = null;

                try
                {
                    URI var3 = new URI("ts3server://ts.soebwyn.fr:9993");

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

            if (var1.id == 19)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
            }
        }
    }
}
