package game.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.entities.mobiles.Player;
import game.entities.mobiles.Tank;
import game.entities.statics.Block;
import game.entities.statics.BlockType;
import game.level.*;

import java.io.*;
import java.util.List;

public class DataConverter {
    private static final File SAVES = new File("C:\\Users\\stran\\IdeaProjects\\Third\\BattleCity\\src\\main\\resources\\json\\saves");
    public static final File LEVELS = new File("C:\\Users\\stran\\IdeaProjects\\Third\\BattleCity\\src\\main\\resources\\json\\levels");

    private static String serialize(Level level) {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(level);
    }

    private static Level deserialize(String data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Level level = gson.fromJson(data, Level.class);

        Block[][] map = level.getMap();
        restoreTanks(level.getEnemies(), map);
        restorePlayer(level.getPlayer(), map);
        restoreBase(level);

        return level;
    }

    private static void restoreBase(Level level) {
        for (Block[] blocks : level.getMap()) {
            for (Block block : blocks) {
                if (block.getType() == BlockType.BASE) {
                    level.getBase().add(block);
                }
            }
        }
    }

    private static void restorePlayer(Player player, Block[][] map) {
        if (player != null) {
            restore(player, map);
            player.newEvents();
        }
    }
    private static void restoreTanks(List<Tank> tanks, Block[][] map) {
        for (Tank tank : tanks) {
            restore(tank, map);
        }
    }

    private static void restore(Tank tank, Block[][] map) {
        Location location = new Location(tank.getX(), tank.getY(), map);
        location.add(tank);
        if (tank.getBullet().isFly()) {
            location.add(tank.getBullet());
        }
    }

    public static Level readSave(String levelName) {
        return read(SAVES, levelName);
    }

    public static Level readLevel(String levelName) {
        return read(LEVELS, levelName);
    }

    public static void writeSave(Level level) {
        writeFile(level.getName(), serialize(level), SAVES);
    }

    public static void writeLevel(Level level) {
        writeFile(level.getName(), serialize(level), LEVELS);
    }

    private static Level read(File directory, String levelName) {
        File file = getJson(directory, levelName);
        return file.exists() ? read(file) : null;
    }

    public static Level read(File file) {
        Level result = deserialize(readFile(file));
        result.setName(file.getName().substring(0, file.getName().lastIndexOf(".")));
        return result;
    }

    private static String readFile(File file) {
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));

            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }

            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private static void writeFile(String levelName, String data, File directory) {
        try {
            File save = getJson(directory, levelName);

            checkExists(directory, levelName);

            save.createNewFile();
            FileWriter fw = new FileWriter(save);
            fw.write(data);
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void checkExists(File directory, String name) {
        File[] list = directory.listFiles();
        if (list != null) {
            for (File file : list) {
                if (file.getName().equals(name)) {
                    file.deleteOnExit();
                    return;
                }
            }
        }
    }

    public static void tryDeleteSave(String levelName) {
        File file = getJson(SAVES, levelName);
        if (file.exists()) {
            file.delete();
        }
    }

    private static File getJson(File directory, String levelName) {
        return new File(directory.getAbsolutePath() + "\\" + levelName + ".json");
    }
}
