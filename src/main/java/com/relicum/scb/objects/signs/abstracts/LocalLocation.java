package com.relicum.scb.objects.signs.abstracts;

import com.relicum.scb.objects.signs.interfaces.ISignLocation;
import com.relicum.scb.objects.signs.interfaces.ISignLocationExtra;
import com.relicum.scb.objects.signs.interfaces.ISignWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;


/**
 * The type LocalLocation.
 *
 * @throws
 */
public class LocalLocation implements ISignLocation, ISignLocationExtra, ISignWorld {


    private double X;

    private double Y;

    private double Z;

    private float Yaw = 0.0F;

    private float Pitch = 0.0F;

    private String world;

    private UUID worldUUID;


    @Override
    public double getX() {
        return this.X;
    }

    public void setX(double x) {
        this.X = x;
    }

    @Override
    public double getY() {
        return this.Y;
    }

    public void setY(double y) {
        this.Y = y;
    }

    @Override
    public double getZ() {
        return this.Z;
    }

    public void setZ(double z) {
        this.Z = z;
    }

    @Override
    public float getYaw() {
        return this.Yaw;
    }

    public void setYaw(float yaw) {
        Yaw = yaw;
    }

    @Override
    public float getPitch() {
        return this.Pitch;
    }

    public void setPitch(float pitch) {
        Pitch = pitch;
    }

    @Override
    public String getWorldName() {
        return this.world;
    }

    @Override
    public World getWorld() {
        return Bukkit.getWorld(this.world);
    }

    /**
     * Sets world of the Location passing world name as a String
     *
     * @param world the world the location is in.
     */
    public void setWorld(String world) {
        this.world = world;
        this.worldUUID = Bukkit.getWorld(world).getUID();

    }

    @Override
    public UUID getWorldUniqueID() {
        return this.worldUUID;
    }

    /**
     * Get Bukkit Location.If yaw was set it will use the value if not it won't
     *
     * @return the location as a Bukkit Location
     */
    public Location getLocation() {

        if (this.Yaw == 0.0F) {

            return new Location(this.getWorld(), this.X, this.Y, this.Z);
        }

        return new Location(this.getWorld(), this.X, this.Y, this.Z, this.Yaw, this.Pitch);
    }


}
