package raunimii.geotrack;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
 
/**
 * Activity for the splash screen
 * @author Miika Raunio
 *
 */
public class SplashActivity extends FragmentActivity{
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

}