package kg.timetotravel.anidea;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewIdeaActivity extends AppCompatActivity {
    private ArrayAdapter<Idea> adapter;
    private EditText nameText, dateText, pic;
    private List<Idea> ideas;
    ListView listView;
    ImageView imageView2;

    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private ImageView imageView;
    static Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea);

        nameText = (EditText) findViewById(R.id.nameText);
        dateText = (EditText) findViewById(R.id.dateText);
        //pic = (EditText) findViewById(R.id.picUri);
        pic = null;

        ideas = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ideas);
        listView.setAdapter(adapter);

        imageView = (ImageView) findViewById(R.id.imageView2);
        photoURI = null;
    }

    public void addPhone(View view){
        String name = nameText.getText().toString();
        String date = dateText.getText().toString();
        String picUri;
        if (photoURI != null){
            picUri = String.valueOf(photoURI);
        } else{
            picUri = null;
        }
        Idea idea = new Idea(name, date, picUri);
        ideas.add(idea);
        adapter.notifyDataSetChanged();
    }

    public void save(View view){
        String name = nameText.getText().toString();
        String date = dateText.getText().toString();
        String picUri;
        if (photoURI != null){
            picUri = String.valueOf(photoURI);
        } else{
            picUri = null;
        }

        Comments comment = new Comments(name, date, picUri);
        comment.save();

        Intent intent = new Intent();

        intent.putExtra("id", comment.getId());

        setResult(RESULT_OK, intent);

        finish();
    }
    public void open(View view){
        ideas = JSONHelper.importFromJSON(this);
        if(ideas!=null){
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ideas);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
        }
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
            imageView.setImageURI(photoURI);

        } else{ ;
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
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
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
}
