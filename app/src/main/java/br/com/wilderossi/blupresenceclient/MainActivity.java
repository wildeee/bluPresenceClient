package br.com.wilderossi.blupresenceclient;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import br.com.wilderossi.blupresenceclient.navigation.SingletonHelper;

public class MainActivity extends BaseActivity {

    @Override
    public int getActivity() {
        return R.layout.activity_main;
    }

    public void onClickProcurarConexoes(View view){
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
            return;
        }

        SingletonHelper.bluetoothAdapter = mBluetoothAdapter;
        redirectTo(ProcurarConexoesListActivity.class);
    }

}
