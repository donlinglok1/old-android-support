package android.support.v4.security;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public final class JsonKey {
	public final static String HEADER = Strings.fString(Strings.LOWU,
			Strings.LOWE, Strings.LOWT, Strings.UPPD, Strings.LOWP,
			Strings.LOWM, Strings.LOWS, Strings.LOWI, Strings.LOWN,
			Strings.LOWG, Strings.UPPP, Strings.LOWA, Strings.LOWT,
			Strings.LOWH, Strings.LOWO, Strings.LOWM, Strings.UPPN,
			Strings.LOWA, Strings.LOWM, Strings.LOWE, Strings.LOWU,
			Strings.LOWM, Strings.LOWS, Strings.LOWI, Strings.LOWN,
			Strings.LOWG, Strings.UPPP, Strings.LOWA, Strings.LOWT,
			Strings.LOWT, Strings.LOWY, Strings.UPPG, Strings.LOWR,
			Strings.LOWM, Strings.LOWS, Strings.LOWI, Strings.LOWN,
			Strings.LOWG, Strings.UPPP, Strings.LOWA, Strings.LOWT,
			Strings.LOWH, Strings.LOWO, Strings.LOWM, Strings.UPPN,
			Strings.LOWA, Strings.LOWM, Strings.LOWE, Strings.LOWU,
			Strings.LOWM, Strings.LOWS, Strings.LOWI, Strings.LOWN,
			Strings.LOWG, Strings.UPPP, Strings.LOWA, Strings.LOWT,
			Strings.LOWH, Strings.LOWO, Strings.LOWM, Strings.UPPN,
			Strings.LOWA, Strings.LOWM, Strings.LOWE, Strings.LOWU,
			Strings.UPPC, Strings.LOWU, Strings.LOWP, Strings.UPPT,
			Strings.LOWG, Strings.UPPP, Strings.LOWA, Strings.LOWT,
			Strings.LOWA, Strings.LOWM, Strings.LOWE, Strings.LOWU,
			Strings.LOWM, Strings.LOWS, Strings.LOWI, Strings.LOWN,
			Strings.LOWG, Strings.UPPP, Strings.LOWA, Strings.LOWT,
			Strings.LOWH, Strings.LOWO, Strings.LOWM, Strings.UPPN,
			Strings.LOWA, Strings.LOWM, Strings.LOWE, Strings.LOWU,
			Strings.LOWI, Strings.LOWD, Strings.LOWL, Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPD, Strings.LOWP,
			Strings.LOWM, Strings.LOWS, Strings.LOWI, Strings.LOWN,
			Strings.LOWG, Strings.UPPP, Strings.LOWA, Strings.LOWT,
			Strings.LOWH, Strings.LOWO, Strings.LOWM, Strings.UPPN,
			Strings.LOWA, Strings.LOWM, Strings.LOWE, Strings.LOWU,
			Strings.LOWM, Strings.LOWS, Strings.LOWI, Strings.LOWN,
			Strings.LOWG, Strings.UPPP, Strings.LOWA, Strings.LOWT,
			Strings.LOWT, Strings.LOWY, Strings.UPPG, Strings.LOWR,
			Strings.LOWO);

	public final static String ACTION = Strings.fString(Strings.UPPA,
			Strings.UPPC, Strings.UPPT, Strings.UPPI, Strings.UPPO,
			Strings.UPPN);
	public final static String ERROR = Strings.fString(Strings.UPPE,
			Strings.UPPR, Strings.UPPR, Strings.UPPO, Strings.UPPR);
	public final static String RECEIVER = Strings.fString(Strings.LOWC,
			Strings.LOWO, Strings.LOWM, Strings.DOT, Strings.LOWR,
			Strings.LOWI, Strings.LOWX, Strings.LOWO, Strings.LOWN,
			Strings.DOT, Strings.LOWF, Strings.LOWD, Strings.LOWR,
			Strings.LOWE, Strings.LOWP, Strings.LOWU, Strings.LOWB,
			Strings.LOWL, Strings.LOWI, Strings.LOWC, Strings.DOT,
			Strings.UPPR, Strings.UPPE, Strings.UPPC, Strings.UPPE,
			Strings.UPPI, Strings.UPPV, Strings.UPPE, Strings.UPPR);
	public final static String REFRESH = Strings.LOWY;
	public final static String REFRESHMYORDER = Strings.LOWF;
	public final static String RETURN = Strings.fString(Strings.UPPR,
			Strings.UPPE, Strings.UPPT, Strings.UPPU, Strings.UPPR,
			Strings.UPPN);

	public final static String ACCEPTORDER = Strings.fString(Strings.LOWA,
			Strings.LOWC, Strings.LOWC, Strings.LOWE, Strings.LOWP,
			Strings.LOWT, Strings.UPPO, Strings.LOWR, Strings.LOWD,
			Strings.LOWE, Strings.LOWR);
	public final static String ADDBANKIN = Strings.fString(Strings.LOWA,
			Strings.LOWD, Strings.LOWD, Strings.UPPB, Strings.LOWA,
			Strings.LOWN, Strings.LOWK, Strings.UPPI, Strings.LOWN);
	public final static String ADDCREDIT = Strings.fString(Strings.LOWA,
			Strings.LOWD, Strings.LOWD, Strings.UPPC, Strings.LOWR,
			Strings.LOWE, Strings.LOWD, Strings.LOWI, Strings.LOWT);
	public final static String ADDDLOCATE = Strings.fString(Strings.LOWA,
			Strings.LOWD, Strings.LOWD, Strings.UPPD, Strings.UPPL,
			Strings.LOWO, Strings.LOWC, Strings.LOWA, Strings.LOWT,
			Strings.LOWE);
	public final static String ADDDUTY = Strings.fString(Strings.LOWA,
			Strings.LOWD, Strings.LOWD, Strings.UPPD, Strings.LOWU,
			Strings.LOWT, Strings.LOWY);
	public final static String ADDPROBLEM = Strings.fString(Strings.LOWA,
			Strings.LOWD, Strings.LOWD, Strings.UPPP, Strings.LOWR,
			Strings.LOWO, Strings.LOWB, Strings.LOWL, Strings.LOWE,
			Strings.LOWM);
	public final static String ADDROSTER = Strings.fString(Strings.LOWA,
			Strings.LOWD, Strings.LOWD, Strings.UPPR, Strings.LOWO,
			Strings.LOWS, Strings.LOWT, Strings.LOWE, Strings.LOWR);
	public final static String ADDSIGN = Strings.fString(Strings.LOWA,
			Strings.LOWD, Strings.LOWD, Strings.UPPS, Strings.LOWI,
			Strings.LOWG, Strings.LOWN);
	public final static String AMOUNT = Strings.fString(Strings.LOWA,
			Strings.LOWM, Strings.LOWO, Strings.LOWU, Strings.LOWN,
			Strings.LOWT);
	public final static String ASSIGN = Strings.fString(Strings.LOWA,
			Strings.LOWS, Strings.LOWS, Strings.LOWI, Strings.LOWG,
			Strings.LOWN);
	public final static String BANKIN_STATUS = Strings.fString(Strings.LOWB,
			Strings.LOWK, Strings.LOWI, Strings.UPPS, Strings.LOWT,
			Strings.LOWA, Strings.LOWT, Strings.LOWU, Strings.LOWS);
	public final static String BANK_ACCOUNT = Strings.fString(Strings.LOWB,
			Strings.LOWA, Strings.LOWN, Strings.LOWK, Strings.UPPA,
			Strings.LOWC);
	public final static String CLIENT_ORDER_NO = Strings.fString(Strings.LOWC,
			Strings.UPPO, Strings.LOWR, Strings.LOWD, Strings.LOWE,
			Strings.LOWR, Strings.UPPN, Strings.LOWO);
	public final static String CODADJID = Strings.fString(Strings.LOWC,
			Strings.LOWO, Strings.LOWD, Strings.LOWA, Strings.LOWD,
			Strings.LOWJ, Strings.UPPI, Strings.LOWD);
	public final static String COMMENT = Strings.fString(Strings.LOWC,
			Strings.LOWO, Strings.LOWM, Strings.LOWM, Strings.LOWE,
			Strings.LOWN, Strings.LOWT);
	public final static String CREDIT_BANK_ID = Strings.fString(Strings.LOWB,
			Strings.LOWA, Strings.LOWN, Strings.LOWK, Strings.UPPI,
			Strings.UPPD);
	public final static String DATE = Strings.fString(Strings.LOWD,
			Strings.LOWA, Strings.LOWT, Strings.LOWE);
	public final static String DELIVERY_DATE = Strings.fString(Strings.LOWD,
			Strings.LOWA, Strings.LOWT, Strings.LOWE, Strings.UPPD,
			Strings.LOWL);
	public final static String DISTRICT_DESC_A = Strings.fString(Strings.LOWS,
			Strings.UPPD, Strings.LOWI, Strings.LOWS, Strings.UPPD,
			Strings.LOWE, Strings.LOWS);
	public final static String DISTRICT_DESC_B = Strings.fString(Strings.LOWE,
			Strings.UPPD, Strings.LOWI, Strings.LOWS, Strings.UPPD,
			Strings.LOWE, Strings.LOWS);
	public final static String DL_ADJUSTED_TIME = Strings.fString(Strings.LOWD,
			Strings.LOWL, Strings.UPPT, Strings.LOWI, Strings.LOWM,
			Strings.LOWE);
	public final static String DL_BUILDING = Strings.fString(Strings.LOWD,
			Strings.LOWL, Strings.UPPB, Strings.LOWU, Strings.LOWI,
			Strings.LOWL, Strings.LOWD);
	public final static String DL_COMPANY_NAME = Strings.fString(Strings.LOWD,
			Strings.LOWL, Strings.UPPC, Strings.LOWO, Strings.LOWM,
			Strings.UPPN, Strings.LOWA, Strings.LOWM, Strings.LOWE);
	public final static String DL_CONTACT_NAME = Strings.fString(Strings.LOWD,
			Strings.LOWL, Strings.UPPN, Strings.LOWA, Strings.LOWM,
			Strings.LOWE);
	public final static String DL_CONTACT_TEL = Strings.fString(Strings.LOWD,
			Strings.LOWL, Strings.UPPT, Strings.LOWE, Strings.LOWL);
	public final static String DL_FLOOR_BLK_UNIT = Strings.fString(
			Strings.LOWD, Strings.LOWL, Strings.UPPU, Strings.LOWN,
			Strings.LOWI, Strings.LOWT);
	public final static String DL_QUOTE_TIME = Strings.fString(Strings.LOWD,
			Strings.LOWL, Strings.UPPQ, Strings.UPPT, Strings.LOWI,
			Strings.LOWM, Strings.LOWE);
	public final static String DL_STREET_NAME = Strings.fString(Strings.LOWD,
			Strings.LOWL, Strings.UPPS, Strings.LOWT);
	public final static String DL_STREET_NO = Strings.fString(Strings.LOWD,
			Strings.LOWL, Strings.UPPS, Strings.LOWT, Strings.UPPN,
			Strings.LOWO);
	public final static String DUTY_TYPE = Strings.fString(Strings.LOWD,
			Strings.LOWT, Strings.UPPT, Strings.LOWY, Strings.LOWP,
			Strings.LOWE);
	public final static String DVE_ID = Strings.fString(Strings.LOWD,
			Strings.UPPI, Strings.UPPD);
	public final static String EDITDLTIME = Strings.fString(Strings.LOWE,
			Strings.LOWD, Strings.LOWI, Strings.LOWT, Strings.UPPD,
			Strings.UPPL, Strings.UPPT, Strings.LOWI, Strings.LOWM,
			Strings.LOWE);
	public final static String EDITDUTY = Strings.fString(Strings.LOWE,
			Strings.LOWD, Strings.LOWI, Strings.LOWT, Strings.UPPD,
			Strings.LOWU, Strings.LOWT, Strings.LOWY);
	public final static String EDITORDER = Strings.fString(Strings.LOWE,
			Strings.LOWD, Strings.LOWI, Strings.LOWT, Strings.UPPO,
			Strings.LOWR, Strings.LOWD, Strings.LOWE, Strings.LOWR);
	public final static String EDITORDERPRIORITY = Strings.fString(
			Strings.LOWE, Strings.LOWD, Strings.LOWI, Strings.LOWT,
			Strings.UPPO, Strings.LOWD, Strings.UPPP, Strings.LOWR,
			Strings.LOWI, Strings.LOWO, Strings.LOWR, Strings.LOWI,
			Strings.LOWT, Strings.LOWY);
	public final static String EDITPUTIME = Strings.fString(Strings.LOWE,
			Strings.LOWD, Strings.LOWI, Strings.LOWT, Strings.UPPP,
			Strings.UPPU, Strings.UPPT, Strings.LOWI, Strings.LOWM,
			Strings.LOWE);
	public final static String EDITUSERAVATER = Strings.fString(Strings.LOWE,
			Strings.LOWD, Strings.LOWI, Strings.LOWT, Strings.UPPA,
			Strings.LOWV, Strings.LOWA, Strings.LOWT, Strings.LOWE,
			Strings.LOWR);
	public final static String END = Strings.fString(Strings.LOWE,
			Strings.LOWN, Strings.LOWD);
	public final static String END_DUTY = Strings.fString(Strings.LOWD,
			Strings.UPPE);
	public final static String GETASSIGNUSER = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPA, Strings.LOWS,
			Strings.LOWS, Strings.LOWI, Strings.LOWG, Strings.LOWN,
			Strings.UPPU, Strings.LOWS, Strings.LOWE, Strings.LOWR);
	public final static String GETBANKIN = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPB, Strings.LOWA,
			Strings.LOWN, Strings.LOWK, Strings.LOWI, Strings.LOWN);
	public final static String GETBEACON = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPB, Strings.LOWE,
			Strings.LOWA, Strings.LOWC, Strings.LOWO, Strings.LOWN);
	public final static String GETCREDIT = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPC, Strings.LOWR,
			Strings.LOWE, Strings.LOWD, Strings.LOWI, Strings.LOWT);
	public final static String GETCODADJ = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPC, Strings.LOWO,
			Strings.LOWD, Strings.UPPA, Strings.LOWD, Strings.LOWJ);
	public final static String GETCREDITBANK = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPB, Strings.LOWA,
			Strings.LOWN, Strings.LOWK);
	public final static String GETDUTYGROUP = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPD, Strings.LOWU,
			Strings.LOWT, Strings.LOWY, Strings.UPPG, Strings.LOWR,
			Strings.LOWO, Strings.LOWU, Strings.LOWP);
	public final static String GETDUTYLOG = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPD, Strings.LOWU,
			Strings.LOWT, Strings.LOWY, Strings.UPPL, Strings.LOWO,
			Strings.LOWG);
	public final static String GETLIVESUMMARY = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPS, Strings.LOWU,
			Strings.LOWM, Strings.LOWM, Strings.LOWA, Strings.LOWR,
			Strings.LOWY);
	public final static String GETLOGIN = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPL, Strings.LOWO,
			Strings.LOWG, Strings.LOWI, Strings.LOWN);
	public final static String GETMYLIST = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPM, Strings.LOWY,
			Strings.UPPL, Strings.LOWI, Strings.LOWS, Strings.LOWT);
	public final static String GETMYLISTHEAD = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPM, Strings.LOWY,
			Strings.UPPL, Strings.LOWI, Strings.LOWS, Strings.LOWT,
			Strings.UPPH, Strings.LOWE, Strings.LOWA, Strings.LOWD);
	public final static String GETORDER = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPO, Strings.LOWR,
			Strings.LOWD, Strings.LOWE, Strings.LOWR);
	public final static String GETORDERLIST = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPO, Strings.LOWR,
			Strings.LOWD, Strings.LOWE, Strings.LOWR, Strings.UPPL,
			Strings.LOWI, Strings.LOWS, Strings.LOWT);
	public final static String GETORDERLISTHEAD = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPO, Strings.LOWR,
			Strings.LOWD, Strings.LOWE, Strings.LOWR, Strings.UPPL,
			Strings.LOWI, Strings.LOWS, Strings.LOWT, Strings.UPPH,
			Strings.LOWE, Strings.LOWA, Strings.LOWD);
	public final static String GETPROBLEM = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPP, Strings.LOWR,
			Strings.LOWO, Strings.LOWB, Strings.LOWL, Strings.LOWE,
			Strings.LOWM);
	public final static String GETPROBLEMTYPE = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPP, Strings.LOWR,
			Strings.LOWO, Strings.LOWB, Strings.UPPT, Strings.LOWY,
			Strings.LOWP, Strings.LOWE);
	public final static String GETROSTER = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPR, Strings.LOWO,
			Strings.LOWS, Strings.LOWT, Strings.LOWE, Strings.LOWR);
	public final static String GETSETTING = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPS, Strings.LOWE,
			Strings.LOWT, Strings.LOWT, Strings.LOWI, Strings.LOWN,
			Strings.LOWG);
	public final static String GETSHIFTORDER = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPS, Strings.LOWH,
			Strings.LOWI, Strings.LOWF, Strings.LOWT, Strings.UPPO,
			Strings.LOWR, Strings.LOWD, Strings.LOWE, Strings.LOWR);
	public final static String GETSHIFTORDERLIST = Strings.fString(
			Strings.LOWG, Strings.LOWE, Strings.LOWT, Strings.UPPS,
			Strings.LOWH, Strings.LOWI, Strings.LOWF, Strings.LOWT,
			Strings.UPPL, Strings.LOWI, Strings.LOWS, Strings.LOWT);
	public final static String GETSOCKETIP = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPS, Strings.LOWO,
			Strings.LOWC, Strings.LOWK, Strings.LOWE, Strings.LOWT,
			Strings.UPPI, Strings.UPPP);
	public final static String GETSUM = Strings.fString(Strings.LOWG,
			Strings.LOWE, Strings.LOWT, Strings.UPPS, Strings.LOWU,
			Strings.UPPM);
	public final static String GROUP_CODE = Strings.fString(Strings.LOWG,
			Strings.LOWU, Strings.LOWP, Strings.UPPC, Strings.LOWO,
			Strings.LOWD, Strings.LOWE);
	public final static String GROUP_ID = Strings.fString(Strings.LOWG,
			Strings.LOWU, Strings.LOWP, Strings.UPPI, Strings.UPPD);
	public final static String ID = Strings.fString(Strings.UPPI, Strings.UPPD);
	public final static String IMAGE_PATH = Strings.fString(Strings.LOWP,
			Strings.LOWA, Strings.LOWT, Strings.LOWH);
	public final static String LANGUAGE = Strings.fString(Strings.LOWL,
			Strings.LOWA, Strings.LOWN, Strings.LOWG);
	public final static String LATEORDERS = Strings.fString(Strings.LOWL,
			Strings.LOWA, Strings.LOWT, Strings.LOWE, Strings.UPPO,
			Strings.LOWR, Strings.LOWD, Strings.LOWE, Strings.UPPR);
	public final static String LATETIME = Strings.fString(Strings.LOWL,
			Strings.LOWA, Strings.LOWT, Strings.LOWE, Strings.LOWT,
			Strings.LOWI, Strings.LOWM, Strings.LOWE);
	public final static String LATLNG = Strings.fString(Strings.LOWL,
			Strings.LOWA, Strings.LOWL, Strings.LOWN);
	public final static String LOCATION = Strings.fString(Strings.LOWL,
			Strings.LOWO, Strings.LOWC);
	public final static String NEWVERSION = Strings.fString(Strings.LOWV,
			Strings.LOWE, Strings.LOWR, Strings.UPPN, Strings.LOWE,
			Strings.LOWW);
	public final static String NICK_NAME = Strings.fString(Strings.LOWN,
			Strings.UPPN);
	public final static String ORDER_NO = Strings.fString(Strings.LOWO,
			Strings.LOWR, Strings.LOWD, Strings.LOWE, Strings.LOWR,
			Strings.UPPN, Strings.LOWO);
	public final static String ORDER_PRIORITY = Strings.fString(Strings.LOWO,
			Strings.LOWD, Strings.UPPP, Strings.LOWR, Strings.LOWI,
			Strings.LOWO, Strings.LOWR, Strings.LOWI, Strings.LOWT,
			Strings.LOWY);
	public final static String ORDER_STATUS_ID = Strings.fString(Strings.LOWO,
			Strings.LOWS, Strings.UPPI, Strings.UPPD);
	public final static String PASSWORD = Strings.fString(Strings.LOWP,
			Strings.LOWW);
	public final static String PAYMENT_AMOUNT = Strings.fString(Strings.LOWP,
			Strings.LOWA, Strings.LOWY, Strings.UPPA, Strings.LOWM,
			Strings.LOWO);
	public final static String PAYMENT_TYPE_CODE = Strings.fString(
			Strings.LOWP, Strings.LOWR, Strings.LOWO, Strings.UPPC,
			Strings.LOWO, Strings.LOWD, Strings.LOWE);
	public final static String PAYMENT_TYPE_ID = Strings.fString(Strings.LOWP,
			Strings.LOWA, Strings.LOWY, Strings.UPPT, Strings.LOWY,
			Strings.LOWP, Strings.LOWE);
	public final static String PENDING_DRIVER_ID = Strings.fString(
			Strings.LOWP, Strings.LOWD, Strings.UPPI, Strings.UPPD);
	public final static String PHP = Strings.fString(Strings.DOT, Strings.LOWP,
			Strings.LOWH, Strings.LOWP);
	public final static String PROBLEM_DESC = Strings.fString(Strings.LOWP,
			Strings.LOWB, Strings.UPPD, Strings.LOWE, Strings.LOWS);
	public final static String PROBLEM_TYPE_ID = Strings.fString(Strings.LOWP,
			Strings.LOWB, Strings.UPPT, Strings.LOWY, Strings.LOWP,
			Strings.LOWE);
	public final static String PU_ADJUSTED_TIME = Strings.fString(Strings.LOWP,
			Strings.LOWU, Strings.UPPT, Strings.LOWI, Strings.LOWM,
			Strings.LOWE);
	public final static String PU_BUILDING = Strings.fString(Strings.LOWP,
			Strings.LOWU, Strings.UPPB, Strings.LOWU, Strings.LOWI,
			Strings.LOWL, Strings.LOWD);
	public final static String PU_COMPANY_NAME = Strings.fString(Strings.LOWP,
			Strings.LOWU, Strings.UPPC, Strings.LOWO, Strings.LOWM,
			Strings.UPPN, Strings.LOWA, Strings.LOWM, Strings.LOWE);
	public final static String PU_CONTACT_NAME = Strings.fString(Strings.LOWP,
			Strings.LOWU, Strings.UPPN, Strings.LOWA, Strings.LOWM,
			Strings.LOWE);
	public final static String PU_CONTACT_TEL = Strings.fString(Strings.LOWP,
			Strings.LOWU, Strings.UPPT, Strings.LOWE, Strings.LOWL);
	public final static String PU_FLOOR_BLK_UNIT = Strings.fString(
			Strings.LOWP, Strings.LOWU, Strings.UPPU, Strings.LOWN,
			Strings.LOWI, Strings.LOWT);
	public final static String PU_QUOTE_TIME = Strings.fString(Strings.LOWP,
			Strings.LOWU, Strings.UPPQ, Strings.UPPT, Strings.LOWI,
			Strings.LOWM, Strings.LOWE);
	public final static String PU_STREET_NAME = Strings.fString(Strings.LOWP,
			Strings.LOWU, Strings.UPPS, Strings.LOWT);
	public final static String PU_STREET_NO = Strings.fString(Strings.LOWP,
			Strings.LOWU, Strings.UPPS, Strings.LOWT, Strings.UPPN,
			Strings.LOWO);
	public final static String RANGE_OF_GPS = Strings.fString(Strings.LOWR,
			Strings.LOWA, Strings.LOWG, Strings.FOUR, Strings.LOWG,
			Strings.LOWP, Strings.LOWS);
	public final static String REFERENCE_ID = Strings.fString(Strings.LOWR,
			Strings.LOWE, Strings.LOWF, Strings.UPPI, Strings.UPPD);
	public final static String REMARKS = Strings.fString(Strings.LOWM,
			Strings.LOWA, Strings.LOWR, Strings.LOWK);
	public final static String REMOVEPENDING = Strings.fString(Strings.LOWE,
			Strings.LOWD, Strings.LOWI, Strings.LOWT, Strings.UPPP,
			Strings.LOWE, Strings.LOWN, Strings.LOWD, Strings.LOWI,
			Strings.LOWN, Strings.LOWG);
	public final static String RSSI = Strings.fString(Strings.LOWR,
			Strings.LOWS, Strings.LOWS, Strings.LOWI);
	public final static String SFTPHOST = Strings.fString(Strings.LOWS,
			Strings.LOWF, Strings.LOWT, Strings.LOWP, Strings.LOWH);
	public final static String SHIFTLOG_ID = Strings.fString(Strings.LOWL,
			Strings.LOWO, Strings.LOWG, Strings.UPPI, Strings.UPPD);
	public final static String SHOWPRE = Strings.fString(Strings.UPPS,
			Strings.UPPH, Strings.UPPO, Strings.UPPW, Strings.UPPP,
			Strings.UPPR, Strings.UPPE);
	public final static String SOCKETIP = Strings.fString(Strings.LOWS,
			Strings.LOWO, Strings.LOWC, Strings.LOWK, Strings.LOWI,
			Strings.LOWP);
	public final static String START = Strings.fString(Strings.LOWS,
			Strings.LOWT, Strings.LOWA, Strings.LOWR, Strings.LOWT);
	public final static String START_BEACON = Strings.fString(Strings.LOWS,
			Strings.UPPB, Strings.LOWC, Strings.LOWO, Strings.LOWN);
	public final static String START_DUTY = Strings.fString(Strings.LOWD,
			Strings.UPPS);
	public final static String SUGNATURE_PATH = Strings.fString(Strings.LOWS,
			Strings.LOWI, Strings.LOWN, Strings.LOWG, Strings.UPPP,
			Strings.LOWA, Strings.LOWT, Strings.LOWH);
	public final static String TIME = Strings.fString(Strings.LOWT,
			Strings.LOWI, Strings.LOWM, Strings.LOWE);
	public final static String TOTAL_COD = Strings.fString(Strings.LOWT,
			Strings.LOWO, Strings.UPPC, Strings.LOWO, Strings.LOWD);
	public final static String TOTAL_ORDERS = Strings.fString(Strings.LOWT,
			Strings.LOWO, Strings.UPPO, Strings.LOWR, Strings.LOWD,
			Strings.LOWE, Strings.LOWR, Strings.LOWS);
	public final static String TOTAL_PAID = Strings.fString(Strings.LOWT,
			Strings.LOWO, Strings.LOWT, Strings.UPPP, Strings.LOWA,
			Strings.LOWI, Strings.LOWD);
	public final static String TOTAL_PCOD = Strings.fString(Strings.LOWT,
			Strings.LOWO, Strings.LOWT, Strings.UPPP, Strings.LOWC,
			Strings.LOWO, Strings.LOWD);
	public final static String TOTAL_SALES = Strings.fString(Strings.LOWT,
			Strings.LOWO, Strings.LOWT, Strings.UPPS, Strings.LOWA,
			Strings.LOWL, Strings.LOWE, Strings.LOWS);
	public final static String TOTAL_VOD = Strings.fString(Strings.LOWT,
			Strings.LOWO, Strings.LOWT, Strings.UPPV, Strings.LOWO,
			Strings.LOWD);
	public final static String UPLOADVOICEFILEPATH = Strings.fString(
			Strings.LOWV, Strings.LOWC, Strings.UPPP, Strings.LOWA,
			Strings.LOWT, Strings.LOWH);
	public final static String USER_ID = Strings.fString(Strings.LOWU,
			Strings.UPPI, Strings.UPPD);
	public final static String USER_NAME = Strings.fString(Strings.LOWU,
			Strings.UPPN);
	public final static String USER_TYPE_ID = Strings.fString(Strings.LOWU,
			Strings.UPPT, Strings.LOWY, Strings.LOWP, Strings.LOWE,
			Strings.UPPI, Strings.UPPD);
	public final static String VERSION_CODE = Strings.fString(Strings.LOWV,
			Strings.LOWE, Strings.LOWR, Strings.UPPC, Strings.LOWO,
			Strings.LOWD, Strings.LOWE);
	public final static String WEBHOST = Strings.fString(Strings.LOWW,
			Strings.LOWE, Strings.LOWN, Strings.LOWH, Strings.LOWO,
			Strings.LOWS, Strings.LOWT);
	public final static String WORKTIME = Strings.fString(Strings.LOWW,
			Strings.UPPT, Strings.LOWI, Strings.LOWM, Strings.LOWE);

	private JsonKey() {
	}
}
