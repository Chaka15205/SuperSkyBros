package com.relicum.scb;

import com.relicum.scb.classes.Creeper;
import com.relicum.scb.commands.CommandManager;
import com.relicum.scb.commands.CommandManagerFirstJoin;
import com.relicum.scb.commands.DebugManager;
import com.relicum.scb.configs.*;
import com.relicum.scb.listeners.*;
import com.relicum.scb.utils.Helper;
import com.relicum.scb.utils.MessageManager;
import com.relicum.scb.we.WorldEditPlugin;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;


/**
 * Main SSB Class
 *
 * @author Relicum
 * @version 0.9
 */
public class SCB extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");

    /**
     * The constant MM.
     */
    public static MessageManager MM;

    /**
     * Holds a static p of itself as a JavaPlugin object
     */
    @SuppressWarnings("StaticVariableOfConcreteClass")
    public static SCB p;

    /**
     * The Group spawn file.
     */
    public File groupSpawnFile = null;

    /**
     * The Group spawn.
     */
    public FileConfiguration groupSpawn = null;

    /**
     * The LBS.
     */
    public LobbyManager LBS;

    /**
     * Lobby Config Object
     */
    public LobbyConfig LBC;

    /**
     * Arena Config Object
     */
    public ArenaConfig ARC;

    public ArenaManager ARM;

    /**
     * Spawn Config Object
     */
    public SpawnConfig SPC;

    /**
     * BaseSign Config Manager
     */
    public SignConfig SNC;

    /**
     * The SignManager
     */
    public SignManager SNM;

    /**
     * The BaseSign Formatter Config.
     */
    public SignFormat SFM;

    /**
     * The Creeper.
     */
    public Creeper co;

    public boolean saveOnDisable = true;

    protected ArrayList<Permission> plist = new ArrayList<>();

    protected PluginManager pm = Bukkit.getServer().getPluginManager();


    /**
     * Gets p.
     *
     * @return the p
     */
    public static SCB getInstance() {
        return p;
    }


    /**
     * Get Instance of MessageManager
     *
     * @return MessageManager message manager
     */
    public static MessageManager getMessageManager() {

        return MM;
    }


    public static WorldEditPlugin getWorldEdit() {

        Plugin WE = BukkitInterface.getServer().getPluginManager().getPlugin("WorldEdit");

        if ((WE instanceof WorldEditPlugin)) {
            return (WorldEditPlugin) WE;

        }

        return null;

    }


    public static Economy econ = null;

    public static Permission perms = null;

    public static Chat chat = null;


    /**
     * On load. Registers any ConfigurationSerializable files at onLoad Before other things have started to load
     */
    public void onLoad() {


    }


    /**
     * On enable.
     */
    @Override
    public void onEnable() {

        p = this;


		/*try {
            @SuppressWarnings("LocalVariableOfConcreteClass")
            MetricsLite metrics = new MetricsLite(p);
			metrics.start();
		} catch (IOException e) {

			System.out.println(e.getStackTrace().toString());
		}*/


        BukkitInterface.setServer(this.getServer());

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        this.reloadConfig();


        setupPermissions();
        setupChat();
        setupEconomy();

        if (SCB.getInstance().getConfig().getBoolean("firstRun")) {

            CommandExecutor cm = new CommandManagerFirstJoin(p);

            p.getCommand("ssba").setExecutor(cm);
            p.getCommand("ssba").setPermissionMessage("You do not have permission to run this command");
            p.pm.registerEvents(new FirstRun(this), this);


        } else {

            getServer().getScheduler().scheduleSyncDelayedTask(SCB.getInstance(), new Startup(), 15L);
        }


    }


    /**
     * On disable.
     */
    @Override
    public void onDisable() throws NullPointerException {

        if (((!this.getConfig().getBoolean("firstRun")) && (this.getConfig().getBoolean("firstRunDone"))) || (!this.saveOnDisable)) {
            this.getConfig().set("firstRun", false);
            this.getConfig().set("firstRunDone", true);
            this.saveConfig();

        } else {


            try {
                LBC.saveConfig();
                ARC.saveConfig();
                SPC.saveConfig();
                SNC.saveConfig();
                SFM.saveConfig();
                this.saveConfig();
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }

        }


    }


    public void loadLobbyEvents() {

        if (this.getConfig().getBoolean("enableLobbyProtection")) {
            System.out.println("Loading Lobby Events in SSB");
            p.pm.registerEvents(new LobbyBlockBreak(p), p);
            p.pm.registerEvents(new LobbyBlockPlace(p), p);
            //p.pm.registerEvents(new PlayerBlockDamage(),p);

        }

    }


    public void unloadLobbyEvents() {

        LobbyBlockBreak bl = new LobbyBlockBreak(this);
        LobbyBlockPlace bp = new LobbyBlockPlace(this);

        BlockBreakEvent.getHandlerList().unregister(bl);

        //BlockPlaceEvent.getHandlerList().bake();

        System.out.println("UnLoading Lobby Events in SSB");
    }


    protected class Startup implements Runnable {

        SCB p = SCB.getInstance();


        /**
         * When an object implementing interface <code>Runnable</code> is used to create a thread, starting the thread
         * causes the object's <code>run</code> method to be called in that separately executing thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {


            if (!p.getConfig().getBoolean("firstRun")) {
                //p.groupSpawn = new YamlConfiguration();

            }


            p.LBC = new LobbyConfig("lobby.yml");
            p.LBC.getConfig().options().copyDefaults(true);
            p.LBC.saveConfig();


            p.SPC = new SpawnConfig("spawns.yml");
            p.SPC.getConfig().options().copyDefaults(true);
            p.SPC.saveConfig();

            p.SNC = new SignConfig("signs.yml");
            p.SNC.getConfig().options().copyDefaults(true);
            p.SNC.saveConfig();

            p.ARC = new ArenaConfig("arena.yml");
            p.ARC.getConfig().options().copyDefaults(true);
            p.ARC.saveConfig();
            p.ARM = new ArenaManager(p);

            p.SFM = new SignFormat("signsText.yml");
            p.SFM.getConfig().options().copyDefaults(true);
            p.SFM.saveDefaultConfig();

            SettingsManager.getInstance().setup(p);
            MM = new MessageManager(p);
            if (!p.getConfig().getBoolean("enable")) {
                getLogger().info("SCB is being disabled due to it enable being false in config.yml");
                p.pm.disablePlugin(p);
                return;

            }


            p.LBS = new LobbyManager();
            p.pm.registerEvents(new PlayerJoin(p), p);
            p.pm.registerEvents(new PlayerQuit(p), p);
            p.pm.registerEvents(new PlayerLoginNoPerm(p), p);

            //p.pm.registerEvents(new ArenaChangeStatus(p), p);

            p.loadLobbyEvents();


            p.SNM = new SignManager();

            //noinspection LocalVariableOfConcreteClass
            CommandExecutor cm = new CommandManager(p);
            p.getCommand("ssb").setExecutor(cm);
            p.getCommand("ssba").setExecutor(cm);
            p.getCommand("ssb").setPermissionMessage(MM.getNoPerm());
            p.getCommand("ssba").setPermissionMessage(MM.getNoPerm());


            //TODO Must refactor out this Helper Class
            Helper.getInstance().setup(p);

            //Debug Commands
            if (p.getConfig().getBoolean("debugCommands")) {

                p.getCommand("vList").setExecutor(new DebugManager(p));
                p.getCommand("vList").setPermissionMessage("Only runs from console");


                System.out.println("Debug Commands installed");
            }

            Set<org.bukkit.permissions.Permission> ap = p.pm.getPermissions();
            for ( org.bukkit.permissions.Permission pme : ap ) {
                System.out.println(pme.getName());
            }

        }


        private void fileExists(String fi) {

            File file = new File(getDataFolder(), fi);
            FileConfiguration fCon;


            try {
                if (!file.exists()) {
                    file.createNewFile();
                    fCon = YamlConfiguration.loadConfiguration(SCB.getInstance().getResource(fi));
                    fCon.save(file);
                }
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }

        }
    }


    private void setupEconomy() {

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

        if (rsp != null) {
            econ = rsp.getProvider();
            log.info("Successfully Hooked into Economy Plugin");
        } else {
            log.warning("Vault could not hook into Economy Plugin");
        }

    }


    private void setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp != null) {
            chat = rsp.getProvider();
            log.info("Successfully Hooked into Chat Plugin");
        } else {
            log.warning("Vault could not hook into Chat Plugin");
        }


    }


    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);


        if (rsp != null) {
            perms = rsp.getProvider();
            log.info("Successfully Hooked into Permissions Plugin");
        } else {
            log.warning("Vault could not hook into Permissions Plugin");
        }

    }
}
