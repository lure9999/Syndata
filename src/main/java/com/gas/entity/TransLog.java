package com.gas.entity;

import lombok.Data;

@Data
public class TransLog {

	private String SYS_CHANGE_VERSION;

	private String SYS_CHANGE_CREATION_VERSION;

	private String SYS_CHANGE_OPERATION;

	private String SYS_CHANGE_COLUMNS;

	private String SYS_CHANGE_CONTEXT;

	private String ID;

	private String TABLE_NAME;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((SYS_CHANGE_OPERATION == null) ? 0 : SYS_CHANGE_OPERATION.hashCode());
		result = prime * result + ((SYS_CHANGE_VERSION == null) ? 0 : SYS_CHANGE_VERSION.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransLog other = (TransLog) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (SYS_CHANGE_OPERATION == null) {
			if (other.SYS_CHANGE_OPERATION != null)
				return false;
		} else if (!SYS_CHANGE_OPERATION.equals(other.SYS_CHANGE_OPERATION))
			return false;
		if (SYS_CHANGE_VERSION == null) {
			if (other.SYS_CHANGE_VERSION != null)
				return false;
		} else if (!SYS_CHANGE_VERSION.equals(other.SYS_CHANGE_VERSION))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransLog [SYS_CHANGE_VERSION=");
		builder.append(SYS_CHANGE_VERSION);
		builder.append(", SYS_CHANGE_CREATION_VERSION=");
		builder.append(SYS_CHANGE_CREATION_VERSION);
		builder.append(", SYS_CHANGE_OPERATION=");
		builder.append(SYS_CHANGE_OPERATION);
		builder.append(", SYS_CHANGE_COLUMNS=");
		builder.append(SYS_CHANGE_COLUMNS);
		builder.append(", SYS_CHANGE_CONTEXT=");
		builder.append(SYS_CHANGE_CONTEXT);
		builder.append(", ID=");
		builder.append(ID);
		builder.append(", TABLE_NAME=");
		builder.append(TABLE_NAME);
		builder.append("]");
		return builder.toString();
	}

}
