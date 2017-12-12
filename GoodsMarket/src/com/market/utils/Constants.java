package com.market.utils;

public class Constants {

	//http://www.haoyikuai.cn/
//	public static final String SERVER_URL = "http://4.113.dns-dns.net/www/excms/api.php";
	public static final String SERVER_ROOT_URL = "http://4.113.dns-dns.net/www/excms";
	public static final String SERVER_URL = SERVER_ROOT_URL+"/api.php";
	public static final String PROJECT_ID = "ApiGoods";
	public static final String TITLEEMPTY = "";
	public static final String MOBILEEMPTY = "";
	public static final float CONTENTSIZE = 18.0f;
 
	// http回调标识
	public static final int CALLBACK_FLAG_LOGIN = 0x01; 
	public static final int CALLBACK_FLAG_REGIST = 0x02;
	public static final int CALLBACK_FLAG_REGISTVERIFYCODE = 0x03;
	public static final int CALLBACK_FLAG_GET_NEWEST_GOODS = 0x04;
	public static final int CALLBACK_FLAG_GET_HOTEST_GOODS = 0x05;
	public static final int CALLBACK_FLAG_FORGOTPWINFO = 0x06;
	public static final int CALLBACK_FLAG_FORGOTREQUESTVFCODE = 0x07;
	public static final int CALLBACK_FLAG_FORGOTPOSRTVFCODE = 0x08;
	public static final int CALLBACK_FLAG_GET_CATEGORY_LIST = 0x09;
	public static final int CALLBACK_FLAG_GET_GOODSINOF = 0x10;
	public static final int CALLBACK_FLAG_GET_ORDERLIST = 0x11;
	public static final int CALLBACK_FLAG_GET_GOOD_LIST = 0x12;
	public static final int CALLBACK_FLAG_GET_AREALIST = 0x13;
	public static final int CALLBACK_FLAG_CHECK_AREALIST = 0x14;
	public static final int CALLBACK_FLAG_GET_ADDRESSLIST = 0x15;
	public static final int CALLBACK_FLAG_POST_ADDRESS = 0x16;
	public static final int CALLBACK_FLAG_GET_ORDER = 0x17;
	public static final int CALLBACK_FLAG_GET_INFOBEFOREPOSTORDER = 0x18;
	public static final int CALLBACK_FLAG_GET_VERCHERLISR = 0x19;
	public static final int CALLBACK_FLAG_GET_ACTIVEVOUCHER = 0x20;
	public static final int CALLBACK_FLAG_GET_RECOMMAND_CATEGORY= 0x21;
	public static final int CALLBACK_FLAG_GET_HOME_PAGE_AD= 0x22;
	public static final int CALLBACK_FLAG_GET_CARTLIST= 0x23;
	public static final int CALLBACK_FLAG_ADDTOCART = 0x24;
	public static final int CALLBACK_FLAG_GOODSINCARTAMOUNTCHANGE = 0x25;
	public static final int CALLBACK_FLAG_CARTCHECKOUTBEFOREPURCHASED = 0x26;
	public static final int CALLBACK_FLAG_OBTAINFAREOFCHOSENADDRESS = 0x27;
	public static final int CALLBACK_FLAG_OBTAINPAYMENT = 0x28;
	public static final int CALLBACK_FLAG_SUBMITANORDER = 0x29;
	public static final int CALLBACK_FLAG_REMOVEITEMFROMCART = 0x30;
	public static final int CALLBACK_FLAG_ABOUT_US = 0x31;
	public static final int CALLBACK_FLAG_CHECK_UPDATE = 0x32;
	public static final int CALLBACK_FLAG_FEEDBACK = 0x33;

	public static final int CALLBACK_RONGYUN_TOKEN = 0x34;

	public static final int CALLBACK_FLAG_DELETEDORDER = 0x34;
	public static final int CALLBACK_FLAG_CANCELORDER = 0x35;
	public static final int CALLBACK_FLAG_GETCOMMENTLIST = 0x36;
	public static final int CALLBACK_FLAG_GETFAVORITE = 0x37;
	public static final int CALLBACK_FLAG_ADDTOMYFAVORITE = 0x38;
	public static final int CALLBACK_FLAG_GETREFUNDLIST = 0x39;
	public static final int CALLBACK_FLAG_GETREFUNDINFO = 0x40;
	public static final int CALLBACK_FLAG_GETGOODSCOMMENT = 0x41;
	public static final int CALLBACK_FLAG_GETORDERCOMMENTLIST = 0x42;
	public static final int CALLBACK_FLAG_POSTORDERCOMMENT = 0x43;

	// 查找商品时的排序方式
	public static final int SORT_SALES_VOLUME = 1;// 销量
	public static final int SORT_PRICE_ASE = 2;// 价格升序
	public static final int SORT_PRICE_DESC = 3;// 价格降序
	public static final int SORT_COMMENT_COUNT = 4;// 评论数
	public static final int SORT_POST_TIME = 5;// 上架时间

	// 影射KEY值表
	public static final String MAP_FORGOTINDENTIFY = "MAP_FORGOTINDENTIFY";
	public static final String MAP_FORGOTMOBILENUMBER = "MAP_FORGOTMOBILENUMBER";

	// 参数名
	public static final String EXTRA_CATEGORY = "extra_category";
	public static final String EXTRA_GOODS = "extra_goods";
	
	//常用整型常量
	public static final int CODE_COMMON_JUMPBETWEEN_ACTIVITYS_REQUEST0 = 0x00;
	public static final int CODE_COMMON_JUMPBETWEEN_ACTIVITYS_REQUEST1 = 0x01;
	public static final int CODE_COMMON_JUMPBETWEEN_ACTIVITYS_REQUEST2 = 0x02;
	public static final int CODE_COMMON_JUMPBETWEEN_ACTIVITYS_REQUEST3 = 0x03;
	public static final int CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE0 = 0x00;
	public static final int CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE1 = 0x01;
	public static final int CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE2 = 0x02;
	public static final int CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE3 = 0x03;
	public static final int CODE_ADDRESSMODIFICATION_ASK_AREA_FORRS = 0x010;
	public static final int CODE_ADDRESS_ASK_ADDRESSIDMODIFICATION = 0x020;
	public static final int CODE_ADDRESSIDMODIFICATION_BACKTO_ADDRESS = 0x030;
	public static final int INTEGER_ADDRESSLISTACTIVITY_LISTSTYLEM = 0x041;
	public static final int INTEGER_ADDRESSLISTACTIVITY_LISTSTYLES = 0x042;
	
	
	
	//常用字符串常量
	public static final String COMMON_TEXTSPLITER = ";";
	public static final String AREA_BACKTO_ADDRESSMODIFICATION = "AREA_BACKTO_ADDRESSMODIFICATION";
	public static final String AREA_BACKTO_ADDRESSIDMODIFICATION = "AREA_BACKTO_ADDRESSIDMODIFICATION";
	public static final String ADDRESS_TO_ADDRESSIDMODIFICATION = "ADDRESS_TO_ADDRESSIDMODIFICATION";
	public static final String ORDERGOODSLIST_TO_ORDERGOODSDETAIL = "ORDERGOODSLIST_TO_ORDERGOODSDETAIL";
	public static final String STRINGEXTRA = "STRINGEXTRA";
	public static final String PASSWORDEXTRA = "PASSWORDEXTRA";
	public static final String NAMEEXTRA = "NAMEEXTRA";
	public static final String INTEXTRA = "INTEXTRA";
	public static final String LONGEXTRA = "LONGEXTRA";
	public static final String STYLEEXTRA = "STYLEEXTRA";
	public static final String OBJECTEXTRA = "OBJECTEXTRA";
	public static final String OBJECTEXTRA2 = "OBJECTEXTRA2";
	public static final String LONGARREXTRA = "LONGARREXTRA";
	public static final String GOODS_ID = "GOODS_ID";
	
	//通用文件名
	public static final String FILE_KEEP_COOKIE = "marketcookie";
	public static final String FILE_SKULIST_NATIVE = "skulist";
	
	public static final String TAB_HOME = "home";
	public static final String TAB_CATATORY = "catagory";
	public static final String TAB_FIND = "find";
	public static final String TAB_ACCOUNT = "account";
	public static final String TAB_CART = "cart";
}
