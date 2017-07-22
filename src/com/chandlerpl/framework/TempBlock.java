package com.chandlerpl.framework;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;

public class TempBlock {

	public static Map<Block, TempBlock> instances = new ConcurrentHashMap<Block, TempBlock>();
	public static final PriorityQueue<TempBlock> REVERT_QUEUE = new PriorityQueue<>(100, new Comparator<TempBlock>() {
		@Override
		public int compare(TempBlock t1, TempBlock t2) {
			return (int) (t1.revertTime - t2.revertTime);
		}
	});

	private Block block;
	private Material newtype;
	private byte newdata;
	private BlockState state;
	private long revertTime;
	private boolean inRevertQueue;

	@SuppressWarnings("deprecation")
	public TempBlock(Block block, Material newtype, byte newdata) {
		this.block = block;
		this.newdata = newdata;
		this.newtype = newtype;
		if (instances.containsKey(block)) {
			TempBlock temp = instances.get(block);
			if (newtype != temp.newtype) {
				temp.block.setType(newtype);
				temp.newtype = newtype;
			}
			if (newdata != temp.newdata) {
				temp.block.setData(newdata);
				temp.newdata = newdata;
			}
			state = temp.state;
			instances.put(block, temp);
		} else {
			state = block.getState();
			instances.put(block, this);
			block.setType(newtype);
			block.setData(newdata);
		}
		if (state.getType() == Material.FIRE)
			state.setType(Material.AIR);
	}

	public static TempBlock get(Block block) {
		if (isTempBlock(block))
			return instances.get(block);
		return null;
	}

	public static boolean isTempBlock(Block block) {
		return block != null ? instances.containsKey(block) : false;
	}

	public static boolean isTouchingTempBlock(Block block) {
		BlockFace[] faces = { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN };
		for (BlockFace face : faces) {
			if (instances.containsKey(block.getRelative(face)))
				return true;
		}
		return false;
	}

	public static void removeAll() {
		for (Block block : instances.keySet()) {
			revertBlock(block, Material.AIR);
		}
		for (TempBlock tempblock : REVERT_QUEUE) {
			tempblock.revertBlock();
		}
	}

	public static void removeBlock(Block block) {
		instances.remove(block);
	}

	@SuppressWarnings("deprecation")
	public static void revertBlock(Block block, Material defaulttype) {
		if (instances.containsKey(block)) {
			instances.get(block).revertBlock();
		} else {
			if ((defaulttype == Material.LAVA || defaulttype == Material.STATIONARY_LAVA) && isAdjacentToThreeOrMoreSources(block)) {
				block.setType(Material.LAVA);
				block.setData((byte) 0x0);
			} else if ((defaulttype == Material.WATER || defaulttype == Material.STATIONARY_WATER) && isAdjacentToThreeOrMoreSources(block)) {
				block.setType(Material.WATER);
				block.setData((byte) 0x0);
			} else {
				block.setType(defaulttype);
			}
		}
		// block.setType(defaulttype);
	}
	
	public static boolean isWater(Material material) {
		return material == Material.WATER || material == Material.STATIONARY_WATER || material == Material.ICE || material == Material.FROSTED_ICE || material == Material.PACKED_ICE;
	}

	public static boolean isAdjacentToThreeOrMoreSources(Block block) {
		if (block == null || (TempBlock.isTempBlock(block))) {
			return false;
		}
		int sources = 0;
		byte full = 0x0;
		BlockFace[] faces = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH };
		for (BlockFace face : faces) {
			Block blocki = block.getRelative(face);
			if ((blocki.getType() == Material.LAVA || blocki.getType() == Material.STATIONARY_LAVA) && blocki.getData() == full) {
				sources++;
			}
			if ((isWater(blocki.getType()) && blocki.getData() == full)) {
				sources++;
			}
		}
		return sources >= 2;
	}
	
	public Block getBlock() {
		return block;
	}

	public Location getLocation() {
		return block.getLocation();
	}

	public BlockState getState() {
		return state;
	}

	public long getRevertTime() {
		return revertTime;
	}

	public void setRevertTime(long revertTime) {
		if (inRevertQueue) {
			REVERT_QUEUE.remove(this);
		}
		this.inRevertQueue = true;
		this.revertTime = revertTime + System.currentTimeMillis();
		REVERT_QUEUE.add(this);
	}

	public void revertBlock() {
		state.update(true);
		instances.remove(block);
	}

	public void setState(BlockState newstate) {
		state = newstate;
	}

	public void setType(Material material) {
		setType(material, newdata);
	}

	@SuppressWarnings("deprecation")
	public void setType(Material material, byte data) {
		newtype = material;
		newdata = data;
		block.setType(material);
		block.setData(data);
	}

	public static void startReversion() {
		new BukkitRunnable() {
			@Override
			public void run() {
				long currentTime = System.currentTimeMillis();
				while (!REVERT_QUEUE.isEmpty()) {
					TempBlock tempBlock = REVERT_QUEUE.peek();
					if (currentTime >= tempBlock.revertTime) {
						REVERT_QUEUE.poll();
						tempBlock.revertBlock();
						//long finish = System.currentTimeMillis();
						//Bukkit.broadcastMessage(String.valueOf(finish - currentTime));
					} else {
						break;
					}
				}
			}
		}.runTaskTimer(Main.instance, 0, 1);

	}
}