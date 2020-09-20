package com.example.latteria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        setCameraProviderListener();

    }

    private void setCameraProviderListener() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);

            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(ProcessCameraProvider cameraProvider) {

        PreviewView previewView = findViewById(R.id.previewView);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {

                    private BarcodeScanner scanner = buildBarCodeScanner();

                    @Override
                    public void analyze(ImageProxy imageProxy) {
                        @SuppressLint("UnsafeExperimentalUsageError") Image mediaImage = imageProxy.getImage();
                        if (mediaImage != null) {
                            InputImage image =
                                    InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                            // Pass image to an ML Kit Vision API
                            Task<List<Barcode>> result = scanner.process(image);

                            result.addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                                @Override
                                public void onSuccess(List<Barcode> barcodes) {
                                    // Task completed successfully
                                    Log.i("CameraXApp3", "scanner task successful");
                                    processBarCodes(barcodes);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Task failed with an exception
                                    Log.i("CameraXApp3", "scanner task failed. Error:" + e);

                                }
                            }).addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                                @Override
                                public void onComplete(@NonNull Task<List<Barcode>> task) {
                                    mediaImage.close();
                                    imageProxy.close();
                                }
                            });
                        }
                    }

            private BarcodeScanner buildBarCodeScanner() {
                BarcodeScannerOptions options =
                        new BarcodeScannerOptions.Builder()
                                .setBarcodeFormats(
                                        Barcode.FORMAT_EAN_13,
                                        Barcode.FORMAT_EAN_8)
                                .build();
                return BarcodeScanning.getClient(options);
            }

            private void processBarCodes(List<Barcode> barcodes) {
                for (Barcode barcode : barcodes) {
                    String rawValue = barcode.getRawValue();
                    int valueType = barcode.getValueType();
                    // See API reference for complete list of supported types
                    if (valueType == Barcode.TYPE_PRODUCT) {
                        toast(getApplicationContext(), "Received Message:" + rawValue);
                        Intent intent = new Intent();
                        intent.putExtra("barcode", rawValue);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }

            public void toast(final Context context, final String text) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> Toast.makeText(context, text, Toast.LENGTH_LONG).show());
            }

        });

        cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview);

    }

}