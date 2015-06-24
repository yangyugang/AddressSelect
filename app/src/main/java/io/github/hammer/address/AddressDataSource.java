package io.github.hammer.address;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Hammer on 6/24/15.
 */
public class AddressDataSource {


    SparseArrayCompat<Address> sProvinces = new SparseArrayCompat<>();
    SparseArrayCompat<ArrayList<Address>> sCities = new SparseArrayCompat<>(); // key=province address no
    SparseArrayCompat<ArrayList<Address>> sAreas = new SparseArrayCompat<>();

    public void test(Context context) {
        BufferedReader br = null;
        try {
            InputStream in = context.getResources().getAssets().open("address");
            br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line =br.readLine()) != null) {
                String[] split = line.split("\\s+");
                Integer addressNo = Integer.parseInt(split[0]);
                Address address = new Address(addressNo, split[1], Integer.parseInt(split[2]));
                if (addressNo % 10000 == 0) { // 说明是province
                    sProvinces.append(addressNo, address);
                    continue;
                }
                if (addressNo % 100 == 0) { // 说明是city
                    Integer provinceAddressNo = (addressNo / 10000) * 10000 ;
                    ArrayList<Address> cities = sCities.get(provinceAddressNo, new ArrayList<Address>());
                    if (cities.size() == 0) {
                        sCities.append(provinceAddressNo, cities);
                    }
                    cities.add(address);
                    continue;
                }
                Integer cityAddressNo = (addressNo / 100) * 100 ;
                ArrayList<Address> areas = sAreas.get(cityAddressNo, new ArrayList<Address>());
                if (areas.size() == 0) {
                    sCities.append(cityAddressNo, areas);
                }
                areas.add(address);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {

                    br.close();
                    br = null;
                }
            } catch (IOException e) {
            }
        }
    }
}
