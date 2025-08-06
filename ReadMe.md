

Resources:
(Supabase + Jetpack Compose)[https://supabase.com/docs/reference/kotlin/insert]


// 
```agsl

    override suspend fun insertImage(fileName: String, fileBytes: ByteArray): String? {
        val bucket = supabase.storage.from("jmedia")
        var uploadedUrl: String? = null

        bucket.uploadAsFlow(fileName, fileBytes).collect { status ->
            when (status) {
                is UploadStatus.Progress -> {
                    val percent = status.totalBytesSend.toFloat() / status.contentLength * 100
                    println("Progress: $percent%")
                }
                is UploadStatus.Success -> {
                    println("Upload successful!")
                    uploadedUrl = bucket.publicUrl(fileName)
                }
            }
        }

        return uploadedUrl
    }
```
# upload image

```agsl
@Composable
fun SelectScreen(
    landViewModel: LandViewModel = viewModel(), modifier: Modifier ) {
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

```