package com.product.nearme.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class UtilitiesImage {
	public static Spanned getUnderlineText(String underLineText) {
		return Html.fromHtml("<u>" + underLineText + "</u>");
	}

	public static String capFirstLetter(String input) {
		if (input.length() != 0)
			return input.substring(0, 1).toUpperCase()
					+ input.substring(1, input.length());
		else
			return "";
	}


	public static String convertImageUriToFile(Uri imageUri, Activity context) {

		Cursor cursor = null;
		int imageID = 0;
		String Path = null;

		try {

			/*********** Which columns values want to get *******/
			String[] proj = { MediaStore.Images.Media.DATA,
					MediaStore.Images.Media._ID,
					MediaStore.Images.Thumbnails._ID,
					MediaStore.Images.ImageColumns.ORIENTATION };

			cursor = context.managedQuery(

			imageUri, // Get data for specific image URI
					proj, // Which columns to return
					null, // WHERE clause; which rows to return (all rows)
					null, // WHERE clause selection arguments (none)
					null // Order-by clause (ascending by name)

					);

			// Get Query Data

			int columnIndex = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
			int columnIndexThumb = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
			int file_ColumnIndex = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

			// int orientation_ColumnIndex = cursor.
			// getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);

			int size = cursor.getCount();

			/******* If size is 0, there are no images on the SD Card. *****/

			if (size == 0) {

			} else {

				int thumbID = 0;
				if (cursor.moveToFirst()) {

					/**************** Captured image details ************/

					/***** Used to show image on view in LoadImagesFromSDCard class ******/
					imageID = cursor.getInt(columnIndex);

					thumbID = cursor.getInt(columnIndexThumb);

					Path = cursor.getString(file_ColumnIndex);
					String CapturedImageDetails = " CapturedImageDetails : \n\n"
							+ " ImageID :"
							+ imageID
							+ "\n"
							+ " ThumbID :"
							+ thumbID + "\n" + " Path :" + Path + "\n";
				}
			}
		} finally {
			if (cursor != null) {

			}
		}
		return Path;
	}

	public static String capitalizeString(String string) {
		char[] chars = string.toLowerCase().toCharArray();
		boolean found = false;
		for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i] == '.'
					|| chars[i] == '\'') { // You can add other chars here
				found = false;
			}
		}
		return String.valueOf(chars);
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static String getpath(Intent data, Context context) {
		String filePath = "";
		try {
			System.out.println("cdata " + data + "get path" + data.getData());
			Uri selectedImage = data.getData();
			System.out.println("selected image" + selectedImage);

			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = context.getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				filePath = cursor.getString(columnIndex);
				cursor.close();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return filePath;
	}

	static public Typeface getVarRoundedBoldTypeface(Context c) {
		Typeface face = Typeface.createFromAsset(c.getAssets(),
				"fonts/VAG Rounded Bold.ttf");
		return face;
	}

	static public Typeface getHelveticaBoldTypeface(Context c) {
		Typeface face = Typeface.createFromAsset(c.getAssets(),
				"fonts/Helvetica-Bold.otf");
		return face;
	}

	static public Typeface getHelveticaObliqueTypeface(Context c) {
		Typeface face = Typeface.createFromAsset(c.getAssets(),
				"fonts/Helvetica-Oblique.otf");
		return face;
	}

	static public Typeface getHelveticaMediumTypeface(Context c) {
		Typeface face = Typeface.createFromAsset(c.getAssets(),
				"fonts/helvetica-neue-medium.ttf");
		return face;
	}

	static public Typeface getHelveticaTypeface(Context c) {
		Typeface face = Typeface.createFromAsset(c.getAssets(),
				"fonts/HelveticaNeueLTStd-Lt.otf");
		return face;
	}

	static public Typeface getHelveticaRegularTypeface(Context c) {
		Typeface face = Typeface.createFromAsset(c.getAssets(),
				"fonts/helveticaneue-regular-webfont.ttf");
		return face;
	}
	
	
	public static String Savefile(String path) {

		File file=null;

		try{
			File f = new File(path);
			String name = f.getName();

			File direct = new File(Environment.getExternalStorageDirectory(),"/zawsmiles");

			if (!direct.exists()) {

				direct.mkdir();
			}

			file = new File(Environment.getExternalStorageDirectory()
					+ "/zawsmiles/" + String.valueOf(System.currentTimeMillis() % 1000) +name);
			try {
				file.createNewFile();
				FileChannel src = new FileInputStream(path).getChannel();
				FileChannel dst = new FileOutputStream(file).getChannel();
				dst.transferFrom(src, 0, src.size());
				src.close();
				dst.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch (Exception e)
		{
			e.printStackTrace();;
		}
		return file.getAbsolutePath();
	}


	public static void deleteSavedFiles(String path) {

		File dir = new File(Environment.getExternalStorageDirectory() + "/zawsmiles");
		Log.d("dir name", "----" + dir);
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				new File(dir, children[i]).delete();
			}
		}



	}
}
