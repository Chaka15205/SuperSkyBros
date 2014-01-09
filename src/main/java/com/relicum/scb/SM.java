package com.relicum.scb;

import com.relicum.scb.configs.*;
import com.relicum.scb.objects.inventory.StorageType;
import com.relicum.scb.types.SkyApi;
import com.relicum.scb.utils.SerializedLocation;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type SM.
 */
public class SM {

    private static SM instance;

    /**
     * Main Plugin Configs
     */
    private FileConfiguration config;

    /**
     * Default Data Folder
     */
    private File dataFolder;

    /**
     * The Lobby config.
     */
    private LobbyConfig lobbyConfig;


    private Lobby2Config lobby2Config;

    /**
     * The Sign config.
     */
    private SignConfig signConfig;

    /**
     * The Sign format config.
     */
    private SignFormat signFormatConfig;

    /**
     * The Spawn config.
     */
    private SpawnConfig spawnConfig;

    /**
     * The Arena config.
     */
    private ArenaConfig arenaConfig;


    /**
     * Is server a dedicated SSB Server
     */
    private boolean dedicated;

    /**
     * Blacklisted Worlds
     */
    private List<String> worldBlackListed = new ArrayList<>();

    /**
     * Worlds that are dedicated to SSB only
     */
    private List<String> ssbWorlds = new ArrayList<>();

    private StorageType storageType;
    /**
     * The Worlds Config.
     */
    private WorldConfig worldConfig;


    private boolean useWorldManagement;

    private boolean generateDefaultWorld;

    private List<String> adminMode = new ArrayList<>(5);

    /**
     * Instantiates a new SM.
     */
    public SM() {
        setup();
    }


    /**
     * Sets .
     */
    public void setup() {
        config = SkyApi.getSCB().getConfig();
        setStorageType(StorageType.valueOf(config.getString("storageType")));
        dataFolder = SkyApi.getSCB().getDataFolder();
        worldBlackListed.addAll(config.getStringList("ignoreWorlds"));
        ssbWorlds.addAll(config.getStringList("dedicatedSSBWorlds"));
        dedicated = config.getBoolean("dedicatedSSB");
        setgenerateDefaultWorld(config.getBoolean("generateDefaultWorld"));
        setUseWorldManagement(config.getBoolean("useWorldManager"));


        if (isUseWorldManagement()) {
            worldConfig = new WorldConfig("worlds.yml");
            worldConfig.getConfig().options().copyDefaults(true);
            worldConfig.saveDefaultConfig();


        }
        lobbyConfig = new LobbyConfig("lobby.yml");
        lobbyConfig.getConfig().options().copyDefaults(true);
        lobbyConfig.saveDefaultConfig();

        lobby2Config = new Lobby2Config("lobby2.yml");
        lobby2Config.getConfig().options().copyDefaults(true);
        lobby2Config.saveDefaultConfig();


        signConfig = new SignConfig("signs.yml");
        signConfig.getConfig().options().copyDefaults(true);
        signConfig.saveDefaultConfig();


        signFormatConfig = new SignFormat("signsText.yml");
        signFormatConfig.getConfig().options().copyDefaults(true);
        signFormatConfig.saveDefaultConfig();


        spawnConfig = new SpawnConfig("spawns.yml");
        spawnConfig.getConfig().options().copyDefaults(true);
        spawnConfig.saveDefaultConfig();

        arenaConfig = new ArenaConfig("arena.yml");
        arenaConfig.getConfig().options().copyDefaults(true);
        arenaConfig.saveDefaultConfig();

    }


    /**
     * Sets serialized world spawn location.
     *
     * @param location the location
     * @param name     the name
     */
    public void setSerializedWorldSpawnLocation(SerializedLocation location, String name) {

        worldConfig.getConfig().set("worlds." + name + ".spawnLocation", location);
        worldConfig.saveConfig();
    }

    /**
     * Gets lobby spawn.
     *
     * @return the lobby spawn
     */
    public Location getLobbySpawn() {
        return ((SerializedLocation) lobbyConfig.getConfig().get("lobby.box.spawn")).getLocation();
    }


    /**
     * Gets lobby config.
     *
     * @return the lobby config
     */
    public LobbyConfig getLobbyConfig() {
        return lobbyConfig;
    }


    /**
     * Sets lobby config.
     *
     * @param lobbyConfig the lobby config
     */
    public void setLobbyConfig(LobbyConfig lobbyConfig) {
        this.lobbyConfig = lobbyConfig;
    }


    /**
     * Gets lobby 2 config.
     *
     * @return the lobby 2 config
     */
    public Lobby2Config getLobby2Config() {
        return lobby2Config;
    }

    /**
     * Sets lobby 2 config.
     *
     * @param lobby2Config the lobby 2 config
     */
    public void setLobby2Config(Lobby2Config lobby2Config) {
        this.lobby2Config = lobby2Config;
    }

    /**
     * Gets arena config.
     *
     * @return the arena config
     */
    public ArenaConfig getArenaConfig() {
        return arenaConfig;
    }

    /**
     * Sets arena config.
     *
     * @param arenaConfig the arena config
     */
    public void setArenaConfig(ArenaConfig arenaConfig) {
        this.arenaConfig = arenaConfig;
    }


    /**
     * Gets spawn config.
     *
     * @return the spawn config
     */
    public SpawnConfig getSpawnConfig() {
        return spawnConfig;
    }


    /**
     * Sets spawn config.
     *
     * @param spawnConfig the spawn config
     */
    public void setSpawnConfig(SpawnConfig spawnConfig) {
        this.spawnConfig = spawnConfig;
    }


    /**
     * Gets sign format config.
     *
     * @return the sign format config
     */
    public SignFormat getSignFormatConfig() {
        return signFormatConfig;
    }


    /**
     * Sets sign format config.
     *
     * @param signFormatConfig the sign format config
     */
    public void setSignFormatConfig(SignFormat signFormatConfig) {
        this.signFormatConfig = signFormatConfig;
    }


    /**
     * Gets sign config.
     *
     * @return the sign config
     */
    public SignConfig getSignConfig() {
        return signConfig;
    }


    /**
     * Sets sign config.
     *
     * @param signConfig the sign config
     */
    public void setSignConfig(SignConfig signConfig) {
        this.signConfig = signConfig;
    }


    /**
     * Gets world config.
     *
     * @return the world config
     */
    public WorldConfig getWorldConfig() {
        return worldConfig;
    }


    /**
     * Sets world config.
     *
     * @param worldConfig the world config
     */
    public void setWorldConfig(WorldConfig worldConfig) {
        this.worldConfig = worldConfig;
    }

    /**
     * Is use world management.
     *
     * @return the boolean
     */
    public boolean isUseWorldManagement() {
        return useWorldManagement;
    }

    /**
     * Sets use world management.
     *
     * @param useWorldManagement the use world management
     */
    public void setUseWorldManagement(boolean useWorldManagement) {
        this.useWorldManagement = useWorldManagement;
    }

    /**
     * Is generate default world.
     *
     * @return the boolean
     */
    public boolean isGenerateDefaultWorld() {
        return generateDefaultWorld;
    }

    /**
     * Sets default world.
     *
     * @param generateDefaultWorld the generate default world
     */
    public void setgenerateDefaultWorld(boolean generateDefaultWorld) {
        this.generateDefaultWorld = generateDefaultWorld;
    }

    /**
     * Black listed.
     *
     * @return the list
     */
    public List<String> blackListed() {
        return worldBlackListed;
    }


    /**
     * Gets ssb worlds.
     *
     * @return the ssb worlds
     */
    public List<String> getSsbWorlds() {
        return ssbWorlds;
    }

    /**
     * Is dedicated.
     *
     * @return the boolean
     */
    public boolean isDedicated() {
        return dedicated;
    }

    /**
     * Add world to white list.
     *
     * @param w the w
     * @return the boolean
     */
    public boolean addWorldToWhiteList(String w) {

        if (blackListed().contains(w)) {
            blackListed().remove(w);
            config.set("ignoreWorlds", blackListed());
        }
        try {
            if (!ssbWorlds.contains(w)) {
                ssbWorlds.add(w);
                config.set("dedicatedSSBWorlds", ssbWorlds);
                SkyApi.getCommandManager().resetWhiteList();
                SkyApi.getCMsg().INFO("World " + w + " has been successfully added to the white list");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets plugin config.
     *
     * @return the plugin config
     */
    public FileConfiguration getPluginConfig() {
        return config;
    }

    /**
     * Add world to black list.
     *
     * @param w the w
     * @return the boolean
     */
    public boolean addWorldToBlackList(String w) {
        if (ssbWorlds.contains(w)) {
            ssbWorlds.remove(w);
            config.set("dedicatedSSBWorlds", ssbWorlds);

        }
        try {
            if (!blackListed().contains(w)) {
                blackListed().add(w);
                config.set("ignoreWorlds", blackListed());
                SkyApi.getCommandManager().resetWhiteList();

                SkyApi.getCMsg().INFO("World " + w + " has been successfully added to the white list");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove world form black and white list.
     *
     * @param w the w
     * @return the boolean
     */
    public boolean removeWorldFormBlackAndWhiteList(String w) {


        try {
            if (ssbWorlds.contains(w)) {
                ssbWorlds.remove(w);
            }
            if (blackListed().contains(w)) {
                blackListed().remove(w);
            }

            config.set("dedicatedSSBWorlds", ssbWorlds);
            config.set("ignoreWorlds", blackListed());

            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Gets admin mode.
     *
     * @return the admin mode
     */
    public List<String> getAdminMode() {
        return adminMode;
    }

    /**
     * Sets admin mode.
     *
     * @param player the player
     */
    public void setAdminMode(String player) {
        this.adminMode.add(player);
    }

    /**
     * Gets plugins storage type.
     *
     * @return the storage type
     */
    public StorageType getStorageType() {
        return storageType;
    }

    /**
     * Sets storage type.
     *
     * @param storageType the storage type
     */
    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    /**
     * Load file.
     *
     * @param file the file
     */
    public void loadFile(File file) {

        File t = file;


        SkyApi.getCMsg().INFO("Writing new file: " + t.getAbsolutePath());


        if (!t.exists()) {

            try {
                t.createNewFile();
                FileWriter out = new FileWriter(t);
                System.out.println(file);
                InputStream is = getClass().getResourceAsStream("/" + file);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    out.write(line + "\n");
                    System.out.println(line);
                }
                out.flush();
                is.close();
                isr.close();
                br.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
