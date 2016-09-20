package linhao.redridinghood.model.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public final class SharedWrapper {

    private static SharedPreferences sp;

    public static SharedPreferences with(@NonNull Context context,@NonNull String shareName){
      if (sp !=null){
          return sp;
      }else {
          sp=context.getSharedPreferences(shareName,Context.MODE_PRIVATE);
      }
      return sp;
    }


    public static void set(String key,String value){
      sp.edit().putString(key,value).apply();
    }


    public static void setBool(String key,boolean value){
        set(key,Boolean.toString(value));
    }

    public static String getString(String key){
        String value=sp.getString(key,null);
        return value;
    }

    public static boolean getBoolean(String key,boolean defaultValue){
       String value=sp.getString(key,null);
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)){
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }


}
