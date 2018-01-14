package mario.ochoa.bbva;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Set;

public class DispositivosBT extends AppCompatActivity {

    public static final String TAG = "DispositivoBT";
    public static final String EXTRA_DEVICE_ADRESS = "device_adress";
    ListView idLista;

    private BluetoothAdapter mbtAdapter;
    private ArrayAdapter<String> mPairedDeviceArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_bt);
    }
     @Override
    public void onResume(){
         super.onResume();

        VerificarEstadoBT();

         mPairedDeviceArrayAdapter = new ArrayAdapter<String>(this,R.layout.nombre_dispositivos);

         idLista = (ListView)findViewById(R.id.idLista);
         idLista.setAdapter(mPairedDeviceArrayAdapter);
         idLista.setOnItemClickListener(mDeviceClickListener);

         mbtAdapter = BluetoothAdapter.getDefaultAdapter();

         Set<BluetoothDevice> pairedDevices = mbtAdapter.getBondedDevices();

         if (pairedDevices.size() > 0){
             for (BluetoothDevice device: pairedDevices){
                 mPairedDeviceArrayAdapter.add(device.getName() + "\n" + device.getAddress());
             }
         }
     }

     private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            String info = ((TextView)v).getText().toString();
             String adress = info.substring(info.length()-17);

             Intent i =new Intent(DispositivosBT.this, MenuP.class);
             i.putExtra(EXTRA_DEVICE_ADRESS, adress);
             startActivity(i);
         }
     };
    private void VerificarEstadoBT() {
        mbtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mbtAdapter==null){
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }else {
            if (mbtAdapter.isEnabled()){
                Log.d(TAG,"..Bluetooth Activado");
            }else {
                Intent enableBtInent  = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtInent,1);
            }
        }
    }


}
