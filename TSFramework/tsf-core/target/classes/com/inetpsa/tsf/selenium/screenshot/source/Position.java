package com.inetpsa.tsf.selenium.screenshot.source;

/** 
 * Positon of the zone
 * 
 */
public class Position {
	/** Attribute zoneNotVerifiedLeft */
	private int zoneNotVerifiedLeft ;
	
	/** Attribute zoneNotVerifiedWidth */
	private int zoneNotVerifiedWidth ;
	
	/** Attribute zoneNotVerifiedTop */
	private int zoneNotVerifiedTop;
	
	/** Attribute zoneNotVerifiedHeight */
	private int zoneNotVerifiedHeight;
	
	/** Default Construct
	 * @param zoneNotVerifiedLeft   : the zone left value
	 * @param zoneNotVerifiedWidth  : the zone Width value
	 * @param zoneNotVerifiedTop    : the zone top value
	 * @param zoneNotVerifiedHeight : the zone Height value
	 */
	public Position(int zoneNotVerifiedLeft, int zoneNotVerifiedWidth,
			int zoneNotVerifiedTop, int zoneNotVerifiedHeight) {
		super();
		this.zoneNotVerifiedLeft = zoneNotVerifiedLeft;
		this.zoneNotVerifiedWidth = zoneNotVerifiedWidth;
		this.zoneNotVerifiedTop = zoneNotVerifiedTop;
		this.zoneNotVerifiedHeight = zoneNotVerifiedHeight;
	}
	
	/** To check the current pixel should be ignored or not.
	 * @param x : postion x
	 * @param y : postion y
	 * @return  : boolean
	 */
	public boolean isIgnored(int x, int y) {
		if (x >= zoneNotVerifiedLeft && x <= zoneNotVerifiedLeft+zoneNotVerifiedWidth 
				&& y >= zoneNotVerifiedTop && y <= zoneNotVerifiedTop+zoneNotVerifiedHeight) {
			return true;
		}
		return false;
	}
}
