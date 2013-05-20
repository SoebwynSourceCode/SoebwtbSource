package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiButtonTp extends GuiButton
{
    public GuiButtonTp(int var1, int var2, int var3, int var4, int var5, String var6)
    {
        super(var1, var2, var3, var4, var5, var6);
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft var1, int var2, int var3)
    {
        var1.renderEngine.bindTexture("/soebwyn/gui/but.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean var4 = var2 >= this.xPosition && var3 >= this.yPosition && var2 < this.xPosition + this.width && var3 < this.yPosition + this.height;
        int var5 = this.getHoverState(var4);
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + var5 * 20, this.width / 2, this.height);
        this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + var5 * 20, this.width / 2, this.height);
        this.mouseDragged(var1, var2, var3);
        FontRenderer var6 = var1.fontRenderer;
        char var7 = 59889;

        if (!this.enabled)
        {
            var7 = 59889;
        }
        else if (var4)
        {
            var7 = 59889;
        }

        this.drawCenteredString(var6, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, var7);
    }
}
