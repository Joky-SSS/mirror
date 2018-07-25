package com.jokysss.mirror.widget;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

public class LocationView extends View implements View.OnClickListener {

    private static final String TAG = LocationView.class.getName();
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mWidth;
    private int mHeight;
    private LocationManager manager;
    private String location;
    private MyLocationListener listener = new MyLocationListener();

    public LocationView(Context context) {
        this(context, null);
    }

    public LocationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LocationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (manager == null) {
            manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        manager = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        String location = getLocation();
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(40);
        canvas.drawText(location, mWidth / 2, mHeight / 2, mPaint);
    }

    private String getLocation() {
        String provider = null;
        List<String> providers = manager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        }
        if (TextUtils.isEmpty(provider)) {
            return "canot get location";
        } else {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return "no permission to get location";
            }
            Location location = manager.getLastKnownLocation(provider);
            if (location == null) {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
                location = listener.current();
            }
            if (location == null) {
                location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            String result = "location is null";
            if (location != null) {
                result = location.getProvider() + " => 纬度为：" + location.getLatitude() + ",经度为：" + location.getLongitude();
            }
            return result;
        }

    }

    @Override
    public void onClick(View v) {
        invalidate();
    }

    private class MyLocationListener implements LocationListener {
        Location mLastLocation;
        boolean mValid = false;

        @Override
        public void onLocationChanged(Location newLocation) {
            if (newLocation.getLatitude() == 0.0 && newLocation.getLongitude() == 0.0) {
                // Hack to filter out 0.0,0.0 locations
                return;
            }
            if (!mValid) {
                Log.e(TAG, "Got first location.");
            }
            mLastLocation.set(newLocation);
            Log.e(TAG, "the newLocation is " + newLocation.getLongitude() + "x" + newLocation.getLatitude());
            mValid = true;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.OUT_OF_SERVICE:
                case LocationProvider.TEMPORARILY_UNAVAILABLE: {
                    mValid = false;
                    break;
                }
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, " support current " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "no support current " + provider);
            mValid = false;
        }

        public Location current() {
            return mValid ? mLastLocation : null;
        }
    }
}
