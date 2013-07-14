package com.google.zxing;

import com.pstreets.nfc.NfcActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        find_and_modify_button();   
        MyApplication.getInstance().addActivity(this);
     
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode != 10 || data == null)
    		return;
    	
    	String contents = data.getStringExtra("SCAN_RESULT");
    	
    	this.setTitle(contents);
    	Intent intent = new Intent("com.google.zxing.ResultActivity");
    	intent.setClass(MainActivity.this, ResultActivity.class);
    	Bundle bundle=new Bundle();
    	bundle.putString("name", contents);
    	intent.putExtras(bundle); 
    	//startActivityForResult(intent, 0);      
    	MainActivity.this.startActivity(intent);
    
   // 	this.setTitle(data.getStringExtra("SCAN_RESULT"));
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menuu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void find_and_modify_button(){
    	Button button_barcode =(Button)findViewById(R.id.button_barcode);        //ͨ����Դ��IDΪbutton����Դ��ʵ����button����   
    	button_barcode.setOnClickListener(button_listener_barcode);              //���ö���button�ļ�����  
    	Button button_qrcode =(Button)findViewById(R.id.button_qrcode);
    	button_qrcode.setOnClickListener(button_listener_qrcode);
    	Button button_nfc =(Button)findViewById(R.id.button_nfc);
    	button_nfc.setOnClickListener(button_listener_nfc);
        Button button_exit = (Button)findViewById(R.id.button_exit);
        button_exit.setOnClickListener(button_listener_exit);
    }
    private Button.OnClickListener button_listener_barcode =new Button.OnClickListener(){  //��Ա��ť��������       
    	  @Override   
    	  public void onClick(View v) {       
    		  //��ť�¼�       
    		  // TODO Auto-generated method stub   
    		  Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    		  intent.putExtra("SCAN_MODE", "TM_CODE_MODE");
    		  MainActivity.this.startActivityForResult(intent, 10);
    	}  
     };
     private Button.OnClickListener button_listener_qrcode =new Button.OnClickListener()
     {
    	 @Override 
    	 public void onClick(View v)
    	 {
    		  //��ť�¼�       
   		  // TODO Auto-generated method stub   
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			MainActivity.this.startActivityForResult(intent, 10);
    	 }
     };
     
     private Button.OnClickListener button_listener_nfc =new Button.OnClickListener()
     {
   	  @Override
   	  public void onClick(View v)
   	  {
   		  Intent intent = new Intent(MainActivity.this,NfcActivity.class);
   		  MainActivity.this.startActivity(intent);
   		  MainActivity.this.finish();
   	  }
     };
     
     private Button.OnClickListener button_listener_exit =new Button.OnClickListener()
     {
    	 @Override 
    	 public void onClick(View v)
    	 {
    		  //��ť�¼�       
   		 int pid = android.os.Process.myPid();
    		 android.os.Process.killProcess(pid);   //ɱ����ǰ����
    		 MainActivity.this.finish();      //ɱ����ǰ����
    		 MyApplication.getInstance().exit();
    	 }
     };
 
}
