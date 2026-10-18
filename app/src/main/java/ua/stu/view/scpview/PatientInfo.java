package ua.stu.view.scpview;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import ua.stu.view.fragments.AddrPatientFragment;
import ua.stu.view.fragments.BloodPressFragment;
import ua.stu.view.fragments.DiagnoseFragment;
import ua.stu.view.fragments.MedicalHistoryFragment;
import ua.stu.view.fragments.PrivatePatientInfoFragment;
import ua.stu.view.temporary.InfoP;
import android.os.Bundle;

public class PatientInfo extends AppCompatActivity {
	private static final String TAG = "PatientInfo";
	
	private PrivatePatientInfoFragment privatePatientInfo;
	private BloodPressFragment bloodPress;
	private AddrPatientFragment addrPatient;
	private DiagnoseFragment diagnose;
	private MedicalHistoryFragment medicalHistory;
	private InfoP infoP;
	private TabLayout tabLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patientinfo);

		init();
		setupTabs();
	}

	private void init() {
		String patientKey = getResources().getString(R.string.app_patient);
		HashMap<?, ?> table = (HashMap<?, ?>) getIntent().getSerializableExtra(patientKey);
		if (table != null) {
			infoP = (InfoP) table.get(patientKey);
		}

		privatePatientInfo = new PrivatePatientInfoFragment(infoP);
		bloodPress = new BloodPressFragment(infoP);
		addrPatient = new AddrPatientFragment(infoP);
		diagnose = new DiagnoseFragment(infoP);
		medicalHistory = new MedicalHistoryFragment(infoP);
	}

	private void setupTabs() {
		tabLayout = findViewById(R.id.tab_layout);
		if (tabLayout == null) return;

		tabLayout.addTab(tabLayout.newTab().setText(R.string.title_private_patient).setIcon(R.drawable.patient));
		tabLayout.addTab(tabLayout.newTab().setText(R.string.title_pressure_patient).setIcon(R.drawable.pressure));
		tabLayout.addTab(tabLayout.newTab().setText(R.string.title_address_patient).setIcon(R.drawable.address));
		tabLayout.addTab(tabLayout.newTab().setText(R.string.title_diagnosis_patient).setIcon(R.drawable.diagnosis));
		tabLayout.addTab(tabLayout.newTab().setText(R.string.title_history_patient).setIcon(R.drawable.history));

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

		// Показуємо першу вкладку за замовчуванням
		showFragment(0);
	}

	private void showFragment(int position) {
		Fragment target = null;
		switch (position) {
			case 0: target = privatePatientInfo; break;
			case 1: target = bloodPress; break;
			case 2: target = addrPatient; break;
			case 3: target = diagnose; break;
			case 4: target = medicalHistory; break;
		}

		if (target != null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.patient_fragment_container, target)
					.commitAllowingStateLoss();
		}
	}
}
