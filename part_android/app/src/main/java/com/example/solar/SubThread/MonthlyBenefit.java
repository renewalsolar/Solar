package com.example.solar.SubThread;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MonthlyBenefit extends Fragment {
    private String htmlPageUrl
            = "https://www.weather.go.kr/weather/climate/average_30years.jsp?yy_st=2011&stn=0&norm=Y&obs=SS&x=24&y=13";
    private Geocoder geocoder;
    private LocationManager lm;

    private ArrayList<String> regions = new ArrayList<>();
    private ArrayList<Float[]> pos = new ArrayList<>(); // 위도, 경도
    private ArrayList<Float> sunshine = new ArrayList<>(); // 연평년 일조합

    private float curSunshine;
    private String curpos = null;
    private String predictDev = null;

    public TextView uiCurpos, uiPredictbenefit;
    public ImageView loading_view;

    public MonthlyBenefit(final Context context, final TextView uiCurpos,
                          final TextView uiPredictbenefit, final ImageView loading_view) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.uiCurpos = uiCurpos;
        this.uiPredictbenefit = uiPredictbenefit;
        this.loading_view = loading_view;
        geocoder = new Geocoder(context);
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // run Thread
        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                crawing();
                Looper.loop();
            }
        }).start();
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                uiCurpos.setText("현재 " + curpos + "에서 ");
                uiPredictbenefit.setText("\n월 " + predictDev + "원 수익 예상");

                loading_view.setVisibility(View.GONE);        }
        }
    };

    //1
    public void crawing() {
        try {
            int even = 1;
            float tmpNum;
            Connection.Response resp = Jsoup.connect(htmlPageUrl).execute();
            Document doc = Jsoup.parse(resp.body());
            System.out.println("this is Month");
            // crawling
            Elements titles = doc.select("tbody tr>td");

            for (Element e : titles) {
                System.out.println("it's on running");
                if (even == 1) {
                    regions.add(e.text().trim().split(" |\\(")[1]);
                    even = 0;
                } else {
                    sunshine.add(Float.parseFloat(e.text().trim()));
                    even = 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            deleteZeroRegions();
        }

    }

    //2
    public void deleteZeroRegions() {
        try {
            int size = regions.size();
            for (int i = 0; i < size; i++)
                if (sunshine.get(i) == 0.0f) {
                    regions.remove(i);
                    sunshine.remove(i--);
                    size--;
                }
        } catch (Exception e) {
            Log.e("ERRRR", e.toString());
        } finally {
            initPos(geocoder);
        }
    }

    //3 take too long
    public void initPos(Geocoder geocoder) {
        Log.e("ERERERER", "333333333333");

        // int fnpbMaxSize;

        try {
            List<Address> list = null;
            int size = regions.size();

            for (int i = 0; i < size; i++) {
                try {
                    list = geocoder.getFromLocationName(
                            regions.get(i), // 지역 이름
                            1); // 읽을 개수
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (list != null) {
                    if (list.size() == 0) {
                        regions.remove(i--);
                        size--;
                    } else {
                        pos.add(new Float[]{
                                (float) (Math.round(list.get(0).getLatitude() * 100d) / 100d),
                                (float) (Math.round(list.get(0).getLongitude() * 100d) / 100d)
                                // 소숫점 둘째 자리까지 잘라 float형으로 만든다
                                // 정확한 값은 double형으로 받아야 한다.
                        });
                    }
                }
            }
        } catch (Exception e) {
            Log.e("ERRRR", e.toString());
        } finally {
            getGPSPos(lm);
        }
    }

    //4
    public void getGPSPos(LocationManager lm) {
        Log.e("ERERERER", "getGPSPos4444444444");

        try {
            // this is thread!!! then running multiply
            // 사용자에게 GPS 권한 및 활성화 요청
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    100, // 통지사이의 최소 시간간격 (miliSecond)
                    0, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    100, // 통지사이의 최소 시간간격 (miliSecond)
                    0, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //4-1
    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.
            Log.e("ERERERER", "44444444-1111111");

            DecimalFormat myFormatter = new DecimalFormat("#,###");

            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도

            float fLat = (float) (Math.round(latitude * 100d) / 100d);
            float fLon = (float) (Math.round(longitude * 100d) / 100d);
            StringBuilder curRegion = new StringBuilder();
            curSunshine = nearestRegionVal(fLat, fLon, curRegion);
            // 연평년, "\n월 %f 원 수익 예상"; kWh × 93.3 원
            predictDev = myFormatter.format(Math.round(
                    curSunshine / 12 * 3 * (float) 93.3)); // hr * kw
            curpos = curRegion.toString(); // "현재 %s 시에서 "

            // in thead ui update
            mHandler.post(new Runnable() {
                // new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(0);
                }
            });

            // stop - onLocationChanged
            lm.removeUpdates(mLocationListener);
        }

        public void onProviderDisabled(String provider) {
            // Disabled시
            // Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            // Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            // Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };

    //4-2
    private float nearestRegionVal(float lat, float lon, StringBuilder curRegion) {
        int nearestIdx = -1;
        float minVal = -1, tmpVal;

        Log.e("ERERERER", "44444444-22222222");

        for (int i = 0; i < pos.size(); i++) {
            tmpVal = ((lat - pos.get(i)[0]) * (lat - pos.get(i)[0]))
                    + ((lon - pos.get(i)[1]) * (lon - pos.get(i)[1]));
            if (minVal == -1 || minVal > tmpVal) {
                minVal = tmpVal;
                nearestIdx = i;
            }
        }

        if (nearestIdx == -1) {
            // del_1_line
            curRegion.append((float) nearestIdx);
            return -1;
        }
        curRegion.append(regions.get(nearestIdx));
        return sunshine.get(nearestIdx);
    }
}