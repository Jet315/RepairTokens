package me.jet315.repairtokens.utils;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class NBTUtils {

    public static ItemStack setNBTData(ItemStack originalItem, HashMap<String, String> nbtTag) {

        //Item to convert to stack
        ItemStack itemToParse = originalItem.clone();

        Object nmsStack = getNMSItemStack(itemToParse);
        Object itemsNBTCompound = getNMSTag();

        for (String var : nbtTag.keySet()) {
            try {
                //When getting a method, you also have to specify it's parameters as class's:
                Method setStringMethod = itemsNBTCompound.getClass().getDeclaredMethod("setString",String.class,String.class);

                setStringMethod.invoke(itemsNBTCompound,var,nbtTag.get(var));

                Method setTagMethod = nmsStack.getClass().getDeclaredMethod("setTag",itemsNBTCompound.getClass());

                setTagMethod.invoke(nmsStack,itemsNBTCompound);


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        ItemStack itemStack = getItemStackFromNMS(nmsStack);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(originalItem.getItemMeta().getDisplayName());
        itemMeta.setLore(originalItem.getItemMeta().getLore());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static String getNBTValue(ItemStack item, String key) {
        Object nmsStack = getNMSItemStack(item);
        try {
            Object nbtCompound = nmsStack.getClass().getMethod("getTag").invoke(nmsStack);
            if(nbtCompound != null) {
                return (String) nbtCompound.getClass().getMethod("getString",String.class).invoke(nbtCompound,key);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "0";
    }


    private static ItemStack getItemStackFromNMS(Object itemStack){
        try {
            Class clazz = ReflectionUtils.getClass("org.bukkit.craftbukkit." + ReflectionUtils.serverVersion +".inventory.CraftItemStack");
            Method method = ReflectionUtils.getMethod(clazz,"asBukkitCopy");

            Object object = method.invoke(null,itemStack);

            return (ItemStack) object;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Object getNMSItemStack(ItemStack itemStack){
        try {
            Class clazz = ReflectionUtils.getClass("org.bukkit.craftbukkit." + ReflectionUtils.serverVersion +".inventory.CraftItemStack");
            Method method = ReflectionUtils.getMethod(clazz,"asNMSCopy");
            Object nmsItemStack = method.invoke(null,itemStack);
            return nmsItemStack;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Object getNMSTag(){
        try {
            return ReflectionUtils.getClass("net.minecraft.server." + ReflectionUtils.nmsVersion + ".NBTTagCompound").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
