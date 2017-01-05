package br.com.wilderossi.blupresenceclient;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import br.com.wilderossi.blupresenceclient.navigation.SingletonHelper;

public class MainActivity extends BaseActivity {

    private static final Integer BLUETOOTH_PERMISSION_REQUEST = 1;

    @Override
    public int getActivity() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
        }
    }

    public void onClickProcurarConexoes(View view){
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, BLUETOOTH_PERMISSION_REQUEST);
            return;
        }

        SingletonHelper.bluetoothAdapter = mBluetoothAdapter;
        redirectTo(ProcurarConexoesListActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BLUETOOTH_PERMISSION_REQUEST){
            if (resultCode == -1){
                onClickProcurarConexoes(null);
            }
        }
    }
}
