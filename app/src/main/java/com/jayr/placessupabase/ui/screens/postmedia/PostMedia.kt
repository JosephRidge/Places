package com.jayr.placessupabase.ui.screens.postmedia

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun SelectScreen(
    landViewModel: LandViewModel = viewModel(),
    modifier: Modifier ) {
    val context = LocalContext.current

    // getfilename
    fun getFileNameFromUri(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex != -1) {
                it.getString(nameIndex)
            } else null
        }
    }

    //  get mime type
    fun getExtensionFromUri(context: Context, uri: Uri): String? {
        val mimeType = context.contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
    }


    // Activity result launcher for picking a file
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data
        if (uri != null) {
            val inputStream = context.contentResolver.openInputStream(uri)
            val fileBytes = inputStream?.readBytes()
            val fileName = getFileNameFromUri(context, uri) ?: "upload.${getExtensionFromUri(context, uri) ?: "dat"}"

            if (fileBytes != null) {
                landViewModel.uploadImage(fileName, fileBytes)
            }
        }
    }



    Column(

        modifier =modifier.fillMaxSize()) {    Button(onClick = {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*" // or "image/*" or "video/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }

        launcher.launch(intent)
    }) {
        Text(text = "pick media")
    }}}
