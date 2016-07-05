package com.by.dalitek.modelhouse.domain;
/**
 *
 * @author zhxr
 * @Description TODO
 * @Name LightDomain.java
 * @param
 * @time: 2015年2月11日 下午3:45:20
 */
public class LightDomain {

	private String name;

	private String command_open;

	private String command_off;

	private int type;

	public LightDomain() {
		// TODO Auto-generated constructor stub
	}

	public LightDomain(String name, String command_open, String command_off,
					   int type) {
		super();
		this.name = name;
		this.command_open = command_open;
		this.command_off = command_off;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommand_open() {
		return command_open;
	}

	public void setCommand_open(String command_open) {
		this.command_open = command_open;
	}

	public String getCommand_off() {
		return command_off;
	}

	public void setCommand_off(String command_off) {
		this.command_off = command_off;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
