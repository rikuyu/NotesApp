package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamplesActivity extends AppCompatActivity {

    private static final String TAG = "ExampleActivity";

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examples);
    }

    public void createDocument(View view) {
        Map<String, Object> map = new HashMap<>();
        map.put("テキスト", "エウレカ");
        map.put("isCompleted", false);
        map.put("作成日", new Timestamp(new Date()));

        firestore.collection("memos").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "データ作成成功");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "データ作成失敗");
                    }
                });
    }

    public void readDocument(View view) {
        FirebaseFirestore.getInstance()
                .collection("memos")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshot) {
                        Log.d(TAG, "読み取り成功");
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshot.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "読み取ったもの" + snapshot.getData().toString());
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "読み取り失敗" + e);
                    }
                });
    }

    public void getChangeDocument(View view) {
        FirebaseFirestore.getInstance()
                .collection("memos")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshot, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e(TAG, "エラー" + error);

                            return;
                        }
                        if (queryDocumentSnapshot != null) {
                            Log.d(TAG, "onEvent: -------------------------------");
//                            List<DocumentSnapshot> snapshotList = queryDocumentSnapshot.getDocuments();
//                            for(DocumentSnapshot snapshot : snapshotList){
//                                Log.d(TAG, "onEvent: "+ snapshot.getData());
//                            }
                            // 変化したドキュメントだけ取得できる
//                            List<DocumentChange> docChangeList = queryDocumentSnapshot.getDocumentChanges();
//                            for(DocumentChange docChange : docChangeList){
//                                Log.d(TAG, "onEvent: "+ docChange.getDocument().getData());
//                            }


                        } else {
                            Log.e(TAG, "onEvent: スナップショットがnull");
                        }
                    }
                });
    }

    public void updateDocument(View view) {
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("memos")
                .document("Q3jsHLy8UuARhhdFMpm0");

        Map<String, Object> map = new HashMap<>();
        map.put("テキスト", "絶対インターン合格");

        // updateはドキュメントの特定のフィールドを更新する
//        docRef.update(map)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "onSuccess: 更新成功");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "onFailure: 更新失敗" + e);
//                    }
//                });

        // setはドキュメント全体を書き換える（第二引数に「SetOptions.merge()」を渡すと、updateと同義）
        docRef.set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: set更新成功");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: set更新失敗" + e);
                    }
                });
    }

    public void deleteDocument(View view) {
        FirebaseFirestore.getInstance()
                .collection("memos");
//                .document("123")
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "onSuccess: 削除成功");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "onFailure: 削除失敗" + e);
//                    }
//                });

    }
}