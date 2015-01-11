package com.example.geospot;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends Activity {
	
	String red;

	private final String TAG = getClass().getSimpleName();
	private GoogleMap mMap;
	private String[] places;
	private LocationManager locationManager;
	private Location loc;
	private String rad = "1";
	public static ArrayList<Place> findPlaces = new ArrayList<Place>();

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		initCompo();
		places = getResources().getStringArray(R.array.places);
		currentLocation();
		final ActionBar actionBar = getActionBar();
		Bundle bundle = getIntent().getExtras();
		rad = bundle.getString("radius");
		red = bundle.getString("name");
		/*actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(ArrayAdapter.createFromResource(
				this, R.array.places, android.R.layout.simple_list_item_1),
				new ActionBar.OnNavigationListener() {

					@Override
					public boolean onNavigationItemSelected(int itemPosition,
							long itemId) {
						Log.e(TAG,
								places[itemPosition].toLowerCase().replace("-",
										"_"));
						if (loc != null) {
							mMap.clear();
							new GetPlaces(MapActivity.this,
									places[itemPosition].toLowerCase().replace(
											"-", "_").replace(" ", "_")).execute();
						}
						return true;
					}

				});*/
		
		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker arg0) {
				// TODO Auto-generated method stub
				Place temp = new Place();
				;
				for (int i = 0; i < findPlaces.size(); i++) {
					if (arg0.getTitle().equals(findPlaces.get(i).getName())
							&& arg0.getSnippet().equals(
									findPlaces.get(i).getVicinity())) {
						temp.setId(findPlaces.get(i).getId());
						temp.setVicinity(findPlaces.get(i).getVicinity());
						temp.setName(findPlaces.get(i).getName());
					}
				}
				Intent i = new Intent(MapActivity.this, EstablishmentPage.class);
				System.out.println(temp.getName());
				i.putExtra("name", arg0.getTitle());
				
				System.out.println("mapactivity id is " + arg0.getId());
				// i.putExtra("place_id", place_id);
				// add mo put extra id

				startActivity(i);

			}

		});

	}	

	private class GetPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {

		private ProgressDialog dialog;
		private Context context;
		private String places;

		public GetPlaces(Context context, String places) {
			this.context = context;
			this.places = places;
		}

		@Override
		protected void onPostExecute(ArrayList<Place> result) {
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			for (int i = 0; i < result.size(); i++) {
				mMap.addMarker(new MarkerOptions()
						.title(result.get(i).getName())
						.position(
								new LatLng(result.get(i).getLatitude(), result
										.get(i).getLongitude()))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.pin))
						.snippet(result.get(i).getVicinity()));
			}
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(result.get(0).getLatitude(), result
							.get(0).getLongitude())) // Sets the center of the map to
											// Mountain View
					.zoom(16) // Sets the zoom
					.tilt(30) // Sets the tilt of the camera to 30 degrees
					.build(); // Creates a CameraPosition from the builder
			mMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
			
			dialog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(context);
			dialog.setCancelable(false);
			dialog.setMessage("Loading..");
			dialog.isIndeterminate();
			dialog.show();
		}

		@Override
		protected ArrayList<Place> doInBackground(Void... arg0) {
			PlacesService service = new PlacesService(
					"AIzaSyC2gs_FPvegt11d2gNq3EO3TGK6jsXxKN4", rad);
			ArrayList<Place> findPlaces = service.findPlaces(loc.getLatitude(), // 28.632808
					loc.getLongitude(), places); // 77.218276

			for (int i = 0; i < findPlaces.size(); i++) {

				Place placeDetail = findPlaces.get(i);
				Log.e(TAG, "places : " + placeDetail.getName());
			}
			return findPlaces;
		}

	}

	private void initCompo() {
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("DefaultLocale")
	private void currentLocation() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		String provider = locationManager
				.getBestProvider(new Criteria(), false);

		Location location = locationManager.getLastKnownLocation(provider);

		//if (location == null) {
			locationManager.requestLocationUpdates(provider, 0, 0, listener);
		//} else {
			loc = location;
			new GetPlaces(MapActivity.this, places[0].toLowerCase().replace(
					"-", "_")).execute();
			Log.e(TAG, "location : " + location);
		//}

	}

	private LocationListener listener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onLocationChanged(Location location) {
			Log.e(TAG, "location update : " + location);
			loc = location;
			locationManager.removeUpdates(listener);
		}
	};

}
