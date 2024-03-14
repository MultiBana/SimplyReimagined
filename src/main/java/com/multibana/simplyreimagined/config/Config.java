package com.multibana.simplyreimagined.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Config {
    public static final String FILE_NAME = "simply_reimagined.properties";


    public static Map<String, Boolean> rules = new HashMap<>();


    public static void createIfAbsent()
    {
        File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), FILE_NAME);
        if (file.exists()) return;

        try
        {
            var writer = new PrintWriter(file);
            writer.println("# change to false to disable slowly losing hunger from walking");
            writer.println("enable_walk_hunger:true");
            writer.println("# change to false to disable losing hunger from jumping");
            writer.println("enable_jump_hunger:true");
            writer.println("# change to false to disable sprint-swim");
            writer.println("enable_swimming:true");
            writer.println("# change to false to disable horse-riding to break leaves you collide with");
            writer.println("enable_horse_leaf_breaking:true");
            writer.println("# change to false to disable power and infinity incompatibility");
            writer.println("infinity_power_incompatible:true");
            writer.println("# change to true to have dolphins spawn");
            writer.println("i_like_dolphins:false");
            writer.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void reload()
    {
        createIfAbsent();
        File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), FILE_NAME);

        try
        {
            var reader = new Scanner(file);
            rules.clear();
            while (reader.hasNextLine())
            {
                var line = reader.nextLine();
                if (line.length() == 0 || line.startsWith("#")) continue;
                var result = Arrays.stream(line.split(":")).map(s -> s.replace(" ", "")).toArray(String[]::new);

                if (result[1].equalsIgnoreCase("true") || result[1].equalsIgnoreCase("false")) {
                    rules.put(result[0], Boolean.valueOf(result[1]));
                } else {
                    rules.put(result[0], true);
                }
            }
            reader.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
