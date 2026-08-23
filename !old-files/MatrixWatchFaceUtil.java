package net.dheera.wearfacematrix;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

public final class MatrixWatchFaceUtil {

    private static final String TAG = MatrixWatchFaceUtil.class.getSimpleName();

    public static final String KEY_MATRIXFONT = "matrixfont";

    public static final boolean KEY_MATRIXFONT_DEF = false;

    public static final String KEY_SUBWAYTICKERFONT = "subwaytickerfont";

    public static final boolean KEY_SUBWAYTICKERFONT_DEF = true;

    public static final String PATH_WITH_FEATURE = "/MatrixWatchFace";


    public static int getInt(final Context context, final String key, final int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }

    public static boolean getBoolean(final Context context, final String key,
            final boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static void setInt(final Context context, final String key, final int value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setBoolean(final Context context, final String key, final boolean value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void resetAllPrefs(final Context context) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    // Callback interface to perform an action with the current config DataMap
    public interface FetchConfigDataMapCallback {

        // Callback invoked with the current config DataMap
        void onConfigDataMapFetched(DataMap config);
    }

    // Asynchronously fetches the current config DataMap and passes it to the given callback. If
    // the current config DataItem doesn't exist, it isn't created and the callback receives an
    // empty DataMap.
    public static void fetchConfigDataMap(final GoogleApiClient client,
            final FetchConfigDataMapCallback callback) {
        Wearable.NodeApi.getLocalNode(client).setResultCallback(
                new ResultCallback<NodeApi.GetLocalNodeResult>() {
                    @Override
                    public void onResult(NodeApi.GetLocalNodeResult getLocalNodeResult) {
                        String localNode = getLocalNodeResult.getNode().getId();
                        Uri uri = new Uri.Builder()
                                .scheme("wear")
                                .path(MatrixWatchFaceUtil.PATH_WITH_FEATURE)
                                .authority(localNode)
                                .build();
                        Wearable.DataApi.getDataItem(client, uri)
                                .setResultCallback(new DataItemResultCallback(callback));
                    }
                }
        );
    }

    // Overwrites (or sets, if not present) the keys in the current config DataItem with the ones
    // appearing in the given DataMap. If the config DataItem doesn't exist, it's created. It is
    // allowed that only some of the keys used in the config DataItem appear in
    // configKeysToOverwrite. The rest of the keys remains unmodified in this case.
    public static void overwriteKeysInConfigDataMap(final GoogleApiClient googleApiClient,
            final DataMap configKeysToOverwrite) {

        MatrixWatchFaceUtil.fetchConfigDataMap(googleApiClient,
                new FetchConfigDataMapCallback() {
                    @Override
                    public void onConfigDataMapFetched(DataMap currentConfig) {
                        DataMap overwrittenConfig = new DataMap();
                        overwrittenConfig.putAll(currentConfig);
                        overwrittenConfig.putAll(configKeysToOverwrite);
                        MatrixWatchFaceUtil.putConfigDataItem(googleApiClient, overwrittenConfig);
                    }
                }
        );
    }


    // Overwrites the current config DataItem's DataMap with newConfig. If the config DataItem
    // doesn't exist, it's created.
    public static void putConfigDataItem(GoogleApiClient googleApiClient, DataMap newConfig) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(PATH_WITH_FEATURE);
        DataMap configToPut = putDataMapRequest.getDataMap();
        configToPut.putAll(newConfig);
        Wearable.DataApi.putDataItem(googleApiClient, putDataMapRequest.asPutDataRequest())
                .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(DataApi.DataItemResult dataItemResult) {
                        if (Log.isLoggable(TAG, Log.DEBUG)) {
                            Log.d(TAG, "putDataItem result status: " + dataItemResult.getStatus());
                        }
                    }
                });
    }

    private static class DataItemResultCallback implements ResultCallback<DataApi.DataItemResult> {

        private final FetchConfigDataMapCallback mCallback;

        public DataItemResultCallback(FetchConfigDataMapCallback callback) {
            mCallback = callback;
        }

        @Override
        public void onResult(DataApi.DataItemResult dataItemResult) {
            if (dataItemResult.getStatus().isSuccess()) {
                if (dataItemResult.getDataItem() != null) {
                    DataItem configDataItem = dataItemResult.getDataItem();
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(configDataItem);
                    DataMap config = dataMapItem.getDataMap();
                    mCallback.onConfigDataMapFetched(config);
                } else {
                    mCallback.onConfigDataMapFetched(new DataMap());
                }
            }
        }
    }

    // singleton
    private MatrixWatchFaceUtil() {
    }
}
