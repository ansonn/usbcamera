package com.market.bean;

import java.io.Serializable;

import com.market.utils.Constants;
import com.market.utils.Log;

public class AreaBean  implements Serializable{
	
	
	public Integer address_id;
	public int pid;
	public Integer level;
	public int[] sids;
	public String name;
	public static final String SPLITER = Constants.COMMON_TEXTSPLITER;
	public static final String SIDS_SPLITER = ",";
	
	private final int INDEXADDRESS_ID = 0;
	private final int INDEXPID = 1;
	private final int INDEXLEVEL = 2;
	private final int INDEXNAME = 3;
	private final int INDEXSIDS = 4;
	
	/**
	 * address_id\pid\level\name(\sids)
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(address_id);sb.append(SPLITER);
		sb.append(pid);sb.append(SPLITER);
		sb.append(level);sb.append(SPLITER);
		sb.append(name);
		if(sids != null && sids.length > 0)
		{
			sb.append(SPLITER);
			for(int i = 0 ; i < sids.length ; i++)
			{
				sb.append(sids[i]);
				sb.append(SIDS_SPLITER);
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("\n");
		return sb.toString();
	}
	

	/**
	 * toString¡¡·´Ê½
	 * @param line
	 */
	public void String2Bean(String line)
	{
		String[] subAttribute = line.split(SPLITER);
		for(int i = 0 ; i < subAttribute.length ; i++)
		{
			switch(i)
			{
			case INDEXADDRESS_ID:
				this.address_id = Integer.parseInt(subAttribute[INDEXADDRESS_ID]);
				break;
			case INDEXPID:
				this.pid = Integer.parseInt(subAttribute[INDEXPID]);
				break;
			case INDEXLEVEL:
				this.level = Integer.parseInt(subAttribute[INDEXLEVEL]);
				break;
			case INDEXNAME:
				this.name = subAttribute[INDEXNAME];
				break;
			case INDEXSIDS:
				String[] sidsarr = subAttribute[INDEXSIDS].split(this.SIDS_SPLITER);
				this.sids = new int[sidsarr.length];
				for(int j = 0 ; j < sidsarr.length ; j++)
				{
					this.sids[j] = Integer.parseInt(sidsarr[j]);
				}
				
				break;
			
			}
			
		}
		
	}


	public Integer getAddress_id() {
		return address_id;
	}


	public void setAddress_id(Integer address_id) {
		this.address_id = address_id;
	}


	public Integer getLevel() {
		return level;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}
