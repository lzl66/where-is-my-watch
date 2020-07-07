package com.google.sharedlibrary.gpxfile;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.sharedlibrary.utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This class provides write function helping GpxFile writing captured gps data to the file.
 */
public class GpxFileWriter {
    private final static String TAG = "GpxFileWriter";
    private Context context;
    protected File gpxFile;
    private boolean append;
    private static ThreadPoolExecutor EXECUTOR;

    public GpxFileWriter(Context context, File gpxFile, boolean append) {
        this.context = context;
        this.gpxFile = gpxFile;
        this.append = append;
        EXECUTOR = new ThreadPoolExecutor(1, 1, 60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(10), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            }
        });
    }

    /**
     * Write the gpx data into file with header if it's new file
     * @param location the updated location
     * @param isNewFile if it's new file
     * @throws Exception
     */
    public void writeGpsData(Location location, boolean isNewFile) throws Exception {
        long time = location.getTime();
        if (time <= 0) {
            time = System.currentTimeMillis();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                context.getResources().getConfiguration().locale);

        Runnable writeHandler = new GpxWriteHandler(context, sdf.format(time), gpxFile, location,
                append, isNewFile);
        EXECUTOR.execute(writeHandler);
    }

    /**
     * Write the gpx file footer in xml format
     */
    public void writeFileFooter() {
        Runnable footerHandler = new GpxFooterHandler(context, gpxFile, append);
        EXECUTOR.execute(footerHandler);
    }
}
