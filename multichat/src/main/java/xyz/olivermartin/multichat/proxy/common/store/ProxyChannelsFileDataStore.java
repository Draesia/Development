package xyz.olivermartin.multichat.proxy.common.store;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import xyz.olivermartin.multichat.proxy.common.MultiChatProxyPlatform;
import xyz.olivermartin.multichat.proxy.common.MultiChatProxyPlayer;

public abstract class ProxyChannelsFileDataStore extends ProxyAbstractFileDataStore implements ProxyChannelsDataStore {

	public ProxyChannelsFileDataStore(MultiChatProxyPlatform platform, File path, String filename) {
		super(platform, path, filename);
	}

	// MAP PLAYER UUIDS TO THE ID OF THEIR CHANNEL
	private Map<UUID, String> currentChannels;

	// MAP CHANNEL ID TO LIST OF PLAYERS WHO HAVE HIDDEN IT
	private Map<String, Set<UUID>> hiddenChannels;

	// MAP JOINABLE CHANNEL ID TO LIST OF PLAYERS IN WHITELIST / BLACKLIST
	private Map<String, Set<UUID>> joinedChannels;

	@Override
	public Optional<String> getAbstractedCurrentChannel(UUID uuid) {
		if (currentChannels.containsKey(uuid)) return Optional.of(currentChannels.get(uuid));
		return Optional.empty();
	}

	@Override
	public Optional<String> getCorrectCurrentChannel(MultiChatProxyPlayer player) {
		if (currentChannels.containsKey(player.getUniqueId())) {
			String channelId = currentChannels.get(player.getUniqueId());
			if (channelId.equalsIgnoreCase("local")) {
				String playerServer = player.getServer().toLowerCase();
				return Optional.of(channelId + ":" + playerServer);
			} else {
				return Optional.of(channelId);
			}
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void setCurrentChannel(UUID uuid, String id) {
		currentChannels.put(uuid, id.toLowerCase());
	}

	@Override
	public Set<UUID> getHiddenViewers(String id) {
		if (hiddenChannels.containsKey(id.toLowerCase())) return hiddenChannels.get(id.toLowerCase());
		return Collections.emptySet();
	}

	@Override
	public boolean hasHiddenChannel(UUID uuid, String channelId) {
		if (hiddenChannels.containsKey(channelId.toLowerCase())) {
			return hiddenChannels.get(channelId.toLowerCase()).contains(uuid);
		} else {
			return false;
		}
	}

	@Override
	public void hideChannel(UUID uuid, String id) {

		Set<UUID> uuids;

		if (hiddenChannels.containsKey(id.toLowerCase())) {
			uuids = hiddenChannels.get(id.toLowerCase());
		} else {
			uuids = new HashSet<UUID>();
		}

		uuids.add(uuid);
		hiddenChannels.put(id.toLowerCase(), uuids);

	}

	@Override
	public void showChannel(UUID uuid, String id) {

		Set<UUID> uuids;

		if (hiddenChannels.containsKey(id.toLowerCase())) {
			uuids = hiddenChannels.get(id.toLowerCase());
		} else {
			return;
		}

		uuids.remove(uuid);
		hiddenChannels.put(id.toLowerCase(), uuids);

	}

	/**
	 * @return the currentChannels
	 */
	protected Map<UUID, String> getCurrentChannels() {
		return currentChannels;
	}

	/**
	 * @return the hiddenChannels
	 */
	protected Map<String, Set<UUID>> getHiddenChannels() {
		return hiddenChannels;
	}

	/**
	 * @return the hiddenChannels
	 */
	protected Map<String, Set<UUID>> getJoinedChannels() {
		return joinedChannels;
	}

	/**
	 * @param currentChannels the currentChannels to set
	 */
	protected void setCurrentChannels(Map<UUID, String> currentChannels) {
		this.currentChannels = currentChannels;
	}

	/**
	 * @param hiddenChannels the hiddenChannels to set
	 */
	protected void setHiddenChannels(Map<String, Set<UUID>> hiddenChannels) {
		this.hiddenChannels = hiddenChannels;
	}

	/**
	 * @param hiddenChannels the hiddenChannels to set
	 */
	protected void setJoinedChannels(Map<String, Set<UUID>> joinedChannels) {
		this.joinedChannels = joinedChannels;
	}

	@Override
	public void addToJoinedChannelList(UUID uuid, String id) {

		Set<UUID> uuids;

		if (this.joinedChannels.containsKey(id.toLowerCase())) {
			uuids = joinedChannels.get(id.toLowerCase());
		} else {
			uuids = new HashSet<UUID>();
		}

		uuids.add(uuid);
		this.joinedChannels.put(id.toLowerCase(), uuids);
	}

	@Override
	public void removeFromJoinedChannelList(UUID uuid, String id) {

		Set<UUID> uuids;

		if (this.joinedChannels.containsKey(id.toLowerCase())) {
			uuids = joinedChannels.get(id.toLowerCase());
		} else {
			return;
		}

		uuids.remove(uuid);
		this.joinedChannels.put(id.toLowerCase(), uuids);

	}

	@Override
	public Set<UUID> getJoinedChannelList(String id) {
		if (joinedChannels.containsKey(id.toLowerCase())) return joinedChannels.get(id.toLowerCase());
		return Collections.emptySet();
	}

	@Override
	public boolean isInJoinedChannelList(UUID uuid, String id) {

		Set<UUID> uuids;

		if (this.joinedChannels.containsKey(id.toLowerCase())) {
			uuids = joinedChannels.get(id.toLowerCase());
		} else {
			return false;
		}

		return uuids.contains(uuid);
	}

}