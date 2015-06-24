package io.github.hammer.address;

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


    private ArrayList<Address> mProvinces = new ArrayList<>();
    private SparseArrayCompat<ArrayList<Address>> mCities = new SparseArrayCompat<>(); // key=province address no
    private SparseArrayCompat<ArrayList<Address>> mAreas = new SparseArrayCompat<>();

    // Make this class a thread safe singleton
    private static class SingletonHolder {
        public static final AddressDataSource INSTANCE = new AddressDataSource();
    }

    public static AddressDataSource getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private AddressDataSource() {
        // Create the handler on the main thread
        initialize();
    }

    public void initialize() {
        BufferedReader br = null;
        try {
            InputStream in = App.getAppContext().getResources().getAssets().open("address");
            br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line =br.readLine()) != null) {
                String[] split = line.split("\\s+");
                Integer addressNo = Integer.parseInt(split[0]);
                Address address = new Address(addressNo, split[1], Integer.parseInt(split[2]));
                if (addressNo % 10000 == 0) { // 说明是province
                    mProvinces.add(address);
                    continue;
                }
                if (addressNo % 100 == 0) { // 说明是city
                    Integer provinceAddressNo = (addressNo / 10000) * 10000 ;
                    ArrayList<Address> cities = mCities.get(provinceAddressNo, new ArrayList<Address>());
                    if (cities.size() == 0) {
                        mCities.append(provinceAddressNo, cities);
                    }
                    cities.add(address);
                    continue;
                }
                Integer cityAddressNo = (addressNo / 100) * 100 ;
                ArrayList<Address> areas = mAreas.get(cityAddressNo, new ArrayList<Address>());
                if (areas.size() == 0) {
                    mCities.append(cityAddressNo, areas);
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

    public ArrayList<Address> getProvinces() {
        return mProvinces;
    }

    public ArrayList<Address> getCitiesByProvinceNo(Integer provinceNo) {
        return mCities.get(provinceNo);
    }

    public ArrayList<Address> getAreasByCityNo(Integer cityNo) {
        return mAreas.get(cityNo);
    }
}
