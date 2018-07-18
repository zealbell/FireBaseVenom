package linkersoft.blackpanther.firebasevenom;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

public class Venom {

    public Venom(Context context){
        this.context=context;
    }

    private static boolean childExists;
    public static String TAG = "#LiNKeR(>_<)~V~ ";
    public static final boolean NO_TIMESTAMP=false;
    public static final boolean YES_TIMESTAMP=true;
    public static final boolean NO_PUSH=NO_TIMESTAMP;
    public static final boolean YES_PUSH=YES_TIMESTAMP;

    public static Context context;
    public static final String[] DATE_FORMAT_STRINGS={
            "MMM dd, yyyy hh:mm:ss a",
            "MMM dd, yyyy HH:mm:ss a",
            "MMM dd, yyyy hh/mm/ss a",
            "dd/MM/yyyy hh:mm:ss a",
            "hh:mm a",
            "HH:mm a",
            "HH:mm a z",
            "HH:mm a Z",
            "MMM dd, yyyy hh:mm a",
            "MMM dd, yyyy HH:mm:ss a z",
            "MMM dd, yyyy HH:mm:ss a Z"
    };

    public interface venomSize{
        void onInject(long childrenCount, DatabaseError dbErr);
    }
    public interface venomStingFangs {
          void onPosionInjection(boolean childExists, DatabaseError dbErr);
    }
    public interface venomHashResults {
        void onResults(HashMap<String, Object> Results, DatabaseError dbErr);
    }
    public interface venomListResults {
        void onResults(ArrayList<String> Results, DatabaseError dbErr);
    }
    public interface venomDelete {
        void onDeletion(boolean deleted, DatabaseError databaseError);
    }
    public interface venomUploadDownload {
        void onFailure(Exception e);
        void onSuccess(StorageReference downloadRef, Uri downloadUrl);
        void onSuccess(File downloaded);
        void onPause(UploadTask.TaskSnapshot taskSnapshot);
        void onPause(FileDownloadTask.TaskSnapshot taskSnapshot);
        void onProgress(String UploadPercentage);
    }
    public interface venomPushCallBack {
        void onFailure(Exception e);
        void onSuccess();
    }
    public interface venomPullCallBack {
        void onFailure(DatabaseError databaseError);
        void onSuccess(Object childValue);
    }
    public interface Time{
        void dispatchStamp(long timestamp);
    }


    public static void checkIfchildExists(DatabaseReference DbRf, final String childname, final venomStingFangs djI) {
         DbRf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i(TAG," onDataChange");
                if(djI!=null)djI.onPosionInjection(snapshot.child(childname).exists(),null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(djI!=null)djI.onPosionInjection(childExists,databaseError);
            }
        });
//         e.g
//         DatabaseReference Dbrf= FirebaseDatabase.getInstance().getReference();
//        venom.checkIfchildExists(Dbrf, "Users", new venom.venomStingFangs() {
//            @Override
//            public void onGet(boolean childExists, DatabaseError dbErr) {
//                if(childExists){
//                    Log.i(venom.TAG," Child Exists ");
//                }else Log.i(venom.TAG," ChildDoes not Exist "+((dbErr!=null)?dbErr:""));
//            }
//        });


    }
    public static  <T extends Object> void checkIfchildExistsByProperty(DatabaseReference DbRf, String Child , String PropertyChild, T PropertyVal, venomHashResults dsR) {
        SearchByProperty(DbRf,Child,PropertyChild,PropertyVal,dsR);
    }
    public static void SizeOfChildren(DatabaseReference DbRf, final String childname, final venomSize djI){
        DbRf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(djI!=null)djI.onInject(snapshot.getChildrenCount(),null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(djI!=null)djI.onInject(-1,databaseError);
            }
        });
    }
    public static <T extends Object> Query SearchByProperty(DatabaseReference DbRf, String Child, String PropertyChild, T PropertyVal, venomHashResults dsR) {
//gets the value of all children of a child
        // e.g List.orderByChild=> where id(PropertyVal) is equal to 0
//      lists anychild with id=0
//    {
//
//        "interswitch_payment_list" : {
//
//        "-K4YlfoWHZqPyWONv68Y" : { ".priority" : -1.449077948445E12, "date" : 1449077948445, "id" : "0", "name" : "Name_0" },
//        "-K4YlfoWHZqPyWONv68Z" : { ".priority" : -1.449077948445E12, "date" : 1449077948445, "id" : "1", "name" : "Name_1" },
//        "-K4YlfoWHZqPyWONv68_" : { ".priority" : -1.449077948445E12, "date" : 1449077948445, "id" : "0", "name" : "Name_2" },
//        "-K4YlfoWHZqPyWONv68a" : { ".priority" : -1.449077948445E12, "date" : 1449077948445, "id" : "1", "name" : "Name_3" },
//                }
//
//     ... }
        Query query = DbRf.child(Child).orderByChild(PropertyChild);
        if (PropertyVal instanceof String) query = query.equalTo((String) PropertyVal);
        else  if (PropertyVal instanceof Long)query = query.equalTo((Long) PropertyVal);
        else  if (PropertyVal instanceof Integer)query = query.equalTo((Integer) PropertyVal);
        else  if (PropertyVal instanceof Double)query = query.equalTo((Double) PropertyVal);
        Search(query,dsR);
        return query;
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot results) {
//                Log.i(TAG,  " onDataChangeSearchByProperty");
//                for (DataSnapshot rs : results.getChildren()) {
//                    Log.i(TAG, rs.getKey() + " " + rs.getValue());
//                    Log.i(TAG,  " ");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

    public static <T extends Object> Query SpecificSearchByProperty(DatabaseReference DbRf, String Child, String PropertyChild, T PropertyVal, String PropertyKey, venomHashResults dsR) {
       //srch res returns all kids that have d same  PropertyVal & PropertyKey as 4rm d meth
        Query query = DbRf.child(Child).orderByChild(PropertyChild);
            if (PropertyVal instanceof String) query = query.equalTo((String) PropertyVal, PropertyKey);
            else query = query.equalTo((Double) PropertyVal,PropertyKey);
        Search(query,dsR);
        return query;

    }
    public static <T extends Object> Query ltdKidSearch(DatabaseReference DbRf, String Child, String PropertyChild, int limit, boolean firstlimit, venomHashResults dsR) {
//gets the value of all children of a child by ordering according 2 PropertyChild's value alphanumerically and limiting to...
        // e.g List.orderByChild(timestamp)=>  limit to first 10kids

        Query query = DbRf.child(Child).orderByChild(PropertyChild);
        query = firstlimit?query.limitToFirst(limit):query.limitToLast(limit);
        Search(query,dsR);
        return query;
    }
    public static <T extends Object> Query ltdKidSearchByKey(DatabaseReference DbRf, String Child, int limit, boolean firstlimit, venomHashResults dsR) {
//gets the value of latest children of a child by ordering according 2 key values  and limiting to...
        // e.g List.orderByKey()=>  limit to first 10 recently added kids
        Query query = DbRf.child(Child).orderByKey();
        query = firstlimit?query.limitToFirst(limit):query.limitToLast(limit);
        Search(query,dsR);
        return query;
    }
    public static  <T extends Object>Query ltdSearchByProperty(DatabaseReference DbRf, String Child, String PropertyChild, T PropertyVal, int limit, boolean firstlimit, final venomHashResults dsR){
//       limits the Search result to the 1st e.g say 50(if int limit=50)
//        e.g
//        Dataset = A, C, B, E, F
//        orderByChild (ascending) = A, B, C, E, F
//        limitToFirst (3) = A, F, C
//        limitToLast (2) = E, F
        Query query;
        if(PropertyVal instanceof String) {
            query = (firstlimit) ? DbRf.child(Child).orderByChild(PropertyChild).equalTo((String) PropertyVal).limitToFirst(limit) :
                    DbRf.child(Child).orderByChild(PropertyChild).equalTo((String)PropertyVal).limitToLast(limit);
        }else{
            query = (firstlimit) ? DbRf.child(Child).orderByChild(PropertyChild).equalTo( (PropertyVal instanceof Integer)?(Integer)PropertyVal:((PropertyVal instanceof Long)?(Long)PropertyVal:(Double) PropertyVal)).limitToFirst(limit) :
                    DbRf.child(Child).orderByChild(PropertyChild).equalTo((Double)PropertyVal).limitToLast(limit);
        }Search(query,dsR);
        return query;
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot results) {
//                for (DataSnapshot rs : results.getChildren()) {
//                    Log.i(TAG, rs.getKey() + " " + rs.getValue());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
    public static <T extends Object>Query SearchAt(Query query, String PropertyChild, String How, T startPropertyVal, T endPropertyVal, final venomHashResults dsR){

        //get the value of all children from the point a child  id=1 downwards
        // e.gList.orderByChild=> starting from where id(PropertyVal) was first equal to 1 and downwards i.e. "List.orderByChild(id).startAt(1)
        //so even where id is 0 below where it was first 1 ld be included
//    {
//
//        "interswitch_payment_list" : {
//
//        "-K4YlfoWHZqPyWONv68Y" : { ".priority" : -1.449077948445E12, "date" : 1449077948445, "id" : "0", "name" : "Name_0" },
//        "-K4YlfoWHZqPyWONv68Z" : { ".priority" : -1.449077948445E12, "date" : 1449077948445, "id" : "1", "name" : "Name_1" },
//        "-K4YlfoWHZqPyWONv68_" : { ".priority" : -1.449077948445E12, "date" : 1449077948445, "id" : "0", "name" : "Name_2" },
//        "-K4YlfoWHZqPyWONv68a" : { ".priority" : -1.449077948445E12, "date" : 1449077948445, "id" : "1", "name" : "Name_3" },
//                }
//
//     ... }
        final HashMap<String,Object> Results=new HashMap<>();
        query=query.orderByChild(PropertyChild);
//        query=query.orderByKey();
        if(How.contentEquals("sAt")){
            if(startPropertyVal instanceof String) query=query.startAt((String) startPropertyVal);
            else query=query.startAt((Double)startPropertyVal);
        }else if(How.contentEquals("eAt")){
            if(endPropertyVal instanceof String) query=query.endAt((String) endPropertyVal);
            else query=query.endAt((Double)endPropertyVal);
        }else {//"sAt&eAt"
            if(startPropertyVal instanceof String) query=query.startAt((String) startPropertyVal);
            else query=query.startAt((Double)startPropertyVal);
            if(endPropertyVal instanceof String) query=query.endAt((String) endPropertyVal);
            else query=query.endAt((Double)endPropertyVal);
        }query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot results) {
                for (DataSnapshot rs : results.getChildren()) {
                    Log.i(TAG, rs.getKey() + " " + rs.getValue());
                    Results.put(rs.getKey(),rs.getValue());
                }if(dsR!=null)dsR.onResults(Results,null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(dsR!=null)dsR.onResults(null,databaseError);
            }
        });



        return query;
    }
    public static  Query getLastKid(DatabaseReference DbRf, String Child, String PropertyChild, boolean orderByKey, venomHashResults dsR){
        Query query = ((orderByKey)?DbRf.child(Child).orderByKey():DbRf.child(Child).orderByChild(PropertyChild)).limitToLast(1);
        Search(query,dsR);
        return query;
    }

    
    public static  void getMostCurrentTimeStamp(DatabaseReference DbRf, String Child, final Time Tstp){
        Query query =DbRf.child(Child).orderByChild("timestamp").limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tstp.dispatchStamp((long)dataSnapshot.child("timestamp").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static void addChildListerner(DatabaseReference DbRf, String Child, ChildEventListener childEventListener){
        DbRf.child(Child).addChildEventListener(childEventListener);
    }

    public static Query Search(Query query,final venomHashResults dsR){
        final HashMap<String,Object> Results=new HashMap<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot results) {
                for (DataSnapshot rs : results.getChildren()) {
                    Log.i(TAG, rs.getKey() + " " + rs.getValue());
                   Results.put(rs.getKey(),rs.getValue());
                }if(dsR!=null)dsR.onResults(Results,null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG,  " " + databaseError);
                if(dsR!=null)dsR.onResults(null,databaseError);
            }
        });return query;
    }
    public static Query downloadNode(DatabaseReference DbRf, String Child, final venomHashResults dsR){
        Query query;
        final HashMap<String,Object> Results=new HashMap<>();
        (query=DbRf.child(Child)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot results) {
                for (DataSnapshot rs : results.getChildren()) {
                    Results.put(rs.getKey(),rs.getValue());
                    Log.i(TAG, "key: "+rs.getKey() + " value: " + rs.getValue());
                }if(dsR!=null)dsR.onResults(Results,null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(dsR!=null)dsR.onResults(null,databaseError);
            }
        });return query;
        }
    public static Query downloadKeys(DatabaseReference DbRf, String Child, final venomListResults dsR){
        Query query;
        final ArrayList<String> Results=new ArrayList<>();
        (query=DbRf.child(Child)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot results) {
                for (DataSnapshot rs : results.getChildren()) {
                    Results.add(rs.getKey());
                    Log.i(TAG, "key: "+rs.getKey());
                }if(dsR!=null)dsR.onResults(Results,null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(dsR!=null)dsR.onResults(null,databaseError);
            }
        });return query;
    }
    public static void deleteNode(DatabaseReference DbRf,String NodeChild){
         DbRf.child(NodeChild).removeValue();
    }
    private static void deleteNodes(Query query,final venomDelete dsD){
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot results) {
                for (DataSnapshot rs : results.getChildren())rs.getRef().removeValue();
                if(dsD!=null)dsD.onDeletion(true,null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(dsD!=null)dsD.onDeletion(false,databaseError);
            }
        });
    }

    public static UploadTask UploadFile(StorageReference Strf, File f, boolean viaInputStream, final venomUploadDownload dsPD) throws FileNotFoundException {


        Log.i("Happening"," Uploaded Started");
        if(!viaInputStream){
            Uri file= Uri.fromFile(f);
            UploadTask uplT=Strf.putFile(file);
            uplT.addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception e){
                    if(dsPD!=null)dsPD.onFailure(e);
                    Log.i("Happening","UploadError: "+e);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                    if(dsPD!=null)dsPD.onSuccess(taskSnapshot.getStorage(),taskSnapshot.getDownloadUrl());
                    Log.i("Happening"," Successfully Uploaded");
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>(){
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot){
                    if(dsPD!=null)dsPD.onPause(taskSnapshot);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>(){
                int ptr;
                float totalBytes,UploadProgress;
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot){
                    if(ptr==0){
                        totalBytes=(float)taskSnapshot.getTotalByteCount();
                        ptr=1;
                    }UploadProgress=((int)taskSnapshot.getBytesTransferred())/totalBytes;
                    if(dsPD!=null)dsPD.onProgress((UploadProgress*100)+"%");
                }
            });return uplT;
        }else{
            InputStream streamy=new FileInputStream(f);
            UploadTask uplT=Strf.putStream(streamy);
            uplT.addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception e){
                    if(dsPD!=null)dsPD.onFailure(e);
                    Log.i("Happening","UploadError: "+e);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                    if(dsPD!=null)dsPD.onSuccess(taskSnapshot.getStorage(),taskSnapshot.getDownloadUrl());
                    Log.i("Happening"," Successfully Uploaded");
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>(){
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot){
                    if(dsPD!=null)dsPD.onPause(taskSnapshot);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>(){
                int ptr;
                float totalBytes,UploadProgress;
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot){
                    if(ptr==0){
                        totalBytes=(float)taskSnapshot.getTotalByteCount();
                        ptr=1;
                    }UploadProgress=((int)taskSnapshot.getBytesTransferred())/totalBytes;
                    if(dsPD!=null)dsPD.onProgress((UploadProgress*100)+"%");
                }
            });return uplT;
        }
    }
    public static<T extends Object> FileDownloadTask DownloadFile(T strf, String downloadPath, final venomUploadDownload dsPD) throws IOException {
            StorageReference Strf=(strf instanceof String)?FirebaseStorage.getInstance().getReferenceFromUrl((String) strf):(StorageReference)strf;
            final File container=new File(downloadPath);container.createNewFile();
            FileDownloadTask fdlT=Strf.getFile(container);
            fdlT.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if(dsPD!=null)dsPD.onFailure(e);
                }
            }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>(){
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot){
                    if(dsPD!=null)dsPD.onSuccess(container);
                }
            }).addOnPausedListener(new OnPausedListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onPaused(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if(dsPD!=null)dsPD.onPause(taskSnapshot);
                }
            }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>(){
                float totalBytes,DownloadProgress;
                @Override
                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot){
                    totalBytes=(float)taskSnapshot.getTotalByteCount();
                    DownloadProgress=((int)taskSnapshot.getBytesTransferred())/totalBytes;
                    if(dsPD!=null)dsPD.onProgress((DownloadProgress*100)+"%");
                }
            });
            return fdlT;
    }
    
    public static<T extends Object> void push(DatabaseReference DbRf, String Child, T Value, boolean push, final venomPushCallBack Icb){

        ((push)?DbRf.child(Child).push():DbRf.child(Child)).setValue(Value).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e){
                if(Icb!=null)Icb.onFailure(e);
                Log.i("Happening","UploadError: "+e);
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(Icb!=null)Icb.onSuccess();
                Log.i("Happening"," Success");
            }
        });
    }
    public static void push(DatabaseReference DbRf, String Child, boolean timestamp, boolean push, final venomPushCallBack Icb, ArrayList<String> Keys, ArrayList<String> Objects){
        HashMap<String,Object> rawHash=Kiddup(Keys,Objects);
        if(timestamp)rawHash.put("timestamp", ServerValue.TIMESTAMP);
        ((push)?DbRf.child(Child).push():DbRf.child(Child)).setValue(rawHash).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e){
                if(Icb!=null)Icb.onFailure(e);
                Log.i("Happening","UploadError: "+e);
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void aVoid){
                if(Icb!=null)Icb.onSuccess();
                Log.i("Happening"," Success");
            }
        });
    }
    public static void push(DatabaseReference DbRf, String Child, boolean timestamp, boolean push, HashMap<String,Object> rawHash, final venomPushCallBack Icb){
        if(timestamp)rawHash.put("timestamp", ServerValue.TIMESTAMP);
        ((push)?DbRf.child(Child).push():DbRf.child(Child)).setValue(rawHash).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e){
                if(Icb!=null)Icb.onFailure(e);
                Log.i("Happening","UploadError: "+e);
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void aVoid){
                if(Icb!=null)Icb.onSuccess();
                Log.i("Happening"," Success");
            }
        });
    }
    public static void pull(DatabaseReference DbRf, String Child, final venomPullCallBack pullover){
        DbRf.child(Child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(pullover!=null)pullover.onSuccess(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(pullover!=null)pullover.onFailure(databaseError);
            }
        });
    }
    
    static HashMap<String,Object> Kiddup(ArrayList<String> Keys, ArrayList<String> Objects){
        HashMap<String,Object> raw=new HashMap<>();
        for (int i = 0; i < Keys.size(); i++) {
            raw.put(Keys.get(i),Objects.get(i));
        }return raw;
    }
    public static HashMap<String,Object> JSONtoHashMap(Object JSON)throws JSONException {
        HashMap<String, Object> hashMap = new HashMap<>();
        JSONObject jObject = new JSONObject(JSON.toString());
        Iterator<?> keys = jObject.keys();
        while( keys.hasNext() ){
            String key = (String)keys.next();
            hashMap.put(key,jObject.get(key));
        }return hashMap;
    }
    public static JSONObject HashMaptoJSON(HashMap<String,Object> map){
        return new JSONObject(map);
    }

    public static Object[] LT2UTC_datetime() throws ParseException {
        long rawLT = System.currentTimeMillis();
        Date LT = new Date(rawLT);
        SimpleDateFormat locFmrt = new SimpleDateFormat(DATE_FORMAT_STRINGS[0],getCurrentLocale(context));
        locFmrt.setTimeZone(TimeZone.getDefault());
        SimpleDateFormat utcFmrt = new SimpleDateFormat(DATE_FORMAT_STRINGS[0],getCurrentLocale(context));
        utcFmrt.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date UTC = locFmrt.parse(utcFmrt.format(LT));
        return new Object[]{UTC.toString(),UTC};
    }
    public static Object[] UTC2LT_datetime(long rawUTC) throws ParseException {
        Date UTC = new Date(rawUTC);
        SimpleDateFormat utcFmrt = new SimpleDateFormat(DATE_FORMAT_STRINGS[0],getCurrentLocale(context));
        utcFmrt.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat locFmrt = new SimpleDateFormat(DATE_FORMAT_STRINGS[0],getCurrentLocale(context));
        locFmrt.setTimeZone(TimeZone.getDefault());
        Date LT = utcFmrt.parse(locFmrt.format(UTC));
        return new Object[]{LT.toString(),LT};
    }
    public static long Date2Long(Date date){
        return date.getTime();
    }
    public static String FormatDate(Date date, String FormatString){
        SimpleDateFormat dFrmt = new SimpleDateFormat(FormatString,getCurrentLocale(context));
        return dFrmt.format(date);
    }
    public static Date getCurrentDate(){
        return new Date(System.currentTimeMillis());
    }
    public static Date String2Date(String date, String Frmt) throws ParseException {
        SimpleDateFormat dFmrt=new SimpleDateFormat(Frmt,getCurrentLocale(context));
        return dFmrt.parse(date);
    }
    public static Date Long2Date(long date){
        return new Date(date);
    }
    public static String getTimeZoneAbbrev(){
        return Calendar.getInstance().getTimeZone().getDisplayName(false, TimeZone.SHORT);
    }
    public static int getCurrentHour(){
        return  Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }
    public static int getCurrentMinute(){
        return  Calendar.getInstance().get(Calendar.MINUTE);
    }
    public static int getCurrentSecond(){
        return  Calendar.getInstance().get(Calendar.SECOND);
    }
    public static Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return context.getResources().getConfiguration().getLocales().get(0);
        else return context.getResources().getConfiguration().locale;
    }



/*so when you refactor meth name it updates here2

 public static String TAG;
    public static final boolean NO_TIMESTAMP;
    public static final boolean YES_TIMESTAMP;
    public static final boolean NO_PUSH;
    public static final boolean YES_PUSH;
    public static final String[] DATE_FORMAT_STRINGS

    public interface venomSize
    public interface venomStingFangs
    public interface venomHashResults
    public interface venomListResults
    public interface venomDelete
    public interface venomUploadDownload
    public interface venomPushCallBack
    public interface venomPullCallBack
    public interface Time


    public static void checkIfchildExists(DatabaseReference DbRf, final String childname, final venomStingFangs djI)
    public static  <T extends Object> void checkIfchildExistsByProperty(DatabaseReference DbRf, String Child , String PropertyChild, T PropertyVal, venomHashResults dsR)
    public static void SizeOfChildren(DatabaseReference DbRf, final String childname, final venomSize djI)
    public static <T extends Object> Query SearchByProperty(DatabaseReference DbRf, String Child, String PropertyChild, T PropertyVal, venomHashResults dsR)
    public static <T extends Object> Query SpecificSearchByProperty(DatabaseReference DbRf, String Child, String PropertyChild, T PropertyVal, String PropertyKey, venomHashResults dsR)
    public static <T extends Object> Query ltdKidSearch(DatabaseReference DbRf, String Child, String PropertyChild, int limit, boolean firstlimit, venomHashResults dsR)
    public static <T extends Object> Query ltdKidSearchByKey(DatabaseReference DbRf, String Child, int limit, boolean firstlimit, venomHashResults dsR)
    public static  <T extends Object>Query ltdSearchByProperty(DatabaseReference DbRf, String Child, String PropertyChild, T PropertyVal, int limit, boolean firstlimit, final venomHashResults dsR)
    public static <T extends Object>Query SearchAt(Query query, String PropertyChild, String How, T startPropertyVal, T endPropertyVal, final venomHashResults dsR)
    public static  Query getLastKid(DatabaseReference DbRf, String Child, String PropertyChild, boolean orderByKey, venomHashResults dsR)
    public static  void getMostCurrentTimeStamp(DatabaseReference DbRf, String Child, final Time Tstp)
    public static void addChildListerner(DatabaseReference DbRf, String Child, ChildEventListener childEventListener)
    public static Query Search(Query query,final venomHashResults dsR)
    public static Query downloadNode(DatabaseReference DbRf, String Child, final venomHashResults dsR)
    public static Query downloadKeys(DatabaseReference DbRf, String Child, final venomListResults dsR)
    public static void deleteNode(DatabaseReference DbRf,String NodeChild)
    private static void deleteNodes(Query query,final venomDelete dsD)
    public static UploadTask UploadFile(StorageReference Strf, File f, boolean viaInputStream, final venomUploadDownload dsPD) throws FileNotFoundException
    public static<T extends Object> FileDownloadTask DownloadFile(T strf, String downloadPath, final venomUploadDownload dsPD) throws IOException
    public static<T extends Object> void push(DatabaseReference DbRf, String Child, T Value, boolean push, final venomPushCallBack Icb)
    public static void push(DatabaseReference DbRf, String Child, boolean timestamp, boolean push, final venomPushCallBack Icb, ArrayList<String> Keys, ArrayList<String> Objects)
    public static void push(DatabaseReference DbRf, String Child, boolean timestamp, boolean push, HashMap<String,Object> rawHash, final venomPushCallBack Icb)
    public static void pull(DatabaseReference DbRf, String Child, final venomPullCallBack pullover)
    public static HashMap<String,Object> Kiddup(ArrayList<String> Keys, ArrayList<String> Objects)
    public static HashMap<String,Object> JSONtoHashMap(Object JSON)throws JSONException
    public static JSONObject HashMaptoJSON(HashMap<String,Object> map)
    public static Object[] LT2UTC_datetime() throws ParseException
    public static Object[] UTC2LT_datetime(long rawUTC) throws ParseException
    public static long Date2Long(Date date)
    public static String FormatDate(Date date, String FormatString)
    public static Date getCurrentDate()
    public static Date String2Date(String date, String Frmt) throws ParseException
    public static Date Long2Date(long date)
    public static String getTimeZoneAbbrev()
    public static int getCurrentHour()
    public static int getCurrentMinute()
    public static int getCurrentSecond()
    public static Locale getCurrentLocale(Context context)

 */
  

}


