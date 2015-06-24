package io.github.hammer.address;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hammer on 6/24/15.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddressDataSource dataSource = new AddressDataSource();
        dataSource.test(this);
    }
}
