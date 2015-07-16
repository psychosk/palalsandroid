package com.starxnet.starxnetdemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.starxnet.palals.ConnectionManager;
import com.starxnet.palals.ConnectionManager.ConnectStatusInterface;
import com.starxnet.palals.EventMananger;
import com.starxnet.palals.Manager;
import com.starxnet.palals.MessageService;
import com.starxnet.palals.NotifyMananger.onEmailCallBack;
import com.starxnet.palals.NotifyMananger.onVoipCallBack;
import com.starxnet.palals.StarxNetConst;
import com.starxnet.palals.MessageService.MessageInOneInterface;
import com.starxnet.palals.MessageService.NotifyInterface;
import com.starxnet.palals.NotifyMananger;
import com.starxnet.palals.MessageService.EventInterface;
import com.starxnet.palals.MessageService.PalalsInterface;
import com.starxnet.palals.MessageService.SensorInterface;
import com.starxnet.palals.NotifyMananger.onPalalsPushCallBack;
import com.starxnet.palals.NotifyMananger.onXinGeCallBack;
import com.starxnet.palals.PalalsMananger;
import com.starxnet.palals.Manager.DataDeliveryInterface;
import com.starxnet.palals.PalalsMananger.PalalsCheckCallBack;
import com.starxnet.palals.PalalsMananger.SearchResultInterface;
import com.starxnet.palals.RuleMananger;
import com.starxnet.palals.SensorManager;
import com.starxnet.sdk_demo.R;
import com.starxnet.util.URLStringEncDec;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity implements SensorInterface,
		EventInterface, PalalsInterface, MessageInOneInterface {
	public final String TAG = "TestDemo";
	private Button bt_connect;
	private Button bt_disconnect;
	private Button bt_jsontree;
	private Button bt_eventlist;
	private Button bt_subscribeToken;
	private Button bt_sloton;
	private Button bt_slotoff;
	private Button bt_additem;
	private Button bt_removeitem;
	private Button bt_search;
	private Button bt_changepwd;
	private Button bt_modityitem;
	private Button bt_addarmmodel;
	private Button bt_removesarmmodel;
	private Button bt_operatearmmodel;
	private Button bt_modifyarmmodel;
	private Button bt_addtoarmmodel;
	private Button bt_rmfromarmmodel;
	private Button bt_rmevent;
	private Button bt_changeevent;
	private Button bt_checkfirm;
	private Button bt_upgradefirm;
	private Button bt_subscribePalalsPush;
	private Button bt_subscribeXinGe;
	private Button bt_subscribeVoIp;
	private Button bt_subscribeEmail;

	private Button bt_unsubscribePalalsPush;
	private Button bt_unsubscribeXinGe;
	private Button bt_unsubscribeVoIp;
	private Button bt_unsubscribeEmail;
	private Button bt_addcamera;
	private Button bt_updatecamera;
	private Button bt_uncode;
	private EditText et_did;
	private EditText et_pwd;
	private TextView status_p2p;
	private TextView tv_log;
	private StringBuilder stringBuilder;
	int[] ids = new int[] { 1, 2, 3, 4 };
	private UIHandler uiHandler = new UIHandler();
	private MessageHandler messageHandler = new MessageHandler();
	private PalalsMananger pm;
	private ConnectionManager cm;
	private NotifyMananger nm;
	private RuleMananger rm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		pm = new PalalsMananger(getApplicationContext());
		cm = new ConnectionManager(getApplicationContext());
		nm = new NotifyMananger();
		rm = new RuleMananger();
		MessageService.setSensorInterface(this);
		MessageService.setEventInterface(this);
		MessageService.setPalalsInterface(this);
		MessageService.setMessageInOneInterface(this);
	}

	private void initView() {
		bt_connect = (Button) findViewById(R.id.bt_connect);
		bt_connect.setOnClickListener(mBtnOnClickListener);
		bt_disconnect = (Button) findViewById(R.id.bt_disconnect);
		bt_disconnect.setOnClickListener(mBtnOnClickListener);
		bt_jsontree = (Button) findViewById(R.id.bt_jsontree);
		bt_jsontree.setOnClickListener(mBtnOnClickListener);
		bt_eventlist = (Button) findViewById(R.id.bt_eventlist);
		bt_eventlist.setOnClickListener(mBtnOnClickListener);
		bt_subscribeToken = (Button) findViewById(R.id.bt_subscribeToken);
		bt_subscribeToken.setOnClickListener(mBtnOnClickListener);
		bt_sloton = (Button) findViewById(R.id.bt_sloton);
		bt_sloton.setOnClickListener(mBtnOnClickListener);
		bt_slotoff = (Button) findViewById(R.id.bt_slotoff);
		bt_slotoff.setOnClickListener(mBtnOnClickListener);

		bt_additem = (Button) findViewById(R.id.bt_additem);
		bt_additem.setOnClickListener(mBtnOnClickListener);

		bt_removeitem = (Button) findViewById(R.id.bt_removeitem);
		bt_removeitem.setOnClickListener(mBtnOnClickListener);
		bt_search = (Button) findViewById(R.id.bt_search);
		bt_search.setOnClickListener(mBtnOnClickListener);

		bt_changepwd = (Button) findViewById(R.id.bt_changepwd);
		bt_changepwd.setOnClickListener(mBtnOnClickListener);

		bt_modityitem = (Button) findViewById(R.id.bt_modityitem);
		bt_modityitem.setOnClickListener(mBtnOnClickListener);

		bt_addarmmodel = (Button) findViewById(R.id.bt_addarmmodel);
		bt_addarmmodel.setOnClickListener(mBtnOnClickListener);

		bt_operatearmmodel = (Button) findViewById(R.id.bt_operatearmmodel);
		bt_operatearmmodel.setOnClickListener(mBtnOnClickListener);

		bt_removesarmmodel = (Button) findViewById(R.id.bt_removesarmmodel);
		bt_removesarmmodel.setOnClickListener(mBtnOnClickListener);
		bt_modifyarmmodel = (Button) findViewById(R.id.bt_modifyarmmodel);
		bt_modifyarmmodel.setOnClickListener(mBtnOnClickListener);
		bt_addtoarmmodel = (Button) findViewById(R.id.bt_addtoarmmodel);
		bt_addtoarmmodel.setOnClickListener(mBtnOnClickListener);

		bt_rmfromarmmodel = (Button) findViewById(R.id.bt_rmfromarmmodel);
		bt_rmfromarmmodel.setOnClickListener(mBtnOnClickListener);

		bt_rmevent = (Button) findViewById(R.id.bt_rmevent);
		bt_rmevent.setOnClickListener(mBtnOnClickListener);

		bt_changeevent = (Button) findViewById(R.id.bt_changeevent);
		bt_changeevent.setOnClickListener(mBtnOnClickListener);

		bt_checkfirm = (Button) findViewById(R.id.bt_checkfirm);
		bt_checkfirm.setOnClickListener(mBtnOnClickListener);

		bt_upgradefirm = (Button) findViewById(R.id.bt_upgradefirm);
		bt_upgradefirm.setOnClickListener(mBtnOnClickListener);

		bt_subscribePalalsPush = (Button) findViewById(R.id.bt_subscribePalalsPush);
		bt_subscribePalalsPush.setOnClickListener(mBtnOnClickListener);

		bt_subscribeXinGe = (Button) findViewById(R.id.bt_subscribeXinGe);
		bt_subscribeXinGe.setOnClickListener(mBtnOnClickListener);

		bt_subscribeVoIp = (Button) findViewById(R.id.bt_subscribeVoIp);
		bt_subscribeVoIp.setOnClickListener(mBtnOnClickListener);

		bt_subscribeEmail = (Button) findViewById(R.id.bt_subscribeEmail);
		bt_subscribeEmail.setOnClickListener(mBtnOnClickListener);

		bt_unsubscribePalalsPush = (Button) findViewById(R.id.bt_unsubscribePalalsPush);
		bt_unsubscribePalalsPush.setOnClickListener(mBtnOnClickListener);

		bt_unsubscribeXinGe = (Button) findViewById(R.id.bt_unsubscribeXinGe);
		bt_unsubscribeXinGe.setOnClickListener(mBtnOnClickListener);

		bt_unsubscribeVoIp = (Button) findViewById(R.id.bt_unsubscribeVoIp);
		bt_unsubscribeVoIp.setOnClickListener(mBtnOnClickListener);

		bt_unsubscribeEmail = (Button) findViewById(R.id.bt_unsubscribeEmail);
		bt_unsubscribeEmail.setOnClickListener(mBtnOnClickListener);

		
		bt_addcamera=(Button) findViewById(R.id.bt_addcamera);
		bt_addcamera.setOnClickListener(mBtnOnClickListener);
		
		bt_updatecamera=(Button) findViewById(R.id.bt_updatecamera);
		bt_updatecamera.setOnClickListener(mBtnOnClickListener);
		
		et_did = (EditText) findViewById(R.id.et_did);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		
		bt_uncode=(Button) findViewById(R.id.bt_uncode);
		bt_uncode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String code="OBOBBKKGMOHKHODDKpHcmgIoKLohAnNJakcKMfddHJnAKeOBknKbgKEhpLLfjAFjIoHcaiiBPoGBoKabmnLLGC";
				String result=URLStringEncDec.decode(code);
				System.out.println("result="+result);
				Message msgss = new Message();
				msgss.what = 11;
				msgss.obj = result;
				uiHandler.sendMessage(msgss);
			}
		});
		
		status_p2p = (TextView) findViewById(R.id.status_p2p);
		tv_log = (TextView) findViewById(R.id.tv_log);

		stringBuilder = new StringBuilder("Log:\n");
		tv_log.setText(stringBuilder.toString());
	}

	private class UIHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				//connection successful
				Toast.makeText(MainActivity.this, "connection successful～", Toast.LENGTH_SHORT)
						.show();
				//connected
				status_p2p.setText("connected！");
				break;
			case 1:
				//connecting
				Toast.makeText(MainActivity.this, "connecting～", Toast.LENGTH_SHORT)
						.show();
				break;
			case -1:
				//wrong did
				Toast.makeText(MainActivity.this, "wrong did", Toast.LENGTH_SHORT)
						.show();
				//not connected
				status_p2p.setText("not connected！");
				break;
			case -2:
				//wrong password
				Toast.makeText(MainActivity.this, "wrong password",
						Toast.LENGTH_SHORT).show();
				//not connected
				status_p2p.setText("not connected！");
				break;
			case -3:
				Toast.makeText(MainActivity.this, "Is connected, do not repeat the connection！",
						Toast.LENGTH_SHORT).show();
				status_p2p.setText("connected！");//connected
				break;
			case -4:

				Toast.makeText(MainActivity.this, "Connection has been disconnected！", Toast.LENGTH_SHORT)
						.show();
				status_p2p.setText("Not connected！");
				break;
			case 2:
				Toast.makeText(MainActivity.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;
			case 11:
				insertLog(msg);
				break;
			}
		}
	}

	private void insertLog(Message msg) {
		if (tv_log.getText().equals("Log:\n")) {
			String result = getStrTime() + msg.obj + "\n";
			stringBuilder.append(ToDBC(result));
			tv_log.setText(stringBuilder.toString());
		} else {
			String result = getStrTime() + msg.obj + "\n";
			stringBuilder.insert(5, ToDBC(result));
			tv_log.setText(stringBuilder.toString());
		}
	}

	public static String getStrTime() {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH：mm：ss");
		// 例如：cc_time=1291778220
		long lcc_time = System.currentTimeMillis();
		re_StrTime = sdf.format(new Date(lcc_time));
		return re_StrTime + ":" + "\n";
	}

	private class MessageHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			String message = msg.obj.toString();
			System.out.println("messagetitle=" + msg.what);
			System.out.println("message=" + message);
		}

	}

	private OnClickListener mBtnOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_connect:
				String did = et_did.getText().toString().trim();
				String pwd = et_pwd.getText().toString().trim();
				cm.connectPalals(did, pwd, new ConnectStatusInterface() {
					@Override
					public void callBackConnectStatus(int rs) {
						System.out.println("rs="+rs);
						Message msg = new Message();
						msg.what = rs;
						uiHandler.sendMessage(msg);
					}
				});
				break;
			case R.id.bt_disconnect:
				cm.disConnectPalals();
				break;
			case R.id.bt_jsontree:
				getDeviceJsonTree();
				break;
			case R.id.bt_eventlist:
				String[] ids = new String[] { "1", "3", "16" };
				getEventList(ids);
				break;
			case R.id.bt_subscribeToken:
				getToken();
				break;
			case R.id.bt_sloton:
				openSlot("70", "OnOff", "On", "ttyS0=433/479391", "ttyS0",
						"SK-5808A-1B");
				break;
			case R.id.bt_slotoff:
				closeSlot("70", "OnOff", "Off", "ttyS0=433/479391", "ttyS0",
						"SK-5808A-1B");
				break;
			case R.id.bt_additem:
				addItem("850948", "", "ttyS0", "yes", "Door Alarm Test 1234",
						"YCF208MC-1527-433", "Open", "OpenClose", "2",
						"ttyS0=433/850948");
				break;
			case R.id.bt_removeitem:
				removeItem("3", "ttyS0", "", "YCF208MC-1527-433",
						"Door Alarm Test 1234", "2", true, "ttyS0=433/850948", "850948",
						"3");
				break;
			case R.id.bt_search:
				pm.search(new SearchResultInterface() {
					@Override
					public void callBackSearchResult(String result) {
						System.out.println("result=" + result);
						Message msg = new Message();
						msg.what = 11;
						msg.obj = result;
						uiHandler.sendMessage(msg);
					}
				});
				break;
			case R.id.bt_changepwd:
				changePsw("123456");
				break;
			case R.id.bt_modityitem:
				modifyItem("3", "yes", "ttyS0", "", "YCF208MC-1527-433",
						"Door Alarm Test 1232131", "Open", "OpenClose", "2", true,
						"ttyS0=433/850948", "850948");
				break;
			case R.id.bt_addarmmodel:
				addArmModel("5", "Arming group 1", "no");
				break;
			case R.id.bt_operatearmmodel:
				operateArmModel("11", "yes");
				break;
			case R.id.bt_removesarmmodel:
				removeArmModel("5");
				break;
			case R.id.bt_modifyarmmodel:
				changeLinkArmName("11", "Arming group 2", "no");
				break;
			case R.id.bt_addtoarmmodel:
				addLinkSource("1", "15", "Door alarm test 1", "Open", "OpenClose",
						"ttyS0=433/850948");
				break;
			case R.id.bt_rmfromarmmodel:
				removeLinkSource("1", "15", "Door alarm test 1", "Open", "OpenClose",
						"ttyS0=433/850948");
				break;
			case R.id.bt_rmevent:
				int[] eventtimes = new int[] { 1418891891, 1418891895,
						1418891905 };
				removeEvent("3", eventtimes);
				break;
			case R.id.bt_changeevent:
				int[] eventtimess = new int[] { 1418891891, 1418891895,
						1418891905 };
				changeEvent("3", eventtimess);
				break;
			case R.id.bt_checkfirm:
				checkUpdate();
				break;
			case R.id.bt_upgradefirm:
				upgradeFirmWare("303cfe2654e0d7a965d6fca7ca9d356f",
						"http://114.215.172.125/download_palals/palals.zip",
						"V1.5.2");
				break;

			case R.id.bt_subscribePalalsPush:
				updatePalalsPush(true, "woody");
				break;
			case R.id.bt_subscribeXinGe:
				updateXinGe(true, "woody");
				break;
			case R.id.bt_subscribeVoIp:
				updateVoIp(true, "18706182675");
				break;
			case R.id.bt_subscribeEmail:
				updateEmailPush(true, "389345340@qq.com");
				break;
			case R.id.bt_unsubscribePalalsPush:
				updatePalalsPush(false, "woody");
				break;
			case R.id.bt_unsubscribeXinGe:
				updateXinGe(false, "woody");
				break;
			case R.id.bt_unsubscribeVoIp:
				updateVoIp(false, "18706182675");
				break;
			case R.id.bt_unsubscribeEmail:
				updateEmailPush(false, "389345340@qq.com");
				break;
			case R.id.bt_addcamera:
				addCamera("","PPCN108701UFUYL","admin","WIFICAM","","seat","p2p","STARX-Camera-0001","p2p=PPCN108701UFUYL",true
						,"2","yes");
				break;
			case R.id.bt_updatecamera:
				updateCamera("","PPCN108701UFUYL","admin","Cam123434656","","seat","p2p","STARX-Camera-0001","p2p=PPCN108701UFUYL",true
						,"2","15","yes");
				break;
			}
		}
	};

	private Manager cc;

	protected void connect() {
		String did = et_did.getText().toString().trim();
		String pwd = et_pwd.getText().toString().trim();
		logi("palals:did = " + did + ", pwd = " + pwd);
		cc = Manager.getInstance();
		System.out.println("pwd=" + pwd);
		cc.connect(this, did, pwd);
	}
	
	protected void updateCamera(String group,String cameradid,String user,String name,String password
			,String seat,String busname,String fullmodelno,String bind,Boolean tosaveintodb
			,String parent,String id,String addarm) {
		String callid=String.valueOf(System.currentTimeMillis());
		sm.updateCamera(group, cameradid, user, name, password, seat, busname, fullmodelno, bind, tosaveintodb, parent, id, addarm,callid);
	}

	protected void addCamera(String group,String cameradid,String user,String name,String password
			,String seat,String busname,String fullmodelno,String bind,Boolean tosaveintodb
			,String parent,String addarm) {
		String callid=String.valueOf(System.currentTimeMillis());
		sm.addCamera(group, cameradid, user, name, password, seat, busname, fullmodelno, bind, tosaveintodb, parent, addarm,callid);
		
	}

	private void updateVoIp(boolean iso, String phoneNumber) {
		JSONObject jsonroot = new JSONObject();
		JSONObject jsonparams = new JSONObject();
		long callid = System.currentTimeMillis();
		try {
			jsonroot.put("type", "req");
			jsonroot.put("method", "updateVoipPush");
			jsonroot.put("callid", String.valueOf(callid));
			if (iso)
				jsonparams.put("enable", "yes");
			else
				jsonparams.put("enable", "no");
			jsonroot.put("params", jsonparams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("js=" + jsonroot.toString());
		nm.updateVoipPush(jsonroot.toString(), phoneNumber,
				new onVoipCallBack() {
					@Override
					public void onSuccess(int resultcode) {

					}

					@Override
					public void onFailed(int resultcode) {

					}
				});
	}

	private void updateEmailPush(boolean iso, String email) {
		JSONObject jsonroot = new JSONObject();
		JSONObject jsonparams = new JSONObject();
		long callid = System.currentTimeMillis();
		try {
			jsonroot.put("type", "req");
			jsonroot.put("method", "updateEmailPush");
			jsonroot.put("callid", String.valueOf(callid));
			if (iso)
				jsonparams.put("enable", "yes");
			else
				jsonparams.put("enable", "no");
			jsonroot.put("params", jsonparams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("js=" + jsonroot.toString());
		nm.updateMailPush(jsonroot.toString(), email, new onEmailCallBack() {

			@Override
			public void onSuccess(int resultcode) {

			}

			@Override
			public void onFailed(int resultcode) {

			}
		});
	}

	protected void updatePalalsPush(boolean iso, String uniqeId) {
		JSONObject jsonroot = new JSONObject();
		JSONObject jsonparams = new JSONObject();
		long callid = System.currentTimeMillis();
		try {
			jsonroot.put("type", "req");
			jsonroot.put("method", "updatePalalsPush");
			jsonroot.put("callid", String.valueOf(callid));
			if (iso)
				jsonparams.put("enable", "yes");
			else
				jsonparams.put("enable", "no");
			jsonroot.put("params", jsonparams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("js=" + jsonroot.toString());
		nm.updatePalalsPush(jsonroot.toString(), uniqeId,
				new onPalalsPushCallBack() {

					@Override
					public void onSuccess(int resultcode) {

					}

					@Override
					public void onFailed(int resultcode) {

					}
				});
	}

	// /{"type" : "req", "method" : "UpdateXGPush", "callid":
	// 1417161922870,"params":{"enable":""}}
	protected void updateXinGe(boolean iso, String token) {
		JSONObject jsonroot = new JSONObject();
		JSONObject jsonparams = new JSONObject();
		long callid = System.currentTimeMillis();
		try {
			jsonroot.put("type", "req");
			jsonroot.put("method", "updateXGPush");
			jsonroot.put("callid", String.valueOf(callid));
			if (iso)
				jsonparams.put("enable", "yes");
			else
				jsonparams.put("enable", "no");
			jsonroot.put("params", jsonparams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("js=" + jsonroot.toString());
		nm.updateXGPush(jsonroot.toString(), token, new onXinGeCallBack() {
			@Override
			public void onSuccess(int resultcode) {
				System.out.println("Success");
			}

			@Override
			public void onFailed(int resultcode) {
				System.out.println("Failure，code=" + resultcode);
			}
		});
	}

	protected void upgradeFirmWare(String md5, String url, String version) {
		String callid=String.valueOf(System.currentTimeMillis());
		pm.upgradeFirmWare(md5, url, version,callid);
	}

	private String infos;

	protected void checkUpdate() {
		pm.onCheckPalals(new PalalsCheckCallBack() {
			@Override
			public void checkStatus(int resultcode, String info) {
				if (resultcode == 1) {
					infos = info;
					Message msg = new Message();
					msg.what = 2;
					msg.obj = info;
					System.out.println("infos=" + infos);
					uiHandler.sendMessage(msg);
				}
			}
		});
	}

	protected void changeEvent(String id, int[] eventtimes) {
		String callid=String.valueOf(System.currentTimeMillis());
		em.updateEventCheckState(id, eventtimes,callid);
	}

	protected void removeEvent(String id, int[] eventtimes) {
		String callid=String.valueOf(System.currentTimeMillis());
		em.removeEvent(id, eventtimes,callid);
	}

	protected void removeLinkSource(String armid, String itemid, String name,
			String value, String action, String bind) {
		String callid=String.valueOf(System.currentTimeMillis());
		rm.removeLinkSource(armid, itemid, name, value, action, bind,callid);
	}

	protected void addLinkSource(String armid, String itemid, String name,
			String value, String action, String bind) {
		String callid=String.valueOf(System.currentTimeMillis());
		rm.addLinkSource(armid, itemid, name, value, action, bind,callid);
	}

	protected void changeLinkArmName(String id, String name, String enable) {
		String callid=String.valueOf(System.currentTimeMillis());
		rm.changeLinkArmName(id, name, enable,callid);
	}

	protected void removeArmModel(String id) {
		String callid=String.valueOf(System.currentTimeMillis());
		rm.removeLinkArm(id,callid);
	}

	protected void addArmModel(String id, String name, String enable) {
		String callid=String.valueOf(System.currentTimeMillis());
		rm.addLinkArm(id, name, enable,callid);
	}

	protected void operateArmModel(String id, String value) {
		String callid=String.valueOf(System.currentTimeMillis());
		rm.setArmEnable(id, value,callid);
	}

	/**
	 * 删除设备
	 * 
	 * @param id
	 * @param busname
	 * @param fullmodelno
	 * @param name
	 * @param bind
	 * @param nodeid
	 * @param childid
	 */
	protected void removeItem(String id, String busname, String seat,
			String fullmodelno, String name, String parent,
			Boolean tosaveintodb, String bind, String nodeid, String childid) {
		String callid=String.valueOf(System.currentTimeMillis());
		sm.removeItem(id, busname, seat, fullmodelno, name, parent,
				tosaveintodb, bind, nodeid, childid,callid);
	}

	private void changePsw(String password) {
		String callid=String.valueOf(System.currentTimeMillis());
		int rc = pm.modifyPassword(password,callid);
	}

	protected void addItem(String nodeid, String seat, String busname,
			String addarm, String name, String fullmodelno, String value,
			String action, String parent, String bind) {
		String callid=String.valueOf(System.currentTimeMillis());
		sm.addItem(nodeid, seat, busname, addarm, name, fullmodelno, value,
				action, parent, bind,callid);
	}

	protected void modifyItem(String id, String addarm, String busname,
			String seat, String fullmodelno, String name, String value,
			String action, String parent, Boolean tosaveintodb, String bind,
			String nodeid) {
		String callid=String.valueOf(System.currentTimeMillis());
		sm.updateItem(id, addarm, busname, seat, fullmodelno, name, value,
				action, parent, tosaveintodb, bind, nodeid,callid);
	}

	private void openSlot(String id, String action, String value, String bind,
			String busname, String fullmodelno) {
		String callid=String.valueOf(System.currentTimeMillis());
		sm.controlItem(id, action, value, bind, busname, fullmodelno,callid);
	}

	protected void closeSlot(String id, String action, String value,
			String bind, String busname, String fullmodelno) {
		String callid=String.valueOf(System.currentTimeMillis());
		sm.controlItem(id, action, value, bind, busname, fullmodelno,callid);
	}

	protected void getToken() {
		// cc.getPalalsIPNSubscribeToken();
		nm.initial(getApplicationContext(), new NotifyInterface() {
			@Override
			public void OnSuccess(int code, String token) {
				System.out.println("code=" + code + ",token=" + token);
				SharedPreferences setting = getSharedPreferences(
						StarxNetConst.SP_PALALS_FILE, Context.MODE_PRIVATE);
				setting.edit().putString("SUBSCRIBETOKEN", token).commit();
				String subscribetoken = setting.getString("SUBSCRIBETOKEN",
						null);
				System.out.println("subscribetoken On Success="
						+ subscribetoken);
			}

			@Override
			public void OnFailed(int code, String token) {

			}

		});
	}

	EventMananger em = new EventMananger();

	protected void getEventList(String[] ids) {
		String callid=String.valueOf(System.currentTimeMillis());
		em.getEventListMultiple(ids,callid);
	}

	SensorManager sm = new SensorManager();

	protected void getDeviceJsonTree() {
		String callid=String.valueOf(System.currentTimeMillis());
		sm.getDevicejsontree(callid);
	}

	private void stop() {
		cc.disConnectPalals();
	}

	private void logi(String msg) {
		Log.i(TAG, msg);
	}

	private void logw(String msg) {
		Log.w(TAG, msg);
	}

	@SuppressWarnings("unused")
	private void logv(String msg) {
		Log.v(TAG, msg);
	}

	@Override
	public void callBackSensorManager(int what, String msg) {
		System.out.println("msg=" + msg);
	}

	@Override
	public void callBackEventManager(int what, String msg) {
		System.out.println("msg=" + msg);
	}

	@Override
	public void callBackPalalsManager(int what, String params) {
		System.out.println("what=" + what + "params=" + params);
	}

	@Override
	public void callAllMessage(String msg) {
		System.out.println("Message In One=" + msg);
		Message msgss = new Message();
		msgss.what = 11;
		msgss.obj = msg;
		uiHandler.sendMessage(msgss);
	}

	//
	// @Override
	// public void callBackMessageArrive(int what,String message) {
	// Message msg=new Message();
	// msg.what=what;
	// msg.obj=message;
	// messageHandler.sendMessage(msg);
	// }
	//
	// @Override
	// public void callBackArmingMessage(String msg) {
	// System.out.println("arming message~~~~~");
	// }
	//
	// @Override
	// public void callBackFirmwareUpgrade(String msg) {
	// System.out.println("firmwire upgrade~~~~~");
	//
	// }

	// @Override
	// public void callBackSearchResult(String[] Dids) {
	// for (int i = 0; i < Dids.length; i++) {
	// System.out.println("Dids="+Dids[i]);
	// }
	// }

	// /**
	// * 搜索Palals的返回结果
	// */
	// @Override
	// public void callBackSearchResult(String[] Dids) {
	// for (int i = 0; i < Dids.length; i++) {
	// System.out.println("Dids="+Dids[i]);
	// }
	// }

	/**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	private static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

}
