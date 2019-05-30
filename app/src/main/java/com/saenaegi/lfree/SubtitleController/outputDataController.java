package com.saenaegi.lfree.SubtitleController;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class outputDataController {

    private LinkedHashMap<String,ArrayList<SubtitleData>> allOfSubtitle=new LinkedHashMap<>();
    private FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    private StorageReference LookReference=firebaseStorage.getReference().child( "LookSubtitle");
    private StorageReference ListorageReference=firebaseStorage.getReference().child( "ListenSubtitle");
    private File readFile;

    public LinkedHashMap<String, ArrayList<SubtitleData>> getListenSubtitleData(File filedirectory, int sectionNum, String idvideo, ArrayList<SubtitleAndKey> temp) {
        Collections.sort(temp);
        File file=filedirectory.getAbsoluteFile();
        FileDownloadTask fileDownloadTask;

        if(!file.exists())
            filedirectory.mkdirs();

        for(SubtitleAndKey subtitleAndKey:temp){
            readFile= new File(file,subtitleAndKey.getKey()+".txt");
            fileDownloadTask=ListorageReference.child( idvideo ).child( String.valueOf( sectionNum )).child( subtitleAndKey.getKey()+".txt" ).getFile(readFile);
            parsingFile( subtitleAndKey.getKey() );
        }
        return allOfSubtitle;
    }

    public LinkedHashMap<String, ArrayList<SubtitleData>> getLookSubtitleData(File filedirectory, int sectionNum, String idvideo, ArrayList<SubtitleAndKey> temp) {
        Collections.sort(temp);
        File file=filedirectory.getAbsoluteFile();
        FileDownloadTask fileDownloadTask;

        if(!file.exists())
            filedirectory.mkdirs();

        for(SubtitleAndKey subtitleAndKey:temp){
            readFile= new File(file,subtitleAndKey.getKey()+".txt");
            fileDownloadTask=LookReference.child( idvideo ).child( String.valueOf( sectionNum )).child( subtitleAndKey.getKey()+".txt" ).getFile(readFile);
            parsingFile( subtitleAndKey.getKey() );
        }
        return allOfSubtitle;
    }

    public void parsingFile(String key){
        ArrayList<SubtitleData> subtitleDatas=new ArrayList<>();
        String oneLine=null;

        try {
            FileReader fileReader=new FileReader( readFile );
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            while((oneLine=bufferedReader.readLine())!=null){
                String[] arr=oneLine.split( "\t" );
                SubtitleData subtitleData=new SubtitleData( arr[0].trim(),arr[1].trim(),arr[2].trim() );
                subtitleDatas.add( subtitleData );
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        allOfSubtitle.put( key,subtitleDatas );

    }

}
