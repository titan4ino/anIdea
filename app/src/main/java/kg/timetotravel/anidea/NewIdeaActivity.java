package kg.timetotravel.anidea;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.File;
import java.io.IOException;

public class NewIdeaActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener  {
    private EditText nameText;
    private TextView rateBarNum;
    ImageView imageView2;

    static final int REQUEST_TAKE_PHOTO = 1;
    private ImageView thumbImage;
    static Uri photoURI;
    SeekBar seekBar;
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea);
        nameText = findViewById(R.id.ideaText);
        thumbImage = findViewById(R.id.thumPic);
        photoURI = null;

        //Create and init seekbar and value textview for rating, set default rating to 5
        seekBar = (SeekBar)findViewById(R.id.rateBar);
        seekBar.setProgress(5);
        seekBar.setOnSeekBarChangeListener(this);

        rateBarNum = (TextView)findViewById(R.id.rateBarNum);
        rateBarNum.setText("5");
    }

    public void save(View view){
        boolean empty = true;

        String name = nameText.getText().toString();
        if(!nameText.getText().toString().equals("")){empty = false;}
        String date = new SimpleDateFormat("H:mm, EEE d MMM").format(new Date());
        String picUri;
        if (photoURI != null){
            picUri = String.valueOf(photoURI);
        } else{
            picUri = null;
        }
        if(picUri != null){empty = false;}

        int rating = seekBar.getProgress();

        if (!empty){
            Comments comment = new Comments(name, date, picUri, rating);
            comment.save();

            Intent intent = new Intent();
            intent.putExtra("id", comment.getId());
            setResult(RESULT_OK, intent);
        }



        finish();
    }

    //---
    public void onPic(View view) {
        Log.e("MARK 1: ", String.valueOf(photoURI));
        dispatchTakePictureIntent();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            thumbImage.setImageURI(photoURI);
            thumbImage.getLayoutParams().height=500;
            thumbImage.requestLayout();

        } else{
            photoURI = null;
        }
        Log.e("MARK 3: ", String.valueOf(photoURI));
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("our file", image.toString());
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.provider",
                        photoFile);
                Log.e("MARK 2: ", String.valueOf(photoURI));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    //change value textview on seekbar change
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        rateBarNum.setText(String.valueOf(seekBar.getProgress()));
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        rateBarNum.setText(String.valueOf(seekBar.getProgress()));
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        rateBarNum.setText(String.valueOf(seekBar.getProgress()));
    }

    @Override
    public void onBackPressed() {
        System.out.println(String.valueOf(FileProvider.getUriForFile(this, "com.example.android.provider", photoFile)));
        if (photoURI != null){
            File file = new File(String.valueOf(FileProvider.getUriForFile(this, "com.example.android.provider", photoFile)));
            file.delete();
            if(file.exists()){
                System.out.println("sdadsasdassssssssssssssssssssss");
                try {
                    file.getCanonicalFile().delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(file.exists()){
                    System.out.println("sdadsasdassssssssssssssssssssss");
                    getApplicationContext().deleteFile(file.getName());
                }
            }
        }
        finish();
    }
}
