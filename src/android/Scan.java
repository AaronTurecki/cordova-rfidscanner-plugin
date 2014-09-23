package com.assettagz.cordova.plugin.scan;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Message;
import android.util.Log;

import com.phychips.rcp.*;

@TargetApi(19)
public class Scan extends CordovaPlugin implements iRcpEvent2,
		OnCompletionListener
{
	private CallbackContext callbackContext;
	public int maxTags = 1;
	public int maxTime = 100;
	public int repeatCycle = 0;
	public Byte resultCode = 0x00;

	/**
	 * Constructor.
	 */
	public Scan() {

	}

	@Override
	public boolean execute (String action, JSONArray args, CallbackContext callbackContext) throws JSONException
	{

		if( action.equals("read") )
		{

			AlertDialog.Builder builder1 = new AlertDialog.Builder(cordova.getActivity());
			builder1.setMessage("success");
		    AlertDialog alert11 = builder1.create();
			alert11.show();

			this.callbackContext = callbackContext;
			callbackContext.success("success");

			RcpApi2 rcpAPI = RcpApi2.getInstance();
			rcpAPI.setOnRcpEventListener(this);
			boolean t = rcpAPI.open();
			// // setVolumeMax();
			boolean k = rcpAPI.startReadTagsWithRssi(maxTags, maxTime, repeatCycle);
			
			return true;
		}
		return false;
	}

	@Override
	public void onBatteryStateReceived(int[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChannelReceived(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
    public void onFailureReceived(final int[] data) {
        // TODO Auto-generated method stub
		
        System.out.println("onFailureReceived");
        resultCode = (byte)(data[0] & 0xFF);
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run()
            {  
               AlertDialog.Builder builder1 = new AlertDialog.Builder(cordova.getActivity());
			   builder1.setMessage("Error: Error Code = 0x" + RcpLib.byte2string((byte)(data[0]&0xff)) + "\nTry again.");
		       AlertDialog alert11 = builder1.create();
			   alert11.show();
            }
        });
    }

	@Override
	public void onFhLbtReceived(int[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onQueryParamReceived(int[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReaderInfoReceived(int[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegionReceived(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResetReceived() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSelectParamReceived(int[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccessReceived(int[] arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTagMemoryLongReceived(int[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTagMemoryReceived(final int[] data) {
		// TODO Auto-generated method stub
		
		cordova.getActivity().runOnUiThread(new Runnable() {
            public void run(){
            	String dataText = RcpLib.int2str(data);
            	
            	AlertDialog.Builder builder1 = new AlertDialog.Builder(cordova.getActivity());
				builder1.setMessage("TID: " + dataText);
				AlertDialog alert11 = builder1.create();
				alert11.show();
            }
        });
	}
	
	@Override
	public void onTagReceived(int[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTagWithRssiReceived(final int[] arg0, final int arg1) {
		// TODO Auto-generated method stub

		cordova.getActivity().runOnUiThread(new Runnable(){
			public void run(){
				String epc = toHexString(arg0).substring(4);
				String rssi = Integer.toString(arg1);
								
//				int accessPassword = 0x000000;
//				int startAddress = 0x0000;
//				int targetLength = 0x02;

				RcpApi2.getInstance().readFromTagMemory(
						0, // Access Password
						RcpLib.convertStringToByteArray(epc),
						2, // TID
						0, // Start
						0); // Length
				
				Log.d("EPC:", epc);
				Log.d("RSSI:", rssi);
		
				AlertDialog.Builder builder1 = new AlertDialog.Builder(cordova.getActivity());
				builder1.setMessage("EPC: " + epc + "\nRSSI: " + rssi);
				AlertDialog alert11 = builder1.create();
				alert11.show();
			}
        });
	}

	public static String toHexString(int[] data)
    {
       StringBuffer sb = new StringBuffer();        
       for (int i = 0; i < data.length; i++)
       {
           sb.append(String.format("%02X", data[i]&0xff));
       }
       return sb.toString();
    }

	@Override
	public void onTxPowerLevelReceived(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub

	}
	
  //   private void setVolumeMax()
  //   {
  //   	AudioManager audioManager = (AudioManager) cordova.getActivity().getSystemService(Context.AUDIO_SERVICE);

		// if (audioManager.getStreamMaxVolume(android.media.AudioManager.STREAM_MUSIC) != audioManager
		// 		.getStreamVolume(android.media.AudioManager.STREAM_MUSIC))
		// {
		// 	audioManager.setStreamVolume(android.media.AudioManager.STREAM_MUSIC, audioManager
		// 		    .getStreamMaxVolume(android.media.AudioManager.STREAM_MUSIC), 1);
		// }
  //   }

}

