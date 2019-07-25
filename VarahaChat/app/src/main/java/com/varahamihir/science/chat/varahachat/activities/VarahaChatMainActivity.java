package com.varahamihir.science.chat.varahachat.activities;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.myscript.iink.ContentPackage;
import com.myscript.iink.ContentPart;
import com.myscript.iink.Editor;
import com.myscript.iink.IEditorListener;
import com.myscript.iink.Renderer;
import com.myscript.iink.uireferenceimplementation.EditorView;
import com.myscript.iink.uireferenceimplementation.FontUtils;
import com.myscript.iink.uireferenceimplementation.InputController;
import com.varahamihir.science.chat.varahachat.R;
import com.varahamihir.science.chat.varahachat.singletons.VarahaEngine;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class VarahaChatMainActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditorView editorView;
    private Renderer renderer;
    private Editor editor;

    private ContentPackage contentPackage;
    private ContentPart contentPart;

    private static final String TAG = "VarahaChatMainActivity";

    //protected DocumentController documentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Initialize the myscript engine with application wide configuration. It requires
         * a handle to Context that's why we have to do it here.
         */
        VarahaEngine.INSTANCE.initializeConfiguration(this);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
  //      fab.setOnClickListener(new View.OnClickListener() {
    //        @Override
      //      public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          //              .setAction("Action", null).show();
            //}
        //});
    editorView = findViewById(R.id.editor_view);
        // load fonts
        AssetManager assetManager = getApplicationContext().getAssets();
        Map<String, Typeface> typefaceMap = FontUtils.loadFontsFromAssets(assetManager);
        editorView.setTypefaces(typefaceMap);
        editorView.setEngine(VarahaEngine.INSTANCE.getEngine());
        final Editor editor = editorView.getEditor();
        editor.addListener(new IEditorListener() {
            @Override
            public void partChanging(Editor editor, ContentPart oldPart, ContentPart newPart) {
                // no-op
            }

            @Override
            public void partChanged(Editor editor) {
                invalidateOptionsMenu();
                invalidateIconButtons();
            }

            @Override
            public void contentChanged(Editor editor, String[] blockIds) {
                invalidateOptionsMenu();
                invalidateIconButtons();
            }

            @Override
            public void onError(Editor editor, String blockId, String message) {
                Log.e(TAG, "Failed to edit block \"" + blockId + "\"" + message);
            }
        });

        setInputMode(InputController.INPUT_MODE_FORCE_PEN); // If using an active pen, put INPUT_MODE_AUTO here

        String packageName = "File1.iink";
        File file = new File(getFilesDir(), packageName);
        try {
            contentPackage = VarahaEngine.INSTANCE.getEngine().createPackage(file);
            contentPart = contentPackage.createPart("Text Document"); // Choose type of content (possible values are: "Text Document", "Text", "Diagram", "Math", and "Drawing")
        } catch (IOException e) {
            Log.e(TAG, "Failed to open package \"" + packageName + "\"", e);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Failed to open package \"" + packageName + "\"", e);
        }

        setTitle("Type: " + contentPart.getType());

        // wait for view size initialization before setting part
        editorView.post(new Runnable() {
            @Override
            public void run() {
                editorView.getRenderer().setViewOffset(0, 0);
                editorView.getRenderer().setViewScale(1);
                editorView.setVisibility(View.VISIBLE);
                editor.setPart(contentPart);
            }
        });

        findViewById(R.id.button_input_mode_forcePen).setOnClickListener(this);
        findViewById(R.id.button_input_mode_forceTouch).setOnClickListener(this);
        findViewById(R.id.button_input_mode_auto).setOnClickListener(this);
        findViewById(R.id.button_undo).setOnClickListener(this);
        findViewById(R.id.button_redo).setOnClickListener(this);
        findViewById(R.id.button_clear).setOnClickListener(this);

        invalidateIconButtons();
    }

    @Override
    protected void onDestroy() {
        editorView.setOnTouchListener(null);
        editorView.close();

        if (contentPart != null) {
            contentPart.close();
            contentPart = null;
        }
        if (contentPackage != null) {
            contentPackage.close();
            contentPackage = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_input_mode_forcePen:
                setInputMode(InputController.INPUT_MODE_FORCE_PEN);
                break;
            case R.id.button_input_mode_forceTouch:
                setInputMode(InputController.INPUT_MODE_FORCE_TOUCH);
                break;
            case R.id.button_input_mode_auto:
                setInputMode(InputController.INPUT_MODE_AUTO);
                break;
            case R.id.button_undo:
                editorView.getEditor().undo();
                break;
            case R.id.button_redo:
                editorView.getEditor().redo();
                break;
            case R.id.button_clear:
                editorView.getEditor().clear();
                break;
            default:
                Log.e(TAG, "Failed to handle click event");
                break;
        }
    }

    private void setInputMode(int inputMode) {
        editorView.setInputMode(inputMode);
        findViewById(R.id.button_input_mode_forcePen).setEnabled(inputMode != InputController.INPUT_MODE_FORCE_PEN);
        findViewById(R.id.button_input_mode_forceTouch).setEnabled(inputMode != InputController.INPUT_MODE_FORCE_TOUCH);
        findViewById(R.id.button_input_mode_auto).setEnabled(inputMode != InputController.INPUT_MODE_AUTO);
    }

    private void invalidateIconButtons() {
        Editor editor = editorView.getEditor();
        final boolean canUndo = editor.canUndo();
        final boolean canRedo = editor.canRedo();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageButton imageButtonUndo = (ImageButton) findViewById(R.id.button_undo);
                imageButtonUndo.setEnabled(canUndo);
                ImageButton imageButtonRedo = (ImageButton) findViewById(R.id.button_redo);
                imageButtonRedo.setEnabled(canRedo);
                ImageButton imageButtonClear = (ImageButton) findViewById(R.id.button_clear);
                imageButtonClear.setEnabled(contentPart != null);
            }
        });
    }
}

