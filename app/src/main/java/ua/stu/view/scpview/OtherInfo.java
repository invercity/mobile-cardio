package ua.stu.view.scpview;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import ua.stu.view.fragments.DeviceForECGFragment;
import ua.stu.view.fragments.ECGInfoFragment;
import ua.stu.view.temporary.InfoO;
import android.os.Bundle;

public class OtherInfo extends AppCompatActivity {
	private static final String TAG = "OtherInfo";
	
	private DeviceForECGFragment device;
	private ECGInfoFragment ecgInfo;
	private InfoO infoO;
	private TabLayout tabLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.otherinfo);
		
		init();
		setupTabs();
	}

	private void init() {
		String otherKey = getResources().getString(R.string.app_other);
		HashMap<?, ?> table = (HashMap<?, ?>) getIntent().getSerializableExtra(otherKey);
		if (table != null) {
			infoO = (InfoO) table.get(otherKey);
		}

		device = new DeviceForECGFragment(infoO);
		ecgInfo = new ECGInfoFragment(infoO);
	}

	private void setupTabs() {
		tabLayout = findViewById(R.id.tab_layout);
		if (tabLayout == null) return;

		tabLayout.addTab(tabLayout.newTab().setText(R.string.title_device_ecg).setIcon(R.drawable.device));
		tabLayout.addTab(tabLayout.newTab().setText(R.string.title_ecg_info).setIcon(R.drawable.ecginfo));

		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				showFragment(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {
			}
		});

		showFragment(0);
	}

	private void showFragment(int position) {
		Fragment target = null;
		if (position == 0) {
			target = device;
		} else if (position == 1) {
			target = ecgInfo;
		}

		if (target != null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.other_fragment_container, target)
					.commitAllowingStateLoss();
		}
	}
}
