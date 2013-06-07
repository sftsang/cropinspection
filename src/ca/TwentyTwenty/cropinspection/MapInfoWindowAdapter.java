package ca.TwentyTwenty.cropinspection;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
  LayoutInflater inflater=null;

  MapInfoWindowAdapter(LayoutInflater inflater) {
    this.inflater=inflater;
  }

  @Override
  public View getInfoWindow(Marker marker) {
    return(null);
  }

  @Override
  public View getInfoContents(Marker marker) {
    View popup=inflater.inflate(R.layout.custom_info_contents, null);

    TextView tv=(TextView)popup.findViewById(R.id.field_no_info);
    tv.setText(marker.getTitle());
    
    tv=(TextView)popup.findViewById(R.id.field_location_info);
    tv.setText(marker.getSnippet());

    return(popup);
  }
}