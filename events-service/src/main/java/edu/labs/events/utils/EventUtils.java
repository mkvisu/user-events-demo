package edu.labs.events.utils;

import org.apache.commons.lang.StringUtils;

import edu.labs.events.entities.Event;

public class EventUtils {
	public static void mapEventToEvent(Event source, Event destination) {
		if(StringUtils.isNotBlank(source.getName())) {
			destination.setName(source.getName());
		}
		if(StringUtils.isNotBlank(source.getCode())) {
			destination.setCode(source.getCode());
		}
		if(StringUtils.isNotBlank(source.getAddress())) {
			destination.setAddress(source.getAddress());
		}
		if(source.getEventDate() != null) {
			destination.setEventDate(source.getEventDate());
		}
	}
}
