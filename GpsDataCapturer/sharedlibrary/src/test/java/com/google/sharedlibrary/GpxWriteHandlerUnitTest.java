package com.google.sharedlibrary;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import com.google.sharedlibrary.gpxfile.GpxAnnotationHandler;
import com.google.sharedlibrary.gpxfile.GpxFileWriter;
import com.google.sharedlibrary.gpxfile.GpxWriteHandler;

import org.checkerframework.checker.units.qual.A;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.io.File;
import java.util.concurrent.ThreadPoolExecutor;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public class GpxWriteHandlerUnitTest {
    private GpxWriteHandler gpxWriteHandler;

    @Mock
    private File gpxFile;
    @Mock
    private Location location;


    @Before
    public void setUp(){
        ShadowLog.stream = System.out;
        gpxFile = mock(File.class);
        location = mock(Location.class);
        float signal = 0.0f;
        String formattedTime = "2020-07-12T00:02:36.000Z";
        boolean append = true;
        gpxWriteHandler = new GpxWriteHandler(formattedTime, gpxFile, location, signal, append);
    }

    @Test
    public void testRun() throws Exception {
        ThreadPoolExecutor executor = mock(ThreadPoolExecutor.class);
        executor.execute(gpxWriteHandler);
        verify(executor).execute(gpxWriteHandler);
    }

    @After
    public void tearDown(){
        gpxFile = null;
        location = null;
        gpxWriteHandler = null;
    }
}
