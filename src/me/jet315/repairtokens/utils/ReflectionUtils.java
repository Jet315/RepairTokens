package me.jet315.repairtokens.utils;

import org.bukkit.Bukkit;

import java.lang.reflect.Method;

public class ReflectionUtils {

    /**
     * A lot of reflection is done in other classes, this is just a few methods
     */
    public static String nmsVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
    public static String serverVersion = Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1);


    public static Method getMethod(Class clazz, String methodName){
        for(Method method : clazz.getDeclaredMethods()){
            if(method.getName().equalsIgnoreCase(methodName)){
                return method;
            }
        }
        return null;
    }

    public static Class getClass(String clazz){
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
