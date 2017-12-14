package org.marker.mushroom.core.channel;

import org.marker.mushroom.beans.Channel;

import java.util.List;


/**
 * 栏目树
 * (提供将数据库数据转换为栏目树功能)
 * @author marker
 * */
public class ChannelTree {

	
	public static ChannelItem foreach(Channel currentChannel, List<Channel> list){
		ChannelItem item = new ChannelItem();
		item.setChannel(currentChannel);
		//如果是当前栏目的子栏目
		list.stream().filter(c -> c.getPid() == currentChannel.getId()).forEach(c -> {//如果是当前栏目的子栏目
			ChannelItem item2 = foreach(c, list);
			item.child.add(item2);
		});
		return item;
	}
	
}
