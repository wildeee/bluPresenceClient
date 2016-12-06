package br.com.wilderossi.blupresenceclient;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.wilderossi.blupresenceclient.components.BluetoothSocketConnector;
import br.com.wilderossi.blupresenceclient.components.Callback;
import br.com.wilderossi.blupresenceclient.navigation.SingletonHelper;

public class ProcurarConexoesListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView listViewDevices;

    private BluetoothAdapter adapter;

    private ArrayAdapter<String> devicesAdapter;
    private List<BluetoothDevice> devices;

    private static final String DEVICE_LIST_FORMAT = "%s\n%s";

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devicesAdapter.add(String.format(DEVICE_LIST_FORMAT, device.getName(), device.getAddress()));
                devices.add(device);
            }
        }
    };

    @Override
    public int getActivity() {
        return R.layout.procurar_conexoes_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = SingletonHelper.bluetoothAdapter;

        devices = new ArrayList<>();
        devicesAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>()
        );
        listViewDevices = (ListView) findViewById(R.id.listViewDevices);
        listViewDevices.setAdapter(devicesAdapter);
        listViewDevices.setOnItemClickListener(this);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        adapter.startDiscovery();
    }

    @Override
    public void finish() {
        super.finish();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BluetoothDevice deviceClicado = devices.get(position);
        try {
            BluetoothSocket socket = deviceClicado.createRfcommSocketToServiceRecord(SingletonHelper.APP_UUID);
            adapter.cancelDiscovery();
            SingletonHelper.procurarConexoesListActivity = this;
            new Thread(new BluetoothSocketConnector(socket)).start();
        } catch (IOException e) {
            Log.e("ProcurarConexoesList", e.getMessage());
        }
    }

    public void showConnectionErrorDialog() {
        this.runOnUiThread(new Runnable()
        {
            public void run() {
                Toast.makeText(getApplicationContext(), "Problema na conexão", Toast.LENGTH_LONG).show();
            }
        });
    }
}
