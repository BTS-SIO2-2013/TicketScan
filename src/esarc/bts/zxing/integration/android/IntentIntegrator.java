/*
 * Copyright 2009 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package esarc.bts.zxing.integration.android;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * <p>
 * A utility class which helps ease integration with Barcode Scanner via
 * {@link Intent}s. This is a simple way to invoke barcode scanning and receive
 * the result, without any need to integrate, modify, or learn the project's
 * source code.
 * </p>
 * <h2>Initiating a barcode scan</h2>
 * <p>
 * To integrate, create an instance of {@code IntentIntegrator} and call
 * {@link #initiateScan()} and wait for the result in your app.
 * </p>
 * <p>
 * It does require that the Barcode Scanner (or work-alike) application is
 * installed. The {@link #initiateScan()} method will prompt the user to
 * download the application, if needed.
 * </p>
 * <p>
 * There are a few steps to using this integration. First, your {@link Activity}
 * must implement the method {@link Activity#onActivityResult(int, int, Intent)}
 * and include a line of code like this:
 * </p>
 * 
 * <pre>
 * {@code
 * public void onActivityResult(int requestCode, int resultCode, Intent intent) {
 *   IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
 *   if (scanResult != null) {
 *     // handle scan result
 *   }
 *   // else continue with any other code you need in the method
 *   ...
 * }
 * }
 * </pre>
 * <p>
 * This is where you will handle a scan result.
 * </p>
 * <p>
 * Second, just call this in response to a user action somewhere to begin the
 * scan process:
 * </p>
 * 
 * <pre>
 * {
 *     &#064;code
 *     IntentIntegrator integrator = new IntentIntegrator(yourActivity);
 *     integrator.initiateScan();
 * }
 * </pre>
 * <p>
 * Note that {@link #initiateScan()} returns an {@link AlertDialog} which is
 * non-null if the user was prompted to download the application. This lets the
 * calling app potentially manage the dialog. In particular, ideally, the app
 * dismisses the dialog if it's still active in its {@link Activity#onPause()}
 * method.
 * </p>
 * <p>
 * You can use {@link #setTitle(String)} to customize the title of this download
 * prompt dialog (or, use {@link #setTitleByID(int)} to set the title by string
 * resource ID.) Likewise, the prompt message, and yes/no button labels can be
 * changed.
 * </p>
 * <p>
 * Finally, you can use {@link #addExtra(String, Object)} to add more parameters
 * to the Intent used to invoke the scanner. This can be used to set additional
 * options not directly exposed by this simplified API.
 * </p>
 * <p>
 * By default, this will only allow applications that are known to respond to
 * this intent correctly do so. The apps that are allowed to response can be set
 * with {@link #setTargetApplications(List)}. For example, set to
 * {@link #TARGET_BARCODE_SCANNER_ONLY} to only target the Barcode Scanner app
 * itself.
 * </p>
 * <h2>Sharing text via barcode</h2>
 * <p>
 * To share text, encoded as a QR Code on-screen, similarly, see
 * {@link #shareText(CharSequence)}.
 * </p>
 * <p>
 * Some code, particularly download integration, was contributed from the
 * Anobiit application.
 * </p>
 * <h2>Enabling experimental barcode formats</h2>
 * <p>
 * Some formats are not enabled by default even when scanning with
 * {@link #ALL_CODE_TYPES}, such as PDF417. Use
 * {@link #initiateScan(java.util.Collection)} with a collection containing the
 * names of formats to scan for explicitly, like "PDF_417", to use such formats.
 * </p>
 * 
 * @author Sean Owen
 * @author Fred Lin
 * @author Isaac Potoczny-Jones
 * @author Brad Drehmer
 * @author gcstang
 */
public class IntentIntegrator {

    public static final int                REQUEST_CODE                = 0x0000c0de;                                                                // bits
    private static final String            TAG                         = IntentIntegrator.class
                                                                               .getSimpleName();

    public static final String             DEFAULT_TITLE               = "Install Barcode Scanner?";
    public static final String             DEFAULT_MESSAGE             = "This application requires Barcode Scanner. Would you like to install it?";
    public static final String             DEFAULT_YES                 = "Yes";
    public static final String             DEFAULT_NO                  = "No";

    private static final String            BS_PACKAGE                  = "com.google.zxing.client.android";
    private static final String            BSPLUS_PACKAGE              = "com.srowen.bs.android";

    // supported barcode formats
    public static final Collection<String> PRODUCT_CODE_TYPES          = list("UPC_A",
                                                                               "UPC_E",
                                                                               "EAN_8",
                                                                               "EAN_13",
                                                                               "RSS_14");
    public static final Collection<String> ONE_D_CODE_TYPES            = list("UPC_A",
                                                                               "UPC_E",
                                                                               "EAN_8",
                                                                               "EAN_13",
                                                                               "CODE_39",
                                                                               "CODE_93",
                                                                               "CODE_128",
                                                                               "ITF",
                                                                               "RSS_14",
                                                                               "RSS_EXPANDED");
    public static final Collection<String> QR_CODE_TYPES               = Collections
                                                                               .singleton("QR_CODE");
    public static final Collection<String> DATA_MATRIX_TYPES           = Collections
                                                                               .singleton("DATA_MATRIX");

    public static final Collection<String> ALL_CODE_TYPES              = null;

    public static final List<String>       TARGET_BARCODE_SCANNER_ONLY = Collections
                                                                               .singletonList(BS_PACKAGE);
    public static final List<String>       TARGET_ALL_KNOWN            = list(BSPLUS_PACKAGE, // Barcode
                                                                                              // Scanner+
                                                                               BSPLUS_PACKAGE
                                                                                       + ".simple", // Barcode
                                                                                                    // Scanner+
                                                                                                    // Simple
                                                                               BS_PACKAGE // Barcode
                                                                                          // Scanner
                                                                       // What
                                                                       // else
                                                                       // supports
                                                                       // this
                                                                       // intent?
                                                                       );

    private final Activity                 activity;
    private final Fragment                 fragment;

    private String                         title;
    private String                         message;
    private String                         buttonYes;
    private String                         buttonNo;
    private List<String>                   targetApplications;
    private final Map<String, Object>      moreExtras                  = new HashMap<String, Object>(
                                                                               3);

    /**
     * @param activity
     *            {@link Activity} invoking the integration
     */
    public IntentIntegrator(final Activity pActivity) {
        this.activity = pActivity;
        this.fragment = null;
        initializeConfiguration();
    }

    /**
     * @param fragment
     *            {@link Fragment} invoking the integration.
     *            {@link #startActivityForResult(Intent, int)} will be called on
     *            the {@link Fragment} instead of an {@link Activity}
     */
    public IntentIntegrator(final Fragment pFragment) {
        this.activity = pFragment.getActivity();
        this.fragment = pFragment;
        initializeConfiguration();
    }

    private void initializeConfiguration() {
        this.title = DEFAULT_TITLE;
        this.message = DEFAULT_MESSAGE;
        this.buttonYes = DEFAULT_YES;
        this.buttonNo = DEFAULT_NO;
        this.targetApplications = TARGET_ALL_KNOWN;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(final String pTitle) {
        this.title = pTitle;
    }

    public final void setTitleByID(final int titleID) {
        this.title = this.activity.getString(titleID);
    }

    public final String getMessage() {
        return this.message;
    }

    public final void setMessage(final String pMessage) {
        this.message = pMessage;
    }

    public final void setMessageByID(final int messageID) {
        this.message = this.activity.getString(messageID);
    }

    public final String getButtonYes() {
        return this.buttonYes;
    }

    public final void setButtonYes(final String pButtonYes) {
        this.buttonYes = pButtonYes;
    }

    public final void setButtonYesByID(final int buttonYesID) {
        this.buttonYes = this.activity.getString(buttonYesID);
    }

    public final String getButtonNo() {
        return this.buttonNo;
    }

    public final void setButtonNo(final String pButtonNo) {
        this.buttonNo = pButtonNo;
    }

    public final void setButtonNoByID(final int buttonNoID) {
        this.buttonNo = this.activity.getString(buttonNoID);
    }

    public final Collection<String> getTargetApplications() {
        return this.targetApplications;
    }

    public final void setTargetApplications(
            final List<String> pTargetApplications) {
        if (this.targetApplications.isEmpty()) {
            throw new IllegalArgumentException("No target applications");
        }
        this.targetApplications = pTargetApplications;
    }

    public final void setSingleTargetApplication(final String targetApplication) {
        this.targetApplications = Collections.singletonList(targetApplication);
    }

    public final Map<String, ?> getMoreExtras() {
        return this.moreExtras;
    }

    public final void addExtra(final String key, final Object value) {
        this.moreExtras.put(key, value);
    }

    /**
     * Initiates a scan for all known barcode types.
     */
    public final AlertDialog initiateScan() {
        return initiateScan(ALL_CODE_TYPES);
    }

    /**
     * Initiates a scan only for a certain set of barcode types, given as
     * strings corresponding to their names in ZXing's {@code BarcodeFormat}
     * class like "UPC_A". You can supply constants like
     * {@link #PRODUCT_CODE_TYPES} for example.
     * 
     * @return the {@link AlertDialog} that was shown to the user prompting them
     *         to download the app if a prompt was needed, or null otherwise
     */
    public final AlertDialog initiateScan(
            final Collection<String> desiredBarcodeFormats) {
        Intent intentScan = new Intent(BS_PACKAGE + ".SCAN");
        intentScan.addCategory(Intent.CATEGORY_DEFAULT);

        // check which types of codes to scan for
        if (desiredBarcodeFormats != null) {
            // set the desired barcode types
            StringBuilder joinedByComma = new StringBuilder();
            for (String format : desiredBarcodeFormats) {
                if (joinedByComma.length() > 0) {
                    joinedByComma.append(',');
                }
                joinedByComma.append(format);
            }
            intentScan.putExtra("SCAN_FORMATS", joinedByComma.toString());
        }

        String targetAppPackage = findTargetAppPackage(intentScan);
        if (targetAppPackage == null) {
            return showDownloadDialog();
        }
        intentScan.setPackage(targetAppPackage);
        intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        attachMoreExtras(intentScan);
        startActivityForResult(intentScan, REQUEST_CODE);
        return null;
    }

    /**
     * Start an activity. This method is defined to allow different methods of
     * activity starting for newer versions of Android and for compatibility
     * library.
     * 
     * @param intent
     *            Intent to start.
     * @param code
     *            Request code for the activity
     * @see android.app.Activity#startActivityForResult(Intent, int)
     * @see android.app.Fragment#startActivityForResult(Intent, int)
     */
    protected final void startActivityForResult(final Intent intent,
            final int code) {
        if (this.fragment == null) {
            this.activity.startActivityForResult(intent, code);
        } else {
            this.fragment.startActivityForResult(intent, code);
        }
    }

    private String findTargetAppPackage(final Intent intent) {
        PackageManager pm = this.activity.getPackageManager();
        List<ResolveInfo> availableApps = pm.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (availableApps != null) {
            for (String targetApp : this.targetApplications) {
                if (contains(availableApps, targetApp)) {
                    return targetApp;
                }
            }
        }
        return null;
    }

    private static boolean contains(final Iterable<ResolveInfo> availableApps,
            final String targetApp) {
        for (ResolveInfo availableApp : availableApps) {
            String packageName = availableApp.activityInfo.packageName;
            if (targetApp.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    private AlertDialog showDownloadDialog() {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(
                this.activity);
        downloadDialog.setTitle(this.title);
        downloadDialog.setMessage(this.message);
        downloadDialog.setPositiveButton(this.buttonYes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                            final int i) {
                        String packageName;
                        if (IntentIntegrator.this.targetApplications
                                .contains(BS_PACKAGE)) {
                            // Prefer to suggest download of BS if it's anywhere
                            // in the list
                            packageName = BS_PACKAGE;
                        } else {
                            // Otherwise, first option:
                            packageName = IntentIntegrator.this.targetApplications
                                    .get(0);
                        }
                        Uri uri = Uri.parse("market://details?id="
                                + packageName);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            if (IntentIntegrator.this.fragment == null) {
                                IntentIntegrator.this.activity
                                        .startActivity(intent);
                            } else {
                                IntentIntegrator.this.fragment
                                        .startActivity(intent);
                            }
                        } catch (ActivityNotFoundException anfe) {
                            // Hmm, market is not installed
                            Log.w(TAG,
                                    "Google Play is not installed; cannot install "
                                            + packageName);
                        }
                    }
                });
        downloadDialog.setNegativeButton(this.buttonNo, null);
        downloadDialog.setCancelable(true);
        return downloadDialog.show();
    }

    /**
     * <p>
     * Call this from your {@link Activity}'s
     * {@link Activity#onActivityResult(int, int, Intent)} method.
     * </p>
     * 
     * @return null if the event handled here was not related to this class, or
     *         else an {@link IntentResult} containing the result of the scan.
     *         If the user cancelled scanning, the fields will be null.
     */
    public static IntentResult parseActivityResult(final int requestCode,
            final int resultCode, final Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String formatName = intent.getStringExtra("SCAN_RESULT_FORMAT");
                byte[] rawBytes = intent.getByteArrayExtra("SCAN_RESULT_BYTES");
                int intentOrientation = intent.getIntExtra(
                        "SCAN_RESULT_ORIENTATION", Integer.MIN_VALUE);
                Integer orientation = null;
                if (orientation == Integer.MIN_VALUE) {
                    orientation = null;
                } else {
                    orientation = intentOrientation;
                }
                String errorCorrectionLevel = intent
                        .getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");
                return new IntentResult(contents, formatName, rawBytes,
                        orientation, errorCorrectionLevel);
            }
            return new IntentResult();
        }
        return null;
    }

    /**
     * Defaults to type "TEXT_TYPE".
     * 
     * @see #shareText(CharSequence, CharSequence)
     */
    public final AlertDialog shareText(final CharSequence text) {
        return shareText(text, "TEXT_TYPE");
    }

    /**
     * Shares the given text by encoding it as a barcode, such that another user
     * can scan the text off the screen of the device.
     * 
     * @param text
     *            the text string to encode as a barcode
     * @param type
     *            type of data to encode. See
     *            {@code com.google.zxing.client.android.Contents.Type}
     *            constants.
     * @return the {@link AlertDialog} that was shown to the user prompting them
     *         to download the app if a prompt was needed, or null otherwise
     */
    public final AlertDialog shareText(final CharSequence text,
            final CharSequence type) {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setAction(BS_PACKAGE + ".ENCODE");
        intent.putExtra("ENCODE_TYPE", type);
        intent.putExtra("ENCODE_DATA", text);
        String targetAppPackage = findTargetAppPackage(intent);
        if (targetAppPackage == null) {
            return showDownloadDialog();
        }
        intent.setPackage(targetAppPackage);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        attachMoreExtras(intent);
        if (this.fragment == null) {
            this.activity.startActivity(intent);
        } else {
            this.fragment.startActivity(intent);
        }
        return null;
    }

    private static List<String> list(final String... values) {
        return Collections.unmodifiableList(Arrays.asList(values));
    }

    private void attachMoreExtras(final Intent intent) {
        for (Map.Entry<String, Object> entry : this.moreExtras.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            // Kind of hacky
            if (value instanceof Integer) {
                intent.putExtra(key, (Integer) value);
            } else if (value instanceof Long) {
                intent.putExtra(key, (Long) value);
            } else if (value instanceof Boolean) {
                intent.putExtra(key, (Boolean) value);
            } else if (value instanceof Double) {
                intent.putExtra(key, (Double) value);
            } else if (value instanceof Float) {
                intent.putExtra(key, (Float) value);
            } else if (value instanceof Bundle) {
                intent.putExtra(key, (Bundle) value);
            } else {
                intent.putExtra(key, value.toString());
            }
        }
    }

}