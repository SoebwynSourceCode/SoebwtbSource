package net.minecraft.src;

public class ItemVin extends ItemFood
{
    public ItemVin(int var1, int var2, float var3, boolean var4)
    {
        super(var1, var2, var3, var4);
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack var1)
    {
        return EnumAction.drink;
    }
}
