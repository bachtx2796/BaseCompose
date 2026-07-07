package com.example.basecompose.utils

import android.content.Context
import java.io.File

object InstallDataGuard {

    fun clearRestoredDataOnFreshInstall(context: Context) {
        val markerFile = File(context.noBackupFilesDir, INSTALL_MARKER_FILE_NAME)
        if (markerFile.exists()) return

        if (context.isFreshInstall()) {
            GSharePreference.clear()
        }
        markerFile.createNewFile()
    }

    private fun Context.isFreshInstall(): Boolean {
        val packageInfo =
            packageManager.getPackageInfo(packageName, 0)
        return (packageInfo.lastUpdateTime - packageInfo.firstInstallTime) <= FRESH_INSTALL_THRESHOLD_MS
    }

    private const val INSTALL_MARKER_FILE_NAME = ".base_compose_install_marker"
    private const val FRESH_INSTALL_THRESHOLD_MS = 2 * 60 * 1000L
}
