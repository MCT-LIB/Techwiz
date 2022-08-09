package com.csupporter.techwiz.presentation.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model._Note;
import com.csupporter.techwiz.presentation.view.adapter._TestNoteAdapter;
import com.csupporter.techwiz.presentation.view.dialog.LoadingDialog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mct.components.baseui.BaseActivity;
import com.mct.components.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class _TestFirebaseActivity extends BaseActivity implements View.OnClickListener, _TestNoteAdapter.OnItemClickListener {

    private static final String TAG = "__TAG";
    private FirebaseFirestore db;
    private EditText edtTitle, edtContent;
    private ImageView imgNote;
    private _TestNoteAdapter noteAdapter;
    private LoadingDialog loadingDialog;
    private Uri mUri;

    private final ActivityResultLauncher<Void> mPickPictureResult =
            registerForActivityResult(new PickPictureResult(), uri -> {
                if ((mUri = uri) == null) {
                    imgNote.setImageResource(R.drawable._ic_placeholder);
                    return;
                }
                imgNote.setTag(uri);
                imgNote.setImageURI(uri);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_test_firebase);
        db = FirebaseFirestore.getInstance();
        initUi();
        loadNotes();
    }

    private void initUi() {
        edtTitle = findViewById(R.id.edt_title);
        edtContent = findViewById(R.id.edt_content);
        imgNote = findViewById(R.id.img_note);
        imgNote.setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_load).setOnClickListener(this);

        noteAdapter = new _TestNoteAdapter();
        noteAdapter.setOnItemClickListener(this);
        RecyclerView rcvNotes = findViewById(R.id.rcv_notes);
        rcvNotes.setAdapter(noteAdapter);
        rcvNotes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    protected int getContainerId() {
        return Window.ID_ANDROID_CONTENT;
    }

    @Override
    protected void showToastOnBackPressed() {
        ToastUtils.makeWarningToast(this, Toast.LENGTH_SHORT, getString(R.string.toast_back_press), false).show();
    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        if (id == R.id.img_note) {
            mPickPictureResult.launch(null);
        }
        if (id == R.id.btn_add) {
            _Note note = new _Note();
            note.setId(UUID.randomUUID().toString());
            addOrUpdate(note);
        }
        if (id == R.id.btn_load) {
            loadNotes();
        }
    }

    private void loadNotes() {
        loading(true);
        _Test.getData("notes", queryDocumentSnapshots -> {
            loading(false);
            List<_Note> noteList = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Log.d(TAG, document.getId() + " => " + document.getData());
                noteList.add(document.toObject(_Note.class));
            }
            noteAdapter.setNotes(noteList);
        }, throwable -> loading(false));
    }

    private void addOrUpdate(_Note note) {
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        if (TextUtils.isEmpty(title)) {
            toast("Title can't null", ToastUtils.ERROR);
            return;
        }
        hideSoftInput();
        loading(true);
        note.setTitle(title);
        note.setContent(content);
        Consumer<Uri> consumer = imgUri -> {
            note.setUrl(imgUri != null ? imgUri.toString() : note.getUrl());
            db.collection("notes").document(note.getId()).set(note)
                    .addOnSuccessListener(documentReference -> {
                        toast("Success", ToastUtils.SUCCESS);
                        loadNotes();
                        refreshForm();
                    }).addOnFailureListener(e -> {
                        loading(false);
                        toast("Failure", ToastUtils.ERROR);
                    });
        };
        uploadImage(note.getId(), mUri, consumer);
    }

    private void uploadImage(String uuid, Uri uri, Consumer<Uri> onUploadSuccess) {
        if (uri == null) {
            onUploadSuccess.accept(null);
            return;
        }
        StorageReference ref = FirebaseStorage.getInstance().getReference(uuid);
        ref.putFile(uri).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException() == null ?
                        new NullPointerException("Task null") : task.getException();
            }
            // Continue with the task to get the download URL
            return ref.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onUploadSuccess.accept(task.getResult());
            } else {
                loading(false);
                Toast.makeText(_TestFirebaseActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteNote(@NonNull _Note note) {
        FirebaseStorage.getInstance().getReference(note.getId()).delete();
        db.collection("notes")
                .document(note.getId()).delete()
                .addOnSuccessListener(unused -> loadNotes());
    }

    private void refreshForm() {
        edtTitle.setText("");
        edtContent.setText("");
        imgNote.setImageResource(R.drawable._ic_placeholder);
        mUri = null;
    }

    private void toast(String msg, int type) {
        ToastUtils.makeText(this, Toast.LENGTH_SHORT, type, msg, true).show();
    }

    private void loading(boolean show) {
        if (show) {
            if (loadingDialog != null) {
                if (loadingDialog.isShowing()) {
                    return;
                } else {
                    loadingDialog.dismiss();
                    loadingDialog = null;
                }
            }
            loadingDialog = new LoadingDialog(this);
            loadingDialog.create(null);
        } else {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }
        }
    }

    @Override
    public void onItemClicked(@NonNull _Note note) {
        edtTitle.setText(note.getTitle());
        edtContent.setText(note.getContent());
        Glide.with(this).load(note.getUrl())
                .error(R.drawable._ic_placeholder)
                .placeholder(R.drawable._ic_placeholder)
                .into(imgNote);
    }

    @Override
    public void onClickedUpdate(_Note note, int position) {
        addOrUpdate(note);
        noteAdapter.notifyItemChanged(position);
    }

    @Override
    public void onClickedDelete(@NonNull _Note note, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want delete " + note.getTitle() + " ?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", (dialog, which) -> deleteNote(note)).show();
    }


    private static class PickPictureResult extends ActivityResultContract<Void, Uri> {


        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Void input) {
            return new Intent(Intent.ACTION_PICK)
                    .setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        }

        @Nullable
        @Override
        public Uri parseResult(int resultCode, @Nullable Intent intent) {
            return resultCode == Activity.RESULT_OK && intent != null ? intent.getData() : null;
        }


    }

}