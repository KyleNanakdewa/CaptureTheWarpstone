package com.kylenanakdewa.ctw;

import java.util.HashMap;
import java.util.Map;

import com.kylenanakdewa.core.CorePlugin;
import com.kylenanakdewa.core.realms.RealmProvider;
import com.kylenanakdewa.warpstones.Warpstone;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Capture The Warpstone
 * <p>
 * A minigame about capturing and holding Warpstones for your realm.
 * Made to show off what Warpstones can do.
 * @author Kyle Nanakdewa
 */
public final class CTWPlugin extends JavaPlugin {
	private static CTWPlugin plugin;

	/** The WarpstoneCaptureData. */
	private static Map<Warpstone,WarpstoneCaptureData> warpstoneData = new HashMap<Warpstone,WarpstoneCaptureData>();


	@Override
	public void onEnable() {
		plugin = this;
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new CTWListener(), this);
	}

	/**
	 * Gets the World that CTW is enabled on. If null, CTW is enabled on all worlds.
	 * @return the world for CTW, or null if all worlds are enabled
	 */
	public static World getCTWWorld(){
		String worldName = plugin.getConfig().getString("world");
		return worldName==null ? null : Bukkit.getWorld(worldName);
	}

	/**
	 * Gets the RealmProvider for the server.
	 * @return the RealmProvider
	 */
	static RealmProvider getRealmProvider(){
		return CorePlugin.getServerRealmProvider();
	}


	/**
	 * Whether teleportation should be blocked on the CTW world.
	 * @return true if teleportation should be blocked
	 */
	static boolean isTeleportationBlocked(){
		return plugin.getConfig().getBoolean("block-teleportation");
	}

	/**
	 * Gets the base time required to capture a Warpstone, in seconds.
	 * @return the base cap time, in seconds
	 */
	static int getBaseCapTime(){
		return plugin.getConfig().getInt("base-cap-time");
	}
	/**
	 * Gets the percentage that each additional realmmate should reduce the remaining cap time by.
	 * @return the percentage of remaining time to remove, with each additional player capping
	 */
	static double getCapTimeReduction(){
		return plugin.getConfig().getDouble("cap-time-reduction-percentage");
	}
	/**
	 * Gets the max distance a player can move from a Warpstone before they stop capping it.
	 * @return the max distance for capping a warpstone, in blocks
	 */
	static double getMaxCapDistance(){
		return plugin.getConfig().getDouble("max-cap-distance");
	}


	/**
	 * Gets the capture data for a Warpstone.
	 * @param warpstone the Warpstone to retrieve for
	 * @return the WarpstoneCaptureData
	 */
	public static WarpstoneCaptureData getWarpstoneCaptureData(Warpstone warpstone){
		WarpstoneCaptureData data = warpstoneData.get(warpstone);
		if(data==null){
			data = new WarpstoneCaptureData(warpstone, plugin);
			warpstoneData.put(warpstone, data);
		}
		return data;
	}

}