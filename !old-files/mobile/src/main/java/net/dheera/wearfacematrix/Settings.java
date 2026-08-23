/*
 * Copyright (C) 2014 Richard Banasiak
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

package net.dheera.wearfacematrix;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {

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

}
