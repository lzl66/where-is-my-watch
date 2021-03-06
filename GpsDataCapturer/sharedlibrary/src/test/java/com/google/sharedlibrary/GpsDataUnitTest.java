package com.google.sharedlibrary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import android.location.Location;

import com.google.sharedlibrary.model.GpsData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DecimalFormat;

@RunWith(MockitoJUnitRunner.class)
public class GpsDataUnitTest {
  private GpsData gpsData;
  private DecimalFormat locationDF;
  private DecimalFormat speedDF;
  private final String defaultLocFormat = "0.000000";
  private final String defaultSpeedFormat = "0.0000";

  @Test
  public void testGpsData() {
    // Given
    Location location = mock(Location.class);
    locationDF = new DecimalFormat(defaultLocFormat);
    speedDF = new DecimalFormat(defaultSpeedFormat);

    // When
    gpsData = new GpsData(location);

    // Then
    assertEquals(locationDF.format(location.getLatitude()), gpsData.getLatitude());
    assertEquals(locationDF.format(location.getLongitude()), gpsData.getLongitude());
    assertEquals(speedDF.format(location.getSpeed()), gpsData.getSpeed());
  }

  @Test
  public void testGpsDataToString() {
    // Given
    String expected =
        "GPS DATA"
            + " Lat: "
            + defaultLocFormat
            + " Lon: "
            + defaultLocFormat
            + " Speed: "
            + defaultSpeedFormat;
    Location location = mock(Location.class);

    // When
    gpsData = new GpsData(location);

    // Then
    assertEquals(expected, gpsData.toString());
  }

  @Test
  public void testGpsDataEqual() {
    // Given
    Location location = mock(Location.class);
    Location location1 = mock(Location.class);

    // When
    gpsData = new GpsData(location);
    GpsData gpsData1 = new GpsData(location1);

    // Then
    assertTrue(gpsData.equals(gpsData1));
  }

  @Test
  public void testGpsDataHashCode() {
    // Given
    Location location = mock(Location.class);
    Location location1 = mock(Location.class);

    // When
    gpsData = new GpsData(location);
    GpsData gpsData1 = new GpsData(location1);

    // Then
    assertNotEquals(gpsData.hashCode(), gpsData1.hashCode());
  }
}
